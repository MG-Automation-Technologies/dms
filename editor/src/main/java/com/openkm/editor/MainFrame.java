package com.openkm.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.beans.LocalOfficeConnection2;
import com.sun.star.comp.beans.NoConnectionException;
import com.sun.star.comp.beans.OOoBean2;
import com.sun.star.comp.beans.OfficeConnection;
import com.sun.star.comp.beans.SystemWindowException;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.util.CloseVetoException;

public class MainFrame extends JFrame implements ActionListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MainFrame.class.getName());
	private static final String OFFICE_DOCUMENT = "file:///home/pavila/lucene.odt";
	private static final String URI = "uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager";

	/**
	 * Private variables declaration - GUI components
	 */
	private JPanel rightPanel;
	private JPanel bottomPanel;
	private JButton closeButton;
	private JCheckBox menuBarButton;
	private JCheckBox mainBarButton;
	private JCheckBox toolBarButton;
	private JCheckBox statusBarButton;
	private JButton storeDocumentButton;
	private JButton loadDocumentButton;
	private JButton syswinButton;
	private JTextField documentURLTextField;
	
	private byte buffer[];
	private OOoBean2 aBean;
	
	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame(null);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	/**
	 * 
	 */
	public MainFrame(JApplet applet) {
		super("Scan & Upload");
		initGUI();
		addWindowListener(this);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		this.setLocation(x, y);
	}

	/**
	 * 
	 */
	private void initGUI() {
		try {
			// Initialize GUI components
			rightPanel = new JPanel();
			bottomPanel = new JPanel();
			closeButton = new JButton("close");
			storeDocumentButton = new JButton("store to buffer");
			loadDocumentButton = new JButton("load from buffer");
			syswinButton = new JButton("release/aquire");
			menuBarButton = new JCheckBox("MenuBar");
			mainBarButton = new JCheckBox("MainBar");
			toolBarButton = new JCheckBox("ToolBar");
			statusBarButton = new JCheckBox("StatusBar");
			documentURLTextField = new JTextField();

			syswinButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
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

			storeDocumentButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						buffer = aBean.storeToByteArray(null, null);
					} catch (Throwable aExc) {
						System.err.println("storeToBuffer failed: " + aExc);
						aExc.printStackTrace(System.err);
					}
				}
			});

			loadDocumentButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						aBean.loadFromByteArray(buffer, null);
					} catch (Throwable aExc) {
						System.err.println("loadFromBuffer failed: " + aExc);
						aExc.printStackTrace(System.err);
					}
				}
			});

			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					close();
				}
			});

			menuBarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aBean.setMenuBarVisible(!aBean.isMenuBarVisible());
				}
			});

			mainBarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aBean.setStandardBarVisible(!aBean.isStandardBarVisible());
				}
			});

			toolBarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aBean.setToolBarVisible(!aBean.isToolBarVisible());
				}
			});

			statusBarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aBean.setStatusBarVisible(!aBean.isStatusBarVisible());
				}
			});

			documentURLTextField.setEditable(false);
			documentURLTextField.setPreferredSize(new Dimension(200, 30));

			rightPanel.setLayout(new GridLayout(10, 1));
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
			// http://qa.openoffice.org/issues/show_bug.cgi?id=82964
			// http://wiki.services.openoffice.org/wiki/OOoBean
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
			    aBean.setEnabled(true);
				aBean.setFocusable(true);

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
		    
		    this.setSize(750, 750);
		    pack();
		    this.setSize(750, 750);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		try {
			JOptionPane.showMessageDialog(this, "Empty file name", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowActivated(WindowEvent we) {
	}

	@Override
	public void windowClosed(WindowEvent we) {
		close();
	}

	@Override
	public void windowClosing(WindowEvent we) {
	}

	@Override
	public void windowDeactivated(WindowEvent we) {
	}

	@Override
	public void windowDeiconified(WindowEvent we) {
	}

	@Override
	public void windowIconified(WindowEvent we) {
	}

	@Override
	public void windowOpened(WindowEvent we) {
	}
	
	@Override
	public void paint(Graphics g) {
		createBlankDoc(OFFICE_DOCUMENT, "document description text !!!");
	}
	
	/**
	 * 
	 */
	private void close() {
		setVisible(false);
		aBean.stopOOoConnection(); 
		System.exit(0);
	}
	
	/**
	 *
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

		if (xDesktop != null) {
			xDesktop.terminate();
		}

		System.exit(0);
	}
	
	/**
	 * 
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
}
