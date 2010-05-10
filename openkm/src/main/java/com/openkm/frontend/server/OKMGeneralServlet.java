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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.frontend.client.bean.GWTFileUploadingStatus;
import com.openkm.frontend.client.bean.GWTTestImap;
import com.openkm.frontend.client.service.OKMGeneralService;
import com.openkm.util.MailUtils;


/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMGeneralService"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMGeneralServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMGeneralServlet extends OKMRemoteServiceServlet implements OKMGeneralService {
	private static Logger log = LoggerFactory.getLogger(OKMGeneralServlet.class);
	private static final long serialVersionUID = -879908904295685769L;
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMGeneralService#getFileUploadStatus()
	 */
	public GWTFileUploadingStatus getFileUploadStatus() {
		log.debug("getFileUploadStatus()");
		GWTFileUploadingStatus fus = new GWTFileUploadingStatus(); 
		if (getThreadLocalRequest().getSession().getAttribute(OKMFileUploadServlet.FILE_UPLOAD_STATUS)!=null) {
			FileUploadListener listener = (FileUploadListener)getThreadLocalRequest().getSession().getAttribute(OKMFileUploadServlet.FILE_UPLOAD_STATUS);
			fus.setStarted(true);
			fus.setBytesRead(listener.getBytesRead());
            fus.setContentLength(listener.getContentLength());
            fus.setUploadFinish(listener.isUploadFinish());
            if (listener.getBytesRead()==listener.getContentLength() || listener.isUploadFinish()) {
            	getThreadLocalRequest().getSession().removeAttribute(OKMFileUploadServlet.FILE_UPLOAD_STATUS);
            }
		}
		
		log.debug("getFileUploadStatus:");
		return fus;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMGeneralService#testImapConnection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public GWTTestImap testImapConnection(String host, String user, String password, String imapFolder) {
		log.debug("testImapConnection(host:"+host+",user:"+user+",imapFolder"+imapFolder+")");
		GWTTestImap test = new GWTTestImap();
		
		try {
			test.setError(false);
			MailUtils.testConnection(host, user, password, imapFolder);
		} catch (IOException e) {
			test.setError(true);
			test.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		
		return test;
	}
}