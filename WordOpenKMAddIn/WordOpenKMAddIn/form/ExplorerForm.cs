using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using MSOpenKMCore.ws;
using MSOpenKMCore.config;
using WordOpenKMAddIn.config;
using WordOpenKMAddIn.util;
using System.IO;

using Word = Microsoft.Office.Interop.Word;
using WordOpenKMAddIn.bean;
using WordOpenKMAddIn.logic;

namespace WordOpenKMAddIn.form
{
    public partial class ExplorerForm : Form
    {
        private ImageUtil imageList = null;
        private ConfigXML configXML = null;
        private DocumentXML documentXML = null;
        private FileUtil fileUtil = null;
        private String token = "";
        private TreeNode rootNode = null;
        private TreeNode actualNode = null;
        private TreeNodeMouseClickEventHandler treeNodeMouseClickEventHandler;

        private OKMAuthService authService = null;
        private OKMRepositoryService repositoryService = null;
        private OKMFolderService folderService = null;
        private OKMDocumentService documentService = null;

        private String formType = ""; // Excel, word form type ( used to validating extensions files )

        private Word.Application application = null;

        public ExplorerForm(Word.Application application, String formType, ConfigXML configXML)
        {
            this.application = application;
            this.formType = formType;
            imageList = new ImageUtil();
            this.configXML = configXML;
            this.documentXML = new DocumentXML();
            this.fileUtil = new FileUtil();
            InitializeComponent();
            treeNodeMouseClickEventHandler = new TreeNodeMouseClickEventHandler(nodeMouseClick);
            initServices();

            // Adding click handler
            tree.NodeMouseClick += treeNodeMouseClickEventHandler;

            // Column names
            dataGridView.Columns[0].HeaderText = "";
            dataGridView.Columns[1].HeaderText = "";
            dataGridView.Columns[2].HeaderText = "";
            dataGridView.Columns[3].HeaderText = "Name";
            dataGridView.Columns[4].HeaderText = "Author";
            dataGridView.Columns[5].HeaderText = "Version";
            dataGridView.Columns[6].HeaderText = "Date";
        }

        // Init services
        private void initServices()
        {
            authService = new OKMAuthService(configXML.getHost());
            repositoryService = new OKMRepositoryService(configXML.getHost());
            folderService = new OKMFolderService(configXML.getHost());
            documentService = new OKMDocumentService(configXML.getHost());
        }

        // Gets the root folder
        private void getRootFolder()
        {
            folder rootFolder = repositoryService.getRootFolder(token);
            rootNode = new TreeNode(Util.getFolderName(rootFolder), 0, imageList.selectImageIndex(rootFolder));
            rootNode.Tag = rootFolder;
            rootNode.Name = rootNode.Text;
            tree.Nodes.Add(rootNode);
            actualNode = rootNode;
        }

        // getChilds
        private void getChilds()
        {
            actualNode.Nodes.Clear(); // removes all nodes
            foreach (folder childFolder in folderService.getChilds(token, ((folder)actualNode.Tag).path))
            {
                TreeNode childNode = new TreeNode(Util.getFolderName(childFolder), 0, imageList.selectImageIndex(childFolder));
                childNode.Tag = childFolder;
                childNode.Name = childNode.Text;
                actualNode.Nodes.Add(childNode);
            }
        }

        // getDocumentChilds
        private void getDocumentChilds()
        {
            dataGridView.Rows.Clear();
            foreach (document rowDocument in documentService.getChilds(token, ((folder)actualNode.Tag).path))
            {
                dataGridView.Rows.Add(1);
                int rowCount = dataGridView.RowCount;
                if (rowDocument.locked && !rowDocument.checkedOut)
                {
                    dataGridView.Rows[rowCount - 1].Cells[0].Value = Image.FromFile(@"images\icon\lock.gif");
                }
                else
                {
                    dataGridView.Rows[rowCount - 1].Cells[0].Value = Image.FromFile(@"images\icon\empty.gif");
                }
                if (rowDocument.checkedOut)
                {
                    dataGridView.Rows[rowCount - 1].Cells[1].Value = Image.FromFile(@"images\icon\edit.gif");
                }
                else
                {
                    dataGridView.Rows[rowCount - 1].Cells[1].Value = Image.FromFile(@"images\icon\empty.gif");
                }
                dataGridView.Rows[rowCount - 1].Cells[2].Value = Image.FromFile(@"images\mime\" + rowDocument.mimeType.Replace("/", @"\") + ".gif");
                dataGridView.Rows[rowCount - 1].Cells[3].Value = fileUtil.getDocumentName(rowDocument);
                dataGridView.Rows[rowCount - 1].Cells[4].Value = rowDocument.author;
                dataGridView.Rows[rowCount - 1].Cells[5].Value = rowDocument.actualVersion.name;
                dataGridView.Rows[rowCount - 1].Cells[6].Value = rowDocument.lastModified;
                dataGridView.Rows[rowCount - 1].Tag = rowDocument;
            }
        }

        // mouse click
        private void nodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            try
            {
                // Init values
                rootNode = null;
                actualNode = null;

                // OpenKM authentication
                token = authService.login(configXML.getUser(), configXML.getPassword());

                folder folderNode = (folder) e.Node.Tag;
                actualNode = e.Node;
                
                // Gets folder childs
                getChilds();
                actualNode.ExpandAll();

                // Gets document childs
                getDocumentChilds(); 

                // Logout OpenKM
                authService.logout(token);
                token = "";
            } catch (Exception ex)
            {
                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
            }
        }

        // Starting up drawing folder tree
        public void startUp()
        {
            try
            {
                // OpenKM authentication
                token = authService.login(configXML.getUser(), configXML.getPassword());

                // Setting the image list
                tree.ImageList = imageList.get();

                // Grid user properties
                dataGridView.AllowUserToAddRows = false;
                dataGridView.AllowUserToDeleteRows = false;
                dataGridView.AllowUserToOrderColumns = false;
                dataGridView.MultiSelect = false;
                dataGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

                // Suppress repainting the TreeView until all the objects have been created.
                tree.BeginUpdate();

                // Clear the TreeView each time the method is called.
                tree.Nodes.Clear();

                // Draws the root folder
                getRootFolder();

                // Draws the root folder childs
                getChilds();

                // Draws document childs
                getDocumentChilds();

                // Expand all nodes
                tree.ExpandAll();

                // Begin repainting the TreeView.
                tree.EndUpdate();

                // Logout OpenKM
                authService.logout(token);
                token = "";

            }
            catch (Exception ex)
            {
                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
            }
        }


        private void cancel_Click(object sender, EventArgs e)
        {
            Hide();
        }

        private void edit_Click(object sender, EventArgs e)
        {
            Hide();
            if (dataGridView.RowCount > 0 && dataGridView.SelectedRows.Count>0)
            {
                document doc = (document) dataGridView.Rows[dataGridView.SelectedRows[0].Index].Tag;
                OKMDocument oKMDocument = DocumentLogic.checkoutDocument(doc, configXML.getHost(), configXML.getUser(), configXML.getPassword());
                if (oKMDocument != null)
                {
                    documentXML.add(oKMDocument);
                    object missingValue = Type.Missing;
                    object readOnly = false;
                    object fileName = oKMDocument.getLocalFilename();
                    application.Documents.Open(ref fileName,
                        ref missingValue, ref readOnly, ref missingValue, ref missingValue, ref missingValue,
                        ref missingValue, ref missingValue, ref missingValue, ref missingValue, ref missingValue,
                        ref missingValue, ref missingValue, ref missingValue, ref missingValue, ref missingValue);
                }
            }
        }
    }
}
