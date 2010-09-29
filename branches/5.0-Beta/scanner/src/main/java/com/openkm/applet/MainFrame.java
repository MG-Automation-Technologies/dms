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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;
import uk.co.mmscomputing.device.scanner.ScannerIOException;

public class MainFrame extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MainFrame.class.getName());
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JButton bScan;
	private JComboBox cbFileType;
	private JTextField tfFileName;
	private JCheckBox cbUI;
	private ScannerManager scanner;
	private JSObject win;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Messages.init(Locale.getDefault());
				MainFrame inst = new MainFrame(null, null);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}

	/**
	 *
	 */
	public MainFrame(ScannerManager scanner, JSObject win) {
		super("Scan & Upload");
		initGUI();
		// JComponent jc = scanner.getDevice().getScanGUI();
		// jc.setBounds(260, 10, 115, 150);
		// getContentPane().add(jc);
		addWindowListener(this);

		// Set instances
		this.scanner = scanner;
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
			getContentPane().setLayout(null);

			jLabel1 = new JLabel();
			getContentPane().add(jLabel1);
			jLabel1.setText(Messages.get("file.name"));
			jLabel1.setBounds(19, 19, 105, 15);
			
			jLabel2 = new JLabel();
			getContentPane().add(jLabel2);
			jLabel2.setText(Messages.get("file.type"));
			jLabel2.setBounds(19, 45, 105, 15);
			
			tfFileName = new JTextField();
			getContentPane().add(tfFileName);
			tfFileName.setBounds(125, 15, 190, 22);
			
			ComboBoxModel cbFileTypeModel = new DefaultComboBoxModel(
			new String[] { "PDF", "TIF", "JPG", "PNG", "GIF", "BMP" });
			cbFileType = new JComboBox();
			getContentPane().add(cbFileType);
			cbFileType.setModel(cbFileTypeModel);
			cbFileType.setBounds(125, 43, 55, 22);
			
			cbUI = new JCheckBox(Messages.get("user.interface"), true);
			getContentPane().add(cbUI);
			cbUI.setBounds(185, 43, 140, 22);
			
			bScan = new JButton();
			getContentPane().add(bScan);
			bScan.setText(Messages.get("scan.upload"));
			bScan.setBounds(50, 84, 235, 22);
			bScan.addActionListener(this);
			
			pack();
			this.setSize(340, 159);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			String fileName = tfFileName.getText().trim();
			String fileType = (String) cbFileType.getSelectedItem();

			if (fileName.length() > 0) {
				scanner.acquire(fileName, fileType.toLowerCase(), cbUI.isSelected(), bScan, tfFileName, cbFileType, cbUI);
			} else {
				JOptionPane.showMessageDialog(this, "Empty file name", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (ScannerIOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		log.info("windowClosed: calling 'destroyScannerApplet'");
		if (win != null) {
			win.call("destroyScannerApplet", new Object[] {});
		} else {
			JOptionPane.showMessageDialog(null, "destroyScannerApplet", "JavaScript call",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
