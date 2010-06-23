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

package com.openkm.servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMWorkflow;
import com.openkm.bean.form.Button;
import com.openkm.bean.form.CheckBox;
import com.openkm.bean.form.FormElement;
import com.openkm.bean.form.Input;
import com.openkm.bean.form.Option;
import com.openkm.bean.form.Select;
import com.openkm.bean.form.TextArea;
import com.openkm.bean.form.Validator;
import com.openkm.bean.workflow.ProcessInstance;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.core.WorkflowException;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.FormatUtil;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * RepositoryView servlet
 */
public class WorkflowServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WorkflowServlet.class);
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("processDefinitionDelete")) {
				processDefinitionDelete(session, request, response);
				processDefinitionList(session, request, response);
			} else if (action.equals("processDefinitionView")) {
				processDefinitionView(session, request, response);
			} else if (action.equals("processInstanceView")) {
				processInstanceView(session, request, response);
			} else if (action.equals("processInstanceDelete")) {
				processInstanceDelete(session, request, response);
				processDefinitionView(session, request, response);
			} else if (action.equals("processInstanceEnd")) {
				processInstanceEnd(session, request, response);
				processDefinitionView(session, request, response);
			} else if (action.equals("processInstanceResume")) {
				processInstanceResume(session, request, response);
				processDefinitionView(session, request, response);
			} else if (action.equals("processInstanceSuspend")) {
				processInstanceSuspend(session, request, response);
				processDefinitionView(session, request, response);
			} else if (action.equals("processInstanceVariableDelete")) {
				processInstanceVariableDelete(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("processInstanceVariableAdd")) {
				processInstanceVariableAdd(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("taskInstanceSetActor")) {
				taskInstanceSetActor(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("taskInstanceSuspend")) {
				taskInstanceSuspend(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("taskInstanceResume")) {
				taskInstanceResume(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("taskInstanceStart")) {
				taskInstanceStart(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("taskInstanceEnd")) {
				taskInstanceEnd(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("taskInstanceView")) {
				taskInstanceView(session, request, response);
			} else if (action.equals("tokenView")) {
				tokenView(session, request, response);
			} else if (action.equals("tokenEnd")) {
				tokenEnd(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("tokenSuspend")) {
				tokenSuspend(session, request, response);
				processInstanceView(session, request, response);
			} else if (action.equals("tokenAddComment")) {
				tokenAddComment(session, request, response);
				processInstanceView(session, request, response);
			} else {
				processDefinitionList(session, request, response);
			}
		} catch (javax.jcr.LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (WorkflowException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}

	/**
	 * List all process definitions
	 */
	private void processDefinitionList(Session session, HttpServletRequest request, HttpServletResponse response) throws 
			ServletException, IOException, com.openkm.core.RepositoryException, DatabaseException, WorkflowException {
		log.debug("listProcessDefinition({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("processDefinitions", OKMWorkflow.getInstance().findAllProcessDefinitions());
		sc.getRequestDispatcher("/admin/process_definition_list.jsp").forward(request, response);
		log.debug("listProcessDefinition: void");
	}
	
	/**
	 * Delete a process definition
	 */
	private void processDefinitionDelete(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("deleteProcessDefinition({}, {}, {})", new Object[] { session, request, response });
		long pdid = WebUtil.getLong(request, "pdid");
		OKMWorkflow.getInstance().deleteProcessDefinition(pdid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_DEFINITION_DELETE", null, null);
		log.debug("deleteProcessDefinition: void");
	}
	
	/**
	 * View process definition
	 */
	private void processDefinitionView(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, RepositoryException, DatabaseException, WorkflowException,
			ParseException {
		log.debug("viewProcessDefinition({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		long pdid = WebUtil.getLong(request, "pdid");
		Map<String, List<FormElement>> procDefForms = OKMWorkflow.getInstance().getProcessDefinitionForms(pdid);
		Map<String, List<Map<String, String>>> pdf = new HashMap<String, List<Map<String,String>>>();
		
		for (String key : procDefForms.keySet()) {
			List<Map<String, String>> value = new ArrayList<Map<String, String>>();
			
			for (FormElement fe : procDefForms.get(key)) {
				value.add(getMap(fe));
			}
			
			pdf.put(key, value);
		}
		
		sc.setAttribute("processDefinition", OKMWorkflow.getInstance().getProcessDefinition(pdid));
		sc.setAttribute("processInstances", OKMWorkflow.getInstance().findProcessInstances(pdid));
		sc.setAttribute("processDefinitionForms", pdf);
		sc.getRequestDispatcher("/admin/process_definition_view.jsp").forward(request, response);
		log.debug("viewProcessDefinition: void");
	}
	
	/**
	 * View process instance
	 */
	private void processInstanceView(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, RepositoryException, DatabaseException, WorkflowException,
			PrincipalAdapterException, AccessDeniedException {
		log.debug("processInstanceView({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		long piid = WebUtil.getLong(request, "piid");
		ProcessInstance pi = OKMWorkflow.getInstance().getProcessInstance(piid);
		Map<String, String> vars = new HashMap<String, String>();
		
		for (Entry<String, Object> entry : pi.getVariables().entrySet()) {
			vars.put(entry.getKey(), FormatUtil.formatObject(entry.getValue()));
			
			if (entry.getKey().equals(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_UUID)) {
				vars.put(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_PATH, 
						OKMDocument.getInstance().getPath(entry.getValue().toString()));
			} 
		}
		
		sc.setAttribute("variables", vars);
		sc.setAttribute("processInstance", pi);
		sc.setAttribute("taskInstances", OKMWorkflow.getInstance().findTaskInstances(piid));
		sc.setAttribute("users", OKMAuth.getInstance().getUsers());
		sc.getRequestDispatcher("/admin/process_instance_view.jsp").forward(request, response);
		log.debug("processInstanceView: void");
	}
	
	/**
	 * Delete process instance
	 */
	private void processInstanceDelete(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("processInstanceDelete({}, {}, {})", new Object[] { session, request, response });
		long piid = WebUtil.getLong(request, "piid");
		OKMWorkflow.getInstance().deleteProcessInstance(piid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_INSTANCE_DELETE", Long.toString(piid), null);
		log.debug("processInstanceDelete: void");
	}
	
	/**
	 * End process instance
	 */
	private void processInstanceEnd(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("processInstanceEnd({}, {}, {})", new Object[] { session, request, response });
		long piid = WebUtil.getLong(request, "piid");
		OKMWorkflow.getInstance().endProcessInstance(piid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_INSTANCE_END", Long.toString(piid), null);
		log.debug("processInstanceEnd: void");
	}
	
	/**
	 * Resume process instance
	 */
	private void processInstanceResume(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("processInstanceResume({}, {}, {})", new Object[] { session, request, response });
		long piid = WebUtil.getLong(request, "piid");
		OKMWorkflow.getInstance().resumeProcessInstance(piid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_INSTANCE_RESUME", Long.toString(piid), null);
		log.debug("processInstanceResume: void");
	}
	
	/**
	 * Suspend process instance
	 */
	private void processInstanceSuspend(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("processInstanceSuspend({}, {}, {})", new Object[] { session, request, response });
		long piid = WebUtil.getLong(request, "piid");
		OKMWorkflow.getInstance().suspendProcessInstance(piid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_INSTANCE_SUSPEND", Long.toString(piid), null);
		log.debug("processInstanceSuspend: void");
	}
	
	/**
	 * Delete process instance variable
	 */
	private void processInstanceVariableDelete(Session session, HttpServletRequest request,
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("processInstanceVariableDelete({}, {}, {})", new Object[] { session, request, response });
		long piid = WebUtil.getLong(request, "piid");
		String name = WebUtil.getString(request, "name");
		OKMWorkflow.getInstance().deleteProcessInstanceVariable(piid, name);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_INSTANCE_VARIABLE_DELETE", Long.toString(piid), null);
		log.debug("processInstanceVariableDelete: void");
	}

	/**
	 * Add process instance variable
	 */
	private void processInstanceVariableAdd(Session session, HttpServletRequest request,
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("processInstanceVariableAdd({}, {}, {})", new Object[] { session, request, response });
		long piid = WebUtil.getLong(request, "piid");
		String name = WebUtil.getString(request, "name");
		String value= WebUtil.getString(request, "value");
		OKMWorkflow.getInstance().addProcessInstanceVariable(piid, name, value);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_PROCESS_INSTANCE_VARIABLE_ADD", Long.toString(piid), 
				name + "=" + value);
		log.debug("processInstanceVariableAdd: void");
	}
	
	/**
	 * Set task instance actor
	 */
	private void taskInstanceSetActor(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("taskInstanceSetActor({}, {}, {})", new Object[] { session, request, response });
		long tiid = WebUtil.getLong(request, "tiid");
		String actor = WebUtil.getString(request, "actor");
		OKMWorkflow.getInstance().setTaskInstanceActorId(tiid, actor);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TASK_INSTANCE_SET_ACTOR", Long.toString(tiid), actor);
		log.debug("taskInstanceSetActor: void");
	}
	
	/**
	 * View task instance
	 */
	private void taskInstanceView(Session session, HttpServletRequest request, HttpServletResponse response) {
	}

	/**
	 * Start task instance
	 */
	private void taskInstanceStart(Session session, HttpServletRequest request, HttpServletResponse response)
			throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("taskInstanceStart({}, {}, {})", new Object[] { session, request, response });
		long tiid = WebUtil.getLong(request, "tiid");
		OKMWorkflow.getInstance().startTaskInstance(tiid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TASK_INSTANCE_START", Long.toString(tiid), null);
		log.debug("taskInstanceStart: void");
	}
	
	/**
	 * End task instance
	 */
	private void taskInstanceEnd(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("taskInstanceEnd({}, {}, {})", new Object[] { session, request, response });
		long tiid = WebUtil.getLong(request, "tiid");
		OKMWorkflow.getInstance().endTaskInstance(tiid, null);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TASK_INSTANCE_END", Long.toString(tiid), null);
		log.debug("taskInstanceEnd: void");
	}

	/**
	 * Suspend task instance
	 */
	private void taskInstanceSuspend(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("taskInstanceSuspend({}, {}, {})", new Object[] { session, request, response });
		long tiid = WebUtil.getLong(request, "tiid");
		OKMWorkflow.getInstance().suspendTaskInstance(tiid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TASK_INSTANCE_SUSPEND", Long.toString(tiid), null);
		log.debug("taskInstanceSuspend: void");
	}
	
	/**
	 * Resume task instance
	 */
	private void taskInstanceResume(Session session, HttpServletRequest request, 
			HttpServletResponse response) throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("taskInstanceResume({}, {}, {})", new Object[] { session, request, response });
		long tiid = WebUtil.getLong(request, "tiid");
		OKMWorkflow.getInstance().resumeTaskInstance(tiid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TASK_INSTANCE_RESUME", Long.toString(tiid), null);
		log.debug("taskInstanceResume: void");
	}
	
	/**
	 * Suspend token
	 */
	private void tokenSuspend(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws RepositoryException, DatabaseException, WorkflowException {
		log.debug("tokenSuspend({}, {}, {})", new Object[] { session, request, response });
		long tid = WebUtil.getLong(request, "tid");
		OKMWorkflow.getInstance().suspendToken(tid);

		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TOKEN_SUSPEND", Long.toString(tid), null);
		log.debug("tokenSuspend: void");
	}

	/**
	 * End token
	 */
	private void tokenEnd(Session session, HttpServletRequest request, HttpServletResponse response) throws 
			RepositoryException, DatabaseException, WorkflowException {
		log.debug("tokenEnd({}, {}, {})", new Object[] { session, request, response });
		long tid = WebUtil.getLong(request, "tid");
		OKMWorkflow.getInstance().endToken(tid);
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TOKEN_END", Long.toString(tid), null);
		log.debug("tokenEnd: void");
	}
	
	/**
	 * Add comment to token
	 */
	private void tokenAddComment(Session session, HttpServletRequest request,
			HttpServletResponse response) throws DatabaseException, WorkflowException, RepositoryException {
		log.debug("processInstanceAddComment({}, {}, {})", new Object[] { session, request, response });
		long tid = WebUtil.getLong(request, "tid");
		String message = WebUtil.getString(request, "message");
		
		if (!message.equals("")) {
			OKMWorkflow.getInstance().addTokenComment(tid, message);
		}
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TOKEN_ADD_COMMENT", Long.toString(tid), null);
		log.debug("processInstanceAddComment: void");
	}

	/**
	 * View token
	 */
	private void tokenView(Session session, HttpServletRequest request, HttpServletResponse response) throws
			ServletException, IOException, RepositoryException, DatabaseException, WorkflowException {
		log.debug("tokenView({}, {}, {})", new Object[] { session, request, response });
		long tid = WebUtil.getLong(request, "tid");
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_TOKEN_VIEW", Long.toString(tid), null);
		log.debug("tokenView: void");
	}

	/**
	 * Get form element type
	 */
	private Map<String, String> getMap(FormElement fe) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("label", fe.getLabel());
		ret.put("name", fe.getName());
		ret.put("width", fe.getWidth());
		ret.put("height", fe.getHeight());
		
		if (fe instanceof Input) {
			Input input = (Input) fe;
			ret.put("field", "Input");
			StringBuilder sb = new StringBuilder();
			sb.append("<i>Type:</i> ");
			sb.append(input.getType());
			drawValidators(sb, input.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof CheckBox) {
			CheckBox checkBox = new CheckBox();
			ret.put("field", "CheckBox");
			StringBuilder sb = new StringBuilder();
			drawValidators(sb, checkBox.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof TextArea) {
			TextArea textArea = (TextArea) fe;
			ret.put("field", "TextArea");
			StringBuilder sb = new StringBuilder();
			drawValidators(sb, textArea.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof Select) {
			Select select = (Select) fe;
			ret.put("field", "Select");
			StringBuilder sb = new StringBuilder();
			sb.append("<i>Type:</i> ");
			sb.append(select.getType());
			sb.append("<br/><i>Options:</i><ul>");
			for (Iterator<Option> itOpt = select.getOptions().iterator(); itOpt.hasNext(); ) {
				Option opt = itOpt.next();
				sb.append("<li><i>Label:</i> ");
				sb.append(opt.getLabel());
				sb.append(", <i>Value:</i> ");
				sb.append(opt.getValue());
				sb.append("</li>");
			}
			sb.append("</ul>");
			drawValidators(sb, select.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof Button) {
			Button button = (Button) fe;
			ret.put("field", "Button");
			StringBuilder sb = new StringBuilder();
			sb.append("<i>Type:</i> ");
			sb.append(button.getType());
			ret.put("others", sb.toString());
		}
		
		return ret;
	}
	
	/**
	 * Draw validation configuration
	 */
	private void drawValidators(StringBuilder sb, List<Validator> validators) {
		if (!validators.isEmpty()) {
			sb.append("<br/><i>Validators:</i><ul>");
			for (Iterator<Validator> it = validators.iterator(); it.hasNext(); ) {
				Validator v = it.next();
				sb.append("<li><i>Type:</i> ");
				sb.append(v.getType());
				sb.append(", <i>Parameter:</i> ");
				sb.append(v.getParameter());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
	}
}
