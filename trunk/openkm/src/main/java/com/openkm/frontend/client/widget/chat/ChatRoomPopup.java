/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
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

package com.openkm.frontend.client.widget.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.service.OKMChatService;
import com.openkm.frontend.client.service.OKMChatServiceAsync;

/**
 * Chat room popup
 * 
 * @author jllort
 *
 */
public class ChatRoomPopup extends ChatRoomDialogBox {
	
	private final OKMChatServiceAsync chatService = (OKMChatServiceAsync) GWT.create(OKMChatService.class);
	private final static int DELAY = 200; // mseg
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private Button close;
	private TextArea textArea;
	private FlexTable table;
	private ScrollPanel scrollPanel;
	private List<String> usersInRoom;
	private HTML usersInRoomText;
	private ChatRoomDialogBox singleton;
	
	/**
	 * Chat room popup
	 */
	public ChatRoomPopup(String user, final String room) {
		// Establishes auto-close when click outside
		super(false,false);
		setText(Main.i18n("chat.room"));
		singleton = this;
		
		usersInRoom = new ArrayList<String>();
		usersInRoom.add(user);
		usersInRoom.add(Main.get().workspaceUserProperties.getUser());
		
		usersInRoomText = new HTML("");
		refreshUsersInRoom();
		
		close = new Button(Main.i18n("button.close"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
				endPoint.setServiceEntryPoint(Config.OKMChatService);
				chatService.logout(new AsyncCallback<Object>() {
					@Override
					public void onSuccess(Object arg0) {
						Main.get().mainPanel.bottomPanel.userInfo.removeChatRoom(singleton);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Main.get().showError("Logout", caught);
					}
				});
				hide();
			}
		});
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		hPanel.add(usersInRoomText);
		
		table = new FlexTable();
		table.setBorderWidth(0);
		table.setCellPadding(2);
		table.setCellSpacing(0);
		table.setWidth("100%");
		
		scrollPanel = new ScrollPanel(table);
		scrollPanel.setSize("388", "225");
		
		textArea = new TextArea();
		textArea.setSize("390", "50");
		textArea.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {		
				if ((char)KeyCodes.KEY_ENTER == event.getNativeKeyCode() && textArea.getText().length()==1) {
					// Case only has typewrite a enter character, reset textArea values
					textArea.setText("");
				} else 	if ((char)KeyCodes.KEY_ENTER == event.getNativeKeyCode() && textArea.getText().length()>1) {
					textArea.setEnabled(false);
					ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
					endPoint.setServiceEntryPoint(Config.OKMChatService);
					chatService.addMessageToRoom(room, formatingMessage(textArea.getText()), new AsyncCallback<Object>() {
						@Override
						public void onSuccess(Object result) {
							addMessage(Main.get().workspaceUserProperties.getUser() +": " + formatingMessage(textArea.getText()));
							textArea.setText("");
							textArea.setEnabled(true);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							textArea.setEnabled(true);
							Main.get().showError("AddMessageToRoom", caught);
						}
					});
				}
			}
		});
		
		vPanel.add(hPanel);
		HTML space = new HTML();
		vPanel.add(space);
		vPanel.add(scrollPanel);
		HTML space2 = new HTML();
		vPanel.add(space2);
		vPanel.add(textArea);
		HTML space3 = new HTML();
		vPanel.add(space3);
		vPanel.add(close);
		
		vPanel.setCellHeight(hPanel, "25");
		vPanel.setCellHeight(scrollPanel, "225");
		vPanel.setCellHeight(textArea, "50");
		vPanel.setCellHeight(space, "5");
		vPanel.setCellHeight(space2, "5");
		vPanel.setCellHeight(space3, "5");
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(textArea, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(close, HasAlignment.ALIGN_CENTER);
		vPanel.setCellVerticalAlignment(hPanel, HasAlignment.ALIGN_MIDDLE);
		vPanel.setCellVerticalAlignment(scrollPanel, HasAlignment.ALIGN_MIDDLE);
		vPanel.setCellVerticalAlignment(textArea, HasAlignment.ALIGN_MIDDLE);
		vPanel.setCellVerticalAlignment(close, HasAlignment.ALIGN_MIDDLE);
		
		scrollPanel.setStyleName("okm-PanelSelected");
		scrollPanel.addStyleName("okm-Input");
		textArea.setStyleName("okm-Input");
		close.setStyleName("okm-Button");
		
		vPanel.setWidth("400px");
		vPanel.setHeight("350px");
		
		setStyleName("okm-Popup");
		
		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		setText(Main.i18n("chat.room"));
		close.setHTML(Main.i18n("button.close"));
		refreshUsersInRoom();
	}
	
	/**
	 * refreshUsersInRoom()
	 */
	private void refreshUsersInRoom() {
		usersInRoomText.setHTML("(" + usersInRoom.size() +") " + Main.i18n("chat.users.in.room"));
	}
	
	/**
	 * getPendingMessage
	 * 
	 * @param room
	 */
	public void getPendingMessage(final String room) {
		ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
		endPoint.setServiceEntryPoint(Config.OKMChatService);
		chatService.getPendingMessage(room, new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> result) {
				for (Iterator<String> it = result.iterator(); it.hasNext();) {
					addMessage(it.next());
				}
				Timer timer = new Timer() {

					@Override
					public void run() {
						getPendingMessage(room);
					}
				};
				timer.schedule(DELAY);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Main.get().showError("getPendingMessage", caught);
			}
		});
	}
	
	/**
	 * addMessage
	 * 
	 * @param msg
	 */
	private void addMessage(String msg) {
		table.setHTML(table.getRowCount(),0,msg);
	}
	
	/**
	 * formatingMessage
	 * 
	 * @param msg
	 * @return
	 */
	private String formatingMessage(String msg) {
		return msg.replaceAll("\\n", "</br>");
	}
}