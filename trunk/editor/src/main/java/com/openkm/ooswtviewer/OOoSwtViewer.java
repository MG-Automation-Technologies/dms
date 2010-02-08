package com.openkm.ooswtviewer;
 
import java.awt.Panel;
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
 
public class OOoSwtViewer extends Panel {
 
 public final String SWRITER = "private:factory/swriter";
 public static final String URI = "uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager";
 
 private static final long serialVersionUID = -1408623115735065822L;
 
 private OOoBean aBean;
 
 public OOoSwtViewer() {
  super();

  OfficeConnection officeConnection = new LocalOfficeConnection();
  try {
	  officeConnection.setUnoUrl(URI);
	  aBean = new OOoBean( officeConnection );
	  System.out.println("connection test: " + aBean.isOOoConnected());
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
 }
 
 	public void setDocument(String url) throws IllegalArgumentException, CloseVetoException, NoConnectionException, 
  											IOException, SystemWindowException {
	 	aBean.loadFromURL(url, null);
	 	aBean.aquireSystemWindow();
 	}
 
 	/** closes the bean viewer and tries to terminate OOo.
 	 */
    public void terminate() throws NoConnectionException {
    	setVisible(false);
        XDesktop xDesktop = null;
        xDesktop = aBean.getOOoDesktop();
        aBean.stopOOoConnection();
        if (xDesktop != null)
        	xDesktop.terminate();
    }
 
    /** closes the bean viewer, leaves OOo running.
     */
    public void close() {
        setVisible(false);
        aBean.stopOOoConnection();
    }
}
