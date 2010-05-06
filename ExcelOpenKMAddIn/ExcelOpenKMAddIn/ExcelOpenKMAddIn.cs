using System;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using Excel = Microsoft.Office.Interop.Excel;
using Office = Microsoft.Office.Core;

namespace ExcelOpenKMAddIn
{
    public partial class ExcelOpenKMAddIn
    {
        private void ExcelOpenKMIn_Startup(object sender, System.EventArgs e)
        {
            #region Código generado por VSTO

            this.Application = (Excel.Application)Microsoft.Office.Tools.Excel.ExcelLocale1033Proxy.Wrap(typeof(Excel.Application), this.Application);

            #endregion

            // Add toolbar
            addToolbar();

            commandBarOption.setEdit(true);
            commandBarOption.setAdd(true);

            // First time opening excel document must evaluate icons
            /*Excel.Workbook activeWorkbook = this.Application.ActiveWorkbook;
            if (this.Application.Workbooks.Count > 0 && activeWorkbook != null)
            {
                evaluateCommandBarIcons(activeWorkbook.FullName);
            }
            else
            {
                evaluateCommandBarIcons(null);
            }*/
        }

        private void ExcelOPenKMAddIn_Shutdown(object sender, System.EventArgs e)
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
            this.Startup += new System.EventHandler(ExcelOpenKMIn_Startup);
            this.Shutdown += new System.EventHandler(ExcelOPenKMAddIn_Shutdown);
        }
        
        #endregion
    }
}
