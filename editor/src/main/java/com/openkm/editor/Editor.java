package com.openkm.editor;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.star.comp.beans.LocalOfficeConnection2;
import com.sun.star.comp.beans.NoConnectionException;
import com.sun.star.comp.beans.OOoBean2;
import com.sun.star.comp.beans.OfficeConnection;
import com.sun.star.comp.beans.SystemWindowException;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.util.CloseVetoException;

/**
 * @author jllort
 *
 */
@SuppressWarnings("deprecation")
public class Editor extends Applet {
	
	// README before executing
	// Now needs to run the command in OS console ->  soffice "-accept=socket,host=0,port=2002;urp;"
    // Not closing the open office window after running
	
	// PROBLEMS applet class loader !!!
	// added in .bashrc "export LD_LIBRARY_PATH=/usr/lib/openoffice/basis3.1/program" but not changes java.library.path 
	// mapUrlToFile(URL url) in UrlToFileManager problem to locate libraries !!!
	// added libraries directly to project path 
	
	private static final long serialVersionUID = -7163587104950744341L;
	private static final String OFFICE_DOCUMENT = "file:///home/jllort/Escritorio/lucene.odt";
	public static final String URI = "uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager";
	private OOoBean2 aBean;
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		System.out.println("alegria 12");
		
		// /usr/lib/openoffice/basis3.1/program here's libofficebean.so
		// /usr/lib/ure/lib/ here's libjpipe.so
		System.setProperty("java.library.path", "/usr/lib/openoffice/basis3.1/program:/usr/lib/ure/lib/");
		
		System.out.println("java.library.path:"+System.getProperty("java.library.path"));
		
		System.out.println("alegria 22");
		
		OfficeConnection officeConnection = new LocalOfficeConnection2();
		
		System.out.println("alegria 23");
		try {
			officeConnection.setUnoUrl(URI);
			aBean = new OOoBean2( officeConnection );
		
			System.out.println("connection test: " + aBean.isOOoConnected());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		add(aBean, BorderLayout.CENTER);
		// createBlankDoc(OFFICE_DOCUMENT, "desc"); //here has problems !!
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
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#destroy()
	 */
	public void destroy() {
		aBean.stopOOoConnection();
		stop();
		System.exit(0);
	}
	
	/**
	 * terminate
	 */
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


	// This method gets called when the applet is terminated
	// That's when the user goes to another page or exits the browser.
    public void stop()
    {
    	// no actions needed here now.
    }


    // The standard method that you have to use to paint things on screen
    // This overrides the empty Applet method, you can't called it "display" for example.
    public void paint(Graphics g)
    {
     g.drawString("Testing 3",20,20);
     g.drawString("Hellow World",20,40);
     createBlankDoc(OFFICE_DOCUMENT, "desc");
    } 
}