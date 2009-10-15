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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMDashboard;
import es.git.openkm.bean.DashboardStatsDocumentResult;
import es.git.openkm.bean.DashboardStatsFolderResult;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsFolderResult;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMDashboardService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMDashboardServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMDashboardServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMDashboardServlet extends OKMRemoteServiceServlet implements OKMDashboardService {

	private static Logger log = LoggerFactory.getLogger(OKMDashboardServlet.class);
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserLockedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserLockedDocuments() throws OKMException {
		log.debug("getUserLockedDocuments()");
		List<GWTDashboardStatsDocumentResult> lockList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			Collection<DashboardStatsDocumentResult> col = OKMDashboard.getInstance().getUserLockedDocuments(token);
			for (Iterator<DashboardStatsDocumentResult> it = col.iterator(); it.hasNext();) {		
				DashboardStatsDocumentResult documentResult = it.next();
				GWTDashboardStatsDocumentResult documentResultClient = Util.copy(documentResult);

				log.debug("DocumentResult: "+documentResult+" -> DocumentResultClient: "+documentResultClient);
				lockList.add(documentResultClient);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserLockedDocuments:"+lockList);
		return lockList;
	}
	

	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserCheckedOutDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserCheckedOutDocuments() throws OKMException {
		log.debug("getUserCheckedOutDocuments()");
		List<GWTDashboardStatsDocumentResult> chekoutList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			Collection<DashboardStatsDocumentResult> col = OKMDashboard.getInstance().getUserCheckedOutDocuments(token);
			for (Iterator<DashboardStatsDocumentResult> it = col.iterator(); it.hasNext();) {
				DashboardStatsDocumentResult documentResult = it.next();
				GWTDashboardStatsDocumentResult documentResultClient = Util.copy(documentResult);

				log.debug("DocumentResult: "+documentResult+" -> DocumentResultClient: "+documentResultClient);
				chekoutList.add(documentResultClient);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserCheckedOutDocuments:"+chekoutList);
		return chekoutList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserLastModifiedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserLastModifiedDocuments() throws OKMException {
		log.debug("getUserLastModifiedDocuments()");
		List<GWTDashboardStatsDocumentResult> lastModifiedList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			Collection<DashboardStatsDocumentResult> col = OKMDashboard.getInstance().getUserLastModifiedDocuments(token);
			for (Iterator<DashboardStatsDocumentResult> it = col.iterator(); it.hasNext();) {		
				DashboardStatsDocumentResult documentResult = it.next();
				GWTDashboardStatsDocumentResult documentResultClient = Util.copy(documentResult);

				log.debug("DocumentResult: "+documentResult+" -> DocumentResultClient: "+documentResultClient);
				lastModifiedList.add(documentResultClient);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserLastModifiedDocuments:"+lastModifiedList);
		return lastModifiedList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserSubscribedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserSubscribedDocuments() throws OKMException {
		log.debug("getUserSubscribedDocuments()");
		List<GWTDashboardStatsDocumentResult> subscribedList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			Collection<DashboardStatsDocumentResult> col = OKMDashboard.getInstance().getUserSubscribedDocuments(token);
			for (Iterator<DashboardStatsDocumentResult> it = col.iterator(); it.hasNext();) {		
				DashboardStatsDocumentResult documentResult = it.next();
				GWTDashboardStatsDocumentResult documentResultClient = Util.copy(documentResult);

				log.debug("DocumentResult: "+documentResult+" -> DocumentResultClient: "+documentResultClient);
				subscribedList.add(documentResultClient);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserSubscribedDocuments:"+subscribedList);
		return subscribedList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserLastUploadedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserLastUploadedDocuments() throws OKMException {
		log.debug("getUserLastUploadedDocuments()");
		List<GWTDashboardStatsDocumentResult> lastUploadedList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			Collection<DashboardStatsDocumentResult> col = OKMDashboard.getInstance().getUserLastUploadedDocuments(token);
			for (Iterator<DashboardStatsDocumentResult> it = col.iterator(); it.hasNext();) {		
				DashboardStatsDocumentResult documentResult = it.next();
				GWTDashboardStatsDocumentResult documentResultClient = Util.copy(documentResult);

				log.debug("DocumentResult: "+documentResult+" -> DocumentResultClient: "+documentResultClient);
				lastUploadedList.add(documentResultClient);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserLastUploadedDocuments:"+lastUploadedList);
		return lastUploadedList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserSubscribedFolders()
	 */
	public List<GWTDashboardStatsFolderResult> getUserSubscribedFolders() throws OKMException {
		log.debug("getUserSubscribedFolders()");
		List<GWTDashboardStatsFolderResult> subscribedList = new ArrayList<GWTDashboardStatsFolderResult>();
		String token = getToken();
		
		try {
			Collection<DashboardStatsFolderResult> col = OKMDashboard.getInstance().getUserSubscribedFolders(token);
			for (Iterator<DashboardStatsFolderResult> it = col.iterator(); it.hasNext();) {		
				DashboardStatsFolderResult folderResult = it.next();
				GWTDashboardStatsFolderResult folderResultClient = Util.copy(folderResult);

				log.debug("FolderResult: "+folderResult+" -> FolderResultClient: "+folderResultClient);
				subscribedList.add(folderResultClient);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserSubscribedFolders:"+subscribedList);
		return subscribedList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserSearchs()
	 */
	public List<String> getUserSearchs() throws OKMException {
		log.debug("getUserSearchs()");
		List<String> searchList = new ArrayList<String>();
		String token = getToken();
		
		try {
			for (Iterator<String> it = OKMDashboard.getInstance().getUserSearchs(token).iterator(); it.hasNext(); ) {
				searchList.add(it.next());
			}
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserSearchs:"+searchList);
		return searchList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#find(java.lang.String)
	 */
	public List<GWTDashboardStatsDocumentResult> find(String name) throws OKMException {
		log.debug("find("+name+")");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().find(token, name).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("find:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getLastWeekTopDownloadedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments() throws OKMException {
		log.debug("getLastWeekTopDownloadedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getLastWeekTopDownloadedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getLastWeekTopDownloadedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getLastMonthTopDownloadedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments() throws OKMException {
		log.debug("getLastMonthTopDownloadedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getLastMonthTopDownloadedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getLastMonthTopDownloadedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getLastWeekTopModifiedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getLastWeekTopModifiedDocuments() throws OKMException {
		log.debug("getLastWeekTopModifiedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getLastWeekTopModifiedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getLastWeekTopModifiedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getLastMonthTopModifiedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getLastMonthTopModifiedDocuments() throws OKMException {
		log.debug("getLastMonthTopModifiedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getLastMonthTopModifiedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getLastMonthTopModifiedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserLastDownloadedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserLastDownloadedDocuments() throws OKMException {
		log.debug("getUserLastDownloadedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getUserLastDownloadedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserLastDownloadedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getLastModifiedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getLastModifiedDocuments() throws OKMException {
		log.debug("getLastModifiedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getLastModifiedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getLastModifiedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getLastUploadedDocuments()
	 */
	public List<GWTDashboardStatsDocumentResult> getLastUploadedDocuments() throws OKMException {
		log.debug("getLastWeekTopUploadedDocuments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getLastUploadedDocuments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getLastWeekTopUploadedDocuments:"+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#getUserLastImportedMailAttachments()
	 */
	public List<GWTDashboardStatsDocumentResult> getUserLastImportedMailAttachments() throws OKMException {
		log.debug("getUserLastImportedMailAttachments()");
		List<GWTDashboardStatsDocumentResult> docList = new ArrayList<GWTDashboardStatsDocumentResult>();
		String token = getToken();
		
		try {
			for (Iterator<DashboardStatsDocumentResult> it = OKMDashboard.getInstance().getUserLastImportedMailAttachments(token).iterator(); it.hasNext(); ) {
				docList.add(Util.copy(it.next()));
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("getUserLastImportedMailAttachments:"+docList);
		return docList;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDashboardService#visiteNode(java.lang.String, java.lang.String, java.util.Date)
	 */
	public void visiteNode(String source, String node, Date date) throws OKMException {
		log.debug("visiteNode("+source+", "+node+", "+date+")");
		String token = getToken();
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			OKMDashboard.getInstance().visiteNode(token, source, node, cal);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDashboardService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		log.debug("visiteNode: void");
	}
}
