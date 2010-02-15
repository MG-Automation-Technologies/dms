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
import java.awt.Window;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.xml.ws.WebServiceException;

import netscape.javascript.JSObject;

import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.FileSizeExceededException_Exception;
import com.openkm.ws.client.IOException_Exception;
import com.openkm.ws.client.ItemExistsException_Exception;
import com.openkm.ws.client.PathNotFoundException_Exception;
import com.openkm.ws.client.RepositoryException_Exception;
import com.openkm.ws.client.UnsupportedMimeTypeException_Exception;
import com.openkm.ws.client.VirusDetectedException_Exception;

public class MainFrame extends JFrame implements DropTargetListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MainFrame.class.getName());
	private String token;
	private String path;
	private String url;
	private JSObject win;
	private BufferedImage logo;
	private JPopupMenu popupMenu;
	private JMenuItem menuItem;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("restriction")
			public void run() {
				// JFrame.setDefaultLookAndFeelDecorated(true);
				MainFrame inst = new MainFrame("000", "/okm:root", null, null);
				inst.setUndecorated(true);
				inst.setResizable(false);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				com.sun.awt.AWTUtilities.setWindowOpacity(inst, 0.50f);
				com.sun.awt.AWTUtilities.setWindowOpaque(inst, true);
				com.sun.awt.AWTUtilities.setWindowShape(inst, new RoundRectangle2D.Double(0, 0, inst
						.getWidth(), inst.getHeight(), 25, 25));
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
		PopupListener pl = new PopupListener();
		addMouseListener(pl);
		WindowListener wl = new WindowListener();
		addWindowListener(wl);
		menuItem.addActionListener(this);
		
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
			popupMenu = new JPopupMenu();
			menuItem = new JMenuItem("Exit");
			popupMenu.add(menuItem);
			setSize(logo.getWidth(), logo.getHeight());
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
		BufferedImage img = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
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
		// clipped round rectangle with the logo
		g2d.drawImage(img, 0, 0, this);
		g2d.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		log.info("actionPerformed("+e+")");
		setVisible(false);
		dispose();
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		targetEnable(this);
	}

	@Override
	public void dragExit(DropTargetEvent dtde) {
		targetDisable(this);
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

				String data = (String) tr.getTransferData(linux);
				List<File> files = Util.textURIListToFileList(data);
				for (File file : files) {
					createDocumentHelper(token, path, url, file);
				}

				dtde.dropComplete(true);

				// Refresh file list
				log.fine("--- refresh - begin ---");
				win.call("refresh", new Object[] {});
				log.fine("--- refresh - end ---");
			} else if (tr.isDataFlavorSupported(windows)) {
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);

				@SuppressWarnings("unchecked")
				List<File> files = (List<File>) tr.getTransferData(windows);
				for (File file : files) {
					createDocumentHelper(token, path, url, file);
				}

				dtde.dropComplete(true);

				// Refresh file list
				log.fine("--- refresh - begin ---");
				win.call("refresh", new Object[] {});
				log.fine("--- refresh - end ---");
			}
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, "ClassNotFoundException: " + e.getMessage(), e);
		} catch (UnsupportedFlavorException e) {
			log.log(Level.SEVERE, "UnsupportedFlavorException: " + e.getMessage(), e);
		} catch (WebServiceException e) {
			log.log(Level.SEVERE, "WebServiceException: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Webservices failure",
					JOptionPane.ERROR_MESSAGE);
		} catch (Throwable e) { // Catch anything else
			log.log(Level.SEVERE, "Throwable: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "General failure", JOptionPane.ERROR_MESSAGE);
		} finally {
			targetDisable(this);
		}
	}

	/**
	 * 
	 */
	private static void createDocumentHelper(String token, String path, String url, File fs)
			throws IOException, AccessDeniedException_Exception, PathNotFoundException_Exception,
			RepositoryException_Exception, IOException_Exception {
		log.info("uploadDocumentHelper(" + token + ", " + path + ", " + url + ", " + fs + ")");

		if (fs.isFile()) {
			try {
				Util.createDocument(token, path, url, fs);
			} catch (VirusDetectedException_Exception e) {
				log.log(Level.SEVERE, "VirusDetectedException: - > " + e.getMessage(), e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Virus detected",
						JOptionPane.ERROR_MESSAGE);
			} catch (FileSizeExceededException_Exception e) {
				log.log(Level.SEVERE, "FileSizeExceededException: - > " + e.getMessage(), e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "FileSize exceeded",
						JOptionPane.ERROR_MESSAGE);
			} catch (UnsupportedMimeTypeException_Exception e) {
				log.log(Level.SEVERE, "UnsupportedMimeTypeException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Unsupported mime type",
						JOptionPane.ERROR_MESSAGE);
			} catch (ItemExistsException_Exception e) {
				log.log(Level.WARNING, "ItemExistsException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Item exists",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (fs.isDirectory()) {
			try {
				Util.createFolder(token, path, url, fs);
			} catch (ItemExistsException_Exception e) {
				log.warning("ItemExistsException: " + e.getMessage());
				JOptionPane.showMessageDialog(null, e.getMessage(), "Item exists",
						JOptionPane.WARNING_MESSAGE);
			}

			File[] files = fs.listFiles();
			for (int i = 0; i < files.length; i++) {
				createDocumentHelper(token, path + "/" + fs.getName(), url, files[i]);
			}
		} else {
			log.log(Level.WARNING, "Unknown file type");
			JOptionPane.showMessageDialog(null, fs.getPath(), "Unknown file type",
					JOptionPane.WARNING_MESSAGE);
		}

		log.info("importDocumentsHelper: void");
	}

	@SuppressWarnings("restriction")
	private static void targetEnable(Window win) {
		com.sun.awt.AWTUtilities.setWindowOpacity(win, 1.00f);
	}

	@SuppressWarnings("restriction")
	private static void targetDisable(Window win) {
		com.sun.awt.AWTUtilities.setWindowOpacity(win, 0.50f);
	}

	/**
	 * 
	 */
	class PopupListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			log.fine("mousePressed("+e+")");
			if (e.isPopupTrigger()) {
				popupMenu.show(((JFrame) e.getComponent()).getContentPane(), e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			log.fine("mouseReleased("+e+")");
			if (e.isPopupTrigger()) {
				popupMenu.show(((JFrame) e.getComponent()).getContentPane(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * 
	 */
	class WindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent we) {
			log.info("windowClosed: calling 'destroyUploaderApplet'");
			win.call("destroyUploaderApplet", new Object[] {});
		}
	}
}
