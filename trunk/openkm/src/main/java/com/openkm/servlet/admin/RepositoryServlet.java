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

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.core.Config;
import com.openkm.core.SessionManager;
import com.openkm.util.FormatUtil;
import com.openkm.util.JCRUtils;
import com.openkm.util.WebUtil;

/**
 * Repository servlet
 */
public class RepositoryServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(RepositoryServlet.class);
	private static final long serialVersionUID = 1L;
	private static final String[] NODE_TYPE = { "UNDEFINED", "STRING", "BINARY", "LONG", "DOUBLE", 
		"DATE", "BOOLEAN", "NAME", "PATH", "REFERENCE" };
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		
		if (action.equals(WebUtil.VIEW)) {
			//view(request, response);
		} else if (action.equals(WebUtil.LIST) || action.equals("")) {
			list(request, response);
		} else {
			ServletContext sc = getServletContext();
			sc.getRequestDispatcher("/admin/repository.jsp").forward(request, response);
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("list({}, {})", request, response);
		String stats = WebUtil.getString(request, "stats");
		String path = WebUtil.getString(request, "path");
		String uuid = WebUtil.getString(request, "uuid");
		String token = (String) request.getSession().getAttribute("token");
		ServletContext sc = getServletContext();
		Session session = null;
		Node node = null;

		// Respository stats calculation
		if (!stats.equals("")) {
			if (stats.equals("0")) {
				request.getSession().removeAttribute("stats");
			} else {
				request.getSession().setAttribute("stats", true);
			}
		}

		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			// Handle part or uuid
			if (!path.equals("")) {
				path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
				node = session.getRootNode().getNode(path.substring(1));
			} else if (!uuid.equals("")) {
				node = session.getNodeByUUID(uuid);
				path = node.getPath();
			} else {
				node = session.getRootNode();
			}
			
			sc.setAttribute("path", createPath(node.getPath()));
			sc.setAttribute("properties", getProperties(node));
			sc.setAttribute("childs", getChilds(node));
			sc.getRequestDispatcher("/admin/repository.jsp").forward(request, response);
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("list: void");
	}

	private String createPath(String path) throws UnsupportedEncodingException {
		int idx = path.lastIndexOf('/');
		if (idx > 0) {
			String name = path.substring(idx+1);
			String parent = path.substring(0, idx);
			return createPath(parent)+" / <a href=\"Repository?path="+URLEncoder.encode(path, "UTF-8")+"\">"+name+"</a>";
		} else {
			if (!path.substring(1).equals("")) {
				return "/ <a href=\"Repository?path="+URLEncoder.encode(path, "UTF-8")+"\">"+path.substring(1)+"</a>";
			} else {
				return "/";
			}
		}
	}
	
	/**
	 * Get child from node
	 */
	private Collection<HashMap<String, String>> getChilds(Node node) throws RepositoryException {
		ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String,String>>();

		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			HashMap<String, String> hm = new HashMap<String, String>();
			Node child = ni.nextNode();
			
			hm.put("type", child.getPrimaryNodeType().getName().toUpperCase());
			hm.put("path", child.getPath());
			hm.put("name", child.getName());
			
			if (child.isNodeType(Document.TYPE)) {
				hm.put("locked", Boolean.toString(child.isLocked()));
			} else if (child.isNodeType(Document.CONTENT_TYPE)) {
				hm.put("locked", Boolean.toString(child.isLocked()));
				hm.put("checkedOut", Boolean.toString(child.isCheckedOut()));
			}
			
			al.add(hm);
		}
		
		Collections.sort(al, new ChildCmp());
		return al;
	}
	
	/**
	 * Make child node comparable
	 */
	protected class ChildCmp implements Comparator<HashMap<String, String>> {
		@Override
		public int compare(HashMap<String, String> arg0, HashMap<String, String> arg1) {
			return arg0.get("name").compareTo(arg1.get("name"));
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
					hm.put("value", toString(p.getValues()));
				} else {
					hm.put("value", p.getString());
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
	
	private String toString(Value[] v) throws ValueFormatException, IllegalStateException, 
			RepositoryException {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<v.length-1; i++) {
			sb.append(v[i].getString());
			sb.append(", ");
		}
		
		if (v.length > 0) {
			sb.append(v[v.length-1].getString());
		}
		
		return sb.toString();
	}
}
