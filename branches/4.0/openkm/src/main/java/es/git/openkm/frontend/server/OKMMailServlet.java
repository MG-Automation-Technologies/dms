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

package es.git.openkm.frontend.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMMail;
import es.git.openkm.api.OKMRepository;
import es.git.openkm.bean.Mail;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMMailService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMMailServlet"
 *                           display-name="Directory mail service"
 *                           description="Directory mail service"
 * @web.servlet-mapping      url-pattern="/OKMMailServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMMailServlet extends OKMRemoteServiceServlet implements OKMMailService {
	private static Logger log = LoggerFactory.getLogger(OKMMailServlet.class);
	private static final long serialVersionUID = 6444705787188086209L;
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMMailService#getChilds(java.lang.String)
	 */
	public List<GWTMail> getChilds(String fldPath) throws OKMException {
		log.debug("getMailChilds("+fldPath+")");
		List<GWTMail> mailList = new ArrayList<GWTMail>(); 
		String token = getToken();
		
		try {
			if (fldPath == null) {
				fldPath = OKMRepository.getInstance().getMailFolder(token).getPath();
			} 
			
			log.debug("ParentFolder: "+fldPath);
			Collection<Mail> col = OKMMail.getInstance().getChilds(token, fldPath);
			
			for (Iterator<Mail> it = col.iterator(); it.hasNext();) {		
				Mail mail = it.next();
				log.debug("Mail: "+mail);
				GWTMail mailClient = Util.copy(mail);

				log.debug("Mail: "+mail+" -> MailClient: "+mailClient);
				mailList.add(mailClient);
			}
			
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		return mailList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMMailService#delete(java.lang.String)
	 */
	public void delete(String mailPath) throws OKMException {
		log.debug("delete("+mailPath+")");
		String token = getToken();
		
		try {
			OKMMail.getInstance().delete(token, mailPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMMailService#move(java.lang.String, java.lang.String)
	 */
	public void move(String mailPath, String destPath) throws OKMException {
		log.debug("move("+mailPath+","+destPath+")");
		String token = getToken();
		
		try {
			OKMMail.getInstance().move(token,mailPath,destPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMMailService#purge(java.lang.String)
	 */
	public void purge(String mailPath) throws OKMException {
		log.debug("purge("+mailPath+")");
		String token = getToken();
		
		try {
			OKMMail.getInstance().purge(token,mailPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("purge: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMMailService#copy(java.lang.String, java.lang.String)
	 */
	public void copy(String mailPath, String fldPath) throws OKMException {
		log.debug("copy("+mailPath+", " + fldPath + ")");
		String token = getToken();
		
		try {
			OKMMail.getInstance().copy(token, mailPath, fldPath);
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("copy: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMMailService#getProperties(java.lang.String)
	 */
	public GWTMail getProperties(String mailPath) throws OKMException {
		log.debug("getProperties("+mailPath+ ")");
		String token = getToken();
		GWTMail mailClient = new GWTMail();
		
		try {
			mailClient = Util.copy(OKMMail.getInstance().getProperties(token, mailPath));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMailService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("copy: getProperties");
		
		return mailClient;
	}
}