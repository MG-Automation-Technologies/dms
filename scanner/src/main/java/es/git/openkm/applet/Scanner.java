package es.git.openkm.applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

public class Scanner extends JApplet implements ActionListener {
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
        	win = (JSObject) JSObject.getWindow(this);
        	
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
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#destroy()
	 */
	//public void destroy() {
		//app = null;
		//token = null;
		//url = null;
		//win = null;
	//}
	
	/**
	 * 
	 */
	private void createGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		URL img = getClass().getResource("image_add.png");
		JButton button = new JButton(new ImageIcon(img));
		button.setToolTipText("Scan document");
        add(button);
        button.addActionListener(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		JFrame main = new MainFrame(app, this);
		main.setVisible(true);
		main.setResizable(false);
	}
	
	/**
	 * Set current repository path
	 */
	public void setPath(String path) {
		log.info("setPath -> "+path);
		app.setPath(path);
	}
}
