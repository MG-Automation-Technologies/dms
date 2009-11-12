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

package es.git.openkm.backend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;

import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTStatsInfo;

/**
 * OKMFolderService
 * 
 * @author jllort
 *
 */
public interface OKMStatsService extends RemoteService {
	public GWTStatsInfo getFoldersByContext() throws OKMException;
	public GWTStatsInfo getDocumentsByContext() throws OKMException;
	public GWTStatsInfo getDocumentsSizeByContext() throws OKMException;
}