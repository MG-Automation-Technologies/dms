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

package es.git.openkm.ws.endpoint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.NoSuchGroupException;
import es.git.openkm.core.NoSuchPropertyException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.PropertyGroupModule;
import es.git.openkm.ws.util.StringArray;
import es.git.openkm.ws.util.StringArrayPair;
import es.git.openkm.ws.util.StringArrayPairArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMPropertyGroup"
 * @web.servlet-mapping url-pattern="/OKMPropertyGroup"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OKMPropertyGroup {
	private static Logger log = LoggerFactory.getLogger(OKMPropertyGroup.class);

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#addGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addGroup(" + token + ", " + docPath + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.addGroup(token, docPath, grpName);
		log.debug("addGroup: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#removeGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void removeGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException, 
			RepositoryException {
		log.debug("removeGroup(" + token + ", " + docPath + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.removeGroup(token, docPath, grpName);
		log.debug("removeGroup: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getGroups(java.lang.String, java.lang.String)
	 */
	public StringArray getGroups(String token, String docPath)
			throws PathNotFoundException, RepositoryException {
		log.debug("getGroups(" + token + ", " + docPath + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		StringArray sa = new StringArray();
		sa.setValue((String[]) cm.getGroups(token, docPath).toArray(new String[0]));
		log.debug("getGroups: "+sa);
		return sa;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getAllGroups(java.lang.String)
	 */
	public StringArray getAllGroups(String token) throws RepositoryException {
		log.debug("getAllGroups(" + token + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		StringArray sa = new StringArray();
		sa.setValue((String[]) cm.getAllGroups(token).toArray(new String[0]));
		log.debug("getAllGroups: "+sa);
		return sa;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getProperties(java.lang.String, java.lang.String, java.lang.String)
	 */
	public StringArrayPairArray getProperties(String token, String docPath, String grpName) 
			throws NoSuchGroupException, PathNotFoundException, RepositoryException {
		log.debug("getProperties(" + token + ", " + docPath + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		HashMap<String, String[]> hm = cm.getProperties(token, docPath, grpName);
		Set<String> keys = hm.keySet();
		StringArrayPair[] tmp = new StringArrayPair[keys.size()];
		int i=0;
		
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String key = it.next();
			StringArrayPair p = new StringArrayPair();
			p.setKey(key);
			StringArray sa = new StringArray();
			sa.setValue((String[]) hm.get(key));
			p.setValue(sa);
			tmp[i++] = p;
		}
		
		StringArrayPairArray uh = new StringArrayPairArray();
		uh.setValue(tmp);
		
		log.debug("getProperties: "+uh);
		return uh;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#setProperties(java.lang.String, java.lang.String, java.lang.String, java.util.HashMap)
	 */
	public void setProperties(String token, String docPath, String grpName, StringArrayPairArray properties)
			throws NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("setProperties(" + token + ", " + docPath + ", " + properties + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		HashMap<String, String[]> uh = new HashMap<String, String[]>();
		StringArrayPair[] spa = properties.getValue();
		
		for (int i=0; i<spa.length; i++) {
			String key = spa[i].getKey();
			StringArray value = spa[i].getValue();
			uh.put(key, value.getValue());
		}
		
		cm.setProperties(token, docPath, grpName, uh);
		log.debug("setProperties: void");
	}
}
