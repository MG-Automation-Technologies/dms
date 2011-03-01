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

import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * JSObject documentation:
 * 
 * http://download.oracle.com/javase/6/docs/technotes/guides/plugin/developer_guide/java_js.html
 * http://www.apl.jhu.edu/~hall/java/JavaScript-from-Java.html
 * http://www.rgagnon.com/javadetails/java-0172.html
 */
@SuppressWarnings("serial")
public class Scanner extends JApplet {
	private static Logger log = Logger.getLogger(Scanner.class.getName());
	private static ScannerManager app;
	private String token;
	private String path;
	private String url;
	private String lang;
	private Locale locale;
	private JSObject win;
	
	public Scanner() {
		super();
		ImageIO.scanForPlugins();
	}

	@Override
	public void init() {
        try {
       		url = getCodeBase().toString();
       		url = url.substring(0, url.length()-1);
       		url = url.substring(0, url.lastIndexOf('/'));
       		token = getParameter("token");
       		path = getParameter("path");
       		lang = getParameter("lang");
       		locale = Util.parseLocaleString(lang);
       		Messages.init(locale);
        	win = JSObject.getWindow(this);        	
        } catch (JSException e) {
        	log.warning("Can't access JSObject object");
        }
        
        log.info("AppVersion: " + JarUtils.getAppVersion());
    	log.info("openkm.token => "+token);
    	log.info("openkm.path => "+path);
    	log.info("openkm.lang => " + lang);
    	log.info("applet.locale => "+ locale);
    	log.info("applet.url => "+url);
    	log.info("applet.jsobject => "+win);

        // Create scanner instance
        app = new ScannerManager(token, path, url, win);
		
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
	private void createGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame main = new MainFrame(app, win);
		main.setVisible(true);
		main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
