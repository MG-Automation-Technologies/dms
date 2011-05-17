/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.servlet.frontend;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.DatabaseMetadataDAO;
import com.openkm.dao.bean.DatabaseMetadataValue;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTDatabaseMetadataValue;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.OKMDatabaseMetadataService;
import com.openkm.util.DatabaseMetadataUtils;
import com.openkm.util.GWTUtil;

/**
 * DatabaseMetadataServlet
 * 
 * @author jllort
 *
 */
public class DatabaseMetadataServlet extends OKMRemoteServiceServlet implements OKMDatabaseMetadataService {
	private static Logger log = LoggerFactory.getLogger(DatabaseMetadataServlet.class);
	private static final long serialVersionUID = -879908904295685769L;
	
	@Override
	public List<GWTDatabaseMetadataValue> executeValueQuery(String table, String filter, String order) throws OKMException {
		log.debug("executeValueQuery({},{},{})", new Object[]{table, filter, order});
		List<GWTDatabaseMetadataValue> metadataValues = new ArrayList<GWTDatabaseMetadataValue>();
		try {
			for (DatabaseMetadataValue dmv : DatabaseMetadataDAO.executeValueQuery(DatabaseMetadataUtils.buildQuery(table, filter, order))) {
				metadataValues.add(GWTUtil.copy(dmv));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDatabaseMetadataService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		log.debug("executeValueQuery: " + metadataValues);
		return metadataValues;
	}
	
	@Override
	public void updateValue(GWTDatabaseMetadataValue dmv) throws OKMException {
		log.debug("updateValue()");
		try {
			DatabaseMetadataDAO.updateValue(GWTUtil.copy(dmv));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDatabaseMetadataService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void createValue(GWTDatabaseMetadataValue dmv) throws OKMException {
		log.debug("createValue()");
		try {			
			DatabaseMetadataDAO.createValue(GWTUtil.copy(dmv));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDatabaseMetadataService, ErrorCode.CAUSE_Database), e.getMessage());
		} 
	}
}