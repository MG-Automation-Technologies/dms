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

package com.openkm.module.direct;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.nodetype.PropertyDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.MetaData;
import com.openkm.bean.PropertyGroup;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.LockException;
import com.openkm.core.NoSuchGroupException;
import com.openkm.core.NoSuchPropertyException;
import com.openkm.core.RepositoryException;
import com.openkm.core.ResourceClassLoader;
import com.openkm.core.SessionManager;
import com.openkm.module.PropertyGroupModule;
import com.openkm.util.UserActivity;
import com.openkm.util.JCRUtils;

public class DirectPropertyGroupModule implements PropertyGroupModule {
	private static Logger log = LoggerFactory.getLogger(DirectPropertyGroupModule.class);

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#addGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addGroup("+token+", "+docPath+", "+grpName+")");
		Node documentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(docPath.substring(1));
			documentNode.addMixin(grpName);
			documentNode.save();
			
			// Activity log
			UserActivity.log(session, "ADD_PROPERTY_GROUP", docPath, grpName);
		} catch (javax.jcr.nodetype.NoSuchNodeTypeException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new NoSuchGroupException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("addGroup: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#removeGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			RepositoryException {
		log.debug("removeGroup("+token+", "+docPath+", "+grpName+")");
		Node documentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(docPath.substring(1));
			documentNode.removeMixin(grpName);
			documentNode.save();
			
			// Activity log
			UserActivity.log(session, "REMOVE_PROPERTY_GROUP", docPath, grpName);
		} catch (javax.jcr.nodetype.NoSuchNodeTypeException e) {
			log.error(e.getMessage(), e);
			throw new NoSuchGroupException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("removeGroup: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getGroups(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<String> getGroups(String token, String docPath)
			throws PathNotFoundException, RepositoryException {
		log.debug("getGroups("+token+", "+docPath+")");
		ArrayList<String> ret = new ArrayList<String>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			NodeType[] nt = documentNode.getMixinNodeTypes();
			
			for (int i=0; i<nt.length; i++) {
				if (nt[i].getName().startsWith(PropertyGroup.GROUP+":")) {
					ret.add(nt[i].getName());
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getGroups: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getAllGroups(java.lang.String)
	 */
	@Override
	public Collection<String> getAllGroups(String token) throws RepositoryException {
		log.debug("getAllGroups("+token+")");
		ArrayList<String> ret = new ArrayList<String>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			
			for (NodeTypeIterator nti = ntm.getMixinNodeTypes(); nti.hasNext();) {
				NodeType nt = nti.nextNodeType();
				
				if (nt.getName().startsWith(PropertyGroup.GROUP+":")) {
					ret.add(nt.getName());
				}
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getAllGroups: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getProperties(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap<String, String[]> getProperties(String token, String docPath, String grpName) 
			throws NoSuchGroupException, PathNotFoundException, RepositoryException {
		log.debug("getProperties("+token+", "+grpName+")");
		HashMap<String, String[]> ret = new HashMap<String, String[]>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			NodeType nt = ntm.getNodeType(grpName);
			PropertyDefinition[] pd = nt.getDeclaredPropertyDefinitions();
			
			for (int i=0; i<pd.length; i++) {
				try {
					Property prop = documentNode.getProperty(pd[i].getName());
		 					
					if (pd[i].isMultiple()) {
						Value[] values = prop.getValues();
						String[] sValues = null;
						
						if (values.length == 1 && values[0].getString().length() == 0) {
							sValues = new String[0];
						} else {
							sValues = new String[values.length];
						
							for (int j=0; j<values.length; j++) {
								sValues[j] = values[j].getString();
							}
						}
						
						ret.put(pd[i].getName(), sValues);
					} else {
						Value value = prop.getValue();
						ret.put(pd[i].getName(), new String[]{ value.getString() });
					}
				} catch (javax.jcr.PathNotFoundException e) {
					log.debug("Requested property not found: "+e.getMessage());
				}
			}
			
			// Activity log
			UserActivity.log(session, "GET_PROPERTY_GROUP_PROPERTIES", docPath, grpName+", "+ret);
		} catch (javax.jcr.nodetype.NoSuchNodeTypeException e) {
			log.error(e.getMessage(), e);
			throw new NoSuchGroupException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getProperties: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#setProperties(java.lang.String, java.lang.String, java.lang.String, java.util.HashMap)
	 */
	@Override
	public void setProperties(String token, String docPath, String grpName, HashMap<String, String[]> properties) throws 
			NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("setProperties("+token+", "+docPath+", "+properties+")");
		Node documentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			NodeType nt = ntm.getNodeType(grpName);
			PropertyDefinition[] pd = nt.getDeclaredPropertyDefinitions();
			documentNode = session.getRootNode().getNode(docPath.substring(1));
			
			for (int i=0; i<pd.length; i++) {
				if (pd[i].isMultiple()) {
					documentNode.setProperty(pd[i].getName(), properties.get(pd[i].getName()));
				} else {
					documentNode.setProperty(pd[i].getName(), properties.get(pd[i].getName())[0]);	
				}
			}
			
			documentNode.save();
			
			// Activity log
			UserActivity.log(session, "SET_PROPERTY_GROUP_PROPERTIES", docPath, grpName+", "+properties);
		} catch (javax.jcr.nodetype.NoSuchNodeTypeException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new NoSuchPropertyException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getMetaData(java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap<String, MetaData> getMetaData(String token, String grpName) throws IOException, RepositoryException {
		log.debug("getMetaData("+token+", "+grpName+")");
		HashMap<String, MetaData> ret = new HashMap<String, MetaData>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			NodeType nt = ntm.getNodeType(grpName);
			PropertyDefinition[] pd = nt.getDeclaredPropertyDefinitions();
			HashMap<String, MetaData> md = parseMetadata();
			
			for (int i=0; i<pd.length; i++) {
				ret.put(pd[i].getName(), md.get(pd[i].getName()));
			}
		} catch (java.io.IOException e) {
			log.error(e.getMessage(), e);
			throw e;		
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getMetaData: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getTranslations(java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap<String, String> getTranslations(String token, String lang) throws IOException, RepositoryException {
		log.debug("getTranslations("+token+", "+lang+")");
		HashMap<String, String> ret = new HashMap<String, String>();
		
		try {
			ResourceBundle rb = ResourceBundle.getBundle("PropertyGroupBundle"+Config.INSTALL, 
					new Locale(lang), new ResourceClassLoader());
		
			for (Enumeration<String> e = rb.getKeys(); e.hasMoreElements(); ) {
				String key = e.nextElement();
				ret.put(key, rb.getString(key));
			}
		} catch (java.util.MissingResourceException e) {
			log.error(e.getMessage(), e);
			throw new IOException(e.getMessage());		
		}
		
		log.debug("getTranslations: "+ret);
		return ret;
	}
	
	/**
	 * Parse Metadata
	 */
	public static HashMap<String, MetaData> parseMetadata() throws IOException {
		HashMap<String, MetaData> ret = new HashMap<String, MetaData>();
		Properties prop = new Properties();
		prop.load(new FileInputStream(Config.JBOSS_HOME+"/"+"PropertyGroupValues"+Config.INSTALL+".properties"));

		for (Iterator<Entry<Object, Object>> it = prop.entrySet().iterator(); it.hasNext(); ) {
			Entry<Object, Object> entry = it.next();
			MetaData md = new MetaData();
			ArrayList<String> al = new ArrayList<String>();
			String values = (String) entry.getValue();
			
			if (values != null) {
				String[] value = values.split(",");
				for (int j=0; j<value.length; j++) {
					if (j == 0) {
						md.setOrder(Integer.parseInt(value[j]));
					} else if (j == 1) {
						md.setType(Integer.parseInt(value[j]));
					} else {
						al.add(value[j]);
					}
				}
				
				md.setValues(al);
				ret.put((String)entry.getKey(), md);
			}
		}

		return ret;
	}
}
