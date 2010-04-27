using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using MSOpenKMCore.config;
using MSOpenKMCore.form;
using WordOpenKMAddIn.bean;
using WordOpenKMAddIn.config;
using WordOpenKMAddIn.form;
using WordOpenKMAddIn.util;
using Office = Microsoft.Office.Core;
using Word = Microsoft.Office.Interop.Word;
using WordOpenKMAddIn.logic;
using System.IO;

namespace WordOpenKMAddIn
{
    public partial class WordOpenKMAddIn
    {
        ComponentResourceManager resources = new ComponentResourceManager(typeof(WordOpenKMAddIn));

        private const String OPENKM_COMMANDBAR_NAME = "OpenKM";
        private const String OPENKM_COMMANDBAR_BUTTON_CAPTION = "OpenKM";

        private Office.CommandBar commandBar;
        private Office.CommandBarButton openKMButton;
        private Office.CommandBarButton addButton;
        private Office.CommandBarButton editButton;
        private Office.CommandBarButton checkinButton;
        private Office.CommandBarButton cancelCheckoutButton;
        private Office.CommandBarButton helpButton;

        private ConfigurationForm configurationForm = null;
        private ExplorerForm explorerForm = null;
        private TreeForm treeForm = null;
        private CommandBarOption commandBarOption = null;
        private ConfigXML configXML = null;
        private DocumentXML docXML = null;
        private FileUtil fileUtil = null;

        // Add tool bar
        private void addToolbar()
        {
            // Initialize vars;
            commandBarOption = new CommandBarOption();
            configXML = new ConfigXML();
            docXML = new DocumentXML();
            fileUtil = new FileUtil();

            // VSTO API uses object-wrapped booleans
            object falseValue = false;
            object trueValue = true;

            // try getting command bar if it's yet created
            try
            {
                commandBar = Application.CommandBars[OPENKM_COMMANDBAR_NAME];

                // Remove any toolbar with the same name
                if (commandBar != null)
                {
                    commandBar.Delete();
                }
            }
            catch
            {
                // Begining - the CommandBar didn't exist
            }

            // Creating commenad new commandbar
            commandBar = Application.CommandBars.Add(OPENKM_COMMANDBAR_NAME, Office.MsoBarPosition.msoBarTop, falseValue, trueValue);

            if (commandBar != null)
            {
                // load saved toolbar position
                loadToolbarPosition();

                // Add openkm commandbar button
                openKMButton = (Office.CommandBarButton)commandBar.Controls.Add(Office.MsoControlType.msoControlButton, missing, missing, missing, falseValue);
                if (openKMButton != null)
                {
                    openKMButton.Style = Office.MsoButtonStyle.msoButtonIconAndCaption;
                    openKMButton.Caption = OPENKM_COMMANDBAR_BUTTON_CAPTION;
                    openKMButton.DescriptionText = resources.GetString("configuration");
                    openKMButton.TooltipText = resources.GetString("configuration");
                    Bitmap bmpButton = new Bitmap(GetType(), "openkm.ico");
                    openKMButton.Picture = new ToolbarPicture(bmpButton);
                    Bitmap bmpMask = new Bitmap(GetType(), "openkm_mask.ico");
                    openKMButton.Mask = new ToolbarPicture(bmpMask);
                    openKMButton.Tag = "OpenKMButton";

                    // Finally add the event handler and make sure the button is visible
                    openKMButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(openKMButton_Click);
                }

                // Add document commandbar button
                addButton = (Office.CommandBarButton)commandBar.Controls.Add(Office.MsoControlType.msoControlButton, missing, missing, missing, trueValue);
                if (addButton != null)
                {
                    addButton.Style = Office.MsoButtonStyle.msoButtonIcon;
                    addButton.DescriptionText = resources.GetString("add");
                    addButton.TooltipText = resources.GetString("add");
                    Bitmap bmpButton = new Bitmap(GetType(), "add_document.ico");
                    addButton.Picture = new ToolbarPicture(bmpButton);
                    Bitmap bmpMask = new Bitmap(GetType(), "add_document_mask.ico");
                    addButton.Mask = new ToolbarPicture(bmpMask);
                    addButton.Tag = "OpenKMCheckinButton";

                    // Finally add the event handler and make sure the button is visible
                    addButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(addButton_Click);
                }

                // Add edit commandbar button
                editButton = (Office.CommandBarButton)commandBar.Controls.Add(Office.MsoControlType.msoControlButton, missing, missing, missing, trueValue);
                if (editButton != null)
                {
                    editButton.Style = Office.MsoButtonStyle.msoButtonIcon;
                    editButton.DescriptionText = resources.GetString("edit");
                    editButton.TooltipText = resources.GetString("edit");
                    Bitmap bmpButton = new Bitmap(GetType(), "edit.ico");
                    editButton.Picture = new ToolbarPicture(bmpButton);
                    Bitmap bmpMask = new Bitmap(GetType(), "edit_mask.ico");
                    editButton.Mask = new ToolbarPicture(bmpMask);
                    editButton.Tag = "OpenKMEditButton";

                    // Finally add the event handler and make sure the button is visible
                    editButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(editButton_Click);
                }

                // Add checkin commandbar button
                checkinButton = (Office.CommandBarButton)commandBar.Controls.Add(Office.MsoControlType.msoControlButton, missing, missing, missing, trueValue);
                if (checkinButton != null)
                {
                    checkinButton.Style = Office.MsoButtonStyle.msoButtonIcon;
                    checkinButton.DescriptionText = resources.GetString("checkin");
                    checkinButton.TooltipText = resources.GetString("checkin");
                    Bitmap bmpButton = new Bitmap(GetType(), "checkin.ico");
                    checkinButton.Picture = new ToolbarPicture(bmpButton);
                    Bitmap bmpMask = new Bitmap(GetType(), "checkin_mask.ico");
                    checkinButton.Mask = new ToolbarPicture(bmpMask);
                    checkinButton.Tag = "OpenKMCheckinButton";

                    // Finally add the event handler and make sure the button is visible
                    checkinButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(checkinButton_Click);
                }

                // Add cancel checkin commandbar button
                cancelCheckoutButton = (Office.CommandBarButton)commandBar.Controls.Add(Office.MsoControlType.msoControlButton, missing, missing, missing, trueValue);
                if (cancelCheckoutButton != null)
                {
                    cancelCheckoutButton.Style = Office.MsoButtonStyle.msoButtonIcon;
                    cancelCheckoutButton.DescriptionText = resources.GetString("cancelcheckout");
                    cancelCheckoutButton.TooltipText = resources.GetString("cancelcheckout");
                    Bitmap bmpButton = new Bitmap(GetType(), "cancel_checkout.ico");
                    cancelCheckoutButton.Picture = new ToolbarPicture(bmpButton);
                    Bitmap bmpMask = new Bitmap(GetType(), "cancel_checkout_mask.ico");
                    cancelCheckoutButton.Mask = new ToolbarPicture(bmpMask);
                    cancelCheckoutButton.Tag = "OpenKMCancelCheckoutButton";

                    // Finally add the event handler and make sure the button is visible
                    cancelCheckoutButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(cancelCheckoutButton_Click);
                }

                // Add help coomand button
                helpButton = (Office.CommandBarButton)commandBar.Controls.Add(Office.MsoControlType.msoControlButton, missing, missing, missing, trueValue);
                if (helpButton != null)
                {
                    helpButton.Style = Office.MsoButtonStyle.msoButtonIcon;
                    helpButton.DescriptionText = resources.GetString("help");
                    helpButton.TooltipText = resources.GetString("help");
                    helpButton.FaceId = 984;
                    helpButton.Tag = "OpenKMHelpButton";

                    // Finally add the event handler and make sure the button is visible
                    helpButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(helpButton_Click);
                }

                // We need to find this toolbar later, so protect it from user changes
                commandBar.Protection = Microsoft.Office.Core.MsoBarProtection.msoBarNoCustomize;
                commandBar.Visible = true;
            }
        }

        // Save the tool bar position
        private void saveToolbarPosition()
        {
            if (commandBar != null)
            {
                Properties.Settings.Default.ToolbarPosition = (int)commandBar.Position;
                Properties.Settings.Default.ToolbarRowIndex = commandBar.RowIndex;
                Properties.Settings.Default.ToolbarTop = commandBar.Top;
                Properties.Settings.Default.ToolbarLeft = commandBar.Left;
                Properties.Settings.Default.ToolbarSaved = true;
                Properties.Settings.Default.Save();
            }
        }

        // Load the tool bar position
        private void loadToolbarPosition()
        {
            if (commandBar != null)
            {
                if (Properties.Settings.Default.ToolbarSaved)
                {
                    commandBar.Position = (Microsoft.Office.Core.MsoBarPosition)Properties.Settings.Default.ToolbarPosition;
                    commandBar.RowIndex = Properties.Settings.Default.ToolbarRowIndex;
                    commandBar.Top = Properties.Settings.Default.ToolbarTop;
                    commandBar.Left = Properties.Settings.Default.ToolbarLeft;
                }
            }
        }

        private void openKMButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            showConfigurationForm();
        }

        private void editButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            showExplorerForm();
        }

        private void addButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            showTreeForm();
        }

        private void checkinButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            if (commandBarOption.isCheckin())
            {
                if (MessageBox.Show("segur checkin", "Error", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                {
                    Word._Document activeDocument = Application.ActiveDocument;
                    object saveChanges = Word.WdSaveOptions.wdSaveChanges;
                    object missing = Type.Missing;
                    String localFileName = activeDocument.FullName;
                    activeDocument.Close(ref saveChanges, ref missing, ref missing); // Always we save document
                    docXML.refresh(); // Refresh document list
                    if (docXML.isOpenKMDocument(localFileName))
                    {
                        OKMDocument oKMDocument = docXML.getOpenKMDocument(localFileName);
                        docXML.remove(oKMDocument);
                        DocumentLogic.checkin(oKMDocument, configXML.getHost(), configXML.getUser(), configXML.getPassword());
                        if (File.Exists(localFileName))
                        {
                            File.Delete(localFileName);
                        }
                    }
                }
            }
        }

        private void cancelCheckoutButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            if (commandBarOption.isCancelCheckin())
            {
                if (MessageBox.Show("cancelar checkin", "Error", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                {

                    Word._Document activeDocument = Application.ActiveDocument;
                    object saveChanges = Word.WdSaveOptions.wdSaveChanges;
                    object missing = Type.Missing;
                    String localFileName = activeDocument.FullName;
                    activeDocument.Close(ref saveChanges, ref missing, ref missing); // Always we save document
                    docXML.refresh(); // Refresh document list
                    if (docXML.isOpenKMDocument(localFileName))
                    {
                        OKMDocument oKMDocument = docXML.getOpenKMDocument(localFileName);
                        docXML.remove(oKMDocument);
                        DocumentLogic.cancelCheckout(oKMDocument, configXML.getHost(), configXML.getUser(), configXML.getPassword());
                        if (File.Exists(localFileName))
                        {
                            File.Delete(localFileName);
                        }
                    }
                }
            }
        }

        private void helpButton_Click(Microsoft.Office.Core.CommandBarButton Ctrl, ref bool CancelDefault)
        {
        }

        // show configuration form
        private void showConfigurationForm()
        {
            if (configurationForm == null) {
                configurationForm = new ConfigurationForm();
            }
            configurationForm.Show();
        }

        // Show explorer form
        private void showExplorerForm()
        {
            if (commandBarOption.isEdit())
            {
                if (explorerForm == null)
                {
                    explorerForm = new ExplorerForm(this.Application, OKMDocumentType.TYPE_WORD, configXML);
                }
                explorerForm.Show();
                explorerForm.startUp();
            }
        }

        // Show tree form
        private void showTreeForm()
        {
            if (commandBarOption.isAdd())
            {
                if (treeForm == null)
                {
                    treeForm = new TreeForm(this.Application, configXML);
                }
                treeForm.Show();
                treeForm.startUp();
            }
        }

        // Document change event
        private void Application_DocumentChange()
        {
            if (Application.Documents.Count > 0)
            {
                MessageBox.Show("documento modificado", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);

                try
                {
                    Word._Document activeDocument = Application.ActiveDocument;
                    refreshIcons(activeDocument.FullName);
                }
                catch (Exception e)
                {
                    MessageBox.Show("error estraño a controlar ????", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                }
            }
            else
            {
                refreshIcons("");
            }
        }

        // Document before closing event
        private void Application_DocumentBeforeClose(Word.Document Doc, ref bool Cancel)
        {
            MessageBox.Show("se dispara antes de cerrar el word", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        // Window activation
        private void Application_WindowActivate(Word.Document Doc, Word.Window Wn)
        {
            //MessageBox.Show("activacion ventana", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        // Window disabled
        private void Application_WindowDeactivate(Word.Document Doc, Word.Window Wn)
        {
            //MessageBox.Show("desactivacion ventana", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        // Refreshing icons
        private void refreshIcons(String localFileName) 
        {
            docXML.refresh(); // Refresh document list
            if (Application.Documents.Count > 0)
            {
                if (docXML.isOpenKMDocument(localFileName))
                {
                    commandBarOption.setAdd(false);
                    commandBarOption.setEdit(false);
                    commandBarOption.setCheckin(true);
                    commandBarOption.setCancelCheckin(true);
                }
                else
                {
                    commandBarOption.setAdd(true);
                    commandBarOption.setEdit(true);
                    commandBarOption.setCheckin(false);
                    commandBarOption.setCancelCheckin(false);
                }
            }
            else
            {
                commandBarOption.setAdd(false);
                commandBarOption.setEdit(true);
                commandBarOption.setCheckin(false);
                commandBarOption.setCancelCheckin(false);
            }
                
            // Enable command bar changes
            try
            {
                commandBar.Protection = Microsoft.Office.Core.MsoBarProtection.msoBarNoProtection;
            } catch (Exception e){
                // Error
                MessageBox.Show("error estraño ????", "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }

            // Evaluate add icon
            if (commandBarOption.isAdd())
            {
                Bitmap bmpButton = new Bitmap(GetType(), "add_document.ico");
                addButton.Picture = new ToolbarPicture(bmpButton); ;
                Bitmap bmpMask = new Bitmap(GetType(), "add_document_mask.ico");
                addButton.Mask = new ToolbarPicture(bmpMask);
            }
            else
            {
                Bitmap bmpButton = new Bitmap(GetType(), "add_document_disabled.ico");
                addButton.Picture = new ToolbarPicture(bmpButton);
                Bitmap bmpMask = new Bitmap(GetType(), "add_document_mask.ico");
                addButton.Mask = new ToolbarPicture(bmpMask);
            }

            // Evaluate edit icon
            if (commandBarOption.isEdit())
            {
                Bitmap bmpButton = new Bitmap(GetType(), "edit.ico");
                editButton.Picture = new ToolbarPicture(bmpButton); ;
                Bitmap bmpMask = new Bitmap(GetType(), "edit_mask.ico");
                editButton.Mask = new ToolbarPicture(bmpMask);
            }
            else
            {
                Bitmap bmpButton = new Bitmap(GetType(), "edit_disabled.ico");
                editButton.Picture = new ToolbarPicture(bmpButton);
                Bitmap bmpMask = new Bitmap(GetType(), "edit_mask.ico");
                editButton.Mask = new ToolbarPicture(bmpMask);
            }

            // Evaluate checkin icon
            if (commandBarOption.isCheckin())
            {
                Bitmap bmpButton = new Bitmap(GetType(), "checkin.ico");
                checkinButton.Picture = new ToolbarPicture(bmpButton);
                Bitmap bmpMask = new Bitmap(GetType(), "checkin_mask.ico");
                checkinButton.Mask = new ToolbarPicture(bmpMask);
            }
            else
            {
                Bitmap bmpButton = new Bitmap(GetType(), "checkin_disabled.ico");
                checkinButton.Picture = new ToolbarPicture(bmpButton);
                Bitmap bmpMask = new Bitmap(GetType(), "checkin_mask.ico");
                checkinButton.Mask = new ToolbarPicture(bmpMask);
            }

            // Evaluate cancelcheckin icon
            if (commandBarOption.isCancelCheckin())
            {
                Bitmap bmpButton = new Bitmap(GetType(), "cancel_checkout.ico");
                cancelCheckoutButton.Picture = new ToolbarPicture(bmpButton);
                Bitmap bmpMask = new Bitmap(GetType(), "cancel_checkout_mask.ico");
                cancelCheckoutButton.Mask = new ToolbarPicture(bmpMask);
            }
            else
            {
                Bitmap bmpButton = new Bitmap(GetType(), "cancel_checkout_disabled.ico");
                cancelCheckoutButton.Picture = new ToolbarPicture(bmpButton);
                Bitmap bmpMask = new Bitmap(GetType(), "cancel_checkout_mask.ico");
                cancelCheckoutButton.Mask = new ToolbarPicture(bmpMask);
            }

            commandBar.Protection = Microsoft.Office.Core.MsoBarProtection.msoBarNoCustomize;
        }
    }
}
