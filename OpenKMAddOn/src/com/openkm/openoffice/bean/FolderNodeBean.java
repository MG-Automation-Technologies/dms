/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.bean;

import com.openkm.openoffice.util.Util;
import com.openkm.ws.client.Folder;
import java.io.Serializable;
import javax.swing.Icon;

/**
 *
 * @author jllort
 */
public class FolderNodeBean implements Serializable {
    private Folder folder = new Folder();
    private Icon icon;

    public FolderNodeBean(){
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getExpandedIcon() {
        return icon;
    }

    public String toString() {
        return Util.getOKMFolderName(folder);
    }
}
