package com.openkm.openoffice;

import com.openkm.openoffice.bean.ConfigBean;
import com.openkm.openoffice.bean.OKMDocumentBean;
import com.openkm.openoffice.config.ConfigFile;
import com.openkm.openoffice.config.DocumentFile;
import com.openkm.openoffice.logic.DocumentLogic;
import com.openkm.openoffice.logic.OKMException;
import com.openkm.openoffice.ui.ConfigForm;
import com.openkm.openoffice.ui.ExplorerForm;
import com.openkm.openoffice.ui.TreeForm;
import com.openkm.openoffice.ui.WaitWindow;
import com.openkm.openoffice.util.ImageUtil;
import com.openkm.openoffice.util.Util;
import com.sun.star.frame.FeatureStateEvent;
import com.sun.star.frame.XModel;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;
import com.sun.star.system.XSystemShellExecute;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;


public final class OpenKMAddOn extends WeakBase
   implements com.sun.star.lang.XInitialization,
              com.sun.star.frame.XDispatch,
              com.sun.star.lang.XServiceInfo,
              com.sun.star.frame.XDispatchProvider
{
    private static OpenKMAddOn singleton;

    /**
	 * @return singleton Main instance
	 */
	public static OpenKMAddOn get() {
		return singleton;
	}

    public ImageUtil imageUtil;
    public ConfigForm configForm;
    public TreeForm treeForm;
    public ExplorerForm explorerForm;
    public WaitWindow waitWindow;
    private final XComponentContext m_xContext;
    private com.sun.star.frame.XFrame m_xFrame;
    private static final String m_implementationName = OpenKMAddOn.class.getName();
    private static final String[] m_serviceNames = { "com.sun.star.frame.ProtocolHandler" };

    public OpenKMAddOn( XComponentContext context )
    {
        singleton = this;
        m_xContext = context;
        try {
            ConfigFile.init();
            DocumentFile.init();
            imageUtil = new ImageUtil();
            configForm = new ConfigForm(ConfigFile.read());
            treeForm = new TreeForm(imageUtil);
            explorerForm = new ExplorerForm(imageUtil);
            waitWindow = new WaitWindow();
        } catch (OKMException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    };

    public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
        XSingleComponentFactory xFactory = null;

        if ( sImplementationName.equals( m_implementationName ) )
            xFactory = Factory.createComponentFactory(OpenKMAddOn.class, m_serviceNames);
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo( XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                                                m_serviceNames,
                                                xRegistryKey);
    }

    // com.sun.star.lang.XInitialization:
    public void initialize( Object[] object )
        throws com.sun.star.uno.Exception
    {
        if ( object.length > 0 )
        {
            m_xFrame = (com.sun.star.frame.XFrame)UnoRuntime.queryInterface(
                com.sun.star.frame.XFrame.class, object[0]);
        }
    }

    // com.sun.star.frame.XDispatch:
     public void dispatch( com.sun.star.util.URL aURL,
                           com.sun.star.beans.PropertyValue[] aArguments )
    {
        try {
            if ( aURL.Protocol.compareTo("com.openkm.openoffice.openkmaddon:") == 0 )
            {
                if ( aURL.Path.compareTo("config") == 0 )
                {
                    Util.startNewThread(this.getClass().getClassLoader(), new Runnable() {
                        public void run() {
                            configForm.setVisible(true);
                        }
                    });
                    return;
                }

                if ( aURL.Path.compareTo("edit") == 0 )
                {
                    Util.startNewThread(this.getClass().getClassLoader(), new Runnable() {
                        public void run() {
                            try {
                                ConfigBean configBean = ConfigFile.read();
                                explorerForm.initServices(ConfigFile.read().getHost());
                                explorerForm.startUp(m_xFrame, configBean.getUser(), configBean.getPassword());
                                explorerForm.setVisible(true);
                            } catch (OKMException ex) {
                                JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    return;
                }
                if ( aURL.Path.compareTo("add") == 0 )
                {
                    String documentPath = getCurrentDocumentPath();
                    if (documentPath!=null && !documentPath.equals("")) {
                        Util.startNewThread(this.getClass().getClassLoader(), new Runnable() {
                            public void run() {
                                try {
                                    ConfigBean configBean = ConfigFile.read();
                                    treeForm.initServices(ConfigFile.read().getHost());
                                    treeForm.startUp(configBean.getUser(), configBean.getPassword());
                                    treeForm.setVisible(true);
                                } catch (OKMException ex) {
                                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null,"You must first, save your file on hard disk","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
                if ( aURL.Path.compareTo("checkin") == 0 )
                {
                    Util.startNewThread(this.getClass().getClassLoader(), new Runnable() {
                        public void run() {
                            try {
                                String documentPath = getCurrentDocumentPath();
                                if (documentPath != null && !documentPath.equals("")) {
                                    if (DocumentFile.isOpenKMDocument(documentPath)) {
                                        if (JOptionPane.showConfirmDialog(null,"Are you sure want updating document ?","Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                                            waitWindow.setVisible(true);
                                            OKMDocumentBean oKMDocumentBean = DocumentFile.findByLocalFileName(documentPath);
                                            ConfigBean configBean = ConfigFile.read();
                                            DocumentLogic.checkin(configBean.getHost(), configBean.getUser(), configBean.getPassword(), oKMDocumentBean);
                                            DocumentFile.remove(oKMDocumentBean);
                                            File file = new File(documentPath);
                                            file.deleteOnExit(); // file is always locally deleted
                                            waitWindow.setVisible(false);
                                            XComponent xcomponent = (XComponent)UnoRuntime.queryInterface(XComponent.class, m_xFrame);
                                            closeDocument(xcomponent);
                                        }
                                    }
                                }
                            } catch (OKMException ex) {
                                waitWindow.setVisible(false);
                                JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                            } catch (CloseVetoException ex) {
                                // Some extrange error on closing document
                                waitWindow.setVisible(false);
                                try {
                                    this.finalize();
                                } catch (Throwable ex1) {
                                }
                            }
                        }
                    });
                    return;
                }
                if ( aURL.Path.compareTo("cancelcheckin") == 0 )
                {
                    Util.startNewThread(this.getClass().getClassLoader(), new Runnable() {
                        public void run() {
                            try {
                                String documentPath = getCurrentDocumentPath();
                                if (documentPath != null && !documentPath.equals("")) {
                                    if (DocumentFile.isOpenKMDocument(documentPath)) {
                                        if (JOptionPane.showConfirmDialog(null,"Are you sure want cancelling editing ?","Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                                            waitWindow.setVisible(true);
                                            OKMDocumentBean oKMDocumentBean = DocumentFile.findByLocalFileName(documentPath);
                                            ConfigBean configBean = ConfigFile.read();
                                            DocumentLogic.cancelCheckout(configBean.getHost(), configBean.getUser(), configBean.getPassword(), oKMDocumentBean);
                                            DocumentFile.remove(oKMDocumentBean);
                                            File file = new File(documentPath);
                                            file.deleteOnExit(); // file is always locally deleted
                                            waitWindow.setVisible(false);
                                            XComponent xcomponent = (XComponent)UnoRuntime.queryInterface(XComponent.class, m_xFrame);
                                            closeDocument(xcomponent);
                                        }
                                    }
                                }
                            } catch (OKMException ex) {
                                waitWindow.setVisible(false);
                                JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                            } catch (CloseVetoException ex) {
                                // Some extrange error on closing document
                                waitWindow.setVisible(false);
                                try {
                                    this.finalize();
                                } catch (Throwable ex1) {
                                }
                            }
                        }
                    });
                    return;
                }
                if ( aURL.Path.compareTo("help") == 0 )
                {
                    Util.startNewThread(this.getClass().getClassLoader(), new Runnable() {
                        public void run() {
                            try {
                                XMultiComponentFactory xFact = m_xContext.getServiceManager();
                                Object xObject = xFact.createInstanceWithContext("com.sun.star.system.SystemShellExecute", m_xContext);
                                XSystemShellExecute shell = (XSystemShellExecute) UnoRuntime.queryInterface( XSystemShellExecute.class, xObject );
                                shell.execute("http://www.openkm.com", "", 0);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    return;
                }
            }
        } catch (OKMException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addStatusListener( com.sun.star.frame.XStatusListener xControl,
                                    com.sun.star.util.URL aURL )
    {
        try {
            boolean isOpenKMDoc = false;

            if (!getCurrentDocumentPath().equals("")) {
                if (DocumentFile.isOpenKMDocument(getCurrentDocumentPath())) {
                    isOpenKMDoc = true;
                }
            }

            if ( aURL.Protocol.compareTo("com.openkm.openoffice.openkmaddon:") == 0 )
            {
                if ( aURL.Path.compareTo("add") == 0 ) {
                    com.sun.star.util.URL buttonAdd = new com.sun.star.util.URL();
                    buttonAdd.Path = "add";
                    buttonAdd.Protocol  = "com.openkm.openoffice.openkmaddon:";
                    buttonAdd.Complete = "com.openkm.openoffice.openkmaddon:add";
                    FeatureStateEvent fsEventAdd = new FeatureStateEvent();
                    fsEventAdd.FeatureURL = buttonAdd;
                    fsEventAdd.Source = this;
                    fsEventAdd.IsEnabled = !isOpenKMDoc;
                    fsEventAdd.Requery = false;
                    xControl.statusChanged(fsEventAdd);
                }
                if ( aURL.Path.compareTo("edit") == 0 ) {
                    com.sun.star.util.URL buttonEdit = new com.sun.star.util.URL();
                    buttonEdit.Path = "edit";
                    buttonEdit.Protocol  = "com.openkm.openoffice.openkmaddon:";
                    buttonEdit.Complete = "com.openkm.openoffice.openkmaddon:edit";
                    FeatureStateEvent fsEventEdit = new FeatureStateEvent();
                    fsEventEdit.FeatureURL = buttonEdit;
                    fsEventEdit.Source = this;
                    fsEventEdit.IsEnabled = !isOpenKMDoc;
                    fsEventEdit.Requery = false;
                    xControl.statusChanged(fsEventEdit);
                }

                if ( aURL.Path.compareTo("checkin") == 0 ) {
                    com.sun.star.util.URL buttonCheckin = new com.sun.star.util.URL();
                    buttonCheckin.Path = "checkin";
                    buttonCheckin.Protocol  = "com.openkm.openoffice.openkmaddon:";
                    buttonCheckin.Complete = "com.openkm.openoffice.openkmaddon:checkin";
                    FeatureStateEvent fsEventCheckin = new FeatureStateEvent();
                    fsEventCheckin.FeatureURL = buttonCheckin;
                    fsEventCheckin.Source = this;
                    fsEventCheckin.IsEnabled = isOpenKMDoc;
                    fsEventCheckin.Requery = false;
                    xControl.statusChanged(fsEventCheckin);
                }

                if ( aURL.Path.compareTo("cancelcheckin") == 0 ) {
                    com.sun.star.util.URL  buttonCancelCheckin = new com.sun.star.util.URL();
                    buttonCancelCheckin.Path = "cancelcheckin";
                    buttonCancelCheckin.Protocol  = "com.openkm.openoffice.openkmaddon:";
                    buttonCancelCheckin.Complete = "com.openkm.openoffice.openkmaddon:cancelcheckin";
                    FeatureStateEvent fsEventCancelCheckin = new FeatureStateEvent();
                    fsEventCancelCheckin.FeatureURL = buttonCancelCheckin;
                    fsEventCancelCheckin.Source = this;
                    fsEventCancelCheckin.IsEnabled = isOpenKMDoc;
                    fsEventCancelCheckin.Requery = false;
                    xControl.statusChanged(fsEventCancelCheckin);
                }
            }

        } catch (OKMException ex) {
        }
    }

    public void removeStatusListener( com.sun.star.frame.XStatusListener xControl,
                                       com.sun.star.util.URL aURL )
    {
    }

    // com.sun.star.lang.XServiceInfo:
    public String getImplementationName() {
         return m_implementationName;
    }

    public boolean supportsService( String sService ) {
        int len = m_serviceNames.length;

        for( int i=0; i < len; i++) {
            if (sService.equals(m_serviceNames[i]))
                return true;
        }
        return false;
    }

    public String[] getSupportedServiceNames() {
        return m_serviceNames;
    }

    // com.sun.star.frame.XDispatchProvider:
    public com.sun.star.frame.XDispatch queryDispatch( com.sun.star.util.URL aURL,
                                                       String sTargetFrameName,
                                                       int iSearchFlags )
    {
        if ( aURL.Protocol.compareTo("com.openkm.openoffice.openkmaddon:") == 0 )
        {
            if ( aURL.Path.compareTo("config") == 0 )
                return this;
            if ( aURL.Path.compareTo("edit") == 0 )
                return this;
            if ( aURL.Path.compareTo("add") == 0 )
                return this;
            if ( aURL.Path.compareTo("checkin") == 0 )
                return this;
            if ( aURL.Path.compareTo("cancelcheckin") == 0 )
                return this;
            if ( aURL.Path.compareTo("help") == 0 )
                return this;
        }
        return null;
    }

    // com.sun.star.frame.XDispatchProvider:
    public com.sun.star.frame.XDispatch[] queryDispatches(
         com.sun.star.frame.DispatchDescriptor[] seqDescriptors )
    {
        int nCount = seqDescriptors.length;
        com.sun.star.frame.XDispatch[] seqDispatcher =
            new com.sun.star.frame.XDispatch[seqDescriptors.length];

        for( int i=0; i < nCount; ++i )
        {
            seqDispatcher[i] = queryDispatch(seqDescriptors[i].FeatureURL,
                                             seqDescriptors[i].FrameName,
                                             seqDescriptors[i].SearchFlags );
        }
        return seqDispatcher;
    }

    private String getCurrentDocumentPath() throws OKMException {
        String docPath = "";
        XModel xDoc = (XModel) UnoRuntime.queryInterface(
        XModel.class, m_xFrame.getController().getModel());
        URL url;
        
        if (xDoc.getURL()!=null && !xDoc.getURL().equals("")) {
            try {
                url = new URL(xDoc.getURL());
                docPath = java.net.URLDecoder.decode(url.getPath(), "UTF-8"); // All local path are in UTF-8
            } catch (MalformedURLException ex) {
                throw new OKMException(ex.getMessage());
            } catch (UnsupportedEncodingException ex) {
                throw new OKMException(ex.getMessage());
            }

            if (docPath.startsWith("////")) {
                docPath = docPath.replaceFirst("////", "/");
            }
        }

        return docPath;
    }

    public void create(String path) throws OKMException {
        String documentPath = getCurrentDocumentPath();
        if (documentPath!=null && !documentPath.equals("")) {
            waitWindow.setVisible(true);
            ConfigBean configBean = ConfigFile.read();
            OKMDocumentBean document = new OKMDocumentBean();
            document.setLocalFilename(documentPath);
            try {
                document.setPath(path + "/" + Util.getFileName(documentPath));
            } catch (UnsupportedEncodingException ex) {
                new OKMException(ex.getMessage());
            }
            DocumentLogic.create(configBean.getHost(), configBean.getUser(), configBean.getPassword(), document);
            waitWindow.setVisible(false);
            JOptionPane.showMessageDialog(null,"Document added","Information", JOptionPane.PLAIN_MESSAGE);
        } else {
            waitWindow.setVisible(false);
            JOptionPane.showMessageDialog(null,"You must first, save your file on hard disk","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void closeDocument(XComponent aComponent) throws CloseVetoException {
        XComponent xComponent2 = (XComponent) UnoRuntime.queryInterface(XComponent.class, aComponent);
        xComponent2.dispose();
        XCloseable xCloseable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, aComponent);

        if (xCloseable != null) {
            xCloseable.close(true);
        } else {
            XComponent xComponent = (XComponent) UnoRuntime.queryInterface(XComponent.class, aComponent);
            xComponent.dispose();
        }
    }

    public void showWaitWindow() {
        waitWindow.setVisible(true);
    }

    public void hideWaitWindow() {
        waitWindow.setVisible(false);
    }
}
