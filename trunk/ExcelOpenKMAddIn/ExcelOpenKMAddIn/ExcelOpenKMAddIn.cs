using System;
using System.Windows.Forms;
using Microsoft.VisualStudio.Tools.Applications.Runtime;
using Excel = Microsoft.Office.Interop.Excel;
using Office = Microsoft.Office.Core;
using MSOpenKMCore.util;
using System.Timers;

namespace ExcelOpenKMAddIn
{
    public partial class ExcelOpenKMAddIn
    {
        private AplicationWatcher aplicationWattcher;
        System.Timers.Timer timer = null;

        private void ExcelOpenKMIn_Startup(object sender, System.EventArgs e)
        {
            #region Código generado por VSTO

            this.Application = (Excel.Application)Microsoft.Office.Tools.Excel.ExcelLocale1033Proxy.Wrap(typeof(Excel.Application), this.Application);

            #endregion

            Application.WorkbookBeforeClose += new Microsoft.Office.Interop.Excel.AppEvents_WorkbookBeforeCloseEventHandler(Application_WorkbookBeforeClose);
            Application.WorkbookActivate += new Microsoft.Office.Interop.Excel.AppEvents_WorkbookActivateEventHandler(Application_WorkbookActivate);
            //Application.WorkbookDeactivate += new Microsoft.Office.Interop.Excel.AppEvents_WorkbookDeactivateEventHandler(Application_WorkbookDeactivate);
            Application.WorkbookOpen += new Microsoft.Office.Interop.Excel.AppEvents_WorkbookOpenEventHandler(Application_WorkbookOpen);

            // Excel has focus / lost focus events
            aplicationWattcher = new AplicationWatcher();
            aplicationWattcher.OnWindowHasFocus += new WindowHasFocus(AplicationWatcher_OnWindowHasFocus);
            aplicationWattcher.OnWindowLostFocus += new WindowLostFocus(AplicationWatcher_OnWindowLostFocus);
            aplicationWattcher.Start(Application.Hwnd);

            // Add toolbar
            addToolbar();

            // Creating a timer to evaluating first workbook ( solves problems firing some event at first time loading )
            timer = new System.Timers.Timer();
            timer.Elapsed += new ElapsedEventHandler(OnTimedEvent);
            timer.Interval = 1000; // 1 second ( after loading we evaluate icons )
            timer.Enabled = true;
            GC.KeepAlive(timer);
        }

        private void ExcelOPenKMAddIn_Shutdown(object sender, System.EventArgs e)
        {
            saveToolbarPosition();
        }

        // Specify what you want to happen when the Elapsed event is 
        // raised.
        private void OnTimedEvent(object source, ElapsedEventArgs e)
        {
            timer.Close(); // We ensure timer is only executed one time
            // First time opening excel document must evaluate icons
            Excel.Workbook activeWorkbook = this.Application.ActiveWorkbook;
            if (this.Application.Workbooks.Count > 0 && activeWorkbook != null)
            {
                refreshIcons(activeWorkbook.FullName);
            }
            else
            {
                refreshIcons(null);
            }
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
