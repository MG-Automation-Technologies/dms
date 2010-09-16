/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.ui;

import com.openkm.openoffice.OpenKMAddOn;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author jllort
 */
public class WaitWindow extends JDialog  {

    public WaitWindow() {
        setUndecorated(true);
        JLabel label = new JLabel(OpenKMAddOn.get().getLang().getString("waitwindow.title"));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(label);
        try {
            setLocationByPlatform(true);
            setLocationRelativeTo(getParent());
        } catch (Exception e) {
            // we will ignore this
        }
        pack();
    }
    
}
