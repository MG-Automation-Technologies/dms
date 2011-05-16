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

package es.git.openkm.backend.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTProcessDefinition;
import es.git.openkm.backend.client.bean.GWTProcessInstance;

/**
 * @author jllort
 *
 */
public interface OKMWorkflowService extends RemoteService {
	public List<GWTProcessDefinition> findLatestProcessDefinitions() throws OKMException;
	public void runProcessDefinition(String docPath, double id) throws OKMException;
	public List<GWTProcessDefinition> findAllProcessDefinitionVersions(String name) throws OKMException;
	public List<GWTProcessInstance> findProcessInstances(double id) throws OKMException;
	public void deleteProcessDefinition(double id) throws OKMException;
}