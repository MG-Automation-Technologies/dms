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
 * Workflow
 * 
 * @author jllort
 *
 */
public class Workflow extends Composite {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	private VerticalPanel vPanel;
	public ExtendedFlexTable table;
    public Status status;
	
	/**
	 * GeneralUtils
	 */
	public Workflow() {
		vPanel = new VerticalPanel();
		table = new ExtendedFlexTable();
		table.setDataType(ExtendedFlexTable.TABLE_WORKFLOW_LIST);
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, Main.i18n("workflow.id"));
		table.setHTML(0, 1, Main.i18n("workflow.name"));
		table.setHTML(0, 2, Main.i18n("workflow.last.version"));
		table.setHTML(0, 3, "");
		
		table.setStyleName("okm-NoWrap");
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 3, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 3, "100%");
		
		vPanel.add(table);
		
		initWidget(vPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		table.setHTML(0, 0, Main.i18n("workflow.id"));
		table.setHTML(0, 1, Main.i18n("workflow.name"));
		table.setHTML(0, 2, Main.i18n("workflow.last.version"));
	}
	
	/**
	 * getProcessDefinitionName
	 * 
	 * @return
	 */
	public String getProcessDefinitionName() {
		return table.getText(table.getSelectedRow(), 1);
	}
	
	/**
	 * getProcessDefinitionID
	 * 
	 * @return
	 */
	public double getProcessDefinitionID() {
		return new Double(table.getText(table.getSelectedRow(), 0)).doubleValue();
	}
	
	/**
	 * Call back find all process definitions
	 */
	final AsyncCallback<List<GWTProcessDefinition>> callbackFindLatestProcessDefinitions = new AsyncCallback<List<GWTProcessDefinition>>() {
		public void onSuccess(List<GWTProcessDefinition> result) {
			for (Iterator<GWTProcessDefinition> it = result.iterator(); it.hasNext(); ) {
				int rows = table.getRowCount();
				GWTProcessDefinition processDefinition = it.next();
				table.setHTML(rows, 0, ""+processDefinition.getId());
				table.setHTML(rows, 1, processDefinition.getName());
				table.setHTML(rows, 2, ""+processDefinition.getVersion());
				table.setHTML(rows, 3, "");
				table.getCellFormatter().setHorizontalAlignment(rows, 0, HasAlignment.ALIGN_CENTER);
				table.getCellFormatter().setHorizontalAlignment(rows, 2, HasAlignment.ALIGN_CENTER);
			}
			// First time selects first row
			if (!table.isRowSelected() && table.getRowCount()>1) {
				table.setSelectedRow(1);
				String name = table.getText(table.getSelectedRow(), 1); 
				Main.get().centerPanel.workflowPanel.workflowVersionData.findAllProcessDefinitionVersions(name);
				
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("findLatestProcessDefinitions", caught);;
		}
	};
	
	/**
	 * Gets all process definitions
	 */
	public void findLatestProcessDefinitions() {
		table.removeAllRows();
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowServletService);	
		workflowService.findLatestProcessDefinitions(callbackFindLatestProcessDefinitions);
	}
	
	/**
	 * Sets new version process after deleting some process version
	 * 
	 * @param newID
	 * @param newVersion
	 */
	public void updatingWorkflowDataAfterDeleting(String newID, String newVersion){
		table.setHTML(table.getSelectedRow(), 0, newID);
		table.setHTML(table.getSelectedRow(), 2, newVersion);
	}
	
	/**
	 * Removes selected Row
	 */
	public void removeSelectedRow() {
		table.removeSelectedRow();
		
		// Refreshing panels other row is selected
		if (table.isRowSelected()) {
			String name = table.getText(table.getSelectedRow(), 1); 
			Main.get().centerPanel.workflowPanel.workflowVersionData.findAllProcessDefinitionVersions(name);
		}
	}
}