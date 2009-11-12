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

package es.git.openkm.module;

import es.git.openkm.bean.StatsInfo;
import es.git.openkm.core.RepositoryException;

public interface StatsModule {

	/**
	 * Get number of documents per context
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public StatsInfo getDocumentsByContext(String token) throws RepositoryException;
	
	/**
	 * Get number of folders per context
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public StatsInfo getFoldersByContext(String token) throws RepositoryException;
	
	/**
	 * Get sizer of documents per context
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public StatsInfo getDocumentsSizeByContext(String token) throws RepositoryException;
}
