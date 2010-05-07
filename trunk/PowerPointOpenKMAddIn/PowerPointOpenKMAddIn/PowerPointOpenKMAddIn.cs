using System;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using Office = Microsoft.Office.Core;

namespace PowerPointOpenKMAddIn
{
    public partial class PowerPointOpenKMAddIn
    {
        private void PowerPointOpenKMAddIn_Startup(object sender, System.EventArgs e)
        {
            // Add toolbar
            addToolbar();
        }

        private void PowerPointOpenKMAddIn_Shutdown(object sender, System.EventArgs e)
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
            this.Startup += new System.EventHandler(PowerPointOpenKMAddIn_Startup);
            this.Shutdown += new System.EventHandler(PowerPointOpenKMAddIn_Shutdown);
        }
        
        #endregion
    }
}
