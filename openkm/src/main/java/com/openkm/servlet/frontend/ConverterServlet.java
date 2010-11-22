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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMRepository;
import com.openkm.bean.Document;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.util.DocConverter;
import com.openkm.util.FileUtils;
import com.openkm.util.WebUtil;

/**
 * Document converter service
 */
public class ConverterServlet extends OKMHttpServlet {
	private static Logger log = LoggerFactory.getLogger(ConverterServlet.class);
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		log.debug("service({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String uuid = WebUtil.getString(request, "uuid");
		boolean toPdf = WebUtil.getBoolean(request, "toPdf");
		boolean toSwf = WebUtil.getBoolean(request, "toSwf");
		boolean toDxf = WebUtil.getBoolean(request, "toDxf");
		File tmp = File.createTempFile("okm", ".tmp");
		InputStream is = null;
		updateSessionManager(request);
		
		try {
			File dxfCache = new File(Config.CACHE_DXF + File.separator + uuid + ".dxf");
			File pdfCache = new File(Config.CACHE_PDF + File.separator + uuid + ".pdf");
			File swfCache = new File(Config.CACHE_SWF + File.separator + uuid + ".swf");
			
			// Now an document can be located by UUID
			if (!uuid.equals("")) {
				String path = OKMRepository.getInstance().getNodePath(null, uuid);
				Document doc = OKMDocument.getInstance().getProperties(null, path);
				String fileName = FileUtils.getName(doc.getPath());
				String mimeType = null;
				DocConverter converter = DocConverter.getInstance();
				is = OKMDocument.getInstance().getContent(null, path, false);
				
				// Convert to DXF
				if (toPdf || toDxf && !Config.SYSTEM_DWG2DXF.equals("")) {
					if (!doc.getMimeType().equals(DocConverter.DXF)) {
						if (pdfCache.exists()) {
							is.close();
							is = new FileInputStream(pdfCache);
						} else {
							if (doc.getMimeType().equals(DocConverter.DWG)) {
								FileOutputStream os = new FileOutputStream(tmp);
								IOUtils.copy(is, os);
								os.flush();
								os.close();
								converter.dwg2dxf(tmp, dxfCache);
							}
							
							is.close();
							is = new FileInputStream(pdfCache);
						}
					}
					
					mimeType = DocConverter.PDF;
					fileName = FileUtils.getFileName(fileName)+".pdf";
				}
				
				// Convert to PDF
				if (toPdf || toSwf && !Config.SYSTEM_PDF2SWF.equals("")) {
					if (!doc.getMimeType().equals(DocConverter.PDF)) {
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
					}
					
					mimeType = DocConverter.PDF;
					fileName = FileUtils.getFileName(fileName)+".pdf";
				}
				
				// Convert to SWF
				if (toSwf && !Config.SYSTEM_PDF2SWF.equals("")) {
					if (swfCache.exists()) {
						is.close();
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
							
							is.close();
							is = new FileInputStream(swfCache);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							is = DownloadServlet.class.getResourceAsStream("preview_problem.swf");
						}
					}
					
					mimeType = DocConverter.SWF;
					fileName = FileUtils.getFileName(fileName)+".swf";
				}
				
				WebUtil.sendFile(request, response, fileName, mimeType, true, is);
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
}
