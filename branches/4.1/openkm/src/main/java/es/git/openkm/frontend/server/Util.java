/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMPropertyGroup;
import es.git.openkm.bean.Bookmark;
import es.git.openkm.bean.DashboardStatsDocumentResult;
import es.git.openkm.bean.DashboardStatsFolderResult;
import es.git.openkm.bean.DashboardStatsMailResult;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.FormField;
import es.git.openkm.bean.Lock;
import es.git.openkm.bean.Mail;
import es.git.openkm.bean.MetaData;
import es.git.openkm.bean.Note;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.QueryParams;
import es.git.openkm.bean.QueryResult;
import es.git.openkm.bean.Version;
import es.git.openkm.bean.form.Button;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.bean.form.Input;
import es.git.openkm.bean.form.Option;
import es.git.openkm.bean.form.Select;
import es.git.openkm.bean.form.TextArea;
import es.git.openkm.bean.workflow.Comment;
import es.git.openkm.bean.workflow.ProcessDefinition;
import es.git.openkm.bean.workflow.ProcessInstance;
import es.git.openkm.bean.workflow.TaskInstance;
import es.git.openkm.bean.workflow.Token;
import es.git.openkm.bean.workflow.Transition;
import es.git.openkm.core.Config;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.bean.GWTBookmark;
import es.git.openkm.frontend.client.bean.GWTButton;
import es.git.openkm.frontend.client.bean.GWTComment;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsFolderResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsMailResult;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTFormElement;
import es.git.openkm.frontend.client.bean.GWTFormField;
import es.git.openkm.frontend.client.bean.GWTInput;
import es.git.openkm.frontend.client.bean.GWTLock;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTMetaData;
import es.git.openkm.frontend.client.bean.GWTNote;
import es.git.openkm.frontend.client.bean.GWTOption;
import es.git.openkm.frontend.client.bean.GWTProcessDefinition;
import es.git.openkm.frontend.client.bean.GWTProcessInstance;
import es.git.openkm.frontend.client.bean.GWTPropertyGroup;
import es.git.openkm.frontend.client.bean.GWTPropertyParams;
import es.git.openkm.frontend.client.bean.GWTQueryParams;
import es.git.openkm.frontend.client.bean.GWTQueryResult;
import es.git.openkm.frontend.client.bean.GWTSelect;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;
import es.git.openkm.frontend.client.bean.GWTTextArea;
import es.git.openkm.frontend.client.bean.GWTToken;
import es.git.openkm.frontend.client.bean.GWTTransition;
import es.git.openkm.frontend.client.bean.GWTVersion;
import es.git.openkm.frontend.client.bean.GWTWorkflowComment;

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
		log.debug("copy("+bookmark+")");
		GWTBookmark gWTBookmark = new GWTBookmark();
		
		if (bookmark != null) {
			gWTBookmark.setName(bookmark.getName());
			gWTBookmark.setPath(bookmark.getPath());
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
		log.debug("getParent("+path+")");
		int lastSlash = path.lastIndexOf('/');
		String ret = (lastSlash > 0)?path.substring(0, lastSlash):"";
		log.debug("getParent: "+ret);
		return ret;	
	}

	/**
	 * Get item name from path.
	 * 
	 * @param path The complete item path.
	 * @return The name of the item.
	 */
	public static String getName(String path) {
		log.debug("getName("+path+")");
		String ret = path.substring(path.lastIndexOf('/')+1);
		log.debug("getName: "+ret);
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
		
		params.setContent(gWTParams.getContent());
		params.setKeywords(gWTParams.getKeywords());
		params.setMimeType(gWTParams.getMimeType());
		params.setName(gWTParams.getName());
		params.setProperties(gWTParams.getSearchProperties());
		params.setPath(gWTParams.getPath());
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
	 * Copy the MetaData data to GWTMetaData
	 * 
	 * @param metaData The original MetaData
	 * @return The gwtMetaData object with data values form the original MetaData
	 */
	public static GWTMetaData copy(MetaData metaData) {
		GWTMetaData gwtMetaData = new GWTMetaData();
		
		gwtMetaData.setOrder(metaData.getOrder());
		gwtMetaData.setType(metaData.getType());
		gwtMetaData.setValues(metaData.getValues());
		
		return gwtMetaData;
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
	 * @throws ParseException 
	 */
	public static GWTQueryParams copy(QueryParams params, String token) throws RepositoryException, IOException, ParseException {
		GWTQueryParams gWTParams = new GWTQueryParams();
		
		gWTParams.setContent(params.getContent());
		gWTParams.setKeywords(params.getKeywords());
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
		
		if (params.getLastModifiedFrom()!=null && params.getLastModifiedTo()!=null) {
			gWTParams.setLastModifiedFrom(params.getLastModifiedFrom().getTime());
			gWTParams.setLastModifiedTo(params.getLastModifiedTo().getTime());
		} 
		
		// Sets group name for each property param
		HashMap<String, GWTPropertyParams> finalProperties = new HashMap<String, GWTPropertyParams> ();
		HashMap<String, String> properties = params.getProperties();
		Collection<String> colKeys = properties.keySet();
		
		for (Iterator<String> itKeys = colKeys.iterator(); itKeys.hasNext(); ){
			String key = itKeys.next();
			boolean found = false;
			
			// Obtain all group names
			Collection<PropertyGroup> colGroups = OKMPropertyGroup.getInstance().getAllGroups(token);
			Iterator<PropertyGroup> itGroup = colGroups.iterator();
			while (itGroup.hasNext() && !found) {
				PropertyGroup group = itGroup.next();
				
				// Obtain all metadata values
				Collection<FormElement> metaData = OKMPropertyGroup.getInstance().getPropertyGroupForm(token, group.getName());
				for (Iterator<FormElement> it = metaData.iterator(); it.hasNext();) {
					FormElement formElement = it.next();
					if (formElement.getName().equals(key)) {
						found = true;
						GWTPropertyParams gWTPropertyParams = new GWTPropertyParams();
						gWTPropertyParams.setGrpName(group.getName());
						gWTPropertyParams.setGrpLabel(group.getLabel());
						gWTPropertyParams.setMetaData(Util.copy(formElement));
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
	 * Copy the DashboardStatsDocumentResult data to GWTDashboardStatsDocumentResult
	 * 
	 * @param dsDocumentResult The original DashboardStatsDocumentResult
	 * @return The GWTDashboardStatsDocumentResult object with data values from de original
	 * DashboardStatsDocumentResult
	 */
	public static GWTDashboardStatsDocumentResult copy(DashboardStatsDocumentResult dsDocumentResult) {
		GWTDashboardStatsDocumentResult gwtDashboardStatsDocumentResult = new GWTDashboardStatsDocumentResult();
		
		gwtDashboardStatsDocumentResult.setDocument(copy(dsDocumentResult.getDocument()));
		gwtDashboardStatsDocumentResult.setVisited(dsDocumentResult.isVisited());
		gwtDashboardStatsDocumentResult.setDate(dsDocumentResult.getDate().getTime());
		
		return gwtDashboardStatsDocumentResult;
	}
	
	/**
	 * Copy the DashboardStatsFolderResult data to GWTDashboardStatsFolderResult
	 * 
	 * @param dsFolderResult The original DashboardStatsFolderResult
	 * @return The GWTDashboardStatsFolderResult object with data values from de original
	 * DashboardStatsFolderResult
	 */
	public static GWTDashboardStatsFolderResult copy(DashboardStatsFolderResult dsFolderResult) {
		GWTDashboardStatsFolderResult gwtDashboardStatsFolderResult = new GWTDashboardStatsFolderResult();
		
		gwtDashboardStatsFolderResult.setFolder(copy(dsFolderResult.getFolder()));
		gwtDashboardStatsFolderResult.setVisited(dsFolderResult.isVisited());
		gwtDashboardStatsFolderResult.setDate(dsFolderResult.getDate().getTime());
		
		return gwtDashboardStatsFolderResult;
	}
	
	
	/**
	 * Copy the DashboardStatsMailResult data to GWTDashboardStatsMailResult
	 * 
	 * @param dsMailResult The original DashboardStatsMailResult
	 * @return The GWTDashboardStatsMailResult object with data values from de original
	 * DashboardStatsMailResult
	 */
	public static GWTDashboardStatsMailResult copy(DashboardStatsMailResult dsmailResult) {
		GWTDashboardStatsMailResult gwtDashboardStatsMailResult = new GWTDashboardStatsMailResult();
		
		gwtDashboardStatsMailResult.setMail(copy(dsmailResult.getMail()));
		gwtDashboardStatsMailResult.setVisited(dsmailResult.isVisited());
		gwtDashboardStatsMailResult.setDate(dsmailResult.getDate().getTime());
		
		return gwtDashboardStatsMailResult;
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
		
		gWTTaskInstance.setComments(copy(taskInstance.getComments()));
		
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
		gWTProcessInstance.setVariables(processInstance.getVariables());
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
	 * Copy to FormElement data to GWTFormElemen
	 * @param FormElement the original data
	 * @return The GWTFormElement object with data values from original FormElement
	 */
	public static GWTFormElement copy(FormElement formElement) {
		if (formElement instanceof Button) {
			GWTButton gWTButton = new GWTButton();
			gWTButton.setLabel(formElement.getLabel());
			gWTButton.setValue(formElement.getValue());
			gWTButton.setWidth(formElement.getWidth());
			gWTButton.setHeight(formElement.getHeight());
			gWTButton.setName(((Button) formElement).getName());
			gWTButton.setType(((Button) formElement).getType());
			return gWTButton;
		} else if (formElement instanceof Input) {
			GWTInput gWTInput = new GWTInput();
			gWTInput.setLabel(formElement.getLabel());
			gWTInput.setValue(formElement.getValue());
			gWTInput.setWidth(formElement.getWidth());
			gWTInput.setHeight(formElement.getHeight());
			gWTInput.setName(((Input) formElement).getName());
			gWTInput.setType(((Input) formElement).getType());
			return gWTInput;
		} else if (formElement instanceof Select) {
			GWTSelect gWTselect = new GWTSelect();
			gWTselect.setLabel(formElement.getLabel());
			gWTselect.setValue(formElement.getValue());
			gWTselect.setWidth(formElement.getWidth());
			gWTselect.setHeight(formElement.getHeight());
			gWTselect.setName(((Select) formElement).getName());
			gWTselect.setType(((Select) formElement).getType());
			Collection<GWTOption> options = new ArrayList<GWTOption>();
			for (Iterator<Option> it = ((Select) formElement).getOptions().iterator(); it.hasNext();) {
				options.add(copy(it.next()));
			}
			gWTselect.setOptions(options);
			return gWTselect;
		} else if (formElement instanceof TextArea) {
			GWTTextArea gWTTextArea= new GWTTextArea();
			gWTTextArea.setLabel(formElement.getLabel());
			gWTTextArea.setValue(formElement.getValue());
			gWTTextArea.setWidth(formElement.getWidth());
			gWTTextArea.setHeight(formElement.getHeight());
			gWTTextArea.setName(((TextArea) formElement).getName());
			return gWTTextArea;
		} else {
			return new GWTFormElement();
		}
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
		return gWTOption;
	}
	
	/**
	 * Copy to FormField data to  GWTFormField
	 * @param FormField the original data
	 * @return The GWTFormField object with data values from original FormField
	 */
	public static GWTFormField copy(FormField formField) {
		GWTFormField gWTFormField = new GWTFormField();
		
		gWTFormField.setLabel(formField.getLabel());
		gWTFormField.setName(formField.getName());
		gWTFormField.setType(formField.getType());
		gWTFormField.setValue(formField.getValue());
		
		return gWTFormField;
	}
	
	/**
	 * Copy to Comment data to  GWTComment
	 * @param Comment the original data
	 * @return The GWTTaskInstanceComment object with data values from original TaskInstanceComment
	 */
	public static List<GWTComment> copy(List<Comment> list) {
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
	public static Collection<GWTNote> copy(Collection<Note> commentList) {
		Collection<GWTNote> gWTCommentList = new ArrayList<GWTNote>();
		
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
	 * Copy the Document data to GWTPropertyGroup data.
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
}