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

package com.openkm.backend.client.widget.stats;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.bean.GWTStatsInfo;
import com.openkm.backend.client.config.Config;
import com.openkm.backend.client.service.OKMStatsService;
import com.openkm.backend.client.service.OKMStatsServiceAsync;

/**
 * 
 * statsPanel
 * 
 * @author jllort
 *
 */
public class GraphicalStats extends Composite {
	
	private final OKMStatsServiceAsync statsService = (OKMStatsServiceAsync) GWT.create(OKMStatsService.class);
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private GraphCircleByContext sizeByContext;
	private GraphCircleByContext foldersByContext;
	private GraphCircleByContext documentsByContext;
	public Status status;
	
	private String[] pieTypes = {	
				Main.i18n("stats.context.taxonomy"), 
				Main.i18n("stats.context.personal"), 
				Main.i18n("stats.context.templates"), 
				Main.i18n("stats.context.trash")
			};
	
	/**
	 * statsPanel
	 */
	public GraphicalStats() {

		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		hPanel.setWidth("750");

		vPanel.add(hPanel);
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		
		vPanel.setStyleName("okm-Input");
		vPanel.setSize("100%","100%");
		
		//getSizeContext(); // It's a secuential to initalize all graphics
		
		initWidget(vPanel);
	}
	
	public void langRefresh() {
		
	}
	
	/**
	 * Call back get folders by context
	 */
	final AsyncCallback<GWTStatsInfo> callbackGetFoldersByContext = new AsyncCallback<GWTStatsInfo>() {
		public void onSuccess(GWTStatsInfo result) {
			foldersByContext = new GraphCircleByContext(result.getPercents(), 
														result.getSizes(), 
														pieTypes, Main.i18n("stats.context.folder.number"), 
														"stats.context.folders",
														GraphCircleByContext.VALUE_NUMERIC);
			hPanel.add(foldersByContext);
			hPanel.setCellHorizontalAlignment(foldersByContext, HasAlignment.ALIGN_RIGHT);
			hPanel.setCellWidth(foldersByContext, "250");
			status.unsetFlag_graph();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getFoldersByContext", caught);
		}
	};
	
	/**
	 * Call back get documents by context
	 */
	final AsyncCallback<GWTStatsInfo> callbackGetDocumentsByContext = new AsyncCallback<GWTStatsInfo>() {
		public void onSuccess(GWTStatsInfo result) {
			getFoldersByContext();
			documentsByContext = new GraphCircleByContext(result.getPercents(), 
					result.getSizes(),
														  pieTypes, 
														  Main.i18n("stats.context.document.number"), 
														  "stats.context.documents",
														  GraphCircleByContext.VALUE_NUMERIC);
			hPanel.add(documentsByContext);
			hPanel.setCellHorizontalAlignment(documentsByContext, HasAlignment.ALIGN_RIGHT);
			hPanel.setCellWidth(documentsByContext, "250");
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getDocumentsByContext", caught);
		}
	};
	
	/**
	 * Call back get size by context
	 */
	final AsyncCallback<GWTStatsInfo> callbackGetDocumentsSizeByContext = new AsyncCallback<GWTStatsInfo>() {
		public void onSuccess(GWTStatsInfo result) {
			getDocumentsByContext();
			
			// Makes somes variation to statistics minor values always must be 1%
			for (int i=0; i<result.getPercents().length; i++) {
				if (!result.getSizes()[i].equals("0") && result.getPercents()[i]<0.01) {
					result.getPercents()[i]=0.01;
					// Prevent negative values for some strange possible cases
					if (result.getPercents()[0]>0.01) {
						result.getPercents()[0] = result.getPercents()[0] - 0.01;
					}
				}
			}
			
			sizeByContext = new GraphCircleByContext(result.getPercents(), 
													 result.getSizes(), 
													 pieTypes, 
													 Main.i18n("stats.context.size"), 
													 "",
													 GraphCircleByContext.VALUE_SIZE);
			hPanel.add(sizeByContext);
			hPanel.setCellHorizontalAlignment(sizeByContext, HasAlignment.ALIGN_RIGHT);
			hPanel.setCellWidth(sizeByContext, "250");
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getDocumentsByContext", caught);
		}
	};
	
	/**
	 * Gets folders by context
	 */
	public void getFoldersByContext() {
		ServiceDefTarget endPoint = (ServiceDefTarget) statsService;
		endPoint.setServiceEntryPoint(Config.OKMStatsService);	
		statsService.getFoldersByContext(callbackGetFoldersByContext);
	}
	
	/**
	 * Gets documents by context
	 */
	public void getDocumentsByContext() {
		ServiceDefTarget endPoint = (ServiceDefTarget) statsService;
		endPoint.setServiceEntryPoint(Config.OKMStatsService);	
		statsService.getDocumentsByContext(callbackGetDocumentsByContext);
	}
	
	/**
	 * Gets Size by context
	 */
	public void getSizeContext() {
		status.setFlag_graph();
		status.refresh(vPanel);
		ServiceDefTarget endPoint = (ServiceDefTarget) statsService;
		endPoint.setServiceEntryPoint(Config.OKMStatsService);	
		statsService.getDocumentsSizeByContext(callbackGetDocumentsSizeByContext);
	}
}