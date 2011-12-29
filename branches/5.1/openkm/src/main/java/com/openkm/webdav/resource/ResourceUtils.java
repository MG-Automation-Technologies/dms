/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
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

package com.openkm.webdav.resource;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradmcevoy.common.Path;
import com.bradmcevoy.http.Resource;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMMail;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Mail;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.webdav.JcrSessionTokenHolder;

public class ResourceUtils {
	private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);
	
	/**
	 * Resolve folder resource.
	 */
	public static Resource getFolder(Path path, String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		String token = JcrSessionTokenHolder.get();
		String fixedFldPath = fixRepositoryPath(fldPath);
		Folder fld = OKMFolder.getInstance().getProperties(token, fixedFldPath);
		List<Folder> fldChilds = OKMFolder.getInstance().getChilds(token, fixedFldPath);
		List<Document> docChilds = OKMDocument.getInstance().getChilds(token, fixedFldPath);
		List<Mail> mailChilds = OKMMail.getInstance().getChilds(token, fixedFldPath);
		Resource fldResource = new FolderResource(path, fld, fldChilds, docChilds, mailChilds);
		return fldResource;
	}
	
	/**
	 * Resolve document resource.
	 */
	public static Resource getDocument(String docPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		String token = JcrSessionTokenHolder.get();
		String fixedDocPath = fixRepositoryPath(docPath);
		Document doc = OKMDocument.getInstance().getProperties(token, fixedDocPath);
		Resource docResource = new DocumentResource(doc);
		return docResource;
	}
	
	/**
	 * Resolve mail resource.
	 */
	public static Resource getMail(String mailPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		String token = JcrSessionTokenHolder.get();
		String fixedMailPath = fixRepositoryPath(mailPath);
		Mail mail = OKMMail.getInstance().getProperties(token, fixedMailPath);
		Resource docResource = new MailResource(mail);
		return docResource;
	}
	
	/**
	 * Resolve node resource (may be folder or document)
	 */
	public static Resource getNode(Path srcPath, String path) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("getNode({}, {})", srcPath, path);
		String token = JcrSessionTokenHolder.get();
		String fixedPath = ResourceUtils.fixRepositoryPath(path);
		
		try {
			if (OKMFolder.getInstance().isValid(token, fixedPath)) {
				Resource res = getFolder(srcPath, path);
				log.debug("getNode: {}", res);
				return res;
			} else if (OKMDocument.getInstance().isValid(token, fixedPath)) {
				Resource res = getDocument(path);
				log.debug("getNode: {}", res);
				return res;
			} else if (OKMMail.getInstance().isValid(token, fixedPath)) {
				Resource res = getMail(path);
				log.debug("getNode: {}", res);
				return res;
			}
		} catch (PathNotFoundException e) {
			log.warn("PathNotFoundException: {}", e.getMessage());
		}
		
		log.debug("getNode: null");
		return null;
	}
	
	/**
	 * Create HTML content.
	 */
	public static void createContent(OutputStream out, Path path, List<Folder> fldChilds, List<Document> docChilds,
			List<Mail> mailChilds) {
		log.debug("createContent({}, {}, {}, {}, {})", new Object[] { out, path, fldChilds, docChilds, mailChilds });
		PrintWriter pw = new PrintWriter(out);
		pw.println("<html>");
		pw.println("<header>");
		pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		pw.println("<link rel=\"Shortcut icon\" href=\"/" + path.getFirst() + "/favicon.ico\" />");
		pw.println("<link rel=\"stylesheet\" href=\"/" + path.getFirst() + "/css/style.css\" type=\"text/css\" />");
		pw.println("<title>OpenKM WebDAV</title>");
		pw.println("</header>");
		pw.println("<body>");
		pw.println("<h1>OpenKM WebDAV</h1>");
		pw.println("<table>");
		
		if (!path.getStripFirst().getStripFirst().isRoot()) {
			String url = path.getParent().toPath();
			pw.print("<tr>");
			pw.print("<td><img src='/" + path.getFirst() + "/img/webdav/folder.png'/></td>");
			pw.print("<td><a href='" + url + "'>..</a></td>");
			pw.println("<tr>");
		}
		
		if (fldChilds != null) {
			for (Folder fld : fldChilds) {
				Path fldPath = Path.path(fld.getPath());
				String url = path.toPath().concat("/").concat(fldPath.getName());
				pw.print("<tr>");
				pw.print("<td><img src='/" + path.getFirst() + "/img/webdav/folder.png'/></td>");
				pw.print("<td><a href='" + url + "'>" + fldPath.getName() + "</a></td>");
				pw.println("<tr>");
			}
		}
		
		if (docChilds != null) {
			for (Document doc : docChilds) {
				Path docPath = Path.path(doc.getPath());
				String url = path.toPath().concat("/").concat(docPath.getName());
				pw.print("<tr>");
				pw.print("<td><img src='/" + path.getFirst() + "/mime/" + doc.getMimeType() + "'/></td>");
				pw.print("<td><a href='" + url + "'>" + docPath.getName() + "</a></td>");
				pw.println("<tr>");
			}
		}
		
		if (mailChilds != null) {
			for (Mail mail : mailChilds) {
				Path mailPath = Path.path(mail.getPath());
				String url = path.toPath().concat("/").concat(mailPath.getName());
				pw.print("<tr>");
				
				if (mail.getAttachments().isEmpty()) {
					pw.print("<td><img src='/" + path.getFirst() + "/img/webdav/email.png'/></td>");
				} else {
					pw.print("<td><img src='/" + path.getFirst() + "/img/webdav/email_attach.png'/></td>");
				}
				
				pw.print("<td><a href='" + url + "'>" + mailPath.getName() + "</a></td>");
				pw.println("<tr>");
			}
		}
		
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
		pw.flush();
		pw.close();
	}
	
	/**
	 * Compile wildcard to regexp
	 */
	public static String wildcard2regexp(String wildcard) {
		StringBuffer sb = new StringBuffer("^");
		
		for (int i = 0; i < wildcard.length(); i++) {
			char c = wildcard.charAt(i);
			
			switch (c) {
				case '.':
					sb.append("\\.");
					break;
				
				case '*':
					sb.append(".*");
					break;
				
				case '?':
					sb.append(".");
					break;
				
				default:
					sb.append(c);
					break;
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Correct webdav folder path
	 */
	public static Folder fixResourcePath(Folder fld) {
		if (Config.SYSTEM_WEBDAV_FIX) {
			fld.setPath(fixResourcePath(fld.getPath()));
		}
		
		return fld;
	}
	
	/**
	 * Correct webdav document path
	 */
	public static Document fixResourcePath(Document doc) {
		if (Config.SYSTEM_WEBDAV_FIX) {
			doc.setPath(fixResourcePath(doc.getPath()));
		}
		
		return doc;
	}
	
	/**
	 * Correct webdav mail path
	 */
	public static Mail fixResourcePath(Mail mail) {
		if (Config.SYSTEM_WEBDAV_FIX) {
			mail.setPath(fixResourcePath(mail.getPath()));
		}
		
		return mail;
	}
	
	/**
	 * 
	 */
	private static String fixResourcePath(String path) {
		return path.replace("okm:", "okm_");
	}
	
	/**
	 * 
	 */
	public static String fixRepositoryPath(String path) {
		if (Config.SYSTEM_WEBDAV_FIX) {
			return path.replace("okm_", "okm:");
		} else {
			return path;
		}
	}
}
