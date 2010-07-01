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

namespace OpenKMPowerPointAddIn
{
    public class OpenKMAddIn
    {
        ComponentResourceManager resources = new ComponentResourceManager(typeof(OpenKMAddIn));

        private ConfigurationForm configurationForm = null;
        private ExplorerForm explorerForm = null;
        private TreeForm treeForm = null;
        private ConfigXML configXML = null;
        private DocumentXML docXML = null;
        private FileUtil fileUtil = null;

        public OpenKMAddIn()
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
        public void showExplorerForm(PowerPoint.Presentations presentations)
        {
            try
            {
                if (explorerForm == null)
                {
                    explorerForm = new ExplorerForm(presentations, OKMDocumentType.TYPE_POWER_POINT, configXML, docXML);
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
        public void showTreeForm(PowerPoint.Presentation presentation)
        {
            try
            {
                presentation.Save(); // Saves the document
                if (treeForm == null)
                {
                    treeForm = new TreeForm(presentation.FullName, configXML);
                }
                else
                {
                    treeForm.setApplication(presentation.FullName);
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

        public void checkin(PowerPoint.Presentation presentationDocument)
        {
            try
            {
                if (MessageBox.Show(resources.GetString("sure_check_in"), resources.GetString("checkin"), MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                {
                    String localFileName = presentationDocument.FullName;
                    presentationDocument.Save(); // save document
                    presentationDocument.Close(); // close document
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
                String errorMsg = "OpenKMPowerPointAddIn - (checkinButton_Click)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        public void cancelCheckout(PowerPoint.Presentation presentationDocument)
        {
            try
            {
                if (MessageBox.Show(resources.GetString("sure_cancel_checkout"), resources.GetString("cancelcheckout"), MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) == DialogResult.OK)
                {
                    String localFileName = presentationDocument.FullName;
                    presentationDocument.Save(); // save document
                    presentationDocument.Close(); // Close document
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
                String errorMsg = "OpenKMPowerPointAddIn - (cancelCheckoutButton_Click)\n" + e.Message + "\n\n" + e.StackTrace;
                MessageBox.Show(errorMsg, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }
    }
}
