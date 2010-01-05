package es.git.openkm.frontend.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedDockPanel;
import es.git.openkm.frontend.client.util.Util;

public class UserInfo extends Composite {
	
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
	
	/**
	 * UserInfo
	 */
	public UserInfo() {
		img = new Image("img/icon/connect.gif");
		panel = new HorizontalPanel();
		panel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		user = new HTML("");
		userRepositorySize = new HTML("");
		lockedDocuments = new HTML("");
		checkoutDocuments = new HTML("");
		subscriptions = new HTML("");
		newsDocuments = new HTML("");
		newsWorkflows = new HTML("");
		imgRepositorySize = new Image("img/icon/user_repository.gif");
		imgLockedDocuments = new Image("img/icon/lock.gif");
		imgCheckoutDocuments = new Image("img/icon/edit.gif");
		imgSubscriptions = new Image("img/icon/subscribed.gif");
		imgNewsDocuments = new Image("img/icon/news.gif");
		imgWorkflows = new Image("img/icon/workflow.gif");
		imgRepositorySize.setVisible(false);
		imgLockedDocuments.setVisible(false);
		imgCheckoutDocuments.setVisible(false);
		imgSubscriptions.setVisible(false);
		imgNewsDocuments.setVisible(false);
		imgWorkflows.setVisible(false);
		
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
		
		
		advertisement = new Image("img/icon/actions/warning.gif");
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
		panel.add(new HTML("&nbsp;"));
		panel.add(imgRepositorySize);
		panel.add(new HTML("&nbsp;"));
		panel.add(userRepositorySize);
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
		
		initWidget(panel);
	}
	
	/**
	 * Sets the user value 
	 * 
	 * @param user The user value
	 */
	public void setUser(String user, boolean isAdmin) {
		this.user.setHTML("&nbsp;"+Main.i18n("general.connected")+" "+user+ "&nbsp;");
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
			imgNewsDocuments.setUrl("img/icon/news_alert.gif");
		} else {
			imgNewsDocuments.setUrl("img/icon/news.gif");
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
			imgWorkflows.setUrl("img/icon/workflow_alert.gif");
		} else {
			imgWorkflows.setUrl("img/icon/workflow.gif");
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
		this.user.setHTML("&nbsp;"+ Main.i18n("general.connected")+" "+Main.get().workspaceUserProperties.getUser() + "&nbsp;");
	}
}
