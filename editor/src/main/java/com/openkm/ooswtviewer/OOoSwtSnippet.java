package com.openkm.ooswtviewer;
 
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
 
import javax.swing.JRootPane;
 
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
 
public class OOoSwtSnippet {
 
private static final String OFFICE_DOCUMENT = "file:///home/jllort/Escritorio/lucene.odt";
 
    private static final String NEW_WRITTER_DOCUMENT = "private:factory/swriter";
 
    public static void main(String[] args) {
    	final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
 
        Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
 
        try {
        	System.setProperty("sun.awt.noerasebackground", "true");
        } catch (NoSuchMethodError error) {
        }
 
        /* Create and setting up frame */
        Frame frame = SWT_AWT.new_Frame(composite);
        Panel panel = new Panel(new BorderLayout()) {
        	public void update(java.awt.Graphics g) {
        		paint(g);
        	}
        };
        frame.add(panel);
        JRootPane root = new JRootPane();
        panel.add(root);
        java.awt.Container contentPane = root.getContentPane();
 
        shell.setSize(800, 600);
        final OOoSwtViewer viewer = new OOoSwtViewer();
        contentPane.add(viewer);
 
        // viewer.setDocument(NEW_WRITTER_DOCUMENT);
        try {
            viewer.setDocument(OFFICE_DOCUMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        shell.open();
        shell.addDisposeListener(new DisposeListener() {
        	public void widgetDisposed(DisposeEvent e) {
        		try {
        			viewer.close();
        		} catch (RuntimeException exception) {
        			exception.printStackTrace();
        		}
        	}
        });
        while (!shell.isDisposed()) {
        	if (!display.readAndDispatch())
        		display.sleep();
        }
        display.dispose();
    }
}
