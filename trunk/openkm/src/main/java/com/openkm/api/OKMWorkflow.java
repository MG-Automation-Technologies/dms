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

package com.openkm.api;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

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
 * @author pavila
 *
 */
public class OKMWorkflow implements WorkflowModule {
	private static Logger log = LoggerFactory.getLogger(OKMWorkflow.class);
	private static OKMWorkflow instance = new OKMWorkflow();

	private OKMWorkflow() {}
	
	public static OKMWorkflow getInstance() {
		return instance;
	}

	@Override
	public void registerProcessDefinition(ZipInputStream is) throws ParseException, RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("registerProcessDefinition({})", is);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.registerProcessDefinition(is);
		log.debug("registerProcessDefinition: void");
	}

	@Override
	public void deleteProcessDefinition(long processDefinitionId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("deleteProcessDefinition({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessDefinition(processDefinitionId);
		log.debug("deleteProcessDefinition: void");
	}

	@Override
	public ProcessDefinition getProcessDefinition(long processDefinitionId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getProcessDefinition({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessDefinition result = wm.getProcessDefinition(processDefinitionId);
		log.debug("getProcessDefinition: {}", result);
		return result;
	}

	@Override
	public byte[] getProcessDefinitionImage(long processDefinitionId, String node) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getProcessDefinitionImage({})", processDefinitionId, node);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		byte[] result = wm.getProcessDefinitionImage(processDefinitionId, node);
		log.debug("getProcessDefinitionImage: {}", result);
		return result;
	}
	
	@Override
	public Map<String, List<FormElement>> getProcessDefinitionForms(long processDefinitionId) throws 
			ParseException, RepositoryException, DatabaseException, WorkflowException {
		log.debug("getProcessDefinitionForms({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Map<String, List<FormElement>> result = wm.getProcessDefinitionForms(processDefinitionId);
		log.debug("getProcessDefinitionForms: "+result);
		return result;
	}

	@Override
	public ProcessInstance runProcessDefinition(long processDefinitionId, String uuid, 
			List<FormElement> variables) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("runProcessDefinition({})", processDefinitionId, variables);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.runProcessDefinition(processDefinitionId, uuid, variables);
		log.debug("runProcessDefinition: {}", result);
		return result;
	}

	@Override
	public ProcessInstance sendProcessInstanceSignal(long processInstanceId, String transitionName) throws
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("sendProcessInstanceSignal({})", processInstanceId, transitionName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.sendProcessInstanceSignal(processInstanceId, transitionName);
		log.debug("sendProcessInstanceSignal: {}", result);
		return result;
	}
	
	@Override
	public void endProcessInstance(long processInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("endProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endProcessInstance(processInstanceId);
		log.debug("endProcessInstance: void");
	}

	@Override
	public void deleteProcessInstance(long processInstanceId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("deleteProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessInstance(processInstanceId);
		log.debug("deleteProcessInstance: void");
	}

	@Override
	public List<ProcessInstance> findProcessInstances(long processDefinitionId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("findProcessInstances({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessInstance> result = wm.findProcessInstances(processDefinitionId);
		log.debug("findProcessInstances: {}", result);
		return result;
	}
	
	@Override
	public List<ProcessDefinition> findAllProcessDefinitions() throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("findAllProcessDefinitions()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> result = wm.findAllProcessDefinitions();
		log.debug("findAllProcessDefinitions: {}", result);
		return result;
	}

	@Override
	public List<ProcessDefinition> findLatestProcessDefinitions() throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("findLatestProcessDefinitions()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> result = wm.findLatestProcessDefinitions();
		log.debug("findLatestProcessDefinitions: {}", result);
		return result;
	}

	@Override
	public List<ProcessDefinition> findAllProcessDefinitionVersions(String name) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("findAllProcessDefinitionVersions({})", name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> result = wm.findAllProcessDefinitionVersions(name);
		log.debug("findAllProcessDefinitionVersions: {}", result);
		return result;
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("getProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.getProcessInstance(processInstanceId);
		log.debug("getProcessInstance: {}", result);
		return result;
	}

	@Override
	public void suspendProcessInstance(long processInstanceId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("suspendProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendProcessInstance(processInstanceId);
		log.debug("suspendProcessInstance: void");
	}

	@Override
	public void resumeProcessInstance(long processInstanceId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("resumeProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeProcessInstance(processInstanceId);
		log.debug("resumeProcessInstance: void");
	}

	@Override
	public void addProcessInstanceVariable(long processInstanceId, String name, Object value) throws
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("addProcessInstanceVariable({}, {}, {})", new Object[] { processInstanceId, name, value });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addProcessInstanceVariable(processInstanceId, name, value);
		log.debug("addProcessInstanceVariable: void");
	}

	@Override
	public void deleteProcessInstanceVariable(long processInstanceId, String name) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("deleteProcessInstanceVariable({}, {})", processInstanceId, name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessInstanceVariable(processInstanceId, name);
		log.debug("deleteProcessInstanceVariable: void");
	}

	@Override
	public List<TaskInstance> findUserTaskInstances() throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("findUserTaskInstances()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> result = wm.findUserTaskInstances();
		log.debug("findUserTaskInstances: {}", result);
		return result;
	}

	@Override
	public List<TaskInstance> findPooledTaskInstances() throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("findPooledTaskInstances()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> result = wm.findPooledTaskInstances();
		log.debug("findPooledTaskInstances: {}", result);
		return result;
	}

	@Override
	public List<TaskInstance> findTaskInstances(long processInstanceId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("findTaskInstances({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> result = wm.findTaskInstances(processInstanceId);
		log.debug("findTaskInstances: {}", result);
		return result;
	}

	@Override
	public void setTaskInstanceValues(long taskInstanceId, String transitionName, List<FormElement> values)
			throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("setTaskInstanceValues({}, {}, {})", new Object[] { taskInstanceId, transitionName, values });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceValues(taskInstanceId, transitionName, values);
		log.debug("setTaskInstanceValues: void");
	}

	@Override
	public void addTaskInstanceComment(long taskInstanceId, String message) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("addTaskInstanceComment({}, {})", taskInstanceId, message);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceComment(taskInstanceId, message);
		log.debug("addTaskInstanceComment: void");
	}

	@Override
	public TaskInstance getTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("getTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		TaskInstance result = wm.getTaskInstance(taskInstanceId);
		log.debug("getTaskInstance: {}", result);
		return result;
	}

	@Override
	public void setTaskInstanceActorId(long taskInstanceId, String actorId) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("setTaskInstanceActorId({})", taskInstanceId, actorId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceActorId(taskInstanceId, actorId);
		log.debug("setTaskInstanceActorId: void");
	}

	@Override
	public void addTaskInstanceVariable(long taskInstanceId, String name, Object value) throws 
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("addTaskInstanceVariable({}, {}, {})", new Object[] { taskInstanceId, name, value });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceVariable(taskInstanceId, name, value);
		log.debug("addTaskInstanceVariable: void");
	}

	@Override
	public void deleteTaskInstanceVariable(long taskInstanceId, String name) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("deleteTaskInstanceVariable({}, {})", taskInstanceId, name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteTaskInstanceVariable(taskInstanceId, name);
		log.debug("deleteTaskInstanceVariable: void");
	}

	@Override
	public void startTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("startTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.startTaskInstance(taskInstanceId);
		log.debug("startTaskInstance: void");
	}

	@Override
	public void endTaskInstance(long taskInstanceId, String transitionName) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("endTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endTaskInstance(taskInstanceId, transitionName);
		log.debug("endTaskInstance: void");
	}

	@Override
	public void suspendTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("suspendTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendTaskInstance(taskInstanceId);
		log.debug("suspendTaskInstance: void");
	}

	@Override
	public void resumeTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("resumeTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeTaskInstance(taskInstanceId);
		log.debug("resumeTaskInstance: void");
	}

	@Override
	public Token getToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("getToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.getToken(tokenId);
		log.debug("getToken: "+result);
		return result;
	}

	@Override
	public void addTokenComment(long tokenId, String message) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("addTokenComment({}, {})", tokenId, message);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTokenComment(tokenId, message);
		log.debug("addTokenComment: void");
	}

	@Override
	public void suspendToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("suspendToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendToken(tokenId);
		log.debug("suspendToken: void");
	}

	@Override
	public void resumeToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("resumeToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeToken(tokenId);
		log.debug("resumeToken: void");
	}

	@Override
	public Token sendTokenSignal(long tokenId, String transitionName) throws RepositoryException,
			DatabaseException, WorkflowException {
		log.debug("sendTokenSignal({}, {})", tokenId, transitionName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.sendTokenSignal(tokenId, transitionName);
		log.debug("sendTokenSignal: {}", result);
		return result;
	}

	@Override
	public void setTokenNode(long tokenId, String nodeName) throws RepositoryException, DatabaseException,
			WorkflowException {
		log.debug("setTokenNode({}, {})", tokenId, nodeName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTokenNode(tokenId, nodeName);
		log.debug("setTokenNode: void");
	}

	@Override
	public void endToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("endToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endToken(tokenId);
		log.debug("endToken: void");
	}
}
