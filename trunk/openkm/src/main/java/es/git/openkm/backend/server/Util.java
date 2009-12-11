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

import es.git.openkm.backend.client.bean.GWTSessionInfo;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.MetaData;
import es.git.openkm.bean.SessionInfo;
import es.git.openkm.bean.StatsInfo;
import es.git.openkm.bean.report.admin.ReportUser;
import es.git.openkm.bean.workflow.ProcessDefinition;
import es.git.openkm.bean.workflow.ProcessInstance;
import es.git.openkm.backend.client.bean.GWTActivity;
import es.git.openkm.backend.client.bean.GWTMetaData;
import es.git.openkm.backend.client.bean.GWTFolder;
import es.git.openkm.backend.client.bean.GWTProcessInstance;
import es.git.openkm.backend.client.bean.GWTStatsInfo;
import es.git.openkm.backend.client.bean.GWTUser;
import es.git.openkm.dao.bean.Activity;
import es.git.openkm.dao.bean.User;
import es.git.openkm.backend.client.bean.GWTProcessDefinition;

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
	 * Copy the MetaData data to GWTMetaData
	 * 
	 * @param metaData The original MetaData
	 * @return The gwtMetaData object with data values form the original MetaData
	 */
	public static GWTMetaData copy(MetaData metaData) {
		GWTMetaData gwtMetaData = new GWTMetaData();
		
		gwtMetaData.setType(metaData.getType());
		gwtMetaData.setValues(metaData.getValues());
		
		return gwtMetaData;
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
		gWTUser.setPass(user.getPass());
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
		
//		gWTProcessInstance.setCurrentNodes(processInstance.getCurrentNodes());
		gWTProcessInstance.setEnded(processInstance.isEnded());
		gWTProcessInstance.setId(processInstance.getId());
		gWTProcessInstance.setSuspended(processInstance.isSuspended());
		gWTProcessInstance.setVariables(processInstance.getVariables());
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