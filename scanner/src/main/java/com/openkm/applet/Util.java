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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
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
	 * Upload scanned document to OpenKM
	 * 
	 */
	public static String createDocument(String token, String path, String fileName, String fileType,
			String url, List<BufferedImage> images) throws MalformedURLException, IOException {
		log.info("createDocument(" + token + ", " + path + ", " + fileName + ", " + fileType +
				", " + url + ", " + images + ")");
		File tmpDir = createTempDir();
		File tmpFile = new File(tmpDir, fileName + "." + fileType);
		ImageOutputStream ios = ImageIO.createImageOutputStream(tmpFile);
		String response = "";
		
		try {
			if ("pdf".equals(fileType)) {
				ImageUtils.writePdf(images, ios);
			} else if ("tif".equals(fileType)) {
				ImageUtils.writeTiff(images, ios);
			} else {
				if (!ImageIO.write(images.get(0), fileType, ios)) {
					throw new IOException("Not appropiated writer found!");
				}
			}
			
			ios.flush();
			ios.close();
			
			if (token != null) {
				// Send image
				HttpClient client = new DefaultHttpClient();
				MultipartEntity form = new MultipartEntity();
				form.addPart("file", new FileBody(tmpFile));
				form.addPart("path", new StringBody(path, Charset.forName("UTF-8")));
				form.addPart("action", new StringBody("0")); // FancyFileUpload.ACTION_INSERT
				HttpPost post = new HttpPost(url + "/frontend/FileUpload;jsessionid=" + token);
				post.setHeader("Cookie", "jsessionid=" + token);
				post.setEntity(form);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				response = client.execute(post, responseHandler);
			} else {
				// Store in disk
				String home = System.getProperty("user.home");
				File dst = new File(home, tmpFile.getName());
				copyFile(tmpFile, dst);
				response = "Image copied to " + dst.getPath(); 
			}
		} finally {
			FileUtils.deleteQuietly(tmpDir);
		}
		
		log.info("createDocument: "+response);
		return response;
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
	
	/**
	 * Copy file
	 */
	private static void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream from = null;
	    FileOutputStream to = null;
	    
	    try {
	    	from = new FileInputStream(fromFile);
	    	to = new FileOutputStream(toFile);
	    	byte[] buffer = new byte[4096];
	    	int bytesRead;
	    	
	    	while ((bytesRead = from.read(buffer)) != -1) {
	            to.write(buffer, 0, bytesRead);
	    	}
	    } finally {
	    	if (from != null) {
	    		try {
	    			from.close();
	    		} catch (IOException e) {}
	    	}
	    	
	    	if (to != null) {
	    		try {
	    			to.close();
	    		} catch (IOException e) {}
	    	}
	    }
	}
}
