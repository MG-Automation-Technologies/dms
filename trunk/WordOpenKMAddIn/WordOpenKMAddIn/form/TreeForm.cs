using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MSOpenKMCore.config;
using MSOpenKMCore.ws;
using WordOpenKMAddIn.bean;
using WordOpenKMAddIn.config;
using WordOpenKMAddIn.logic;
using WordOpenKMAddIn.util;
using Word = Microsoft.Office.Interop.Word;

namespace WordOpenKMAddIn.form
{
    public partial class TreeForm : Form
    {
        private ConfigXML configXML = null;
        private ImageUtil imageList = null;
        private String token = "";
        private TreeNode rootNode = null;
        private TreeNode actualNode = null;
        private TreeNodeMouseClickEventHandler treeNodeMouseClickEventHandler;

        private OKMAuthService authService = null;
        private OKMRepositoryService repositoryService = null;
        private OKMFolderService folderService = null;

        private Word.Application application = null;

        public TreeForm(Word.Application application, ConfigXML configXML)
        {
            ComponentResourceManager resources = new ComponentResourceManager(typeof(TreeForm));

            this.application = application;
            this.configXML = configXML;
            imageList = new ImageUtil();
            initServices();

            // Initialize component
            InitializeComponent();

            // Centering form
            this.CenterToParent();

            // Translations
            accept.Text = resources.GetString("accept");
            cancel.Text = resources.GetString("cancel");
            this.Text = resources.GetString("treenavigator");

            // By default accept button is always disabled
            accept.Enabled = false;

            // Adding click handler
            treeNodeMouseClickEventHandler = new TreeNodeMouseClickEventHandler(nodeMouseClick);
            tree.NodeMouseClick += treeNodeMouseClickEventHandler;
        }

        // Init services
        private void initServices()
        {
            authService = new OKMAuthService(configXML.getHost());
            repositoryService = new OKMRepositoryService(configXML.getHost());
            folderService = new OKMFolderService(configXML.getHost());
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

        // Starting up drawing folder tree
        public void startUp()
        {
            try
            {
                // Default buttons enabled
                enableDefaultButtons();

                // OpenKM authentication
                token = authService.login(configXML.getUser(), configXML.getPassword());

                // Setting the image list
                tree.ImageList = imageList.get();

                // Suppress repainting the TreeView until all the objects have been created.
                tree.BeginUpdate();

                // Clear the TreeView each time the method is called.
                tree.Nodes.Clear();

                // Draws the root folder
                getRootFolder();

                // Draws the root folder childs
                getChilds();

                // Expand all nodes
                tree.ExpandAll();

                // Begin repainting the TreeView.
                tree.EndUpdate();

                // Evaluate enabled buttons
                evaluateEnabledButtonByPermissions((folder)actualNode.Tag);

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

                folder folderNode = (folder)e.Node.Tag;
                actualNode = e.Node;

                // Gets folder childs
                getChilds();
                actualNode.ExpandAll();

                // Evaluate enabled buttons
                evaluateEnabledButtonByPermissions((folder)actualNode.Tag);

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

        // Evaluates enabled buttons by permisions
        private void evaluateEnabledButtonByPermissions(folder fld)
        {
            if (Util.hasWritePermission(fld))
            {
                accept.Enabled = true;
            }
            else
            {
                accept.Enabled = false;
            }
        }

        // Disables als buttons
        private void disableAllButtons()
        {
            accept.Enabled = false;
            cancel.Enabled = false;
        }

        // Defaults buttons enabled view
        private void enableDefaultButtons()
        {
            accept.Enabled = false;
            cancel.Enabled = true;
        }

        // Cancel button
        private void cancel_Click(object sender, EventArgs e)
        {
            Hide();
        }

        //Accept button
        private void accept_Click(object sender, EventArgs e)
        {
            disableAllButtons();
            Word._Document activeDocument = application.ActiveDocument;
            activeDocument.Save(); // Saves document
            String localFileName = activeDocument.FullName;
            String docPath = Util.getOpenKMPath(localFileName, (folder)actualNode.Tag);
            // Must save a temporary file to be uploaded
            File.Copy(localFileName, localFileName + "_TEMP");
            localFileName = localFileName + "_TEMP";
            DocumentLogic.create(localFileName, docPath, configXML.getHost(), configXML.getUser(), configXML.getPassword());
            File.Delete(localFileName); // Deletes temporary file
            Hide();
        }
    }
}
