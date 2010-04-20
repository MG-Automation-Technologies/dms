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

package es.git.openkm.frontend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMWorkflow;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.bean.workflow.ProcessDefinition;
import es.git.openkm.bean.workflow.TaskInstance;
import es.git.openkm.core.Config;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTFormElement;
import es.git.openkm.frontend.client.bean.GWTProcessDefinition;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMWorkflowService;

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
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#findLatestProcessDefinitions()
	 */
	public List<GWTProcessDefinition> findLatestProcessDefinitions() throws OKMException {
		log.debug("findLatestProcessDefinitions()");
		List<GWTProcessDefinition> processDefinitionList = new ArrayList<GWTProcessDefinition>();
		String token = getToken();
		
		try {
			for (Iterator<ProcessDefinition> it = OKMWorkflow.getInstance().findLatestProcessDefinitions(token).iterator(); it.hasNext();) {
				processDefinitionList.add(Util.copy(it.next()));
			}

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("findLatestProcessDefinitions:"+processDefinitionList);
		return processDefinitionList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#runProcessDefinition(java.lang.String, double)
	 */
	public void runProcessDefinition(String UUID, double id, Map<String,Object> variables) throws OKMException  {
		log.debug("runProcessDefinition()");
		variables.put(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_UUID, UUID);
		String token = getToken();
		
		try {
			OKMWorkflow.getInstance().runProcessDefinition(token, new Double(id).longValue(), variables);

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("runProcessDefinition:");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#findUserTaskInstances()
	 */
	public List<GWTTaskInstance> findUserTaskInstances() throws OKMException {
		log.debug("findUserTaskInstances()");
		List<GWTTaskInstance> taskInstances = new ArrayList<GWTTaskInstance>();
		String token = getToken();
		
		try {
			for (Iterator<TaskInstance> it= OKMWorkflow.getInstance().findUserTaskInstances(token).iterator(); it.hasNext();) {
				taskInstances.add(Util.copy(it.next()));
			}
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("findUserTaskInstances:");
		return taskInstances;
	}
	

	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#findPooledTaskInstances()
	 */
	public List<GWTTaskInstance> findPooledTaskInstances() throws OKMException {
		log.debug("findPooledTaskInstances()");
		List<GWTTaskInstance> taskInstances = new ArrayList<GWTTaskInstance>();
		String token = getToken();
		
		try {
			for (Iterator<TaskInstance> it= OKMWorkflow.getInstance().findPooledTaskInstances(token).iterator(); it.hasNext();) {
				taskInstances.add(Util.copy(it.next()));
			}
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("findPooledTaskInstances:");
		return taskInstances;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#getProcessDefinitionForms(long)
	 */
	public Map<String, Collection<GWTFormElement>> getProcessDefinitionForms(double id) throws OKMException {
		log.debug("getProcessDefinitionForms()");
		Map<String, Collection<GWTFormElement>> formElementList = new HashMap<String, Collection<GWTFormElement>>();
		String token = getToken();
		
		try {
			Map<String, Collection<FormElement>> list = OKMWorkflow.getInstance().getProcessDefinitionForms(token, new Double(id).longValue());
			
			for (Iterator<String> it= list.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				Collection<FormElement> col = list.get(key);
				Collection<GWTFormElement> gwtCol = new ArrayList<GWTFormElement>();
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
		}

		
		log.debug("getProcessDefinitionForms:");
		return formElementList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#setTaskInstanceValues(double, java.lang.String, java.util.Map)
	 */
	public void setTaskInstanceValues(double id, String transitionName, Map<String, Object> values ) throws OKMException {
		log.debug("setTaskInstanceValues()");
		String token = getToken();
	
		try {
			OKMWorkflow.getInstance().setTaskInstanceValues(token, new Double(id).longValue(), transitionName, values);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		log.debug("setTaskInstanceValues:");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#addComment(double, java.lang.String)
	 */
	public void addComment(double tokenId, String message) throws OKMException {
		log.debug("addComment(tokenId:"+tokenId+",message:"+message+")");
		String token = getToken();

		try {
			OKMWorkflow.getInstance().addTokenComment(token, new Double(tokenId).longValue(), message);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		log.debug("addComment:");
	}
	
}