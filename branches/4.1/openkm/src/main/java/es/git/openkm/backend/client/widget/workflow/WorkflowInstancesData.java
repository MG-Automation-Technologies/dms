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
import es.git.openkm.backend.client.bean.GWTProcessInstance;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMWorkflowService;
import es.git.openkm.backend.client.service.OKMWorkflowServiceAsync;

/**
 * WorkflowInstancesData
 * 
 * @author jllort
 *
 */
public class WorkflowInstancesData extends Composite {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	VerticalPanel vPanel;
	ExtendedFlexTable table;
	public Status status;
	
	public WorkflowInstancesData() {
		vPanel = new VerticalPanel();
		table = new ExtendedFlexTable();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, Main.i18n("workflow.id"));
		table.setHTML(0, 1, Main.i18n("workflow.version"));
		table.setHTML(0, 2, Main.i18n("workflow.ended"));
		table.setHTML(0, 3, Main.i18n("workflow.suspended"));
		table.setHTML(0, 4, Main.i18n("workflow.current.nodes"));
		table.setHTML(0, 5, Main.i18n("workflow.variables"));
		table.setHTML(0, 6, "");
		
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 3, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 4, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 5, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 6, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 4, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 5, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 6, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 4, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 5, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 6, "100%");
		
		vPanel.add(table);
		
		table.setStyleName("okm-NoWrap");
		
		initWidget(vPanel);
	}
	
	/**
	 * Call back find all process definitions versions
	 */
	final AsyncCallback<List<GWTProcessInstance>> callbackFindProcessInstances = new AsyncCallback<List<GWTProcessInstance>>() {
		public void onSuccess(List<GWTProcessInstance> result) {
			for (Iterator<GWTProcessInstance> it = result.iterator(); it.hasNext(); ) {
				int rows = table.getRowCount();
				GWTProcessInstance processInstance = it.next();
				table.setHTML(rows, 0, ""+processInstance.getId());
				table.setHTML(rows, 1, ""+processInstance.getVersion());
				table.setHTML(rows, 2, ""+processInstance.isEnded());
				table.setHTML(rows, 3, ""+processInstance.isSuspended());
				String currentNodes = "";
				boolean first = true;
				for (Iterator<String> itn = processInstance.getCurrentNodes().iterator(); itn.hasNext();) {
					if (!first) {
						currentNodes +="<br>";
					}
					currentNodes += itn.next();
					first=false;
				}
				table.setHTML(rows, 4, currentNodes);
				
				String variables = "";
				first=true;
				for (Iterator<String> itv = processInstance.getVariables().keySet().iterator(); itv.hasNext();) {
					if (!first) {
						variables +="<br>";
					}
					variables += processInstance.getVariables().get(itv.next());
					first=false;
				}
				table.setHTML(rows, 5, variables);
				
				table.setHTML(rows, 6, "");
				table.getCellFormatter().setHorizontalAlignment(rows, 0, HasAlignment.ALIGN_CENTER);
				table.getCellFormatter().setHorizontalAlignment(rows, 1, HasAlignment.ALIGN_CENTER);
				table.getCellFormatter().setHorizontalAlignment(rows, 2, HasAlignment.ALIGN_CENTER);
				table.getCellFormatter().setHorizontalAlignment(rows, 3, HasAlignment.ALIGN_CENTER);
				table.getRowFormatter().setVerticalAlign(rows, HasAlignment.ALIGN_TOP);
			}
			status.unsetFlag_instances();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("findProcessInstances", caught);
			status.unsetFlag_instances();
		}
	};
	
	/**
	 * findProcessInstances
	 * 
	 * @param id
	 */
	public void findProcessInstances(double id) {
		table.setDataType(ExtendedFlexTable.TABLE_WORKFLOW_INSTANCES);
		status.setFlag_instances();
		status.refresh(vPanel);
		removeAllRows();

		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowServletService);	
		workflowService.findProcessInstances(id, callbackFindProcessInstances);
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
}