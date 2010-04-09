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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTKeyword;

/**
 * KeywordWidget
 * 
 * @author jllort
 *
 */
public class KeywordWidget extends Composite {
	
	private VerticalPanel vPanel; 
	private FlexTable table;
	private Header header;
	private Map<String, String> selectedMap = new HashMap<String, String>();
	private Map<String, String> keywordTableMap = new HashMap<String, String>();
	public Status status;
	private boolean zoom = false;
	
	/**
	 * KeywordWidget
	 */
	public KeywordWidget(String text) {
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		vPanel = new VerticalPanel();
		header = new Header(true, text);
		table = new FlexTable();
		
		table.addTableListener(new TableListener(){

			public void onCellClicked(SourcesTableEvents sender, int row, int scol) {
				String keyword = table.getHTML(row, 0);
				if (selectedMap.keySet().contains(keyword)) {
					styleRow(row, false);
					selectedMap.remove(keyword);
					Main.get().mainPanel.dashboard.keyMapDashboard.removeKey(keyword);
				} else {
					selectedMap.put(keyword, ""+row);
					styleRow(row, true);
					Main.get().mainPanel.dashboard.keyMapDashboard.selectKey(keyword);
				}
			}
		});
		
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.setWidth("100%");
		
		vPanel.add(header);
		vPanel.add(table);
		
		header.setSize("100%", "30");
		vPanel.setWidth("100%");
		vPanel.setCellHeight(header, "30");
		
		header.setStyleName("okm-KeywordWidget-Header");
		
		initWidget(vPanel);
	}
	
	/**
	 * @param keyword
	 */
	public void selectRow(String keyword) {
		if (keywordTableMap.keySet().contains(keyword) && !selectedMap.keySet().contains(keyword)) {
			int row = Integer.valueOf(keywordTableMap.get(keyword));
			selectedMap.put(keyword, ""+row);
			styleRow(row, true);
		}
	}
	
	/**
	 * Unselect the keyword row
	 * @param keyword
	 */
	public void unselectRow(String keyword) {
		if (selectedMap.keySet().contains(keyword)) {
			int row = Integer.valueOf(selectedMap.get(keyword));
			selectedMap.remove(keyword);
			styleRow(row, false);
		}
	}
	
	/**
	 * Unselect all rows
	 */
	public void unselectAllRows() {
		for (Iterator<String> it = selectedMap.keySet().iterator(); it.hasNext();) {
			int row = Integer.valueOf(selectedMap.get(it.next()));
			styleRow(row, false);
		}
		selectedMap = new HashMap<String, String>();
	}
	
	/**
	 * Adds a keyword
	 * 
	 * @param keyword
	 */
	public void add(GWTKeyword keyword) {
		int row = table.getRowCount();
		table.setHTML(row, 0, keyword.getKeyword());
		table.setHTML(row, 1, ""+keyword.getFrequency());
		table.getRowFormatter().setStyleName(row, "okm-Table-Row");
		keywordTableMap.put(keyword.getKeyword(), ""+row);
	}
	
	/**
	 * Removes all rows
	 */
	private void removeAllRows() {
		while (table.getRowCount()>0) {
			table.removeRow(0);
		}
	}
	
	/**
	 * Initializes table values
	 */
	public void reset() {
		selectedMap = new HashMap<String, String>();
		keywordTableMap = new HashMap<String, String>();
		removeAllRows();
	}
	
	/**
	 * Sets the header text
	 * 
	 * @param text
	 */
	public void setHeaderText(String text) {
		selectedMap = new HashMap<String, String>();
		header.setHeaderText(text);
	}
	
	/**
	 * Change the style row selected or unselected
	 * 
	 * @param row The row afected
	 * @param selected Indicates selected unselected row
	 */
	private void styleRow(int row, boolean selected) {
		// Ensures that header is never changed
	    if (selected) {
	        table.getRowFormatter().addStyleName(row, "okm-Table-SelectedRow");
	    } else {
	    	table.getRowFormatter().removeStyleName(row, "okm-Table-SelectedRow");
	    }
	 }
	
	/**
	 * Header
	 * 
	 * @author jllort
	 *
	 */
	private class Header extends HorizontalPanel implements SourcesClickEvents {
		
		private ClickListenerCollection clickListeners;
		private Image zoomImage;
		private HTML headerText;
		
		/**
		 * Header
		 */
		public Header(boolean visible, String text) {
			super();
			sinkEvents(Event.ONCLICK);
			headerText = new HTML();
			headerText.setStyleName("okm-noWrap");
			setHeaderText(text);
			
			zoom = visible;
			if (zoom) {
				zoomImage = new Image("img/zoom_out.gif");
			} else {
				zoomImage = new Image("img/zoom_in.gif");
			}
			zoomImage.setStyleName("okm-Hyperlink");
			
			this.add(zoomImage);
			this.add(headerText);
			
			setCellWidth(zoomImage, "30");
			this.setCellHorizontalAlignment(zoomImage, HasAlignment.ALIGN_CENTER);
			setCellVerticalAlignment(zoomImage, HasAlignment.ALIGN_MIDDLE);
			setCellVerticalAlignment(headerText, HasAlignment.ALIGN_MIDDLE);
			
			addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					zoom = !zoom;
					table.setVisible(zoom);
					if (zoom) {
						zoomImage.setUrl("img/zoom_out.gif");
					} else {
						zoomImage.setUrl("img/zoom_in.gif");
					}
				}
			});
		}
		
		/**
		 * setHeaderText
		 * 
		 * @param text
		 */
		public void setHeaderText(String text) {
			headerText.setHTML("<b>" + text + "</b>");
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
		int left = getAbsoluteLeft() + (getOffsetWidth()/2);
		int top = getAbsoluteTop() + (getOffsetHeight()/2);
		if (zoom) {
			status.setFlag_getDashboard();
		}
		status.refresh(left, top);
	}
	
	/**
	 * Unsets the refreshing
	 */
	public void unsetRefreshing() {
		status.unsetFlag_getDashboard();
	}
	
	/**
	 * Increase the keyword rate with one
	 * 
	 * @param keyword The keyword to change rate
	 */
	public void increaseKeywordRate(String keyword, boolean add) {
		if (keywordTableMap.containsKey(keyword)) {
			int row = Integer.valueOf(keywordTableMap.get(keyword));
			int value = Integer.valueOf(table.getHTML(row, 1)) + 1;
			table.setHTML(row, 1, ""+value);
		} else if (add) {
			GWTKeyword key = new GWTKeyword();
			key.setFrequency(1);
			key.setKeyword(keyword);
			key.setTop10(false);
			add(key);
		}
	}
	
	/**
	 * Decrease the keyword rate with one
	 * 
	 * @param keyword The keyword to change rate
	 */
	public void decreaseKeywordRate(String keyword) {
		if (keywordTableMap.containsKey(keyword)) {
			int row = Integer.valueOf(keywordTableMap.get(keyword));
			int value = Integer.valueOf(table.getHTML(row, 1)) - 1;
			if (value>0) {
				table.setHTML(row, 1, ""+value);
			} else {
				// This case is not possible in KeyMapDashBoard controls case <=0 
				table.setHTML(row, 1, "0");
			}
		}
	}
}