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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jackrabbit.util.ISO8601;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMPropertyGroup;
import com.openkm.api.OKMRepository;
import com.openkm.bean.DashboardDocumentResult;
import com.openkm.bean.DashboardFolderResult;
import com.openkm.bean.DashboardMailResult;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Lock;
import com.openkm.bean.Mail;
import com.openkm.bean.Note;
import com.openkm.bean.PropertyGroup;
import com.openkm.bean.QueryResult;
import com.openkm.bean.Version;
import com.openkm.bean.form.Button;
import com.openkm.bean.form.CheckBox;
import com.openkm.bean.form.FormElement;
import com.openkm.bean.form.Input;
import com.openkm.bean.form.Option;
import com.openkm.bean.form.Select;
import com.openkm.bean.form.TextArea;
import com.openkm.bean.form.Validator;
import com.openkm.bean.workflow.Comment;
import com.openkm.bean.workflow.ProcessDefinition;
import com.openkm.bean.workflow.ProcessInstance;
import com.openkm.bean.workflow.TaskInstance;
import com.openkm.bean.workflow.Token;
import com.openkm.bean.workflow.Transition;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.Bookmark;
import com.openkm.dao.bean.QueryParams;
import com.openkm.dao.bean.UserConfig;
import com.openkm.frontend.client.bean.GWTBookmark;
import com.openkm.frontend.client.bean.GWTButton;
import com.openkm.frontend.client.bean.GWTCheckBox;
import com.openkm.frontend.client.bean.GWTComment;
import com.openkm.frontend.client.bean.GWTDashboardDocumentResult;
import com.openkm.frontend.client.bean.GWTDashboardFolderResult;
import com.openkm.frontend.client.bean.GWTDashboardMailResult;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTInput;
import com.openkm.frontend.client.bean.GWTLock;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.bean.GWTNote;
import com.openkm.frontend.client.bean.GWTOption;
import com.openkm.frontend.client.bean.GWTProcessDefinition;
import com.openkm.frontend.client.bean.GWTProcessInstance;
import com.openkm.frontend.client.bean.GWTPropertyGroup;
import com.openkm.frontend.client.bean.GWTPropertyParams;
import com.openkm.frontend.client.bean.GWTQueryParams;
import com.openkm.frontend.client.bean.GWTQueryResult;
import com.openkm.frontend.client.bean.GWTSelect;
import com.openkm.frontend.client.bean.GWTTaskInstance;
import com.openkm.frontend.client.bean.GWTTextArea;
import com.openkm.frontend.client.bean.GWTToken;
import com.openkm.frontend.client.bean.GWTTransition;
import com.openkm.frontend.client.bean.GWTUserConfig;
import com.openkm.frontend.client.bean.GWTValidator;
import com.openkm.frontend.client.bean.GWTVersion;
import com.openkm.frontend.client.bean.GWTWorkflowComment;

public class Util {
	private static Logger log = LoggerFactory.getLogger(Util.class);
	
	/**
	 * Copy the Document data to GWTDocument data.
	 * 
	 * @param doc The original Document object.
	 * @return A GWTDocument object with the data from 
	 * the original Document.
	 */
	public static GWTDocument copy(Document doc) {
		log.debug("copy("+doc+")");
		GWTDocument gWTDoc = new GWTDocument();
		
		gWTDoc.setKeywords(doc.getKeywords());
		gWTDoc.setMimeType(doc.getMimeType());
		gWTDoc.setName(Util.getName(doc.getPath()));
		gWTDoc.setParent(Util.getParent(doc.getPath()));
		gWTDoc.setParentId(Util.getParent(doc.getPath()));
		gWTDoc.setPath(doc.getPath());
		gWTDoc.setAuthor(doc.getAuthor());
		gWTDoc.setCreated(doc.getCreated().getTime());
		gWTDoc.setLastModified(doc.getLastModified().getTime());
		gWTDoc.setCheckedOut(doc.isCheckedOut());
		gWTDoc.setLocked(doc.isLocked());
		gWTDoc.setSubscribed(doc.isSubscribed());
		gWTDoc.setActualVersion(copy(doc.getActualVersion()));
		gWTDoc.setPermissions(doc.getPermissions());
		gWTDoc.setLockInfo(copy(doc.getLockInfo()));
		gWTDoc.setConvertibleToPdf(doc.isConvertibleToPdf());
		gWTDoc.setConvertibleToSwf(doc.isConvertibleToSwf());
		gWTDoc.setSubscriptors(doc.getSubscriptors());
		gWTDoc.setUuid(doc.getUuid());
		gWTDoc.setNotes(copy(doc.getNotes()));
		
		for (Iterator<Note> it = doc.getNotes().iterator(); it.hasNext() && !gWTDoc.isHasNotes();) {
			Note note = it.next();
			if (!note.getUser().equals(Config.SYSTEM_USER)) {
				gWTDoc.setHasNotes(true);
			}
		}
		
		Set<GWTFolder> categories = new HashSet<GWTFolder>();
		for (Iterator<Folder> it = doc.getCategories().iterator(); it.hasNext();) {
			categories.add(copy(it.next()));
		}
		gWTDoc.setCategories(categories);
		
		log.debug("copy: "+gWTDoc);
		return gWTDoc;
	}
	
	/**
	 * Copy the GWTDocument data to Document data.
	 * 
	 * @param gWTDoc The original GWTDocument object.
	 * @return A Document object with the data form de original GWTDocument
	 */
	public static Document copy(GWTDocument gWTDoc) {
		log.debug("copy("+gWTDoc+")");
		Document doc = new Document();
		Calendar cal = Calendar.getInstance();
		
		doc.setKeywords(gWTDoc.getKeywords());
		doc.setMimeType(gWTDoc.getMimeType());
		doc.setPath(gWTDoc.getPath());
		doc.setAuthor(gWTDoc.getAuthor());
		cal.setTime(gWTDoc.getCreated());
		doc.setCreated(cal);
		cal.setTime(gWTDoc.getLastModified());
		doc.setLastModified(cal);
		doc.setCheckedOut(gWTDoc.isCheckedOut());
		doc.setLocked(gWTDoc.isLocked());
		doc.setActualVersion(Util.copy(gWTDoc.getActualVersion()));
		doc.setPermissions(gWTDoc.getPermissions());
		doc.setSubscribed(gWTDoc.isSubscribed());
		doc.setSubscriptors(gWTDoc.getSubscriptors());
		
		Set <Folder> categories = new HashSet<Folder>();
		for (Iterator<GWTFolder> it = gWTDoc.getCategories().iterator(); it.hasNext();){
			categories.add(copy(it.next()));
		}
		doc.setCategories(categories);
		
		gWTDoc.setActualVersion(copy(doc.getActualVersion()));
		
		log.debug("copy: "+gWTDoc);
		return doc;
	}

	/**
	 * Copy the Folder data to GWTFolder data.
	 * 
	 * @param doc The original Folder object.
	 * @return A GWTFolder object with the data from 
	 * the original Document.
	 */
	public static GWTFolder copy(Folder fld) {
		log.debug("copy("+fld+")");
		GWTFolder gWTFolder = new GWTFolder();
		
		gWTFolder.setUuid(fld.getUuid());
		gWTFolder.setPath(fld.getPath());
		gWTFolder.setParentPath(Util.getParent(fld.getPath()));
		gWTFolder.setName(Util.getName(fld.getPath()));
		gWTFolder.setHasChilds(fld.getHasChilds());
		gWTFolder.setCreated(fld.getCreated().getTime());
		gWTFolder.setPermissions(fld.getPermissions());
		gWTFolder.setAuthor(fld.getAuthor());
		gWTFolder.setSubscribed(fld.isSubscribed());
		gWTFolder.setSubscriptors(fld.getSubscriptors());
		
		log.debug("copy: "+gWTFolder);
		return gWTFolder;
	}	
	
	/**
	 * Copy the GWTFolder data to Folder data.
	 * 
	 * @param doc The original GWTFolder object.
	 * @return A Folder object with the data from 
	 * the original Document.
	 */
	public static Folder copy(GWTFolder fld) {
		log.debug("copy("+fld+")");
		Folder folder = new Folder();
		
		folder.setUuid(fld.getUuid());
		folder.setPath(fld.getPath());
		folder.setHasChilds(fld.getHasChilds());
		Calendar created = Calendar.getInstance();
		created.setTimeInMillis(fld.getCreated().getTime());
		folder.setCreated(created);
		folder.setPermissions(fld.getPermissions());
		folder.setAuthor(fld.getAuthor());
		folder.setSubscribed(fld.isSubscribed());
		folder.setSubscriptors(fld.getSubscriptors());
		
		log.debug("copy: "+folder);
		return folder;
	}
	
	/**
	 * Copy the Version data to GWTVersion data.
	 * 
	 * @param doc The original Version object.
	 * @return A GWTVersion object with the data from 
	 * the original Document.
	 */
	public static GWTVersion copy(Version version) {
		log.debug("copy("+version+")");
		GWTVersion gWTVersion = new GWTVersion();
		
		gWTVersion.setCreated(version.getCreated().getTime());
		gWTVersion.setName(version.getName());
		gWTVersion.setSize(version.getSize());
		gWTVersion.setAuthor(version.getAuthor());
		gWTVersion.setActual(version.isActual());
		gWTVersion.setComment(version.getComment());
		
		log.debug("copy: "+gWTVersion);
		return gWTVersion;
	}
	
	/**
	 * Copy the GWTVersion data to Version data object
	 * 
	 * @param gWTVersion The original GWTVersion
	 * @return A Version object with the data from the original GWTVersion
	 */
	public static Version copy(GWTVersion gWTVersion) {
		log.debug("copy("+gWTVersion+")");
		Version version = new Version();
		Calendar cal = Calendar.getInstance();
		
		version.setName(gWTVersion.getName());
		version.setSize(gWTVersion.getSize());
		version.setAuthor(gWTVersion.getAuthor());
		version.setActual(gWTVersion.isActual());
		cal.setTime(gWTVersion.getCreated());
		version.setCreated(cal);
		version.setComment(gWTVersion.getComment());
		
		log.debug("copy: "+version);
		return version;
	}
	
	/**
	 * Copy the Lock data to GWTLock data.
	 * 
	 * @param doc The original Version object.
	 * @return A GWTLock object with the data from 
	 * the original Lock.
	 */
	public static GWTLock copy(Lock lock) {
		log.debug("copy("+lock+")");
		GWTLock gWTLock = new GWTLock();
		
		if (lock != null) {
			gWTLock.setNodePath(lock.getNodePath());
			gWTLock.setOwner(lock.getOwner());
			gWTLock.setToken(lock.getToken());
		}
		
		log.debug("copy: "+gWTLock);
		return gWTLock;
	}
	
	/**
	 * Copy the Bookmark data to GWTBookmark data.
	 * 
	 * @param bookmark The original Version object.
	 * @return A GWTBookmark object with the data from 
	 * the original Bookmark.
	 */
	public static GWTBookmark copy(Bookmark bookmark) {
		log.debug("copy({})", bookmark);
		GWTBookmark gWTBookmark = new GWTBookmark();
		
		if (bookmark != null) {
			gWTBookmark.setId(bookmark.getId());
			gWTBookmark.setName(bookmark.getName());
			gWTBookmark.setPath(bookmark.getPath());
			gWTBookmark.setUuid(bookmark.getUuid());
			gWTBookmark.setType(bookmark.getType());
		}
		
		log.debug("copy: "+gWTBookmark);
		return gWTBookmark;
	}
	
	/**
	 * Get parent item path from path.
	 * 
	 * @param path The complete item path.
	 * @return The parent item path.
	 */
	public static String getParent(String path) {
		log.debug("getParent({})", path);
		int lastSlash = path.lastIndexOf('/');
		String ret = (lastSlash > 0)?path.substring(0, lastSlash):"";
		log.debug("getParent: {}", ret);
		return ret;	
	}

	/**
	 * Get item name from path.
	 * 
	 * @param path The complete item path.
	 * @return The name of the item.
	 */
	public static String getName(String path) {
		log.debug("getName({})", path);
		String ret = path.substring(path.lastIndexOf('/')+1);
		log.debug("getName: {}", ret);
		return ret;
	}
	
	/**
	 * Copy the gWTparams data to GWTQueryParams data object
	 * 
	 * @param gWTParams The original GWTQueryParams
	 * @return The QueryParams object with the data from de original GWTQueryParams
	 */
	public static QueryParams copy(GWTQueryParams gWTParams) {
		QueryParams params = new QueryParams();
		
		params.setId(gWTParams.getId());
		params.setQueryName(gWTParams.getQueryName());
		params.setContent(gWTParams.getContent());
		String keywords = gWTParams.getKeywords().trim();
		Set<String> tmpKwd = new HashSet<String>();
		if (!keywords.equals("")) {
			String kw[] = keywords.split(" ");
			for (int i=0; i<kw.length; i++) {
				tmpKwd.add(kw[i]);
			}
		}
		params.setKeywords(tmpKwd);
		params.setMimeType(gWTParams.getMimeType());
		params.setName(gWTParams.getName());
		
		Map<String, String> properties = new HashMap<String, String>();
		for (Iterator<String> it = gWTParams.getProperties().keySet().iterator(); it.hasNext();) {
			String key = it.next();
			properties.put(key, gWTParams.getProperties().get(key).getValue());
		}
		params.setProperties(properties);
		
		params.setPath(gWTParams.getPath());
		String categories = gWTParams.getCategoryUuid().trim();
		Set<String> tmpCat = new HashSet<String>();
		if (!categories.equals("")) {
			tmpCat.add(categories);
		}
		params.setCategories(tmpCat);
		params.setAuthor(gWTParams.getAuthor());
		Calendar lastModifiedFrom = Calendar.getInstance();
		Calendar lastModifiedTo = Calendar.getInstance();
		if (gWTParams.getLastModifiedFrom()!= null && gWTParams.getLastModifiedTo()!=null) {
			lastModifiedFrom.setTime(gWTParams.getLastModifiedFrom());
			lastModifiedTo.setTime(gWTParams.getLastModifiedTo());
		} else {
			lastModifiedFrom = null;
			lastModifiedTo = null;
		}
		params.setLastModifiedFrom(lastModifiedFrom);
		params.setLastModifiedTo(lastModifiedTo);
		params.setDashboard(gWTParams.isDashboard());
		params.setDomain(gWTParams.getDomain());
		params.setSubject(gWTParams.getSubject());
		params.setTo(gWTParams.getTo());
		params.setFrom(gWTParams.getFrom());
		params.setOperator(gWTParams.getOperator());
		
		return params;
	}
	
	/**
	 * Copy the QueryResult data to GWTQueryResult
	 * 
	 * @param queryResult The original QueryResult
	 * @return The GWTQueryResult object with data values from de origina QueryResult
	 */
	public static GWTQueryResult copy(QueryResult queryResult) {
		GWTQueryResult gwtQueryResult = new GWTQueryResult();
		
		if (queryResult.getDocument()!=null) {
			gwtQueryResult.setDocument(copy(queryResult.getDocument())); 
			gwtQueryResult.getDocument().setAttachment(false);
		} else if (queryResult.getFolder()!=null) {
			gwtQueryResult.setFolder(copy(queryResult.getFolder()));
		} else if (queryResult.getMail()!=null) {
			gwtQueryResult.setMail(copy(queryResult.getMail()));
		} else if (queryResult.getAttachment()!=null) {
			gwtQueryResult.setAttachment(copy(queryResult.getAttachment()));
			gwtQueryResult.getAttachment().setAttachment(true);
		}
		gwtQueryResult.setScore(queryResult.getScore());
		
		return gwtQueryResult;
	}
	
	/**
	 * Copy the QueryParams data to GWTQueryParams data object
	 * 
	 * @param GWTQueryParams The original QueryParams
	 * @return The GWTQueryParams object with the data from de original QueryParams 
	 * @throws RepositoryException 
	 * @throws IOException 
	 * @throws PathNotFoundException 
	 * @throws ParseException 
	 */
	public static GWTQueryParams copy(QueryParams params) throws RepositoryException, 
			IOException, PathNotFoundException, ParseException, DatabaseException {
		GWTQueryParams gWTParams = new GWTQueryParams();
		
		gWTParams.setId(params.getId());
		gWTParams.setQueryName(params.getQueryName());
		gWTParams.setContent(params.getContent());
		String tmp = "";
		for (Iterator<String> itKwd = params.getKeywords().iterator(); itKwd.hasNext(); ) {
			tmp += itKwd.next() + " "; 
		}
		gWTParams.setKeywords(tmp);
		gWTParams.setMimeType(params.getMimeType());
		gWTParams.setName(params.getName());
		gWTParams.setPath(params.getPath());
		gWTParams.setAuthor(params.getAuthor());
		gWTParams.setDashboard(params.isDashboard());
		gWTParams.setDomain(params.getDomain());
		gWTParams.setSubject(params.getSubject());
		gWTParams.setFrom(params.getFrom());
		gWTParams.setTo(params.getTo());
		gWTParams.setOperator(params.getOperator());
		Iterator<String> itCat = params.getCategories().iterator();
		if (itCat.hasNext()) {
			gWTParams.setCategoryUuid(itCat.next());
		}
		
		if (params.getCategories() != null && !params.getCategories().isEmpty()) {
			itCat = params.getCategories().iterator();
			if (itCat.hasNext()) {
				gWTParams.setCategoryPath(OKMRepository.getInstance().getPath(null, itCat.next()));
			}
		}
		
		if (params.getLastModifiedFrom() != null && params.getLastModifiedTo() != null) {
			gWTParams.setLastModifiedFrom(params.getLastModifiedFrom().getTime());
			gWTParams.setLastModifiedTo(params.getLastModifiedTo().getTime());
		} 
		
		// Sets group name for each property param
		Map<String, GWTPropertyParams> finalProperties = new HashMap<String, GWTPropertyParams> ();
		Map<String, String> properties = params.getProperties();
		Collection<String> colKeys = properties.keySet();
		
		for (Iterator<String> itKeys = colKeys.iterator(); itKeys.hasNext(); ){
			String key = itKeys.next();
			boolean found = false;
			
			// Obtain all group names
			Collection<PropertyGroup> colGroups = OKMPropertyGroup.getInstance().getAllGroups(null);
			Iterator<PropertyGroup> itGroup = colGroups.iterator();
			while (itGroup.hasNext() && !found) {
				PropertyGroup group = itGroup.next();
				
				// Obtain all metadata values
				Collection<FormElement> metaData = OKMPropertyGroup.getInstance().getPropertyGroupForm(null, group.getName());
				for (Iterator<FormElement> it = metaData.iterator(); it.hasNext();) {
					FormElement formElement = it.next();
					if (formElement.getName().equals(key)) {
						found = true;
						GWTPropertyParams gWTPropertyParams = new GWTPropertyParams();
						gWTPropertyParams.setGrpName(group.getName());
						gWTPropertyParams.setGrpLabel(group.getLabel());
						gWTPropertyParams.setFormElement(Util.copy(formElement));
						gWTPropertyParams.setValue(properties.get(key));
						finalProperties.put(key,gWTPropertyParams);
						break;
					}
				}
			}
		}
		
		gWTParams.setProperties(finalProperties);
		
		return gWTParams;
	}

	/**
	 * Copy the DashboardDocumentResult data to GWTDashboardDocumentResult
	 * 
	 * @param dsDocumentResult The original DashboardDocumentResult
	 * @return The GWTDashboardDocumentResult object with data values from the original
	 * DashboardDocumentResult
	 */
	public static GWTDashboardDocumentResult copy(DashboardDocumentResult dsDocumentResult) {
		GWTDashboardDocumentResult gwtDashboardDocumentResult = new GWTDashboardDocumentResult();
		
		gwtDashboardDocumentResult.setDocument(copy(dsDocumentResult.getDocument()));
		gwtDashboardDocumentResult.setVisited(dsDocumentResult.isVisited());
		gwtDashboardDocumentResult.setDate(dsDocumentResult.getDate().getTime());
		
		return gwtDashboardDocumentResult;
	}
	
	/**
	 * Copy the DashboardFolderResult data to GWTDashboardFolderResult
	 * 
	 * @param dsFolderResult The original DashboardFolderResult
	 * @return The GWTDashboardFolderResult object with data values from the original
	 * DashboardFolderResult
	 */
	public static GWTDashboardFolderResult copy(DashboardFolderResult dsFolderResult) {
		GWTDashboardFolderResult gwtDashboardFolderResult = new GWTDashboardFolderResult();
		
		gwtDashboardFolderResult.setFolder(copy(dsFolderResult.getFolder()));
		gwtDashboardFolderResult.setVisited(dsFolderResult.isVisited());
		gwtDashboardFolderResult.setDate(dsFolderResult.getDate().getTime());
		
		return gwtDashboardFolderResult;
	}
	
	
	/**
	 * Copy the DashboardMailResult data to GWTDashboardMailResult
	 * 
	 * @param dsMailResult The original DashboardMailResult
	 * @return The GWTDashboardMailResult object with data values from the original
	 * DashboardMailResult
	 */
	public static GWTDashboardMailResult copy(DashboardMailResult dsmailResult) {
		GWTDashboardMailResult gwtDashboardMailResult = new GWTDashboardMailResult();
		
		gwtDashboardMailResult.setMail(copy(dsmailResult.getMail()));
		gwtDashboardMailResult.setVisited(dsmailResult.isVisited());
		gwtDashboardMailResult.setDate(dsmailResult.getDate().getTime());
		
		return gwtDashboardMailResult;
	}
	
	
	/**
	 * Copy to ProcessDefinition data to  GWTProcessDefinition
	 * @param ProcessDefinition the original data
	 * @return The GWTProcessDefinition object with data values from original ProcessDefinition
	 */
	public static GWTProcessDefinition copy(ProcessDefinition processDefinition) {
		GWTProcessDefinition gWTProcessDefinition = new GWTProcessDefinition();
		
		gWTProcessDefinition.setId(processDefinition.getId());
		gWTProcessDefinition.setName(processDefinition.getName());
		gWTProcessDefinition.setVersion(processDefinition.getVersion());
		gWTProcessDefinition.setDescription(processDefinition.getDescription());
		
		return gWTProcessDefinition;
	}
	
	/**
	 * Copy to TaskInstance data to  GWTTaskInstance
	 * @param TaskInstance the original data
	 * @return The GWTTaskInstance object with data values from original TaskInstance
	 */
	public static GWTTaskInstance copy(TaskInstance taskInstance) {
		GWTTaskInstance gWTTaskInstance = new GWTTaskInstance();
		
		gWTTaskInstance.setActorId(taskInstance.getActorId());
		gWTTaskInstance.setCreate(taskInstance.getCreate().getTime());
		gWTTaskInstance.setId(taskInstance.getId());
		gWTTaskInstance.setName(taskInstance.getName());
		gWTTaskInstance.setProcessInstance(copy(taskInstance.getProcessInstance()));
		gWTTaskInstance.setDescription(taskInstance.getDescription());
		
		if (taskInstance.getDueDate() != null) {
			gWTTaskInstance.setDueDate(taskInstance.getDueDate().getTime());
		}
		
		if (taskInstance.getStart() != null) {
			gWTTaskInstance.setStart(taskInstance.getStart().getTime());
		}
		
		gWTTaskInstance.setComments(copyComments(taskInstance.getComments()));
		
		return gWTTaskInstance;
	}
	
	/**
	 * Copy to ProcessInstance data to  GWTProcessInstance
	 * @param ProcessInstance the original data
	 * @return The GWTProcessInstance object with data values from original ProcessInstance
	 */
	public static GWTProcessInstance copy(ProcessInstance processInstance) {
		GWTProcessInstance gWTProcessInstance = new GWTProcessInstance();
		
		gWTProcessInstance.setEnded(processInstance.isEnded());
		gWTProcessInstance.setId(processInstance.getId());
		gWTProcessInstance.setProcessDefinition(copy(processInstance.getProcessDefinition()));
		gWTProcessInstance.setSuspended(processInstance.isSuspended());
		Map<String, Object> variables = new HashMap<String, Object>();
		for (Iterator<String> it = processInstance.getVariables().keySet().iterator(); it.hasNext();) {
			String key = it.next();
			Object obj = processInstance.getVariables().get(key);
			if (obj instanceof FormElement ) {
				variables.put(key, copy((FormElement) obj));
			} else {
				variables.put(key, obj);
			}	
		}
		gWTProcessInstance.setVariables(variables);
		gWTProcessInstance.setVersion(processInstance.getVersion());
		gWTProcessInstance.setStart(processInstance.getStart().getTime());
		gWTProcessInstance.setKey(processInstance.getKey());
		gWTProcessInstance.setRootToken(copy(processInstance.getRootToken()));
		processInstance.getRootToken();
		

		
		return gWTProcessInstance;
	}
	
	/**
	 * Copy to Token data to GWTToken
	 * @param FormElement the original data
	 * @return The GWTToken object with data values from original Token
	 */
	public static GWTToken copy(Token token) {
		GWTToken gWTToken = new GWTToken();
		Collection<GWTTransition> availableTransitions = new ArrayList<GWTTransition>();
		for (Iterator<Transition> it = token.getAvailableTransitions().iterator(); it.hasNext();) {
			availableTransitions.add(copy(it.next()));
		}
		gWTToken.setAvailableTransitions(availableTransitions);
		Collection<GWTWorkflowComment> comments = new ArrayList<GWTWorkflowComment>();
		for (Iterator<Comment> it = token.getComments().iterator(); it.hasNext();) {
			comments.add(copy(it.next()));
		}
		gWTToken.setComments(comments);
		if (token.getEnd()!=null) {
			gWTToken.setEnd(token.getEnd().getTime());
		}
		gWTToken.setId(token.getId());
		gWTToken.setName(token.getName());
		gWTToken.setNode(token.getNode());
		if (token.getParent()!=null) {
			gWTToken.setParent(copy(token.getParent()));
		} 
		
		if (token.getProcessInstance()!=null) {
			gWTToken.setProcessInstance(copy(token.getProcessInstance()));
		}
		gWTToken.setStart(token.getStart().getTime());
		gWTToken.setSuspended(token.isSuspended());
		
		return gWTToken;
	}
	
	/**
	 * Copy to Token data to GWTTransition
	 * @param Transition the original data
	 * @return The GWTTransition object with data values from original Transition
	 */
	public static GWTTransition copy(Transition transition) {
		GWTTransition gWTTransition = new GWTTransition();
		gWTTransition.setFrom(transition.getFrom());
		gWTTransition.setId(transition.getId());
		gWTTransition.setName(transition.getName());
		gWTTransition.setTo(transition.getTo());
		
		return gWTTransition;
	}
	
	/**
	 * Copy to Comment data to GWTWorkFlowComment
	 * @param Transition the original data
	 * @return The GWTWorkFlowComment object with data values from original Comment
	 */
	public static GWTWorkflowComment copy(Comment comment) {
		GWTWorkflowComment gWTComment = new GWTWorkflowComment();
		gWTComment.setActorId(comment.getActorId());
		gWTComment.setMessage(comment.getMessage());
		gWTComment.setTime(comment.getTime().getTime());
		
		return gWTComment;
	}
	
	/**
	 * Copy to Validator data to GWTValidator
	 * @param Validator the original data
	 * @return The GWTValidator object with data values from original Validator
	 */
	public static List<GWTValidator> copyValidators(List<Validator> validators) {
		List<GWTValidator> gwtValidatorsList = new ArrayList<GWTValidator>();
		for (Iterator<Validator> it = validators.iterator(); it.hasNext();) {
			Validator validator = it.next();
			GWTValidator valid = new GWTValidator();
			valid.setParameter(validator.getParameter());
			valid.setType(validator.getType());
		}
		
		return gwtValidatorsList;
	}
	
	/**
	 * Copy to FormElement data to GWTFormElemen
	 * @param FormElement the original data
	 * @return The GWTFormElement object with data values from original FormElement
	 */
	public static GWTFormElement copy(FormElement formElement) {
		if (formElement instanceof Button) {
			GWTButton gWTButton = new GWTButton();
			gWTButton.setLabel(formElement.getLabel());
			gWTButton.setWidth(formElement.getWidth());
			gWTButton.setHeight(formElement.getHeight());
			gWTButton.setName(((Button) formElement).getName());
			gWTButton.setValue(((Button) formElement).getValue());
			gWTButton.setType(((Button) formElement).getType());
			return gWTButton;
		} else if (formElement instanceof Input) {
			GWTInput gWTInput = new GWTInput();
			gWTInput.setLabel(formElement.getLabel());
			gWTInput.setWidth(formElement.getWidth());
			gWTInput.setHeight(formElement.getHeight());
			gWTInput.setName(((Input) formElement).getName());
			if (((Input) formElement).getType().equals(Input.TYPE_TEXT) || 
				((Input) formElement).getType().equals(Input.TYPE_LINK)) {
				gWTInput.setValue(((Input) formElement).getValue());
			} else if (((Input) formElement).getType().equals(Input.TYPE_DATE)) {
				if (!((Input) formElement).getValue().equals("")) {
					gWTInput.setDate(ISO8601.parse(((Input) formElement).getValue()).getTime());
				}
			}
			gWTInput.setType(((Input) formElement).getType());
			gWTInput.setValidators(copyValidators(((Input) formElement).getValidators()));
			return gWTInput;
		} else if (formElement instanceof CheckBox) {
			GWTCheckBox gWTCheckbox = new GWTCheckBox();
			gWTCheckbox.setLabel(formElement.getLabel());
			gWTCheckbox.setName(((CheckBox) formElement).getName());
			gWTCheckbox.setValue(((CheckBox) formElement).getValue());
			gWTCheckbox.setValidators(copyValidators(((CheckBox) formElement).getValidators()));
			return gWTCheckbox;
		} else if (formElement instanceof Select) {
			GWTSelect gWTselect = new GWTSelect();
			gWTselect.setLabel(formElement.getLabel());
			gWTselect.setWidth(formElement.getWidth());
			gWTselect.setHeight(formElement.getHeight());
			gWTselect.setName(((Select) formElement).getName());
			gWTselect.setType(((Select) formElement).getType());
			List<GWTOption> options = new ArrayList<GWTOption>();
			for (Iterator<Option> it = ((Select) formElement).getOptions().iterator(); it.hasNext();) {
				options.add(copy(it.next()));
			}
			gWTselect.setOptions(options);
			gWTselect.setValidators(copyValidators(((Select) formElement).getValidators()));
			return gWTselect;
		} else if (formElement instanceof TextArea) {
			GWTTextArea gWTTextArea= new GWTTextArea();
			gWTTextArea.setLabel(formElement.getLabel());
			gWTTextArea.setWidth(formElement.getWidth());
			gWTTextArea.setHeight(formElement.getHeight());
			gWTTextArea.setName(((TextArea) formElement).getName());
			gWTTextArea.setValue(((TextArea) formElement).getValue());
			gWTTextArea.setValidators(copyValidators(((TextArea) formElement).getValidators()));
			return gWTTextArea;
		} else {
			return new GWTFormElement();
		}
	}
	
	/**
	 * Copy to GWTFormElement data to FormElement
	 * @param GWTFormElement the original data
	 * @return The FormElement object with data values from original GWTFormElement
	 */
	public static FormElement copy(GWTFormElement formElement) {
		if (formElement instanceof GWTButton) {
			Button button = new Button();
			button.setName(((GWTButton) formElement).getName());
			button.setValue(((GWTButton) formElement).getValue());
			button.setType(((GWTButton) formElement).getType());
			return button;
		} else if (formElement instanceof GWTInput) {
			Input input = new Input();
			input.setName(((GWTInput) formElement).getName());
			if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_TEXT) || 
					((GWTInput) formElement).getType().equals(GWTInput.TYPE_LINK)) {
				input.setValue(((GWTInput) formElement).getValue());
			} else if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_DATE)) {
				if (((GWTInput) formElement).getDate()!=null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(((GWTInput) formElement).getDate());
					input.setValue(ISO8601.format(cal));
				}
			} 
			input.setType(((GWTInput) formElement).getType());
			return input;
		} else if (formElement instanceof GWTCheckBox) {
			CheckBox checkbox = new CheckBox();
			checkbox.setLabel(formElement.getLabel());
			checkbox.setName(((GWTCheckBox) formElement).getName());
			checkbox.setValue(((GWTCheckBox) formElement).getValue());
			return checkbox;
		} else if (formElement instanceof GWTSelect) {
			Select gWTselect = new Select();
			gWTselect.setName(((GWTSelect) formElement).getName());
			gWTselect.setType(((GWTSelect) formElement).getType());
			List<Option> options = new ArrayList<Option>();
			for (Iterator<GWTOption> it = ((GWTSelect) formElement).getOptions().iterator(); it.hasNext();) {
				options.add(copy(it.next()));
			}
			gWTselect.setOptions(options);
			return gWTselect;
		} else if (formElement instanceof GWTTextArea) {
			TextArea gWTTextArea= new TextArea();
			gWTTextArea.setName(((GWTTextArea) formElement).getName());
			gWTTextArea.setValue(((GWTTextArea) formElement).getValue());
			return gWTTextArea;
		} else {
			return new FormElement();
		}
	}
	
	/**
	 * Copy to GWTOption data to  Option
	 * @param GWTOption the original data
	 * @return The Option object with data values from original GWTOption
	 */
	public static Option copy(GWTOption gWTOption) {
		Option option = new Option();
		option.setLabel(gWTOption.getLabel());
		option.setValue(gWTOption.getValue());
		option.setSelected(gWTOption.isSelected());
		return option;
	}
	
	/**
	 * Copy to Option data to  GWTOption
	 * @param Option the original data
	 * @return The GWTOption object with data values from original Option
	 */
	public static GWTOption copy(Option option) {
		GWTOption gWTOption = new GWTOption();
		gWTOption.setLabel(option.getLabel());
		gWTOption.setValue(option.getValue());
		gWTOption.setSelected(option.isSelected());
		return gWTOption;
	}
	
	/**
	 * Copy to Comment data to  GWTComment
	 * @param Comment the original data
	 * @return The GWTTaskInstanceComment object with data values from original TaskInstanceComment
	 */
	public static List<GWTComment> copyComments(List<Comment> list) {
		List<GWTComment> al = new ArrayList<GWTComment>();
		GWTComment gWTComment;
		
		for (Iterator<Comment> it = list.iterator(); it.hasNext();) {
			Comment comment = it.next();
			gWTComment = new GWTComment();
		
			gWTComment.setActorId(comment.getActorId());
			gWTComment.setMessage(comment.getMessage());
			gWTComment.setTime(comment.getTime().getTime());
			al.add(gWTComment);
		}
		
		return al;
	}
	
	/**
	 * Copy Note data to GWTNote
	 * @param Note the original data
	 * @return The GWTNote object with data values from original Note
	 */
	public static List<GWTNote> copy(List<Note> commentList) {
		List<GWTNote> gWTCommentList = new ArrayList<GWTNote>();
		
		for (Iterator<Note> it = commentList.iterator(); it.hasNext();) {
			Note documentComment = it.next();
			GWTNote gWTNote = new GWTNote();
			
			gWTNote.setDate(documentComment.getDate().getTime());
			gWTNote.setText(documentComment.getText());
			gWTNote.setUser(documentComment.getUser());
			
			gWTCommentList.add(gWTNote);
		}
		
		return gWTCommentList;
	}
	
	/**
	 * Copy the Mail data to GWTMail data.
	 * 
	 * @param mail The original Mail object.
	 * @return A GWTMail object with the data from 
	 * the original Mail.
	 */
	public static GWTMail copy(Mail mail) {
		log.debug("copy("+mail+")");
		GWTMail gWTMail = new GWTMail();
		List<GWTDocument> attachList = new ArrayList<GWTDocument>();
		
		for (Iterator<Document> it = mail.getAttachments().iterator(); it.hasNext();) {
			attachList.add(copy(it.next()));
		}
		gWTMail.setAttachments(attachList);
		gWTMail.setBcc(mail.getBcc());
		gWTMail.setCc(mail.getCc());
		gWTMail.setContent(mail.getContent());
		gWTMail.setFrom(mail.getFrom());
		gWTMail.setPath(mail.getPath());
		gWTMail.setPermissions(mail.getPermissions());
		gWTMail.setReceivedDate(mail.getReceivedDate().getTime());
		gWTMail.setReply(mail.getReply());
		gWTMail.setSentDate(mail.getSentDate().getTime());
		gWTMail.setSize(mail.getSize());
		gWTMail.setSubject(mail.getSubject());
		gWTMail.setTo(mail.getTo());
		gWTMail.setUuid(mail.getUuid());
		gWTMail.setParent(Util.getParent(mail.getPath()));
		gWTMail.setMimeType(mail.getMimeType());
		
		log.debug("copy: "+gWTMail);
		return gWTMail;
	}
	
	/**
	 * Copy the PropertyGroup data to GWTPropertyGroup data.
	 * 
	 * @param doc The original PropertyGroup object.
	 * @return A GWTPropertyGroup object with the data from 
	 * the original PropertyGroup.
	 */
	public static GWTPropertyGroup copy(PropertyGroup property) {
		GWTPropertyGroup gWTPropertyGroup = new GWTPropertyGroup();
		
		gWTPropertyGroup.setLabel(property.getLabel());
		gWTPropertyGroup.setName(property.getName());
		
		return gWTPropertyGroup;
	}
	
	/**
	 * Copy the UserConfig data to GWTUserConfig data.
	 * 
	 * @param doc The original UserConfig object.
	 * @return A GWTUserConfig object with the data from 
	 * the original UserConfig.
	 */
	public static GWTUserConfig copy(UserConfig userCopy) {
		GWTUserConfig gWTUserConfig = new GWTUserConfig();
		
		gWTUserConfig.setHomePath(userCopy.getHomePath());
		gWTUserConfig.setHomeType(userCopy.getHomeType());
		gWTUserConfig.setHomeUuid(userCopy.getHomeUuid());
		gWTUserConfig.setUser(userCopy.getUser());
		
		return gWTUserConfig;
	}
}
