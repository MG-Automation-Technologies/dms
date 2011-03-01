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

import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

/**
 * JSObject documentation:
 * 
 * http://download.oracle.com/javase/6/docs/technotes/guides/plugin/developer_guide/java_js.html
 * http://www.apl.jhu.edu/~hall/java/JavaScript-from-Java.html
 * http://www.rgagnon.com/javadetails/java-0172.html
 */
public class Crypto extends JApplet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Crypto.class.getName());
	private String token;
	private String path;
	private String url;
	private String action;
	private String uuid;
	private String cipherName;
	private JSObject win;
	private CryptoManager cryptoManager;
	
	/**
	 * Crypto
	 */
	public Crypto() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
        try {
       		url = getCodeBase().toString();
       		url = url.substring(0, url.length()-1);
       		url = url.substring(0, url.lastIndexOf('/'));
       		token = getParameter("token");
       		path = getParameter("path");
       		action = getParameter("action");
       		uuid = getParameter("uuid");
       		cipherName = getParameter("cipherName");
        	win = (JSObject) JSObject.getWindow(this);
        	
        	log.info("openkm.token => "+token);
        	log.info("openkm.path => "+path);
        	log.info("applet.url => "+url);
        	log.info("applet.action => "+action);
        	log.info("applet.uuid => "+uuid);
        	log.info("applet.cipherName => "+cipherName);
        	log.info("applet.jsobject => "+win);
        } catch (Exception e) {
        	log.warning("Can't access JSObject object");
        }
		
        // Create the cryto manager instance
        cryptoManager = new CryptoManager(token, path, url, action, uuid, cipherName, win);
        
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
	 * createGUI
	 */
	private void createGUI() {
		if (action==null || action.equals("insert")) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame main = new UploadMainFrame(cryptoManager, win);
			main.setVisible(true);
			main.setResizable(false);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			cryptoManager.cryptographyLoaded(main);
			
		} else if (action.equals("download")) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame main = new DownloadMainFrame(cryptoManager, win);
			main.setVisible(true);
			main.setResizable(false);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			cryptoManager.cryptographyLoaded(main);
			
		} else if (action.equals("updatecrypt")) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame main = new CryptExistingMainFrame(cryptoManager, win);
			main.setVisible(true);
			main.setResizable(false);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			cryptoManager.cryptographyLoaded(main);
			
		} else if (action.equals("updatedecrypt")) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame main = new DecryptExistingMainFrame(cryptoManager, win);
			main.setVisible(true);
			main.setResizable(false);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			cryptoManager.cryptographyLoaded(main);
			
		} else {
			// Case checkin
			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame main = new CheckinMainFrame(cryptoManager, win);
			main.setVisible(true);
			main.setResizable(false);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			cryptoManager.cryptographyLoaded(main);
		}
	}
}
