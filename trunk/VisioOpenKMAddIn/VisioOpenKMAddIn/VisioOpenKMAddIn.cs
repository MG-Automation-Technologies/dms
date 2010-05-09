using System;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using Visio = Microsoft.Office.Interop.Visio;
using Office = Microsoft.Office.Core;

namespace VisioOpenKMAddIn
{
    public partial class VisioOpenKMAddIn
    {
        private void VisioOpenKMAddIn_Startup(object sender, System.EventArgs e)
        {
            // Registering some events 
            Application.WindowActivated += new Visio.EApplication_WindowActivatedEventHandler(Application_WindowActivated);
            Application.DocumentOpened += new Visio.EApplication_DocumentOpenedEventHandler(Application_DocumentOpened);
            Application.DocumentCreated += new Visio.EApplication_DocumentCreatedEventHandler(Application_DocumentCreated);
            Application.DocumentChanged += new Visio.EApplication_DocumentChangedEventHandler(Application_DocumentChanged);
            Application.BeforeWindowClosed += new Visio.EApplication_BeforeWindowClosedEventHandler(Application_WindowClosed);

            // Add the button to the visio toolbar
            addToolbar();

            // First time opening visio document must evaluate icons
            Visio.Document activeDocument = Application.ActiveDocument;
            if (Application.Documents.Count > 0)
            {
                refreshIcons(activeDocument.FullName);
            }
            else
            {
                refreshIcons(null);
            }
        }

        private void VisioOpenKMAddIn_Shutdown(object sender, System.EventArgs e)
        {
        }

        #region Código generado por VSTO

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido del método con el editor de código.
        /// </summary>
        private void InternalStartup()
        {
            this.Startup += new System.EventHandler(VisioOpenKMAddIn_Startup);
            this.Shutdown += new System.EventHandler(VisioOpenKMAddIn_Shutdown);
        }
        
        #endregion
    }
}
