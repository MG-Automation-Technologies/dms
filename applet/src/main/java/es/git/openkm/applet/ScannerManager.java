package es.git.openkm.applet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.ksoap2.SoapFault;

import netscape.javascript.JSObject;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type;
import es.git.openkm.util.SoapClient;

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
		log.fine("########## ScannerManager ##########");
		this.token = token;
		this.path = path;
		this.url = url;
		this.win = win;
		scanner = Scanner.getDevice();
		scanner.addListener(this);
	}
	
	/**
	 * @return
	 */
	public Scanner getDevice() {
		return scanner;
	}
	
	/**
	 * @param fileName
	 * @param fileType
	 * @param bScan
	 * @throws ScannerIOException
	 */
	public void acquire(String fileName, String fileType, boolean ui, 
			JButton bScan, JTextField tfFileName, JComboBox cbFileType, JCheckBox cbUI) throws ScannerIOException {
		log.fine("########## adquire ########## "+fileName+" -> "+fileType);
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
	
	/* (non-Javadoc)
	 * @see uk.co.mmscomputing.device.scanner.ScannerListener#update(uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type, uk.co.mmscomputing.device.scanner.ScannerIOMetadata)
	 */
	public void update(Type type, ScannerIOMetadata metadata) {
		if (type.equals(ScannerIOMetadata.ACQUIRED)) {
			log.fine("***** ACQUIRED *****");
			BufferedImage image = metadata.getImage();
						
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, fileType, baos);
				//ImageIO.write(image, fileType, new FileOutputStream(fileName+"."+fileType));
				baos.flush();
				SoapClient client = new SoapClient(url);
				client.create(token, path+"/"+fileName+"."+fileType, baos.toByteArray());
				baos.close();
				
				// Refresh file list
				log.fine("--- refresh - begin ---");
				win.call("refresh", new Object[] {"xxx"} );
				log.fine("--- refresh - end ---");
			} catch (SoapFault e) {
				log.log(Level.SEVERE, "SoapFault - > "+e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), SoapClient.getDetail(e.detail), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Throwable e) { // Catch java.lang.OutOfMemeoryException
				log.log(Level.SEVERE, "Throwable - > "+e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
			log.fine("***** STATECHANGE: "+metadata.getStateStr()+" *****");
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
				//device.setResolution(300);
				//device.setOption("mode", "Color");
				//device.setOption("br-x", 215);
				//device.setOption("br-y", 297.0);
				
				//SaneDevice sd = (SaneDevice) device;
				//FileOutputStream fos = new FileOutputStream("scanner.txt");
				//OptionDescriptor[] od = sd.getOptionDescriptors();
				
				//for (int o=0; o<od.length; o++) {
				//	Descriptor d = (Descriptor)od[o];
				//	System.out.println("- "+d.getName());
				//	fos.write(d.toString().getBytes());
				//	fos.write("\n\n----------------\n".getBytes());
				//}
	        	//fos.close();
				
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				JOptionPane.showMessageDialog(bScan.getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
			log.fine("***** EXCEPTION *****");
			log.log(Level.SEVERE, metadata.getException().getMessage(), metadata.getException());
			JOptionPane.showMessageDialog(bScan.getParent(), metadata.getException(), "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			log.finer("update("+type+", "+metadata+")");
		}
	}
}
