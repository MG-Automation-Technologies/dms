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

package es.git.openkm.module.direct;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.jcr.Session;

import org.apache.commons.io.IOUtils;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.workflow.ProcessDefinition;
import es.git.openkm.bean.workflow.ProcessInstance;
import es.git.openkm.bean.workflow.TaskInstance;
import es.git.openkm.bean.workflow.Token;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.WorkflowModule;
import es.git.openkm.util.FormUtils;
import es.git.openkm.util.UserActivity;

public class DirectWorkflowModule implements WorkflowModule {
	private static Logger log = LoggerFactory.getLogger(DirectWorkflowModule.class);

	@Override
	public void registerProcessDefinition(String token, ZipInputStream zis)
			throws ParseException, RepositoryException {
		log.debug("registerProcessDefinition("+token+", "+zis+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		InputStream is = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.def.ProcessDefinition processDefinition = org.jbpm.graph.def.ProcessDefinition.parseParZipInputStream(zis);
									
			// Check xml form definition  
			FileDefinition fileDef = processDefinition.getFileDefinition();
			is = fileDef.getInputStream("forms.xml");
			FormUtils.parseWorkflowForms(is);
						
			// If it is ok, deploy it
			jbpmContext.deployProcessDefinition(processDefinition);
			
			// Activity log
			UserActivity.log(session, "REGISTER_PROCESS_DEFINITION", null, null);
		} catch (ParseException e) {
			throw e;
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			IOUtils.closeQuietly(is);
			jbpmContext.close();
		}
		
		log.debug("registerProcessDefinition: void");
	}

	@Override
	public void deleteProcessDefinition(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("deleteProcessDefinition("+token+", "+processDefinitionId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			graphSession.deleteProcessDefinition(processDefinitionId);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "DELETE_PROCESS_DEFINITION", ""+processDefinitionId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("deleteProcessDefinition: void");
	}

	@Override
	public ProcessDefinition getProcessDefinition(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("getProcessDefinition("+token+", "+processDefinitionId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ProcessDefinition vo = new ProcessDefinition();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.def.ProcessDefinition pd = graphSession.getProcessDefinition(processDefinitionId);
			vo = WorkflowUtils.copy(pd);
			
			// Activity log
			UserActivity.log(session, "GET_PROCESS_DEFINITION", ""+processDefinitionId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("getProcessDefinition: "+vo);
		return vo;
	}

	@Override
	public byte[] getProcessDefinitionImage(String token, long processDefinitionId, String node)
			throws RepositoryException {
		log.debug("getProcessDefinitionImage("+token+", "+processDefinitionId+", "+node+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		byte[] image = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.def.ProcessDefinition pd = graphSession.getProcessDefinition(processDefinitionId);
			FileDefinition fileDef = pd.getFileDefinition();
			//image = fileDef.getBytes("processimage.jpg");
			
			WorkflowUtils.DiagramInfo dInfo = WorkflowUtils.getDiagramInfo(fileDef.getInputStream("gpd.xml"));
			WorkflowUtils.DiagramNodeInfo dNodeInfo = dInfo.getNodeMap().get(node);
			BufferedImage img = ImageIO.read(fileDef.getInputStream("processimage.jpg"));
			
			if (dNodeInfo != null) {
				// Select node
				Graphics g = img.getGraphics();
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25F));
				g2d.setColor(Color.blue);
				g2d.fillRect(dNodeInfo.getX(), dNodeInfo.getY(), dNodeInfo.getWidth(), dNodeInfo.getHeight());
				g.dispose();
				
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", baos);
			image = baos.toByteArray();
			baos.flush();
			baos.close();

			// Activity log
			UserActivity.log(session, "GET_PROCESS_DEFINITION_IMAGE", ""+processDefinitionId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("getProcessDefinitionImage: "+image);
		return image;
	}

	@Override
	public Map<String, Collection<FormElement>> getProcessDefinitionForms(String token, long processDefinitionId)
			throws ParseException, RepositoryException {
		log.debug("getProcessDefinitionForms("+token+", "+processDefinitionId+")");
		//long begin = Calendar.getInstance().getTimeInMillis();
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		Map<String, Collection<FormElement>> forms = new HashMap<String, Collection<FormElement>>();
		InputStream is = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.def.ProcessDefinition pd = graphSession.getProcessDefinition(processDefinitionId);
			FileDefinition fileDef = pd.getFileDefinition();
			is = fileDef.getInputStream("forms.xml");
			
			if (is != null) {
				forms = FormUtils.parseWorkflowForms(is);
				is.close();
			}
			
			// Activity log
			UserActivity.log(session, "GET_PROCESS_DEFINITION_FORMS", processDefinitionId+"", null);
		} catch (ParseException e) {
			throw e;
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			IOUtils.closeQuietly(is);
			jbpmContext.close();
		}
		
		log.info("getProcessDefinitionForms: "+forms);
		//log.info("Time: "+(Calendar.getInstance().getTimeInMillis()-begin)+" ms");
		return forms;
	}

	@Override
	public ProcessInstance runProcessDefinition(String token, long processDefinitionId, Map<String, Object> variables)
			throws RepositoryException {
		log.debug("runProcessDefinition("+token+", "+processDefinitionId+", "+variables+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ProcessInstance vo = new ProcessInstance();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			jbpmContext.setActorId(session.getUserID());
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.def.ProcessDefinition pd = graphSession.getProcessDefinition(processDefinitionId);
			org.jbpm.graph.exe.ProcessInstance pi = pd.createProcessInstance(variables);
			org.jbpm.taskmgmt.exe.TaskMgmtInstance tmi = pi.getTaskMgmtInstance();
			
			// http://www.jboss.com/index.html?module=bb&op=viewtopic&t=100779
			if (tmi.getTaskMgmtDefinition().getStartTask() != null) {
				org.jbpm.taskmgmt.exe.TaskInstance ti = tmi.createStartTaskInstance();
				ti.start();
				//ti.end();
			} else {
				pi.getRootToken().signal();
			}
			 
			jbpmContext.save(pi);
			vo = WorkflowUtils.copy(pi);
			
			// Activity log
			UserActivity.log(session, "RUN_PROCESS_DEFINITION", ""+processDefinitionId, variables.toString());
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("runProcessDefinition: "+vo);
		return vo;
	}

	@Override
	public ProcessInstance sendProcessInstanceSignal(String token, long processInstanceId, String transitionName)
			throws RepositoryException {
		log.debug("sendProcessInstanceSignal("+token+", "+processInstanceId+", "+transitionName+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ProcessInstance vo = new ProcessInstance();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.exe.ProcessInstance pi = graphSession.getProcessInstance(processInstanceId);
			org.jbpm.graph.exe.Token t = pi.getRootToken();
			
			if (transitionName != null) {
				t.signal(transitionName);
			} else {
				t.signal();
			}

			jbpmContext.getSession().flush();
			vo = WorkflowUtils.copy(pi);
			
			// Activity log
			UserActivity.log(session, "SEND_PROCESS_INSTANCE_SIGNAL", ""+processInstanceId, transitionName);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("sendProcessInstanceSignal: "+vo);
		return vo;
	}

	@Override
	public void deleteProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("deleteProcessInstance("+token+", "+processInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			graphSession.deleteProcessInstance(processInstanceId);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "DELETE_PROCESS_INSTANCE", ""+processInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("deleteProcessInstance: void");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ProcessInstance> findProcessInstances(String token, long processDefinitionId)
			throws RepositoryException {
		log.debug("findProcessInstances("+token+", "+processDefinitionId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ArrayList<ProcessInstance> al = new ArrayList<ProcessInstance>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			
			for (Iterator it = graphSession.findProcessInstances(processDefinitionId).iterator(); it.hasNext(); ) {
				al.add(WorkflowUtils.copy((org.jbpm.graph.exe.ProcessInstance) it.next()));
			}
			
			// Activity log
			UserActivity.log(session, "FIND_PROCESS_INSTANCES", ""+processDefinitionId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("findProcessInstances: "+al);
		return al;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ProcessDefinition> findAllProcessDefinitions(String token)
			throws RepositoryException {
		log.debug("findAllProcessDefinitions("+token+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ArrayList<ProcessDefinition> al = new ArrayList<ProcessDefinition>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			
			for (Iterator it = graphSession.findAllProcessDefinitions().iterator(); it.hasNext(); ){
				al.add(WorkflowUtils.copy((org.jbpm.graph.def.ProcessDefinition) it.next()));
			}
			
			// Activity log
			UserActivity.log(session, "FIND_ALL_PROCESS_DEFINITIONS", null, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("findAllProcessDefinitions: "+al);
		return al;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ProcessDefinition> findLatestProcessDefinitions(String token)
			throws RepositoryException {
		log.debug("findLatestProcessDefinitions("+token+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ArrayList<ProcessDefinition> al = new ArrayList<ProcessDefinition>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			
			for (Iterator it = graphSession.findLatestProcessDefinitions().iterator(); it.hasNext(); ) {
				al.add(WorkflowUtils.copy((org.jbpm.graph.def.ProcessDefinition) it.next()));
			}
			
			// Activity log
			UserActivity.log(session, "FIND_LATEST_PROCESS_DEFINITIONS", null, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("findLatestProcessDefinitions: "+al);
		return al;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ProcessDefinition> findAllProcessDefinitionVersions(String token, String name)
			throws RepositoryException {
		log.debug("findAllProcessDefinitionVersions("+token+", "+name+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ArrayList<ProcessDefinition> al = new ArrayList<ProcessDefinition>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			
			for (Iterator it = graphSession.findAllProcessDefinitionVersions(name).iterator(); it.hasNext(); ){
				al.add(WorkflowUtils.copy((org.jbpm.graph.def.ProcessDefinition) it.next()));
			}
			
			// Activity log
			UserActivity.log(session, "FIND_ALL_PROCESS_DEFINITION_VERSIONS", name, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("findAllProcessDefinitionVersions: "+al);
		return al;
	}

	@Override
	public ProcessInstance getProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("getProcessInstance("+token+", "+processInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ProcessInstance vo = new ProcessInstance();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.exe.ProcessInstance pi = graphSession.getProcessInstance(processInstanceId);
			vo = WorkflowUtils.copy(pi);
			
			// Activity log
			UserActivity.log(session, "GET_PROCESS_INSTANCE", ""+processInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("getProcessInstance: "+vo);
		return vo;
	}

	@Override
	public void suspendProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.info("suspendProcessInstance("+token+", "+processInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.ProcessInstance pi = jbpmContext.getProcessInstance(processInstanceId);
			pi.suspend();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "SUSPEND_PROCESS_INSTANCE", ""+processInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("suspendProcessInstance: void");
	}

	@Override
	public void resumeProcessInstance(String token, long processInstanceId)
			throws RepositoryException {
		log.info("resumeProcessInstance("+token+", "+processInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.ProcessInstance pi = jbpmContext.getProcessInstance(processInstanceId);
			pi.resume();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "RESUME_PROCESS_INSTANCE", ""+processInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("resumeProcessInstance: void");
	}

	@Override
	public void addProcessInstanceVariable(String token, long processInstanceId, String name,
			Object value) throws RepositoryException {
		log.info("addProcessInstanceVariable("+token+", "+processInstanceId+", "+name+", "+value+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.ProcessInstance pi = jbpmContext.getProcessInstance(processInstanceId);
			pi.getContextInstance().setVariable(name, value);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "ADD_PROCESS_INSTANCE_VARIABLE", ""+processInstanceId, name+", "+value);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("addProcessInstanceVariable: void");
	}

	@Override
	public void removeProcessInstanceVariable(String token, long processInstanceId, String name)
			throws RepositoryException {
		log.info("removeProcessInstanceVariable("+token+", "+processInstanceId+", "+name+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.ProcessInstance pi = jbpmContext.getProcessInstance(processInstanceId);
			pi.getContextInstance().deleteVariable(name);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "REMOVE_PROCESS_INSTANCE_VARIABLE", ""+processInstanceId, name);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("removeProcessInstanceVariable: void");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<TaskInstance> findUserTaskInstances(String token)
			throws RepositoryException {
		log.debug("findUserTaskInstances("+token+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ArrayList<TaskInstance> al = new ArrayList<TaskInstance>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			
			for (Iterator it = taskMgmtSession.findTaskInstances(session.getUserID()).iterator(); it.hasNext(); ) {
				org.jbpm.taskmgmt.exe.TaskInstance ti = (org.jbpm.taskmgmt.exe.TaskInstance) it.next();
				al.add(WorkflowUtils.copy(ti));
			}
			
			// Sort
			Collections.sort(al);
			
			// Activity log
			UserActivity.log(session, "FIND_USER_TASK_INSTANCES", null, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("findUserTaskInstances: "+al);
		return al;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<TaskInstance> findTaskInstances(String token, long processInstanceId)
			throws RepositoryException {
		log.debug("findTaskInstances("+token+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ArrayList<TaskInstance> al = new ArrayList<TaskInstance>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			GraphSession graphSession = jbpmContext.getGraphSession();
			org.jbpm.graph.exe.ProcessInstance pi = graphSession.getProcessInstance(processInstanceId);
			TaskMgmtInstance taskMgmtInstance = pi.getTaskMgmtInstance();
			
			for (Iterator it = taskMgmtInstance.getTaskInstances().iterator(); it.hasNext(); ) {
				org.jbpm.taskmgmt.exe.TaskInstance ti = (org.jbpm.taskmgmt.exe.TaskInstance) it.next();
				al.add(WorkflowUtils.copy(ti));
			}
			
			// Sort
			Collections.sort(al);
			
			// Activity log
			UserActivity.log(session, "FIND_TASK_INSTANCES", ""+processInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("findTaskInstances: "+al);
		return al;
	}

	@Override
	public void setTaskInstanceValues(String token, long taskInstanceId, String transitionName, 
			Map<String, Object> values) throws RepositoryException {
		log.info("setTaskInstanceValues("+token+", "+taskInstanceId+", "+transitionName+", "+values+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			ti.setVariables(values);
			
			if (transitionName != null) {
				ti.end(transitionName);
			} else {
				ti.end();
			}
			
			// Activity log
			UserActivity.log(session, "SET_TASK_INSTANCE_VALUES", ""+taskInstanceId, transitionName);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("setTaskInstanceValues: void");
	}

	@Override
	public void addTaskInstanceComment(String token, long taskInstanceId, String message)
			throws RepositoryException {
		log.info("addTaskInstanceComment("+token+", "+taskInstanceId+", "+message+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			ti.addComment(new org.jbpm.graph.exe.Comment(session.getUserID(), message));
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "ADD_TASK_INSTANCE_COMMENT", ""+taskInstanceId, message);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("addTaskInstanceComment: void");
	}

	@Override
	public TaskInstance getTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.info("getTaskInstance("+token+", "+taskInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		TaskInstance vo = new TaskInstance();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			vo = WorkflowUtils.copy(ti);
						
			// Activity log
			UserActivity.log(session, "GET_TASK_INSTANCE", ""+taskInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("getTaskInstance: "+vo);
		return vo;
	}

	@Override
	public void setTaskInstanceActorId(String token, long taskInstanceId,
			String actorId) throws RepositoryException {
		log.debug("setTaskInstanceActorId("+token+", "+taskInstanceId+", "+actorId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.taskmgmt.exe.TaskInstance ti = jbpmContext.getTaskInstance(taskInstanceId);
			ti.setActorId(actorId);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "SET_TASK_INSTANCE_ACTOR_ID", ""+taskInstanceId, actorId);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("setTaskInstanceActorId: void");
	}

	@Override
	// TODO Esto creo que sobra pq no se puede hacer
	public void addTaskInstanceVariable(String token, long taskInstanceId,
			String name, Object value) throws RepositoryException {
		log.debug("addTaskInstanceVariable("+token+", "+taskInstanceId+", "+name+", "+value+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.taskmgmt.exe.TaskInstance ti = jbpmContext.getTaskInstance(taskInstanceId);
			ti.setVariable(name, value);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "ADD_TASK_INSTANCE_VARIABLE", ""+taskInstanceId, name+", "+value);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("addTaskInstanceVariable: void");
	}

	@Override
	public void removeTaskInstanceVariable(String token, long taskInstanceId,
			String name) throws RepositoryException {
		log.info("removeTaskInstanceVariable("+token+", "+taskInstanceId+", "+name+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.taskmgmt.exe.TaskInstance ti = jbpmContext.getTaskInstance(taskInstanceId);
			ti.deleteVariable(name);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "REMOVE_TASK_INSTANCE_VARIABLE", ""+taskInstanceId, name);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("removeTaskInstanceVariable: void");
	}

	@Override
	public void startTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.info("startTaskInstance("+token+", "+taskInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			ti.start();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "START_TASK_INSTANCE", ""+taskInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("startTaskInstance: void");
	}

	@Override
	public void endTaskInstance(String token, long taskInstanceId,
			String transitionName) throws RepositoryException {
		log.info("endTaskInstance("+token+", "+taskInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			
			if (transitionName != null) {
				ti.end(transitionName);
			} else {
				ti.end();
			}
			
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "END_TASK_INSTANCE", ""+taskInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("endTaskInstance: void");
	}

	@Override
	public void suspendTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.info("suspendTaskInstance("+token+", "+taskInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			ti.suspend();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "SUSPEND_TASK_INSTANCE", ""+taskInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("suspendTaskInstance: void");
	}

	@Override
	public void resumeTaskInstance(String token, long taskInstanceId)
			throws RepositoryException {
		log.info("resumeTaskInstance("+token+", "+taskInstanceId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			org.jbpm.taskmgmt.exe.TaskInstance ti = taskMgmtSession.getTaskInstance(taskInstanceId);
			ti.resume();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "RESUME_TASK_INSTANCE", ""+taskInstanceId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("resumeTaskInstance: void");
	}

	@Override
	public Token getToken(String token, long tokenId)
			throws RepositoryException {
		log.debug("getToken("+token+", "+tokenId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		Token vo = new Token();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.Token t = jbpmContext.getToken(tokenId);
			vo = WorkflowUtils.copy(t);
			vo.setProcessInstance(WorkflowUtils.copy(t.getProcessInstance())); // Avoid recursion
						
			// Activity log
			UserActivity.log(session, "GET_TOKEN", ""+tokenId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("getToken: "+vo);
		return vo;
	}

	@Override
	public void addTokenComment(String token, long tokenId, String message)
			throws RepositoryException {
		log.info("addTokenComment("+token+", "+tokenId+", "+message+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.Token t = jbpmContext.getToken(tokenId);
			t.addComment(new org.jbpm.graph.exe.Comment(session.getUserID(), message));
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "ADD_TOKEN_COMMENT", ""+tokenId, message);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("addTokenComment: void");
	}

	@Override
	public void suspendToken(String token, long tokenId) throws RepositoryException {
		log.info("suspendToken("+token+", "+tokenId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.Token t = jbpmContext.getToken(tokenId);
			t.suspend();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "SUSPEND_TOKEN", ""+tokenId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("suspendToken: void");
	}

	@Override
	public void resumeToken(String token, long tokenId) throws RepositoryException {
		log.info("resumeToken("+token+", "+tokenId+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
				
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.Token t = jbpmContext.getToken(tokenId);
			t.resume();
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "RESUME_TOKEN", ""+tokenId, null);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("resumeToken: void");
	}

	@Override
	public Token sendTokenSignal(String token, long tokenId,
			String transitionName) throws RepositoryException {
		log.debug("sendTokenSignal("+token+", "+tokenId+", "+transitionName+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		Token vo = new Token();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.Token t = jbpmContext.getToken(tokenId);

			if (transitionName != null) {
				t.signal(transitionName);
			} else {
				t.signal();
			}

			jbpmContext.getSession().flush();
			vo = WorkflowUtils.copy(t);
			vo.setProcessInstance(WorkflowUtils.copy(t.getProcessInstance())); // Avoid recursion
			
			// Activity log
			UserActivity.log(session, "SEND_TOKEN_SIGNAL", ""+tokenId, transitionName);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("sendTokenSignal: "+vo);
		return vo;
	}

	@Override
	public void setTokenNode(String token, long tokenId, String nodeName)
			throws RepositoryException {
		log.debug("setTokenNode("+token+", "+tokenId+", "+nodeName+")");
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			org.jbpm.graph.exe.Token t = jbpmContext.getToken(tokenId);
			org.jbpm.graph.def.Node node = t.getProcessInstance().getProcessDefinition().getNode(nodeName);
			t.setNode(node);
			jbpmContext.getSession().flush();
			
			// Activity log
			UserActivity.log(session, "SEND_TOKEN_NODE", ""+tokenId, nodeName);
		} catch (Exception e) {
			throw new RepositoryException(e);
		} finally {
			jbpmContext.close();
		}
		
		log.debug("setTokenNode: void");
	}
}
