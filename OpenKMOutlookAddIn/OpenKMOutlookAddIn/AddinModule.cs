using System;
using System.Runtime.InteropServices;
using System.ComponentModel;

using MSOpenKMCore.form;
using MSOpenKMCore.config;

namespace OpenKMOutlookAddIn
{
    /// <summary>
    ///   Add-in Express Add-in Module
    /// </summary>
    [GuidAttribute("965341E9-B5E1-4003-8E18-1BA1EE121457"), ProgId("OpenKMOutlookAddIn.AddinModule")]
    public partial class AddinModule : AddinExpress.MSO.ADXAddinModule
    {
        ConfigXML configXML;
        OpenKMAddIn openkmAddin;
        ComponentResourceManager resources = new ComponentResourceManager(typeof(AddinModule));

        public AddinModule()
        {
            InitializeComponent();
            configXML = new ConfigXML();
            openkmAddin = new OpenKMAddIn();

            // Internacionalitzation
            this.import.Caption = resources.GetString("import");
            this.configure.Caption = resources.GetString("configure");

            // Please add any initialization code to the AddinInitialize event handler
            this.import.Click += new AddinExpress.MSO.ADXClick_EventHandler(import_Click);
            this.configure.Click += new AddinExpress.MSO.ADXClick_EventHandler(configure_Click);
        }

        void import_Click(object sender)
        {
            Outlook.Explorers explorers = null;
            Outlook.Selection selection = null;
            Outlook.MailItem mailItem = null;
            try
            {
                explorers = this.OutlookApp.Explorers;
                openkmAddin.ImportMail(explorers, configXML);
            }
            catch { }
            finally
            {
                if (explorers != null) Marshal.ReleaseComObject(explorers);
                if (selection != null) Marshal.ReleaseComObject(selection);
                if (mailItem != null) Marshal.ReleaseComObject(mailItem);
            }
        }

        void configure_Click(object sender)
        {
            openkmAddin.ShowConfigurationForm();
        }

        private AddinExpress.MSO.ADXOlExplorerMainMenu mainMenu;
        private AddinExpress.MSO.ADXCommandBarPopup popup;
        private AddinExpress.MSO.ADXCommandBarButton import;
        private AddinExpress.MSO.ADXCommandBarButton configure;
 
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
            this.mainMenu = new AddinExpress.MSO.ADXOlExplorerMainMenu(this.components);
            this.popup = new AddinExpress.MSO.ADXCommandBarPopup(this.components);
            this.import = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            this.configure = new AddinExpress.MSO.ADXCommandBarButton(this.components);
            // 
            // mainMenu
            // 
            this.mainMenu.CommandBarName = "Menu Bar";
            this.mainMenu.CommandBarTag = "32663e6f-5298-4846-9992-b8059226334c";
            this.mainMenu.Controls.Add(this.popup);
            this.mainMenu.Temporary = true;
            this.mainMenu.UpdateCounter = 3;
            this.mainMenu.UseForRibbon = true;
            // 
            // popup
            // 
            this.popup.Before = 7;
            this.popup.Caption = "OpenKM";
            this.popup.Controls.Add(this.import);
            this.popup.Controls.Add(this.configure);
            this.popup.ControlTag = "26c5c726-2d1e-4121-8e0f-692aa7820812";
            this.popup.Temporary = true;
            this.popup.UpdateCounter = 9;
            // 
            // import
            // 
            this.import.Caption = "Import e-mail";
            this.import.ControlTag = "e6b75742-754b-4e58-889c-365eeb56160d";
            this.import.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.import.Temporary = true;
            this.import.UpdateCounter = 2;
            // 
            // configure
            // 
            this.configure.Caption = "Configure";
            this.configure.ControlTag = "eccd89ff-7553-4b33-8e2b-25846598ffdc";
            this.configure.ImageTransparentColor = System.Drawing.Color.Transparent;
            this.configure.Temporary = true;
            this.configure.UpdateCounter = 1;
            // 
            // AddinModule
            // 
            this.AddinName = "OpenKMOutlookAddIn";
            this.SupportedApps = AddinExpress.MSO.ADXOfficeHostApp.ohaOutlook;

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

        public Outlook._Application OutlookApp
        {
            get
            {
                return (HostApplication as Outlook._Application);
            }
        }
    }
}

