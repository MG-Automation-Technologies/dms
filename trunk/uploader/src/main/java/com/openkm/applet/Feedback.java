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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Feedback extends JFrame {
	private static Logger log = Logger.getLogger(Feedback.class.getName());
	private ColorPane textPane;
	private JButton button;
	
	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		final ArrayList<File> files = new ArrayList<File>();
		files.add(new File("Uno/Dos/Tres.txt"));
		files.add(new File("Dos/Tres/Cuatro/Cinco.txt"));
		files.add(new File("Tres/Cuatro/Cinco/Seis/Siete/Ocho.txt"));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);
				Feedback inst = new Feedback(files);
				inst.setUndecorated(true);
				inst.setResizable(false);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}
	public Feedback(List<File> files) {
		super("Uploader");
		initGUI();

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		setLocation(x, y);
		
		for (File file : files) {
			textPane.append(Color.red, "-> "+file.getPath()+"\n");
		}
	}
	
	/**
	 * 
	 */
	private void initGUI() {
		try {
			setLayout(new BorderLayout());
			textPane = new ColorPane();
			JPanel noWrapPanel = new JPanel( new BorderLayout() );
			noWrapPanel.add( textPane );
			JScrollPane scrollPane = new JScrollPane( noWrapPanel );
			add(scrollPane, BorderLayout.CENTER);
			button = new JButton("Close");
			add(button, BorderLayout.SOUTH);
			
			setSize(200, 300);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
