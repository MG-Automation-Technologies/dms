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

package es.git.openkm.backend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * OKMGeneralUtilsServiceAsync
 * 
 * @author jllort
 *
 */
public interface OKMGeneralUtilsServiceAsync {
	public void repositoryImport(String repoPath, String fsPath, AsyncCallback callback);
	public void repositoryExport(String repoPath, String fsPath, AsyncCallback callback);
	public void registerCustomNodeTypes(String pgPath, AsyncCallback callback);
}