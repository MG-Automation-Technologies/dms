package com.openkm.editor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author jllort
 * 
 */
public class Editor extends JApplet {

	// README before executing
	// Now needs to run the command in OS console -> soffice -bean "-accept=socket,host=0,port=2002;urp;"
	// Not closing the open office window after running

	// PROBLEMS applet class loader !!!
	// added in .bashrc
	// "export LD_LIBRARY_PATH=/usr/lib/openoffice/basis3.1/program" but not
	// changes java.library.path
	// mapUrlToFile(URL url) in UrlToFileManager problem to locate libraries !!!
	// added libraries directly to project path; libofficebean.so and
	// libjpipe.so
	// -Djava.library.path=/usr/lib/openoffice/basis3.1/program in eclipse
	// environment has no effects in java.library.path

	private static Logger log = Logger.getLogger(Editor.class.getName());
	private static final long serialVersionUID = -7163587104950744341L;
			
	Timer timer;
	TimerTask task;

	@Override
	public void init() {
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
	
	private void createGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame main = new MainFrame(this);
		main.setVisible(true);
		main.setResizable(false);
	}

	@Override
	public void destroy() {
		//aBean.stopOOoConnection();
		stop();
		System.exit(0);
	}

	@Override
	public void stop() {
	}
	
	@Override
	public void start() {
		// Timer to not losing OpenKM session if browser is closed by user, must call http keepalive servlet methow
		timer = new Timer();
		task = new TimerTask() { 
			public void run() { 
				/* do stuff in here*/ 
			} 
		};
		timer.schedule(task , 0, 50000);
	}
}