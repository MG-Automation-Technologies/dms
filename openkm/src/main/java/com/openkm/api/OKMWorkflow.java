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

package com.openkm.api;

import java.util.Collection;
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

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#registerProcessDefinition(java.lang.String, java.util.zip.ZipInputStream)
	 */
	@Override
	public void registerProcessDefinition(String token, ZipInputStream is)
			throws ParseException, RepositoryException {
		log.debug("registerProcessDefinition(" + token + ", " + is + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.registerProcessDefinition(token, is);
		log.debug("registerProcessDefinition: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#deleteProcessDefinition(java.lang.String, long)
	 */
	@Override
	public void deleteProcessDefinition(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("deleteProcessDefinition(" + token + ", " + processDefinitionId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessDefinition(token, processDefinitionId);
		log.debug("deleteProcessDefinition: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#getProcessDefinition(java.lang.String, long)
	 */
	@Override
	public ProcessDefinition getProcessDefinition(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("getProcessDefinition(" + token + ", " + processDefinitionId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessDefinition result = wm.getProcessDefinition(token, processDefinitionId);
		log.debug("getProcessDefinition: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#getProcessDefinitionImage(java.lang.String, long)
	 */
	@Override
	public byte[] getProcessDefinitionImage(String token, long processDefinitionId, String node)
			throws RepositoryException {
		log.debug("getProcessDefinitionImage(" + token + ", " + processDefinitionId + ", " + node + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		byte[] result = wm.getProcessDefinitionImage(token, processDefinitionId, node);
		log.debug("getProcessDefinitionImage: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#getProcessDefinitionForms(java.lang.String, long)
	 */
	@Override
	public Map<String, Collection<FormElement>> getProcessDefinitionForms(String token, long processDefinitionId)
			throws ParseException, RepositoryException {
		log.debug("getProcessDefinitionForms(" + token + ", " + processDefinitionId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Map<String, Collection<FormElement>> result = wm.getProcessDefinitionForms(token, processDefinitionId);
		log.debug("getProcessDefinitionForms: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#runProcessDefinition(java.lang.String, long, java.util.Map)
	 */
	@Override
	public ProcessInstance runProcessDefinition(String token,
			long processDefinitionId, Map<String, Object> variables) throws RepositoryException {
		log.debug("runProcessDefinition(" + token + ", " + processDefinitionId + ", " + variables + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.runProcessDefinition(token, processDefinitionId, variables);
		log.debug("runProcessDefinition: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#sendProcessInstanceSignal(java.lang.String, long, java.lang.String)
	 */
	@Override
	public ProcessInstance sendProcessInstanceSignal(String token, long processInstanceId, String transitionName)
			throws RepositoryException {
		log.debug("sendProcessInstanceSignal(" + token + ", " + processInstanceId + ", " + transitionName + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.sendProcessInstanceSignal(token, processInstanceId, transitionName);
		log.debug("sendProcessInstanceSignal: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#deleteProcessInstance(java.lang.String, long)
	 */
	@Override
	public void deleteProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("deleteProcessInstance(" + token + ", " + processInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.deleteProcessInstance(token, processInstanceId);
		log.debug("deleteProcessInstance: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#findProcessInstances(java.lang.String, long)
	 */
	@Override
	public Collection<ProcessInstance> findProcessInstances(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("findProcessInstances(" + token + ", " + processDefinitionId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Collection<ProcessInstance> result = wm.findProcessInstances(token, processDefinitionId);
		log.debug("findProcessInstances: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#findAllProcessDefinitions(java.lang.String)
	 */
	@Override
	public Collection<ProcessDefinition> findAllProcessDefinitions(String token)
			throws RepositoryException {
		log.debug("findAllProcessDefinitions(" + token + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Collection<ProcessDefinition> result = wm.findAllProcessDefinitions(token);
		log.debug("findAllProcessDefinitions: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#findLatestProcessDefinitions(java.lang.String)
	 */
	@Override
	public Collection<ProcessDefinition> findLatestProcessDefinitions(String token)
			throws RepositoryException {
		log.debug("findLatestProcessDefinitions(" + token + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Collection<ProcessDefinition> result = wm.findLatestProcessDefinitions(token);
		log.debug("findLatestProcessDefinitions: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#findAllProcessDefinitionVersions(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<ProcessDefinition> findAllProcessDefinitionVersions(String token, String name)
			throws RepositoryException {
		log.debug("findAllProcessDefinitionVersions(" + token + ", " + name + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Collection<ProcessDefinition> result = wm.findAllProcessDefinitionVersions(token, name);
		log.debug("findAllProcessDefinitionVersions: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#getProcessInstance(java.lang.String, long)
	 */
	@Override
	public ProcessInstance getProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("getProcessInstance(" + token + ", " + processInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		ProcessInstance result = wm.getProcessInstance(token, processInstanceId);
		log.debug("getProcessInstance: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#suspendProcessInstance(java.lang.String, long)
	 */
	@Override
	public void suspendProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("suspendProcessInstance(" + token + ", " + processInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendProcessInstance(token, processInstanceId);
		log.debug("suspendProcessInstance: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#resumeProcessInstance(java.lang.String, long)
	 */
	@Override
	public void resumeProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("resumeProcessInstance(" + token + ", " + processInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeProcessInstance(token, processInstanceId);
		log.debug("resumeProcessInstance: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#addProcessInstanceVariable(java.lang.String, long, java.lang.String, java.lang.Object)
	 */
	@Override
	public void addProcessInstanceVariable(String token, long processInstanceId, String name,
			Object value) throws RepositoryException {
		log.debug("addProcessInstanceVariable(" + token + ", " + processInstanceId + ", " + name + ", " + value + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addProcessInstanceVariable(token, processInstanceId, name, value);
		log.debug("addProcessInstanceVariable: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#removeProcessInstanceVariable(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void removeProcessInstanceVariable(String token, long processInstanceId, String name)
			throws RepositoryException {
		log.debug("removeProcessInstanceVariable(" + token + ", " + processInstanceId + ", " + name + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.removeProcessInstanceVariable(token, processInstanceId, name);
		log.debug("removeProcessInstanceVariable: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#findUserTaskInstances(java.lang.String)
	 */
	@Override
	public Collection<TaskInstance> findUserTaskInstances(String token)
			throws RepositoryException {
		log.debug("findUserTaskInstances(" + token + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Collection<TaskInstance> result = wm.findUserTaskInstances(token);
		log.debug("findUserTaskInstances: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#findTaskInstances(java.lang.String, long)
	 */
	@Override
	public Collection<TaskInstance> findTaskInstances(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("findTaskInstances(" + token + ", " + processInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Collection<TaskInstance> result = wm.findTaskInstances(token, processInstanceId);
		log.debug("findTaskInstances: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#setTaskInstanceValues(java.lang.String, long, java.lang.String, java.util.Map)
	 */
	@Override
	public void setTaskInstanceValues(String token, long taskInstanceId,
			String transitionName, Map<String, Object> values)
			throws RepositoryException {
		log.debug("setTaskInstanceValues(" + token + ", " + taskInstanceId + ", " + transitionName + ", " + values + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceValues(token, taskInstanceId, transitionName, values);
		log.debug("setTaskInstanceValues: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#addTaskInstanceComment(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void addTaskInstanceComment(String token, long taskInstanceId, String message)
			throws RepositoryException {
		log.debug("addTaskInstanceComment(" + token + ", " + taskInstanceId + ", " + message + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceComment(token, taskInstanceId, message);
		log.debug("addTaskInstanceComment: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#getTaskInstance(java.lang.String, long)
	 */
	@Override
	public TaskInstance getTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.debug("getTaskInstance(" + token + ", " + taskInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		TaskInstance result = wm.getTaskInstance(token, taskInstanceId);
		log.debug("getTaskInstance: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#setTaskInstanceActorId(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void setTaskInstanceActorId(String token, long taskInstanceId,
			String actorId) throws RepositoryException {
		log.debug("setTaskInstanceActorId(" + token + ", " + taskInstanceId + ", " + actorId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTaskInstanceActorId(token, taskInstanceId, actorId);
		log.debug("setTaskInstanceActorId: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#addTaskInstanceVariable(java.lang.String, long, java.lang.String, java.lang.Object)
	 */
	@Override
	public void addTaskInstanceVariable(String token, long taskInstanceId,
			String name, Object value) throws RepositoryException {
		log.debug("addTaskInstanceVariable(" + token + ", " + taskInstanceId + ", " + name + ", " + value + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTaskInstanceVariable(token, taskInstanceId, name, value);
		log.debug("addTaskInstanceVariable: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#removeTaskInstanceVariable(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void removeTaskInstanceVariable(String token, long taskInstanceId,
			String name) throws RepositoryException {
		log.debug("removeTaskInstanceVariable(" + token + ", " + taskInstanceId + ", " + name + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.removeTaskInstanceVariable(token, taskInstanceId, name);
		log.debug("removeTaskInstanceVariable: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#startTaskInstance(java.lang.String, long)
	 */
	@Override
	public void startTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.debug("startTaskInstance(" + token + ", " + taskInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.startTaskInstance(token, taskInstanceId);
		log.debug("startTaskInstance: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#endTaskInstance(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void endTaskInstance(String token, long taskInstanceId,
			String transitionName) throws RepositoryException {
		log.debug("endTaskInstance(" + token + ", " + taskInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.endTaskInstance(token, taskInstanceId, transitionName);
		log.debug("endTaskInstance: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#suspendTaskInstance(java.lang.String, long)
	 */
	@Override
	public void suspendTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.debug("suspendTaskInstance(" + token + ", " + taskInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendTaskInstance(token, taskInstanceId);
		log.debug("suspendTaskInstance: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#resumeTaskInstance(java.lang.String, long)
	 */
	@Override
	public void resumeTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.debug("resumeTaskInstance(" + token + ", " + taskInstanceId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeTaskInstance(token, taskInstanceId);
		log.debug("resumeTaskInstance: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#getToken(java.lang.String, long)
	 */
	@Override
	public Token getToken(String token, long tokenId)
			throws RepositoryException {
		log.debug("getToken(" + token + ", " + tokenId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.getToken(token, tokenId);
		log.debug("getToken: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#addTokenComment(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void addTokenComment(String token, long tokenId, String message)
			throws RepositoryException {
		log.debug("addTokenComment(" + token + ", " + tokenId + ", " + message + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.addTokenComment(token, tokenId, message);
		log.debug("addTokenComment: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#suspendToken(java.lang.String, long)
	 */
	@Override
	public void suspendToken(String token, long tokenId) throws RepositoryException {
		log.debug("suspendToken(" + token + ", " + tokenId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.suspendToken(token, tokenId);
		log.debug("suspendToken: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#resumeToken(java.lang.String, long)
	 */
	@Override
	public void resumeToken(String token, long tokenId) throws RepositoryException {
		log.debug("resumeToken(" + token + ", " + tokenId + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.resumeToken(token, tokenId);
		log.debug("resumeToken: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#sendTokenSignal(java.lang.String, long, java.lang.String)
	 */
	@Override
	public Token sendTokenSignal(String token, long tokenId,
			String transitionName) throws RepositoryException {
		log.debug("sendTokenSignal(" + token + ", " + tokenId + ", " + transitionName + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		Token result = wm.sendTokenSignal(token, tokenId, transitionName);
		log.debug("sendTokenSignal: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.WorkflowModule#setTokenNode(java.lang.String, long, java.lang.String)
	 */
	@Override
	public void setTokenNode(String token, long tokenId, String nodeName)
			throws RepositoryException {
		log.debug("setTokenNode(" + token + ", " + tokenId + ", " + nodeName + ")");
		WorkflowModule wm = ModuleManager.getWorkflowModule();
		wm.setTokenNode(token, tokenId, nodeName);
		log.debug("setTokenNode: void");
	}
}
