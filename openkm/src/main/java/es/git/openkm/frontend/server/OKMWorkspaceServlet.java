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

import java.sql.SQLException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allen_sauer.gwt.log.client.Log;

import es.git.openkm.api.OKMDashboard;
import es.git.openkm.core.Config;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.dao.AuthDAO;
import es.git.openkm.dao.bean.MailAccount;
import es.git.openkm.dao.bean.User;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTWorkspace;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMWorkspaceService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMWorkspaceServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMWorkspaceServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMWorkspaceServlet extends OKMRemoteServiceServlet implements OKMWorkspaceService {
	private static Logger log = LoggerFactory.getLogger(OKMWorkspaceServlet.class);
	private static final long serialVersionUID = 8673521252684830906L;
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkspaceService#getWorkspaceUser()
	 */
	public GWTWorkspace getUserWorkspace() throws OKMException {
		GWTWorkspace workspace = new GWTWorkspace();
		
		if ( Config.APPLICATION_URL!= null && Config.APPLICATION_URL.indexOf("/OpenKM")>0) {
			workspace.setApplicationURL(Config.APPLICATION_URL);
		}
		
		workspace.setUser(getThreadLocalRequest().getRemoteUser());
		workspace.setAdmin(getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE));
		workspace.setToken((String)getThreadLocalRequest().getSession().getAttribute("token"));
		
		if (Config.PRINCIPAL_ADAPTER.equals("es.git.openkm.principal.DatabasePrincipalAdapter")) {
			AuthDAO authDAO = AuthDAO.getInstance();
			try {
				for (Iterator<MailAccount> it = authDAO.findMailAccountsByUser(getThreadLocalRequest().getRemoteUser(), true).iterator(); it.hasNext();) {
					MailAccount mailAccount = it.next();
					workspace.setImapHost(mailAccount.getMailHost());
					workspace.setImapUser(mailAccount.getMailUser());
					workspace.setImapFolder(mailAccount.getMailFolder());
				}
			} catch (SQLException e) {
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
			}
			
			workspace.setChangePassword(true);
		} else {
			workspace.setChangePassword(false);
		}
		
		return workspace;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkspaceService#getUserDocumentsSize()
	 */
	public Double getUserDocumentsSize() throws OKMException {
		String token = getToken();
		Double docSize = new Double(0);
		
		try {
			docSize = new Double(OKMDashboard.getInstance().getUserDocumentsSize(token));
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		return docSize;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMWorkspaceService#updateUserWorkspace(es.git.openkm.frontend.client.bean.GWTWorkspace)
	 */
	public void updateUserWorkspace(GWTWorkspace workspace) throws OKMException {
		Log.info("host:"+workspace.getImapHost());
		Log.info("folder:"+workspace.getImapFolder());
		Log.info("user:"+workspace.getImapUser());
		Log.info("imat password:"+workspace.getImapPassword());
		Log.info("password:"+workspace.getPassword());
		Log.info("user:"+workspace.getUser());
		Log.info("PRINCIPAL_ADAPTER:"+Config.PRINCIPAL_ADAPTER);
	
		// For updating user
		User user = new User();
		user.setId(workspace.getUser());
		user.setPass(workspace.getPassword());
		
		// For updating imap mail
		MailAccount mailAccount = new MailAccount();
		mailAccount.setActive(true);
		mailAccount.setMailFolder(workspace.getImapFolder());
		mailAccount.setMailHost(workspace.getImapHost());
		mailAccount.setMailPassword(workspace.getImapPassword());
		mailAccount.setMailUser(workspace.getImapUser());
		mailAccount.setUser(workspace.getUser());
		
		try {
			// Can change password
			if (Config.PRINCIPAL_ADAPTER.equals("es.git.openkm.principal.DatabasePrincipalAdapter")) {
				AuthDAO authDAO = AuthDAO.getInstance();
				authDAO.updatePassword(user);
				if (authDAO.findMailAccountsByUser(workspace.getUser(), false).size()>0) {
					authDAO.updateMailAccount(mailAccount);
				} else {
					authDAO.createMailAccount(mailAccount);
				}
			}
		} catch (SQLException e) {
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
	}
}