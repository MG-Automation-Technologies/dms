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

package com.openkm.backend.client.widget.advancedsearch;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * AdvancedSearchPanel
 * 
 * @author jllort
 *
 */
public class AdvancedSearchPanel extends Composite {
	
	private VerticalSplitPanel verticalSplitPanel;
	private AdvancedSearchQuery advancedSearchQuery;
	public ResultsSearch resultsSearch;
	
	/**
	 * AdvancedSearchPanel
	 */
	public AdvancedSearchPanel () {
		verticalSplitPanel = new VerticalSplitPanel();
		advancedSearchQuery = new AdvancedSearchQuery();
		resultsSearch = new ResultsSearch();
		
		verticalSplitPanel.setTopWidget(advancedSearchQuery);
		verticalSplitPanel.setBottomWidget(resultsSearch);
		verticalSplitPanel.setSplitPosition("200");
		
		advancedSearchQuery.setStyleName("okm-Input");
		resultsSearch.setStyleName("okm-Input");
		
		initWidget(verticalSplitPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		advancedSearchQuery.langRefresh();
	}
}