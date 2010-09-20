using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.IO;

namespace MSOpenKMCore.util
{
    public class ImageToolBar
    {

        // Image index constants
        public const int IMG_TOOLBAR_OPENKM                     = 0;
        public const int IMG_TOOLBAR_ADD                        = 1;
        public const int IMG_TOOLBAR_EDIT                       = 2;
        public const int IMG_TOOLBAR_CHECKIN                    = 3;
        public const int IMG_TOOLBAR_CANCELCHECKOUT             = 4;
        public const int IMG_TOOLBAR_ADD_DISABLED               = 5;
        public const int IMG_TOOLBAR_EDIT_DISABLED              = 6;
        public const int IMG_TOOLBAR_CHECKIN_DISABLED           = 7;
        public const int IMG_TOOLBAR_CANCELCHECKOUT_DISABLED    = 8;

        private ImageList imageList = null;

        // Image ToolBar
        public ImageToolBar()
        {
            try
            {
                imageList = new ImageList();

                Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.openkm.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.add_document.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.edit.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.checkin.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.cancel_checkout.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.add_document_disabled.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.edit_disabled.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.checkin_disabled.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.toolbar.cancel_checkout_disabled.gif");
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
