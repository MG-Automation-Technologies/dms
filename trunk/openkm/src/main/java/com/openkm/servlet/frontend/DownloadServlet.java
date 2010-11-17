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

package com.openkm.servlet.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMRepository;
import com.openkm.bean.Document;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.util.ArchiveUtils;
import com.openkm.util.DocConverter;
import com.openkm.util.FileUtils;
import com.openkm.util.WebUtil;
import com.openkm.util.impexp.RepositoryExporter;
import com.openkm.util.impexp.TextInfoDecorator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="DownloadServlet"
 *                           display-name="Name for DownloadDocument"
 *                           description="Description for Download Servlet"
 * @web.servlet-mapping      url-pattern="/DownloadServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class DownloadServlet extends OKMHttpServlet {
	private static Logger log = LoggerFactory.getLogger(DownloadServlet.class);
	private static final long serialVersionUID = -1575303169358903617L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		log.debug("service({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String path = id != null?new String(id.getBytes("ISO-8859-1"), "UTF-8"):null;
		String uuid = request.getParameter("uuid");
		String checkout = request.getParameter("checkout");
		String ver = request.getParameter("ver");
		boolean export = request.getParameter("export") != null;
		boolean toPdf = request.getParameter("toPdf") != null;
		boolean toSwf = request.getParameter("toSwf") != null;
		boolean inline = request.getParameter("inline") != null;
		File tmp = File.createTempFile("okm", ".tmp");
		Document doc = null;
		InputStream is = null;
		updateSessionManager(request);
		
		try {
			File pdfCache = null;
			File swfCache = null;
			
			// Now an document can be located by UUID
			if (uuid != null && !uuid.equals("")) {
				OKMRepository okmRepo = OKMRepository.getInstance();
				path = okmRepo.getNodePath(null, uuid);
			}
						
			// Get document
			if (export) {
				FileOutputStream os = new FileOutputStream(tmp);
				exportZip(path, os);
				os.flush();
				os.close();
				is = new FileInputStream(tmp);
			} else {
				OKMDocument okmDoc = OKMDocument.getInstance();
				doc = okmDoc.getProperties(null, path);
				pdfCache = new File(Config.CACHE_PDF + File.separator + doc.getUuid() + ".pdf");
				swfCache = new File(Config.CACHE_SWF + File.separator + doc.getUuid() + ".swf");
				
				if (!toSwf || toSwf && !Config.SYSTEM_PDF2SWF.equals("") && !swfCache.exists() || !toPdf || toPdf && !pdfCache.exists()) {
					if (ver != null && !ver.equals("")) {
						is = okmDoc.getContentByVersion(null, path, ver);
					} else {
						is = okmDoc.getContent(null, path, checkout != null);
					}
				}
			}
			
			// Send document
			if (export) {
				String fileName = FileUtils.getName(path)+".zip";
				WebUtil.sendFile(request, response, fileName, "application/zip", inline, is);
				is.close();
				tmp.delete();
			} else if (doc != null) {
				String fileName = FileUtils.getName(doc.getPath());
				DocConverter converter = DocConverter.getInstance();
				
				// Convert to PDF
				if (toPdf || toSwf && !Config.SYSTEM_PDF2SWF.equals("") && !doc.getMimeType().equals(DocConverter.PDF)) {
					if (pdfCache.exists()) {
						is.close();
						is = new FileInputStream(pdfCache);
					} else {
						if (doc.getMimeType().startsWith("image/")) {
							converter.img2pdf(is, doc.getMimeType(), pdfCache);
						} else {
							converter.doc2pdf(is, doc.getMimeType(), pdfCache);
						}
						
						is.close();
						is = new FileInputStream(pdfCache);
					}
					
					doc.setMimeType(DocConverter.PDF);
					fileName = FileUtils.getFileName(fileName)+".pdf";
				}

				// Convert to SWF
				if (toSwf && !Config.SYSTEM_PDF2SWF.equals("")) {
					if (swfCache.exists()) {
						is = new FileInputStream(swfCache);
					} else {
						try {
							if (doc.getMimeType().equals(DocConverter.PDF)) {
								FileOutputStream os = new FileOutputStream(tmp);
								IOUtils.copy(is, os);
								os.flush();
								os.close();
								converter.pdf2swf(tmp, swfCache);
							} else {
								converter.pdf2swf(pdfCache, swfCache);
							}
							is = new FileInputStream(swfCache);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							is = DownloadServlet.class.getResourceAsStream("preview_problem.swf");
						}
					}
					
					doc.setMimeType(DocConverter.SWF);
					fileName = FileUtils.getFileName(fileName)+".swf";
				}
				
				WebUtil.sendFile(request, response, fileName, doc.getMimeType(), inline, is);
				is.close();
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_PathNotFound), e.getMessage()));
		} catch (RepositoryException e) {
			log.warn(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_Repository), e.getMessage()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_IO), e.getMessage()));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_Database), e.getMessage()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_General), e.getMessage()));
		} finally {
			org.apache.commons.io.FileUtils.deleteQuietly(tmp);
		}
		
		log.debug("service: void");
	}
	
	/**
	 * Generate a zip file from a repository folder path   
	 */
	private void exportZip(String path, OutputStream os) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, ArchiveException, IOException, DatabaseException  {
		log.debug("exportZip({}, {})", path, os);
		File tmp = null;
		
		try {
			tmp = FileUtils.createTempDir();
			
			// Export files
			StringWriter out = new StringWriter();
			RepositoryExporter.exportDocuments(path, tmp, false, out, new TextInfoDecorator(path));
			out.close();
			
			// Zip files
			ArchiveUtils.createZip(tmp, FileUtils.getName(path), os);
		} catch (IOException e) {
			log.error("Error exporting zip", e);
			throw e;
		} finally {
			if (tmp != null) {
				try {
					org.apache.commons.io.FileUtils.deleteDirectory(tmp);
				} catch (IOException e) {
					log.error("Error deleting temporal directory", e);
					throw e;
				}
			}
		}
		
		log.debug("exportZip: void");
	}
}
