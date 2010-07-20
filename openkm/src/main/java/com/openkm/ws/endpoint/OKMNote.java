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

package com.openkm.ws.endpoint;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Note;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.ModuleManager;
import com.openkm.module.NoteModule;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMNote" 
 * @web.servlet-mapping url-pattern="/OKMNote"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMNote {
	private static Logger log = LoggerFactory.getLogger(OKMNote.class);
	
	@WebMethod
	public void add(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String docPath,
			@WebParam(name = "text") String text) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("add({}, {}, {})", new Object[] {  token, docPath, text });
		NoteModule nm = ModuleManager.getNoteModule();
		nm.add(token, docPath, text);
		log.debug("addNote: void");
	}
	
	@WebMethod
	public Note get(@WebParam(name = "token") String token,
			@WebParam(name = "notePath") String notePath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("get({}, {})", token, notePath);
		NoteModule nm = ModuleManager.getNoteModule();
		Note ret = nm.get(token, notePath);
		log.debug("get: {}", ret);
		return ret;
	}

	@WebMethod
	public void remove(@WebParam(name = "token") String token,
			@WebParam(name = "notePath") String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("remove({}, {})", token, notePath);
		NoteModule nm = ModuleManager.getNoteModule();
		nm.remove(token, notePath);
		log.debug("remove: void");
	}

	@WebMethod
	public void set(@WebParam(name = "token") String token,
			@WebParam(name = "notePath") String notePath,
			@WebParam(name = "text") String text) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("set({}, {}, {})", new Object[] { token, notePath, text });
		NoteModule nm = ModuleManager.getNoteModule();
		nm.set(token, notePath, text);
		log.debug("set: void");
	}
	
	@WebMethod
	public Note[] list(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String notePath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("list({}, {})", token, notePath);
		NoteModule nm = ModuleManager.getNoteModule();
		List<Note> col = nm.list(token, notePath);
		Note[] result = (Note[]) col.toArray(new Note[col.size()]);
		log.debug("list: {}", result);
		return result;
	}
}
