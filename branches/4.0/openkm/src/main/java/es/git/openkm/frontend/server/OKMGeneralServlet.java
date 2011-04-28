/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.frontend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.frontend.client.bean.GWTFileUploadingStatus;
import es.git.openkm.frontend.client.service.OKMGeneralService;


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
	 * @see es.git.openkm.frontend.client.service.OKMGeneralService#getFileUploadStatus()
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
}