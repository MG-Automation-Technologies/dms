using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WordOpenKMAddIn.util
{
     public sealed class ImageToPictureConverter : System.Windows.Forms.AxHost   
     {
        private ImageToPictureConverter()
            : base(null)
        {
        }

        public static stdole.IPictureDisp Convert(System.Drawing.Image image)
        {
            return (stdole.IPictureDisp)System.Windows.Forms.AxHost.GetIPictureDispFromPicture(image);
        }
     }
}
