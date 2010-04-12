using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

using Office = Microsoft.Office.Core;
using Outlook = Microsoft.Office.Interop.Outlook;

namespace OutlookOpenKMAddIn
{
    class ExplorerWindow
    {
        private Office.CommandBar mainMenuBar = null;
        private int helpMenuIndex = 0;
        private int openkmMenuIndex = 0;
        private Office.CommandBarPopup mainMenu = null;
        private Office.CommandBarButton importMailMenuItem = null;
        private Office.CommandBarButton configurationMenuItem = null;
        private Outlook.Explorer thisExplorer = null;

        private void AddCustomersMenu()
        {
            ComponentResourceManager resources = new ComponentResourceManager(typeof(ExplorerWindow));

            // Get a reference to the Outlook main menu bar
            mainMenuBar = thisExplorer.CommandBars.ActiveMenuBar;

            // Check if there is a Customers menu
            try
            {
                openkmMenuIndex = mainMenuBar.Controls["OpenKM"].Index;
            }
            catch
            {
                openkmMenuIndex = 0;
            }
            if (openkmMenuIndex == 0) // Add the Customers menu
            {
                // Locate the Help menu
                try
                {
                    helpMenuIndex = mainMenuBar.Controls["Help"].Index;
                }
                catch
                {
                    helpMenuIndex = mainMenuBar.Controls.Count;
                }
                // Create a Customers menu to the left of the Help menu
                mainMenu = ((Office.CommandBarPopup)(mainMenuBar.Controls.Add(Office.MsoControlType.msoControlPopup, Type.Missing, Type.Missing, helpMenuIndex, true)));
                mainMenu.Caption = "OpenKM";
                mainMenu.Visible = true;

                // Create menu items in the Customers menu
                importMailMenuItem = ((Office.CommandBarButton)(mainMenu.Controls.Add(Office.MsoControlType.msoControlButton, Type.Missing, Type.Missing, 1, 1)));
                importMailMenuItem.Style = Microsoft.Office.Core.MsoButtonStyle.msoButtonCaption;
                importMailMenuItem.Caption = resources.GetString("import");
                importMailMenuItem.Click += new Office._CommandBarButtonEvents_ClickEventHandler(respondMenu_Click);
                configurationMenuItem = ((Office.CommandBarButton)(mainMenu.Controls.Add(Office.MsoControlType.msoControlButton, Type.Missing, Type.Missing, 2, 1)));
                configurationMenuItem.Style = Microsoft.Office.Core.MsoButtonStyle.msoButtonCaption;
                configurationMenuItem.Caption = resources.GetString("configure");
                configurationMenuItem.Click += new Office._CommandBarButtonEvents_ClickEventHandler(scheduleMenu_Click);
            }
        }

        private void respondMenu_Click(Office.CommandBarButton ctrl, ref bool CancelDefault)
        {
            Globals.OpenKMAddIn.ImportMail();
        }
        private void scheduleMenu_Click(Office.CommandBarButton ctrl, ref bool CancelDefault)
        {
            Globals.OpenKMAddIn.ShowConfigurationForm();
        }

        public ExplorerWindow(Outlook.Explorer explorer)
        {
            thisExplorer = explorer;
            AddCustomersMenu();
            Outlook.ExplorerEvents_10_Event explorerEvents = ((Outlook.ExplorerEvents_10_Event)(thisExplorer));
            explorerEvents.Close += new Outlook.ExplorerEvents_10_CloseEventHandler(Explorers_Close);
        }

        private void Explorers_Close()
        {
            Globals.OpenKMAddIn.explorerWindowList.Remove(this);
        }
    }
}
