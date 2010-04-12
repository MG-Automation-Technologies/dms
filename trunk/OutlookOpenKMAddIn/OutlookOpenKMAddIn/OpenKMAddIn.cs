using System;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using System.Collections.Generic;

using Outlook = Microsoft.Office.Interop.Outlook;
using Office = Microsoft.Office.Core;

namespace OutlookOpenKMAddIn
{
    public partial class OpenKMAddIn
    {
        private Outlook.Explorers explorers = null;
        internal List<ExplorerWindow> explorerWindowList = null;

        private void OpenKMAddIn_Startup(object sender, System.EventArgs e)
        {
            explorerWindowList = new List<ExplorerWindow>();
            explorerWindowList.Add(new ExplorerWindow(this.Application.ActiveExplorer()));
            explorers = this.Application.Explorers;
            explorers.NewExplorer += new Outlook.ExplorersEvents_NewExplorerEventHandler(Explorers_NewExplorer);
        }

        private void OpenKMAddIn_Shutdown(object sender, System.EventArgs e)
        {
        }

        private void Explorers_NewExplorer(Outlook.Explorer explorerWindow)
        {
            explorerWindowList.Add(new ExplorerWindow(explorerWindow));
        }

        #region VSTO generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InternalStartup()
        {
            this.Startup += new System.EventHandler(OpenKMAddIn_Startup);
            this.Shutdown += new System.EventHandler(OpenKMAddIn_Shutdown);
        }

        #endregion
    }
}
