using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;
using MSOpenKMCore.ws;
using MSOpenKMCore.bean;
using System.IO;

namespace MSOpenKMCore.util
{
    public class ImageUtil
    {
        // Image index constants
        private const int IMG_FOLDER_EMPTY                          = 0;
        private const int IMG_FOLDER_EMPTY_READ_ONLY                = 1;
        private const int IMG_FOLDER_EMPTY_READ_ONLY_SUBSCRIBED     = 2;
        private const int IMG_FOLDER_EMPTY_SUBSCRIBED               = 3;
        private const int IMG_FOLDER_CHILDS                         = 4;
        private const int IMG_FOLDER_CHILDS_READ_ONLY               = 5;
        private const int IMG_FOLDER_CHILDS_READ_ONLY_SUBSCRIBED    = 6;
        private const int IMG_FOLDER_CHILDS_SUBSCRIBED              = 7;

        private ImageList imageList = null;

        // Image Util
        public ImageUtil()
        {
            try
            {
                imageList = new ImageList();
                Stream s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_empty.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_empty_ro.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_empty_ro_subscribed.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_empty_subscribed.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_childs.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_childs_ro.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_childs_ro_subscribed.gif");
                imageList.Images.Add(Image.FromStream(s));
                s.Close();
                s = this.GetType().Assembly.GetManifestResourceStream("MSOpenKMCore.images.tree.menuitem_childs_subscribed.gif");
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

        // Selects the image index depending some folder values
        public int selectImageIndex(folder folderNode)
        {
            try
            {
                bool hasWritePermission = ((folderNode.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE);

                if (folderNode.hasChildren)
                {
                    if (folderNode.subscribed)
                    {
                        if (hasWritePermission)
                        {
                            return IMG_FOLDER_CHILDS_SUBSCRIBED;
                        }
                        else
                        {
                            return IMG_FOLDER_CHILDS_READ_ONLY_SUBSCRIBED;
                        }
                    }
                    else
                    {
                        if (hasWritePermission)
                        {
                            return IMG_FOLDER_CHILDS;
                        }
                        else
                        {
                            return IMG_FOLDER_CHILDS_READ_ONLY;
                        }
                    }
                }
                else
                {
                    if (folderNode.subscribed)
                    {
                        if (hasWritePermission)
                        {
                            return IMG_FOLDER_EMPTY_SUBSCRIBED;
                        }
                        else
                        {
                            return IMG_FOLDER_EMPTY_READ_ONLY_SUBSCRIBED;
                        }
                    }
                    else
                    {
                        if (hasWritePermission)
                        {
                            return IMG_FOLDER_EMPTY;
                        }
                        else
                        {
                            return IMG_FOLDER_EMPTY_READ_ONLY;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }    

    
    }
}
