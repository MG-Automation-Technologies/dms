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

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMAuthService;
import es.git.openkm.frontend.client.service.OKMAuthServiceAsync;
import es.git.openkm.frontend.client.service.OKMNotifyService;
import es.git.openkm.frontend.client.service.OKMNotifyServiceAsync;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.upload.UserScrollTable;

/**
 * NotifyPopup
 * 
 * @author jllort
 *
 */
public class NotifyPopup extends DialogBox  {
	
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	private final OKMNotifyServiceAsync notifyService = (OKMNotifyServiceAsync) GWT.create(OKMNotifyService.class);
	
	private VerticalPanel vPanel;
	private Button closeButton;
	private Button sendButton;
	private HorizontalPanel hUserPanel;
	private HorizontalPanel vButtonPanel;
	private TextArea message;
	private ScrollPanel messageScroll;
	private UserScrollTable notifyTable;
	private UserScrollTable userTable;
	private VerticalPanel buttonPanel;
	private HTML addButtom;
	private HTML removeButtom;
	private HTML commentTXT;
	private String users;
	private GWTDocument doc;
	
	public NotifyPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		setText(Main.i18n("notify.label"));
		users = "";
		doc = new GWTDocument();
		
		vPanel = new VerticalPanel();
		hUserPanel = new HorizontalPanel();
		vButtonPanel = new HorizontalPanel();
		message = new TextArea();
		notifyTable = new UserScrollTable(true);
		userTable = new UserScrollTable(false);
		notifyTable.reset();
		userTable.reset();
		hUserPanel = new HorizontalPanel();
		buttonPanel = new VerticalPanel();
		addButtom = new HTML(Util.imageHTML("img/icon/security/add.gif"));
		removeButtom = new HTML(Util.imageHTML("img/icon/security/remove.gif"));
		commentTXT = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Main.i18n("fileupload.label.notify.comment"));
		
		closeButton = new Button(Main.i18n("fileupload.button.close"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
				reset();
				
			}
		}
		);
		
		sendButton = new Button(Main.i18n("fileupload.send"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				// Only sends if there's some user selected
				if (!users.equals("")) {
					sendLinkNotification();
				}
				hide();
				reset();
			}
		}
		);
		
		message.setSize("260","60");
		message.setStyleName("okm-Input");
		// TODO This is a workaround for a Firefox 2 bug
		// http://code.google.com/p/google-web-toolkit/issues/detail?id=891
		messageScroll = new ScrollPanel(message);
		messageScroll.setAlwaysShowScrollBars(false);
		
		addButtom.addClickHandler(addButtomListener);
		removeButtom.addClickHandler(removeButtomListener);
		
		buttonPanel.add(addButtom);
		buttonPanel.add(new HTML("<br><br><br>")); // separator
		buttonPanel.add(removeButtom);
		
		hUserPanel.setSize("260","140");
		hUserPanel.add(notifyTable);
		hUserPanel.add(buttonPanel);
		hUserPanel.add(userTable);
		hUserPanel.setCellVerticalAlignment(buttonPanel,VerticalPanel.ALIGN_MIDDLE);
		hUserPanel.setCellHorizontalAlignment(buttonPanel,HorizontalPanel.ALIGN_CENTER);
		hUserPanel.setCellWidth(buttonPanel,"20");
		
		vButtonPanel.add(closeButton);
		vButtonPanel.add(new HTML("&nbsp;&nbsp;"));
		vButtonPanel.add(sendButton);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(commentTXT);
		vPanel.add(messageScroll);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hUserPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(vButtonPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(messageScroll, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hUserPanel, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(vButtonPanel, VerticalPanel.ALIGN_CENTER);
		
		vPanel.setWidth("100%");
		
		notifyTable.addStyleName("okm-Input");
		userTable.addStyleName("okm-Input");
		closeButton.setStyleName("okm-Button");
		sendButton.setStyleName("okm-Button");
		
		commentTXT.addStyleName("okm-DisableSelect");
		hUserPanel.addStyleName("okm-DisableSelect");
		vButtonPanel.addStyleName("okm-DisableSelect");
		
		setWidget(vPanel);
	}
	
	/**
	 * langRefresh 
	 * 
	 * Refreshing lang
	 */
	public void langRefresh(){
		setText(Main.i18n("notify.label"));
		closeButton.setHTML(Main.i18n("button.close")); 
		sendButton.setHTML(Main.i18n("fileupload.send"));
		commentTXT = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Main.i18n("fileupload.label.notify.comment"));
		notifyTable.langRefresh();
		userTable.langRefresh();
	}
	
	/**
	 * executeSendDocumentLink
	 */
	public void executeSendDocumentLink() {
		if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected()) {
			doc = Main.get().mainPanel.browser.fileBrowser.getDocument();
			super.center();
			getAllUsers(); // Initialices users
		} 
	}
	
	/**
	 * Add buttom listener
	 */
	ClickHandler addButtomListener = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (userTable.getUser() != null) {
				notifyTable.addRow(userTable.getUser());	
				notifyTable.selectLastRow();
				userTable.removeSelectedRow();
				users = notifyTable.getUsersToNotify();
			}
		}
	};
	
	/**
	 * Remove buttom listener
	 */
	ClickHandler removeButtomListener = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (notifyTable.getUser() != null) {
				userTable.addRow(notifyTable.getUser());
				userTable.selectLastRow();
				notifyTable.removeSelectedRow();
				users = notifyTable.getUsersToNotify();
			}
		}
	};
	
	/**
	 * Call back get granted users
	 */
	final AsyncCallback<List<String>> callbackAllUsers = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result) {			
			for (Iterator<String> it = result.iterator(); it.hasNext(); ) {
				String userName = it.next();
				userTable.addRow(userName);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetGrantedUsers", caught);
		}
	};
	
	/**
	 * Call back send link notification
	 */
	final AsyncCallback<Object> callbackSendLinkNotification = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("Notify", caught);
		}
	};
	
	/**
	 * Gets all users
	 */
	private void getAllUsers() {
		ServiceDefTarget endPoint = (ServiceDefTarget) authService;
		endPoint.setServiceEntryPoint(Config.OKMAuthService);	
		authService.getAllUsers( callbackAllUsers);
	}
	

	/**
	 * Sens the link notification
	 */
	private void sendLinkNotification() {
		ServiceDefTarget endPoint = (ServiceDefTarget) notifyService;
		endPoint.setServiceEntryPoint(Config.OKMNotifyService);	
		notifyService.notify(doc.getPath(), users, message.getText() ,callbackSendLinkNotification);
	}
	
	/**
	 * Reste values
	 */
	private void reset() {
		notifyTable.reset();
		userTable.reset();
		users = "";
		message.setText("");
		doc = new GWTDocument();
	}
}
