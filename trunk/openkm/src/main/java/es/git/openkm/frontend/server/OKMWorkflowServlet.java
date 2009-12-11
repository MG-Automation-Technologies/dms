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
import es.git.openkm.bean.FormField;
import es.git.openkm.bean.workflow.ProcessDefinition;
import es.git.openkm.bean.workflow.TaskInstance;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTFormField;
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
	public void runProcessDefinition(String docPath, double id) throws OKMException  {
		log.debug("runProcessDefinition()");
		Map<String,String> variables = new HashMap<String,String>();
		variables.put("path", docPath);
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
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#getProcessDefinitionForms(long)
	 */
	public Map<String, Collection<GWTFormField>> getProcessDefinitionForms(double id) throws OKMException {
		log.debug("getProcessDefinitionForms()");
		Map<String, Collection<GWTFormField>> formFieldList = new HashMap<String, Collection<GWTFormField>>();
		String token = getToken();
		
		try {
			Map<String, Collection<FormField>> list = OKMWorkflow.getInstance().getProcessDefinitionForms(token, new Double(id).longValue());
			
			for (Iterator<String> it= list.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				Collection<FormField> col = list.get(key);
				Collection<GWTFormField> gwtCol = new ArrayList<GWTFormField>();
				for (Iterator<FormField> itf= col.iterator(); itf.hasNext();) {
					gwtCol.add(Util.copy(itf.next()));
				}
				formFieldList.put(key, gwtCol);
			}
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}

		
		log.debug("getProcessDefinitionForms:");
		return formFieldList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkflowService#setTaskInstanceValues(double, java.lang.String, java.util.Map)
	 */
	public void setTaskInstanceValues(double id, String transitionName, Map<String, String> values ) throws OKMException {
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
	
}