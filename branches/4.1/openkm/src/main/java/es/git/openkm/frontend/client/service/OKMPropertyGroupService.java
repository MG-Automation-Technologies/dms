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

package es.git.openkm.frontend.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTMetaData;

/**
 * OKMPropertyGroupService
 * 
 * @author jllort
 *
 */
public interface OKMPropertyGroupService extends RemoteService {

	public List<String> getAllGroups() throws OKMException;
	
	public List<String> getAllGroups(String docPath) throws OKMException;
	
	public void addGroup(String docPath, String grpName) throws OKMException;
	
	public List<String> getGroups(String docPath) throws OKMException;

	public HashMap<String, String> getTranslations(String lang) throws OKMException;
	
	public Map<String, String[]> getProperties(String docPath, String grpName) throws OKMException;
	
	public Map<String, GWTMetaData> getMetaData(String grpName) throws OKMException;
	
	public void setProperties(String docPath, String grpName, HashMap<String, String[]> properties) throws OKMException;
	public void removeGroup( String docPath, String grpName) throws OKMException;
}