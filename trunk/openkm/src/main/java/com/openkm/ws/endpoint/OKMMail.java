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
 * Servlet Class
 * 
 * @web.servlet name="OKMMail"
 * @web.servlet-mapping url-pattern="/OKMMail"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@SecurityDomain("OpenKM")
public class OKMMail {
	private static Logger log = LoggerFactory.getLogger(OKMMail.class);

	@WebMethod
	public Mail create(@WebParam(name = "mail") Mail mail) throws PathNotFoundException, ItemExistsException,
			VirusDetectedException, AccessDeniedException, RepositoryException, DatabaseException,
			UserQuotaExceededException {
		log.debug("create({})", mail);
		MailModule mm = ModuleManager.getMailModule();
		Mail newMail = mm.create(mail);
		log.debug("create: {}", newMail);
		return newMail;
	}

	@WebMethod
	public Mail getProperties(@WebParam(name = "mailPath") String mailPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getProperties({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		Mail mail = mm.getProperties(mailPath);
		log.debug("getProperties: {}", mail);
		return mail;
	}

	@WebMethod
	public void delete(@WebParam(name = "mailPath") String mailPath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("delete({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		mm.delete(mailPath);
		log.debug("delete: void");
	}
	
	@WebMethod
	public Mail rename(@WebParam(name = "mailPath") String mailPath,
			@WebParam(name = "newName") String newName) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("rename({}, {})", mailPath, newName);
		MailModule mm = ModuleManager.getMailModule();
		Mail renamedMail = mm.rename(mailPath, newName);
		log.debug("rename: {}");
		return renamedMail;
	}
	
	@WebMethod
	public void move(@WebParam(name = "mailPath") String mailPath,
			@WebParam(name = "dstPath") String dstPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {})", mailPath, dstPath);
		MailModule mm = ModuleManager.getMailModule();
		mm.move(mailPath, dstPath);
		log.debug("move: void");
	}
	
	@WebMethod
	public Mail[] getChilds(@WebParam(name = "mailPath") String mailPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({})", mailPath);
		MailModule mm = ModuleManager.getMailModule();
		List<Mail> col = mm.getChilds(mailPath);
		Mail[] result = (Mail[]) col.toArray(new Mail[col.size()]);
		log.debug("getChilds: {}", result);
		return result;
	}
}
