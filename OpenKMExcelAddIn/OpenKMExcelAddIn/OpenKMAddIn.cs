using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using System.IO;
using System.Windows.Forms;

using MSOpenKMCore.logic;
using MSOpenKMCore.config;
using MSOpenKMCore.util;
using MSOpenKMCore.form;
using MSOpenKMCore.bean;

namespace OpenKMExcelAddIn
{
    public class OpenkMAddIn
    {
        ComponentResourceManager resources = new ComponentResourceManager(typeof(OpenkMAddIn));

        private ConfigurationForm configurationForm = null;
        private ExplorerForm explorerForm = null;
        private TreeForm treeForm = null;
        private ConfigXML configXML = null;
        private DocumentXML docXML = null;
        private FileUtil fileUtil = null;

        public OpenkMAddIn()
        {

            // Initialize vars;
            configXML = new ConfigXML();
            docXML = new DocumentXML();
            fileUtil = new FileUtil();

            // Initialize forms
            configurationForm = new ConfigurationForm();
        }

        // show configuration form
        public void showConfigurationForm()
        {
            try
            {
                configurationForm.Show();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Show explorer form
        public void showExplorerForm(Excel.Workbooks workkbooks)
        {
            try
            {
                if (explorerForm == null)
                {
                    explorerForm = new ExplorerForm(workkbooks, OKMDocumentType.TYPE_EXCEL, configXML, docXML);
                }
                explorerForm.Show();
                explorerForm.startUp();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Show tree form
        public void showTreeForm(Excel.Workbook workbook)
        {
            try
            {
                if (treeForm == null)
                {
                    treeForm = new TreeForm(workbook, configXML);
                }
                else
                {
                    treeForm.setApplication(workbook);
                }
                treeForm.Show();
                treeForm.startUp();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Gets docXML
        public DocumentXML getDocXML()
        {
            return docXML;
        }

        public void checkin(Excel.Workbook activeWorkbook)
        {
            try
            {
                if (MessageBox.Show(resources.GetString("sure_check_in"), resources.GetString("checkin"), MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                {
                    object saveChanges = true;
                    object missing = Type.Missing;
                    String localFileName = activeWorkbook.FullName;
                    activeWorkbook.Close(saveChanges, missing, missing); // Always we save document
                    docXML.refresh(); // Refresh document list
                    if (docXML.isOpenKMDocument(localFileName))
                    {
                        OKMDocument oKMDocument = docXML.getOpenKMDocument(localFileName);
                        docXML.remove(oKMDocument);
                        DocumentLogic.checkin(oKMDocument, configXML.getHost(), configXML.getUser(), configXML.getPassword());
                        if (File.Exists(localFileName))
                        {
                            File.Delete(localFileName);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                String errorMsg = "OpenKMExcelAddIn - (checkinButton_Click)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        public void cancelCheckout(Excel.Workbook activeWorkbook)
        {
            try
            {
                if (MessageBox.Show(resources.GetString("sure_cancel_checkout"), resources.GetString("cancelcheckout"), MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                {
                    object saveChanges = true;
                    object missing = Type.Missing;
                    String localFileName = activeWorkbook.FullName;
                    activeWorkbook.Close(saveChanges, missing, missing); // Always we save document
                    docXML.refresh(); // Refresh document list
                    if (docXML.isOpenKMDocument(localFileName))
                    {
                        OKMDocument oKMDocument = docXML.getOpenKMDocument(localFileName);
                        docXML.remove(oKMDocument);
                        DocumentLogic.cancelCheckout(oKMDocument, configXML.getHost(), configXML.getUser(), configXML.getPassword());
                        if (File.Exists(localFileName))
                        {
                            File.Delete(localFileName);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                String errorMsg = "OpenKMExcelAddIn - (cancelCheckoutButton_Click)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }
    }
}
