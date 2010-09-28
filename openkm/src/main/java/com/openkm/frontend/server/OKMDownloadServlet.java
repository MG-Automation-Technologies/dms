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

package com.openkm.frontend.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream.UnicodeExtraFieldPolicy;
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
import com.openkm.util.DocConverter;
import com.openkm.util.FileUtils;
import com.openkm.util.impexp.RepositoryExporter;
import com.openkm.util.impexp.TextInfoDecorator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMDownloadServlet"
 *                           display-name="Name for DownloadDocument"
 *                           description="Description for Download Servlet"
 * @web.servlet-mapping      url-pattern="/OKMDownloadServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMDownloadServlet extends OKMHttpServlet {
	private static Logger log = LoggerFactory.getLogger(OKMDownloadServlet.class);
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
				pdfCache = new File(Config.PDF_CACHE+File.separator+doc.getUuid()+".pdf");
				swfCache = new File(Config.SWF_CACHE+File.separator+doc.getUuid()+".swf");
				
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
				sendFile(request, response, fileName, "application/zip", inline, is);
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
							is = OKMDownloadServlet.class.getResourceAsStream("preview_problem.swf");
						}
					}
					
					doc.setMimeType(DocConverter.SWF);
					fileName = FileUtils.getFileName(fileName)+".swf";
				}
				
				sendFile(request, response, fileName, doc.getMimeType(), inline, is);
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
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_IOException), e.getMessage()));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_DatabaseException), e.getMessage()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_General), e.getMessage()));
		} finally {
			org.apache.commons.io.FileUtils.deleteQuietly(tmp);
		}
		
		log.debug("service: void");
	}
	
	/**
	 * Send file to client browser
	 * @throws IOException If there is a communication error.
	 */
	private void sendFile(HttpServletRequest req, HttpServletResponse resp, 
			String fileName, String mimeType, boolean inline, InputStream is) throws IOException {
		log.debug("sendFile({}, {}, {}, {}, {}, {})", new Object[] {req, resp, fileName, mimeType, inline, is});
		String agent = req.getHeader("USER-AGENT");
		
		// Disable browser cache
		resp.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		resp.setHeader("Cache-Control", "max-age=0, must-revalidate");
		resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
		
		// Set MIME type
		resp.setContentType(mimeType);
		
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			log.debug("Agent: Explorer");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (null != agent && -1 != agent.indexOf("Mozilla"))	{
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}
		
		if (inline) {
			resp.setHeader("Content-disposition", "inline; filename=\""+fileName+"\"");
		} else {
			resp.setHeader("Content-disposition", "attachment; filename=\""+fileName+"\"");
		}

		// Set length
		resp.setContentLength(is.available());
		log.debug("File: {}, Length: {}", fileName, is.available());
		
		ServletOutputStream sos = resp.getOutputStream();
		IOUtils.copy(is, sos);
		sos.flush();
		sos.close();
	}
	
	/**
	 * Generate a zip file from a repository folder path   
	 */
	private void exportZip(String path, OutputStream os) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, ArchiveException, IOException, DatabaseException  {
		log.debug("exportZip({}, {})", path, os);
		ZipArchiveOutputStream zos = null;
		File tmp = null;
		
		try {
			tmp = FileUtils.createTempDir();
			
			// Export files
			StringWriter out = new StringWriter();
			RepositoryExporter.exportDocuments(path, tmp, false, out, new TextInfoDecorator(path));
			out.close();
			
			// Zip files
			zos =  new ZipArchiveOutputStream(os);
			zos.setComment("Generated by OpenKM");
			zos.setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy.ALWAYS);
			zos.setUseLanguageEncodingFlag(true);
			zos.setFallbackToUTF8(true);
			
			// Prevents java.util.zip.ZipException: ZIP file must have at least one entry
			ZipArchiveEntry ze = new ZipArchiveEntry(FileUtils.getName(path)+"/");
			zos.putArchiveEntry(ze);
			zos.closeArchiveEntry();
			
			exportZipHelper(tmp, zos, FileUtils.getName(path));
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
			
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					log.error("Error closing zip output stream", e);
					throw e;
				}
			}
		}
		
		log.debug("exportZip: void");
	}
	
	/**
	 * Create a ZIP from a filesystem directory.
	 */
	private void exportZipHelper(File fs, ZipArchiveOutputStream zos, String zePath) throws IOException {
		log.debug("exportZipHelper({}, {}, {})", new Object[]{ fs, zos, zePath });
		File[] files = fs.listFiles();
		
		for (int i=0; i<files.length; i++) {
			if (files[i].isDirectory()) {
				log.debug("DIRECTORY {}", files[i]);
				ZipArchiveEntry ze = new ZipArchiveEntry(zePath + "/" + files[i].getName() + "/");
				zos.putArchiveEntry(ze);
				zos.closeArchiveEntry();
				
				exportZipHelper(files[i], zos, zePath + "/" + files[i].getName());
			} else {
				log.debug("FILE {}", files[i]);
				FileInputStream fis = new FileInputStream(files[i]);
				ZipArchiveEntry ze = new ZipArchiveEntry(zePath + "/" + files[i].getName());
				zos.putArchiveEntry(ze);
				IOUtils.copy(fis, zos);
				zos.closeArchiveEntry();
				fis.close();
			}
		}
		
		log.debug("exportZipHelper: void");
	}
}
