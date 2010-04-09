/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.frontend.client.widget.upload;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;

/**
 * File Upload
 * 
 * @author jllort
 *
 */
public class FileUpload extends DialogBox {
	
	private Button closeButton;
	private Button addButton;
	private VerticalPanel vPanel;
	private HorizontalPanel vButtonPanel;
	private FancyFileUpload ffUpload = new FancyFileUpload();
	private int popupWidth = 315;
	private int popupHeight = 125;
	private int doAction = 0;
	private boolean enableAddButton = false;
	private boolean enableImport = true;
	
	/**
	 * File upload
	 */
	public FileUpload() {
		super(false,true);
		vPanel = new VerticalPanel();
		vButtonPanel = new HorizontalPanel();
		
		closeButton = new Button(Main.i18n("fileupload.button.close"), new ClickListener(){
				public void onClick(Widget sender) {
					hide();
					addButton.setVisible(false);
				}
			}
		);
		
		addButton = new Button(Main.i18n("fileupload.button.add.other.file"), new ClickListener(){
				public void onClick(Widget sender) {
					ffUpload.reset(enableImport);
					addButton.setVisible(false); // Add new file button must be unvisible after clicking
				}
			}
		);
		addButton.setVisible(false);
		
		vPanel.setWidth("315px");
		vPanel.setHeight("100px");
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(ffUpload);
		        
		ffUpload.addChangeListener(new ChangeListener() {
            public void onChange(Widget sender) {
            	if (ffUpload.getUploadState() == FancyFileUpload.PENDING_STATE ||
            		ffUpload.getUploadState() == FancyFileUpload.UPLOADING_STATE) {
            		closeButton.setEnabled(false);
            		addButton.setVisible(false);
            	   
            	} else if (ffUpload.getUploadState() == FancyFileUpload.EMPTY_STATE ||
            			   ffUpload.getUploadState() == FancyFileUpload.FAILED_STATE ||
            		       ffUpload.getUploadState() == FancyFileUpload.UPLOADED_STATE) {
            		closeButton.setEnabled(true);
            		if (ffUpload.getUploadState() != FancyFileUpload.EMPTY_STATE && enableAddButton) {
           				addButton.setVisible(true);
            		}
               }
            }
	    }); 
		
		vButtonPanel.add(closeButton);
		vButtonPanel.add(new HTML("&nbsp;&nbsp;"));
		vButtonPanel.add(addButton);
		
		vPanel.add(vButtonPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(ffUpload, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(vButtonPanel, VerticalPanel.ALIGN_CENTER);
		
		closeButton.setStyleName("okm-Button");
		addButton.setStyleName("okm-Button");
		
		setWidget(vPanel);
	}
	
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		closeButton.setHTML(Main.i18n("button.close")); 
		addButton.setHTML(Main.i18n("fileupload.button.add.other.file"));
		
		if (doAction == FancyFileUpload.ACTION_INSERT) {
			setText(Main.i18n("fileupload.label.insert"));
		} else {
			setText(Main.i18n("fileupload.label.update"));
		}
		
		ffUpload.langRefresh();
	}
	
	/**
	 * Show file upload popup
	 */
	public void showPopup(boolean enableAddButton, boolean enableImport) {
		this.enableAddButton = enableAddButton;
		this.enableImport = enableImport;
		setWidth(""+popupWidth);
		setHeight(""+popupHeight);
		ffUpload.init(); // Inits to correct center position
		center();

		// Allways must initilize htmlForm for tree path initialization
		langRefresh();
		ffUpload.reset(enableImport);
	}
	
	/**
	 * Hide file upload 
	 */
	public void hide() {
		if (doAction == FancyFileUpload.ACTION_UPDATE) {
			GWTDocument doc = Main.get().mainPanel.browser.fileBrowser.table.getDocument();
			Main.get().mainPanel.browser.tabMultiple.tabDocument.setProperties(doc);
		}
		super.hide();
	}
	
	/**
	 * Sets the action to do on upload ( create new file or update and existing )
	 * 
	 * @param action Action to do on upload 
	 */
	public void setAction(int action) {
		doAction = action;
		ffUpload.setAction(action);
	}
	
	/**
	 * Sets the path ( document if it's update or directory if it's insert )
	 * 
	 * @param path The document or directory path
	 */
	public void setPath(String path) {
		ffUpload.setPath(path);
	}
}