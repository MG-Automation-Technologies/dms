package com.openkm.applet;

import java.awt.geom.RoundRectangle2D;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

public class Uploader extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Uploader.class.getName());
	private String token;
	private String path;
	private String url;
	private JSObject win;

	@Override
	public void init() {
		try {
			url = getCodeBase().toString();
			url = url.substring(0, url.length() - 1);
			url = url.substring(0, url.lastIndexOf('/'));
			token = getParameter("token");
			path = getParameter("path");
			win = (JSObject) JSObject.getWindow(this);

			log.info("openkm.token => " + token);
			log.info("openkm.path => " + path);
			log.info("applet.url => " + url);
			log.info("applet.jsobject => " + win);
		} catch (Exception e) {
			log.warning("Can't access JSObject object");
		}

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
