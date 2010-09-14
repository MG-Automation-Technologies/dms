/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.logic;

/**
 *
 * @author jllort
 */
public class OKMException extends Exception {

    String message = "";

    public OKMException() {
    }

    public OKMException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
