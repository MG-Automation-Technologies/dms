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

package com.openkm.backend.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMWorkflow;
import com.openkm.bean.workflow.ProcessDefinition;
import com.openkm.bean.workflow.ProcessInstance;
import com.openkm.core.RepositoryException;
import com.openkm.backend.client.OKMException;
import com.openkm.backend.client.bean.GWTProcessDefinition;
import com.openkm.backend.client.bean.GWTProcessInstance;
import com.openkm.backend.client.config.ErrorCode;
import com.openkm.backend.client.service.OKMWorkflowService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMWorkflowServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMWorkflowServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMWorkflowServletAdmin extends OKMRemoteServiceServletAdmin implements OKMWorkflowService {

	private static Logger log = LoggerFactory.getLogger(OKMWorkflowServletAdmin.class);
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMWorkflowService#findLatestProcessDefinitions()
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
	 * @see com.openkm.backend.client.service.OKMWorkflowService#runProcessDefinition(java.lang.String, double)
	 */
	public void runProcessDefinition(String docPath, double id) throws OKMException  {
		log.debug("runProcessDefinition()");
		Map<String,Object> variables = new HashMap<String,Object>();
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
	 * @see com.openkm.backend.client.service.OKMWorkflowService#findAllProcessDefinitionVersions(java.lang.String)
	 */
	public List<GWTProcessDefinition> findAllProcessDefinitionVersions(String name) throws OKMException {
		log.debug("findAllProcessDefinitionVersions(name:"+name+")");
		List<GWTProcessDefinition> processDefinitionList = new ArrayList<GWTProcessDefinition>();
		String token = getToken();
		
		try {
			for (Iterator<ProcessDefinition> it = OKMWorkflow.getInstance().findAllProcessDefinitionVersions(token, name).iterator(); it.hasNext();) {
				processDefinitionList.add(Util.copy(it.next()));
			}

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("findAllProcessDefinitionVersions:"+processDefinitionList);
		return processDefinitionList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMWorkflowService#findProcessInstances(double)
	 */
	public List<GWTProcessInstance> findProcessInstances(double id) throws OKMException {
		log.debug("findProcessInstances(id:"+id+")");
		List<GWTProcessInstance> processInstanceList = new ArrayList<GWTProcessInstance>();
		String token = getToken();
		
		try {
			for (Iterator<ProcessInstance> it = OKMWorkflow.getInstance().findProcessInstances(token, new Double(id).longValue() ).iterator(); it.hasNext();) {
				processInstanceList.add(Util.copy(it.next()));
			}

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("findProcessInstances:"+processInstanceList);
		return processInstanceList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMWorkflowService#deleteProcessDefinition(double)
	 */
	public void deleteProcessDefinition(double id) throws OKMException {
		log.debug("deleteProcessDefinition(id:"+id+")");
		String token = getToken();
		
		try {
			OKMWorkflow.getInstance().deleteProcessDefinition(token, new Double(id).longValue());

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("deleteProcessDefinition:");
	}
	
}