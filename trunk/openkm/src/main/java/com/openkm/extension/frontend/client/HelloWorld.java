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


package com.openkm.extension.frontend.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.extension.ExtensionUIComunicator;
import com.openkm.frontend.client.extension.HasDocumentExtension;
import com.openkm.frontend.client.extension.widget.TabDocumentExtension;

/**
 * DocumentForum
 * 
 * @author jllort
 *
 */
public class HelloWorld extends TabDocumentExtension implements HasDocumentExtension {
	
	Button refresh;
	VerticalPanel vPanel;
	
	public HelloWorld() {
		HTML html = new HTML("Hello Word");
		refresh = new Button("refresh UI");
		refresh.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ExtensionUIComunicator.refreshUI();
			}
		});
		vPanel = new VerticalPanel();
		vPanel.add(html);
		vPanel.add(refresh);
		
		refresh.setStyleName("okm-Input");
		
		initWidget(vPanel);
	}

	@Override
	public String getTabText() {
		return "Hello tab";
	}

	@Override
	public void langRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(GWTDocument doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisibleButtons(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLang(String code) {
		// TODO Auto-generated method stub
	}
}

