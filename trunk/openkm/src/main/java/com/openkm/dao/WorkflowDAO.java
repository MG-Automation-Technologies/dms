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

package com.openkm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

public class WorkflowDAO extends AbstractDAO {
	private static Logger log = LoggerFactory.getLogger(WorkflowDAO.class);
	private static WorkflowDAO instance = null;

	/* (non-Javadoc)
	 * @see com.openkm.dao.AbstractDAO#getDataSourceName()
	 */
	protected String getDataSourceName() {
		return "java:/OKMWorkflow"+Config.INSTALL+"DS";
	}

	/* (non-Javadoc)
	 * @see com.openkm.dao.AbstractDAO#getTableName()
	 */
	protected String getTableName() {
		return "JBPM_ACTION";
	}

	/* (non-Javadoc)
	 * @see com.openkm.dao.AbstractDAO#getSchema()
	 */
	protected String getSchema() {
		return "jbpm_jpdl";
	}

	private WorkflowDAO() {}
	
	/**
	 * @return
	 */
	public static synchronized WorkflowDAO getInstance() { 
		if (instance == null) {
			log.debug("getInstance()");
			instance = new WorkflowDAO();
		}
		
		return instance;
	}
}
