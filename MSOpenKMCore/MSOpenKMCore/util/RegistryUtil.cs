using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Win32;

namespace MSOpenKMCore.util
{
    public class RegistryUtil
    {
        private String personal = "";
        
        public RegistryUtil()
        {
            // Take a look here http://support.microsoft.com/kb/221837/es ( user could change default my documents path )
            // now this case is not contempled
            try
            {
                RegistryKey myDocumentskey = Registry.CurrentUser.OpenSubKey(@"Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders\");
                personal = (String)myDocumentskey.GetValue("Personal");
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Get local personal user folder
        public String getLocalPersonalUserFolder() {
            return personal;
        }
    }
}
