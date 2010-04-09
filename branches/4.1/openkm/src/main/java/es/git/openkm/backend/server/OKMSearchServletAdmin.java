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

package es.git.openkm.backend.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.service.OKMSearchService;
import es.git.openkm.backend.client.config.ErrorCode;
import es.git.openkm.module.direct.DirectRepositoryModule;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMSearchServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMSearchServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMSearchServletAdmin extends RemoteServiceServlet implements OKMSearchService {
	
	private static Logger log = LoggerFactory.getLogger(OKMSearchServletAdmin.class);
	private static final long serialVersionUID = 2638205115826644606L;
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMSearchService#getSearch(java.lang.String, java.lang.String)
	 */
	public List<Vector<String>> getSearch(String statement, String type) throws OKMException {
		log.debug("getSearch(statement:"+statement+", type:"+type+")");
		List<Vector<String>> al = new ArrayList<Vector<String>>();

		Session jcrSession = DirectRepositoryModule.getSystemSession();
		Workspace workspace = jcrSession.getWorkspace();
		QueryManager queryManager;
		
		try {
			queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, type);
			QueryResult result = query.execute();
			RowIterator it = result.getRows();
			String[] columns = result.getColumnNames();
			
			// First row always are columns name
			if (columns.length>0) {
				Vector<String> vector = new Vector<String>();
				for (int i=0; i<columns.length; i++) {
					vector.add(columns[i]);
				}
				al.add(vector);
			}
			
			// Adding results
			while (it.hasNext()) {
				Row row = it.nextRow();
				Vector<String> vector = new Vector<String>();
				
				for (int i=0; i<columns.length; i++) {
					Value v = row.getValue(columns[i]);
					String valor = (v==null?"NULL":v.getString());
					vector.add(valor);
				}
				
				al.add(vector);
			}
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchServiceAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		}

		log.debug("getSearch: ");
		return al;
	}
}
