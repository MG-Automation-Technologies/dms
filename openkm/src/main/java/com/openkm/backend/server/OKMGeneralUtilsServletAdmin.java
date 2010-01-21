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

package com.openkm.backend.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.jcr.Session;

import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.compact.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.backend.client.OKMException;
import com.openkm.backend.client.config.ErrorCode;
import com.openkm.backend.client.service.OKMGeneralUtilsService;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.FormatUtil;
import com.openkm.util.impexp.HTMLInfoDecorator;
import com.openkm.util.impexp.ImpExpStats;
import com.openkm.util.impexp.RepositoryExporter;
import com.openkm.util.impexp.RepositoryImporter;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMGeneralUtilsServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMGeneralUtilsServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMGeneralUtilsServletAdmin extends OKMRemoteServiceServletAdmin implements OKMGeneralUtilsService {
	private static Logger log = LoggerFactory.getLogger(OKMGeneralUtilsServletAdmin.class);
	private static final long serialVersionUID = 2638205115826644606L;
	
	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMGeneralUtilsService#repositoryImport(java.lang.String, java.lang.String)
	 */
	public String repositoryImport(String repoPath, String fsPath) throws OKMException {
		log.debug("repositoryImport("+ repoPath +", "+ fsPath + ")");
		String msg = "";
		
		if (repoPath != null && !repoPath.equals("") && fsPath != null && !fsPath.equals("")) {
			SessionManager sm = SessionManager.getInstance();
			String token = sm.getTokenByUserId(Config.SYSTEM_USER);

			try {
				StringWriter out = new StringWriter();
				long begin = System.currentTimeMillis();
				ImpExpStats stats = RepositoryImporter.importDocuments(token, new File(fsPath), repoPath, out, new HTMLInfoDecorator());
				long end = System.currentTimeMillis();
				out.append("<br/>");
				out.append("<b>Documents:</b> "+stats.getDocuments()+"<br/>");
				out.append("<b>Folders:</b> "+stats.getFolders()+"<br/>");
				out.append("<b>Size:</b> "+FormatUtil.formatSize(stats.getSize())+"<br/>");
				out.append("<b>Time:</b> "+FormatUtil.formatSeconds(end - begin)+"<br/>");
				msg = out.toString();

			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_IOException), e.getMessage());
				
			} catch (PathNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_PathNotFound), e.getMessage());
				
			} catch (ItemExistsException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_ItemExists), e.getMessage());
				
			} catch (AccessDeniedException e ) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_AccessDenied), e.getMessage());
				
			} catch	(RepositoryException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
			} 
		}
		
		log.debug("repositoryImport: void");
		return msg;
	}


	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMGeneralUtilsService#repositoryExport(java.lang.String, java.lang.String)
	 */
	public String repositoryExport(String repoPath, String fsPath) throws OKMException {
		log.debug("repositoryExport("+ repoPath +", "+ fsPath + ")");
		String msg = "";
		
		if (repoPath != null && !repoPath.equals("") && fsPath != null && !fsPath.equals("")) {
			SessionManager sm = SessionManager.getInstance();
			String token = sm.getTokenByUserId(Config.SYSTEM_USER);
			
			try {
				StringWriter out = new StringWriter();
				long begin = System.currentTimeMillis();
				ImpExpStats stats = RepositoryExporter.exportDocuments(token, repoPath, new File(fsPath), out, new HTMLInfoDecorator());
				long end = System.currentTimeMillis();
				out.append("<br/>");
				out.append("<b>Documents:</b> "+stats.getDocuments()+"<br/>");
				out.append("<b>Folders:</b> "+stats.getFolders()+"<br/>");
				out.append("<b>Size:</b> "+FormatUtil.formatSize(stats.getSize())+"<br/>");
				out.append("<b>Time:</b> "+FormatUtil.formatSeconds(end - begin)+"<br/>");
				msg = out.toString();

			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_IOException), e.getMessage());
				
			} catch (PathNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_PathNotFound), e.getMessage());
				
			} catch (AccessDeniedException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_AccessDenied), e.getMessage());
				
			} catch	(RepositoryException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
			}
		}
		
		log.debug("repositoryExport: void");
		return msg;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMGeneralUtilsService#registerCustomNodeTypes(java.lang.String)
	 */
	public void registerCustomNodeTypes(String pgPath) throws OKMException {
		log.debug("registerCustomNodeTypes("+ pgPath + ")");
		if (!pgPath.equals("")) {
			Session system = DirectRepositoryModule.getSystemSession();
			FileInputStream fis;
			try {
				fis = new FileInputStream(pgPath);
				DirectRepositoryModule.registerCustomNodeTypes(system, fis);
			} catch (FileNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_FileNotFoundException), e.getMessage());
				
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_ParseException), e.getMessage());
				
			} catch (javax.jcr.RepositoryException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
				
			} catch (InvalidNodeTypeDefException e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralUtilsServletAdmin, ErrorCode.CAUSE_InvalidNodeTypeDefException), e.getMessage());
				
			}
		}
		log.debug("repositoryExport: void");
	}
}
