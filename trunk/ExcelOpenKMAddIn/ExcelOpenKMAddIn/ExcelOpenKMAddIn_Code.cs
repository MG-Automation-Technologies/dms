using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using MSOpenKMCore.config;
using MSOpenKMCore.form;
using MSOpenKMCore.bean;
using MSOpenKMCore.util;
using Office = Microsoft.Office.Core;
using Excel = Microsoft.Office.Interop.Excel;
using MSOpenKMCore.logic;
using System.IO;
using System.Runtime.InteropServices;
using stdole;
using Microsoft.Office.Core;
using System.Collections;

namespace ExcelOpenKMAddIn
{
    partial class ExcelOpenKMAddIn
    {
        ComponentResourceManager resources = new ComponentResourceManager(typeof(ExcelOpenKMAddIn));

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
            try
            {
                // Initialize vars;
                commandBarOption = new CommandBarOption();
                configXML = new ConfigXML();
                docXML = new DocumentXML();
                fileUtil = new FileUtil();

                // Initialize forms
                configurationForm = new ConfigurationForm();
                explorerForm = new ExplorerForm(this.Application, OKMDocumentType.TYPE_WORD, configXML, docXML);
                treeForm = new TreeForm(this.Application, configXML);

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
                    //loadToolbarPosition();

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
                        //addButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(addButton_Click);
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
                        //editButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(editButton_Click);
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
                        //checkinButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(checkinButton_Click);
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
                        //cancelCheckoutButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(cancelCheckoutButton_Click);
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
                        //helpButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(helpButton_Click);
                    }

                    // We need to find this toolbar later, so protect it from user changes
                    commandBar.Protection = Microsoft.Office.Core.MsoBarProtection.msoBarNoCustomize;
                    commandBar.Visible = true;
                }
            }
            catch (Exception e)
            {
                String errorMsg = "WordOpenKMAddIn - (addToolbar)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        private void openKMButton_Click(Office.CommandBarButton Ctrl, ref bool CancelDefault)
        {
            showConfigurationForm();
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
    }
}
