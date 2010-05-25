package com.openkm.frontend.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.panel.ExtendedDockPanel;
import com.openkm.frontend.client.service.OKMChatService;
import com.openkm.frontend.client.service.OKMChatServiceAsync;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.chat.ChatRoomDialogBox;
import com.openkm.frontend.client.widget.chat.ChatRoomPopup;

public class UserInfo extends Composite {
	
	private final OKMChatServiceAsync chatService = (OKMChatServiceAsync) GWT.create(OKMChatService.class);
	private static final int USERS_IN_ROOM_REFRESHING_TIME = 1000;
	private static final int NEW_ROOM_REFRESHING_TIME = 200;
	
	private HorizontalPanel panel;
	private Image advertisement;
	private HTML user;
	private String msg = "";
	private Image img;
	private HTML userRepositorySize;
	private Image imgRepositorySize;
	private HTML lockedDocuments;
	private Image imgLockedDocuments;
	private HTML checkoutDocuments;
	private Image imgCheckoutDocuments;
	private HTML subscriptions;
	private Image imgSubscriptions;
	private HTML newsDocuments;
	private Image imgNewsDocuments;
	private HTML newsWorkflows;	
	private Image imgWorkflows;
	private Image imgChat;
	private Image imgNewChatRoom;
	private boolean chatConnected = false;
	private HTML usersConnected;
	private List<String> connectUsersList;
	private List<ChatRoomDialogBox> chatRoomList;
	
	/**
	 * UserInfo
	 */
	public UserInfo() {
		connectUsersList = new ArrayList<String>();
		chatRoomList = new ArrayList<ChatRoomDialogBox>();
		img = new Image(OKMBundleResources.INSTANCE.openkmConnected());
		panel = new HorizontalPanel();
		panel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		user = new HTML("");
		userRepositorySize = new HTML("");
		usersConnected = new HTML(Main.i18n("user.info.chat.offline"));
		lockedDocuments = new HTML("");
		checkoutDocuments = new HTML("");
		subscriptions = new HTML("");
		newsDocuments = new HTML("");
		newsWorkflows = new HTML("");
		imgRepositorySize = new Image(OKMBundleResources.INSTANCE.repositorySize());
		imgChat = new Image(OKMBundleResources.INSTANCE.chatDisconnected());
		imgNewChatRoom = new Image(OKMBundleResources.INSTANCE.newChatRoom());
		imgLockedDocuments = new Image(OKMBundleResources.INSTANCE.lock());
		imgCheckoutDocuments = new Image(OKMBundleResources.INSTANCE.checkout());
		imgSubscriptions = new Image(OKMBundleResources.INSTANCE.subscribed());
		imgNewsDocuments = new Image(OKMBundleResources.INSTANCE.news());
		imgWorkflows = new Image(OKMBundleResources.INSTANCE.workflow());
		imgRepositorySize.setVisible(false);
		usersConnected.setVisible(false);
		imgNewChatRoom.setVisible(false);
		imgLockedDocuments.setVisible(false);
		imgCheckoutDocuments.setVisible(false);
		imgSubscriptions.setVisible(false);
		imgNewsDocuments.setVisible(false);
		imgWorkflows.setVisible(false);
		usersConnected.setTitle(Main.i18n("user.info.chat.connect"));
		imgNewChatRoom.setTitle(Main.i18n("user.info.chat.new.room"));
		imgLockedDocuments.setTitle(Main.i18n("user.info.locked.actual"));
		imgCheckoutDocuments.setTitle(Main.i18n("user.info.checkout.actual"));
		imgSubscriptions.setTitle(Main.i18n("user.info.subscription.actual"));
		imgNewsDocuments.setTitle(Main.i18n("user.info.news.new"));
		imgWorkflows.setTitle(Main.i18n("user.info.workflow.pending"));
		
		imgLockedDocuments.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DASHBOARD);
				Main.get().mainPanel.dashboard.horizontalToolBar.showUserView();
			}
		});
		
		imgCheckoutDocuments.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DASHBOARD);
				Main.get().mainPanel.dashboard.horizontalToolBar.showUserView();
			}
		});
		
		imgSubscriptions.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DASHBOARD);
				Main.get().mainPanel.dashboard.horizontalToolBar.showUserView();
			}
		});
		
		imgNewsDocuments.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DASHBOARD);
				Main.get().mainPanel.dashboard.horizontalToolBar.showNewsView();
			}
		});
		
		imgWorkflows.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DASHBOARD);
				Main.get().mainPanel.dashboard.horizontalToolBar.showWorkflowView();
			}
		});
		
		imgChat.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!chatConnected) {
					ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
					endPoint.setServiceEntryPoint(Config.OKMChatService);
					chatService.login(new AsyncCallback<Object>() {
						@Override
						public void onSuccess(Object result) {
							chatConnected = true;
							imgChat.setResource(OKMBundleResources.INSTANCE.chatConnected());
							usersConnected.setVisible(true);
							imgNewChatRoom.setVisible(true);
							refreshConnectedUsers();
							getPendingChatRoomUser();
						}
						@Override
						public void onFailure(Throwable caught) {
							Main.get().showError("GetLoginChat", caught);
						}
					});
				} else {
					ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
					endPoint.setServiceEntryPoint(Config.OKMChatService);
					chatService.logout(new AsyncCallback<Object>() {
						@Override
						public void onSuccess(Object result) {
							chatConnected = false;
							usersConnected.setVisible(false);
							imgNewChatRoom.setVisible(false);
							usersConnected.setHTML("");
							imgChat.setResource(OKMBundleResources.INSTANCE.chatDisconnected());
						}
						@Override
						public void onFailure(Throwable caught) {
							Main.get().showError("GetLogoutChat", caught);
						}
					});
				}
			}
		});
		
		imgNewChatRoom.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Main.get().onlineUsersPopup.center();
				Main.get().onlineUsersPopup.refreshOnlineUsers();
			}
		});
		
		advertisement = new Image(OKMBundleResources.INSTANCE.warning());
		advertisement.setVisible(false);
		
		advertisement.addClickHandler( new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Main.get().msgPopup.show("openkm.update.available", msg, 400, 200);
			}
		});
		
		panel.add(advertisement);
		panel.add(new HTML("&nbsp;"));
		panel.add(img);
		panel.add(new HTML("&nbsp;"));
		panel.add(user);
		panel.add(new Image(OKMBundleResources.INSTANCE.separator()));
		panel.add(new HTML("&nbsp;"));
		panel.add(imgRepositorySize);
		panel.add(new HTML("&nbsp;"));
		panel.add(userRepositorySize);
		panel.add(new Image(OKMBundleResources.INSTANCE.separator()));
		panel.add(new HTML("&nbsp;"));
		panel.add(imgChat);
		panel.add(new HTML("&nbsp;"));
		panel.add(imgNewChatRoom);
		panel.add(new HTML("&nbsp;"));
		panel.add(usersConnected);
		panel.add(new HTML("&nbsp;"));
		panel.add(new Image(OKMBundleResources.INSTANCE.separator()));
		panel.add(new HTML("&nbsp;"));
		panel.add(imgLockedDocuments);
		panel.add(new HTML("&nbsp;"));
		panel.add(lockedDocuments);
		panel.add(new HTML("&nbsp;"));
		panel.add(imgCheckoutDocuments);
		panel.add(new HTML("&nbsp;"));
		panel.add(checkoutDocuments);
		panel.add(new HTML("&nbsp;"));
		panel.add(imgSubscriptions);
		panel.add(new HTML("&nbsp;"));
		panel.add(subscriptions);
		panel.add(new HTML("&nbsp;"));
		panel.add(imgNewsDocuments);
		panel.add(newsDocuments);
		panel.add(new HTML("&nbsp;"));
		panel.add(imgWorkflows);
		panel.add(newsWorkflows);
		panel.add(new HTML("&nbsp;"));
		
		imgLockedDocuments.setStyleName("okm-Hyperlink");
		imgCheckoutDocuments.setStyleName("okm-Hyperlink");
		imgSubscriptions.setStyleName("okm-Hyperlink");
		imgNewsDocuments.setStyleName("okm-Hyperlink");
		imgWorkflows.setStyleName("okm-Hyperlink");
		imgChat.setStyleName("okm-Hyperlink");
		imgNewChatRoom.setStyleName("okm-Hyperlink");
		
		initWidget(panel);
	}
	
	/**
	 * Sets the user value 
	 * 
	 * @param user The user value
	 */
	public void setUser(String user, boolean isAdmin) {
		this.user.setHTML("&nbsp;"+Main.i18n("user.info.chat.online")+" "+user+ "&nbsp;");
		if (isAdmin) {
			this.user.addStyleName("okm-Input-System");
		} 
	}
	
	/**
	 * Sets the repository size
	 * 
	 * @param size
	 */
	public void setUserRepositorySize(double size) {
		imgRepositorySize.setVisible(true);
		userRepositorySize.setHTML("&nbsp;"+Util.formatSize(size)+ "&nbsp;");
	}
	
	/**
	 * Sets the locked documents 
	 * 
	 * @param value
	 */
	public void setLockedDocuments(int value) {
		imgLockedDocuments.setVisible(true);
		lockedDocuments.setHTML("&nbsp;"+value+ "&nbsp;");
	}
	
	/**e
	 * Sets the checkout documents
	 * 
	 * @param value
	 */
	public void setCheckoutDocuments(int value) {
		imgCheckoutDocuments.setVisible(true);
		checkoutDocuments.setHTML("&nbsp;"+value+ "&nbsp;");
	}
	
	/**
	 * Sets the subscriptions documents and folders 
	 * 
	 * @param value
	 */
	public void setSubscriptions(int value) {
		imgSubscriptions.setVisible(true);
		subscriptions.setHTML("&nbsp;"+value+ "&nbsp;");
	}
	
	/**
	 * Sets the news documents
	 * 
	 * @param value
	 */
	public void setNewsDocuments(int value) {
		imgNewsDocuments.setVisible(true);
		newsDocuments.setHTML("&nbsp;"+value+ "&nbsp;");
		if (value>0) {
			imgNewsDocuments.setResource(OKMBundleResources.INSTANCE.newsAlert());
		} else {
			imgNewsDocuments.setResource(OKMBundleResources.INSTANCE.news());
		}
	}
	
	/**
	 * Sets the news workflows
	 * 
	 * @param value
	 */
	public void setNewsWorkflows(int value) {
		imgWorkflows.setVisible(true);
		newsWorkflows.setHTML("&nbsp;"+value+ "&nbsp;");
		if (value>0) {
			imgWorkflows.setResource(OKMBundleResources.INSTANCE.workflowAlert());
		} else {
			imgWorkflows.setResource(OKMBundleResources.INSTANCE.workflow());
		}
	}
	
	/**
	 * Sets the msg value 
	 * 
	 * @param msg The msg value
	 */
	public void setUpdateMessage(String msg) {
		if (msg!=null && !msg.equals("")) {
			advertisement.setVisible(true);
			this.msg = msg;
		}
	}

	/**
	 * langRefresh
	 */
	public void langRefresh() {
		if (chatConnected) {
			user.setHTML("&nbsp;"+ Main.i18n("user.info.chat.online")+" "+Main.get().workspaceUserProperties.getUser() + "&nbsp;");
		} else {
			user.setHTML("&nbsp;"+ Main.i18n("user.info.chat.offline")+" "+Main.get().workspaceUserProperties.getUser() + "&nbsp;");
		}
		if (chatConnected) {
			usersConnected.setTitle(Main.i18n("user.info.chat.disconnect"));
		} else {
			usersConnected.setTitle(Main.i18n("user.info.chat.connect"));
		}
		imgNewChatRoom.setTitle(Main.i18n("user.info.chat.new.room"));
		imgLockedDocuments.setTitle(Main.i18n("user.info.locked.actual"));
		imgCheckoutDocuments.setTitle(Main.i18n("user.info.checkout.actual"));
		imgSubscriptions.setTitle(Main.i18n("user.info.subscription.actual"));
		imgNewsDocuments.setTitle(Main.i18n("user.info.news.new"));
		imgWorkflows.setTitle(Main.i18n("user.info.workflow.pending"));
		
		// Resfreshing actual chatrooms
		for (Iterator<ChatRoomDialogBox> it = chatRoomList.iterator(); it.hasNext();) {
			it.next().langRefresh();
		}
	}
	
	/**
	 * refreshConnectedUsers
	 */
	private void refreshConnectedUsers() {
		if (chatConnected) {
			ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
			endPoint.setServiceEntryPoint(Config.OKMChatService);
			chatService.getLogedUsers(new AsyncCallback<List<String>>() {
				@Override
				public void onSuccess(List<String> result) {
					connectUsersList = result;
					usersConnected.setHTML("(" + result.size() +") Connected");
					Timer timer = new Timer() {
						@Override
						public void run() {
							refreshConnectedUsers();
						}
					};
					timer.schedule(USERS_IN_ROOM_REFRESHING_TIME); // Each minute seconds refreshing connected users
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Main.get().showError("GetLogedUsers", caught);
				}
			});
		}
	}
	
	/**
	 * getPendingChatRoomUser
	 */
	private void getPendingChatRoomUser() {
		if (chatConnected) {
			ServiceDefTarget endPoint = (ServiceDefTarget) chatService;
			endPoint.setServiceEntryPoint(Config.OKMChatService);
			chatService.getPendingChatRoomUser(new AsyncCallback<List<String>>() {
				
				@Override
				public void onSuccess(List<String> result) {
					for (Iterator<String> it = result.iterator(); it.hasNext();) {
						String room = it.next();
						ChatRoomPopup chatRoomPopup = new ChatRoomPopup("",room);
						chatRoomPopup.center();
						chatRoomPopup.getPendingMessage(room);
						addChatRoom(chatRoomPopup);
					}
					
					Timer timer = new Timer() {
						@Override
						public void run() {
							getPendingChatRoomUser();
						}
					};
					timer.schedule(NEW_ROOM_REFRESHING_TIME); // Each minute seconds refreshing connected users
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Main.get().showError("GetLogedUsers", caught);
				}
			});
		}
	}
	
	/**
	 * getConnectedUserList
	 * 
	 * @return
	 */
	public List<String> getConnectedUserList() {
		return connectUsersList;
	}
	
	/**
	 * addChatRoom
	 * 
	 * @param chatRoom
	 */
	public void addChatRoom(ChatRoomDialogBox chatRoom) {
		if (!chatRoomList.contains(chatRoom)) {
			chatRoomList.add(chatRoom);
		}
	}
	
	/**
	 * removeChatRoom
	 * 
	 * @param chatRoom
	 */
	public void removeChatRoom(ChatRoomDialogBox chatRoom) {
		if (chatRoomList.contains(chatRoom)) {
			chatRoomList.remove(chatRoom);
		}
	}
}
