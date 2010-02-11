package com.openkm.applet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import netscape.javascript.JSObject;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type;

import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.FileSizeExceededException_Exception;
import com.openkm.ws.client.IOException_Exception;
import com.openkm.ws.client.ItemExistsException_Exception;
import com.openkm.ws.client.PathNotFoundException_Exception;
import com.openkm.ws.client.RepositoryException_Exception;
import com.openkm.ws.client.UnsupportedMimeTypeException_Exception;
import com.openkm.ws.client.VirusDetectedException_Exception;

public class ScannerManager implements ScannerListener {

	private static Logger log = Logger.getLogger(ScannerManager.class.getName());
	private String token;
	private String path;
	private String url;
	private JSObject win;
	private Scanner scanner;
	private String fileName;
	private String fileType;
	private boolean ui;
	private JButton bScan;
	private JTextField tfFileName;
	private JComboBox cbFileType;
	private JCheckBox cbUI;

	/**
	 * @param token
	 * @param win
	 */
	public ScannerManager(String token, String path, String url, JSObject win) {
		log.info("########## ScannerManager ##########");
		this.token = token;
		this.path = path;
		this.url = url;
		this.win = win;
		scanner = Scanner.getDevice();
		scanner.addListener(this);
	}

	/**
	 *
	 */
	public Scanner getDevice() {
		return scanner;
	}

	/**
	 *
	 */
	public void acquire(String fileName, String fileType, boolean ui, JButton bScan, JTextField tfFileName,
			JComboBox cbFileType, JCheckBox cbUI) throws ScannerIOException {
		log.fine("########## adquire ########## " + fileName + " -> " + fileType);
		this.bScan = bScan;
		this.tfFileName = tfFileName;
		this.cbFileType = cbFileType;
		this.cbUI = cbUI;
		this.fileName = fileName;
		this.fileType = fileType;
		this.ui = ui;
		bScan.setEnabled(false);
		tfFileName.setEnabled(false);
		cbFileType.setEnabled(false);
		cbUI.setEnabled(false);
		scanner.acquire();
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void update(Type type, ScannerIOMetadata metadata) {
		if (type.equals(ScannerIOMetadata.ACQUIRED)) {
			log.fine("***** ACQUIRED *****");
			BufferedImage image = metadata.getImage();

			try {
				Util.uploadDocument(token, path, fileName, fileType, url, image);

				// Refresh file list
				log.fine("--- refresh - begin ---");
				win.call("refresh", new Object[] { "xxx" });
				log.fine("--- refresh - end ---");
			} catch (VirusDetectedException_Exception e) {
				log.log(Level.SEVERE, "VirusDetectedException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (FileSizeExceededException_Exception e) {
				log.log(Level.SEVERE, "FileSizeExceededException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (UnsupportedMimeTypeException_Exception e) {
				log.log(Level.SEVERE, "UnsupportedMimeTypeException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (ItemExistsException_Exception e) {
				log.log(Level.SEVERE, "ItemExistsException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (PathNotFoundException_Exception e) {
				log.log(Level.SEVERE, "PathNotFoundException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (AccessDeniedException_Exception e) {
				log.log(Level.SEVERE, "AccessDeniedException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (RepositoryException_Exception e) {
				log.log(Level.SEVERE, "RepositoryException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException_Exception e) {
				log.log(Level.SEVERE, "IOException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				log.log(Level.SEVERE, "IOException: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (Throwable e) { // Catch java.lang.OutOfMemeoryException
				log.log(Level.SEVERE, "Throwable: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
			log.fine("***** STATECHANGE: " + metadata.getStateStr() + " *****");
			if (metadata.isFinished()) {
				bScan.setEnabled(true);
				tfFileName.setEnabled(true);
				cbFileType.setEnabled(true);
				cbUI.setEnabled(true);
			}
		} else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			log.fine("***** NEGOTIATE *****");
			ScannerDevice device = metadata.getDevice();

			try {
				device.setShowUserInterface(ui);
				device.setShowProgressBar(true);
				// device.setResolution(300);
				// device.setOption("mode", "Color");
				// device.setOption("br-x", 215);
				// device.setOption("br-y", 297.0);

				// SaneDevice sd = (SaneDevice) device;
				// FileOutputStream fos = new FileOutputStream("scanner.txt");
				// OptionDescriptor[] od = sd.getOptionDescriptors();

				// for (int o=0; o<od.length; o++) {
				// Descriptor d = (Descriptor)od[o];
				// System.out.println("- "+d.getName());
				// fos.write(d.toString().getBytes());
				// fos.write("\n\n----------------\n".getBytes());
				// }
				// fos.close();

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
			log.log(Level.SEVERE, metadata.getException().getMessage(), metadata.getException());
			JOptionPane.showMessageDialog(bScan.getParent(), metadata.getException(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			log.finer("update(" + type + ", " + metadata + ")");
		}
	}
}
