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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.ExtensionDAO;
import com.openkm.dao.bean.Extension;
import com.openkm.dao.bean.MailAccount;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTFileUploadingStatus;
import com.openkm.frontend.client.bean.GWTTestImap;
import com.openkm.frontend.client.extension.general.ErrorCode;
import com.openkm.frontend.client.service.OKMGeneralService;
import com.openkm.util.MailUtils;

/**
 * GeneralServlet
 * 
 * @author jllort
 *
 */
public class GeneralServlet extends OKMRemoteServiceServlet implements OKMGeneralService {
	private static Logger log = LoggerFactory.getLogger(GeneralServlet.class);
	private static final long serialVersionUID = -879908904295685769L;
	
	@Override
	public GWTFileUploadingStatus getFileUploadStatus() {
		log.debug("getFileUploadStatus()");
		GWTFileUploadingStatus fus = new GWTFileUploadingStatus();
		updateSessionManager();
		
		if (getThreadLocalRequest().getSession().getAttribute(FileUploadServlet.FILE_UPLOAD_STATUS)!=null) {
			FileUploadListener listener = (FileUploadListener)getThreadLocalRequest().getSession().getAttribute(FileUploadServlet.FILE_UPLOAD_STATUS);
			fus.setStarted(true);
			fus.setBytesRead(listener.getBytesRead());
            fus.setContentLength(listener.getContentLength());
            fus.setUploadFinish(listener.isUploadFinish());
            if (listener.getBytesRead()==listener.getContentLength() || listener.isUploadFinish()) {
            	getThreadLocalRequest().getSession().removeAttribute(FileUploadServlet.FILE_UPLOAD_STATUS);
            }
		}
		
		log.debug("getFileUploadStatus: {}", fus);
		return fus;
	}
	
	@Override
	public GWTTestImap testImapConnection(String host, String user, String password, String imapFolder)  {
		log.debug("testImapConnection({}, {}, {}, {})", new Object[] { host, user, password, imapFolder });
		GWTTestImap test = new GWTTestImap();
		updateSessionManager();
		
		try {
			test.setError(false);
			MailAccount ma = new MailAccount();
			ma.setMailProtocol(MailAccount.PROTOCOL_IMAP);
			ma.setMailHost(host);
			ma.setMailUser(user);
			ma.setMailPassword(password);
			ma.setMailFolder(imapFolder);
			ma.setMailMarkSeen(true);
			MailUtils.testConnection(ma);
		} catch (IOException e) {
			test.setError(true);
			test.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		
		log.debug("testImapConnection: {}", test);
		return test;
	}
	
	@Override
	public List<String> getEnabledExtensions() throws OKMException {
		List<String> extensions = new ArrayList<String>();
		
		try {
			for (Extension extension :ExtensionDAO.findAll()) {
				extensions.add(extension.getUuid());
			}
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (RepositoryException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMGeneralService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		return extensions;
	}
}
