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

package es.git.openkm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.ScriptingModule;

/**
 * @author pavila
 *
 */
public class OKMScripting implements ScriptingModule {
	private static Logger log = LoggerFactory.getLogger(OKMScripting.class);
	private static OKMScripting instance = new OKMScripting();

	private OKMScripting() {}
	
	public static OKMScripting getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.ScriptingModule#setScript(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setScript(String token, String nodePath, String code)
			throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("setScript("+token+")");
		ScriptingModule sm = ModuleManager.getScriptingModule();
		sm.setScript(token, nodePath, code);
		log.debug("setScript: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.ScriptingModule#removeScript(java.lang.String, java.lang.String)
	 */
	public void removeScript(String token, String nodePath)
			throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("removeScript("+token+", "+nodePath+")");
		ScriptingModule sm = ModuleManager.getScriptingModule();
		sm.removeScript(token, nodePath);
		log.debug("removeScript: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.ScriptingModule#getScript(java.lang.String, java.lang.String)
	 */
	public String getScript(String token, String nodePath)
			throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("getScript("+token+")");
		ScriptingModule sm = ModuleManager.getScriptingModule();
		String code = sm.getScript(token, nodePath);
		log.debug("getScript: "+code);
		return code;
	}
}
