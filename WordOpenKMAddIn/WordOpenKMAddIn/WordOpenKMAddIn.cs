using System;
using System.Drawing;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using Word = Microsoft.Office.Interop.Word;
using Office = Microsoft.Office.Core;

namespace WordOpenKMAddIn
{
    public partial class WordOpenKMAddIn
    {

        private void ordOpenKMAddIn_Startup(object sender, System.EventArgs e)
        {
            // Registering some events 
            Application.WindowActivate += new Microsoft.Office.Interop.Word.ApplicationEvents4_WindowActivateEventHandler(Application_WindowActivate);
            Application.WindowDeactivate += new Microsoft.Office.Interop.Word.ApplicationEvents4_WindowDeactivateEventHandler(Application_WindowDeactivate);
            Application.DocumentBeforeClose += new Word.ApplicationEvents4_DocumentBeforeCloseEventHandler(Application_DocumentBeforeClose);
            Application.DocumentChange += new Microsoft.Office.Interop.Word.ApplicationEvents4_DocumentChangeEventHandler(Application_DocumentChange);

            // Add the button to the Office toolbar
            addToolbar();
        }

        private void WordOpenKMAddIn_Shutdown(object sender, System.EventArgs e)
        {
            saveToolbarPosition();
        }

        

        #region Código generado por VSTO

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido del método con el editor de código.
        /// </summary>
        private void InternalStartup()
        {
            this.Startup += new System.EventHandler(ordOpenKMAddIn_Startup);
            this.Shutdown += new System.EventHandler(WordOpenKMAddIn_Shutdown);
        }
        
        #endregion
    }
}
