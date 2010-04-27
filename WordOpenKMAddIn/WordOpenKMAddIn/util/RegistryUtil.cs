using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Win32;

namespace WordOpenKMAddIn.util
{
    class RegistryUtil
    {
        private String personal = "";
        
        public RegistryUtil()
        {
            // Take a look here http://support.microsoft.com/kb/221837/es ( user could change default my documents path )
            // now this case is not contempled
            RegistryKey myDocumentskey = Registry.CurrentUser.OpenSubKey(@"Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders\");
            personal = (String)myDocumentskey.GetValue("Personal");
        }

        public String getPersonal() {
            return personal;
        }
    }
}
