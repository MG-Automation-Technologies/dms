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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;

/**
 * WorkflowWidget
 * 
 * @author jllort
 *
 */
public class WorkflowWidget extends Composite {

	private static int HEADER_SQUARE = 24;
	private static int SEPARATOR_HEIGHT = 20;
	private static int SEPARATOR_WIDTH = 20;
	
	private VerticalPanel vPanel;
	private SimplePanel spTop;
	private HorizontalPanel hPanel;
	private SimplePanel spLeft;
	private VerticalPanel vCenterPanel;
	private SimplePanel spRight;
	private Header header;
	private SimplePanel panelData;
	private FlexTable table;
	private Image zoomImage;
	// private Image viewedImage;
	private boolean zoom = false;
	private boolean flagZoom = true;
	private List<GWTTaskInstance> lastTaskInstanceList = new ArrayList<GWTTaskInstance>();
	private WidgetToFire widgetToFire;
	public Status status;
	private String headerTextKey;
	
	/**
	 * WorkflowWidget
	 */
	public WorkflowWidget(String headerTextKey, String iconUrl, boolean zoom) {
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		spTop = new SimplePanel();
		spLeft = new SimplePanel();
		spRight = new SimplePanel();
		panelData = new SimplePanel();
		table = new FlexTable();
		vCenterPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		header = new Header(iconUrl, zoom);
		vPanel = new VerticalPanel();
		this.headerTextKey = headerTextKey;
		
		// Sets or unsets visible table
		table.setVisible(zoom);
		
		header.setHeaderText(Main.i18n(headerTextKey));
		
		panelData.add(table);
		
		vCenterPanel.add(header);
		vCenterPanel.add(panelData);
		
		hPanel.add(spLeft);
		hPanel.add(vCenterPanel);
		hPanel.add(spRight);
		
		vPanel.add(spTop);
		vPanel.add(hPanel);
		
		spTop.setHeight(""+SEPARATOR_HEIGHT);
		spLeft.setWidth(""+SEPARATOR_WIDTH);
		spRight.setWidth(""+SEPARATOR_WIDTH);
		
		vPanel.setStyleName("okm-DashboardWidget ");
		panelData.setStyleName("data");
		table.setStyleName("okm-NoWrap");
		
		panelData.setWidth("99.6%");
		header.setWidth("100%");
		
		table.setCellPadding(0);
		table.setCellSpacing(0);
		
		vPanel.addStyleName("okm-DisableSelect");
		
		initWidget(vPanel);
	}
	
	/**
	 * Sets the widget to be fired
	 * 
	 * @param widgetToFire
	 */
	public void setWidgetToFire(WidgetToFire widgetToFire){
		this.widgetToFire = widgetToFire;
	}
	
	/**
	 * setHeaderText
	 * 
	 * @param text
	 */
	public void setHeaderText(String text) {
		header.setHeaderText(text);
	}
	
	/**
	 * setHeaderResults
	 * 
	 * @param value
	 */
	public void setHeaderResults(int value) {
		header.setHeaderResults(value);
	}
	
	/**
	 * setWidth
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		vCenterPanel.setWidth(""+(width-2*SEPARATOR_WIDTH));
	}
	
	/**
	 * removeAllRows
	 */
	private void removeAllRows() {
		while (table.getRowCount()>0) {
			table.removeRow(0);
		}
	}
	
	/**
	 * Setting documents
	 * 
	 * @param docList document list
	 */
	public void setTasks(List<GWTTaskInstance> taskIntanceList) {
		int tasksNotViewed = 0;
		removeAllRows();
		
		for (ListIterator<GWTTaskInstance> it = taskIntanceList.listIterator(); it.hasNext();) {
			int row = table.getRowCount();
			final GWTTaskInstance taskInstanceResult = it.next();
			Hyperlink taskName = new Hyperlink();
			taskName.setText(taskInstanceResult.getName());
			taskName.setTitle(taskInstanceResult.getProcessInstance().getProcessDefinition().getName());
			taskName.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					Main.get().mainPanel.dashboard.workflowDashboard.workflowFormPanel.setTaskInstance(taskInstanceResult);
				}
			});
			taskName.setStyleName("okm-Hyperlink");
						
			table.setHTML(row, 0, "");
			table.setWidget(row, 1, taskName);
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			table.setHTML(row, 2, dtf.format(taskInstanceResult.getCreate()));
			table.getCellFormatter().setWidth(row, 0, "20"); 
			table.getCellFormatter().setWidth(row, 1, "100%"); // Table sets de 100% of space
			table.getCellFormatter().setHorizontalAlignment(row, 2, HasAlignment.ALIGN_RIGHT);
			
			tasksNotViewed++;
			table.getRowFormatter().setStyleName(row, "okm-NotViewed"); 
		}
		
		header.setHeaderNotViewedResults(tasksNotViewed);
		lastTaskInstanceList = taskIntanceList; // Saves actual list
	}
	
	/**
	 * getNotViewed
	 * 
	 * @return
	 */
	public int getNotViewed() {
		return header.getNotViewed();
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		header.setHeaderText(Main.i18n(headerTextKey));
		header.setHeaderNotViewedResults(header.getNotViewed());
	}
	
	/**
	 * Header
	 * 
	 * @author jllort
	 *
	 */
	private class Header extends HorizontalPanel implements SourcesClickEvents {
		
		private ClickListenerCollection clickListeners;
		private SimplePanel spLeft;
		private SimplePanel spRight;
		private SimplePanel iconImagePanel;
		private HorizontalPanel center;
		private HorizontalPanel titlePanel;
		private HTML headerText;
		private HTML headerResults;
		private HTML headerNotViewedResults;
		private int notViewed = 0;
		private Image iconImage;
		
		/**
		 * Header
		 */
		public Header(String iconUrl, boolean visible) {
			super();
			sinkEvents(Event.ONCLICK);
			
			iconImage = new Image(iconUrl);
			
			zoom = visible;
			if (zoom) {
				zoomImage = new Image("img/zoom_out.gif");
			} else {
				zoomImage = new Image("img/zoom_in.gif");
			}
			zoomImage.setStyleName("okm-Hyperlink");
			
//			viewedImage = new Image("img/viewed.gif");
//			viewedImage.setStyleName("okm-Hyperlink");
//			
//			viewedImage.addClickListener(new ClickListener() {
//				public void onClick(Widget sender) {
//					flagZoom = false;
//					//markAllRowsAsViewed();
//				}
//			});
			
			addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					if (flagZoom) {
						zoom = !zoom;
						table.setVisible(zoom);
						if (zoom) {
							zoomImage.setUrl("img/zoom_out.gif");
						} else {
							zoomImage.setUrl("img/zoom_in.gif");
						}
					} else {
						flagZoom = true;
					}
				}
			});
			
			setHeight(""+HEADER_SQUARE);
			
			spLeft = new SimplePanel();
			spRight = new SimplePanel();
			iconImagePanel = new SimplePanel();
			center = new HorizontalPanel();
			titlePanel = new HorizontalPanel();
			headerText = new HTML("");
			headerResults = new HTML("");
			headerNotViewedResults = new HTML("");
			
			iconImagePanel.add(iconImage);
			
			titlePanel.add(headerText);
			titlePanel.add(headerResults);
			
			center.add(iconImagePanel);
			center.add(titlePanel);
			center.add(headerNotViewedResults);
			//center.add(viewedImage);
			center.add(zoomImage);
			
			spLeft.setSize(""+HEADER_SQUARE, ""+HEADER_SQUARE);
			center.setWidth("100%");
			center.setCellVerticalAlignment(iconImagePanel, HasAlignment.ALIGN_MIDDLE);
			center.setCellHorizontalAlignment(iconImagePanel, HasAlignment.ALIGN_LEFT);
			//center.setCellHorizontalAlignment(viewedImage, HasAlignment.ALIGN_LEFT);
			center.setCellHorizontalAlignment(zoomImage, HasAlignment.ALIGN_RIGHT);
			center.setCellVerticalAlignment(titlePanel, HasAlignment.ALIGN_MIDDLE);
			center.setCellVerticalAlignment(headerNotViewedResults, HasAlignment.ALIGN_MIDDLE);
			center.setCellHorizontalAlignment(headerNotViewedResults, HasAlignment.ALIGN_RIGHT);
	
			//center.setCellVerticalAlignment(viewedImage, HasAlignment.ALIGN_MIDDLE);
			center.setCellVerticalAlignment(zoomImage, HasAlignment.ALIGN_MIDDLE);
			center.setCellWidth(iconImagePanel, "22");
			//center.setCellWidth(viewedImage, "22");
			center.setCellWidth(zoomImage, "16");
			center.setHeight(""+HEADER_SQUARE);
			spRight.setSize(""+HEADER_SQUARE, ""+HEADER_SQUARE);
			
			titlePanel.setCellVerticalAlignment(headerResults, HasAlignment.ALIGN_MIDDLE);
			titlePanel.setCellVerticalAlignment(headerNotViewedResults, HasAlignment.ALIGN_MIDDLE);
			titlePanel.setCellHorizontalAlignment(headerResults, HasAlignment.ALIGN_LEFT);
			titlePanel.setCellHorizontalAlignment(headerNotViewedResults, HasAlignment.ALIGN_LEFT);
			
			add(spLeft);
			add(center);
			add(spRight);
			
			spLeft.setStyleName("topLeft");
			center.setStyleName("topCenter");
			spRight.setStyleName("topRight");
			
			setCellWidth(spLeft, ""+HEADER_SQUARE);
			setCellWidth(spRight, ""+HEADER_SQUARE);
			setCellVerticalAlignment(center, HasAlignment.ALIGN_MIDDLE);
		}
		
		/**
		 * setHeaderText
		 * 
		 * @param text
		 */
		public void setHeaderText(String text) {
			headerText.setHTML(text);
		}
		
		/**
		 * setHeaderResults
		 * 
		 * @param value
		 */
		public void setHeaderResults(int value) {
			headerResults.setHTML("&nbsp;&nbsp;(" + value + ")&nbsp;&nbsp;");
		}
		
		/**
		 * setHeaderNotViewedResults
		 * 
		 * @param value
		 */
		public void setHeaderNotViewedResults(int value) {
			notViewed = value;
			if (value>0) {
				headerNotViewedResults.setHTML("&nbsp;" + value + "&nbsp;&nbsp;");
				titlePanel.setStyleName("okm-NotViewed");
				headerNotViewedResults.setStyleName("okm-NotViewed");
				//viewedImage.setUrl("img/viewed_pending.gif");
				
			} else {
				headerNotViewedResults.setHTML("");
				titlePanel.removeStyleName("okm-NotViewed");
				headerNotViewedResults.removeStyleName("okm-NotViewed");
				//viewedImage.setUrl("img/viewed.gif");
			}
		}
		
		/**
		 * Decrements viewed
		 */
		public void decrementNotViewed(int value) {
			notViewed -= value;
			setHeaderNotViewedResults(notViewed);
		}
		
		/**
		 * getNotViewed
		 * 
		 * @return
		 */
		public int getNotViewed() {
			return notViewed;
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
		    }
		    super.onBrowserEvent(event);
		}
	}
	
	/**
	 * Sets the refreshing
	 */
	public void setRefreshing() {
		int left = getAbsoluteLeft() + ((getOffsetWidth()-status.getOffsetWidth())/2);
		int top = getAbsoluteTop() + ((getOffsetHeight()-status.getOffsetHeight())/2);
		status.setFlag_getDashboard();
		if (zoom) {
			status.refresh(left, top);
		}
	}
	
	/**
	 * Unsets the refreshing
	 */
	public void unsetRefreshing() {
		status.unsetFlag_getDashboard();
	}
}
