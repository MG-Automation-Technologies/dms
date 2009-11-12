/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.backend.client.widget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.backend.client.Main;

/**
 * Confirm panel
 * 
 * @author jllort
 *
 */
public class ConfirmPopup extends DialogBox {
	
	public static final int NO_ACTION 							= 0;
	public static final int CONFIRM_DELETE_USER 				= 1;
	public static final int CONFIRM_DELETE_WORKFLOW_VERSION		= 2;
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private HTML text;
	private Button cancelButton;
	private Button acceptButton;
	private int action = 0;
	private Object object;
	
	/**
	 * Confirm popup
	 */
	public ConfirmPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		text = new HTML();
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickListener() { 
			public void onClick(Widget sender) {
				hide();
			}
		});
		
		acceptButton = new Button(Main.i18n("button.accept"), new ClickListener() { 
			public void onClick(Widget sender) {
				execute();
				hide();
			}
		});

		vPanel.setWidth("300px");
		vPanel.setHeight("100px");
		cancelButton.setStyleName("okm-Button");
		acceptButton.setStyleName("okm-Button");

		text.setHTML("");
		
		hPanel.add(cancelButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(acceptButton);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(text);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(text, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, VerticalPanel.ALIGN_CENTER);

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Execute the confirmed action
	 */
	private void execute() {
		switch (action) {
			
			case CONFIRM_DELETE_USER:
				Main.get().centerPanel.adminUsersPanel.users.deleteUser();
				break;
				
			case CONFIRM_DELETE_WORKFLOW_VERSION:
				Main.get().centerPanel.workflowPanel.workflowVersionData.deleteProcessDefinition();
				break;
		}
		
		action = NO_ACTION; // Resets action value
	}
	
	/**
	 * Sets the action to be confirmed
	 * 
	 * @param action The action to be confirmed
	 */
	public void setConfirm(int action) {
		this.action = action;
		switch (action) {
		
			case CONFIRM_DELETE_USER :
				text.setHTML(Main.i18n("confirm.delete.user"));
				break;
				
			case CONFIRM_DELETE_WORKFLOW_VERSION:
				text.setHTML(Main.i18n("confirm.delete.workflow.version"));
				break;
				
		}
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("confirm.label"));
		cancelButton.setText(Main.i18n("button.cancel"));
		acceptButton.setText(Main.i18n("button.accept"));
	}
	
	/**
	 * Sets the value to object
	 * 
	 * @param object The object to set
	 */
	public void setValue(Object object) {
		this.object = object;
	}
	
	/**
	 * Get the object value
	 * 
	 * @return The object
	 */
	public Object getValue() {
		return this.object;
	}
	
	/**
	 * Shows de popup
	 */
	public void show() {
		setText(Main.i18n("confirm.label"));
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-125)/2;
		setPopupPosition(left,top);
		super.show();
	}
}