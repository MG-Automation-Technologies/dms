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

package com.openkm.applet;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Util {
	private static Logger log = Logger.getLogger(Util.class.getName());
	
	/**
	 * 
	 */
	public static String createDocument(String token, String path, String url, File file) throws IOException {
		log.info("createDocument(" + token + ", " + path + ", " + url + ", " + file + ")");
		HttpClient client = new DefaultHttpClient();
		MultipartEntity form = new MultipartEntity();
		form.addPart("file", new FileBody(file));
		form.addPart("path", new StringBody(path));
		form.addPart("action", new StringBody("0")); // FancyFileUpload.ACTION_INSERT
		HttpPost post = new HttpPost(url+"/OKMFileUploadServlet;jsessionid="+token);
		post.setHeader("Cookie", "jsessionid="+token);
		post.setEntity(form);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = client.execute(post, responseHandler);
		log.info("createDocument: "+response);
		return response;
	}

	/**
	 * 
	 */
	public static String createFolder(String token, String path, String url, File file) throws IOException {
		log.info("createFolder(" + token + ", " + path + ", " + url + ", " + file + ")");
		HttpClient client = new DefaultHttpClient();
		MultipartEntity form = new MultipartEntity();
		form.addPart("folder", new StringBody(file.getName()));
		form.addPart("path", new StringBody(path));
		form.addPart("action", new StringBody("2")); // FancyFileUpload.ACTION_FOLDER
		HttpPost post = new HttpPost(url+"/OKMFileUploadServlet;jsessionid="+token);
		post.setHeader("Cookie", "jsessionid="+token);
		post.setEntity(form);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = client.execute(post, responseHandler);
		log.info("createFolder: "+response);
		return response;
	}

	/**
	 * 
	 */
	public static List<File> textURIListToFileList(String data) {
		List<File> list = new ArrayList<File>(1);

		for (StringTokenizer st = new StringTokenizer(data, "\r\n"); st.hasMoreTokens();) {
			String s = st.nextToken();

			if (s.startsWith("#")) {
				// the line is a comment (as per the RFC 2483)
				continue;
			}

			try {
				URI uri = new URI(s);
				File file = new File(uri);
				list.add(file);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	/**
	 * 
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
