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

package es.git.openkm.backend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import es.git.openkm.backend.client.bean.GWTActivity;
import es.git.openkm.backend.client.bean.GWTButton;
import es.git.openkm.backend.client.bean.GWTFolder;
import es.git.openkm.backend.client.bean.GWTFormElement;
import es.git.openkm.backend.client.bean.GWTInput;
import es.git.openkm.backend.client.bean.GWTOption;
import es.git.openkm.backend.client.bean.GWTProcessDefinition;
import es.git.openkm.backend.client.bean.GWTProcessInstance;
import es.git.openkm.backend.client.bean.GWTPropertyGroup;
import es.git.openkm.backend.client.bean.GWTSelect;
import es.git.openkm.backend.client.bean.GWTSessionInfo;
import es.git.openkm.backend.client.bean.GWTStatsInfo;
import es.git.openkm.backend.client.bean.GWTTextArea;
import es.git.openkm.backend.client.bean.GWTUser;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.workflow.ProcessDefinition;
import es.git.openkm.bean.workflow.ProcessInstance;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.SessionInfo;
import es.git.openkm.bean.StatsInfo;
import es.git.openkm.bean.form.Button;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.bean.form.Input;
import es.git.openkm.bean.form.Option;
import es.git.openkm.bean.form.Select;
import es.git.openkm.bean.form.TextArea;
import es.git.openkm.bean.report.admin.ReportUser;
import es.git.openkm.dao.bean.Activity;
import es.git.openkm.dao.bean.User;

public class Util {
	
	/**
	 * Copy data from Sesssion info object to GWTSessionInfo
	 * @param sessionInfo
	 * 
	 * @return The GWTSessionInfo object
	 */
	public static GWTSessionInfo copy(SessionInfo sessionInfo, String token) {
		GWTSessionInfo gWTSessionInfo = new GWTSessionInfo();
		
		gWTSessionInfo.setAccess(sessionInfo.getAccess().getTime());
		gWTSessionInfo.setCreation(sessionInfo.getCreation().getTime());
		gWTSessionInfo.setUserID(sessionInfo.getSession().getUserID());
		gWTSessionInfo.setToken(token);
		
		return gWTSessionInfo;
	}
	
	/**
	 * Copy the PropertyGroup object to GWTPropertyGroup
	 * 
	 * @param pGroup The original PropertyGroup
	 * @return The gwtPRopertyGroup object with data values from original PropertyGroup
	 */
	public static GWTPropertyGroup copy(PropertyGroup pGroup) {
		GWTPropertyGroup gWTPropertyGroup = new GWTPropertyGroup();
		
		gWTPropertyGroup.setLabel(pGroup.getLabel());
		gWTPropertyGroup.setName(pGroup.getName());
		
		return gWTPropertyGroup;
	}
	
	/**
	 * Copy the FormElement data to GWTFormElement
	 * 
	 * @param formElement The original FormElement
	 * @return The gwtFormElement object with data values form the original MetaData
	 */
	public static GWTFormElement copy(FormElement formElement) {
		if (formElement instanceof Button) {
			GWTButton gWTButton = new GWTButton();
			gWTButton.setLabel(formElement.getLabel());
			gWTButton.setValue(formElement.getValue());
			gWTButton.setWidth(formElement.getWidth());
			gWTButton.setHeight(formElement.getHeight());
			gWTButton.setName(((Button) formElement).getName());
			gWTButton.setType(((Button) formElement).getType());
			return gWTButton;
		} else if (formElement instanceof Input) {
			GWTInput gWTInput = new GWTInput();
			gWTInput.setLabel(formElement.getLabel());
			gWTInput.setValue(formElement.getValue());
			gWTInput.setWidth(formElement.getWidth());
			gWTInput.setHeight(formElement.getHeight());
			gWTInput.setName(((Input) formElement).getName());
			gWTInput.setType(((Input) formElement).getType());
			return gWTInput;
		} else if (formElement instanceof Select) {
			GWTSelect gWTselect = new GWTSelect();
			gWTselect.setLabel(formElement.getLabel());
			gWTselect.setValue(formElement.getValue());
			gWTselect.setWidth(formElement.getWidth());
			gWTselect.setHeight(formElement.getHeight());
			gWTselect.setName(((Select) formElement).getName());
			gWTselect.setType(((Select) formElement).getType());
			Collection<GWTOption> options = new ArrayList<GWTOption>();
			for (Iterator<Option> it = ((Select) formElement).getOptions().iterator(); it.hasNext();) {
				options.add(copy(it.next()));
			}
			gWTselect.setOptions(options);
			return gWTselect;
		} else if (formElement instanceof TextArea) {
			GWTTextArea gWTTextArea= new GWTTextArea();
			gWTTextArea.setLabel(formElement.getLabel());
			gWTTextArea.setValue(formElement.getValue());
			gWTTextArea.setWidth(formElement.getWidth());
			gWTTextArea.setHeight(formElement.getHeight());
			gWTTextArea.setName(((TextArea) formElement).getName());
			return gWTTextArea;
		} else {
			return new GWTFormElement();
		}
	}
	
	/**
	 * Copy to Option data to  GWTOption
	 * @param Option the original data
	 * @return The GWTOption object with data values from original Option
	 */
	public static GWTOption copy(Option option) {
		GWTOption gWTOption = new GWTOption();
		gWTOption.setName(option.getName());
		gWTOption.setValue(option.getValue());
		return gWTOption;
	}
	
	/**
	 * Copy the Folder data to GWTFolder data.
	 * 
	 * @param doc The original Folder object.
	 * @return A GWTFolder object with the data from 
	 * the original Document.
	 */
	public static GWTFolder copy(Folder fld) {
		GWTFolder gWTFolder = new GWTFolder();
		
		gWTFolder.setPath(fld.getPath());
		gWTFolder.setParentPath(Util.getParent(fld.getPath()));
		gWTFolder.setName(Util.getName(fld.getPath()));
		gWTFolder.setHasChilds(fld.getHasChilds());
		gWTFolder.setCreated(fld.getCreated().getTime());
		gWTFolder.setPermissions(fld.getPermissions());
		gWTFolder.setAuthor(fld.getAuthor());
		gWTFolder.setSubscribed(fld.isSubscribed());
		gWTFolder.setSubscriptors(fld.getSubscriptors());

		return gWTFolder;
	}
	
	/**
	 * Copy the Users data to GWTUser data.
	 * 
	 * @param user The original User object.
	 * @return A GWTUser object with the data from 
	 * the original user.
	 */
	public static GWTUser copy(User user) {
		GWTUser gWTUser = new GWTUser();
		
		gWTUser.setEmail(user.getEmail());
		gWTUser.setId(user.getId());
		gWTUser.setPass(""); // We must not copy password
		gWTUser.setRoles(user.getRoles());
		gWTUser.setActive(user.isActive());

		return gWTUser;
	}
	
	/**
	 * Copy the GWTUser data to Usersdata.
	 * 
	 * @param user The original User object.
	 * @return A User object with the data from 
	 * the original GWTUser.
	 */
	public static User copy(GWTUser gWTUser) {
		User user = new User();
		
		user.setEmail(gWTUser.getEmail());
		user.setId(gWTUser.getId());
		user.setPass(gWTUser.getPass());
		user.setRoles(gWTUser.getRoles());
		user.setActive(gWTUser.isActive());

		return user;
	}
	
	/**
	 * Copy the Users data to GWTUser data.
	 * 
	 * @param user The original User object.
	 * @return A GWTUser object with the data from 
	 * the original user.
	 */
	public static GWTStatsInfo copy(StatsInfo statsInfo) {
		GWTStatsInfo gWTStatsInfo = new GWTStatsInfo();
		
		gWTStatsInfo.setPercents(statsInfo.getPercents());
		gWTStatsInfo.setSizes(statsInfo.getSizes());

		return gWTStatsInfo;
	}
	
	/**
	 * Copy the Activity data to GWTActivity data.
	 * 
	 * @param activity The original User object.
	 * @return A GWTActivity object with the data from 
	 * the original activity.
	 */
	public static GWTActivity copy(Activity activity) {
		GWTActivity gWTActivity = new GWTActivity();
		
		gWTActivity.setActAction(activity.getActAction());
		gWTActivity.setActDate(activity.getActDate().getTime());
		gWTActivity.setActParams(activity.getActParams());
		gWTActivity.setActToken(activity.getActToken());
		gWTActivity.setActUser(activity.getActUser());

		return gWTActivity;
	}
	
	/**
	 * Get parent item path from path.
	 * 
	 * @param path The complete item path.
	 * @return The parent item path.
	 */
	public static String getParent(String path) {
		int lastSlash = path.lastIndexOf('/');
		String ret = (lastSlash > 0)?path.substring(0, lastSlash):"";
		return ret;	
	}

	/**
	 * Get item name from path.
	 * 
	 * @param path The complete item path.
	 * @return The name of the item.
	 */
	public static String getName(String path) {
		String ret = path.substring(path.lastIndexOf('/')+1);
		return ret;
	}
	
	/**
	 * Copy to ProcessDefinition data to  GWTProcessDefinition
	 * @param ProcessDefinition the original data
	 * @return The GWTProcessDefinition object with data values from original ProcessDefinition
	 */
	public static GWTProcessDefinition copy(ProcessDefinition processDefinition) {
		GWTProcessDefinition gWTProcessDefinition = new GWTProcessDefinition();
		
		gWTProcessDefinition.setId(processDefinition.getId());
		gWTProcessDefinition.setName(processDefinition.getName());
		gWTProcessDefinition.setVersion(processDefinition.getVersion());
		
		return gWTProcessDefinition;
	}
	
	/**
	 * Copy to ProcessInstance data to GWTProcessInstance
	 * @param processInstance The original data
	 * @return The GWTProcessInstance object with data values from original ProcessInstance
	 */
	public static GWTProcessInstance copy(ProcessInstance processInstance) {
		GWTProcessInstance gWTProcessInstance = new GWTProcessInstance();
		
		gWTProcessInstance.setEnded(processInstance.isEnded());
		gWTProcessInstance.setId(processInstance.getId());
		gWTProcessInstance.setSuspended(processInstance.isSuspended());
		//gWTProcessInstance.setVariables(processInstance.getVariables());
		gWTProcessInstance.setVersion(processInstance.getVersion());
		
		return gWTProcessInstance;
	}
	
	/**
	 * Copy the Users data to GWTUser data.
	 * 
	 * @param user The original User object.
	 * @return A GWTUser object with the data from 
	 * the original user.
	 */
	public static ReportUser reportCopy(User user) {
		ReportUser reportUser = new ReportUser();
		
		reportUser.setEmail(user.getEmail());
		reportUser.setId(user.getId());
		//reportUser.setRoles(user.getRoles());
		reportUser.setActive(user.isActive());

		return reportUser;
	}
}