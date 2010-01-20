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

package com.openkm.backend.client.widget.propertygroups;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * PropertyGroupPanel
 * 
 * @author jllort
 *
 */
public class PropertyGroupPanel extends Composite {
	
	private VerticalSplitPanel verticalSplitPanel;
	private PropertyGroup propertyGroup;
	public PropertyGroupDetail propertyGroupDetail;
	
	/**
	 * PropertyGroupPanel
	 */
	public PropertyGroupPanel() {
		verticalSplitPanel = new VerticalSplitPanel();
		propertyGroup = new PropertyGroup();
		propertyGroupDetail = new PropertyGroupDetail();
		
		verticalSplitPanel.setTopWidget(propertyGroup);
		verticalSplitPanel.setBottomWidget(propertyGroupDetail);
		verticalSplitPanel.setSplitPosition("200");
		
		propertyGroup.setStyleName("okm-Input");
		propertyGroupDetail.setStyleName("okm-Input");
		
		initWidget(verticalSplitPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		propertyGroup.langRefresh();
		propertyGroupDetail.langRefresh();
	}
}