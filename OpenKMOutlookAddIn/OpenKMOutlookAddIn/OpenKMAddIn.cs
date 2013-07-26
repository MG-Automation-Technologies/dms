using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.IO;
using System.ComponentModel;
using System.Runtime.InteropServices;
using System.Windows.Forms;

using MSOpenKMCore.config;
using MSOpenKMCore.form;
using MSOpenKMCore.ws;

namespace OpenKMOutlookAddIn
{
    public class OpenKMAddIn 
    {
        public void ImportMail(Outlook.Explorers explorers, ConfigXML configXML)
        {
            ComponentResourceManager resources = new ComponentResourceManager(typeof(OpenKMAddIn));

            int mailCount = 0;
            int mailAttach = 0;

            for (int y = 1; y <= explorers.Count; y++)
            {
                Outlook.Explorer openWindow = explorers.Item(y);
                String token = "";
                OKMAuth authService = new OKMAuth(configXML.getHost());
                OKMFolder folderService = new OKMFolder(configXML.getHost());
                OKMRepository repositoryService = new OKMRepository(configXML.getHost());
                OKMMail mailService = new OKMMail(configXML.getHost());
                OKMDocument documentService = new OKMDocument(configXML.getHost());

                try
                {
                    if (configXML.getUser().Equals("") || configXML.getPassword().Equals("") ||
                        configXML.getHost().Equals(""))
                    {
                        throw new Exception(resources.GetString("error_configuration_empty"));
                    }

                    token = authService.login(configXML.getUser(), configXML.getPassword());
                    
                    for (int i = 1; i<=openWindow.Selection.Count; i++) {
                        Object selObject = openWindow.Selection.Item(i);
                        if (selObject is Outlook.MailItem)
                        {
                            mailCount++;
                            Outlook.MailItem mailItem = (selObject as Outlook.MailItem);
                            DateTime receivedTime = mailItem.ReceivedTime;
                            String user = configXML.getUser();
                            String basePath = "/okm:mail/" + user + "/";
                            String year = "" + receivedTime.Year;
                            String month = "" + receivedTime.Month;
                            String day = "" + receivedTime.Day;

                            // Only creating folders when it's needed
                            if (repositoryService.hasNode(token, basePath + year))
                            {
                                if (repositoryService.hasNode(token, basePath + year + "/" + month))
                                {
                                    if (!repositoryService.hasNode(token, basePath + year + "/" + month + "/" + day))
                                    {
                                        folder dayFolder = new folder();
                                        dayFolder.path = basePath + year + "/" + month + "/" + day;
                                        folderService.create(token, dayFolder);
                                    }
                                }
                                else
                                {
                                    folder monthFolder = new folder();
                                    folder dayFolder = new folder();
                                    monthFolder.path = basePath + year + "/" + month;
                                    dayFolder.path = basePath + year + "/" + month + "/" + day;
                                    folderService.create(token, monthFolder);
                                    folderService.create(token, dayFolder);
                                }
                            }
                            else
                            {
                                folder yearFolder = new folder();
                                folder monthFolder = new folder();
                                folder dayFolder = new folder();
                                yearFolder.path = basePath + year;
                                monthFolder.path = basePath + year + "/" + month;
                                dayFolder.path = basePath + year + "/" + month + "/" + day;
                                folderService.create(token, yearFolder);
                                folderService.create(token, monthFolder);
                                folderService.create(token, dayFolder);
                            }

                            // Adding mail values
                            mail newMail = new mail();
                            newMail.path = basePath + year + "/" + month + "/" + day + "/" + mailItem.Subject;
                            newMail.subject = mailItem.Subject;

                            newMail.from = mailItem.GetType().InvokeMember("SenderEmailAddress", System.Reflection.BindingFlags.GetProperty, null, mailItem, null).ToString();
                            //newMail.from = mailItem.SenderEmailAddress; // SenderEmailAddress was introduced in outlook 2002
                            newMail.sentDate = mailItem.SentOn;
                            newMail.sentDateSpecified = true;
                            newMail.receivedDate = mailItem.ReceivedTime;
                            newMail.receivedDateSpecified = true;

                            // Setting mail context and type

                            BodyFormat format = (BodyFormat) mailItem.GetType().InvokeMember("BodyFormat", System.Reflection.BindingFlags.GetProperty, null, mailItem, null);
                            if (format.Equals(BodyFormat.olFormatPlain))
                            {
                                newMail.mimeType = "text/plain";
                                newMail.content = mailItem.Body;
                            }
                            else
                            {
                                newMail.mimeType = "text/html";
                                newMail.content = mailItem.HTMLBody;
                            } 

                            // Initialize count recipient address variables
                            int count = 0;
                            int countTo = 0;
                            int countCC = 0;
                            int countBCC = 0;
                            int actualCountTo = 0;
                            int actualCountCC = 0;
                            int actualCountBCC = 0;

                            // Count each mail addresss type to / cc / bcc
                            for (int x=1; x<=mailItem.Recipients.Count; x++) {
                                Outlook.Recipient recipient = mailItem.Recipients.Item(x);
                                switch (recipient.Type)
                                {
                                    case 1:
                                        countTo++;
                                        break;

                                    case 2:
                                        countCC++;
                                        break;

                                    case 3:
                                        countBCC++;
                                        break;

                                    default:
                                        countTo++;
                                        break;
                                }
                                count++;
                            }

                            // Initialize variables
                            String[] mailTo = new String[(countTo > 0) ? countTo : 1];
                            String[] mailCC = new String[(countCC > 0) ? countCC : 1];
                            String[] mailBCC = new String[(countBCC > 0) ? countBCC : 1];

                            // All string[] must have at least one value, it¡s mandatory in webservices
                            if (countTo == 0)
                            {
                                mailTo[0] = "";
                            }
                            if (countCC == 0)
                            {
                                mailCC[0] = "";
                            }
                            if (countBCC == 0)
                            {
                                mailBCC[0] = "";
                            }

                            // Depending mail type each mail is assignede to it own type String[]
                            for (int x=1; x<=mailItem.Recipients.Count; x++) {
                                Outlook.Recipient recipient = mailItem.Recipients.Item(x);
                                switch (recipient.Type)
                                {
                                    case 1:
                                        mailTo[actualCountTo] = recipient.Address;
                                        actualCountTo++;
                                        break;

                                    case 2:
                                        mailCC[actualCountCC] = recipient.Address;
                                        actualCountCC++;
                                        break;

                                    case 3:
                                        mailBCC[actualCountBCC] = recipient.Address;
                                        actualCountBCC++;
                                        break;

                                    default:
                                        mailTo[actualCountTo] = recipient.Address;
                                        actualCountTo++;
                                        break;
                                }

                            }

                            // Assign mail recipients by type
                            newMail.bcc = mailBCC;
                            newMail.cc = mailCC;
                            newMail.to = mailTo;

                            // Creating mail
                            newMail = mailService.create(token, newMail);

                            // Setting attachments
                            if (mailItem.Attachments.Count > 0)
                            {
                                for (int x=1; x<=mailItem.Attachments.Count; x++) {
                                    Outlook.Attachment attachment = mailItem.Attachments.Item(x);

                                    mailAttach++;
                                    document doc = new document();
                                    doc.path = newMail.path + "/" + attachment.FileName;

                                    // save as tempfile for reading
                                    String filename = Environment.GetEnvironmentVariable("TEMP") + "\\" + DateTime.Now.ToString("yymmddHHMMss-") + attachment.FileName;
                                    // save the attachment
                                    attachment.SaveAsFile(filename);

                                    // Uploading document
                                    documentService.create(token, doc, ReadFile(filename));

                                    // Delete a file by using File class static method...
                                    if (File.Exists(filename))
                                    {
                                        // Use a try block to catch IOExceptions, to
                                        // handle the case of the file already being
                                        // opened by another process.
                                        try
                                        {
                                            File.Delete(filename);
                                        }
                                        catch (System.IO.IOException ex)
                                        {
                                            MessageBox.Show(String.Format(resources.GetString("error_deleting_tmp_file"), filename, ex.Message), "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                        }
                                    }

                                    // Releasing com object
                                    Marshal.ReleaseComObject(attachment);
                                }
                            }

                            // Releasing com object
                            Marshal.ReleaseComObject(mailItem);
                        }

                        // Releasing com object
                        Marshal.ReleaseComObject(selObject);
                    }

                    if (!token.Equals(""))
                    {
                        authService.logout(token);  // Always we logout
                        token = "";                 // Reseting token value
                    }

                    if (mailCount > 0)
                    {
                        MessageBox.Show(String.Format(resources.GetString("email_successful_imported"), mailCount, mailAttach));
                    }
                    else
                    {
                        MessageBox.Show(resources.GetString("error_mail_not_selected"), "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    }



                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    if (!token.Equals(""))
                    {
                        authService.logout(token); // Always we logout
                    }
                }

            }
        }

        private enum BodyFormat : int
        {
            olFormatUnspecified = 0,
            olFormatPlain = 1,
            olFormatHTML = 2,
            olFormatRichText =3
        }

        public void ShowConfigurationForm()
        {
            ConfigurationForm form = new ConfigurationForm();
            form.Show();
        }

        private static byte[] ReadFile(string filePath)
        {
            byte[] buffer;
            FileStream fileStream = new FileStream(filePath, FileMode.Open, FileAccess.Read);
            try
            {
                int length = (int)fileStream.Length;  // get file length
                buffer = new byte[length];            // create buffer
                int count;                            // actual number of bytes read
                int sum = 0;                          // total number of bytes read

                // read until Read method returns 0 (end of the stream has been reached)
                while ((count = fileStream.Read(buffer, sum, length - sum)) > 0)
                    sum += count;  // sum is a buffer offset for next reading
            }
            finally
            {
                fileStream.Close();
            }
            return buffer;
        }
    }
}
