import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.net.MalformedURLException;
import java.io.IOException;

import com.sun.star.comp.beans.LocalOfficeConnection2;
import com.sun.star.comp.beans.OOoBean2;
import com.sun.star.comp.beans.OfficeConnection;
import com.sun.star.comp.beans.TestOffice;
import com.sun.star.comp.beans.NoConnectionException;
import com.sun.star.comp.beans.SystemWindowException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.util.CloseVetoException;
import com.sun.star.frame.XDesktop;

public class Hello extends Applet {
	private static final String OFFICE_DOCUMENT = "file:///home/jllort/Escritorio/lucene.odt";
	public static final String URI = "uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager";
	private OOoBean2 aBean;
	
	public void init() {
		System.out.println("alegria 12");
		
		// /usr/lib/openoffice/basis3.1/program here's libofficebean.so
		// /usr/lib/ure/lib/ here's libjpipe.so
		System.setProperty("java.library.path", "/usr/lib/openoffice/basis3.1/program:/usr/lib/ure/lib/");
		
		System.out.println("java.library.path:"+System.getProperty("java.library.path"));

		
		TestOffice test = new TestOffice();
		
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