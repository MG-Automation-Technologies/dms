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

package es.git.openkm.backend.client.widget.workflow;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.bean.GWTProcessDefinition;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMWorkflowService;
import es.git.openkm.backend.client.service.OKMWorkflowServiceAsync;

/**
 * WorkflowVersionData
 * 
 * @author jllort
 *
 */
public class WorkflowVersionData extends Composite {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	VerticalPanel vPanel;
	ExtendedFlexTable table;
	public MenuPopup menuPopup;
	public Status status;
	
	public WorkflowVersionData() {
		vPanel = new VerticalPanel();
		table = new ExtendedFlexTable();
		menuPopup = new MenuPopup();
		menuPopup.setStylePrimaryName("okm-MenuPopup");
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, Main.i18n("workflow.id"));
		table.setHTML(0, 1, Main.i18n("workflow.version"));
		table.setHTML(0, 2, "");
		
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 2, "100%");
		
		vPanel.add(table);
		
		table.setStyleName("okm-NoWrap");
		
		vPanel.setSize("100%", "100%");
		
		initWidget(vPanel);
	}
	
	/**
	 * removeAllRows
	 */
	public void removeAllRows() {
		table.deselectedRow();
		while (table.getRowCount()>1) {
			table.removeRow(1);
		}
	}
	
	/**
	 * Show the menu
	 */
	public void showMenu() {
		// The browser menu depends on actual view
		// Must substract top position from Y Screen Position
		menuPopup.setPopupPosition(table.getMouseX(), table.getMouseY());
		menuPopup.show();		
	}
	
	/**
	 * Call back find all process definitions versions
	 */
	final AsyncCallback<List<GWTProcessDefinition>> callbackFindAllProcessDefinitionVersions = new AsyncCallback<List<GWTProcessDefinition>>() {
		public void onSuccess(List<GWTProcessDefinition> result) {
			for (Iterator<GWTProcessDefinition> it = result.iterator(); it.hasNext(); ) {
				int rows = table.getRowCount();
				GWTProcessDefinition processDefinition = it.next();
				table.setHTML(rows, 0, ""+processDefinition.getId());
				table.setHTML(rows, 1, ""+processDefinition.getVersion());
				table.setHTML(rows, 2, "");
				table.getCellFormatter().setHorizontalAlignment(rows, 0, HasAlignment.ALIGN_CENTER);
				table.getCellFormatter().setHorizontalAlignment(rows, 1, HasAlignment.ALIGN_CENTER);
			}
			if (!table.isRowSelected() && table.getRowCount()>1) {
				table.setSelectedRow(1);
				refreshingInstances();
			}
			status.unsetFlag_version();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("findAllProcessDefinitionVersions", caught);
			status.unsetFlag_version();
		}
	};
	
	/**
	 * Call back find all process definitions
	 */
	final AsyncCallback<Object> callbackDeleteProcessDefinition = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			table.removeSelectedRow();
			if (table.isRowSelected()) {
				// Actual version is always the first one on table list
				String newID = table.getHTML(1, 0);
				String newVersion = table.getHTML(1, 1);
				Main.get().centerPanel.workflowPanel.workflow.updatingWorkflowDataAfterDeleting(newID, newVersion);
				refreshingInstances();
			} else {
				// All versions has been deleted must delete actual row
				Main.get().centerPanel.workflowPanel.workflow.removeSelectedRow();
				refreshingInstances();
			}
			status.unsetFlag_delete();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("deleteProcessDefinition", caught);
			status.unsetFlag_delete();
		}
	};
	
	/**
	 * findAllProcessDefinitionVersions
	 * 
	 * @param name Process definition name
	 */
	public void findAllProcessDefinitionVersions(String name) {
		table.setDataType(ExtendedFlexTable.TABLE_WORKFLOW_VERSIONS);
		status.setFlag_version();
		status.refresh(vPanel);
		removeAllRows();
		
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowServletService);	
		workflowService.findAllProcessDefinitionVersions(name, callbackFindAllProcessDefinitionVersions);
	}
	
	/**
	 * Delete process definitions
	 */
	public void deleteProcessDefinition() {
		double id = new Double(table.getText(table.getSelectedRow(), 0)).doubleValue();
		status.setFlag_delete();
		status.refresh(vPanel);
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowServletService);	
		workflowService.deleteProcessDefinition(id, callbackDeleteProcessDefinition);
	}
	
	/**
	 * Refreshing instances
	 */
	private void refreshingInstances(){
		if (table.isRowSelected()) {
			double id = new Double(table.getText(table.getSelectedRow(),0));
			Main.get().centerPanel.workflowPanel.workflowInstancesData.findProcessInstances(id);
		} else {
			Main.get().centerPanel.workflowPanel.workflowInstancesData.removeAllRows();
		}
	}
}