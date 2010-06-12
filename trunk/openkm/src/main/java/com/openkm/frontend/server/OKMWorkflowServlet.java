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

package com.openkm.frontend.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMWorkflow;
import com.openkm.bean.form.FormElement;
import com.openkm.bean.workflow.ProcessDefinition;
import com.openkm.bean.workflow.TaskInstance;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.core.WorkflowException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTProcessDefinition;
import com.openkm.frontend.client.bean.GWTTaskInstance;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMWorkflowService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMWorkflowServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMWorkflowServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMWorkflowServlet extends OKMRemoteServiceServlet implements OKMWorkflowService {
	private static Logger log = LoggerFactory.getLogger(OKMWorkflowServlet.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<GWTProcessDefinition> findLatestProcessDefinitions() throws OKMException {
		log.debug("findLatestProcessDefinitions()");
		List<GWTProcessDefinition> processDefinitionList = new ArrayList<GWTProcessDefinition>();
		updateSessionManager();
		
		try {
			for (Iterator<ProcessDefinition> it = OKMWorkflow.getInstance().findLatestProcessDefinitions().iterator(); it.hasNext();) {
				processDefinitionList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("findLatestProcessDefinitions: {}", processDefinitionList);
		return processDefinitionList;
	}
	
	@Override
	public void runProcessDefinition(String UUID, double id, Map<String,Object> variables) throws OKMException  {
		log.debug("runProcessDefinition()");
		variables.put(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_UUID, UUID);
		updateSessionManager();
		
		try {
			OKMWorkflow.getInstance().runProcessDefinition(new Double(id).longValue(), variables);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
				
		log.debug("runProcessDefinition: void");
	}
	
	@Override
	public List<GWTTaskInstance> findUserTaskInstances() throws OKMException {
		log.debug("findUserTaskInstances()");
		List<GWTTaskInstance> taskInstances = new ArrayList<GWTTaskInstance>();
		updateSessionManager();
		
		try {
			for (Iterator<TaskInstance> it= OKMWorkflow.getInstance().findUserTaskInstances().iterator(); it.hasNext();) {
				taskInstances.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("findUserTaskInstances: {}", taskInstances);
		return taskInstances;
	}
	
	@Override
	public List<GWTTaskInstance> findPooledTaskInstances() throws OKMException {
		log.debug("findPooledTaskInstances()");
		List<GWTTaskInstance> taskInstances = new ArrayList<GWTTaskInstance>();
		updateSessionManager();
		
		try {
			for (Iterator<TaskInstance> it= OKMWorkflow.getInstance().findPooledTaskInstances().iterator(); it.hasNext();) {
				taskInstances.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("findPooledTaskInstances: {}", taskInstances);
		return taskInstances;
	}
	
	@Override
	public Map<String, List<GWTFormElement>> getProcessDefinitionForms(double id) throws OKMException {
		log.debug("getProcessDefinitionForms()");
		Map<String, List<GWTFormElement>> formElementList = new HashMap<String, List<GWTFormElement>>();
		updateSessionManager();
		
		try {
			Map<String, List<FormElement>> list = OKMWorkflow.getInstance().getProcessDefinitionForms(new Double(id).longValue());
			
			for (Iterator<String> it= list.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				List<FormElement> col = list.get(key);
				List<GWTFormElement> gwtCol = new ArrayList<GWTFormElement>();
				for (Iterator<FormElement> itf= col.iterator(); itf.hasNext();) {
					gwtCol.add(Util.copy(itf.next()));
				}
				formElementList.put(key, gwtCol);
			}
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_ParseException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("getProcessDefinitionForms: {}", formElementList);
		return formElementList;
	}
	
	@Override
	public void setTaskInstanceValues(double id, String transitionName, Map<String, Object> values ) throws OKMException {
		log.debug("setTaskInstanceValues()");
		updateSessionManager();
		
		try {
			OKMWorkflow.getInstance().setTaskInstanceValues(new Double(id).longValue(), transitionName, values);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("setTaskInstanceValues: void");
	}
	
	@Override
	public void addComment(double tokenId, String message) throws OKMException {
		log.debug("addComment({}, {})", tokenId, message);
		updateSessionManager();

		try {
			OKMWorkflow.getInstance().addTokenComment(new Double(tokenId).longValue(), message);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("addComment: void");
	}
	
	@Override
	public void setTaskInstanceActorId(double id) throws OKMException {
		log.debug("setTaskInstanceActorId({})", id);
		updateSessionManager();
		
		try {
			OKMWorkflow.getInstance().setTaskInstanceActorId(new Double(id).longValue(), getThreadLocalRequest().getRemoteUser());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_WorkflowException), e.getMessage());
		}
		
		log.debug("setTaskInstanceActorId: void");
	}
}
