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

package es.git.openkm.frontend.client.widget.dashboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.center.Dashboard;


/**
 * HorizontalToolBar
 * 
 * @author jllort
 *
 */
public class HorizontalToolBar extends Composite implements MouseListener {
	private DashboardImageBundle dbImageBundle = (DashboardImageBundle) GWT.create(DashboardImageBundle.class);
	private HorizontalPanel hPanel;
	private ToolBarBox user;
	private ToolBarBox mail;
	private ToolBarBox news;
	private ToolBarBox general;
	private ToolBarBox workflow;
	private ToolBarBox keywordMap;
	private Widget enabledWidget;
	
	/**
	 * HorizontalToolBar
	 */
	public HorizontalToolBar() {
		hPanel = new HorizontalPanel();
		user = new ToolBarBox(dbImageBundle.userIcon(), Main.i18n("dashboard.tab.user"));
		mail = new ToolBarBox(dbImageBundle.mailIcon(), Main.i18n("dashboard.tab.mail"));
		news = new ToolBarBox(dbImageBundle.newsIcon(), Main.i18n("dashboard.tab.news"));
		general = new ToolBarBox(dbImageBundle.generalIcon(), Main.i18n("dashboard.tab.general"));
		workflow = new ToolBarBox(dbImageBundle.workflowIcon(), Main.i18n("dashboard.tab.workflow"));
		keywordMap = new ToolBarBox(dbImageBundle.keywordMapIcon(), Main.i18n("dashboard.tab.keymap"));
		
		enabledWidget = user; // Setting the enabled widget
		
		user.addMouseListener(this);
		mail.addMouseListener(this);
		news.addMouseListener(this);
		general.addMouseListener(this);
		workflow.addMouseListener(this);
		keywordMap.addMouseListener(this);
		
		user.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_USER);
			}
		});
		
		mail.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_MAIL);
			}
		});
		
		news.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_NEWS);
			}
		});
		
		general.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_GENERAL);
			}
		});
		
		workflow.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_WORKFLOW);
			}
		});
		
		keywordMap.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_KEYMAP);
			}
		});
		
		hPanel.add(user);
		hPanel.add(mail);
		hPanel.add(news);
		hPanel.add(general);
		hPanel.add(workflow);
		hPanel.add(keywordMap);
		HTML space = new HTML("&nbsp;");
		hPanel.add(space);
		
		hPanel.setCellWidth(user, "80");
		hPanel.setCellWidth(mail, "80");
		hPanel.setCellWidth(news, "80");
		hPanel.setCellWidth(general, "80");
		hPanel.setCellWidth(workflow, "80");
		hPanel.setCellWidth(keywordMap, "80");
		
		user.setStyleName("okm-ToolBar-Big-selected");
		hPanel.setStyleName("okm-ToolBar");
		hPanel.addStyleName("okm-ToolBar-Border");
		hPanel.addStyleName("okm-DisableSelect");
		
		initWidget(hPanel);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		user.setLabelText(Main.i18n("dashboard.tab.user"));
		mail.setLabelText(Main.i18n("dashboard.tab.mail"));
		news.setLabelText(Main.i18n("dashboard.tab.news"));
		general.setLabelText(Main.i18n("dashboard.tab.general"));
		workflow.setLabelText(Main.i18n("dashboard.tab.workflow"));
		keywordMap.setLabelText(Main.i18n("dashboard.tab.keymap"));
	}
	
	/**
	 * ToolBarBox
	 * 
	 * @author jllort
	 *
	 */
	private class ToolBarBox extends HorizontalPanel implements SourcesClickEvents, SourcesMouseEvents {
		
		private ClickListenerCollection clickListeners;
		private MouseListenerCollection mouseListeners;
		private VerticalPanel vPanel;
		private Image image;
		private HTML html;
		
		/**
		 * ToolBarBox
		 * 
		 * @param url
		 * @param text
		 */
		public ToolBarBox(AbstractImagePrototype img, String text) {
			super();
			sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
			
			vPanel = new VerticalPanel();
			HTML space1 = new HTML("&nbsp;");
			HTML space2 = new HTML("&nbsp;");
			image = img.createImage();
			html = new HTML(text);
			html.setText(text);
			html.setTitle(text);
			image.setTitle(text);
			
			vPanel.add(image);
			vPanel.add(html);
			
			add(space1);
			add(vPanel);
			add(space2);
			
			vPanel.setCellHorizontalAlignment(html, HasAlignment.ALIGN_CENTER);
			vPanel.setCellHorizontalAlignment(image, HasAlignment.ALIGN_CENTER);
			
			setCellVerticalAlignment(vPanel, HasAlignment.ALIGN_MIDDLE);
			setCellHorizontalAlignment(vPanel, HasAlignment.ALIGN_CENTER);
			
			setCellWidth(space1, "15");
			setCellWidth(space2, "15");
			
			html.setStyleName("okm-noWrap");
			
			setHeight("59");
			setWidth("100%");
		}
		
		/**
		 * setLabelText
		 * 
		 * @param text
		 */
		public void setLabelText(String text) {
			html.setText(text);
			html.setTitle(text);
			image.setTitle(text);
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SourcesClickEvents#addClickListener(com.google.gwt.user.client.ui.ClickListener)
		 */
		public void addClickListener(ClickListener listener) {
		    if (clickListeners == null) {
		      clickListeners = new ClickListenerCollection();
		    }
		    clickListeners.add(listener);
		  }

		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SourcesClickEvents#removeClickListener(com.google.gwt.user.client.ui.ClickListener)
		 */
		public void removeClickListener(ClickListener listener) {
			if (clickListeners != null) {
				clickListeners.remove(listener);
			}
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SourcesMouseEvents#addMouseListener(com.google.gwt.user.client.ui.MouseListener)
		 */
		public void addMouseListener(MouseListener listener) {
		    if (mouseListeners == null) {
		      mouseListeners = new MouseListenerCollection();
		    }
		    mouseListeners.add(listener);
		  }
		
		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SourcesMouseEvents#removeMouseListener(com.google.gwt.user.client.ui.MouseListener)
		 */
		public void removeMouseListener(MouseListener listener) {
		    if (mouseListeners != null) {
		      mouseListeners.remove(listener);
		    }
		  }
		
		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
		 */
		public void onBrowserEvent(Event event) {
		    switch (DOM.eventGetType(event)) {
		    	case Event.ONCLICK: {
		    		if (clickListeners != null) {
		    			clickListeners.fireClick(this);
		    		}
		    		break;
		    	}
		    	
		    	case Event.ONMOUSEDOWN:
		        case Event.ONMOUSEUP:
		        case Event.ONMOUSEMOVE:
		        case Event.ONMOUSEOVER:
		        case Event.ONMOUSEOUT: {
		          if (mouseListeners != null) {
		            mouseListeners.fireMouseEvent(this, event);
		          }
		          break;
		        }
		    	
		    }
		    super.onBrowserEvent(event);
		}
	}
	
	/**
	 * Show the user view
	 */
	public void showUserView() {
		enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
		user.setStyleName("okm-ToolBar-Big-selected");
		enabledWidget = user;
		Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_USER);
	}
	
	/**
	 * Shows the news view
	 */
	public void showNewsView() {
		enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
		news.setStyleName("okm-ToolBar-Big-selected");
		enabledWidget = news;
		Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_NEWS);
	}
	
	/**
	 * Shows the workflow view
	 */
	public void showWorkflowView() {
		enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
		workflow.setStyleName("okm-ToolBar-Big-selected");
		enabledWidget = news;
		Main.get().mainPanel.dashboard.changeView(Dashboard.DASHBOARD_WORKFLOW);
	}
	
	public void onMouseDown(Widget sender, int x, int y) {
		// TODO Auto-generated method stub
	}

	public void onMouseEnter(Widget sender) {
		sender.addStyleName("okm-ToolBar-BigTMP-selected");
	}

	public void onMouseLeave(Widget sender) {
		sender.removeStyleName("okm-ToolBar-BigTMP-selected");
	}

	public void onMouseMove(Widget sender, int x, int y) {
		// TODO Auto-generated method stub
	}

	public void onMouseUp(Widget sender, int x, int y) {
		// TODO Auto-generated method stub
	}
}