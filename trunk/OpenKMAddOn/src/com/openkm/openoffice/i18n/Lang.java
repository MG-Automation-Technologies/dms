/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author jllort
 */
public class Lang {

    private String lang = "";
    private ResourceBundle rBundle = null;

    public Lang() {
        Locale locale = Locale.getDefault();
        lang = locale.getLanguage();
        if (!lang.equals("en") && !lang.equals("es")) {
            lang = "en"; // setting default language
        }
        rBundle = ResourceBundle.getBundle("com/openkm/openoffice/i18n/lang", new Locale(lang));

    }

    public String getString(String key) {
        try {
            return rBundle.getString(key);
        } catch (Exception ex) {
            return key + " - not found";
        }
    }
}
