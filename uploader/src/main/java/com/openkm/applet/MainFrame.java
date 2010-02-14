package com.openkm.applet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.FileSizeExceededException_Exception;
import com.openkm.ws.client.IOException_Exception;
import com.openkm.ws.client.ItemExistsException_Exception;
import com.openkm.ws.client.PathNotFoundException_Exception;
import com.openkm.ws.client.RepositoryException_Exception;
import com.openkm.ws.client.UnsupportedMimeTypeException_Exception;
import com.openkm.ws.client.VirusDetectedException_Exception;

public class MainFrame extends JFrame implements ActionListener, WindowListener, DropTargetListener {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MainFrame.class.getName());
	private String token;
	private String path;
	private String url;
	private JSObject win;
	private BufferedImage logo;
	
	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("restriction")
			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);
				MainFrame inst = new MainFrame(null, null, null, null);
				inst.setUndecorated(true);
				inst.setResizable(false);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				com.sun.awt.AWTUtilities.setWindowOpacity(inst, 0.50f);
				com.sun.awt.AWTUtilities.setWindowOpaque(inst, true);
				com.sun.awt.AWTUtilities.setWindowShape(inst,
						new RoundRectangle2D.Double(0, 0, inst.getWidth(), inst.getHeight(), 25, 25));
			}
		});
	}

	/**
	 * 
	 */
	public MainFrame(String token, String path, String url, JSObject win) {
		super("Uploader");
		try {
			logo = ImageIO.read(MainFrame.class.getResource("openkm.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initGUI();
		addWindowListener(this);

		// Set instances
		this.token = token;
		this.path = path;
		this.url = url;
		this.win = win;

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		setLocation(x, y);
	}
	
	/**
	 * 
	 */
	private void initGUI() {
		try {
			new DropTarget(getContentPane(), this);
			setSize(157, 43);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		// code from
		// http://weblogs.java.net/blog/campbell/archive/2006/07/java_2d_tricker.html
		int width = logo.getWidth();
		int height = logo.getHeight();
		GraphicsConfiguration gc = g2d.getDeviceConfiguration();
		BufferedImage img = gc.createCompatibleImage(width,	height,	Transparency.TRANSLUCENT);
		Graphics2D g2 = img.createGraphics();

		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, width, height);

		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(0, 0, width, height + 10, 10, 10);

		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(logo, 0, 0, null);
		g2.dispose();

		// at this point the 'img' contains a soft
		// clipped round rectangle with the avatar
		g2d.drawImage(img, 0, 0, this);
		g2d.dispose();
	}
	
	@Override
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
		log.info("windowClosed: calling 'destroyUploaderApplet'");
		win.call("destroyUploaderApplet", new Object[] {});
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

	@SuppressWarnings("restriction")
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		com.sun.awt.AWTUtilities.setWindowOpacity(this, 1.00f);
	}

	@SuppressWarnings("restriction")
	@Override
	public void dragExit(DropTargetEvent dtde) {
		com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.50f);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			Transferable tr = dtde.getTransferable();

			// Flavors to check
			DataFlavor linux = new DataFlavor("text/uri-list;class=java.lang.String");
			DataFlavor windows = DataFlavor.javaFileListFlavor;

			if (tr.isDataFlavorSupported(linux)) {
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
				getContentPane().setBackground(Color.RED);

				String data = (String) tr.getTransferData(linux);
				List<File> files = Util.textURIListToFileList(data);
				for (File file : files) {
					callCreateDocument(token, path, url, file);
				}

				dtde.dropComplete(true);
				getContentPane().setBackground(Color.LIGHT_GRAY);
				
				// Refresh file list
				log.fine("--- refresh - begin ---");
				win.call("refresh", new Object[] {});
				log.fine("--- refresh - end ---");
			} else if (tr.isDataFlavorSupported(windows)) {
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
				getContentPane().setBackground(Color.RED);

				@SuppressWarnings("unchecked")
				List<File> files = (List<File>) tr.getTransferData(windows);
				for (File file : files) {
					callCreateDocument(token, path, url, file);
				}

				dtde.dropComplete(true);
				getContentPane().setBackground(Color.LIGHT_GRAY);
				
				// Refresh file list
				log.fine("--- refresh - begin ---");
				win.call("refresh", new Object[] {});
				log.fine("--- refresh - end ---");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void callCreateDocument(String toke, String path, String url, File file) {
		try {
			if (file.isFile()) {
				Util.createDocument(token, path, url, file);
			} else if (file.isDirectory()) {
				Util.createFolder(token, path, url, file);
				createDocumentHelper(token, path+"/"+file.getName(), url, file);
			}
		} catch (VirusDetectedException_Exception e) {
			log.log(Level.SEVERE, "VirusDetectedException: - > " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (FileSizeExceededException_Exception e) {
			log.log(Level.SEVERE, "FileSizeExceededException: - > " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedMimeTypeException_Exception e) {
			log.log(Level.SEVERE, "UnsupportedMimeTypeException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ItemExistsException_Exception e) {
			log.log(Level.SEVERE, "ItemExistsException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (PathNotFoundException_Exception e) {
			log.log(Level.SEVERE, "PathNotFoundException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AccessDeniedException_Exception e) {
			log.log(Level.SEVERE, "AccessDeniedException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (RepositoryException_Exception e) {
			log.log(Level.SEVERE, "RepositoryException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException_Exception e) {
			log.log(Level.SEVERE, "IOException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			log.log(Level.SEVERE, "IOException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** 
	 * 
	 */
	private static void createDocumentHelper(String token, String path, String url, File fs)
			throws IOException, AccessDeniedException_Exception, PathNotFoundException_Exception,
			RepositoryException_Exception, IOException_Exception {
		log.info("uploadDocumentHelper(" + token + ", " + path + ", " + url + ", " + fs + ")");
		File[] files = fs.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				try {
					Util.createFolder(token, path, url, files[i]);
					createDocumentHelper(token, path+"/"+files[i].getName(), url, files[i]);
				} catch (ItemExistsException_Exception e) {
					log.warning("ItemExistsException: " + e.getMessage());
				}
			} else {
				try {
					Util.createDocument(token, path, url, files[i]);
				} catch (UnsupportedMimeTypeException_Exception e) {
					log.warning("UnsupportedMimeTypeException: " + e.getMessage());
				} catch (FileSizeExceededException_Exception e) {
					log.warning("FileSizeExceededException: " + e.getMessage());
				} catch (VirusDetectedException_Exception e) {
					log.warning("VirusWarningException: " + e.getMessage());
				} catch (ItemExistsException_Exception e) {
					log.warning("ItemExistsException: " + e.getMessage());
				}
			}
		}

		log.info("importDocumentsHelper: void");
	}
}
