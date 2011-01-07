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

package com.openkm.applet.crypto;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

/**
 * MainFrame
 * 
 * @author jllort
 *
 */
public class UploadMainFrame extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UploadMainFrame.class.getName());
	private JLabel jLabel1;
	private JPasswordField jPasswordField1;
	private JPasswordField jPasswordField2;
	private JButton selectFile;
	private CryptoManager cryptoManager;
	private JFileChooser chooser;
	private String choosertitle;
	private File[] files; 
	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UploadMainFrame inst = new UploadMainFrame(new CryptoManager("8E520CF147843CB343DC1754B13A40AC", 
																 "/okm:root", 
																 "http://localhost:8080/OpenKM", 
																 "insert",
																 "",
																 null), 
											   null);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}

	/**
	 * MainFrame
	 * 
	 * @param crypto
	 * @param applet
	 */
	public UploadMainFrame(CryptoManager cryptoManager, JSObject win) {
		super("Encrypt");
		initGUI();
		addWindowListener(this);

		// Set instances
		this.cryptoManager = cryptoManager;

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
	 * initGUI
	 */
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Password pharase");
				jLabel1.setBounds(19, 19, 175, 15);
				
				jPasswordField1 = new JPasswordField();
				getContentPane().add(jPasswordField1);
				jPasswordField1.setText("");
				jPasswordField1.setBounds(19, 36, 235, 22);
				jPasswordField1.setSize(235, 20);
				
				jPasswordField2 = new JPasswordField();
				getContentPane().add(jPasswordField2);
				jPasswordField2.setText("");
				jPasswordField2.setBounds(19, 57, 235, 22);
				jPasswordField2.setSize(235, 20);

				selectFile = new JButton();
				getContentPane().add(selectFile);
				selectFile.setText("Select file");
				selectFile.setBounds(19, 84, 235, 22);
				selectFile.addActionListener(this);
			}
			pack();
			this.setSize(283, 159);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!jPasswordField1.getPassword().equals("") && new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword()))) {
			selectFile.setVisible(false);
			jPasswordField1.setEditable(false);
			jPasswordField2.setEditable(false);
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle(choosertitle);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setMultiSelectionEnabled(true);
	
	//		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Word file","doc","doc"));
	//		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Java file","java","java"));
	
			/* Enable the "All files" option */
			chooser.setAcceptAllFileFilterUsed(true);
	
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				files = chooser.getSelectedFiles();
				
				/* Loop through all files */
				for (int i=0;i<files.length;i++) {
					log.log(Level.INFO, "Document to crypt: " + files[i].getAbsoluteFile());
					File tmpDir = null;
					try {
						// Reading local document
						FileInputStream fis = new FileInputStream(files[i].getAbsoluteFile());
						byte[] data = new byte[fis.available()];
						int read = -1;
						while ((read = fis.read(data)) > 0) {
						}
						log.log(Level.INFO, "Document readed: " + files[i].getAbsoluteFile());
						
						// Create temporary file in temporary folder and save encrypted document
						tmpDir = Util.createTempDir();
						byte[] cryptData = cryptoManager.encrypt(data, new String(jPasswordField1.getPassword()));
						File tmp = new File(tmpDir, files[i].getName());
						FileOutputStream fos = new FileOutputStream(tmp);
						fos.write(cryptData);
						fos.flush();
						fos.close();
						log.log(Level.INFO, "Document encrypted saved localy in temporary folder: ");
						
						// Upload file
						cryptoManager.upload(tmp, files[i].getName(), this);
						log.log(Level.INFO, "Crypt file uploaded: ");
						
						// Refreshing OpenKM UI
						cryptoManager.refreshFolder(this);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} finally {
						tmpDir.delete();
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Must be selected at least one document", "Error", JOptionPane.ERROR_MESSAGE);
			} 
			
			// Making editable
			jPasswordField1.setEditable(true);
			jPasswordField2.setEditable(true);
			// Reseting password
			jPasswordField1.setText("");
			jPasswordField2.setText("");
		} else if (jPasswordField1.getPassword().equals("")) {
			JOptionPane.showMessageDialog(this, "Password must not be empty", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Password might be equals", "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Setting button visible
		selectFile.setVisible(true);
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
	}
	
	@Override
	public void windowClosed(WindowEvent arg0) {	
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
	}
	
	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}
	
	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}
	
	@Override
	public void windowIconified(WindowEvent arg0) {	
	}
	
	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
