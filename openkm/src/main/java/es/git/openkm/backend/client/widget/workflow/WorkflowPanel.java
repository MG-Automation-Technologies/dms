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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ScrollPanel;

import es.git.openkm.backend.client.config.Config;

/**
 * GeneralUtilsPanel
 * 
 * @author jllort
 *
 */
public class WorkflowPanel extends Composite {
	
	//private VerticalSplitPanel verticalSplitPanel;
	public Workflow workflow;
	public WorkflowInstancesData workflowInstancesData;
	//private HorizontalPanel hPanel;
	//private VerticalPanel leftPanel;
	//private VerticalBorderPanel centerSeparatorPanel;
	public WorkflowVersionData workflowVersionData;
	
	// Added to include external jsp
	private ScrollPanel scrollPanel;
	private Frame iframe;
	
	/**
	 * GeneralUtilsPanel
	 */
	public WorkflowPanel() {
		//verticalSplitPanel = new VerticalSplitPanel();
		//workflow = new Workflow();
		//workflowInstancesData = new WorkflowInstancesData();
		//hPanel = new HorizontalPanel();
		//leftPanel = new VerticalPanel();
		//workflowVersionData = new WorkflowVersionData();
		//centerSeparatorPanel = new VerticalBorderPanel();
		
		//leftPanel.add(workflowVersionData);
		
		//hPanel.add(workflow);
		//hPanel.add(centerSeparatorPanel);
		//hPanel.add(leftPanel);
		
//		verticalSplitPanel.setTopWidget(hPanel);
//		verticalSplitPanel.setBottomWidget(workflowInstancesData);
//		verticalSplitPanel.setSplitPosition("230");
//		
//		workflow.setStyleName("okm-Input");
//		workflowInstancesData.setStyleName("okm-Input");
//		leftPanel.setStyleName("okm-Input");
//		
//		hPanel.setCellWidth(leftPanel, "250");
//		hPanel.setCellWidth(centerSeparatorPanel,"10");
//		
//		hPanel.setSize("100%", "100%");
//		leftPanel.setSize("100%","100%");
//		workflow.setSize("100%","100%");
//		workflowInstancesData.setSize("100%","100%");
//		centerSeparatorPanel.setSize("10","100%");
//		workflowVersionData.setSize("100%","100%");
		
		
		// Added to include external jsp
		iframe = new Frame("about:blank");
		scrollPanel = new ScrollPanel(iframe);
		
		
		DOM.setElementProperty(iframe.getElement(), "frameborder", "0");
		DOM.setElementProperty(iframe.getElement(), "marginwidth", "0");
		DOM.setElementProperty(iframe.getElement(), "marginheight", "0");
		//DOM.setElementProperty(iframe.getElement(), "allowtransparency", "true"); // Commented because on IE show clear if allowtransparency=true
		DOM.setElementProperty(iframe.getElement(), "scrolling", "auto");

		iframe.setUrl("/OpenKM"+Config.INSTALL+"/admin/wf_processes.jsp?menu=0");
		//iframe.setUrl("/jbpm-console/");
		iframe.setSize("100%", "100%");
		
		iframe.setStyleName("okm-noBorder");
		scrollPanel.setStyleName("okm-Input");
		
		initWidget(scrollPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		//workflow.langRefresh();
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		scrollPanel.setSize(""+(width-2), ""+(height-2));
	}
}