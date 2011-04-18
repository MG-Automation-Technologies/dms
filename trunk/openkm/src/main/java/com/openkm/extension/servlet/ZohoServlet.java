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

package com.openkm.extension.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMRepository;
import com.openkm.bean.Document;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMZohoService;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;
import com.openkm.util.FileUtils;

/**
 * ZohoServlet
 */
public class ZohoServlet extends OKMRemoteServiceServlet implements OKMZohoService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ZohoServlet.class);
	
	@Override
	public String getTicket() throws OKMException {
		log.debug("getTicket()");
		updateSessionManager();
		String ticket = "";
		try {
			//Construct data
			String data = URLEncoder.encode("LOGIN_ID", "UTF-8") + "=" + URLEncoder.encode(Config.ZOHO_USER, "UTF-8");
			data += "&" + URLEncoder.encode("PASSWORD", "UTF-8") + "=" + URLEncoder.encode(Config.ZOHO_PASSWORD, "UTF-8");
			data += "&" + URLEncoder.encode("FROM_AGENT", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8");
			data += "&" + URLEncoder.encode("servicename", "UTF-8") + "=" + URLEncoder.encode("ZohoWriter", "UTF-8");
			
			//Send data
			URL url;
			url = new URL("https://accounts.zoho.com/login"); 
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
			    if (line.startsWith("TICKET=")) {
			    	ticket = line.substring(7);
			    }
			}

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_UnsupportedEncoding), e.getMessage());
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_MalformedURL), e.getMessage());
		}catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_IO), e.getMessage());
		}
		
		log.debug("ticket:"+ticket);
		return ticket;
	}

	@Override
	public Map<String,String> getZohoWriterUrl(String uuid, String lang) throws OKMException {
		log.debug("getZohoWriterUrl({},{})",uuid,lang);
		updateSessionManager();
		Map<String,String> result = new HashMap<String,String>();
		File tmp = null;
		try {
			String path = OKMRepository.getInstance().getNodePath(null, uuid);
			String fileName = FileUtils.getName(path);
			tmp = File.createTempFile("okm", ".tmp");
			InputStream is = OKMDocument.getInstance().getContent(null, path, false); // Checkouts
			Document doc = OKMDocument.getInstance().getProperties(null, path);
			FileOutputStream fos = new FileOutputStream(tmp);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			
			String id = "12345678";
			
			Part[] parts = { new FilePart("content", tmp),
				     		 new StringPart("apikey", Config.ZOHO_API_KEY),
				     		 new StringPart("output", "url"),
				     		 new StringPart("mode", "normaledit"),
				     		 new StringPart("filename", fileName),
				     		 new StringPart("skey", Config.ZOHO_SECRET_KEY),
				     		 new StringPart("lang", lang),
				     		 new StringPart("id", id),
				     		 new StringPart("format", getFormat(doc.getMimeType())),
				     		 new StringPart("saveurl", "http://darkman97i.dyndns.org/OpenKM/ZohoFileUpload")
			};
			
			PostMethod filePost = new PostMethod("https://export.writer.zoho.com/remotedoc.im");
		    filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		    filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
		    HttpClient client = new HttpClient();
		    client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);    
		    int status = client.executeMethod(filePost);
		    if (status == HttpStatus.SC_OK) {
		        System.out.println("OK: "+filePost.getResponseBodyAsString());
		        
		     	// Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream()));
		        String line;
		        while ((line = rd.readLine()) != null) {
		            System.out.println(line);
		            if (line.startsWith("URL=")) {
		            	result.put("url",line.substring(4));
		            	result.put("id", id);
		            	break;
		            }
		        }
		        rd.close();
		    } else {
		    	String error = HttpStatus.getStatusText(status) + "\n\n" + filePost.getResponseBodyAsString();
		    	log.error("ERROR: "+error);
		    	throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_Zoho), error);
		    }
			
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_IO), e.getMessage());
		} finally {
			if (tmp!=null) {
				tmp.delete();
			}
		}
		log.debug("result:"+result);
		return result;
	}
	
	@Override
	public Map<String,String> getZohoSheetUrl(String uuid, String lang) throws OKMException {
		log.debug("getZohoSheetUrl({},{})",uuid,lang);
		updateSessionManager();
		Map<String,String> result = new HashMap<String,String>();
		File tmp = null;
		try {
			String path = OKMRepository.getInstance().getNodePath(null, uuid);
			String fileName = FileUtils.getName(path);
			tmp = File.createTempFile("okm", ".tmp");
			InputStream is = OKMDocument.getInstance().getContent(null, path, false); // Checkouts
			Document doc = OKMDocument.getInstance().getProperties(null, path);
			FileOutputStream fos = new FileOutputStream(tmp);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			
			String id = "12345678";
			
			Part[] parts = { new FilePart("content", tmp),
				     		 new StringPart("apikey", Config.ZOHO_API_KEY),
				     		 new StringPart("output", "url"),
				     		 new StringPart("mode", "normaledit"),
				     		 new StringPart("filename", fileName),
				     		 new StringPart("skey", Config.ZOHO_SECRET_KEY),
				     		 new StringPart("lang", lang),
				     		 new StringPart("id", id),
				     		 new StringPart("format", getFormat(doc.getMimeType())),
				     		 new StringPart("saveurl", "http://darkman97i.dyndns.org/OpenKM/ZohoFileUpload")
			};
			
			PostMethod filePost = new PostMethod("https://sheet.zoho.com/remotedoc.im");
		    filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		    filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
		    HttpClient client = new HttpClient();
		    client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);    
		    int status = client.executeMethod(filePost);
		    if (status == HttpStatus.SC_OK) {
		        System.out.println("OK: "+filePost.getResponseBodyAsString());
		        
		     	// Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream()));
		        String line;
		        while ((line = rd.readLine()) != null) {
		            System.out.println(line);
		            if (line.startsWith("URL=")) {
		            	result.put("url",line.substring(4));
		            	result.put("id", id);
		            	break;
		            }
		        }
		        rd.close();
		    } else {
		    	String error = HttpStatus.getStatusText(status) + "\n\n" + filePost.getResponseBodyAsString();
		    	log.error("ERROR: "+error);
		    	throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_Zoho), error);
		    }
			
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMZohoService, ErrorCode.CAUSE_IO), e.getMessage());
		} finally {
			if (tmp!=null) {
				tmp.delete();
			}
		}
		log.debug("result:"+result);
		return result;
	}
	
	@Override
	public void closeZohoWriter(String id) {
		log.debug("closeZohoWriter({})",id);
		
		log.debug("closeZohoWriter: void");
	}
	
	/**
	 * getFormat
	 * 
	 * @param mimeType
	 * @return
	 */
	private String getFormat(String mimeType) {
		String format = "";
		
		if (mimeType.equals("application/msword")) {
			format = "doc";
		} else if (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.documentapplication/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			format = "docx";
		} else if (mimeType.equals("text/html")) {
			format = "html";
		} else if (mimeType.equals("application/pdf")){
			format = "pdf";
		} else if (mimeType.equals("application/vnd.oasis.opendocument.text")) {
			format = "odt";
		} else if (mimeType.equals("application/rtf")) {
			format = "rtf";
		} else if(mimeType.equals("text/plain")) {
			format = "txt";
		}
		
		return format;
	}
		
}