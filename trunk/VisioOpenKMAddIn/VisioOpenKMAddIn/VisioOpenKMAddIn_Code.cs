using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using MSOpenKMCore.config;
using MSOpenKMCore.form;
using MSOpenKMCore.bean;
using MSOpenKMCore.util;
using Office = Microsoft.Office.Core;
using Visio = Microsoft.Office.Interop.Visio;
using MSOpenKMCore.logic;
using System.IO;
using System.Runtime.InteropServices;
using stdole;
using Microsoft.Office.Core;
using System.Collections;

namespace VisioOpenKMAddIn
{
    public partial class VisioOpenKMAddIn
    {
        ComponentResourceManager resources = new ComponentResourceManager(typeof(VisioOpenKMAddIn));

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
        private bool flagZeroDocument = false;

        // Add tool bar
        private void addToolbar()
        {
            try
            {
                // Initialize vars;
                commandBarOption = new CommandBarOption();
                configXML = new ConfigXML();
                docXML = new DocumentXML();
                fileUtil = new FileUtil();

                // Initialize forms
                configurationForm = new ConfigurationForm();
                explorerForm = new ExplorerForm(this.Application, OKMDocumentType.TYPE_VISIO, configXML, docXML);
                treeForm = new TreeForm(this.Application, configXML);

                // VSTO API uses object-wrapped booleans
                object falseValue = false;
                object trueValue = true;

                // try getting command bar if it's yet created
                try
                {
                    commandBar = ((CommandBars)Application.CommandBars)[OPENKM_COMMANDBAR_NAME];

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
                commandBar = ((CommandBars)Application.CommandBars).Add(OPENKM_COMMANDBAR_NAME, Office.MsoBarPosition.msoBarTop, falseValue, trueValue);

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
                        Image bmpButton = new Bitmap(GetType(), "openkm.ico");
                        Image bmpMask = new Bitmap(GetType(), "openkm_mask.ico");
                        IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                        IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                        openKMButton.Picture = buttonIco;
                        openKMButton.Mask = maskIco;
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
                        Image bmpButton = new Bitmap(GetType(), "add_document.ico");
                        Image bmpMask = new Bitmap(GetType(), "add_document_mask.ico");
                        IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                        IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                        addButton.Picture = buttonIco;
                        addButton.Mask = maskIco;
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
                        Image bmpButton = new Bitmap(GetType(), "edit.ico");
                        Image bmpMask = new Bitmap(GetType(), "edit_mask.ico");
                        IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                        IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                        editButton.Picture = buttonIco;
                        editButton.Mask = maskIco;
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
                        Image bmpButton = new Bitmap(GetType(), "checkin.ico");
                        Image bmpMask = new Bitmap(GetType(), "checkin_mask.ico");
                        IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                        IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                        checkinButton.Picture = buttonIco;
                        checkinButton.Mask = maskIco;
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
                        Bitmap bmpMask = new Bitmap(GetType(), "cancel_checkout_mask.ico");
                        IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                        IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                        cancelCheckoutButton.Picture = buttonIco;
                        cancelCheckoutButton.Mask = maskIco;
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
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (addToolbar)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        // Save the tool bar position
        private void saveToolbarPosition()
        {
            try
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
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (saveToolbarPosition)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        // Load the tool bar position
        private void loadToolbarPosition()
        {
            try
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
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (loadToolbarPosition)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
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
            try
            {
                if (commandBarOption.isCheckin())
                {
                    if (MessageBox.Show(resources.GetString("sure_check_in"), resources.GetString("checkin"), MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                    {
                        Visio.Document activeDocument = Application.ActiveDocument;
                        String localFileName = activeDocument.FullName;
                        activeDocument.Save(); // save document
                        activeDocument.Close(); // close document
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
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (checkinButton_Click)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        private void cancelCheckoutButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            try
            {
                if (commandBarOption.isCancelCheckin())
                {
                    if (MessageBox.Show(resources.GetString("sure_cancel_checkout"), resources.GetString("cancelcheckout"), MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                    {

                        Visio.Document activeDocument = Application.ActiveDocument;
                        String localFileName = activeDocument.FullName;
                        activeDocument.Save(); // save document
                        activeDocument.Close(); // close document
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
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (cancelCheckoutButton_Click)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        // Help click
        private void helpButton_Click(Microsoft.Office.Core.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            System.Diagnostics.Process.Start("http://wiki.openkm.com/index.php/Microsoft_Office_Addin");
        }

        // show configuration form
        private void showConfigurationForm()
        {
            try
            {
                configurationForm.Show();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Show explorer form
        private void showExplorerForm()
        {
            try
            {
                if (commandBarOption.isEdit())
                {
                    explorerForm.Show();
                    explorerForm.startUp();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Show tree form
        private void showTreeForm()
        {
            try
            {
                if (commandBarOption.isAdd())
                {
                    treeForm.Show();
                    treeForm.startUp();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Evaluate on creation opening, changing or activating document
        private void onDocumentCreatedOpenedChangedActivated() {
            try
            {
                if (Application.Documents.Count > 0)
                {
                    Visio.Document activeDocument = Application.ActiveDocument;
                    refreshIcons(activeDocument.FullName);
                }
                else
                {
                    refreshIcons(null);
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Document created event
        private void Application_DocumentCreated(Visio.Document doc)
        {
            try
            {
                onDocumentCreatedOpenedChangedActivated();
            }
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (Application_DocumentCreated)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        // Document opened event
        private void Application_DocumentOpened(Visio.Document doc)
        {
            try
            {
                onDocumentCreatedOpenedChangedActivated();
            }
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (Application_DocumentOpened)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        // Document change event
        private void Application_DocumentChanged(Visio.Document doc)
        {
            try
            {
                onDocumentCreatedOpenedChangedActivated();
            }
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (Application_DocumentChanged)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }
        
        // Window activation event
        private void Application_WindowActivated(Visio.Window Wn)
        {
            try
            {
                onDocumentCreatedOpenedChangedActivated();
            }
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (Application_WindowActivated)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        //Window close canceled event
        private void Application_WindowClosed(Visio.Window Wn)
        {
            try
            {
                // Document is still avaliable when window closed is fired, >1 to test at least one document after closing document
                if (Application.Documents.Count > 1)
                {
                    flagZeroDocument = false;
                    Visio.Document activeDocument = Application.ActiveDocument;
                    refreshIcons(activeDocument.FullName);
                }
                else
                {
                    flagZeroDocument = true;
                    refreshIcons(null);
                }
            }
            catch (Exception e)
            {
                String errorMsg = "VisioOpenKMAddIn - (Application_WindowCloseCanceled)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        // Refreshing icons
        private void refreshIcons(String localFileName)
        {
            try
            {
                docXML.refresh(); // Refresh document list
                if (localFileName != null && Application.Documents.Count > 0)
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
                }
                catch (Exception e)
                {
                }

                // Evaluate add icon
                if (commandBarOption.isAdd())
                {
                    Image bmpButton = new Bitmap(GetType(), "add_document.ico");
                    Image bmpMask = new Bitmap(GetType(), "add_document_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    addButton.Picture = buttonIco;
                    addButton.Mask = maskIco;
                }
                else
                {
                    Image bmpButton = new Bitmap(GetType(), "add_document_disabled.ico");
                    Image bmpMask = new Bitmap(GetType(), "add_document_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    addButton.Picture = buttonIco;
                    addButton.Mask = maskIco;
                }

                // Evaluate edit icon
                if (commandBarOption.isEdit())
                {
                    Image bmpButton = new Bitmap(GetType(), "edit.ico");
                    Image bmpMask = new Bitmap(GetType(), "edit_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    editButton.Picture = buttonIco;
                    editButton.Mask = maskIco;
                }
                else
                {
                    Image bmpButton = new Bitmap(GetType(), "edit_disabled.ico");
                    Image bmpMask = new Bitmap(GetType(), "edit_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    editButton.Picture = buttonIco;
                    editButton.Mask = maskIco;
                }

                // Evaluate checkin icon
                if (commandBarOption.isCheckin())
                {
                    Image bmpButton = new Bitmap(GetType(), "checkin.ico");
                    Image bmpMask = new Bitmap(GetType(), "checkin_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    checkinButton.Picture = buttonIco;
                    checkinButton.Mask = maskIco;
                }
                else
                {
                    Image bmpButton = new Bitmap(GetType(), "checkin_disabled.ico");
                    Image bmpMask = new Bitmap(GetType(), "checkin_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    checkinButton.Picture = buttonIco;
                    checkinButton.Mask = maskIco;
                }

                // Evaluate cancelcheckin icon
                if (commandBarOption.isCancelCheckin())
                {
                    Image bmpButton = new Bitmap(GetType(), "cancel_checkout.ico");
                    Image bmpMask = new Bitmap(GetType(), "cancel_checkout_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    cancelCheckoutButton.Picture = buttonIco;
                    cancelCheckoutButton.Mask = maskIco;
                }
                else
                {
                    Image bmpButton = new Bitmap(GetType(), "cancel_checkout_disabled.ico");
                    Image bmpMask = new Bitmap(GetType(), "cancel_checkout_mask.ico");
                    IPictureDisp buttonIco = ImageToPictureConverter.Convert(bmpButton);
                    IPictureDisp maskIco = ImageToPictureConverter.Convert(bmpMask);
                    cancelCheckoutButton.Picture = buttonIco;
                    cancelCheckoutButton.Mask = maskIco;
                }

                saveToolbarPosition(); //Save tool bar position ( there's a problem in shutdown );
                commandBar.Protection = Microsoft.Office.Core.MsoBarProtection.msoBarNoCustomize;
            }
            catch (COMException)
            {
                commandBar.Delete();
                addToolbar();
                loadToolbarPosition(); // After creating new tool bar might set in save position
                if (Application.Documents.Count > 0 && !flagZeroDocument)
                {
                    Visio.Document activeDocument = Application.ActiveDocument;
                    refreshIcons(activeDocument.FullName);
                }
                else
                {
                    flagZeroDocument = false;
                    refreshIcons(null);
                }
                saveToolbarPosition();//Save tool bar position ( there's a problem in shutdown );
            }
            catch (Exception e)
            {
                throw e;
            }
        }
    }
}

