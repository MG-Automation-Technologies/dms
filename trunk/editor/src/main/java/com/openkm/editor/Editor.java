package com.openkm.editor;

import java.io.IOException;
import java.net.MalformedURLException;

import com.sun.star.comp.beans.LocalOfficeConnection;
import com.sun.star.comp.beans.NoConnectionException;
import com.sun.star.comp.beans.OOoBean;
import com.sun.star.comp.beans.OfficeConnection;
import com.sun.star.comp.beans.SystemWindowException;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.util.CloseVetoException;

public class Editor extends java.applet.Applet {
	private static final String OFFICE_DOCUMENT = "file:///home/jllort/Escritorio/lucene.odt";
	public static final String URI = "uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager";
	private OOoBean aBean;
	
	public void init() {
		
		OfficeConnection officeConnection = new LocalOfficeConnection();
		try {
			officeConnection.setUnoUrl(URI);
			aBean = new OOoBean( officeConnection );
		
			//System.out.println("connection test: " + aBean.isOOoConnected());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//aBean = new OOoBean();
		setLayout(new java.awt.BorderLayout());
		add(aBean, java.awt.BorderLayout.CENTER);
		createBlankDoc(OFFICE_DOCUMENT, "desc");
	}
	
	private void createBlankDoc(String url, String desc) {
		try {
			aBean.loadFromURL(url, null);
			aBean.aquireSystemWindow();
		} catch (SystemWindowException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (CloseVetoException e) {
			e.printStackTrace();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		aBean.stopOOoConnection();
		stop();
		System.exit(0);
	}
	
	private void terminate() {
		XDesktop xDesktop = null;
		
		try {
			xDesktop = aBean.getOOoDesktop();
		} catch (NoConnectionException e) {
			e.printStackTrace();
		}
		
		aBean.stopOOoConnection();
		stop();
		
		if (xDesktop != null) {
			xDesktop.terminate();
		}
		
		System.exit(0);
	}
}