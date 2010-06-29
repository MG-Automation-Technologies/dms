using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.IO;

namespace MSOpenKMCore.util
{
    public class ImageLargeToolbar
    {
        // Image index constants
        public const int IMG_TOOLBAR_LOGO                       = 0;

        private ImageList imageList = null;

        // Image ToolBar
        public ImageLargeToolbar()
        {
            try
            {
                imageList = new ImageList();

                Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.logo.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return the image list
        public ImageList get()
        {
            return imageList;
        }
    }
}
