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

package com.openkm.module;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.openkm.bean.form.FormElement;
import com.openkm.bean.workflow.ProcessDefinition;
import com.openkm.bean.workflow.ProcessInstance;
import com.openkm.bean.workflow.TaskInstance;
import com.openkm.bean.workflow.Token;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.core.WorkflowException;

public interface WorkflowModule {

	/**
	 * Get list of locked documents by user
	 */
	public void registerProcessDefinition(InputStream is) throws ParseException, RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * Delete a previously registered process definition
	 */
	public void deleteProcessDefinition(long processDefinitionId) throws RepositoryException, 
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public ProcessDefinition getProcessDefinition(long processDefinitionId) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public byte[] getProcessDefinitionImage(long processDefinitionId, String node) throws RepositoryException,
		DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public Map<String, List<FormElement>> getProcessDefinitionForms(long processDefinitionId) throws 
			ParseException, RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public ProcessInstance runProcessDefinition(long processDefinitionId, String uuid, 
			List<FormElement> variables) throws RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public ProcessInstance sendProcessInstanceSignal(long processInstanceId, String transitionName) throws 
			RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void endProcessInstance(long processInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;
	
	/**
	 * 
	 */
	public void deleteProcessInstance(long processInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public List<ProcessInstance> findProcessInstances(long processDefinitionId) throws RepositoryException, 
			DatabaseException, WorkflowException;

	/**
	 * Get list of registered process definitions
	 */
	public List<ProcessDefinition> findAllProcessDefinitions() throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * Get list of registered process definitions. Only last version for each
	 * process
	 */
	public List<ProcessDefinition> findLatestProcessDefinitions() throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * Get list of registered process definitions versions
	 */
	public List<ProcessDefinition> findAllProcessDefinitionVersions(String name) throws RepositoryException, 
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public ProcessInstance getProcessInstance(long processInstanceId) throws RepositoryException, 
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void suspendProcessInstance(long processInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public void resumeProcessInstance(long processInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public void addProcessInstanceVariable(long processInstanceId, String name, Object value) throws
			RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void deleteProcessInstanceVariable(long processInstanceId, String name) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public List<TaskInstance> findUserTaskInstances() throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public List<TaskInstance> findPooledTaskInstances() throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public List<TaskInstance> findTaskInstances(long processInstanceId) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void setTaskInstanceValues(long taskInstanceId, String transitionName, List<FormElement> values)
			throws RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void addTaskInstanceComment(long taskInstanceId, String message) throws RepositoryException, 
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public TaskInstance getTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public void setTaskInstanceActorId(long taskInstanceId, String actorId) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void addTaskInstanceVariable(long taskInstanceId, String name, Object value) throws 
			RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void deleteTaskInstanceVariable(long taskInstanceId, String name) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void startTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;
	
	/**
	 * 
	 */
	public void endTaskInstance(long taskInstanceId, String transitionName) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void suspendTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public void resumeTaskInstance(long taskInstanceId) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public Token getToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void addTokenComment(long tokenId, String message) throws RepositoryException, DatabaseException,
			WorkflowException;

	/**
	 * 
	 */
	public void suspendToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void resumeToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public Token sendTokenSignal(long tokenId, String transitionName) throws RepositoryException,
			DatabaseException, WorkflowException;

	/**
	 * 
	 */
	public void setTokenNode(long tokenId, String nodeName) throws RepositoryException, DatabaseException,
			WorkflowException;
	
	/**
	 * 
	 */
	public void endToken(long tokenId) throws RepositoryException, DatabaseException, WorkflowException;
}
