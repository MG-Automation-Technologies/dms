using System;
using System.Runtime.InteropServices;
using System.ComponentModel;
using System.Drawing;
using stdole;
using System.Windows.Forms;

using MSOpenKMCore.util;
using MSOpenKMCore.config;
using MSOpenKMCore.bean;

namespace OpenKMWordAddIn
{
    /// <summary>
    ///   Add-in Express Add-in Module
    /// </summary>
    [GuidAttribute("C219650F-7652-4263-8721-DC4B5831821C"), ProgId("OpenKMWordAddIn.AddinModule")]
    public class AddinModule : AddinExpress.MSO.ADXAddinModule
    {
        private ImageToolBar imageToolBar;
        private ImageLargeToolbar imageLargeToolBar;
        private OpenkMAddIn openkmAddIn;
        private CommandBarOption commandBarOption = null;
        private AddinExpress.MSO.ADXCommandBar commandBar;
        private AddinExpress.MSO.ADXCommandBarButton configureButton;
        private AddinExpress.MSO.ADXCommandBarButton addButton;
        private AddinExpress.MSO.ADXCommandBarButton editButton;
        private AddinExpress.MSO.ADXCommandBarButton checkinButton;
        private AddinExpress.MSO.ADXCommandBarButton cancelCheckoutButton;
        private AddinExpress.MSO.ADXCommandBarButton helpButton;
        private AddinExpress.MSO.ADXWordAppEvents adxWordEvents;
        private AddinExpress.MSO.ADXRibbonTab ribbonTab;
        private AddinExpress.MSO.ADXRibbonGroup ribbonGroup;
        private AddinExpress.MSO.ADXRibbonBox ribbonBox;
        private AddinExpress.MSO.ADXRibbonButton configurationRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton addRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton editRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton checkinRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton cancelCheckoutRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton helpRibbonButton;
        private AddinExpress.MSO.ADXRibbonSplitButton ribbonSplitButton;
        private AddinExpress.MSO.ADXRibbonMenu ribbonMenu;
        private ComponentResourceManager resources = new ComponentResourceManager(typeof(AddinModule));

        public AddinModule()
        {
            InitializeComponent();

            // Please add any initialization code to the AddinInitialize event handler
            commandBarOption = new CommandBarOption();
            imageToolBar = new ImageToolBar();
            imageLargeToolBar = new ImageLargeToolbar();
            openkmAddIn = new OpenkMAddIn();

            // Buttons
            this.configureButton.ImageList = imageToolBar.get();
            this.addButton.ImageList = imageToolBar.get();
            this.editButton.ImageList = imageToolBar.get();
            this.checkinButton.ImageList = imageToolBar.get();
            this.cancelCheckoutButton.ImageList = imageToolBar.get();
            this.configureButton.Image = ImageToolBar.IMG_TOOLBAR_OPENKM;
            this.addButton.Image = ImageToolBar.IMG_TOOLBAR_ADD;
            this.editButton.Image = ImageToolBar.IMG_TOOLBAR_EDIT;
            this.checkinButton.Image = ImageToolBar.IMG_TOOLBAR_CHECKIN;
            this.cancelCheckoutButton.Image = ImageToolBar.IMG_TOOLBAR_CANCELCHECKOUT;
            this.addButton.ImageTransparentColor = Color.Black;
            this.editButton.ImageTransparentColor = Color.Black;
            this.checkinButton.ImageTransparentColor = Color.Black;
            this.cancelCheckoutButton.ImageTransparentColor = Color.Black;
            this.configureButton.ImageTransparentColor = Color.White;
            // Internationalization
            this.configureButton.Caption = resources.GetString("configuration");
            this.configureButton.TooltipText = resources.GetString("configuration");
            this.configureButton.DescriptionText = resources.GetString("configuration");
            this.addButton.TooltipText = resources.GetString("add");
            this.addButton.DescriptionText = resources.GetString("add");
            this.editButton.TooltipText = resources.GetString("edit");
            this.editButton.DescriptionText = resources.GetString("edit");
            this.checkinButton.TooltipText = resources.GetString("checkin");
            this.checkinButton.DescriptionText = resources.GetString("checkin");
            this.cancelCheckoutButton.TooltipText = resources.GetString("cancelcheckout");
            this.cancelCheckoutButton.DescriptionText = resources.GetString("cancelcheckout");
            this.helpButton.TooltipText = resources.GetString("help");
            this.helpButton.DescriptionText = resources.GetString("help");
            // Click
            this.configureButton.Click += new AddinExpress.MSO.ADXClick_EventHandler(configureButton_Click);
            this.addButton.Click += new AddinExpress.MSO.ADXClick_EventHandler(addButton_Click);
            this.editButton.Click += new AddinExpress.MSO.ADXClick_EventHandler(editButton_Click);
            this.checkinButton.Click += new AddinExpress.MSO.ADXClick_EventHandler(checkinButton_Click);
            this.helpButton.Click += new AddinExpress.MSO.ADXClick_EventHandler(helpButton_Click);
            this.cancelCheckoutButton.Click += new AddinExpress.MSO.ADXClick_EventHandler(cancelCheckoutButton_Click); 

            // Ribbons
            this.configurationRibbonButton.ImageList = imageToolBar.get();
            this.addRibbonButton.ImageList = imageToolBar.get();
            this.editRibbonButton.ImageList = imageToolBar.get();
            this.checkinRibbonButton.ImageList = imageToolBar.get();
            this.cancelCheckoutRibbonButton.ImageList = imageToolBar.get();
            imageLargeToolBar.get().ImageSize = new Size(32, 32);
            this.ribbonSplitButton.ImageList = imageLargeToolBar.get();
            this.configurationRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_OPENKM;
            this.addRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_ADD;
            this.editRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_EDIT;
            this.checkinRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_CHECKIN;
            this.cancelCheckoutRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_CANCELCHECKOUT;
            this.ribbonSplitButton.Image = ImageLargeToolbar.IMG_TOOLBAR_LOGO;
            this.configurationRibbonButton.ImageTransparentColor = Color.White;
            this.addRibbonButton.ImageTransparentColor = Color.Black;
            this.editRibbonButton.ImageTransparentColor = Color.Black;
            this.checkinRibbonButton.ImageTransparentColor = Color.Black;
            this.cancelCheckoutRibbonButton.ImageTransparentColor = Color.Black;
            this.ribbonSplitButton.ImageTransparentColor = Color.White;
            // Internationalization
            this.configurationRibbonButton.Caption = resources.GetString("configuration");
            this.addRibbonButton.Caption = resources.GetString("add");
            this.editRibbonButton.Caption = resources.GetString("edit");
            this.checkinRibbonButton.Caption = resources.GetString("checkin");
            this.cancelCheckoutRibbonButton.Caption = resources.GetString("cancelcheckout");
            this.helpRibbonButton.Caption = resources.GetString("help");
            // Click
            this.configurationRibbonButton.OnClick += new AddinExpress.MSO.ADXRibbonOnAction_EventHandler(configurationRibbonButton_OnClick);
            this.addRibbonButton.OnClick += new AddinExpress.MSO.ADXRibbonOnAction_EventHandler(addRibbonButton_OnClick);
            this.editRibbonButton.OnClick += new AddinExpress.MSO.ADXRibbonOnAction_EventHandler(editRibbonButton_OnClick);
            this.checkinRibbonButton.OnClick += new AddinExpress.MSO.ADXRibbonOnAction_EventHandler(checkinRibbonButton_OnClick);
            this.cancelCheckoutRibbonButton.OnClick += new AddinExpress.MSO.ADXRibbonOnAction_EventHandler(cancelcheckoutRibbonButton_OnClick);
            this.helpRibbonButton.OnClick += new AddinExpress.MSO.ADXRibbonOnAction_EventHandler(helpRibbonButton_OnClick);

            adxWordEvents.DocumentChange += new EventHandler(adxWordEvents_DocumentChange);
        }

        void helpRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            System.Diagnostics.Process.Start("http://wiki.openkm.com/index.php/Microsoft_Office_Addin");
        }

        void cancelcheckoutRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isCancelCheckin())
            {
                openkmAddIn.cancelCheckout(WordApp.ActiveDocument);
            }
        }

        void checkinRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isCheckin())
            {
                openkmAddIn.checkin(WordApp.ActiveDocument);
            }
        }

        void editRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isEdit())
            {
                openkmAddIn.showExplorerForm(WordApp.Documents);
            }
        }

        void addRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isAdd())
            {
                openkmAddIn.showTreeForm(WordApp.ActiveDocument);
            }
        }

        void configurationRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            openkmAddIn.showConfigurationForm();
        }

        void cancelCheckoutButton_Click(object sender)
        {
            if (commandBarOption.isCancelCheckin())
            {
                openkmAddIn.cancelCheckout(WordApp.ActiveDocument);
            }
        }

        void helpButton_Click(object sender)
        {
            System.Diagnostics.Process.Start("http://wiki.openkm.com/index.php/Microsoft_Office_Addin");
        }

        void addButton_Click(object sender)
        {
            if (commandBarOption.isAdd())
            {
                openkmAddIn.showTreeForm(WordApp.ActiveDocument);
            }
        }

        void checkinButton_Click(object sender)
        {
            if (commandBarOption.isCheckin())
            {
                openkmAddIn.checkin(WordApp.ActiveDocument);
            }
        }

        void editButton_Click(object sender)
        {
            if (commandBarOption.isEdit())
            {
                openkmAddIn.showExplorerForm(WordApp.Documents);
            }
        }

        void configureButton_Click(object sender)
        {
            openkmAddIn.showConfigurationForm();
        }

        void adxWordEvents_DocumentChange(object sender, EventArgs e)
        {
            try
            {
                if (WordApp.Documents.Count > 0)
                {
                    refreshIcons(WordApp.ActiveDocument.FullName);
                }
                else
                {
                    refreshIcons(null);
                }
            }
            catch (Exception ex)
            {
                String errorMsg = "OpenKMWordAddIn - (Application_DocumentChange)\n" + ex.Message + "\n\n" + ex.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }
 
        #region Component Designer generated code
        /// <summary>
        /// Required by designer
        /// </summary>
        private System.ComponentModel.IContainer components;
 
        /// <summary>
        /// Required by designer support - do not modify
        /// the following method
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.commandBar = new AddinExpress.MSO.ADXCommandBar(this.components);
            this.configureButton = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.addButton = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.editButton = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.checkinButton = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.cancelCheckoutButton = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.helpButton = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.adxWordEvents = new AddinExpress.MSO.ADXWordAppEvents(this.components);
            this.ribbonTab = new AddinExpress.MSO.ADXRibbonTab(this.components);
            this.ribbonGroup = new AddinExpress.MSO.ADXRibbonGroup(this.components);
            this.ribbonBox = new AddinExpress.MSO.ADXRibbonBox(this.components);
            this.ribbonSplitButton = new AddinExpress.MSO.ADXRibbonSplitButton(this.components);
            this.ribbonMenu = new AddinExpress.MSO.ADXRibbonMenu(this.components);
            this.configurationRibbonButton = new AddinExpress.MSO.ADXRibbonButton(this.components);
            this.addRibbonButton = new AddinExpress.MSO.ADXRibbonButton(this.components);
            this.editRibbonButton = new AddinExpress.MSO.ADXRibbonButton(this.components);
            this.checkinRibbonButton = new AddinExpress.MSO.ADXRibbonButton(this.components);
            this.cancelCheckoutRibbonButton = new AddinExpress.MSO.ADXRibbonButton(this.components);
            this.helpRibbonButton = new AddinExpress.MSO.ADXRibbonButton(this.components);
            // 
            // commandBar
            // 
            this.commandBar.CommandBarName = "OpenKM Bar";
            this.commandBar.CommandBarTag = "0b952d78-86de-4dc8-8da2-aa405bd4a9be";
            this.commandBar.Controls.Add(this.configureButton);
            this.commandBar.Controls.Add(this.addButton);
            this.commandBar.Controls.Add(this.editButton);
            this.commandBar.Controls.Add(this.checkinButton);
            this.commandBar.Controls.Add(this.cancelCheckoutButton);
            this.commandBar.Controls.Add(this.helpButton);
            this.commandBar.UpdateCounter = 7;
            this.commandBar.UseForRibbon = true;
            // 
            // configureButton
            // 
            this.configureButton.Caption = "Configure";
            this.configureButton.ControlTag = "1e299dda-6709-4366-b0ad-0df0ef33c5e6";
            this.configureButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.configureButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIconAndCaption;
            this.configureButton.Tag = "Configure OpenKM";
            this.configureButton.UpdateCounter = 6;
            // 
            // addButton
            // 
            this.addButton.Caption = "addButton";
            this.addButton.ControlTag = "5a17fe7d-02ba-47df-bf98-dc16245d8966";
            this.addButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.addButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.addButton.Tag = "addButton";
            this.addButton.UpdateCounter = 3;
            // 
            // editButton
            // 
            this.editButton.Caption = "editButton";
            this.editButton.ControlTag = "0c6f517e-cd9e-4b1a-b7eb-cce13e9b782c";
            this.editButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.editButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.editButton.Tag = "editButton";
            this.editButton.UpdateCounter = 11;
            // 
            // checkinButton
            // 
            this.checkinButton.Caption = "checkinButton";
            this.checkinButton.ControlTag = "4f845e46-f6b2-4986-8852-9335534cc19a";
            this.checkinButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.checkinButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.checkinButton.Tag = "checkinButton";
            this.checkinButton.UpdateCounter = 3;
            // 
            // cancelCheckoutButton
            // 
            this.cancelCheckoutButton.Caption = "cancelCheckoutButton";
            this.cancelCheckoutButton.ControlTag = "8f357dd5-1c1a-4e4d-afb2-b0f44f7cabfb";
            this.cancelCheckoutButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.cancelCheckoutButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.cancelCheckoutButton.Tag = "cancelCheckoutButton";
            this.cancelCheckoutButton.UpdateCounter = 3;
            // 
            // helpButton
            // 
            this.helpButton.Caption = "helpButton";
            this.helpButton.ControlTag = "6f44b48b-ebee-492c-8856-cdbb989d97db";
            this.helpButton.FaceID = 984;
            this.helpButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.helpButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.helpButton.Tag = "helpButton";
            this.helpButton.UpdateCounter = 4;
            // 
            // ribbonTab
            // 
            this.ribbonTab.Caption = "OpenKM";
            this.ribbonTab.Controls.Add(this.ribbonGroup);
            this.ribbonTab.Id = "adxRibbonTab_08381d1c02bf432391f0dfe1ef65d510";
            this.ribbonTab.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // ribbonGroup
            // 
            this.ribbonGroup.Caption = "OpenKM tools";
            this.ribbonGroup.Controls.Add(this.ribbonBox);
            this.ribbonGroup.Id = "adxRibbonGroup_5ccf1332a1c4437d8775f2a045fdb10a";
            this.ribbonGroup.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.ribbonGroup.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // ribbonBox
            // 
            this.ribbonBox.Controls.Add(this.ribbonSplitButton);
            this.ribbonBox.Id = "adxRibbonBox_488573399981470b89b0404d005365fa";
            this.ribbonBox.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // ribbonSplitButton
            // 
            this.ribbonSplitButton.Controls.Add(this.ribbonMenu);
            this.ribbonSplitButton.Id = "adxRibbonSplitButton_fd8ed0f7957e470d98ab0938222fb823";
            this.ribbonSplitButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.ribbonSplitButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            this.ribbonSplitButton.Size = AddinExpress.MSO.ADXRibbonXControlSize.Large;
            // 
            // ribbonMenu
            // 
            this.ribbonMenu.Controls.Add(this.configurationRibbonButton);
            this.ribbonMenu.Controls.Add(this.addRibbonButton);
            this.ribbonMenu.Controls.Add(this.editRibbonButton);
            this.ribbonMenu.Controls.Add(this.checkinRibbonButton);
            this.ribbonMenu.Controls.Add(this.cancelCheckoutRibbonButton);
            this.ribbonMenu.Controls.Add(this.helpRibbonButton);
            this.ribbonMenu.Id = "adxRibbonMenu_a94f8c0efc504284bcd5fb337db7bbed";
            this.ribbonMenu.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.ribbonMenu.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // configurationRibbonButton
            // 
            this.configurationRibbonButton.Caption = "Configuration";
            this.configurationRibbonButton.Id = "adxRibbonButton_64cd0492a5534dafb325c7282c95bd5f";
            this.configurationRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.configurationRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // addRibbonButton
            // 
            this.addRibbonButton.Caption = "Add";
            this.addRibbonButton.Id = "adxRibbonButton_812447ea4e0e406cbd5832258beae26e";
            this.addRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.addRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // editRibbonButton
            // 
            this.editRibbonButton.Caption = "Edit";
            this.editRibbonButton.Id = "adxRibbonButton_2d25925c80d3457c8d00cd8710aa10cb";
            this.editRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.editRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // checkinRibbonButton
            // 
            this.checkinRibbonButton.Caption = "Checkin";
            this.checkinRibbonButton.Id = "adxRibbonButton_0c8960c8357a4598903251f0bd5372a7";
            this.checkinRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.checkinRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // cancelCheckoutRibbonButton
            // 
            this.cancelCheckoutRibbonButton.Caption = "Cancel chekcout";
            this.cancelCheckoutRibbonButton.Id = "adxRibbonButton_7507b93657f94901a291c165c38ff660";
            this.cancelCheckoutRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.cancelCheckoutRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // helpRibbonButton
            // 
            this.helpRibbonButton.Caption = "Help";
            this.helpRibbonButton.Id = "adxRibbonButton_69f5891a85524e7d8810c5b7fd10267b";
            this.helpRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.helpRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrWordDocument;
            // 
            // AddinModule
            // 
            this.AddinName = "OpenKMWordAddIn";
            this.SupportedApps = AddinExpress.MSO.ADXOfficeHostApp.ohaWord;

        }
        #endregion
 
        #region Add-in Express automatic code
 
        // Required by Add-in Express - do not modify
        // the methods within this region
 
        public override System.ComponentModel.IContainer GetContainer()
        {
            if (components == null)
                components = new System.ComponentModel.Container();
            return components;
        }
 
        [ComRegisterFunctionAttribute]
        public static void AddinRegister(Type t)
        {
            AddinExpress.MSO.ADXAddinModule.ADXRegister(t);
        }
 
        [ComUnregisterFunctionAttribute]
        public static void AddinUnregister(Type t)
        {
            AddinExpress.MSO.ADXAddinModule.ADXUnregister(t);
        }
 
        public override void UninstallControls()
        {
            base.UninstallControls();
        }

        #endregion

        public Word._Application WordApp
        {
            get
            {
                return (HostApplication as Word._Application);
            }
        }

        // Refreshing icons
        private void refreshIcons(String localFileName)
        {
            try
            {
                DocumentXML docXML = openkmAddIn.getDocXML();
                docXML.refresh(); // Refresh document list
                if (localFileName != null && WordApp.Documents.Count > 0)
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

                // Evaluate add icon
                if (commandBarOption.isAdd())
                {
                    addButton.Image = ImageToolBar.IMG_TOOLBAR_ADD;
                    addRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_ADD;
                    addRibbonButton.Enabled = true;
                }
                else
                {
                    addButton.Image = ImageToolBar.IMG_TOOLBAR_ADD_DISABLED;
                    addRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_ADD_DISABLED;
                    addRibbonButton.Enabled = false;
                }

                // Evaluate edit icon
                if (commandBarOption.isEdit())
                {
                    editButton.Image = ImageToolBar.IMG_TOOLBAR_EDIT;
                    editRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_EDIT;
                    editRibbonButton.Enabled = true;
                }
                else
                {
                    editButton.Image = ImageToolBar.IMG_TOOLBAR_EDIT_DISABLED;
                    editRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_EDIT_DISABLED;
                    editRibbonButton.Enabled = false;
                }

                // Evaluate checkin icon
                if (commandBarOption.isCheckin())
                {
                    checkinButton.Image = ImageToolBar.IMG_TOOLBAR_CHECKIN;
                    checkinRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_CHECKIN;
                    checkinRibbonButton.Enabled = true;
                }
                else
                {
                    checkinButton.Image = ImageToolBar.IMG_TOOLBAR_CHECKIN_DISABLED;
                    checkinRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_CHECKIN_DISABLED;
                    checkinRibbonButton.Enabled = false;
                }

                // Evaluate cancelcheckin icon
                if (commandBarOption.isCancelCheckin())
                {
                    cancelCheckoutButton.Image = ImageToolBar.IMG_TOOLBAR_CANCELCHECKOUT;
                    cancelCheckoutRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_CANCELCHECKOUT;
                    cancelCheckoutRibbonButton.Enabled = true;
                }
                else
                {
                    cancelCheckoutButton.Image = ImageToolBar.IMG_TOOLBAR_CANCELCHECKOUT_DISABLED;
                    cancelCheckoutRibbonButton.Image = ImageToolBar.IMG_TOOLBAR_CANCELCHECKOUT_DISABLED;
                    cancelCheckoutRibbonButton.Enabled = false;
                }
            }
            catch (COMException)
            {
                // Not trowed
            }
            catch (Exception e)
            {
                throw e;
            }
        } 
    }
}

