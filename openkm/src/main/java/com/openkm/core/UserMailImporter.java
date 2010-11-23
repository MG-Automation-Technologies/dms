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

package com.openkm.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.dao.MailAccountDAO;
import com.openkm.dao.bean.MailAccount;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.MailUtils;

public class UserMailImporter extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(UserMailImporter.class);

	@Override
	public void run() {
		log.info("*** UserMailImporter activated ***");
        
		try {
			if (!Config.SYSTEM_READONLY) {
				String systemToken = JcrSessionManager.getInstance().getSystemToken();
				Collection<String> users = OKMAuth.getInstance().getUsers(systemToken);
				
				for (Iterator<String> usrIt = users.iterator(); usrIt.hasNext(); ) {
					String uid = usrIt.next();
					List<MailAccount> mailAccounts = MailAccountDAO.findByUser(uid, true);
					
					for (Iterator<MailAccount> maIt = mailAccounts.iterator(); maIt.hasNext(); ) {
						MailAccount ma = maIt.next();
						
						if (!Config.SYSTEM_READONLY) {
							MailUtils.importMessages(uid, ma);
						}
					}
				}
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (ItemExistsException e) {
			log.error(e.getMessage(), e);
		} catch (VirusDetectedException e) {
			log.error(e.getMessage(), e);
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
		} catch (UserQuotaExceededException e) {
			log.error(e.getMessage(), e);
		}
	}
}
