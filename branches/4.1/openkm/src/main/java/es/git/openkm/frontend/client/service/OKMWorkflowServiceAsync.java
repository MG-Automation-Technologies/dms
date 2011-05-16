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

package es.git.openkm.frontend.client.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import es.git.openkm.frontend.client.bean.GWTFormElement;
import es.git.openkm.frontend.client.bean.GWTProcessDefinition;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;


/**
 * @author jllort
 *
 */
public interface OKMWorkflowServiceAsync {
	public void findLatestProcessDefinitions(AsyncCallback<List<GWTProcessDefinition>> callback);
	public void runProcessDefinition(String UUID, double id, Map<String,Object> variables, AsyncCallback<?> callback);
	public void findUserTaskInstances(AsyncCallback<List<GWTTaskInstance>> callback);
	public void getProcessDefinitionForms(double id, AsyncCallback<Map<String, Collection<GWTFormElement>>> callback);
	public void setTaskInstanceValues(double id, String transitionName, Map<String, Object> values, AsyncCallback<?> callback );
	public void addComment(double tokenId, String message, AsyncCallback<?> callback );
	public void findPooledTaskInstances(AsyncCallback<List<GWTTaskInstance>> callback);
	public void setTaskInstanceActorId(double id, AsyncCallback<?> callback );
}