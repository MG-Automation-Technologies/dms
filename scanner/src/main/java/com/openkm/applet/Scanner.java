package com.openkm.applet;

import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

public class Scanner extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Scanner.class.getName());
	private static ScannerManager app;
	private String token;
	private String path;
	private String url;
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
        	win = JSObject.getWindow(this);
        	
        	log.info("openkm.token => "+token);
        	log.info("openkm.path => "+path);
        	log.info("applet.url => "+url);
        	log.info("applet.jsobject => "+win);
        } catch (Exception e) {
        	log.warning("Can't access JSObject object");
        }
        
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
	}
}
