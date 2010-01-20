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


package com.openkm.backend.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.panel.CenterPanel;

public class VerticalToolBar extends Composite {
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
		
		statsUtil.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		statsUtil.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		advancedSearchUtil.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		advancedSearchUtil.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});

		usersUtil.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		usersUtil.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		generalUtils.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		generalUtils.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		propertyGroupsUtil.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		propertyGroupsUtil.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});

		workflowUtil.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		workflowUtil.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		config.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		config.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-BigTMP-selected");
			}
		});
		
		statsUtil.addClickHandler(new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			    Widget sender = (Widget) event.getSource();
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_STATS);
			}
		});
		
		advancedSearchUtil.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Widget sender = (Widget) event.getSource();
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_INDEX_ADVANCED_SEARCH);
			}
		});
		
		usersUtil.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Widget sender = (Widget) event.getSource();
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_INDEX_USERS);
			}
		});
		
		generalUtils.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Widget sender = (Widget) event.getSource();
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_INDEX_GENERAL_UTILS);
			}
		});
		
		propertyGroupsUtil.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Widget sender = (Widget) event.getSource();
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_PROPERTY_GROUPS);
			}
		});
		
		workflowUtil.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Widget sender = (Widget) event.getSource();
				enabledWidget.removeStyleName("okm-ToolBar-Big-selected");
				sender.setStyleName("okm-ToolBar-Big-selected");
				enabledWidget = sender;
				Main.get().centerPanel.setVisiblePanel(CenterPanel.PANEL_WORKFLOW);
			}
		});

		config.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Widget sender = (Widget) event.getSource();
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
	private class ToolBarBox extends VerticalPanel implements HasClickHandlers, HasAllMouseHandlers {
		
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
			
			vPanel.setCellHeight(hSpace1, "5");
			vPanel.setCellHeight(hSpace2, "5");
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
			
			setHeight("50");
			setWidth("100%");
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
		 */
		@Override
		public HandlerRegistration addClickHandler(ClickHandler handler) {
			return addHandler(handler, ClickEvent.getType());
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasMouseDownHandlers#addMouseDownHandler(com.google.gwt.event.dom.client.MouseDownHandler)
		 */
		public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		    return addDomHandler(handler, MouseDownEvent.getType());
		}
		  
		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasMouseMoveHandlers#addMouseMoveHandler(com.google.gwt.event.dom.client.MouseMoveHandler)
		 */
		public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
			return addDomHandler(handler, MouseMoveEvent.getType());
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasMouseOutHandlers#addMouseOutHandler(com.google.gwt.event.dom.client.MouseOutHandler)
		 */
		public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		    return addDomHandler(handler, MouseOutEvent.getType());
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasMouseOverHandlers#addMouseOverHandler(com.google.gwt.event.dom.client.MouseOverHandler)
		 */
		public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		    return addDomHandler(handler, MouseOverEvent.getType());
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasMouseUpHandlers#addMouseUpHandler(com.google.gwt.event.dom.client.MouseUpHandler)
		 */
		public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		    return addDomHandler(handler, MouseUpEvent.getType());
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasMouseWheelHandlers#addMouseWheelHandler(com.google.gwt.event.dom.client.MouseWheelHandler)
		 */
		public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		    return addDomHandler(handler, MouseWheelEvent.getType());
		}
	}
}