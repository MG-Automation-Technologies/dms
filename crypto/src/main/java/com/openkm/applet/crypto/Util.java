/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
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

package com.openkm.applet.crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Util {
	private static Logger log = Logger.getLogger(Util.class.getName());
    
	/**
	 * Upload crypted document to OpenKM
	 * 
	 * @param token
	 * @param path
	 * @param fileName
	 * @param url
	 * @param tmpFile
	 * @param update
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String uploadDocument(String token, String path, String url, File tmpFile, String cipherName,
										boolean update) throws MalformedURLException, IOException {
		log.info("createDocument(" + token + ", " + path + ", " + url + ")");
		String response = "";
		
		HttpClient client = new DefaultHttpClient();
		MultipartEntity form = new MultipartEntity();
		form.addPart("file", new FileBody(tmpFile));
		form.addPart("path", new StringBody(path, Charset.forName("UTF-8")));
		form.addPart("comment", new StringBody(""));		
		if (!update) {
			form.addPart("action", new StringBody("0")); // UIFileUploadConstants.ACTION_INSERT
		} else {
			form.addPart("action", new StringBody("1")); // UIFileUploadConstants.ACTION_UPDATE
		}
		form.addPart("cipherName", new StringBody(cipherName));
		HttpPost post = new HttpPost(url+"/frontend/FileUpload;jsessionid="+token);
		post.setHeader("Cookie", "jsessionid="+token);
		post.setEntity(form);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		response = client.execute(post, responseHandler);
		
		log.info("createDocument: "+response);
		return response;
	}
	
	/**
	 * Download crypted document to local computer
	 * 
	 * @param token
	 * @param path
	 * @param url
	 * @param chekcout
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static File downloadDocument(String token, String path, String url, boolean checkout) throws ClientProtocolException, IOException {
		log.info("downloadDocument(" + token + ", " + path + ", " + url + ")");
		File tmp = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url+"/frontend/Download;jsessionid="+token+"?uuid="+path+(checkout?"&checkout":""));
		get.setHeader("Cookie", "jsessionid="+token);

		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			tmp = File.createTempFile("okm", null);
			FileOutputStream fos = new FileOutputStream(tmp);
			InputStream instream = entity.getContent();
			IOUtils.copy(instream, fos);
			fos.flush();
			fos.close();
		}
		log.info("downloadDocument: tmp file created");
		return tmp;
	}
	
	/**
	 * Creates a temporal and unique directory
	 * 
	 * @throws IOException If something fails.
	 */
	public static File createTempDir() throws IOException {
		File tmpFile = File.createTempFile("okm", null);
		
		if (!tmpFile.delete())
            throw new IOException();
        if (!tmpFile.mkdir())
            throw new IOException();
        
        return tmpFile;       
	}
	
	/**
	 * parseLocaleString
	 */
	public static Locale parseLocaleString(String localeString) {
		if (localeString == null) {
			localeString = "en-GB";
		}
		 
		String[] parts = localeString.split("-");
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		return (language.length() > 0 ? new Locale(language, country) : null);
	 }
}
