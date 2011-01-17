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

package com.openkm.servlet.extension;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.extension.Contact;
import com.openkm.dao.extension.ContactDAO;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTContact;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMContactService;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;
import com.openkm.util.GWTUtil;

/**
 * ContactServlet
 * 
 * @author jllort
 *
 */
public class ContactServlet extends OKMRemoteServiceServlet implements OKMContactService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ContactServlet.class);
	
	@Override
	public void create(GWTContact contact) throws OKMException {
		try {
			ContactDAO.create(GWTUtil.copy(contact));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMContactService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void delete(int id) throws OKMException {
		try {
			ContactDAO.delete(id);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMContactService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public List<GWTContact> findByUuid(String uuid) throws OKMException {
		List<GWTContact> contacts = new ArrayList<GWTContact>();
		try {
			for (Contact contact : ContactDAO.findByUuid(uuid)) {
				contacts.add(GWTUtil.copy(contact));
			}
			return contacts;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMContactService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void update(GWTContact contact) throws OKMException {
		try {
			ContactDAO.update(GWTUtil.copy(contact));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMContactService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
}