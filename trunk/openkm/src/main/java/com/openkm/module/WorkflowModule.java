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

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.openkm.bean.form.FormElement;
import com.openkm.bean.workflow.ProcessDefinition;
import com.openkm.bean.workflow.ProcessInstance;
import com.openkm.bean.workflow.TaskInstance;
import com.openkm.bean.workflow.Token;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;

public interface WorkflowModule {

	/**
	 * Get list of locked documents by user
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException
	 *             If there is any general repository problem or the query
	 *             fails.
	 */
	public void registerProcessDefinition(String token, ZipInputStream is) throws ParseException,
			RepositoryException;

	/**
	 * Delete a previously registered process definition
	 * 
	 * @param token
	 * @param processDefinitionId
	 * @throws RepositoryException
	 */
	public void deleteProcessDefinition(String token, long processDefinitionId) throws RepositoryException;

	/**
	 * @param token
	 * @param processDefinitionId
	 * @return
	 * @throws RepositoryException
	 */
	public ProcessDefinition getProcessDefinition(String token, long processDefinitionId)
			throws RepositoryException;

	/**
	 * @param token
	 * @param processDefinitionId
	 * @return
	 * @throws RepositoryException
	 */
	public byte[] getProcessDefinitionImage(String token, long processDefinitionId, String node)
			throws RepositoryException;

	/**
	 * @param token
	 * @param processDefinitionId
	 * @param name
	 *            Form name
	 * @return
	 * @throws RepositoryException
	 */
	public Map<String, List<FormElement>> getProcessDefinitionForms(String token, long processDefinitionId)
			throws ParseException, RepositoryException;

	/**
	 * @param token
	 * @param processDefinitionId
	 * @param variables
	 * @return
	 * @throws RepositoryException
	 */
	public ProcessInstance runProcessDefinition(String token, long processDefinitionId,
			Map<String, Object> variables) throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @throws RepositoryException
	 */
	public ProcessInstance sendProcessInstanceSignal(String token, long processInstanceId,
			String transitionName) throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @throws RepositoryException
	 */
	public void deleteProcessInstance(String token, long processInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param processDefinitionId
	 * @return
	 * @throws RepositoryException
	 */
	public List<ProcessInstance> findProcessInstances(String token, long processDefinitionId)
			throws RepositoryException;

	/**
	 * Get list of registered process definitions
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException
	 *             If there is any general repository problem or the query
	 *             fails.
	 */
	public List<ProcessDefinition> findAllProcessDefinitions(String token) throws RepositoryException;

	/**
	 * Get list of registered process definitions. Only last version for each
	 * process
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException
	 *             If there is any general repository problem or the query
	 *             fails.
	 */
	public List<ProcessDefinition> findLatestProcessDefinitions(String token) throws RepositoryException;

	/**
	 * Get list of registered process definitions versions
	 * 
	 * @param token
	 * @param name
	 * @return
	 * @throws RepositoryException
	 *             If there is any general repository problem or the query
	 *             fails.
	 */
	public List<ProcessDefinition> findAllProcessDefinitionVersions(String token, String name)
			throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @return
	 * @throws RepositoryException
	 */
	public ProcessInstance getProcessInstance(String token, long processInstanceId)
			throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @throws RepositoryException
	 */
	public void suspendProcessInstance(String token, long processInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @throws RepositoryException
	 */
	public void resumeProcessInstance(String token, long processInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @param name
	 * @param value
	 * @throws RepositoryException
	 */
	public void addProcessInstanceVariable(String token, long processInstanceId, String name, Object value)
			throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @param name
	 * @throws RepositoryException
	 */
	public void removeProcessInstanceVariable(String token, long processInstanceId, String name)
			throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public List<TaskInstance> findUserTaskInstances(String token) throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public List<TaskInstance> findPooledTaskInstances(String token) throws RepositoryException;

	/**
	 * @param token
	 * @param processInstanceId
	 * @return
	 * @throws RepositoryException
	 */
	public List<TaskInstance> findTaskInstances(String token, long processInstanceId)
			throws RepositoryException;

	/**
	 * @param token
	 * @param taskInstanceId
	 * @param transitionName
	 * @param values
	 * @throws RepositoryException
	 */
	public void setTaskInstanceValues(String token, long taskInstanceId, String transitionName,
			Map<String, Object> values) throws RepositoryException;

	/**
	 * @param token
	 * @param taskInstanceId
	 * @param message
	 * @throws RepositoryException
	 */
	public void addTaskInstanceComment(String token, long taskInstanceId, String message)
			throws RepositoryException;

	/**
	 * @param token
	 * @param taskInstanceId
	 * @return
	 * @throws RepositoryException
	 */
	public TaskInstance getTaskInstance(String token, long taskInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param taskInstanceId
	 * @param actorId
	 * @throws RepositoryException
	 */
	public void setTaskInstanceActorId(String token, long taskInstanceId, String actorId)
			throws RepositoryException;

	/**
	 * @param token
	 * @param taskInstanceId
	 * @param name
	 * @param value
	 * @throws RepositoryException
	 */
	public void addTaskInstanceVariable(String token, long taskInstanceId, String name, Object value)
			throws RepositoryException;

	/**
	 * @param token
	 * @param taskInstanceId
	 * @param name
	 * @throws RepositoryException
	 */
	public void removeTaskInstanceVariable(String token, long taskInstanceId, String name)
			throws RepositoryException;

	/**
	 * @param token
	 * @param id
	 * @throws RepositoryException
	 */
	public void startTaskInstance(String token, long taskInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param id
	 * @throws RepositoryException
	 */
	public void endTaskInstance(String token, long taskInstanceId, String transitionName)
			throws RepositoryException;

	/**
	 * @param token
	 * @param id
	 * @throws RepositoryException
	 */
	public void suspendTaskInstance(String token, long taskInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param id
	 * @throws RepositoryException
	 */
	public void resumeTaskInstance(String token, long taskInstanceId) throws RepositoryException;

	/**
	 * @param token
	 * @param tokenId
	 * @return
	 * @throws RepositoryException
	 */
	public Token getToken(String token, long tokenId) throws RepositoryException;

	/**
	 * @param token
	 * @param tokenId
	 * @param message
	 * @throws RepositoryException
	 */
	public void addTokenComment(String token, long tokenId, String message) throws RepositoryException;

	/**
	 * @param token
	 * @param tokenId
	 * @throws RepositoryException
	 */
	public void suspendToken(String token, long tokenId) throws RepositoryException;

	/**
	 * @param token
	 * @param tokenId
	 * @throws RepositoryException
	 */
	public void resumeToken(String token, long tokenId) throws RepositoryException;

	/**
	 * @param token
	 * @param tokenId
	 * @throws RepositoryException
	 */
	public Token sendTokenSignal(String token, long tokenId, String transitionName)
			throws RepositoryException;

	/**
	 * @param token
	 * @param tokenId
	 * @param nodeName
	 * @throws RepositoryException
	 */
	public void setTokenNode(String token, long tokenId, String nodeName) throws RepositoryException;
}
