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

package es.git.openkm.core;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMStats;
import es.git.openkm.bean.StatsInfo;

public class RepositoryInfo extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(RepositoryInfo.class);
	private static StatsInfo documentsByContext = new StatsInfo();
	private static StatsInfo foldersByContext = new StatsInfo();
	private static StatsInfo documentsSizeByContext = new StatsInfo();
	
	public void run() {
		log.debug("*** Begin repository info ***");
		String systemToken = SessionManager.getInstance().getSystemToken();
		OKMStats okmStats = OKMStats.getInstance();
		
		try {
			documentsByContext = okmStats.getDocumentsByContext(systemToken);
			foldersByContext = okmStats.getFoldersByContext(systemToken);
			documentsSizeByContext = okmStats.getDocumentsSizeByContext(systemToken);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}

		log.debug("*** End repository info ***");
	}
	
	/**
	 * @return
	 */
	public static StatsInfo getDocumentsByContext() {
		return documentsByContext;
	}
	
	/**
	 * @return
	 */
	public static StatsInfo getFoldersByContext() {
		return foldersByContext;
	}
	
	/**
	 * @return
	 */
	public static StatsInfo getDocumentsSizeByContext() {
		return documentsSizeByContext;
	}
}
