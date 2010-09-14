/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
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
        JLabel label = new JLabel("Please wait...");
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(label);
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            this.setLocation((int)screenSize.getWidth()/2-this.getWidth()/2,(int)screenSize.getHeight()/2-this.getHeight()/2);
        } catch (Exception e) {
            // we will ignore this
        }
        pack();
    }
    
}
