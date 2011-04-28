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

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Mail;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.VirusDetectedException;
import es.git.openkm.module.MailModule;
import es.git.openkm.module.ModuleManager;

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
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#create(java.lang.String, es.git.openkm.bean.Mail)
	 */
	@Override
	public Mail create(String token, Mail mail) throws PathNotFoundException, 
			ItemExistsException, VirusDetectedException, AccessDeniedException, RepositoryException {
		log.debug("create("+token+", " + mail + ")");
		MailModule mm = ModuleManager.getMailModule();
		Mail newMail = mm.create(token, mail);
		log.debug("create: "+ newMail);
		return newMail;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#getProperties(java.lang.String, java.lang.String)
	 */
	@Override
	public Mail getProperties(String token, String mailPath) throws PathNotFoundException, 
			RepositoryException {
		log.debug("getProperties("+token+", " + mailPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		Mail mail = mm.getProperties(token, mailPath);
		log.debug("getProperties: "+mail);
		return mail;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String token, String mailPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("delete("+token+", " + mailPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		mm.delete(token, mailPath);
		log.debug("delete: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#purge(java.lang.String, java.lang.String)
	 */
	@Override
	public void purge(String token, String mailPath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("purge("+token+", " + mailPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		mm.purge(token, mailPath);
		log.debug("purge: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Mail rename(String token, String mailPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("rename("+token+", " + mailPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		Mail renamedMail = mm.rename(token, mailPath, newName);
		log.debug("rename: "+renamedMail);
		return renamedMail;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void move(String token, String mailPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("move("+token+", " + mailPath + ", " + dstPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		mm.move(token, mailPath, dstPath);
		log.debug("move: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void copy(String token, String mailPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, IOException {
		log.debug("copy("+token+", " + mailPath + ", " + dstPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		mm.copy(token, mailPath, dstPath);
		log.debug("copy: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#getChilds(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Mail> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException {
		log.debug("getChilds(" + token + ", " + fldPath + ")");
		MailModule mm = ModuleManager.getMailModule();
		Collection<Mail> childs = mm.getChilds(token, fldPath);
		log.debug("getChilds: "+childs);
		return childs;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.MailModule#isValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isValid(String token, String mailPath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("isValid("+token+", "+mailPath+")");
		MailModule mm = ModuleManager.getMailModule();
		boolean valid = mm.isValid(token, mailPath);
		log.debug("isValid: "+valid);
		return valid;
	}
}