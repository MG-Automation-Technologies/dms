/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExplorerForm.java
 *
 * Created on 08-sep-2010, 9:13:13
 */

package com.openkm.openoffice.ui;

import com.openkm.openoffice.OpenKMAddOn;
import com.openkm.openoffice.bean.FolderNodeBean;
import com.openkm.openoffice.bean.OKMDocumentBean;
import com.openkm.openoffice.config.DocumentFile;
import com.openkm.openoffice.logic.DocumentLogic;
import com.openkm.openoffice.logic.OKMException;
import com.openkm.openoffice.util.ImageUtil;
import com.openkm.openoffice.util.Util;
import com.openkm.ws.client.Document;
import com.openkm.ws.client.Folder;
import com.openkm.ws.client.OKMAuth;
import com.openkm.ws.client.OKMAuthService;
import com.openkm.ws.client.OKMDocument;
import com.openkm.ws.client.OKMDocumentService;
import com.openkm.ws.client.OKMFolder;
import com.openkm.ws.client.OKMFolderService;
import com.openkm.ws.client.OKMRepository;
import com.openkm.ws.client.OKMRepositoryService;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XFrame;
import com.sun.star.uno.UnoRuntime;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.namespace.QName;

/**
 *
 * @author jllort
 */
public class ExplorerForm extends javax.swing.JFrame {

    private static QName AuthServiceName = new QName("http://endpoint.ws.openkm.com/", "OKMAuthService");
    private static QName RepositoryServiceName = new QName("http://endpoint.ws.openkm.com/", "OKMRepositoryService");
    private static QName FolderServiceName = new QName("http://endpoint.ws.openkm.com/", "OKMFolderService");
    private static QName DocumentServiceName = new QName("http://endpoint.ws.openkm.com/", "OKMDocumentService");

    private DefaultTreeModel rootModel;
    private DefaultTableModel tableModel;
    private DefaultTableCellRenderer tableRender;
    private String token = "";
    private OKMAuthService authService = null;
    private OKMRepositoryService repositoryService = null;
    private OKMFolderService folderService = null;
    private OKMDocumentService documentService = null;
    private OKMAuth okmAuth = null;
    private OKMRepository okmRepository = null;
    private OKMFolder okmFolder = null;
    private OKMDocument okmDocument = null;
    private DefaultMutableTreeNode actualNode = null;
    private String host = "";
    private String username = "";
    private String password = "";
    private ImageUtil imageUtil;
    private XFrame xFrame;
    private DocumentFile documentFile;

    /** Creates new form ExplorerForm */
    public ExplorerForm(DocumentFile documentFile, ImageUtil imageUtil) {
        try {
            this.documentFile = documentFile;
            this.imageUtil = imageUtil;
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }

        tableModel = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "", 
                OpenKMAddOn.get().getLang().getString("explorer.name"),
                OpenKMAddOn.get().getLang().getString("explorer.author"),
                OpenKMAddOn.get().getLang().getString("explorer.version"),
                OpenKMAddOn.get().getLang().getString("explorer.date")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };

        tableRender = new IconTableCellRenderer();

        initComponents();
        setLocationByPlatform(true);
        setLocationRelativeTo(getParent());
        table.setDefaultRenderer(Object.class, tableRender);

        TableColumn column = table.getColumnModel().getColumn(0);
        column.setResizable(false);
        column.setPreferredWidth(20);
        column.setMinWidth(20);
        column.setMaxWidth(20);
        column = table.getColumnModel().getColumn(1);
        column.setResizable(false);
        column.setPreferredWidth(20);
        column.setMinWidth(20);
        column.setMaxWidth(20);
        column = table.getColumnModel().getColumn(2);
        column.setResizable(false);
        column.setPreferredWidth(20);
        column.setMinWidth(20);
        column.setMaxWidth(20);
        column = table.getColumnModel().getColumn(3);
        column.setResizable(true);
        column.setPreferredWidth(300);
        column = table.getColumnModel().getColumn(4);
        column.setResizable(true);
        column.setPreferredWidth(100);
        column = table.getColumnModel().getColumn(5);
        column.setResizable(true);
        column.setPreferredWidth(60);
        column = table.getColumnModel().getColumn(6);
        column.setResizable(true);
        column.setPreferredWidth(140);

        setTitle(OpenKMAddOn.get().getLang().getString("explorer.title"));
        editButton.setText(OpenKMAddOn.get().getLang().getString("button.edit"));
        cancelButton.setText(OpenKMAddOn.get().getLang().getString("button.cancel"));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPanel = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        scrollPanelTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        editButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("File explorer");
        setName("explorerForm"); // NOI18N
        setResizable(false);

        scrollPanel.setViewportView(tree);

        table.setBackground(java.awt.Color.white);
        table.setModel(tableModel);
        table.setSelectionBackground(new java.awt.Color(91, 94, 100));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        scrollPanelTable.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        editButton.setText("Edit");
        editButton.setName("edit"); // NOI18N
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setName("cancel"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPanelTable, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(editButton)
                        .addGap(204, 204, 204)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scrollPanelTable, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();

    }//GEN-LAST:event_cancelButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        disableAllButton();
        int selectedRow = table.getSelectedRow();
        try {
            Document doc = (Document) table.getValueAt(selectedRow, 3);
            OKMDocumentBean oKMDocumentBean = DocumentLogic.chekckout(host, username, password, doc, documentFile.getDirectoryToStoreFiles());
            documentFile.add(oKMDocumentBean);
            XComponentLoader loader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, xFrame);
            loader.loadComponentFromURL("file:///"+Util.convertFileNamePathToURI(oKMDocumentBean.getLocalFilename()).toURL().getPath(), "_blank", 0, new PropertyValue[0]);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),OpenKMAddOn.get().getLang().getString("window.error"), JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }//GEN-LAST:event_editButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton editButton;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JScrollPane scrollPanelTable;
    private javax.swing.JTable table;
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables

    public void initServices(String host) throws OKMException {
        try {
            this.host = host;
            authService = new OKMAuthService(new URL(host + "/OKMAuth?wsdl"), AuthServiceName);
            repositoryService = new OKMRepositoryService(new URL(host + "/OKMRepository?wsdl"), RepositoryServiceName);
            folderService = new OKMFolderService(new URL(host + "/OKMFolder?wsdl"), FolderServiceName);
            documentService = new OKMDocumentService(new URL(host + "/OKMDocument?wsdl"), DocumentServiceName);
        } catch (Exception ex) {
            throw new OKMException(ex.getMessage());
        }
    }

    private void getRootFolder() throws OKMException {
        try {
            Folder folder = okmRepository.getRootFolder(token);
            FolderNodeBean folderNode = new FolderNodeBean();
            folderNode.setFolder(folder);
            folderNode.setIcon(imageUtil.selectImage(folder));
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(folderNode);
            rootModel = new DefaultTreeModel(rootNode);
            tree = new JTree(rootModel);
            TreeCellRenderer renderer = new IconCellRenderer();
            tree.setCellRenderer(renderer);
            tree.putClientProperty("JTree.lineStyle", "Angled");
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            tree.setShowsRootHandles(true);
            scrollPanel.setViewportView(tree);
            actualNode = rootNode;
            pack();

        } catch (Exception ex) {
            throw new OKMException(ex.getMessage());
        }
    }

    @SuppressWarnings("static-access")
    private void getChilds() throws OKMException {
        try {
            actualNode.removeAllChildren();
            FolderNodeBean folderNode = (FolderNodeBean) actualNode.getUserObject();
            for (Iterator<Folder> it = okmFolder.getChilds(token, folderNode.getFolder().getPath()).getItem().iterator(); it.hasNext();) {
                Folder folder = it.next();
                FolderNodeBean newfolderNode = new FolderNodeBean();
                newfolderNode.setFolder(folder);
                newfolderNode.setIcon(imageUtil.selectImage(folder));
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newfolderNode);
                actualNode.add(childNode);
            }
            pack();
        } catch (Exception ex) {
            throw new OKMException(ex.getMessage());
        }
    }

    private void getDocumentChilds() throws OKMException {
        InputStream is = null;
        byte[] buf = new byte[1024*10];
        int size = 0;

        while (tableModel.getRowCount()>0) {
            tableModel.removeRow(0);
        }
        FolderNodeBean folderNode = (FolderNodeBean) actualNode.getUserObject();
        try {
            for (Iterator<Document> it = okmDocument.getChilds(token, folderNode.getFolder().getPath()).getItem().iterator(); it.hasNext();) {
                Document doc = it.next();
                Object[] data = new Object[7];
                if (doc.isLocked() && !doc.isCheckedOut()) {
                    is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/icon/lock.gif");
                    buf = new byte[1024*10];
                    size = is.read(buf);
                    data[0] = new ImageIcon(buf);
                } else {
                    is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/icon/empty.gif");
                    buf = new byte[1024*10];
                    size = is.read(buf);
                    data[0] = new ImageIcon(buf);
                }
                if (doc.isCheckedOut()) {
                    is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/icon/edit.gif");
                    buf = new byte[1024*10];
                    size = is.read(buf);
                    data[1] = new ImageIcon(buf);
                } else {
                    is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/icon/empty.gif");
                    buf = new byte[1024*10];
                    size = is.read(buf);
                    data[1] = new ImageIcon(buf);
                }
                try {
                    is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/mime/" + doc.getMimeType() + ".gif");
                    buf = new byte[1024*10];
                    size = is.read(buf);
                    data[2] = new ImageIcon(buf);
                } catch (Exception e) {
                    // Case mime type is not in added icons !!
                    is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/icon/empty.gif");
                    buf = new byte[1024*10];
                    size = is.read(buf);
                    data[2] = new ImageIcon(buf);
                }
                data[3] = doc;
                data[4] = doc.getAuthor();
                data[5] = doc.getActualVersion().getName();
                DateFormat df = new SimpleDateFormat(OpenKMAddOn.get().getLang().getString("date.pattern"));
                Calendar cal = doc.getLastModified().toGregorianCalendar();
                df.format(cal.getTime());
                data[6] = df.format(cal.getTime());
                tableModel.addRow(data);
            }
            pack();
        } catch (Exception ex) {
            throw new OKMException(ex.getMessage());
        }
    }

    public void startUp(XFrame xFrame, final String username, final String password) throws OKMException {
        this.xFrame = xFrame;
        this.username = username;
        this.password = password;
        okmAuth = authService.getOKMAuthPort();
        okmRepository = repositoryService.getOKMRepositoryPort();
        okmFolder = folderService.getOKMFolderPort();
        okmDocument = documentService.getOKMDocumentPort();
        try {
            enableDefaultButton();
            token = okmAuth.login(username, password);
            getRootFolder();
            getChilds();
            getDocumentChilds();

            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent arg0) {
                    int selectedRow = table.getSelectedRow();
                    Document doc = (Document) table.getValueAt(selectedRow, 3);
                    evaluateEnabledButtonByPermissions(doc);
                }
            });

            tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent arg0) {
                    try {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)  tree.getLastSelectedPathComponent();
                        if (node == null) return;
                        actualNode = node;

                        token = okmAuth.login(username, password);
                        getChilds();
                        getDocumentChilds();

                        // Logout OpenKM
                        okmAuth.logout(token);
                        token = "";
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),OpenKMAddOn.get().getLang().getString("window.error"), JOptionPane.ERROR_MESSAGE);
                        if (!token.equals("")) {
                            try {
                                // Logout OpenKM
                                okmAuth.logout(token);
                                token = "";
                            } catch (Exception ex1) {
                            } 
                        }
                    }
                }
            });

            // Logout OpenKM
            okmAuth.logout(token);
            token = "";
        } catch (Exception ex) {
            if (!token.equals("")) {
                try {
                    // Logout OpenKM
                    okmAuth.logout(token);
                    token = "";
                } catch (Exception ex1) {
                } 
            }
            throw new OKMException(ex.getMessage());
        }
    }

    private void disableAllButton() {
        editButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }

    private void enableDefaultButton() {
        editButton.setEnabled(false);
        cancelButton.setEnabled(true);
    }

    private void evaluateEnabledButtonByPermissions(Document doc) {
        if (Util.hasWritePermission(((FolderNodeBean) actualNode.getUserObject()).getFolder(), doc) &&
            !doc.isCheckedOut() && !doc.isLocked()) {
            editButton.setEnabled(true);
        } else {
            editButton.setEnabled(false);
        }
        cancelButton.setEnabled(true);
    }

    class IconCellRenderer extends JLabel implements TreeCellRenderer {
        protected Color m_textSelectionColor;
        protected Color m_textNonSelectionColor;
        protected Color m_bkSelectionColor;
        protected Color m_bkNonSelectionColor;
        protected Color m_borderSelectionColor;
        protected boolean m_selected;

        public IconCellRenderer()
        {
            super();
            m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
            m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
            m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
            m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
            m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
            setOpaque(false);
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
                                                      int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            Object obj = node.getUserObject();
            setText(obj.toString());

            if (obj instanceof Boolean)
                setText(OpenKMAddOn.get().getLang().getString("explorer.retrieve.data"));

            if (obj instanceof FolderNodeBean)
            {
                FolderNodeBean idata = (FolderNodeBean) obj;
                if (expanded) {
                    setIcon(idata.getExpandedIcon());
                } else {
                    setIcon(idata.getIcon());
                }
            } else {
                setIcon(null);
            }

            setFont(tree.getFont());
            setForeground(sel ? m_textSelectionColor :  m_textNonSelectionColor);
            setBackground(sel ? m_bkSelectionColor :  m_bkNonSelectionColor);
            m_selected = sel;
            return this;
        }

        @Override
        public void paintComponent(Graphics g) {
            Color bColor = getBackground();
            Icon icon = getIcon();

            g.setColor(bColor);
            int offset = 0;
            if(icon != null && getText() != null)
              offset = (icon.getIconWidth() + getIconTextGap());
            g.fillRect(offset, 0, getWidth() - 1 - offset,
              getHeight() - 1);

            if (m_selected)
            {
              g.setColor(m_borderSelectionColor);
              g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
            }
            super.paintComponent(g);
        }
    }

    class TableIconCellRendererOLd extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof ImageIcon) {
                setIcon((Icon)value);
                super.setValue(null);
            } else {
                setIcon(null);
                super.setValue(value);
            }
        }
    }

    private class IconTableCellRenderer extends DefaultTableCellRenderer
    {
        protected Color m_textSelectionColor;
        protected Color m_textNonSelectionColor;
        protected Color m_bkSelectionColor;
        protected Color m_bkNonSelectionColor;
        protected Color m_borderSelectionColor;
        protected boolean m_selected;

        public IconTableCellRenderer() {
            super();
            m_textSelectionColor = UIManager.getColor("Table.selectionForeground");
            m_textNonSelectionColor = UIManager.getColor("Table.textForeground");
            m_bkSelectionColor = UIManager.getColor("Table.selectionBackground");
            m_bkNonSelectionColor = UIManager.getColor("Table.textBackground");
            m_borderSelectionColor = UIManager.getColor("Table.selectionBorderColor");
            setOpaque(false);
        }

        @Override
        protected void setValue(Object value) {
            if (value instanceof ImageIcon) {
                setIcon((Icon)value);
                super.setValue(null);
            } else {
                setIcon(null);
                super.setValue(value);
            }
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,  Object value, boolean selected,
                                                       boolean focused, int row, int column) {


            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (value instanceof Document) {
                setText(Util.getDocumentName((Document)value));
                setIcon(null);
            } else if (value instanceof ImageIcon) {
                setText(value.toString());
                setIcon((Icon) value);
            } else {
                setText(value.toString());
                setIcon(null);
            }

            setFont(tree.getFont());
            setForeground(selected ? m_textSelectionColor :  m_textNonSelectionColor);
            setBackground(selected ? m_bkSelectionColor :  m_bkNonSelectionColor);
            m_selected = selected;
            return this;
        }

        @Override
        public void paintComponent(Graphics g) {
            Color bColor = getBackground();
            Icon icon = getIcon();

            g.setColor(bColor);
            int offset = 0;
            if(icon != null && getText() != null)
              offset = (icon.getIconWidth() + getIconTextGap());
            g.fillRect(offset, 0, getWidth() - 1 - offset,
              getHeight() - 1);

            if (m_selected)
            {
              g.setColor(m_borderSelectionColor);
              g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
            }
            super.paintComponent(g);
        }
    }
}
