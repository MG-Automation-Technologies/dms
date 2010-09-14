/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.util;

import com.openkm.ws.client.Folder;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author jllort
 */
public class ImageUtil {
    public static Icon IMG_FOLDER_EMPTY;
    public static Icon IMG_FOLDER_EMPTY_READ_ONLY;
    public static Icon IMG_FOLDER_EMPTY_READ_ONLY_SUBSCRIBED;
    public static Icon IMG_FOLDER_EMPTY_SUBSCRIBED;
    public static Icon IMG_FOLDER_CHILDS;
    public static Icon IMG_FOLDER_CHILDS_READ_ONLY;
    public static Icon IMG_FOLDER_CHILDS_READ_ONLY_SUBSCRIBED;
    public static Icon IMG_FOLDER_CHILDS_SUBSCRIBED;

    public ImageUtil() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_empty.gif");
            byte[] buf = new byte[1024*10];
            int size = is.read(buf);
            IMG_FOLDER_EMPTY = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_empty_ro.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_EMPTY_READ_ONLY = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_empty_ro_subscribed.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_EMPTY_READ_ONLY_SUBSCRIBED = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_empty_subscribed.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_EMPTY_SUBSCRIBED = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_childs.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_CHILDS = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_childs_ro.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_CHILDS_READ_ONLY = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_childs_ro_subscribed.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_CHILDS_READ_ONLY_SUBSCRIBED = new ImageIcon(buf);
            is = this.getClass().getClassLoader().getResourceAsStream("com/openkm/openoffice/images/tree/menuitem_childs_subscribed.gif");
            buf = new byte[1024*10];
            size = is.read(buf);
            IMG_FOLDER_CHILDS_SUBSCRIBED = new ImageIcon(buf);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Icon selectImage(Folder folder) {
        boolean hasWritePermission = Util.hasWritePermission(folder);

        if (folder.isHasChilds()) {
            if (folder.isSubscribed()) {
                if (hasWritePermission) {
                    return IMG_FOLDER_CHILDS_SUBSCRIBED;
                } else {
                    return IMG_FOLDER_CHILDS_READ_ONLY_SUBSCRIBED;
                }
            } else {
                if (hasWritePermission) {
                    return IMG_FOLDER_CHILDS;
                } else {
                    return IMG_FOLDER_CHILDS_READ_ONLY;
                }
            }
        } else {
            if (folder.isSubscribed()) {
                if(hasWritePermission) {
                    return IMG_FOLDER_EMPTY_SUBSCRIBED;
                } else {
                    return IMG_FOLDER_EMPTY_READ_ONLY_SUBSCRIBED;
                }
            }
            else {
               if(hasWritePermission) {
                  return IMG_FOLDER_EMPTY;
               } else {
                   return IMG_FOLDER_EMPTY_READ_ONLY; 
               }
            }
        }
    }
}
