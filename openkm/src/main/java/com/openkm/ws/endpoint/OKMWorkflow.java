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

package com.openkm.ws.endpoint;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.form.FormElement;
import com.openkm.bean.workflow.ProcessDefinition;
import com.openkm.bean.workflow.ProcessInstance;
import com.openkm.bean.workflow.TaskInstance;
import com.openkm.bean.workflow.Token;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.core.WorkflowException;
import com.openkm.module.ModuleManager;
import com.openkm.module.WorkflowModule;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMWorkflow"
 * @web.servlet-mapping url-pattern="/OKMWorkflow"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@SecurityDomain("OpenKM")
public class OKMWorkflow {
	private static Logger log = LoggerFactory.getLogger(OKMWorkflow.class);
	
	@WebMethod
	public void registerProcessDefinition(@WebParam(name = "pda") byte[] pda) throws
			ParseException, RepositoryException, DatabaseException, WorkflowException {
		log.debug("registerProcessDefinition({})", pda);
		ByteArrayInputStream bais = new ByteArrayInputStream(pda);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.registerProcessDefinition(bais);
		IOUtils.closeQuietly(bais);
		log.debug("registerProcessDefinition: void");
	}
	
	@WebMethod
	public void deleteProcessDefinition(@WebParam(name = "pdId") long pdId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("deleteProcessDefinition({})", pdId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessDefinition(pdId);
		log.debug("deleteProcessDefinition: void");
	}
	
	@WebMethod
	public ProcessDefinition getProcessDefinition(@WebParam(name = "pdId") long pdId) throws 
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("getProcessDefinition({})", pdId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessDefinition result = wm.getProcessDefinition(pdId);
		log.debug("getProcessDefinition: {}", result);
		return result;
	}
	
	@WebMethod
	public byte[] getProcessDefinitionImage(@WebParam(name = "pdId") long pdId,
			@WebParam(name = "node") String node) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getProcessDefinitionImage({})", pdId, node);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		byte[] result = wm.getProcessDefinitionImage(pdId, node);
		log.debug("getProcessDefinitionImage: {}", result);
		return result;
	}
	
	/*
	public Map<String, List<FormElement>> getProcessDefinitionForms(@WebParam(name = "pdId") long pdId) throws 
			ParseException, RepositoryException, DatabaseException, WorkflowException {
		log.debug("getProcessDefinitionForms({})", pdId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Map<String, List<FormElement>> result = wm.getProcessDefinitionForms(pdId);
		log.debug("getProcessDefinitionForms: "+result);
		return result;
	}
	*/
	
	@WebMethod
	public ProcessInstance runProcessDefinition(@WebParam(name = "pdId") long pdId,
			@WebParam(name = "uuid") String uuid, 
			@WebParam(name = "vars") FormElement[] vars) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("runProcessDefinition({})", pdId, vars);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<FormElement> col = Arrays.asList(vars);
		ProcessInstance result = wm.runProcessDefinition(pdId, uuid, col);
		log.debug("runProcessDefinition: {}", result);
		return result;
	}
	
	@WebMethod
	public ProcessInstance sendProcessInstanceSignal(@WebParam(name = "piId") long piId,
			@WebParam(name = "transName") String transName) throws
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("sendProcessInstanceSignal({})", piId, transName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.sendProcessInstanceSignal(piId, transName);
		log.debug("sendProcessInstanceSignal: {}", result);
		return result;
	}
	
	@WebMethod
	public void endProcessInstance(@WebParam(name = "piId") long piId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("endProcessInstance({})", piId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endProcessInstance(piId);
		log.debug("endProcessInstance: void");
	}
	
	@WebMethod
	public void deleteProcessInstance(@WebParam(name = "piId") long piId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("deleteProcessInstance({})", piId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessInstance(piId);
		log.debug("deleteProcessInstance: void");
	}
	
	@WebMethod
	public ProcessInstance[] findProcessInstances(@WebParam(name = "pdId") long pdId) throws 
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("findProcessInstances({})", pdId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessInstance> col = wm.findProcessInstances(pdId);
		ProcessInstance[] result = (ProcessInstance[]) col.toArray(new ProcessInstance[col.size()]);
		log.debug("findProcessInstances: {}", result);
		return result;
	}
	
	@WebMethod
	public ProcessDefinition[] findAllProcessDefinitions() throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("findAllProcessDefinitions()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> col = wm.findAllProcessDefinitions();
		ProcessDefinition[] result = (ProcessDefinition[]) col.toArray(new ProcessDefinition[col.size()]);
		log.debug("findAllProcessDefinitions: {}", result);
		return result;
	}
	
	@WebMethod
	public ProcessDefinition[] findLatestProcessDefinitions() throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("findLatestProcessDefinitions()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> col = wm.findLatestProcessDefinitions();
		ProcessDefinition[] result = (ProcessDefinition[]) col.toArray(new ProcessDefinition[col.size()]);
		log.debug("findLatestProcessDefinitions: {}", result);
		return result;
	}
	
	@WebMethod
	public ProcessDefinition[] findAllProcessDefinitionVersions(@WebParam(name = "name") String name) throws 
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("findAllProcessDefinitionVersions({})", name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> col = wm.findAllProcessDefinitionVersions(name);
		ProcessDefinition[] result = (ProcessDefinition[]) col.toArray(new ProcessDefinition[col.size()]);
		log.debug("findAllProcessDefinitionVersions: {}", result);
		return result;
	}
	
	@WebMethod
	public ProcessInstance getProcessInstance(@WebParam(name = "piId") long piId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getProcessInstance({})", piId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.getProcessInstance(piId);
		log.debug("getProcessInstance: {}", result);
		return result;
	}
	
	@WebMethod
	public void suspendProcessInstance(@WebParam(name = "piId") long piId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("suspendProcessInstance({})", piId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendProcessInstance(piId);
		log.debug("suspendProcessInstance: void");
	}
	
	@WebMethod
	public void resumeProcessInstance(@WebParam(name = "piId") long piId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("resumeProcessInstance({})", piId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeProcessInstance(piId);
		log.debug("resumeProcessInstance: void");
	}

	@WebMethod
	public void addProcessInstanceVariable(@WebParam(name = "piId") long piId,
			@WebParam(name = "name") String name,
			@WebParam(name = "value") Object value) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("addProcessInstanceVariable({}, {}, {})", new Object[] { piId, name, value });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addProcessInstanceVariable(piId, name, value);
		log.debug("addProcessInstanceVariable: void");
	}
	
	@WebMethod
	public void deleteProcessInstanceVariable(@WebParam(name = "piId") long piId,
			@WebParam(name = "name") String name) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("deleteProcessInstanceVariable({}, {})", piId, name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessInstanceVariable(piId, name);
		log.debug("deleteProcessInstanceVariable: void");
	}
	
	@WebMethod
	public TaskInstance[] findUserTaskInstances() throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("findUserTaskInstances()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> col = wm.findUserTaskInstances();
		TaskInstance[] result = (TaskInstance[]) col.toArray(new TaskInstance[col.size()]);
		log.debug("findUserTaskInstances: {}", result);
		return result;
	}
	
	@WebMethod
	public TaskInstance[] findPooledTaskInstances() throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("findPooledTaskInstances()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> col = wm.findPooledTaskInstances();
		TaskInstance[] result = (TaskInstance[]) col.toArray(new TaskInstance[col.size()]);
		log.debug("findPooledTaskInstances: {}", result);
		return result;
	}
	
	@WebMethod
	public TaskInstance[] findTaskInstances(@WebParam(name = "piId") long piId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("findTaskInstances({})", piId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> col = wm.findTaskInstances(piId);
		TaskInstance[] result = (TaskInstance[]) col.toArray(new TaskInstance[col.size()]);
		log.debug("findTaskInstances: {}", result);
		return result;
	}
	
	@WebMethod
	public void setTaskInstanceValues(@WebParam(name = "tiId") long tiId,
			@WebParam(name = "transName") String transName,
			@WebParam(name = "values") FormElement[] values)
			throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("setTaskInstanceValues({}, {}, {})", new Object[] { tiId, transName, values });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<FormElement> col = Arrays.asList(values);
		wm.setTaskInstanceValues(tiId, transName, col);
		log.debug("setTaskInstanceValues: void");
	}
	
	@WebMethod
	public void addTaskInstanceComment(@WebParam(name = "tiId") long tiId,
			@WebParam(name = "message") String message) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("addTaskInstanceComment({}, {})", tiId, message);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceComment(tiId, message);
		log.debug("addTaskInstanceComment: void");
	}
	
	@WebMethod
	public TaskInstance getTaskInstance(@WebParam(name = "tiId") long tiId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getTaskInstance({})", tiId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		TaskInstance result = wm.getTaskInstance(tiId);
		log.debug("getTaskInstance: {}", result);
		return result;
	}
	
	@WebMethod
	public void setTaskInstanceActorId(@WebParam(name = "tiId") long tiId,
			@WebParam(name = "actorId") String actorId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("setTaskInstanceActorId({})", tiId, actorId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceActorId(tiId, actorId);
		log.debug("setTaskInstanceActorId: void");
	}
	
	@WebMethod
	public void addTaskInstanceVariable(@WebParam(name = "tiId") long tiId, 
			@WebParam(name = "name") String name,
			@WebParam(name = "value") Object value) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("addTaskInstanceVariable({}, {}, {})", new Object[] { tiId, name, value });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceVariable(tiId, name, value);
		log.debug("addTaskInstanceVariable: void");
	}
	
	@WebMethod
	public void deleteTaskInstanceVariable(@WebParam(name = "tiId") long tiId,
			@WebParam(name = "name") String name) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("deleteTaskInstanceVariable({}, {})", tiId, name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteTaskInstanceVariable(tiId, name);
		log.debug("deleteTaskInstanceVariable: void");
	}
	
	@WebMethod
	public void startTaskInstance(@WebParam(name = "tiId") long tiId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("startTaskInstance({})", tiId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.startTaskInstance(tiId);
		log.debug("startTaskInstance: void");
	}
	
	@WebMethod
	public void endTaskInstance(@WebParam(name = "tiId") long tiId,
			@WebParam(name = "transName") String transName) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("endTaskInstance({}, {})", tiId, transName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endTaskInstance(tiId, transName);
		log.debug("endTaskInstance: void");
	}
	
	@WebMethod
	public void suspendTaskInstance(@WebParam(name = "tiId") long tiId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("suspendTaskInstance({})", tiId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendTaskInstance(tiId);
		log.debug("suspendTaskInstance: void");
	}
	
	@WebMethod
	public void resumeTaskInstance(@WebParam(name = "tiId") long tiId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("resumeTaskInstance({})", tiId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeTaskInstance(tiId);
		log.debug("resumeTaskInstance: void");
	}
	
	@WebMethod
	public Token getToken(@WebParam(name = "tkId") long tkId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getToken({})", tkId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.getToken(tkId);
		log.debug("getToken: "+result);
		return result;
	}

	@WebMethod
	public void addTokenComment(@WebParam(name = "tkId") long tkId,
			@WebParam(name = "message") String message) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("addTokenComment({}, {})", tkId, message);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTokenComment(tkId, message);
		log.debug("addTokenComment: void");
	}
	
	@WebMethod
	public void suspendToken(@WebParam(name = "tkId") long tkId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("suspendToken({})", tkId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendToken(tkId);
		log.debug("suspendToken: void");
	}
	
	@WebMethod
	public void resumeToken(@WebParam(name = "tkId") long tkId) throws RepositoryException, 
			DatabaseException, WorkflowException {
		log.debug("resumeToken({})", tkId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeToken(tkId);
		log.debug("resumeToken: void");
	}
	
	@WebMethod
	public Token sendTokenSignal(@WebParam(name = "tkId") long tkId,
			@WebParam(name = "transName") String transName) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("sendTokenSignal({}, {})", tkId, transName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.sendTokenSignal(tkId, transName);
		log.debug("sendTokenSignal: {}", result);
		return result;
	}
	
	@WebMethod
	public void setTokenNode(@WebParam(name = "tkId") long tkId,
			@WebParam(name = "nodeName") String nodeName) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("setTokenNode({}, {})", tkId, nodeName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTokenNode(tkId, nodeName);
		log.debug("setTokenNode: void");
	}
	
	@WebMethod
	public void endToken(@WebParam(name = "tkId") long tkId) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("endToken({})", tkId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endToken(tkId);
		log.debug("endToken: void");
	}
}
