/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

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

import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type;

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
				String response = Util.createDocument(token, path, fileName, fileType, url, image);
				if (!response.startsWith("OKM_OK")) {
					log.log(Level.SEVERE, "Error: " + response);
					ErrorCode.displayError(response, path+"/"+fileName+"."+fileType);
				}
				
				win.call("refresh", new Object[] {});
			} catch (JSException e) {
				// TODO Investigate why occurs but js method is executed
				log.log(Level.WARNING, "JSException: " + e.getMessage(), e);
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
