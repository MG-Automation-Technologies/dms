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

package com.openkm.frontend.client.config;

/**
 * Error code to determine more exactly by administrator the error cause
 * without needed to go to log file making general error codification for
 * application
 * 
 * @author jllort
 */
public class ErrorCode {
	
	// Origin code error is XXX digits
	public static final String ORIGIN_OKMFolderService 				= "001";
	public static final String ORIGIN_OKMDocumentService 			= "002";
	public static final String ORIGIN_OKMRemoteService				= "003";
	public static final String ORIGIN_OKMDownloadService			= "004";
	public static final String ORIGIN_OKMUploadService				= "005";
	public static final String ORIGIN_OKMHttpServlet				= "006";
	public static final String ORIGIN_OKMAuthServlet				= "007";
	public static final String ORIGIN_OKMSearchService				= "008";
	public static final String ORIGIN_OKMPropertyGroupService		= "009";
	public static final String ORIGIN_OKMNotifyService 				= "010";
	public static final String ORIGIN_OKMBookmarkService 			= "011";
	public static final String ORIGIN_RepositoryServlet				= "012";
	public static final String ORIGIN_OKMGeneralUtilsServletAdmin	= "013";
	public static final String ORIGIN_OKMAuthServletAdmin			= "014";
	public static final String ORIGIN_OKMSearchServiceAdmin			= "015";
	public static final String ORIGIN_OKMRemoteServiceAdmin			= "016";
	public static final String ORIGIN_OKMPropertyGroupServiceAdmin	= "017";
	public static final String ORIGIN_RepositoryServletAdmin		= "018";
	public static final String ORIGIN_OKMFolderServiceAdmin			= "019";
	public static final String ORIGIN_OKMDashboardService 			= "020";
	public static final String ORIGIN_OKMWorkspaceService 			= "021";
	public static final String ORIGIN_OKMWorkflowService 			= "022";
	public static final String ORIGIN_OKMWorkflowUploadServiceAdmin	= "023";
	public static final String ORIGIN_OKMMailService	 			= "024";
	public static final String ORIGIN_OKMPropertyService	 		= "025";
	
	// Cause code error is XXX digits
	public static final String CAUSE_Repository 				= "001";
	public static final String CAUSE_ItemNotFound 				= "002";
	public static final String CAUSE_ItemExists 				= "003";
	public static final String CAUSE_Lock						= "004";
	public static final String CAUSE_UnLock						= "005";
	public static final String CAUSE_General 					= "006";
	public static final String CAUSE_OKMGeneral					= "007";
	public static final String CAUSE_GWTShellEnviroment			= "008";
	public static final String CAUSE_AccessDenied				= "009";
	public static final String CAUSE_UnsupportedMimeType		= "010";
	public static final String CAUSE_FileSizeExceeded			= "011";
	public static final String CAUSE_NoSuchGroup				= "012";
	public static final String CAUSE_IOException				= "013";
	public static final String CAUSE_NoSuchProperty				= "014";
	public static final String CAUSE_PathNotFound				= "015";
	public static final String CAUSE_Version					= "016";
	public static final String CAUSE_SessionLost				= "017";
	public static final String CAUSE_FileNotFoundException		= "018";
	public static final String CAUSE_ParseException				= "019";
	public static final String CAUSE_InvalidNodeTypeDefException= "020";
	public static final String CAUSE_SQLException 				= "021";

	/**
	 * Gets the error
	 * 
	 * The final error code returned by appplication is ORIGIN_CODE_ERROR + CAUSE_CODE_ERROR
	 * exemple ERROR 001001 = Error causes on OKMFolderService and originated by Repository Exception
	 * 
	 * @param origin The error origine
	 * @param cause The error cause
	 * @return The error
	 */
	public static String get(String origin, String cause) {
		return "OKM-" + origin + cause;
	}
}