package com.openkm.editor;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextField;

import com.sun.star.comp.beans.LocalOfficeConnection2;
import com.sun.star.comp.beans.NoConnectionException;
import com.sun.star.comp.beans.OOoBean2;
import com.sun.star.comp.beans.OfficeConnection;
import com.sun.star.comp.beans.SystemWindowException;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.util.CloseVetoException;

/**
 * @author jllort
 * 
 */
@SuppressWarnings("deprecation")
public class Editor extends Applet {

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

	private static final long serialVersionUID = -7163587104950744341L;
	private static final String OFFICE_DOCUMENT = "file:///home/jllort/Escritorio/lucene.odt";
	public static final String URI = "uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager";
	private OOoBean2 aBean;

	/**
	 * Private variables declaration - GUI components
	 */
	private java.awt.Panel rightPanel;
	private java.awt.Panel bottomPanel;
	private javax.swing.JButton closeButton;
	private javax.swing.JCheckBox menuBarButton;
	private javax.swing.JCheckBox mainBarButton;
	private javax.swing.JCheckBox toolBarButton;
	private javax.swing.JCheckBox statusBarButton;
	private javax.swing.JButton storeDocumentButton;
	private javax.swing.JButton loadDocumentButton;
	private javax.swing.JButton syswinButton;
	private JTextField documentURLTextField;
	private byte buffer[];
	
	Timer timer;
	TimerTask task;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		// Initialize GUI components
		rightPanel = new java.awt.Panel();
		bottomPanel = new java.awt.Panel();
		closeButton = new javax.swing.JButton("close");
		storeDocumentButton = new javax.swing.JButton("store to buffer");
		loadDocumentButton = new javax.swing.JButton("load from buffer");
		syswinButton = new javax.swing.JButton("release/aquire");
		menuBarButton = new javax.swing.JCheckBox("MenuBar");
		mainBarButton = new javax.swing.JCheckBox("MainBar");
		toolBarButton = new javax.swing.JCheckBox("ToolBar");
		statusBarButton = new javax.swing.JCheckBox("StatusBar");
		documentURLTextField = new javax.swing.JTextField();

		syswinButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					aBean.releaseSystemWindow();
					aBean.aquireSystemWindow();
				} catch (com.sun.star.comp.beans.NoConnectionException aExc) {
					aExc.printStackTrace();
				} catch (com.sun.star.comp.beans.SystemWindowException aExc) {
					aExc.printStackTrace();
				}
			}
		});

		storeDocumentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					buffer = aBean.storeToByteArray(null, null);
				} catch (Throwable aExc) {
					System.err.println("storeToBuffer failed: " + aExc);
					aExc.printStackTrace(System.err);
				}
			}
		});

		loadDocumentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					aBean.loadFromByteArray(buffer, null);
				} catch (Throwable aExc) {
					System.err.println("loadFromBuffer failed: " + aExc);
					aExc.printStackTrace(System.err);
				}
			}
		});

		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				close();
			}
		});

		menuBarButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aBean.setMenuBarVisible(!aBean.isMenuBarVisible());
			}
		});

		mainBarButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aBean.setStandardBarVisible(!aBean.isStandardBarVisible());
			}
		});

		toolBarButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aBean.setToolBarVisible(!aBean.isToolBarVisible());
			}
		});

		statusBarButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aBean.setStatusBarVisible(!aBean.isStatusBarVisible());
			}
		});

		documentURLTextField.setEditable(false);
		documentURLTextField.setPreferredSize(new java.awt.Dimension(200, 30));

		rightPanel.setLayout(new java.awt.GridLayout(10, 1));
		rightPanel.add(closeButton);
		rightPanel.add(storeDocumentButton);
		rightPanel.add(loadDocumentButton);
		rightPanel.add(syswinButton);
		rightPanel.add(menuBarButton);
		rightPanel.add(mainBarButton);
		rightPanel.add(toolBarButton);
		rightPanel.add(statusBarButton);

		System.out.println("alegria 12");
		
		// Trying to solve keyboard problem or in jvm parameters -Dsun.awt.xembedserver=true
		System.setProperty("sun.awt.xembedserver", "true");

		// /usr/lib/openoffice/basis3.1/program here's libofficebean.so
		// /usr/lib/ure/lib/ here's libjpipe.so
		System.setProperty("java.library.path","/usr/lib/openoffice/basis3.1/program:/usr/lib/ure/lib/");

		System.out.println("java.library.path:"+ System.getProperty("java.library.path"));

		System.out.println("alegria 22");

		OfficeConnection officeConnection = new LocalOfficeConnection2();

		System.out.println("alegria 23");
		try {
			officeConnection.setUnoUrl(URI);
			aBean = new OOoBean2(officeConnection);
			
			System.out.println("connection test: " + aBean.isOOoConnected());
			
			// setting some UI preferences
		    //aBean.setEnabled(true);
			//aBean.setFocusable(true);

			// Initializing buttons
			menuBarButton.setSelected(aBean.isMenuBarVisible());
			mainBarButton.setSelected(aBean.isStandardBarVisible());
			toolBarButton.setSelected(aBean.isToolBarVisible());
			statusBarButton.setSelected(aBean.isStatusBarVisible());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}
		
		bottomPanel.setLayout(new BorderLayout());
	    bottomPanel.add(documentURLTextField);

		setLayout(new BorderLayout());
		add(aBean, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	    add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * createBlankDoc
	 * 
	 * @param url
	 * @param desc
	 */
	private void createBlankDoc(String url, String desc) {
		try {
			documentURLTextField.setText(desc);
			aBean.loadFromURL(url, null);
			aBean.aquireSystemWindow();
		} catch (SystemWindowException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (CloseVetoException e) {
			e.printStackTrace();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * close
	 */
	private void close() {
		setVisible(false);
		aBean.stopOOoConnection(); 
		stop();
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#destroy()
	 */
	public void destroy() {
		aBean.stopOOoConnection();
		stop();
		System.exit(0);
	}

	/**
	 * terminate
	 */
	private void terminate() {
		setVisible(false);
		XDesktop xDesktop = null;

		try {
			xDesktop = aBean.getOOoDesktop();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}

		aBean.stopOOoConnection();
		stop();

		if (xDesktop != null) {
			xDesktop.terminate();
		}

		System.exit(0);
	}

	// This method gets called when the applet is terminated
	// That's when the user goes to another page or exits the browser.
	public void stop() {
		// no actions needed here now.
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	public void start() {
		// Timer to not losing OpenKM session if browser is closed by user, must call http keepalive servlet methow
		timer = new Timer();
		task= new TimerTask() { 
			public void run() { 
				/* do stuff in here*/ 
			} 
		};
		timer.schedule(task , 0, 50000);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		createBlankDoc(OFFICE_DOCUMENT, "document description text !!!");
	}
}