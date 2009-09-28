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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Repository;
import es.git.openkm.core.Config;

public class Update {
	private static Logger log = LoggerFactory.getLogger(Update.class);
	
	public static String query(String uuid) {
		log.debug("query("+uuid+")");
		StringBuffer sb = new StringBuffer();
		
		try {
			URL url = new URL("http://update.openkm.com");
			HttpURLConnection urlConn =(HttpURLConnection)url.openConnection();
			String content = "okm_uuid="+URLEncoder.encode(uuid, "UTF-8")+
				"&okm_version="+URLEncoder.encode(Repository.VERSION, "UTF-8")+
				"&os_name="+URLEncoder.encode(System.getProperty("os.name"), "UTF-8")+
				"&os_version="+URLEncoder.encode(System.getProperty("os.version"), "UTF-8")+
				"&java_vendor="+URLEncoder.encode(System.getProperty("java.vm.vendor"), "UTF-8")+
				"&java_version="+URLEncoder.encode(System.getProperty("java.version"), "UTF-8");

			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Content-Length", content.length()+ "" );

			// Send POST output.
			DataOutputStream printout = new DataOutputStream( urlConn.getOutputStream() );
			printout.writeBytes(content);
			printout.flush();
			printout.close();

			// Get response data.
			BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String line;
			
			while ((line = input.readLine()) != null) {
				sb.append(line);
	        }

			input.close();
						
			// Trial management
			log.debug(sb.toString());
			int idx = sb.indexOf("·");
						
			if (idx > 0) {
				long endTrial = Long.valueOf(sb.substring(0, idx));
				sb.delete(0, idx+1);
				
				if (Config.TRIAL) {
					if (Calendar.getInstance().getTimeInMillis() > endTrial) {
						sb.append(expired());
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException: "+e.getMessage());
			if (Config.TRIAL) sb.append(expired());
		} catch (MalformedURLException e) {
			log.error("MalformedURLException: "+e.getMessage());
			if (Config.TRIAL) sb.append(expired());
		} catch (UnknownHostException e) {
			log.error("UnknownHostException: "+e.getMessage());
			if (Config.TRIAL) sb.append(expired());
		} catch (IOException e) {
			log.error("IOException: "+e.getMessage());
			if (Config.TRIAL) sb.append(expired());
		}
				
		log.debug("query: "+sb.toString());
		return sb.toString();
	}

	/**
	 * 
	 */
	public static String expired() {
		Config.MAX_FILE_SIZE = 0;
		log.info("*** *** ***** *** ***");
		log.info("*** TRIAL EXPIRED ***");
		log.info("*** *** ***** *** ***");
		return "*** TRIAL EXPIRED ***";
	}
}
