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

import java.awt.geom.RoundRectangle2D;
import java.util.Locale;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

@SuppressWarnings("serial")
public class Uploader extends JApplet {
	private static Logger log = Logger.getLogger(Uploader.class.getName());
	private String token;
	private String path;
	private String url;
	private String lang;
	private Locale locale;
	private JSObject win;

	@Override
	public void init() {
		try {
			url = getCodeBase().toString();
			url = url.substring(0, url.length() - 1);
			url = url.substring(0, url.lastIndexOf('/'));
			token = getParameter("token");
			path = getParameter("path");
			lang = getParameter("lang");
			locale = Util.parseLocaleString(lang);
			Messages.init(locale);
			win = (JSObject) JSObject.getWindow(this);
		} catch (JSException e) {
			log.warning("Can't access JSObject object");
		}

		log.info("openkm.token => " + token);
		log.info("openkm.path => " + path);
		log.info("openkm.lang => " + lang);
		log.info("applet.locale => "+ locale);
		log.info("applet.url => " + url);
		log.info("applet.jsobject => " + win);

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createGUI();
				}
			});
		} catch (Exception e) {
			log.severe("createGUI didn't successfully complete");
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("restriction")
	private void createGUI() {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame inst = new MainFrame(token, path, url, win);
		inst.setUndecorated(true);
		inst.setResizable(false);
		inst.setVisible(true);
		inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		com.sun.awt.AWTUtilities.setWindowOpacity(inst, 0.50f);
		com.sun.awt.AWTUtilities.setWindowOpaque(inst, true);
		com.sun.awt.AWTUtilities.setWindowShape(inst,
				new RoundRectangle2D.Double(0, 0, inst.getWidth(), inst.getHeight(), 25, 25));
	}
}
