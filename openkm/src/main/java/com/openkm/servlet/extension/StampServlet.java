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

package com.openkm.servlet.extension;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;

import com.lowagie.text.DocumentException;
import com.openkm.api.OKMDocument;
import com.openkm.bean.Document;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VersionException;
import com.openkm.core.VirusDetectedException;
import com.openkm.dao.bean.extension.StampImage;
import com.openkm.dao.bean.extension.StampText;
import com.openkm.dao.extension.StampImageDAO;
import com.openkm.dao.extension.StampTextDAO;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTStamp;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMStampService;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;
import com.openkm.util.DocConverter;
import com.openkm.util.GWTUtil;
import com.openkm.util.PDFUtils;
import com.openkm.util.SecureStore;

/**
 * StampServlet
 * 
 * @author jllort
 *
 */
public class StampServlet extends OKMRemoteServiceServlet implements OKMStampService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(StampServlet.class);

	@Override
	public List<GWTStamp> findAll() throws OKMException {
		log.debug("findAll()");
		List<GWTStamp> stampList = new ArrayList<GWTStamp>();
		String remoteUser = getThreadLocalRequest().getRemoteUser();
		try {
			for (StampText stampText : StampTextDAO.findByUser(remoteUser)) {
				stampList.add(GWTUtil.copy(stampText));
			}
			for (StampImage stampImage : StampImageDAO.findByUser(remoteUser)) {
				stampList.add(GWTUtil.copy(stampImage));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		
		return stampList;
	}

	@Override
	public void Stamp(int id, int type, String path) throws OKMException {
		log.debug("Stamp({}, {})", new Object[] {(Object)id , (Object)type});
		File tmp = null; 
		File tmpPdf= null;
		File tmpStampPdf = null;
		try {
			Document doc = OKMDocument.getInstance().getProperties(null, path); 
			tmp = File.createTempFile("okm", ".tmp");
			tmpPdf = File.createTempFile("okm", ".pdf");
			tmpStampPdf = File.createTempFile("okm", ".pdf");
			FileOutputStream fos = null;
			
			// Copying from repository to temporal file
			if (!doc.getMimeType().equals("application/pdf")) {
				fos = new FileOutputStream(tmp);
			} else {
				fos = new FileOutputStream(tmpPdf);
			}
			InputStream is = OKMDocument.getInstance().getContent(null, path, false);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			is.close();
			
			// Convert from temporal file to temporal pdf if it's needed
			if (!doc.getMimeType().equals("application/pdf")) {
				is = new FileInputStream(tmp);
				DocConverter converter = DocConverter.getInstance();
				if (doc.getMimeType().startsWith("image/")) {
					converter.img2pdf(is, doc.getMimeType(), tmpPdf);
				} else {
					converter.doc2pdf(is, doc.getMimeType(), tmpPdf);
				}
				is.close();
			}
			
			// Stamping pdf file
			is = new FileInputStream(tmpPdf);
			fos = new FileOutputStream(tmpStampPdf);
			switch (type) {
				case GWTStamp.STAMP_TEXT:
					StampText st = StampTextDAO.findByPk(id);
					PDFUtils.stampText(is, st.getText(), st.getLayer(), st.getOpacity(), st.getSize(),
							Color.decode(st.getColor()), st.getRotation(), st.getAlign(), st.getExprX(), st.getExprY(),
							fos);
					break;
					
				case GWTStamp.STAMP_IMAGE:
					StampImage si = StampImageDAO.findByPk(id);
					byte[] image = SecureStore.b64Decode(si.getImageContent());
					PDFUtils.stampImage(is, image, si.getLayer(), si.getOpacity(), si.getExprX(), si.getExprY(), fos);
					break;
			}
			fos.close();
			is.close();
			
			is = new FileInputStream(tmpStampPdf);
			// Uploading document to repository if original is pdf we increment version otheside create new file
			if (!doc.getMimeType().equals("application/pdf")) {
				OKMDocument.getInstance().checkout(null, path);
				OKMDocument.getInstance().setContent(null, path, is);
				OKMDocument.getInstance().checkin(null, path, "Stamped");
			} else {
				Document newDoc = new Document();
				path = path.substring(0,path.lastIndexOf(".")+1) + "pdf";
				newDoc.setPath(path);
				OKMDocument.getInstance().create(null, newDoc, is);
			}
			is.close();
			
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_IO), e.getMessage());
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_NumberFormat), e.getMessage());
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Document), e.getMessage());
		} catch (EvalError e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Eval), e.getMessage());
		} catch (UnsupportedMimeTypeException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_UnsupportedMimeType), e.getMessage());
		} catch (FileSizeExceededException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_FileSizeExceeded), e.getMessage());
		} catch (UserQuotaExceededException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_QuotaExceed), e.getMessage());
		} catch (VirusDetectedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Virus), e.getMessage());
		} catch (ItemExistsException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (VersionException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStampService, ErrorCode.CAUSE_Version), e.getMessage());
		} finally {
			// Cleaning temp files
			tmp.delete();
			tmpPdf.delete();
			tmpStampPdf.delete();
		}
	}
}
	