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
public class ConfigBean implements Serializable {

    private String user = "";
    private String password = "";
    private String host = "";

    public ConfigBean() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean hasConfiguration() {
        return !user.equals("") && !password.equals("") && !host.equals("");
    }
}
