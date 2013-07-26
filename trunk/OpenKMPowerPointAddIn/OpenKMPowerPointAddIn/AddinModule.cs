using System;
using System.Runtime.InteropServices;
using System.ComponentModel;
using System.Drawing;
using stdole;
using System.Windows.Forms;

using MSOpenKMCore.util;
using MSOpenKMCore.config;
using MSOpenKMCore.bean;

namespace OpenKMPowerPointAddIn
{
    /// <summary>
    ///   Add-in Express Add-in Module
    /// </summary>
    [GuidAttribute("2406ECF7-D8B6-42A2-8CE4-22D6147F5538"), ProgId("OpenKMPowerPointAddIn.AddinModule")]
    public class AddinModule : AddinExpress.MSO.ADXAddinModule
    {
        private ImageToolBar imageToolBar;
        private ImageLargeToolbar imageLargeToolBar;
        private CommandBarOption commandBarOption = null;
        private OpenKMAddIn openkmAddIn;
        private AddinExpress.MSO.ADXCommandBar commandBar;
        private AddinExpress.MSO.ADXCommandBarButton configureButton;
        private AddinExpress.MSO.ADXCommandBarButton addButton;
        private AddinExpress.MSO.ADXCommandBarButton editButton;
        private AddinExpress.MSO.ADXCommandBarButton checkinButton;
        private AddinExpress.MSO.ADXCommandBarButton cancelCheckoutButton;
        private AddinExpress.MSO.ADXCommandBarButton helpButton;
        private AddinExpress.MSO.ADXPowerPointAppEvents adxPowerPointEvents;
        private AddinExpress.MSO.ADXRibbonTab ribbonTab;
        private AddinExpress.MSO.ADXRibbonGroup ribbonGroup;
        private AddinExpress.MSO.ADXRibbonBox RibbonBox;
        private AddinExpress.MSO.ADXRibbonSplitButton ribbonSplitButton;
        private AddinExpress.MSO.ADXRibbonMenu ribbonMenu;
        private AddinExpress.MSO.ADXRibbonButton configurationRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton addRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton editRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton checkinRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton cancelCheckoutRibbonButton;
        private AddinExpress.MSO.ADXRibbonButton helpRibbonButton;
        private ComponentResourceManager resources = new ComponentResourceManager(typeof(AddinModule));

        public AddinModule()
        {
            InitializeComponent();

            // Please add any initialization code to the AddinInitialize event handler
            commandBarOption = new CommandBarOption();
            imageToolBar = new ImageToolBar();
            imageLargeToolBar = new ImageLargeToolbar();
            openkmAddIn = new OpenKMAddIn();

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

            adxPowerPointEvents.WindowActivate += new AddinExpress.MSO.ADXHostWindow_EventHandler(adxPowerPointEvents_WindowActivate);
            adxPowerPointEvents.PresentationClose += new AddinExpress.MSO.ADXHostActiveObject_EventHandler(adxPowerPointEvents_PresentationClose);
        }

        void helpRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            System.Diagnostics.Process.Start("http://wiki.openkm.com/index.php/Microsoft_Office_Addin");
        }

        void cancelcheckoutRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isCancelCheckin())
            {
                openkmAddIn.cancelCheckout(PowerPointApp.ActivePresentation);
            }
        }

        void checkinRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isCheckin())
            {
                openkmAddIn.checkin(PowerPointApp.ActivePresentation);
            }
        }

        void editRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isEdit())
            {
                openkmAddIn.showExplorerForm(PowerPointApp.Presentations);
            }
        }

        void addRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            if (commandBarOption.isAdd())
            {
                openkmAddIn.showTreeForm(PowerPointApp.ActivePresentation);
            }
        }

        void configurationRibbonButton_OnClick(object sender, AddinExpress.MSO.IRibbonControl control, bool pressed)
        {
            openkmAddIn.showConfigurationForm();
        }

        void adxPowerPointEvents_PresentationClose(object sender, object hostObj)
        {
            // Last document must disable checkin / cancel checkin icons
            if (PowerPointApp.Presentations.Count == 1)
            {
                refreshIcons(null);
            }
        }

        void adxPowerPointEvents_WindowActivate(object sender, object hostObj, object window)
        {
            try
            {
                if (hostObj != null)
                {
                    refreshIcons(PowerPointApp.ActivePresentation.FullName);
                }
                else
                {
                    refreshIcons(null);
                }
            }
            catch (Exception e)
            {
                String errorMsg = "OpenKMPowerPointAddIn - (Application_DocumentChange)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        void cancelCheckoutButton_Click(object sender)
        {
            if (commandBarOption.isCancelCheckin())
            {
                openkmAddIn.cancelCheckout(PowerPointApp.ActivePresentation);
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
                openkmAddIn.showTreeForm(PowerPointApp.ActivePresentation);
            }
        }

        void checkinButton_Click(object sender)
        {
            if (commandBarOption.isCheckin())
            {
                openkmAddIn.checkin(PowerPointApp.ActivePresentation);
            }
        }

        void editButton_Click(object sender)
        {
            if (commandBarOption.isEdit())
            {
                openkmAddIn.showExplorerForm(PowerPointApp.Presentations);
            }
        }

        void configureButton_Click(object sender)
        {
            openkmAddIn.showConfigurationForm();
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
            this.adxPowerPointEvents = new AddinExpress.MSO.ADXPowerPointAppEvents(this.components);
            this.ribbonTab = new AddinExpress.MSO.ADXRibbonTab(this.components);
            this.ribbonGroup = new AddinExpress.MSO.ADXRibbonGroup(this.components);
            this.RibbonBox = new AddinExpress.MSO.ADXRibbonBox(this.components);
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
            this.commandBar.CommandBarName = "adxCommandBar1";
            this.commandBar.CommandBarTag = "f0f07bc1-b6c6-49c7-a120-e46400b073e7";
            this.commandBar.Controls.Add(this.configureButton);
            this.commandBar.Controls.Add(this.addButton);
            this.commandBar.Controls.Add(this.editButton);
            this.commandBar.Controls.Add(this.checkinButton);
            this.commandBar.Controls.Add(this.cancelCheckoutButton);
            this.commandBar.Controls.Add(this.helpButton);
            this.commandBar.UpdateCounter = 3;
            this.commandBar.UseForRibbon = true;
            // 
            // configureButton
            // 
            this.configureButton.Caption = "Configure";
            this.configureButton.ControlTag = "865c124b-19a4-44cf-b51b-c09c4c417edb";
            this.configureButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.configureButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIconAndCaption;
            this.configureButton.UpdateCounter = 2;
            // 
            // addButton
            // 
            this.addButton.Caption = "Add";
            this.addButton.ControlTag = "084ad330-a06d-4aa2-92ba-8d709cd41eb4";
            this.addButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.addButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.addButton.UpdateCounter = 2;
            // 
            // editButton
            // 
            this.editButton.Caption = "Edit";
            this.editButton.ControlTag = "ec2c6ff6-21f9-4dee-a9d2-bf24d52c3353";
            this.editButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.editButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.editButton.UpdateCounter = 2;
            // 
            // checkinButton
            // 
            this.checkinButton.Caption = "Checkin";
            this.checkinButton.ControlTag = "57ffe431-e75b-44ed-b212-bdc296118177";
            this.checkinButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.checkinButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.checkinButton.UpdateCounter = 2;
            // 
            // cancelCheckoutButton
            // 
            this.cancelCheckoutButton.Caption = "Cancel checkout";
            this.cancelCheckoutButton.ControlTag = "5cd79938-1044-4887-8e53-60c167dc0137";
            this.cancelCheckoutButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.cancelCheckoutButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.cancelCheckoutButton.UpdateCounter = 3;
            // 
            // helpButton
            // 
            this.helpButton.Caption = "Help";
            this.helpButton.ControlTag = "fe7a2f02-b04e-4901-8e49-b8e5fa037787";
            this.helpButton.FaceID = 984;
            this.helpButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.helpButton.Style = AddinExpress.MSO.ADXMsoButtonStyle.adxMsoButtonIcon;
            this.helpButton.UpdateCounter = 3;
            // 
            // ribbonTab
            // 
            this.ribbonTab.Caption = "OpenKM";
            this.ribbonTab.Controls.Add(this.ribbonGroup);
            this.ribbonTab.Id = "adxRibbonTab_2da5e826a51b403ab937b858f1e02aa1";
            this.ribbonTab.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // ribbonGroup
            // 
            this.ribbonGroup.Caption = "OpenKM tools";
            this.ribbonGroup.Controls.Add(this.RibbonBox);
            this.ribbonGroup.Id = "adxRibbonGroup_a51148e204f14c849ec5f0d4913290f0";
            this.ribbonGroup.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.ribbonGroup.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // RibbonBox
            // 
            this.RibbonBox.Controls.Add(this.ribbonSplitButton);
            this.RibbonBox.Id = "adxRibbonBox_99865a6e69f44b7ab32935430b9ee8e5";
            this.RibbonBox.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // ribbonSplitButton
            // 
            this.ribbonSplitButton.Controls.Add(this.ribbonMenu);
            this.ribbonSplitButton.Id = "adxRibbonSplitButton_08ec12e635d44c87939527e2b35a09a1";
            this.ribbonSplitButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.ribbonSplitButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
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
            this.ribbonMenu.Id = "adxRibbonMenu_2f270dd0e42c4f149096f9c0ba205073";
            this.ribbonMenu.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.ribbonMenu.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // configurationRibbonButton
            // 
            this.configurationRibbonButton.Caption = "Configure";
            this.configurationRibbonButton.Id = "adxRibbonButton_856531045d04488994c0a4543143ca65";
            this.configurationRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.configurationRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // addRibbonButton
            // 
            this.addRibbonButton.Caption = "Add";
            this.addRibbonButton.Id = "adxRibbonButton_594652ed3a0c4788bdcccf7e70adf724";
            this.addRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.addRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // editRibbonButton
            // 
            this.editRibbonButton.Caption = "Edit";
            this.editRibbonButton.Id = "adxRibbonButton_9cce86e5bb754d12a27d8ee42813b79b";
            this.editRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.editRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // checkinRibbonButton
            // 
            this.checkinRibbonButton.Caption = "Checkin";
            this.checkinRibbonButton.Id = "adxRibbonButton_0d42fb72615d448c97ac617ba207dbb5";
            this.checkinRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.checkinRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // cancelCheckoutRibbonButton
            // 
            this.cancelCheckoutRibbonButton.Caption = "Cancel checkout";
            this.cancelCheckoutRibbonButton.Id = "adxRibbonButton_467bee6d33894d2e8607f28ccf9b6224";
            this.cancelCheckoutRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.cancelCheckoutRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // helpRibbonButton
            // 
            this.helpRibbonButton.Caption = "Help";
            this.helpRibbonButton.Id = "adxRibbonButton_b639797daee14ba99bb376757c5463a9";
            this.helpRibbonButton.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.helpRibbonButton.Ribbons = AddinExpress.MSO.ADXRibbons.msrPowerPointPresentation;
            // 
            // AddinModule
            // 
            this.AddinName = "OpenKMPowerPointAddIn";
            this.SupportedApps = AddinExpress.MSO.ADXOfficeHostApp.ohaPowerPoint;

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

        public PowerPoint._Application PowerPointApp
        {
            get
            {
                return (HostApplication as PowerPoint._Application);
            }
        }

        // Refreshing icons
        private void refreshIcons(String localFileName)
        {
            try
            {
                DocumentXML docXML = openkmAddIn.getDocXML();
                docXML.refresh(); // Refresh document list
                if (localFileName != null && PowerPointApp.Presentations.Count > 0)
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

