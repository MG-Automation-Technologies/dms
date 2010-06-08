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
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
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
	public void registerProcessDefinition(String token, ZipInputStream is) throws ParseException, 
			RepositoryException {
		log.debug("registerProcessDefinition({})", is);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.registerProcessDefinition(token, is);
		log.debug("registerProcessDefinition: void");
	}

	@Override
	public void deleteProcessDefinition(String token, long processDefinitionId) throws RepositoryException {
		log.debug("deleteProcessDefinition({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessDefinition(token, processDefinitionId);
		log.debug("deleteProcessDefinition: void");
	}

	@Override
	public ProcessDefinition getProcessDefinition(String token, long processDefinitionId) throws 
			RepositoryException {
		log.debug("getProcessDefinition({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessDefinition result = wm.getProcessDefinition(token, processDefinitionId);
		log.debug("getProcessDefinition: {}", result);
		return result;
	}

	@Override
	public byte[] getProcessDefinitionImage(String token, long processDefinitionId, String node) throws 
			RepositoryException {
		log.debug("getProcessDefinitionImage({})", processDefinitionId, node);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		byte[] result = wm.getProcessDefinitionImage(token, processDefinitionId, node);
		log.debug("getProcessDefinitionImage: {}", result);
		return result;
	}
	
	@Override
	public Map<String, List<FormElement>> getProcessDefinitionForms(String token, long processDefinitionId)
			throws ParseException, RepositoryException {
		log.debug("getProcessDefinitionForms({})", processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Map<String, List<FormElement>> result = wm.getProcessDefinitionForms(token, processDefinitionId);
		log.debug("getProcessDefinitionForms: "+result);
		return result;
	}

	@Override
	public ProcessInstance runProcessDefinition(String token, long processDefinitionId, 
			Map<String, Object> variables) throws RepositoryException {
		log.debug("runProcessDefinition({})", processDefinitionId, variables);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.runProcessDefinition(token, processDefinitionId, variables);
		log.debug("runProcessDefinition: {}", result);
		return result;
	}

	@Override
	public ProcessInstance sendProcessInstanceSignal(String token, long processInstanceId, String transitionName)
			throws RepositoryException {
		log.debug("sendProcessInstanceSignal({})", processInstanceId, transitionName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.sendProcessInstanceSignal(token, processInstanceId, transitionName);
		log.debug("sendProcessInstanceSignal: {}", result);
		return result;
	}

	@Override
	public void deleteProcessInstance(String token, long processInstanceId) throws RepositoryException {
		log.debug("deleteProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessInstance(token, processInstanceId);
		log.debug("deleteProcessInstance: void");
	}

	@Override
	public List<ProcessInstance> findProcessInstances(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("findProcessInstances({})", token, processDefinitionId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessInstance> result = wm.findProcessInstances(token, processDefinitionId);
		log.debug("findProcessInstances: {}", result);
		return result;
	}
	
	@Override
	public List<ProcessDefinition> findAllProcessDefinitions(String token) throws RepositoryException {
		log.debug("findAllProcessDefinitions()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> result = wm.findAllProcessDefinitions(token);
		log.debug("findAllProcessDefinitions: {}", result);
		return result;
	}

	@Override
	public List<ProcessDefinition> findLatestProcessDefinitions(String token) throws 
			RepositoryException {
		log.debug("findLatestProcessDefinitions()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> result = wm.findLatestProcessDefinitions(token);
		log.debug("findLatestProcessDefinitions: {}", result);
		return result;
	}

	@Override
	public List<ProcessDefinition> findAllProcessDefinitionVersions(String token, String name) throws 
			RepositoryException {
		log.debug("findAllProcessDefinitionVersions({})", name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<ProcessDefinition> result = wm.findAllProcessDefinitionVersions(token, name);
		log.debug("findAllProcessDefinitionVersions: {}", result);
		return result;
	}

	@Override
	public ProcessInstance getProcessInstance(String token, long processInstanceId) throws 
			RepositoryException {
		log.debug("getProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.getProcessInstance(token, processInstanceId);
		log.debug("getProcessInstance: {}", result);
		return result;
	}

	@Override
	public void suspendProcessInstance(String token, long processInstanceId) throws RepositoryException {
		log.debug("suspendProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendProcessInstance(token, processInstanceId);
		log.debug("suspendProcessInstance: void");
	}

	@Override
	public void resumeProcessInstance(String token, long processInstanceId) throws RepositoryException {
		log.debug("resumeProcessInstance({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeProcessInstance(token, processInstanceId);
		log.debug("resumeProcessInstance: void");
	}

	@Override
	public void addProcessInstanceVariable(String token, long processInstanceId, String name, Object value)
			throws RepositoryException {
		log.debug("addProcessInstanceVariable({}, {}, {})", new Object[] { processInstanceId, name, value });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addProcessInstanceVariable(token, processInstanceId, name, value);
		log.debug("addProcessInstanceVariable: void");
	}

	@Override
	public void removeProcessInstanceVariable(String token, long processInstanceId, String name) throws 
			RepositoryException {
		log.debug("removeProcessInstanceVariable({}, {})", processInstanceId, name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.removeProcessInstanceVariable(token, processInstanceId, name);
		log.debug("removeProcessInstanceVariable: void");
	}

	@Override
	public List<TaskInstance> findUserTaskInstances(String token) throws RepositoryException {
		log.debug("findUserTaskInstances()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> result = wm.findUserTaskInstances(token);
		log.debug("findUserTaskInstances: {}", result);
		return result;
	}

	@Override
	public List<TaskInstance> findPooledTaskInstances(String token) throws RepositoryException {
		log.debug("findPooledTaskInstances()");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> result = wm.findPooledTaskInstances(token);
		log.debug("findPooledTaskInstances: {}", result);
		return result;
	}

	@Override
	public List<TaskInstance> findTaskInstances(String token, long processInstanceId) throws 
			RepositoryException {
		log.debug("findTaskInstances({})", processInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		List<TaskInstance> result = wm.findTaskInstances(token, processInstanceId);
		log.debug("findTaskInstances: {}", result);
		return result;
	}

	@Override
	public void setTaskInstanceValues(String token, long taskInstanceId, String transitionName, 
			Map<String, Object> values) throws RepositoryException {
		log.debug("setTaskInstanceValues({}, {}, {})", new Object[] { taskInstanceId, transitionName, values });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceValues(token, taskInstanceId, transitionName, values);
		log.debug("setTaskInstanceValues: void");
	}

	@Override
	public void addTaskInstanceComment(String token, long taskInstanceId, String message) throws 
			RepositoryException {
		log.debug("addTaskInstanceComment({}, {})", taskInstanceId, message);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceComment(token, taskInstanceId, message);
		log.debug("addTaskInstanceComment: void");
	}

	@Override
	public TaskInstance getTaskInstance(String token, long taskInstanceId) throws RepositoryException {
		log.debug("getTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		TaskInstance result = wm.getTaskInstance(token, taskInstanceId);
		log.debug("getTaskInstance: {}", result);
		return result;
	}

	@Override
	public void setTaskInstanceActorId(String token, long taskInstanceId, String actorId) throws 
			RepositoryException {
		log.debug("setTaskInstanceActorId({})", taskInstanceId, actorId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceActorId(token, taskInstanceId, actorId);
		log.debug("setTaskInstanceActorId: void");
	}

	@Override
	public void addTaskInstanceVariable(String token, long taskInstanceId, String name, Object value)
			throws RepositoryException {
		log.debug("addTaskInstanceVariable({}, {}, {})", new Object[] { taskInstanceId, name, value });
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceVariable(token, taskInstanceId, name, value);
		log.debug("addTaskInstanceVariable: void");
	}

	@Override
	public void removeTaskInstanceVariable(String token, long taskInstanceId, String name) throws 
			RepositoryException {
		log.debug("removeTaskInstanceVariable({}, {})", taskInstanceId, name);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.removeTaskInstanceVariable(token, taskInstanceId, name);
		log.debug("removeTaskInstanceVariable: void");
	}

	@Override
	public void startTaskInstance(String token, long taskInstanceId) throws RepositoryException {
		log.debug("startTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.startTaskInstance(token, taskInstanceId);
		log.debug("startTaskInstance: void");
	}

	@Override
	public void endTaskInstance(String token, long taskInstanceId, String transitionName) throws 
			RepositoryException {
		log.debug("endTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endTaskInstance(token, taskInstanceId, transitionName);
		log.debug("endTaskInstance: void");
	}

	@Override
	public void suspendTaskInstance(String token, long taskInstanceId) throws RepositoryException {
		log.debug("suspendTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendTaskInstance(token, taskInstanceId);
		log.debug("suspendTaskInstance: void");
	}

	@Override
	public void resumeTaskInstance(String token, long taskInstanceId) throws RepositoryException {
		log.debug("resumeTaskInstance({})", taskInstanceId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeTaskInstance(token, taskInstanceId);
		log.debug("resumeTaskInstance: void");
	}

	@Override
	public Token getToken(String token, long tokenId) throws RepositoryException {
		log.debug("getToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.getToken(token, tokenId);
		log.debug("getToken: "+result);
		return result;
	}

	@Override
	public void addTokenComment(String token, long tokenId, String message) throws RepositoryException {
		log.debug("addTokenComment({}, {})", tokenId, message);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTokenComment(token, tokenId, message);
		log.debug("addTokenComment: void");
	}

	@Override
	public void suspendToken(String token, long tokenId) throws RepositoryException {
		log.debug("suspendToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendToken(token, tokenId);
		log.debug("suspendToken: void");
	}

	@Override
	public void resumeToken(String token, long tokenId) throws RepositoryException {
		log.debug("resumeToken({})", tokenId);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeToken(token, tokenId);
		log.debug("resumeToken: void");
	}

	@Override
	public Token sendTokenSignal(String token, long tokenId, String transitionName) throws 
			RepositoryException {
		log.debug("sendTokenSignal({}, {})", tokenId, transitionName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.sendTokenSignal(token, tokenId, transitionName);
		log.debug("sendTokenSignal: {}", result);
		return result;
	}

	@Override
	public void setTokenNode(String token, long tokenId, String nodeName) throws RepositoryException {
		log.debug("setTokenNode({})", tokenId, nodeName);
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTokenNode(token, tokenId, nodeName);
		log.debug("setTokenNode: void");
	}
}
