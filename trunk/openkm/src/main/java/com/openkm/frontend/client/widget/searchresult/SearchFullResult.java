/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.widget.searchresult;

import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTQueryResult;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.WidgetUtil;
import com.openkm.frontend.client.widget.dashboard.keymap.TagCloud;

/**
 * SearchFullResult
 * 
 * @author jllort
 *
 */
public class SearchFullResult extends Composite {
	
	private ScrollPanel scrollPanel;
	private FlexTable table;
	
	/**
	 * SearchFullResult
	 */
	public SearchFullResult() {
		table = new FlexTable();
		scrollPanel = new ScrollPanel(table);
		
		scrollPanel.setStyleName("okm-Input");
		
		initWidget(scrollPanel);
	}
	
	 /* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setPixelSize(int, int)
	 */
	public void setPixelSize(int width, int height) {
		table.setWidth(""+(width));
		scrollPanel.setPixelSize(width, height);
	}
	
	/**
	 * Adds a document to the panel
	 * 
	 * @param doc The doc to add
	 */
	public void addRow(GWTQueryResult gwtQueryResult) {
		if (gwtQueryResult.getDocument()!=null || gwtQueryResult.getAttachment()!=null) {
			addDocumentRow(gwtQueryResult, new Score(gwtQueryResult.getScore()));
		} else if (gwtQueryResult.getFolder()!=null) {
//			addFolderRow(gwtQueryResult, new Score(gwtQueryResult.getScore()));
		} else if (gwtQueryResult.getMail()!=null) {
//			addMailRow(gwtQueryResult, new Score(gwtQueryResult.getScore()));
		}
	}
	
	/**
	 * Adding document row
	 * 
	 * @param gwtQueryResult Query result
	 * @param score Document score
	 */
	private void addDocumentRow(GWTQueryResult gwtQueryResult, Score score) {
		int rows = table.getRowCount();
		
		GWTDocument doc = new GWTDocument();
		if (gwtQueryResult.getDocument()!=null) {
			doc = gwtQueryResult.getDocument();
		} else if (gwtQueryResult.getAttachment()!=null) {
			doc = gwtQueryResult.getAttachment();
		}
		
		// Document row
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setStyleName("okm-NoWrap");
		hPanel.add(new HTML(score.getHTML()));
		hPanel.add(Util.hSpace("5"));
		if(doc.isAttachment())  {
			hPanel.add(new HTML(Util.imageItemHTML("img/email_attach.gif")));
			hPanel.add(Util.hSpace("5"));
		} 
		hPanel.add(new HTML(Util.mimeImageHTML(doc.getMimeType())));
		hPanel.add(Util.hSpace("5"));
		Anchor anchor = new Anchor();
		anchor.setHTML(doc.getName());
		anchor.setStyleName("okm-Hyperlink");
		// On attachemt case must remove last folder path, because it's internal usage not for visualization
		if (doc.isAttachment()) {
			anchor.setTitle(doc.getParent().substring(0, doc.getParent().lastIndexOf("/")));
		} else {
			anchor.setTitle(doc.getParent());
		}
		final String docPath = doc.getPath();
		anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CommonUI.openAllFolderPath(docPath.substring(0,docPath.lastIndexOf("/")), docPath);
			}
		});
		hPanel.add(anchor);
		hPanel.add(Util.hSpace("5"));
		hPanel.add(new HTML(doc.getActualVersion().getName()));
		hPanel.add(Util.hSpace("5"));
		// Download
		Image gotoDocument = new Image(OKMBundleResources.INSTANCE.download());
		gotoDocument.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				Util.downloadFile(docPath, "");
			}
			
		});
		gotoDocument.setTitle(Main.i18n("dashboard.keyword.goto.document"));
		gotoDocument.setStyleName("okm-KeyMap-ImageHover");
		hPanel.add(gotoDocument);
		table.setWidget(rows++, 0, hPanel);		
		
		// Excerpt row
		table.setHTML(rows++, 0, gwtQueryResult.getExcerpt());
		
		// Folder row
		HorizontalPanel hPanel2 = new HorizontalPanel();
		hPanel2.setStyleName("okm-NoWrap");
		hPanel2.add(new HTML("<b>"+Main.i18n("document.folder")+":</b>&nbsp;"));
		hPanel2.add(drawFolder(doc.getParentId()));
		table.setWidget(rows++, 0, hPanel2);
		
		// Document detail
		HorizontalPanel hPanel4 = new HorizontalPanel();
		hPanel4.setStyleName("okm-NoWrap");
		hPanel4.add(new HTML("<b>"+Main.i18n("search.result.author")+":</b>&nbsp;"));
		hPanel4.add(new HTML(doc.getActualVersion().getAuthor()));
		hPanel4.add(Util.hSpace("33"));
		hPanel4.add(new HTML("<b>"+Main.i18n("search.result.size")+":</b>&nbsp;"));
		hPanel4.add(new HTML(Util.formatSize(doc.getActualVersion().getSize())));
		hPanel4.add(Util.hSpace("33"));
		hPanel4.add(new HTML("<b>"+Main.i18n("search.result.version")+":</b>&nbsp;"));
		hPanel4.add(new HTML(doc.getActualVersion().getName()));
		hPanel4.add(Util.hSpace("33"));
		hPanel4.add(new HTML("<b>"+Main.i18n("search.result.date.update")+":&nbsp;</b>"));
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		hPanel4.add(new HTML(dtf.format(doc.getLastModified())));
		table.setWidget(rows++, 0, hPanel4);
		
		// Categories and tagcloud
		if (doc.getCategories().size()>0 || doc.getKeywords().size()>0) { 
			HorizontalPanel hPanel5 = new HorizontalPanel();
			hPanel5.setStyleName("okm-NoWrap");
			if (doc.getCategories().size()>0) {
				FlexTable tableSubscribedCategories = new FlexTable();
				tableSubscribedCategories.setStyleName("okm-DisableSelect");
				// Sets the document categories
				for (Iterator<GWTFolder> it = doc.getCategories().iterator(); it.hasNext();) {
					drawCategory(tableSubscribedCategories, it.next());
				}
				hPanel5.add(new HTML("<b>"+Main.i18n("document.categories")+"</b>"));
				hPanel5.add(Util.hSpace("5"));
				hPanel5.add(tableSubscribedCategories);
				hPanel5.add(Util.hSpace("33"));
			}
			if (doc.getKeywords().size()>0) {
				// Tag cloud
				TagCloud keywordsCloud = new TagCloud();
				keywordsCloud.setWidth("350");
				WidgetUtil.drawTagCloud(keywordsCloud, doc.getKeywords());
				hPanel5.add(new HTML("<b>"+Main.i18n("document.keywords.cloud")+"</b>"));
				hPanel5.add(Util.hSpace("5"));
				hPanel5.add(keywordsCloud);
			}
			table.setWidget(rows++, 0, hPanel5);
		}
		
		// Final line
		Image horizontalLine = new Image("img/transparent_pixel.gif");
		horizontalLine.setStyleName("okm-TopPanel-Line-Border");
		horizontalLine.setSize("100%", "2px");
		table.setWidget(rows, 0, horizontalLine);
		table.getFlexCellFormatter().setVerticalAlignment(rows, 0, HasAlignment.ALIGN_BOTTOM);
		table.getFlexCellFormatter().setHeight(rows, 0, "30");		
		
		for (int i=0; i<1; i++) {
			table.getCellFormatter().addStyleName(rows, i, "okm-DisableSelect");
		}
	}
	
	/**
	 * drawCategory
	 * 
	 * @param category
	 */
	private void drawCategory(final FlexTable tableSubscribedCategories, final GWTFolder category) {
		int row = tableSubscribedCategories.getRowCount();
		Anchor anchor = new Anchor();
		// Looks if must change icon on parent if now has no childs and properties with user security atention
		String path = category.getPath().substring(16); // Removes /okm:categories
		if (category.getHasChilds()) {
			anchor.setHTML(Util.imageItemHTML("img/menuitem_childs.gif", path, "top"));
		} else {
			anchor.setHTML(Util.imageItemHTML("img/menuitem_empty.gif", path, "top"));
		}
		
		anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				CommonUI.openAllFolderPath(category.getPath(), null);
			}
		});
		anchor.setStyleName("okm-KeyMap-ImageHover");
		tableSubscribedCategories.setWidget(row, 0, anchor);
	}
	
	/**
	 * drawFolder
	 * 
	 * @param folder
	 * @return
	 */
	private Anchor drawFolder(final String path) {
		Anchor anchor = new Anchor();
		anchor.setHTML(Util.imageItemHTML("img/menuitem_childs.gif", path, "top"));	
		anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				CommonUI.openAllFolderPath(path, null);
			}
		});
		anchor.setStyleName("okm-KeyMap-ImageHover");
		return anchor;
	}
	
	/**
	 * removeAllRows
	 */
	public void removeAllRows() {
		table.removeAllRows();
	}
}