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
