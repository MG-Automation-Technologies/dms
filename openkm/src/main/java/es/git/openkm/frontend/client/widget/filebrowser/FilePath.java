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

package es.git.openkm.frontend.client.widget.filebrowser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMRepositoryService;
import es.git.openkm.frontend.client.service.OKMRepositoryServiceAsync;

/**
 * File Path
 * 
 * @author jllort
 *
 */
public class FilePath  extends Composite {
	
	private VerticalPanel panel;
	private HTML path;
	private String valuePath;
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	
	/**
	 * FilePath
	 */
	public FilePath(){
		panel = new VerticalPanel();
		path = new HTML(Main.i18n("filebrowser.path") + ": ", false);
		panel.setStyleName("okm-FilePath-Title");
		panel.setSize("100%","22");
		panel.add(path);
		panel.setCellVerticalAlignment(path,VerticalPanel.ALIGN_MIDDLE);
		initWidget(panel);
		
		// Only executes first time when object is created to initalize values
		getRootPath();
	}
	
	/**
	 * Refresh languague values
	 */
	public void langRefresh() {
		setPath(valuePath);
	}
	
	/**
	 * Sets the directory path to show
	 * 
	 * @param path The directory path
	 */
	public void setPath(String path) {
		valuePath = path;
		// Always add / at path ends
		this.path.setHTML(Main.i18n("filebrowser.path") + " : " + valuePath + "/");
	}
	
	/**
	 * Gets ayncronous root node
	 */
	final AsyncCallback<String> callbackGetRootNode = new AsyncCallback<String>() {
		public void onSuccess(String result) {
			//Only executes on initalization to get root path node
			setPath(result);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetRootNode", caught);
		}
	};
	
	/**
	 * Gets the root
	 */
	public void getRootPath() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getRootPath(callbackGetRootNode);
	}	
}