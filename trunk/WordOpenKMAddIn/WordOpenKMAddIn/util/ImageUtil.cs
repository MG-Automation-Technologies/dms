using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Windows.Forms;
using WordOpenKMAddIn.bean;
using MSOpenKMCore.ws;

namespace WordOpenKMAddIn.util
{
    class ImageUtil
    {
        private const int IMG_FOLDER_EMPTY = 1;
        private const int IMG_FOLDER_EMPTY_READ_ONLY = 2;
        private const int IMG_FOLDER_EMPTY_READ_ONLY_SUBSCRIBED = 3;
        private const int IMG_FOLDER_EMPTY_SUBSCRIBED = 4;
        private const int IMG_FOLDER_CHILDS = 5;
        private const int IMG_FOLDER_CHILDS_READ_ONLY = 6;
        private const int IMG_FOLDER_CHILDS_READ_ONLY_SUBSCRIBED = 7;
        private const int IMG_FOLDER_CHILDS_SUBSCRIBED = 8;

        private ImageList imageList = null;

        public ImageUtil()
        {
            imageList = new ImageList();
            imageList.Images.Add(Image.FromFile(@"images\menuitem_empty.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_empty_ro.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_empty_ro_subscribed.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_empty_subscribed.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_childs.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_childs_ro.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_childs_ro_subscribed.gif"));
            imageList.Images.Add(Image.FromFile(@"images\menuitem_childs_subscribed.gif"));
        }

        public ImageList get()
        {
            return imageList;
        }

        // Selects the image index depending some folder values
        public int selectImageIndex(folder folderNode)
        {
            bool hasWritePermission = ((folderNode.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE);

            if (folderNode.hasChilds)
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
    }
}
