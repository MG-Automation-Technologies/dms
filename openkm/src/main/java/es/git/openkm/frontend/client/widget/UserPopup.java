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

package es.git.openkm.frontend.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTWorkspace;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMWorkspaceService;
import es.git.openkm.frontend.client.service.OKMWorkspaceServiceAsync;

/**
 * User popup
 * 
 * @author jllort
 *
 */
public class UserPopup extends DialogBox implements ClickListener {
	
	private final OKMWorkspaceServiceAsync workspaceService = (OKMWorkspaceServiceAsync) GWT.create(OKMWorkspaceService.class);
	
	private VerticalPanel vPanel;
	private FlexTable flexTable;
	private HTML userName;
	private HTML userPassword;
	private HTML imapHost;
	private HTML imapUser;
	private HTML imapPassword;
	private HTML imapFolder;
	private TextBox hostText;
	private TextBox imapUserText;
	private TextBox folderText;
	private PasswordTextBox userPasswordText;
	private PasswordTextBox userPasswordTextVerify;
	private PasswordTextBox imapUserPasswordText;
	private PasswordTextBox imapUserPasswordTextVerify;
	private Button update;
	private Button cancel;
	private HorizontalPanel hPanel; 
	private HTML passwordError;
	
	/**
	 * User popup
	 */
	public UserPopup() {
		
		// Establishes auto-close when click outside
		super(false,true);
		int left = (Window.getClientWidth()-400)/2;
		int top = (Window.getClientHeight()-220)/2;
		
		vPanel = new VerticalPanel();
		flexTable = new FlexTable();
		
		userName = new HTML(Main.i18n("user.preferences.name"));
		userPassword = new HTML(Main.i18n("user.preferences.password"));
		imapHost = new HTML(Main.i18n("user.preferences.imap.host"));
		imapUser = new HTML(Main.i18n("user.preferences.imap.user"));
		imapPassword = new HTML(Main.i18n("user.preferences.imap.user.password"));
		imapFolder = new HTML(Main.i18n("user.preferences.imap.folder"));
		userPasswordText = new PasswordTextBox();
		userPasswordTextVerify = new PasswordTextBox();
		imapUserPasswordText = new PasswordTextBox();
		imapUserPasswordTextVerify = new PasswordTextBox();
		passwordError = new HTML(Main.i18n("user.preferences.password.error"));
		
		passwordError.setVisible(false);
		
		hostText = new TextBox();
		imapUserText = new TextBox();
		folderText = new TextBox();
		
		update = new Button(Main.i18n("button.update"), new ClickListener(){
			public void onClick(Widget sender) {
				if (!userPasswordText.getText().equals(userPasswordTextVerify.getText()) || 
					!imapUserPasswordText.getText().equals(imapUserPasswordTextVerify.getText())) {
					passwordError.setVisible(true);
				} else {
					passwordError.setVisible(false);
					GWTWorkspace workspace = new GWTWorkspace();
					workspace.setUser(Main.get().workspaceUserProperties.getUser());
					workspace.setImapFolder(folderText.getText());
					workspace.setImapHost(hostText.getText());
					workspace.setImapUser(imapUserText.getText());
					workspace.setImapPassword(imapUserPasswordText.getText());
					workspace.setPassword(userPasswordText.getText());
					ServiceDefTarget endPoint = (ServiceDefTarget) workspaceService;
					endPoint.setServiceEntryPoint(Config.OKMWorkspaceService);
					workspaceService.updateUserWorkspace(workspace, callbackUpdateUserWorkspace);
				}
			}			
		});
		
		cancel = new Button(Main.i18n("button.cancel"), new ClickListener(){
			public void onClick(Widget sender) {
				hide();
			}			
		});
		
		hPanel = new HorizontalPanel();
		hPanel.add(update);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(cancel);
		
		flexTable.setCellPadding(0);
		flexTable.setCellSpacing(2);
		flexTable.setWidth("95%");
		
		flexTable.setWidget(0, 0, userName);
		flexTable.setWidget(1, 0, userPassword);
		flexTable.setWidget(2, 0, imapHost);
		flexTable.setWidget(3, 0, imapUser);
		flexTable.setWidget(4, 0, imapPassword);
		flexTable.setWidget(5, 0, imapFolder);
		
		flexTable.setWidget(1, 1, userPasswordText);
		flexTable.setWidget(1, 2, userPasswordTextVerify);
		flexTable.setWidget(2, 1, hostText);
		flexTable.setWidget(3, 1, imapUserText);
		flexTable.setWidget(4, 1, imapUserPasswordText);
		flexTable.setWidget(4, 2, imapUserPasswordTextVerify);
		flexTable.setWidget(5, 1, folderText);
		
		flexTable.getFlexCellFormatter().setColSpan(2, 1, 2);
		hostText.setWidth("275");
		
		vPanel.setWidth("400px");
		vPanel.setHeight("195px");
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(flexTable);
		vPanel.add(passwordError);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(flexTable, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(passwordError, HasAlignment.ALIGN_CENTER);
		
		userName.addStyleName("okm-NoWrap");
		userPassword.addStyleName("okm-NoWrap");
		imapHost.addStyleName("okm-NoWrap");
		imapUser.addStyleName("okm-NoWrap");
		imapPassword.addStyleName("okm-NoWrap");
		imapFolder.addStyleName("okm-NoWrap");
		userPasswordText.setStyleName("okm-Input");
		userPasswordTextVerify.setStyleName("okm-Input");
		hostText.setStyleName("okm-Input");
		imapUserText.setStyleName("okm-Input");
		imapUserPasswordText.setStyleName("okm-Input");
		imapUserPasswordTextVerify.setStyleName("okm-Input");
		folderText.setStyleName("okm-Input");
		passwordError.setStyleName("okm-Input-Error");
		update.setStyleName("okm-Button");
		cancel.setStyleName("okm-Button");
		
		setPopupPosition(left,top);

		super.hide();
		setWidget(vPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	 */
	public void onClick(Widget sender) {
		super.hide();
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("user.preferences.label"));
		userName.setHTML(Main.i18n("user.preferences.name"));
		userPassword.setHTML(Main.i18n("user.preferences.password"));
		imapHost.setHTML(Main.i18n("user.preferences.imap.host"));
		imapUser.setHTML(Main.i18n("user.preferences.imap.user"));
		imapPassword.setHTML(Main.i18n("user.preferences.imap.user.password"));
		imapFolder.setHTML(Main.i18n("user.preferences.imap.folder"));
		passwordError.setHTML(Main.i18n("user.preferences.password.error"));
		update.setText(Main.i18n("button.update"));
		cancel.setText(Main.i18n("button.cancel"));
	}
	
	/**
	 * Reset values
	 */
	private void reset() {
		userPasswordText.setText("");
		userPasswordTextVerify.setText("");
		imapUserPasswordText.setText("");
		imapUserPasswordTextVerify.setText("");
	}
	
	/**
	 * Show the popup user preferences
	 * 
	 */
	public void show() {
		setText(Main.i18n("user.preferences.label"));
		GWTWorkspace workspace = Main.get().workspaceUserProperties.getWorkspace();
		
		reset();
		hostText.setText(workspace.getImapHost());
		imapUserText.setText(workspace.getImapUser());
		folderText.setText(workspace.getImapFolder());
		flexTable.setText(0, 1, workspace.getUser());
		
		if (workspace.isChangePassword()) {
			userPasswordText.setVisible(true);
			userPasswordTextVerify.setVisible(true);
		} else {
			userPasswordText.setVisible(false);
			userPasswordTextVerify.setVisible(false);
		}
		
		super.show();
	}
	
	/**
	 * Call back update user workspace data 
	 */
	final AsyncCallback<Object> callbackUpdateUserWorkspace = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			Main.get().workspaceUserProperties.getUserWorkspace(); // Refreshing workspace saved values
			hide();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackUpdateUserWorkspace", caught);
		}
	};
}