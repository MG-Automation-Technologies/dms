package com.openkm.applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
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

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame(null, null, null, null);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}

	/**
	 * 
	 */
	public MainFrame(String token, String path, String url, JSObject win) {
		super("Uploader");
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
			JLabel jLabel = new JLabel("Drop a list from your file chooser here:");
			getContentPane().add(jLabel, BorderLayout.NORTH);
			new DropTarget(getContentPane(), this);

			pack();
			this.setSize(283, 159);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
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
		log.info("windowClosed: calling 'destroyScannerApplet'");
		win.call("destroyScannerApplet", new Object[] {});
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
	public void dragEnter(DropTargetDragEvent dtde) {
		getContentPane().setBackground(Color.GREEN);
	}

	@Override
	public void dragExit(DropTargetEvent dtde) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
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
