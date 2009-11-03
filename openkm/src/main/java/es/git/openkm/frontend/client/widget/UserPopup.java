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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
	private FlexTable userFlexTable;
	private FlexTable mailFlexTable;
	private HTML userName;
	private HTML userPassword;
	private HTML userMail;
	private HTML imapHost;
	private HTML imapUser;
	private HTML imapPassword;
	private HTML imapFolder;
	private TextBox hostText;
	private TextBox imapUserText;
	private TextBox folderText;
	private PasswordTextBox userPasswordText;
	private PasswordTextBox userPasswordTextVerify;
	private TextBox userMailText;
	private PasswordTextBox imapUserPasswordText;
	private PasswordTextBox imapUserPasswordTextVerify;
	private Button update;
	private Button cancel;
	private Button delete;
	private HorizontalPanel hPanel; 
	private HTML passwordError;
	private HTML imapPassordError;
	private HTML imapError;
	private GroupBoxPanel userGroupBoxPanel;
	private GroupBoxPanel mailGroupBoxPanel;
	
	/**
	 * User popup
	 */
	public UserPopup() {
		
		// Establishes auto-close when click outside
		super(false,true);
		int left = (Window.getClientWidth()-400)/2;
		int top = (Window.getClientHeight()-220)/2;
		
		vPanel = new VerticalPanel();
		userFlexTable = new FlexTable();
		mailFlexTable = new FlexTable();
		
		userGroupBoxPanel = new GroupBoxPanel();
		userGroupBoxPanel.setCaption(Main.i18n("user.preferences.user.data"));
		userGroupBoxPanel.add(userFlexTable);
		
		mailGroupBoxPanel = new GroupBoxPanel();
		mailGroupBoxPanel.setCaption(Main.i18n("user.preferences.mail.data"));
		mailGroupBoxPanel.add(mailFlexTable);
		
		userName = new HTML(Main.i18n("user.preferences.user"));
		userPassword = new HTML(Main.i18n("user.preferences.password"));
		userMail = new HTML(Main.i18n("user.preferences.mail"));
		imapHost = new HTML(Main.i18n("user.preferences.imap.host"));
		imapUser = new HTML(Main.i18n("user.preferences.imap.user"));
		imapPassword = new HTML(Main.i18n("user.preferences.imap.user.password"));
		imapFolder = new HTML(Main.i18n("user.preferences.imap.folder"));
		userPasswordText = new PasswordTextBox();
		userPasswordTextVerify = new PasswordTextBox();
		userMailText = new TextBox();
		imapUserPasswordText = new PasswordTextBox();
		imapUserPasswordTextVerify = new PasswordTextBox();
		passwordError = new HTML(Main.i18n("user.preferences.password.error"));
		imapPassordError = new HTML(Main.i18n("user.preferences.imap.password.error.void"));
		imapError = new HTML(Main.i18n("user.preferences.imap.error"));
		
		passwordError.setVisible(false);
		imapPassordError.setVisible(false);
		imapError.setVisible(false);
		
		hostText = new TextBox();
		imapUserText = new TextBox();
		folderText = new TextBox();
		
		update = new Button(Main.i18n("button.update"), new ClickListener(){
			public void onClick(Widget sender) {
				passwordError.setVisible(false);
				imapPassordError.setVisible(false);
				imapError.setVisible(false);
				if (!userPasswordText.getText().equals(userPasswordTextVerify.getText()) || 
					!imapUserPasswordText.getText().equals(imapUserPasswordTextVerify.getText())) {
					passwordError.setVisible(true);
				} else if (Main.get().workspaceUserProperties.getWorkspace().getImapID()<0 && imapUserPasswordText.getText().equals("")) {
					imapPassordError.setVisible(true);
			    } else if( (imapUserPasswordText.getText().length()>0 || folderText.getText().length()>0 || imapUserText.getText().length()>0 ||
							hostText.getText().length()>0) && !(folderText.getText().length()>0 && imapUserText.getText().length()>0 
							&& hostText.getText().length()>0) ) {
					imapError.setVisible(true);
				} else {
					GWTWorkspace workspace = new GWTWorkspace();
					workspace.setUser(Main.get().workspaceUserProperties.getUser());
					workspace.setEmail(userMailText.getText());
					workspace.setImapFolder(folderText.getText());
					workspace.setImapHost(hostText.getText());
					workspace.setImapUser(imapUserText.getText());
					workspace.setImapPassword(imapUserPasswordText.getText());
					workspace.setPassword(userPasswordText.getText());
					workspace.setImapID(Main.get().workspaceUserProperties.getWorkspace().getImapID());
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
		
		delete = new Button(Main.i18n("button.delete"), new ClickListener(){
			public void onClick(Widget sender) {
				int Id = Main.get().workspaceUserProperties.getWorkspace().getImapID();
				if (Id>=0) {
					ServiceDefTarget endPoint = (ServiceDefTarget) workspaceService;
					endPoint.setServiceEntryPoint(Config.OKMWorkspaceService);
					workspaceService.deleteMailAccount(Id, callbackDeleteMailAccount);
				}
			}			
		});
		
		hPanel = new HorizontalPanel();
		hPanel.add(update);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(cancel);
		
		userFlexTable.setCellPadding(0);
		userFlexTable.setCellSpacing(2);
		userFlexTable.setWidth("95%");
		
		userFlexTable.setWidget(0, 0, userName);
		userFlexTable.setWidget(1, 0, userPassword);
		userFlexTable.setWidget(2, 0, userMail);
		
		userFlexTable.setWidget(1, 1, userPasswordText);
		userFlexTable.setWidget(1, 2, userPasswordTextVerify);
		userFlexTable.setWidget(2, 1, userMailText);
		
		userFlexTable.getFlexCellFormatter().setColSpan(2, 1, 2);
		
		mailFlexTable.setCellPadding(0);
		mailFlexTable.setCellSpacing(2);
		mailFlexTable.setWidth("95%");
		
		mailFlexTable.setWidget(1, 0, imapHost);
		mailFlexTable.setWidget(2, 0, imapUser);
		mailFlexTable.setWidget(3, 0, imapPassword);
		mailFlexTable.setWidget(4, 0, imapFolder);
		
		mailFlexTable.setWidget(1, 1, hostText);
		mailFlexTable.setWidget(2, 1, imapUserText);
		mailFlexTable.setWidget(3, 1, imapUserPasswordText);
		mailFlexTable.setWidget(3, 2, imapUserPasswordTextVerify);
		mailFlexTable.setWidget(4, 1, folderText);
		mailFlexTable.setWidget(5, 0, new HTML("&nbsp;"));
		mailFlexTable.setWidget(5, 1, new HTML("&nbsp;"));
		mailFlexTable.setWidget(5, 2, delete);
		
		mailFlexTable.getFlexCellFormatter().setColSpan(1, 1, 2);
		mailFlexTable.getFlexCellFormatter().setAlignment(5, 2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		
		userMailText.setWidth("275");
		hostText.setWidth("275");
		userGroupBoxPanel.setWidth("370px");
		mailGroupBoxPanel.setWidth("370px");
		
		vPanel.setWidth("410px");
		vPanel.setHeight("195px");
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(userGroupBoxPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(mailGroupBoxPanel);
		vPanel.add(passwordError);
		vPanel.add(imapPassordError);
		vPanel.add(imapError);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(userGroupBoxPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(mailGroupBoxPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(passwordError, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(imapPassordError, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(imapError, HasAlignment.ALIGN_CENTER);
		
		userName.addStyleName("okm-NoWrap");
		userPassword.addStyleName("okm-NoWrap");
		userMail.addStyleName("okm-NoWrap");
		imapHost.addStyleName("okm-NoWrap");
		imapUser.addStyleName("okm-NoWrap");
		imapPassword.addStyleName("okm-NoWrap");
		imapFolder.addStyleName("okm-NoWrap");
		userPasswordText.setStyleName("okm-Input");
		userPasswordTextVerify.setStyleName("okm-Input");
		userMailText.setStyleName("okm-Input");
		hostText.setStyleName("okm-Input");
		imapUserText.setStyleName("okm-Input");
		imapUserPasswordText.setStyleName("okm-Input");
		imapUserPasswordTextVerify.setStyleName("okm-Input");
		folderText.setStyleName("okm-Input");
		passwordError.setStyleName("okm-Input-Error");
		imapPassordError.setStyleName("okm-Input-Error");
		imapError.setStyleName("okm-Input-Error");
		update.setStyleName("okm-Button");
		cancel.setStyleName("okm-Button");
		delete.setStyleName("okm-Button");
		
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
		userName.setHTML(Main.i18n("user.preferences.user"));
		userPassword.setHTML(Main.i18n("user.preferences.password"));
		userMail.setHTML(Main.i18n("user.preferences.mail"));
		imapHost.setHTML(Main.i18n("user.preferences.imap.host"));
		imapUser.setHTML(Main.i18n("user.preferences.imap.user"));
		imapPassword.setHTML(Main.i18n("user.preferences.imap.user.password"));
		imapFolder.setHTML(Main.i18n("user.preferences.imap.folder"));
		passwordError.setHTML(Main.i18n("user.preferences.password.error"));
		imapPassordError.setHTML(Main.i18n("user.preferences.imap.password.error.void"));
		imapError.setHTML(Main.i18n("user.preferences.imap.error"));
		update.setText(Main.i18n("button.update"));
		cancel.setText(Main.i18n("button.cancel"));
		delete.setText(Main.i18n("button.delete"));
		userGroupBoxPanel.setCaption(Main.i18n("user.preferences.user.data"));
		mailGroupBoxPanel.setCaption(Main.i18n("user.preferences.mail.data"));
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
		userFlexTable.setText(0, 1, workspace.getUser());
		userFlexTable.getFlexCellFormatter().setColSpan(0, 1, 2);
		userMailText.setText(workspace.getEmail());
		
		passwordError.setVisible(false);
		imapPassordError.setVisible(false);
		imapError.setVisible(false);
		
		if (workspace.isChangePassword()) {
			userMail.setVisible(true);
		    userMailText.setVisible(true);
		    userPassword.setVisible(true);
			userPasswordText.setVisible(true);
			userPasswordTextVerify.setVisible(true);
		} else {
			userMail.setVisible(true);
			userMailText.setVisible(false);
			userPassword.setVisible(false);
			userPasswordText.setVisible(false);
			userPasswordTextVerify.setVisible(false);
		}
		
		// Enables delete button only if there's some imap server configured to be removed
		if (workspace.getImapID()>=0) {
			delete.setVisible(true);
		} else {
			delete.setVisible(false);
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
	
	/**
	 * Call back delete mail account
	 */
	final AsyncCallback<Object> callbackDeleteMailAccount = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			Main.get().workspaceUserProperties.getUserWorkspace(); // Refreshing workspace saved values
			hostText.setText("");
			imapUserText.setText("");
			imapUserPasswordText.setText("");
			imapUserPasswordTextVerify.setText("");
			folderText.setText("");
			delete.setVisible(false);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackDeleteMailAccount", caught);
		}
	};
	
}