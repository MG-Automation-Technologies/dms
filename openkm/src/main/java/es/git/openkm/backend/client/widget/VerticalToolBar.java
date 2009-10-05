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

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.panel.CenterPanel;

public class VerticalToolBar extends Composite implements MouseListener {
	private AdminImageBundle admImageBundle = (AdminImageBundle) GWT.create(AdminImageBundle.class);
	private VerticalPanel vPanel;
	private ToolBarBox advancedSearchUtil;
	private ToolBarBox usersUtil;
	private ToolBarBox generalUtils;
	private ToolBarBox propertyGroupsUtil;
	private ToolBarBox statsUtil;
	private ToolBarBox workflowUtil;
	private ToolBarBox config;
	private Widget enabledWidget;
	
	public VerticalToolBar() {
		vPanel = new VerticalPanel();
		statsUtil = new ToolBarBox(admImageBundle.homeIcon(), Main.i18n("toolbar.general"));
		advancedSearchUtil = new ToolBarBox(admImageBundle.searchIcon(), Main.i18n("toolbar.search"));
		usersUtil = new ToolBarBox(admImageBundle.usersIcon(), Main.i18n("toolbar.users"));
		generalUtils = new ToolBarBox(admImageBundle.utilsIcon(), Main.i18n("toolbar.utilities"));
		propertyGroupsUtil = new ToolBarBox(admImageBundle.propertiesIcon(), Main.i18n("toolbar.properties"));
		workflowUtil = new ToolBarBox(admImageBundle.workflowIcon(), Main.i18n("toolbar.workflow"));
		config = new ToolBarBox(admImageBundle.configIcon(), Main.i18n("toolbar.config"));
		
		enabledWidget = statsUtil; // Setting the enabled widget
		
 		statsUtil.setStyleName("okm-ToolBar-Big-selected");
		
		statsUtil.addMouseListener(this);
		advancedSearchUtil.addMouseListener(this);
		usersUtil.addMouseListener(this);
		generalUtils.addMouseListener(this);
		propertyGroupsUtil.addMouseListener(this);
		workflowUtil.addMouseListener(this);
		config.addMouseListener(this);
		
		statsUtil.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_STATS);
			}
		});
		
		advancedSearchUtil.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_INDEX_ADVANCED_SEARCH);
			}
		});
		
		usersUtil.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_INDEX_USERS);
			}
		});
		
		generalUtils.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_INDEX_GENERAL_UTILS);
			}
		});
		
		propertyGroupsUtil.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_PROPERTY_GROUPS);
			}
		});
		
		workflowUtil.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_WORKFLOW);
			}
		});

		config.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_CONFIG);
			}
		});

		vPanel.add(statsUtil);
		vPanel.add(advancedSearchUtil);
		vPanel.add(usersUtil);
		vPanel.add(generalUtils);
		vPanel.add(propertyGroupsUtil);
		vPanel.add(workflowUtil);
		vPanel.add(config);
		HTML space1 = new HTML("");
		vPanel.add(space1);
		
		vPanel.setCellHorizontalAlignment(statsUtil, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(advancedSearchUtil, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(usersUtil, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(generalUtils, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(propertyGroupsUtil, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(workflowUtil, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(config, HasAlignment.ALIGN_CENTER);
		
		vPanel.setCellHeight(statsUtil, "50");
		vPanel.setCellHeight(advancedSearchUtil, "50");
		vPanel.setCellHeight(usersUtil, "50");
		vPanel.setCellHeight(generalUtils, "50");
		vPanel.setCellHeight(propertyGroupsUtil, "50");
		vPanel.setCellHeight(workflowUtil, "50");
		vPanel.setCellHeight(config, "50");
			
		initWidget(vPanel);
	}
	
	/**
	 * ToolBarBox
	 * 
	 * @author jllort
	 *
	 */
	private class ToolBarBox extends VerticalPanel implements SourcesClickEvents, SourcesMouseEvents {
		
		private ClickListenerCollection clickListeners;
		private MouseListenerCollection mouseListeners;
		private HorizontalPanel hPanel;
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
			hPanel = new HorizontalPanel();
			HTML hSpace1 = new HTML("&nbsp;");
			HTML hSpace2 = new HTML("&nbsp;");
			image = img.createImage();
			html = new HTML(text);
			html.setText(text);
			html.setTitle(text);
			image.setTitle(text);
			
			vPanel.add(image);
			vPanel.add(html);
			
			hPanel.add(hSpace1);
			hPanel.add(vPanel);
			hPanel.add(hSpace2);
			
			vPanel.setCellHorizontalAlignment(html, HasAlignment.ALIGN_CENTER);
			vPanel.setCellHorizontalAlignment(image, HasAlignment.ALIGN_CENTER);
			
			hPanel.setCellHorizontalAlignment(vPanel, HasAlignment.ALIGN_CENTER);
			hPanel.setCellVerticalAlignment(vPanel, HasAlignment.ALIGN_MIDDLE);
			
			vPanel.setCellWidth(hSpace1, "5");
			vPanel.setCellWidth(hSpace2, "5");
			hPanel.setHeight("50");
			vPanel.setWidth("100%");
			hPanel.setWidth("100%");
			
			html.setStyleName("okm-noWrap");
			
			HTML vSpace1 = new HTML("&nbsp;");
			HTML vSpace2 = new HTML("&nbsp;");
			
			add(vSpace1);
			add(hPanel);
			add(vSpace2);
			
			setCellWidth(vSpace1, "5");
			setCellWidth(vSpace2, "5");
			
			setHeight("75");
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