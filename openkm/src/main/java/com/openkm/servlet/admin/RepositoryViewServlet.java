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

package com.openkm.servlet.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.Lock;
import javax.jcr.nodetype.PropertyDefinition;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMFolder;
import com.openkm.api.OKMScripting;
import com.openkm.bean.ContentInfo;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Scripting;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.util.FormatUtil;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * RepositoryView servlet
 */
public class RepositoryViewServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(RepositoryViewServlet.class);
	private static final String[] NODE_TYPE = { "UNDEFINED", "STRING", "BINARY", "LONG", "DOUBLE", 
		"DATE", "BOOLEAN", "NAME", "PATH", "REFERENCE" };
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		String path = WebUtil.getString(request, "path");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("unlock")) {
				unlock(session, path, request, response);
			} else if (action.equals("checkin")) {
				checkin(session, path, request, response);
			} else if (action.equals("remove_content")) {
				removeContent(session, path, request, response);
			} else if (action.equals("remove_current")) {
				path = removeCurrent(session, path, request, response);
			} else if (action.equals("edit")) {
				edit(session, path, request, response);
			} else if (action.equals("save")) {
				save(session, path, request, response);
			} else if (action.equals("set_script")) {
				OKMScripting.getInstance().setScript(path, Config.DEFAULT_SCRIPT);
			} else if (action.equals("remove_script")) {
				OKMScripting.getInstance().removeScript(path);
			}
			
			if (!action.equals("edit")) {
				list(session, path, request, response);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (com.openkm.core.RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Unlock node
	 */
	private void unlock(Session session, String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.info("unlock({}, {}, {}, {})", new Object[] { session, path, request, response });
		Node node = session.getRootNode().getNode(path.substring(1));
		Lock lock = node.getLock();
		
		if (lock.getLockOwner().equals(session.getUserID())) {
			node.unlock();
		} else {
			String lt = JCRUtils.getLockToken(node.getUUID());
			session.addLockToken(lt);
			node.unlock();	
			session.removeLockToken(lt);
		}
		
		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_UNLOCK", path, "");
		log.debug("unlock: void");
	}
	
	/**
	 * Node check-in
	 */
	private void checkin(Session session, String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.info("checkin({}, {}, {}, {})", new Object[] { session, path, request, response });
		Node node = session.getRootNode().getNode(path.substring(1));
		node.checkin();

		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_CHECKIN", path, "");
		log.debug("checkin: void");
	}

	/**
	 * Remove children nodes
	 */
	private void removeContent(Session session, String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.info("removeCurrent({}, {}, {}, {})", new Object[] { session, path, request, response });
		Node node = session.getRootNode().getNode(path.substring(1));
						
		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			Node child = ni.nextNode();
			child.remove();
			node.save();
		}
		
		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_REMOVE_CONTENT", path, "");
		log.debug("removeCurrent: void");
	}
	
	/**
	 * Remove current node and its children
	 */
	private String removeCurrent(Session session, String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.info("removeCurrent({}, {}, {}, {})", new Object[] { session, path, request, response });
		Node node = session.getRootNode().getNode(path.substring(1));
		Node parent = node.getParent();
		String parentPath = parent.getPath();
		node.remove();
		parent.save();
				
		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_REMOVE_CURRENT", path, "");
		log.debug("removeCurrent: {}", path);
		return parentPath;
	}
	
	/**
	 * Edit property
	 */
	private void edit(Session session, String path, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.debug("edit({}, {}, {}, {})", new Object[] { session, path, request, response });
		String property = WebUtil.getString(request, "property");
		ServletContext sc = getServletContext();
		Node node = session.getRootNode().getNode(path.substring(1));
		Property prop = node.getProperty(property);
		boolean multiple = false;
		String value;
		
		if (prop.getDefinition().isMultiple()) {
			value = toString(prop.getValues(), "\n");
			multiple = true;
		} else {
			value = prop.getValue().getString();
		}
		
		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_EDIT", path, property+" : "+value);
		
		sc.setAttribute("node", node);
		sc.setAttribute("property", prop);
		sc.setAttribute("multiple", multiple || prop.getName().equals(Scripting.SCRIPT_CODE));
		sc.setAttribute("value", value);
		sc.getRequestDispatcher("/admin/repository_edit.jsp").forward(request, response);
		log.debug("edit: void");
	}
	
	/**
	 * Save property
	 */
	private void save(Session session, String path, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.debug("save({}, {}, {}, {})", new Object[] { session, path, request, response });
		String value = WebUtil.getString(request, "value");
		String property = WebUtil.getString(request, "property");
		Node node = session.getRootNode().getNode(path.substring(1));
		Property prop = node.getProperty(property);
		ValueFactory vf = session.getValueFactory();
		
		if (prop.getDefinition().isMultiple()) {
			StringTokenizer st = new StringTokenizer(value, "\n");
			Value[] values = new Value[st.countTokens()];
			
			for (int i=0 ; st.hasMoreTokens(); i++) {
				values[i] = vf.createValue(st.nextToken().trim());
			}
			
			node.setProperty(property, values);
		} else {
			node.setProperty(property, value);
		}
		
		node.save();
		
		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_SAVE", path, property+" : "+value);
		log.debug("save: void");
	}

	/**
	 * List node properties and children
	 */
	private void list(Session session, String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, javax.jcr.PathNotFoundException, RepositoryException {
		log.info("list({}, {}, {}, {})", new Object[] { session, path, request, response });
		String stats = WebUtil.getString(request, "stats");
		String uuid = WebUtil.getString(request, "uuid");
		ServletContext sc = getServletContext();
		ContentInfo ci = null;
		Node node = null;

		// Respository stats calculation
		if (!stats.equals("")) {
			if (stats.equals("0")) {
				request.getSession().removeAttribute("stats");
			} else {
				request.getSession().setAttribute("stats", true);
			}
		}

		// Handle path or uuid
		if (!path.equals("")) {
			node = session.getRootNode().getNode(path.substring(1));
		} else if (!uuid.equals("")) {
			node = session.getNodeByUUID(uuid);
			path = node.getPath();
		} else {
			node = session.getRootNode();
		}
		
		if (request.getSession().getAttribute("stats") != null && node.isNodeType(Folder.TYPE)) {
			try {
				ci = OKMFolder.getInstance().getContentInfo(node.getPath());
			} catch (AccessDeniedException e) {
				log.warn(e.getMessage(), e);
			} catch (com.openkm.core.RepositoryException e) {
				log.warn(e.getMessage(), e);
			} catch (PathNotFoundException e) {
				log.warn(e.getMessage(), e);
			} catch (DatabaseException e) {
				log.warn(e.getMessage(), e);
			}
		}

		// Activity log
		UserActivity.log(session.getUserID(), "REPOSITORY_LIST", node.getPath(), null);
		
		sc.setAttribute("contentInfo", ci);
		sc.setAttribute("node", node);
		sc.setAttribute("isFolder", node.isNodeType(Folder.TYPE));
		sc.setAttribute("isDocument", node.isNodeType(Document.TYPE));
		sc.setAttribute("isDocumentContent", node.isNodeType(Document.CONTENT_TYPE));
		sc.setAttribute("isScripting", node.isNodeType(Scripting.TYPE));
		sc.setAttribute("holdsLock", node.holdsLock());
		sc.setAttribute("breadcrumb", createBreadcrumb(node.getPath()));
		sc.setAttribute("properties", getProperties(node));
		sc.setAttribute("children", getChildren(node));
		sc.getRequestDispatcher("/admin/repository_list.jsp").forward(request, response);
		log.debug("list: void");
	}

	/**
	 * Create bread crumb for easy navigation
	 */
	private String createBreadcrumb(String path) throws UnsupportedEncodingException {
		int idx = path.lastIndexOf('/');
		if (idx > 0) {
			String name = path.substring(idx+1);
			String parent = path.substring(0, idx);
			return createBreadcrumb(parent)+" / <a href=\"RepositoryView?path="+URLEncoder.encode(path, "UTF-8")+"\">"+name+"</a>";
		} else {
			if (!path.substring(1).equals("")) {
				return "<a href=\"RepositoryView?path=\">ROOT</a> / <a href=\"RepositoryView?path="+URLEncoder.encode(path, "UTF-8")+"\">"+path.substring(1)+"</a>";
			} else {
				return "<a href=\"RepositoryView?path=\">ROOT</a> /";
			}
		}
	}
	
	/**
	 * Get children from node
	 */
	private Collection<Node> getChildren(Node node) throws RepositoryException {
		ArrayList<Node> al = new ArrayList<Node>();

		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			Node child = ni.nextNode();
			al.add(child);
		}
		
		Collections.sort(al, new ChildCmp());
		return al;
	}
	
	/**
	 * Make child node comparable
	 */
	protected class ChildCmp implements Comparator<Node> {
		@Override
		public int compare(Node arg0, Node arg1) {
			try {
				return arg0.getName().compareTo(arg1.getName());
			} catch (RepositoryException e) {
				return 0;
			}
		}
	}
	
	/**
	 * Get properties from node
	 */
	private Collection<HashMap<String, String>> getProperties(Node node) throws ValueFormatException, RepositoryException {
		ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String,String>>();
		
		for (PropertyIterator pi = node.getProperties(); pi.hasNext(); ) {
			HashMap<String, String> hm = new HashMap<String, String>();
			Property p = pi.nextProperty();
			PropertyDefinition pd = p.getDefinition();
			
			hm.put("name", p.getName());
			hm.put("protected", Boolean.toString(pd.isProtected()));
			hm.put("multiple", Boolean.toString(pd.isMultiple()));
			hm.put("type", NODE_TYPE[pd.getRequiredType()]);
			
			if (pd.getRequiredType() == PropertyType.BINARY) {
				InputStream is = p.getStream();
				
				try {
					hm.put("value", "DATA: "+FormatUtil.formatSize(is.available()));
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				if (pd.isMultiple()) {
					hm.put("value", toString(p.getValues(), "<br/>"));
				} else {
					if (p.getName().equals(Scripting.SCRIPT_CODE)) {
						hm.put("value", p.getString().replace("\n", "<br>"));	
					} else {
						hm.put("value", p.getString());
					}
				}
			}
						
			al.add(hm);
		}
		
		Collections.sort(al, new PropertyCmp());
		return al;
	}
	
	/**
	 * Make properties comparable
	 */
	protected class PropertyCmp implements Comparator<HashMap<String, String>> {
		@Override
		public int compare(HashMap<String, String> arg0, HashMap<String, String> arg1) {
			return arg0.get("name").compareTo(arg1.get("name"));
		}
	}
	
	/**
	 * Convert multi-value property to string 
	 */
	private String toString(Value[] v, String delim) throws ValueFormatException, IllegalStateException, 
			RepositoryException {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<v.length-1; i++) {
			sb.append(v[i].getString());
			sb.append(delim);
		}
		
		if (v.length > 0) {
			sb.append(v[v.length-1].getString());
		}
		
		return sb.toString();
	}
}
