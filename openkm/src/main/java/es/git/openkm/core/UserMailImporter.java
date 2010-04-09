/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.core;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMAuth;
import es.git.openkm.dao.AuthDAO;
import es.git.openkm.dao.bean.MailAccount;
import es.git.openkm.util.MailUtils;

public class UserMailImporter extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(UserMailImporter.class);

	public void run() {
		log.info("*** UserMailImporter activated ***");
		String systemToken = SessionManager.getInstance().getSystemToken();
        
		try {
			Collection<String> users = OKMAuth.getInstance().getUsers(systemToken);
			AuthDAO authDao = AuthDAO.getInstance();
			
			for (Iterator<String> usrIt = users.iterator(); usrIt.hasNext(); ) {
				String uid = usrIt.next();
				Collection<MailAccount> mailAccounts = authDao.findMailAccountsByUser(uid, true);
				
				for (Iterator<MailAccount> maIt = mailAccounts.iterator(); maIt.hasNext(); ) {
					MailAccount ma = maIt.next();
					MailUtils.importMessages(systemToken, uid, ma.getMailHost(), ma.getMailUser(), ma.getMailPassword(), ma.getMailFolder());
				}
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (ItemExistsException e) {
			log.error(e.getMessage(), e);
		} catch (VirusDetectedException e) {
			log.error(e.getMessage(), e);
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
		}
	}
}
