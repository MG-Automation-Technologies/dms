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

package com.openkm.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

/**
 * Image Logo Servlet
 */
public class ImageLogoServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(ImageLogoServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String img = request.getPathInfo();
		OutputStream os = response.getOutputStream();
		
		try {
			if (img != null && img.length() > 1) {
				URL logo = getImage(img.substring(1));
				
				if (logo != null) {
					if (logo.toString().endsWith(".gif")) {
						response.setContentType("image/gif");
					} else if (logo.toString().endsWith(".jpg")) {
						response.setContentType("image/jpg");
					} else if (logo.toString().endsWith(".png")) {
						response.setContentType("image/png");
					}
					
					InputStream is = logo.openStream();
					IOUtils.copy(is, os);
					os.flush();		
				} else {
					sendError(response, os);
				}
			}
		} catch (MalformedURLException e) {
			sendError(response, os);
			log.warn(e.getMessage(), e);
		} catch (IOException e) {
			sendError(response, os);
			log.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * Send error image
	 */
	private void sendError(HttpServletResponse response, OutputStream os) throws IOException {
		response.setContentType("image/png");
		InputStream is = getServletContext().getResource("/img/error.png").openStream();
		IOUtils.copy(is, os);
		os.flush();
	}

	/**
	 * Get requested image input stream.
	 */
	private URL getImage(String img) throws MalformedURLException, IOException {
		log.debug("getImage({})", img);
		URL urlLogo = null;
		
		if ("login".equals(img)) {
			File logo = new File(Config.HOME_DIR + File.separator + Config.LOGO_LOGIN);
			
			if (logo.exists()) {
				urlLogo = logo.toURI().toURL();
			} else {
				urlLogo = new URL(Config.LOGO_LOGIN);
			}
		} else if ("mobi".equals(img)) {
			File logo = new File(Config.HOME_DIR + File.separator + Config.LOGO_MOBI);
			
			if (logo.exists()) {
				urlLogo = logo.toURI().toURL();
			} else {
				urlLogo = new URL(Config.LOGO_MOBI);
			}
		} else if ("report".equals(img)) {
			File logo = new File(Config.HOME_DIR + File.separator + Config.LOGO_REPORT);
			
			if (logo.exists()) {
				urlLogo = logo.toURI().toURL();
			} else {
				urlLogo = new URL(Config.LOGO_REPORT);
			}
		}
		
		log.debug("getImage: {}", urlLogo);
		return urlLogo;
	}
}
