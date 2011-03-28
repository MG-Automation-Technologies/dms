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

package com.openkm.extension.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.extension.dao.WikiPageDAO;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTWikiPage;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMWikiService;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;

/**
 * WikiServlet
 */
public class WikiServlet extends OKMRemoteServiceServlet implements OKMWikiService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WikiServlet.class);
	
	@Override
	public GWTWikiPage findByPk(String uuid) throws OKMException {
		GWTWikiPage wikiPage = new GWTWikiPage();
		 try {
			WikiPageDAO.findByPk(uuid);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWikiService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		return wikiPage;
	}
}