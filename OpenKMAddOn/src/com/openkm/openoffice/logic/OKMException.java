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
    Throwable cause = null;

    public OKMException() {
    }

    public OKMException(String message) {
        this.message = message;
    }

    public OKMException(Exception ex) {
        this.cause = ex.getCause();
        this.message = ex.getMessage();
        super.setStackTrace(ex.getStackTrace());
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
