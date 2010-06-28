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

package com.openkm.api;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Mail;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.MailModule;
import com.openkm.module.ModuleManager;

/**
 * @author pavila
 *
 */
public class OKMMail implements MailModule {
	private static Logger log = LoggerFactory.getLogger(OKMMail.class);
	private static OKMMail instance = new OKMMail();
	
	private OKMMail() {}
	
	public static OKMMail getInstance() {
		return instance;
	}
	
	@Override
	public Mail create(Mail mail) throws PathNotFoundException, ItemExistsException, VirusDetectedException,
			AccessDeniedException, RepositoryException, DatabaseException, UserQuotaExceededException {
		log.debug("create({})", mail);
		MailModule mm = ModuleManager.getMailModule();
		Mail newMail = mm.create(mail);
		log.debug("create: {}", newMail);
		return newMail;
	}
	
	@Override
	public Mail getProperties(String mailPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("getProperties({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		Mail mail = mm.getProperties(mailPath);
		log.debug("getProperties: {}", mail);
		return mail;
	}
	
	@Override
	public void delete(String mailPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("delete({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		mm.delete(mailPath);
		log.debug("delete: void");
	}

	@Override
	public void purge(String mailPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("purge({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		mm.purge(mailPath);
		log.debug("purge: void");
	}
	
	@Override
	public Mail rename(String mailPath, String newName) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("rename({}, {})", mailPath,  newName);
		MailModule mm = ModuleManager.getMailModule();
		Mail renamedMail = mm.rename(mailPath, newName);
		log.debug("rename: {}", renamedMail);
		return renamedMail;
	}
	
	@Override
	public void move(String mailPath, String dstPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {})", mailPath, dstPath);
		MailModule mm = ModuleManager.getMailModule();
		mm.move(mailPath, dstPath);
		log.debug("move: void");
	}

	@Override
	public void copy(String mailPath, String dstPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException, 
			UserQuotaExceededException {
		log.debug("copy({}, {})", mailPath, dstPath);
		MailModule mm = ModuleManager.getMailModule();
		mm.copy(mailPath, dstPath);
		log.debug("copy: void");
	}
	
	@Override
	public List<Mail> getChilds(String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("getChilds({})", fldPath);
		MailModule mm = ModuleManager.getMailModule();
		List<Mail> childs = mm.getChilds(fldPath);
		log.debug("getChilds: {}", childs);
		return childs;
	}
	
	@Override
	public boolean isValid(String mailPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("isValid({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		boolean valid = mm.isValid(mailPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
}
