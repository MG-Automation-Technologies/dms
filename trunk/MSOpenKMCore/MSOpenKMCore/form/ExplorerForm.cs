using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

using MSOpenKMCore.ws;
using MSOpenKMCore.config;
using MSOpenKMCore.util;
using System.IO;

using Word = Microsoft.Office.Interop.Word;
using Excel = Microsoft.Office.Interop.Excel;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using Visio = Microsoft.Office.Interop.Visio;
using Office = Microsoft.Office.Core;
using MSOpenKMCore.bean;
using MSOpenKMCore.logic;

namespace MSOpenKMCore.form
{
    public partial class ExplorerForm : Form
    {
        private ImageUtil imageList = null;
        private ConfigXML configXML = null;
        private DocumentXML documentXML = null;
        private String token = "";
        private TreeNode rootNode = null;
        private TreeNode actualNode = null;

        private OKMAuth authService = null;
        private OKMRepository repositoryService = null;
        private OKMFolder folderService = null;
        private MSOpenKMCore.ws.OKMDocument documentService = null;

        private String formType = ""; // Excel, word form type ( used to validating extensions files )

        private Object application  = null;
        ComponentResourceManager resources = null;

        public ExplorerForm(Object application, String formType, ConfigXML configXML, DocumentXML docXML)
        {
            try
            {
                resources = new ComponentResourceManager(typeof(ExplorerForm));

                this.application = application;
                this.formType = formType;
                imageList = new ImageUtil();
                this.configXML = configXML;
                this.documentXML = docXML;

                // Initialize component
                InitializeComponent();

                // Centering form
                this.CenterToParent();

                // Column translations
                dataGridView.Columns[0].HeaderText = "";
                dataGridView.Columns[1].HeaderText = "";
                dataGridView.Columns[2].HeaderText = "";
                dataGridView.Columns[3].HeaderText = resources.GetString("name");
                dataGridView.Columns[4].HeaderText = resources.GetString("author");
                dataGridView.Columns[5].HeaderText = resources.GetString("version");
                dataGridView.Columns[6].HeaderText = resources.GetString("date");

                // Translations
                edit.Text = resources.GetString("edit");
                cancel.Text = resources.GetString("cancel");
                this.Text = resources.GetString("documentexplorer");

                // By default edit button is always disabled
                edit.Enabled = false;

                // Setting the image list
                tree.ImageList = imageList.get();

                // Grid user properties
                dataGridView.AllowUserToAddRows = false;
                dataGridView.AllowUserToDeleteRows = false;
                dataGridView.AllowUserToOrderColumns = false;
                dataGridView.MultiSelect = false;
                dataGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

                // Adding click handlers
                tree.NodeMouseClick += new TreeNodeMouseClickEventHandler(nodeMouseClick);
                dataGridView.CellClick += new DataGridViewCellEventHandler(dataGridView_CellClick);
            }
            catch (Exception e)
            {
                String errorMsg = "ExplorerForm - (ExplorerForm)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        

        // Init services
        private void initServices()
        {
            try
            {
                SSL.init(configXML.getHost());
                authService = new OKMAuth(configXML.getHost());
                repositoryService = new OKMRepository(configXML.getHost());
                folderService = new OKMFolder(configXML.getHost());
                documentService = new MSOpenKMCore.ws.OKMDocument(configXML.getHost());
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Gets the root folder
        private void getRootFolder()
        {
            try
            {
                folder rootFolder = repositoryService.getRootFolder(token);
                int selectedImage = imageList.selectImageIndex(rootFolder);
                rootNode = new TreeNode(Util.getFolderName(rootFolder), selectedImage, selectedImage);
                rootNode.Tag = rootFolder;
                rootNode.Name = rootNode.Text;
                tree.Nodes.Add(rootNode);
                actualNode = rootNode;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // getChilds
        private void getChilds()
        {
            try
            {
                actualNode.Nodes.Clear(); // removes all nodes
                foreach (MSOpenKMCore.ws.folder childFolder in folderService.getChilds(token, ((MSOpenKMCore.ws.folder)actualNode.Tag).path))
                {
                    int selectedImage = imageList.selectImageIndex(childFolder);
                    TreeNode childNode = new TreeNode(Util.getFolderName(childFolder), selectedImage, selectedImage);
                    childNode.Tag = childFolder;
                    childNode.Name = childNode.Text;
                    actualNode.Nodes.Add(childNode);
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // getDocumentChilds
        private void getDocumentChilds()
        {
            try
            {
                dataGridView.Rows.Clear();
                foreach (document rowDocument in documentService.getChilds(token, ((folder)actualNode.Tag).path))
                {
                    dataGridView.Rows.Add(1);
                    int rowCount = dataGridView.RowCount;
                    if (rowDocument.locked && !rowDocument.checkedOut)
                    {
                        Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.icon.lock.gif");
                        dataGridView.Rows[rowCount - 1].Cells[0].Value = Image.FromStream(s);
                        s.Close();
                    }
                    else
                    {
                        Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.icon.empty.gif");
                        dataGridView.Rows[rowCount - 1].Cells[0].Value = Image.FromStream(s);
                        s.Close();
                    }
                    if (rowDocument.checkedOut)
                    {
                        Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.icon.edit.gif");
                        dataGridView.Rows[rowCount - 1].Cells[1].Value = Image.FromStream(s);
                        s.Close();
                    }
                    else
                    {
                        Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.icon.empty.gif");
                        dataGridView.Rows[rowCount - 1].Cells[1].Value = Image.FromStream(s);
                        s.Close();
                    }
                    Stream sTream = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.mime." + rowDocument.mimeType.Replace("/", ".") + ".gif");
                    dataGridView.Rows[rowCount - 1].Cells[2].Value = Image.FromStream(sTream);
                    sTream.Close();
                    dataGridView.Rows[rowCount - 1].Cells[3].Value = Util.getDocumentName(rowDocument);
                    dataGridView.Rows[rowCount - 1].Cells[4].Value = rowDocument.author;
                    dataGridView.Rows[rowCount - 1].Cells[5].Value = rowDocument.actualVersion.name;
                    dataGridView.Rows[rowCount - 1].Cells[6].Value = rowDocument.lastModified;
                    dataGridView.Rows[rowCount - 1].Tag = rowDocument;
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // mouse click
        private void dataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                // Evaluates buttons enabled
                if (dataGridView.RowCount > 0 && dataGridView.SelectedRows.Count > 0)
                {
                    MSOpenKMCore.ws.folder fld = (MSOpenKMCore.ws.folder)actualNode.Tag;
                    MSOpenKMCore.ws.document doc = (MSOpenKMCore.ws.document)dataGridView.Rows[dataGridView.SelectedRows[0].Index].Tag;
                    evaluateEnabledButtonByPermissions(fld, doc);
                }
                else
                {
                    enableDefaultButtons();
                }
            }
            catch (Exception ex)
            {
                throw ex;
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

                MSOpenKMCore.ws.folder folderNode = (MSOpenKMCore.ws.folder)e.Node.Tag;
                actualNode = e.Node;
                
                // Gets folder childs
                getChilds();
                actualNode.ExpandAll();

                // Gets document childs
                getDocumentChilds();

                // Evaluates buttons enabled
                if (dataGridView.RowCount > 0 && dataGridView.SelectedRows.Count > 0)
                {
                    MSOpenKMCore.ws.folder fld = (MSOpenKMCore.ws.folder)actualNode.Tag;
                    MSOpenKMCore.ws.document doc = (MSOpenKMCore.ws.document)dataGridView.Rows[dataGridView.SelectedRows[0].Index].Tag;
                    evaluateEnabledButtonByPermissions(fld, doc);
                }
                else
                {
                    enableDefaultButtons();
                }

                // Logout OpenKM
                authService.logout(token);
                token = "";
            } catch (Exception ex)
            {
                if (!token.Equals(""))
                {
                    String errorMsg = "ExplorerForm - (nodeMouseClick)\n" + ex.Message + "\n\n" + ex.StackTrace;
                    MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);

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
                // Refreshing config file
                configXML.ReadConfig();

                // Init services
                initServices();

                // Default buttons enabled
                enableDefaultButtons();

                // OpenKM authentication
                token = authService.login(configXML.getUser(), configXML.getPassword());

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

                // Evaluates buttons enabled
                if (dataGridView.RowCount > 0 && dataGridView.SelectedRows.Count > 0)
                {
                    MSOpenKMCore.ws.folder fld = (MSOpenKMCore.ws.folder)actualNode.Tag;
                    MSOpenKMCore.ws.document doc = (MSOpenKMCore.ws.document)dataGridView.Rows[dataGridView.SelectedRows[0].Index].Tag;
                    evaluateEnabledButtonByPermissions(fld, doc);
                }
                else
                {
                    enableDefaultButtons();
                }

                // Logout OpenKM
                authService.logout(token);
                token = "";
            }
            catch (Exception ex)
            {
                String errorMsg = "ExplorerForm - (startUp)\n" + ex.Message;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);

                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
            }
        }

        // Evaluates enabled buttons by permisions
        private void evaluateEnabledButtonByPermissions(MSOpenKMCore.ws.folder fld, MSOpenKMCore.ws.document doc)
        {
            if (Util.hasWritePermission(fld, doc) && !doc.locked && !doc.checkedOut)
            {
                edit.Enabled = true;
            }
            else
            {
                edit.Enabled = false;
            }
        }

        // Disables als buttons
        private void disableAllButtons()
        {
            edit.Enabled = false;
            cancel.Enabled = false;
        }

        // Defaults buttons enabled view
        private void enableDefaultButtons()
        {
            edit.Enabled = false;
            cancel.Enabled = true;
        }

        //  cancel button
        private void cancel_Click(object sender, EventArgs e)
        {
            Hide();
        }

        // edit button
        private void edit_Click(object sender, EventArgs e)
        {
            try
            {
                if (dataGridView.RowCount > 0 && dataGridView.SelectedRows.Count > 0)
                {
                    bool editFile = true;
                    MSOpenKMCore.ws.document doc = (MSOpenKMCore.ws.document)dataGridView.Rows[dataGridView.SelectedRows[0].Index].Tag;

                    // We try to advice user in case selects some document extension that seems not be good to be opened with MS Word
                    if (formType.Equals(OKMDocumentType.TYPE_WORD))
                    {
                        if (!Util.isDocumentValidToOpenWithMSWord(doc))
                        {
                            String msg = String.Format(resources.GetString("word_document_extension_warning"), Util.getDocumentName(doc));
                            editFile = (MessageBox.Show(msg, "Warning", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK);
                        }
                    } 
                    else if (formType.Equals(OKMDocumentType.TYPE_EXCEL)) 
                    {
                        if (!Util.isDocumentValidToOpenWithMSExcel(doc))
                        {
                            String msg = String.Format(resources.GetString("excel_document_extension_warning"), Util.getDocumentName(doc));
                            editFile = (MessageBox.Show(msg, "Warning", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK);
                        }
                    }
                    else if (formType.Equals(OKMDocumentType.TYPE_POWER_POINT))
                    {
                        if (!Util.isDocumentValidToOpenWithMSPowerPoint(doc))
                        {
                            String msg = String.Format(resources.GetString("powerpoint_document_extension_warning"), Util.getDocumentName(doc));
                            editFile = (MessageBox.Show(msg, "Warning", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK);
                        }
                    }
                    else if (formType.Equals(OKMDocumentType.TYPE_VISIO))
                    {
                        if (!Util.isDocumentValidToOpenWithMSVisio(doc))
                        {
                            String msg = String.Format(resources.GetString("visio_document_extension_warning"), Util.getDocumentName(doc));
                            editFile = (MessageBox.Show(msg, "Warning", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK);
                        }
                    }

                    if (editFile)
                    {
                        MSOpenKMCore.bean.OKMDocument oKMDocument = DocumentLogic.checkoutDocument(doc, formType, configXML.getHost(), configXML.getUser(), configXML.getPassword());
                        if (oKMDocument != null)
                        {
                            documentXML.add(oKMDocument);
                            object missingValue = Type.Missing;
                            object readOnly = false;
                            object fileName = oKMDocument.getLocalFilename();

                            if (application is Word.Documents)
                            {
                                ((Word.Documents)application).Open(ref fileName,
                                    ref missingValue, ref readOnly, ref missingValue, ref missingValue, ref missingValue,
                                    ref missingValue, ref missingValue, ref missingValue, ref missingValue, ref missingValue,
                                    ref missingValue, ref missingValue, ref missingValue, ref missingValue, ref missingValue);
                            }
                            else if (application is Excel.Workbooks)
                            {
                                ((Excel.Workbooks)application).Open(oKMDocument.getLocalFilename(),
                                    missingValue, readOnly, missingValue, missingValue, missingValue,
                                    missingValue, missingValue, missingValue, missingValue, missingValue,
                                    missingValue, missingValue, missingValue, missingValue);
                            } 
                            else if (application is PowerPoint.Presentations)
                            {
                                ((PowerPoint.Presentations)application).Open(oKMDocument.getLocalFilename(), Office.MsoTriState.msoFalse, Office.MsoTriState.msoFalse,
                                  Office.MsoTriState.msoTrue);
                            }
                            else if (application is Visio.Documents)
                            {
                                ((Visio.Documents)application).Open(oKMDocument.getLocalFilename());
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                String errorMsg = "ExplorerForm - (edit_Click)\n" + ex.Message + "\n\n" + ex.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
            finally
            {
                Hide();
            }
        }
    }
}
