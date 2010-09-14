/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.bean;

import java.io.Serializable;

/**
 *
 * @author jllort
 */
public class OKMDocumentBean implements Serializable {

    private String name = "";
    private String UUID = "";
    private String localFilename = "";
    private String path = "";

    public OKMDocumentBean() {
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getLocalFilename() {
        return localFilename;
    }

    public void setLocalFilename(String localFilename) {
        this.localFilename = localFilename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
