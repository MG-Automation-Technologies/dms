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

package com.openkm.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import uk.co.mmscomputing.device.sane.jsane;
import uk.co.mmscomputing.device.scanner.ScannerIOException;

@SuppressWarnings("serial")
public class Applet extends JApplet implements ActionListener {
	
	public Applet() {}

	@Override
	public void init() {
		try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.out.println("createGUI didn't successfully complete");
        }
	}
	
	@Override
	public void destroy() {
		System.out.println("Bye!");
	}
	
	/**
	 * 
	 */
	private void createGUI() {
		JButton hi = new JButton("Helo!");
		hi.addActionListener(this);
		add(hi);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			//Scanner device = Scanner.getDevice();
			//device.select();
			//device.getDeviceNames();
			jsane.init();
			String[] x = jsane.getDevices(true);
			System.out.println("LEN: "+x.length);
			//for (int i=0; i<x.length; i++) {
				//System.out.println("--> "+x[i]);
			//}
			jsane.exit();
		} catch (ScannerIOException e) {
			e.printStackTrace();
		}
	}
}
