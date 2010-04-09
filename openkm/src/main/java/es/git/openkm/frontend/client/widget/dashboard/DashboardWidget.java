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
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
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
import es.git.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsFolderResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsMailResult;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMDashboardService;
import es.git.openkm.frontend.client.service.OKMDashboardServiceAsync;
import es.git.openkm.frontend.client.util.CommonUI;
import es.git.openkm.frontend.client.util.Util;

/**
 * DashboardWidget
 * 
 * @author jllort
 *
 */
public class DashboardWidget extends Composite {
	
	private final OKMDashboardServiceAsync dashboardService = (OKMDashboardServiceAsync) GWT.create(OKMDashboardService.class);

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
	private Image viewedImage;
	private boolean zoom = false;
	private boolean flagZoom = true;
	private List<GWTDashboardStatsDocumentResult> lastDocList = new ArrayList<GWTDashboardStatsDocumentResult>();
	private List<GWTDashboardStatsFolderResult> lastFolderList = new ArrayList<GWTDashboardStatsFolderResult>();
	private List<GWTDashboardStatsMailResult> lastMailList = new ArrayList<GWTDashboardStatsMailResult>();
	private WidgetToFire widgetToFire;
	private String source;
	public Status status;
	private String headerTextKey;
	
	/**
	 * DashboardWidget
	 */
	public DashboardWidget(String source, String headerTextKey, String iconUrl, boolean zoom) {
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
		this.source = source;
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
	public void setDocuments(List<GWTDashboardStatsDocumentResult> docList) {
		int documentsNotViewed = 0;
		removeAllRows();
		
		for (ListIterator<GWTDashboardStatsDocumentResult> it = docList.listIterator(); it.hasNext();) {
			int row = table.getRowCount();
			final GWTDashboardStatsDocumentResult dsDocumentResult = it.next();
			final GWTDocument doc = dsDocumentResult.getDocument();
			Hyperlink docName = new Hyperlink();
			docName.setText(doc.getName());
			docName.setTitle(doc.getPath());
			docName.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					if (!dsDocumentResult.isVisited()) {
						markPathAsViewed(doc.getPath());
					}
					String docPath = doc.getPath();
					visiteNode(source, docPath, dsDocumentResult.getDate());
					String path = docPath.substring(0, docPath.lastIndexOf("/"));
					CommonUI.openAllFolderPath(path, docPath);
				}
			});
			docName.setStyleName("okm-Hyperlink");
						
			table.setHTML(row, 0, Util.mimeImageHTML(doc.getMimeType()));
			table.setWidget(row, 1, docName);
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			table.setHTML(row, 2, dtf.format(dsDocumentResult.getDate()));
			table.getCellFormatter().setWidth(row, 0, "20"); 
			table.getCellFormatter().setWidth(row, 1, "100%"); // Table sets de 100% of space
			table.getCellFormatter().setHorizontalAlignment(row, 2, HasAlignment.ALIGN_RIGHT);
			
			if (!dsDocumentResult.isVisited()) {
				documentsNotViewed++;
				table.getRowFormatter().setStyleName(row, "okm-NotViewed");
			} 
		}
		
		header.setHeaderNotViewedResults(documentsNotViewed);
		lastDocList = docList; // Saves actual list
	}
	
	/**
	 * Setting folders
	 * 
	 * @param folderList folder list
	 */
	public void setFolders(List<GWTDashboardStatsFolderResult> folderList) {
		int folderNotViewed = 0;
		removeAllRows();
		for (ListIterator<GWTDashboardStatsFolderResult> it = folderList.listIterator(); it.hasNext();) {
			int row = table.getRowCount();
			final GWTDashboardStatsFolderResult folderResult = it.next();
			final GWTFolder folder = folderResult.getFolder();
			
			if ( (folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {
				if (folder.getHasChilds()) {
					table.setHTML(row, 0, Util.imageItemHTML("img/menuitem_childs.gif"));
				} else {
					table.setHTML(row, 0, Util.imageItemHTML("img/menuitem_empty.gif"));
				}
			} else {
				if (folder.getHasChilds()) {
					table.setHTML(row, 0, Util.imageItemHTML("img/menuitem_childs_ro.gif"));
				} else {
					table.setHTML(row, 0, Util.imageItemHTML("img/menuitem_empty_ro.gif"));
				}
			}
			
			Hyperlink folderName = new Hyperlink(); 
			folderName.setText(folder.getName());
			folderName.setTitle(folder.getPath());
			folderName.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					if (!folderResult.isVisited()) {
						markPathAsViewed(folder.getPath());
						visiteNode(source, folder.getPath(), folderResult.getDate());
					}
					CommonUI.openAllFolderPath(folder.getPath(), "");	
				}
			});
			folderName.setStyleName("okm-Hyperlink");
			table.setWidget(row, 1, folderName);
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			table.setHTML(row, 2, dtf.format(folder.getCreated()));
			table.getCellFormatter().setWidth(row, 0, "20"); 
			table.getCellFormatter().setWidth(row, 1, "100%"); // Table sets de 100% of space
			if (!folderResult.isVisited()) {
				folderNotViewed++;
				table.getRowFormatter().setStyleName(row, "okm-NotViewed");
			}
		}
		
		header.setHeaderNotViewedResults(folderNotViewed);
		lastFolderList = folderList;
	}
	
	/**
	 * Setting mails
	 * 
	 * @param mailList mail list
	 */
	public void setMails(List<GWTDashboardStatsMailResult> mailList) {
		int documentsNotViewed = 0;
		removeAllRows();
		
		for (ListIterator<GWTDashboardStatsMailResult> it = mailList.listIterator(); it.hasNext();) {
			int row = table.getRowCount();
			final GWTDashboardStatsMailResult dsMailResult = it.next();
			final GWTMail mail = dsMailResult.getMail();
			Hyperlink mailName = new Hyperlink();
			mailName.setText(mail.getSubject());
			mailName.setTitle(mail.getPath());
			mailName.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					if (!dsMailResult.isVisited()) {
						markPathAsViewed(mail.getPath());
					}
					String mailPath = mail.getPath();
					visiteNode(source, mailPath, dsMailResult.getDate());
					String path = mailPath.substring(0, mailPath.lastIndexOf("/"));
					CommonUI.openAllFolderPath(path, mailPath);
				}
			});
			mailName.setStyleName("okm-Hyperlink");
						
			table.setHTML(row, 0, Util.mimeImageHTML(mail.getMimeType()));
			table.setWidget(row, 1, mailName);
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			table.setHTML(row, 2, dtf.format(dsMailResult.getDate()));
			table.getCellFormatter().setWidth(row, 0, "20"); 
			table.getCellFormatter().setWidth(row, 1, "100%"); // Table sets de 100% of space
			table.getCellFormatter().setHorizontalAlignment(row, 2, HasAlignment.ALIGN_RIGHT);
			
			if (!dsMailResult.isVisited()) {
				documentsNotViewed++;
				table.getRowFormatter().setStyleName(row, "okm-NotViewed");
			} 
		}
		
		header.setHeaderNotViewedResults(documentsNotViewed);
		lastMailList = mailList; // Saves actual list
	}
	
	/**
	 * Mark all table rows as viewed
	 */
	public void markAllRowsAsViewed() {
		int decrement = 0;
		
		for (int i=0; i<table.getRowCount(); i++) {
			table.getRowFormatter().removeStyleName(i, "okm-NotViewed");
		}
		
		for (ListIterator<GWTDashboardStatsDocumentResult> it = lastDocList.listIterator(); it.hasNext(); ) {
			GWTDashboardStatsDocumentResult dsDocumentResult = it.next();
			if (!dsDocumentResult.isVisited()) {
				visiteNode(source, dsDocumentResult.getDocument().getPath(), dsDocumentResult.getDate());
				dsDocumentResult.setVisited(true);
			}
		}
		
		for (ListIterator<GWTDashboardStatsFolderResult> it = lastFolderList.listIterator(); it.hasNext(); ) {
			GWTDashboardStatsFolderResult folderResult =  it.next();
			if (!folderResult.isVisited()) {
				visiteNode(source, folderResult.getFolder().getPath(), folderResult.getDate());
				folderResult.setVisited(true);
			}
		}
		
		for (ListIterator<GWTDashboardStatsMailResult> it = lastMailList.listIterator(); it.hasNext(); ) {
			GWTDashboardStatsMailResult mailResult =  it.next();
			if (!mailResult.isVisited()) {
				visiteNode(source, mailResult.getMail().getPath(), mailResult.getDate());
				mailResult.setVisited(true);
			}
		}
		
		decrement = header.getNotViewed();
		header.decrementNotViewed(decrement);	
		
		// Refreshing other panels data
		if (widgetToFire!=null) {
			widgetToFire.decrementNewDocuments(decrement);
		}
	}
	
	/**
	 * Mark folder or documetn path as viewed
	 * 
	 * Could be more than one document / folder with same path at the same list ( for example last downloading )
	 * 
	 * @param widget
	 */
	private void markPathAsViewed(String path) {
		int count = 0;
		int decrement = 0;
		for (ListIterator<GWTDashboardStatsDocumentResult> it = lastDocList.listIterator(); it.hasNext(); ) {
			GWTDashboardStatsDocumentResult dsDocumentResult = it.next();
			if (dsDocumentResult.getDocument().getPath().equals(path)) {
				table.getRowFormatter().removeStyleName(count++, "okm-NotViewed");
				decrement++;
				dsDocumentResult.setVisited(true);
			} else {
				count++;
			}
		}
		
		count = 0;
		for (ListIterator<GWTDashboardStatsFolderResult> it = lastFolderList.listIterator(); it.hasNext(); ) {
			GWTDashboardStatsFolderResult dsFolderResult = it.next();
			if (dsFolderResult.getFolder().getPath().equals(path)) {
				table.getRowFormatter().removeStyleName(count++, "okm-NotViewed");
				decrement++;
				dsFolderResult.setVisited(true);
			} else {
				count++;
			}
		}
		
		count = 0;
		for (ListIterator<GWTDashboardStatsMailResult> it = lastMailList.listIterator(); it.hasNext(); ) {
			GWTDashboardStatsMailResult dsMailResult = it.next();
			if (dsMailResult.getMail().getPath().equals(path)) {
				table.getRowFormatter().removeStyleName(count++, "okm-NotViewed");
				decrement++;
				dsMailResult.setVisited(true);
			} else {
				count++;
			}
		}
		
		header.decrementNotViewed(decrement);
		
		// Refreshing other panels data
		if (widgetToFire!=null) {
			widgetToFire.decrementNewDocuments(decrement);
		}

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
	 * Visite a node
	 */
	public void visiteNode(String source, String node, Date date) {
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.visiteNode(source, node, date, callbackVisiteNode);
	}
	
	/**
	 * Callback handler
	 */
	final AsyncCallback callbackVisiteNode = new AsyncCallback() {
		public void onSuccess(Object result) {
			// This method should call markPathAsViewed
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("visiteNode", caught);
		}
	};
	
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
			
			viewedImage = new Image("img/viewed.gif");
			viewedImage.setStyleName("okm-Hyperlink");
			
			viewedImage.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					flagZoom = false;
					markAllRowsAsViewed();
				}
			});
			
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
			center.add(viewedImage);
			center.add(zoomImage);
			
			spLeft.setSize(""+HEADER_SQUARE, ""+HEADER_SQUARE);
			center.setWidth("100%");
			center.setCellVerticalAlignment(iconImagePanel, HasAlignment.ALIGN_MIDDLE);
			center.setCellHorizontalAlignment(iconImagePanel, HasAlignment.ALIGN_LEFT);
			center.setCellHorizontalAlignment(viewedImage, HasAlignment.ALIGN_LEFT);
			center.setCellHorizontalAlignment(zoomImage, HasAlignment.ALIGN_RIGHT);
			center.setCellVerticalAlignment(titlePanel, HasAlignment.ALIGN_MIDDLE);
			center.setCellVerticalAlignment(headerNotViewedResults, HasAlignment.ALIGN_MIDDLE);
			center.setCellHorizontalAlignment(headerNotViewedResults, HasAlignment.ALIGN_RIGHT);
	
			center.setCellVerticalAlignment(viewedImage, HasAlignment.ALIGN_MIDDLE);
			center.setCellVerticalAlignment(zoomImage, HasAlignment.ALIGN_MIDDLE);
			center.setCellWidth(iconImagePanel, "22");
			center.setCellWidth(viewedImage, "22");
			center.setCellWidth(zoomImage, "16");
			center.setHeight(""+HEADER_SQUARE);
			spRight.setSize(""+HEADER_SQUARE, ""+HEADER_SQUARE);
			
			titlePanel.setCellVerticalAlignment(headerResults, HasAlignment.ALIGN_MIDDLE);
			titlePanel.setCellVerticalAlignment(headerNotViewedResults, HasAlignment.ALIGN_MIDDLE);
			titlePanel.setCellHorizontalAlignment(headerResults, HasAlignment.ALIGN_LEFT);
			titlePanel.setCellHorizontalAlignment(headerNotViewedResults, HasAlignment.ALIGN_LEFT);
			
			add(spLeft);table.getCellFormatter().setWidth(1, 2, "100%");
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
				headerNotViewedResults.setHTML("&nbsp;" + Main.i18n("dashboard.new.items") + ":&nbsp;" + value + "&nbsp;&nbsp;");
				titlePanel.setStyleName("okm-NotViewed");
				headerNotViewedResults.setStyleName("okm-NotViewed");
				viewedImage.setUrl("img/viewed_pending.gif");
				
			} else {
				headerNotViewedResults.setHTML("");
				titlePanel.removeStyleName("okm-NotViewed");
				headerNotViewedResults.removeStyleName("okm-NotViewed");
				viewedImage.setUrl("img/viewed.gif");
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
		int left = getAbsoluteLeft() + (getOffsetWidth()/2);
		int top = getAbsoluteTop() + (getOffsetHeight()/2);
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
