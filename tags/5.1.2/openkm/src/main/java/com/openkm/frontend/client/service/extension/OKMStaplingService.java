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

package com.openkm.frontend.client.service.extension;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTStapleGroup;

/**
 * @author jllort
 *
 */
public interface OKMStaplingService extends RemoteService {
	public String create(String username, String uuid, String type, String uuid2, String type2) throws OKMException;
	public void add(String id, String uuid, String type) throws OKMException;
	public void remove(String id) throws OKMException;
	public void removeStaple(String id) throws OKMException;
	public List<GWTStapleGroup> getAll(String uuid) throws OKMException;
	public void removeAllStapleByUuid(String uuid) throws OKMException;
}