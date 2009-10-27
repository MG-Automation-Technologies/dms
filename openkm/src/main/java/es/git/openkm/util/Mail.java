/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPFolder;

import es.git.openkm.api.OKMDocument;
import es.git.openkm.api.OKMFolder;
import es.git.openkm.api.OKMMail;
import es.git.openkm.api.OKMRepository;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Repository;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.FileSizeExceededException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.UnsupportedMimeTypeException;
import es.git.openkm.core.VirusDetectedException;

public class Mail {
	private static Logger log = LoggerFactory.getLogger(Mail.class);
	
	/**
	 * Send mail without FROM addresses.
	 * 
	 * @param toAddress Destination addresses.
	 * @param subject The mail subject.
	 * @param text The mail body.
	 * @throws MessagingException If there is any error.
	 */
	public static void send(Collection<String> toAddress, String subject, String text) throws MessagingException {
		send(null, toAddress, subject, text);
	}

	/**
	 * Send mail with FROM addresses.
	 * 
	 * @param fromAddress Origin address.
	 * @param toAddress Destination addresses.
	 * @param subject The mail subject.
	 * @param text The mail body.
	 * @throws MessagingException If there is any error.
	 */
	public static void send(String fromAddress, Collection<String> toAddress, String subject, String text) throws MessagingException {
		log.debug("send("+fromAddress+", "+toAddress+", "+subject+", "+text+")");
		Session mailSession = null;

		try {
			InitialContext initialContext = new InitialContext();
			mailSession = (Session) PortableRemoteObject.narrow(initialContext.lookup("java:/mail/OpenKM"), Session.class);
		} catch (javax.naming.NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MimeMessage m = new MimeMessage(mailSession);

		if (fromAddress != null) {
			InternetAddress from = new InternetAddress(fromAddress);
			m.setFrom(from);
		} else {
			m.setFrom();
		}
		
		InternetAddress[] to = new InternetAddress[toAddress.size()];
		int i = 0;
		
		for (Iterator<String> it = toAddress.iterator(); it.hasNext(); ) {
			to[i++] = new InternetAddress(it.next());
		}
		
		m.addHeader("charset", "UTF-8");
		m.setRecipients(Message.RecipientType.TO, to);
		m.setSubject(subject, "UTF-8");
		m.setSentDate(new Date());
		
		// Build a multiparted mail with HTML and text content for better SPAM behaviour
		MimeMultipart content = new MimeMultipart("alternative");
		
		// HTML Part
		MimeBodyPart htmlPart = new MimeBodyPart();
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
		htmlContent.append("<html>\n<head>\n");
		htmlContent.append("<meta content=\"text/html;charset=UTF-8\" http-equiv=\"Content-Type\"/>\n");
		htmlContent.append("</head>\n<body>\n");
		htmlContent.append(text);
		htmlContent.append("\n</body>\n</html>");
		htmlPart.setContent(htmlContent.toString(), "text/html; charset=UTF-8");
		htmlPart.setHeader("Content-Type", "text/html");
		
		// Text part
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(text.replaceAll("<br/?>", "\n").replaceAll("<[^>]*>", ""));
		textPart.setHeader("Content-Type", "text/plain");
		
        content.addBodyPart(textPart);
        content.addBodyPart(htmlPart);
        m.setContent(content);		
		Transport.send(m);
		log.debug("send: void");		
	}
	
	/**
	 * 
	 */
	public static void importMessages(String token, String uid, String host, String user, String password,
			String imapFolder) throws PathNotFoundException, ItemExistsException, VirusDetectedException,
			AccessDeniedException, RepositoryException {
		log.info("importMessages("+token+", "+uid+", "+host+", "+user+", "+password+", "+imapFolder+")");
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		
		try {
			OKMRepository okmRepository = OKMRepository.getInstance();
			OKMFolder okmFolder = OKMFolder.getInstance();
			OKMMail okmMail = OKMMail.getInstance();
			String mailPath = "/"+Repository.HOME+"/"+uid+"/"+Repository.MAIL;
			
			if (okmRepository.hasNode(token, mailPath)) {
				// Open connection
				Store store = session.getStore("imaps");
				store.connect(host, user, password);
				
				Folder folder = store.getFolder(imapFolder);
				folder.open(Folder.READ_ONLY);
				//Message messages[] = folder.getMessages();
				Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
						
				for (int i=0; i < messages.length; i++) {
					Message msg = messages[i];
					//log.info(i + ": " + msg.getFrom()[0] + " " + msg.getSubject()+" "+msg.getContentType());
					//log.info("Received: "+msg.getReceivedDate());
					//log.info("Sent: "+msg.getSentDate());
					
					Calendar receivedDate = Calendar.getInstance();
					Calendar sentDate = Calendar.getInstance();
					
					// Can be void
					if (msg.getReceivedDate() != null) {
						receivedDate.setTime(msg.getReceivedDate());
					}
					
					// Can be void
					if (msg.getSentDate() != null) {
						sentDate.setTime(msg.getSentDate());
					}
					
					log.info(i+" -> "+msg.getSubject()+" - "+msg.getReceivedDate());
					String path = mailPath+"/"+receivedDate.get(Calendar.YEAR);
					
					if (!okmRepository.hasNode(token, path)) {
						es.git.openkm.bean.Folder fld = new es.git.openkm.bean.Folder();
						fld.setPath(path);
						okmFolder.create(token, fld);
					}
					
					path += "/"+(receivedDate.get(Calendar.MONTH)+1);
					
					if (!okmRepository.hasNode(token, path)) {
						es.git.openkm.bean.Folder fld = new es.git.openkm.bean.Folder();
						fld.setPath(path);
						okmFolder.create(token, fld);
					}
					
					path += "/"+receivedDate.get(Calendar.DAY_OF_MONTH);
					
					if (!okmRepository.hasNode(token, path)) {
						es.git.openkm.bean.Folder fld = new es.git.openkm.bean.Folder();
						fld.setPath(path);
						okmFolder.create(token, fld);
					}
					
					es.git.openkm.bean.Mail mail = new es.git.openkm.bean.Mail();
					mail.setPath(path+"/"+((IMAPFolder)folder).getUID(msg)+"-"+FileUtils.escape(msg.getSubject()));
					mail.setReceivedDate(receivedDate);
					mail.setSentDate(sentDate);
					
					String body = getText(msg);
					//log.info("getText: "+body);
					if (body.charAt(0) == 'H') {
						mail.setMimeType("text/html");
					} else if (body.charAt(0) == 'T') {
						mail.setMimeType("text/plain");
					} else {
						mail.setMimeType("unknown");
					}
					
					mail.setContent(body.substring(1));
					
					if (msg.getFrom().length > 0) {
						mail.setFrom(MimeUtility.decodeText(msg.getFrom()[0].toString()));
					}
					
					mail.setSize(msg.getSize());
					mail.setSubject(msg.getSubject());
					mail.setTo(address2String(msg.getRecipients(Message.RecipientType.TO)));
					mail.setCc(address2String(msg.getRecipients(Message.RecipientType.CC)));
					mail.setBcc(address2String(msg.getRecipients(Message.RecipientType.BCC)));
					
					String newMailPath = FileUtils.getParent(mail.getPath())+"/"+FileUtils.escape(FileUtils.getName(mail.getPath())); 
					log.info("newMailPath: "+newMailPath);
					
					if (!okmRepository.hasNode(token, newMailPath)) {
						okmMail.create(token, mail);
						try {
							addAttachments(token, mail, msg);
						} catch (UnsupportedMimeTypeException e) {
							e.printStackTrace();
						} catch (FileSizeExceededException e) {
							e.printStackTrace();
						}
					}
					
					// Set message as seen
					//msg.setFlag(Flags.Flag.SEEN, true);
				}
			
				// Close connection 
				folder.close(false);
				store.close();
			}
		} catch (NoSuchProviderException e) {
			log.error(e.getMessage(), e);
		} catch (MessagingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		log.info("importMessages: void");
	}
	
	/**
	 * @param p
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private static String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String)p.getContent();

			if (p.isMimeType("text/html")) {
				return "H"+s;
			} else if (p.isMimeType("text/plain")) {
				return "T"+s;
			} else {
				return "X"+s;
			}
		} else if (p.isMimeType("multipart/alternative")) {
			// prefer plain text over html
			Multipart mp = (Multipart)p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}
	
	/**
	 * @param p
	 * @throws MessagingException
	 * @throws IOException
	 * @throws RepositoryException 
	 * @throws AccessDeniedException 
	 * @throws PathNotFoundException 
	 * @throws ItemExistsException 
	 * @throws VirusDetectedException 
	 * @throws FileSizeExceededException 
	 * @throws UnsupportedMimeTypeException 
	 */
	private static void addAttachments(String token, es.git.openkm.bean.Mail mail, Part p) throws 
			MessagingException, IOException, UnsupportedMimeTypeException,
			FileSizeExceededException, VirusDetectedException, ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException {
		if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();
			int count = mp.getCount();
			OKMDocument okmDocument = OKMDocument.getInstance();
			
			for (int i = 1; i < count; i++) {
				BodyPart bp = mp.getBodyPart(i);
				
				if (bp.getFileName() != null) {
					Document attachment = new Document();
					String mimeType = Config.mimeTypes.getContentType(bp.getFileName().toLowerCase());
					attachment.setMimeType(mimeType);
					attachment.setPath(mail.getPath()+"/"+bp.getFileName());
					InputStream is = bp.getInputStream();
					okmDocument.create(token, attachment, is);
					is.close();
				}
			}
		}
	}
	
	/**
	 * @param addresses
	 * @return
	 */
	private static String[] address2String(Address[] addresses) {
		ArrayList<String> list = new ArrayList<String>();
		
		if (addresses != null) {
			for (int i=0; i<addresses.length; i++) {
				list.add(addresses[i].toString()); 
			}
		}
		
		return (String[]) list.toArray(new String[0]);
	}
	
	/**
	 * @param fullUrl
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getTinyUrl(String fullUrl) throws HttpException, IOException {
		HttpClient httpclient = new HttpClient();
 
		// Prepare a request object
		HttpMethod method = new GetMethod("http://tinyurl.com/api-create.php");
		method.setQueryString(new NameValuePair[]{ new NameValuePair("url", fullUrl) });
		httpclient.executeMethod(method);
		InputStreamReader isr = new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8");
		StringWriter sw = new StringWriter();
		int c; while ((c = isr.read()) != -1) sw.write(c);
		isr.close();
		method.releaseConnection();
	
		return sw.toString();
	}
}
