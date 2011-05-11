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
	public static final String ORIGIN_OKMAuthService				= "007";
	public static final String ORIGIN_OKMSearchService				= "008";
	public static final String ORIGIN_OKMPropertyGroupService		= "009";
	public static final String ORIGIN_OKMNotifyService 				= "010";
	public static final String ORIGIN_OKMBookmarkService 			= "011";
	public static final String ORIGIN_OKMRepositoryService			= "012";
	public static final String ORIGIN_OKMDashboardService 			= "013";
	public static final String ORIGIN_OKMWorkspaceService 			= "014";
	public static final String ORIGIN_OKMWorkflowService 			= "015";
	public static final String ORIGIN_OKMMailService	 			= "016";
	public static final String ORIGIN_OKMPropertyService	 		= "017";
	public static final String ORIGIN_OKMBrowser 					= "018";
	public static final String ORIGIN_OKMUserCopyService 			= "019";
	public static final String ORIGIN_OKMNoteService				= "020";
	
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
	public static final String CAUSE_Configuration 				= "022";
	public static final String CAUSE_QuotaExceed 				= "023";
	public static final String CAUSE_DatabaseException			= "024";
	public static final String CAUSE_PrincipalAdapterException	= "025";
	public static final String CAUSE_WorkflowException			= "026";
	public static final String CAUSE_DocumentNameMismatch		= "027";

	/**
	 * Gets the error
	 * 
	 * The final error code returned by application is ORIGIN_CODE_ERROR + CAUSE_CODE_ERROR
	 * example ERROR 001001 = Error causes on OKMFolderService and originated by Repository Exception
	 * 
	 * @param origin The error origin
	 * @param cause The error cause
	 * @return The error
	 */
	public static String get(String origin, String cause) {
		return "OKM-" + origin + cause;
	}
}