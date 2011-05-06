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

package es.git.openkm.module.direct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.NoSuchGroupException;
import es.git.openkm.core.NoSuchPropertyException;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.PropertyGroupModule;
import es.git.openkm.util.FormUtils;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.UserActivity;

public class DirectPropertyGroupModule implements PropertyGroupModule {
	private static Logger log = LoggerFactory.getLogger(DirectPropertyGroupModule.class);

	@Override
	public void addGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addGroup({}, {}, {})", new Object[] { token, docPath, grpName });
		Node documentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(docPath.substring(1));
			
			synchronized (documentNode) {
				documentNode.addMixin(grpName);
				documentNode.save();
			}
			
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

	@Override
	public void removeGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			RepositoryException {
		log.debug("removeGroup({}, {}, {})", new Object[] { token, docPath, grpName });
		Node documentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(docPath.substring(1));
			
			synchronized (documentNode) {
				documentNode.removeMixin(grpName);
				documentNode.save();				
			}
			
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

	@Override
	public Collection<PropertyGroup> getGroups(String token, String docPath)
			throws IOException, ParseException, PathNotFoundException, RepositoryException {
		log.debug("getGroups({}, {})", token, docPath);
		ArrayList<PropertyGroup> ret = new ArrayList<PropertyGroup>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			NodeType[] nt = documentNode.getMixinNodeTypes();
			Map<PropertyGroup, Collection<FormElement>> pgf = FormUtils.parsePropertyGroupsForms();
			
			// Only return registered property definitions
			for (int i=0; i<nt.length; i++) {
				if (nt[i].getName().startsWith(PropertyGroup.GROUP+":")) {
					for (Iterator<PropertyGroup> it = pgf.keySet().iterator(); it.hasNext(); ) {
						PropertyGroup pg = it.next();
						
						if (pg.getName().equals(nt[i].getName())) {
							ret.add(pg);
						}
					}
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getGroups: {}", ret);
		return ret;
	}

	@Override
	public Collection<PropertyGroup> getAllGroups(String token) throws IOException, ParseException, 
			RepositoryException {
		log.debug("getAllGroups({})", token);
		ArrayList<PropertyGroup> ret = new ArrayList<PropertyGroup>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			Map<PropertyGroup, Collection<FormElement>> pgf = FormUtils.parsePropertyGroupsForms();
			
			// Only return registered property definitions
			for (NodeTypeIterator nti = ntm.getMixinNodeTypes(); nti.hasNext();) {
				NodeType nt = nti.nextNodeType();
				
				if (nt.getName().startsWith(PropertyGroup.GROUP+":")) {
					for (Iterator<PropertyGroup> it = pgf.keySet().iterator(); it.hasNext(); ) {
						PropertyGroup pg = it.next();
						
						if (pg.getName().equals(nt.getName())) {
							ret.add(pg);
						}
					}
				}
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getAllGroups: {}", ret);
		return ret;
	}

	@Override
	public HashMap<String, String[]> getProperties(String token, String docPath, String grpName) 
			throws NoSuchGroupException, PathNotFoundException, RepositoryException {
		log.debug("getProperties({}, {})", token, grpName);
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
		
		log.debug("getProperties: {}", ret);
		return ret;
	}

	@Override
	public void setProperties(String token, String docPath, String grpName, Map<String, String[]> properties) throws 
			NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("setProperties({}, {}, {})", new Object[] { token, docPath, properties });
		Node documentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			NodeType nt = ntm.getNodeType(grpName);
			PropertyDefinition[] pd = nt.getDeclaredPropertyDefinitions();
			documentNode = session.getRootNode().getNode(docPath.substring(1));
			
			synchronized (documentNode) {
				for (int i=0; i<pd.length; i++) {
					if (pd[i].isMultiple()) {
						documentNode.setProperty(pd[i].getName(), properties.get(pd[i].getName()));
					} else {
						documentNode.setProperty(pd[i].getName(), properties.get(pd[i].getName())[0]);	
					}
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

	@Override
	public Collection<FormElement> getPropertyGroupForm(String token, String grpName) throws IOException,
			ParseException, RepositoryException {
		log.debug("getPropertyGroupForm({}, {})", token, grpName);
		Collection<FormElement> ret = new ArrayList<FormElement>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			NodeTypeManager ntm = session.getWorkspace().getNodeTypeManager();
			NodeType nt = ntm.getNodeType(grpName);
			PropertyDefinition[] pd = nt.getDeclaredPropertyDefinitions();
			Map<PropertyGroup, Collection<FormElement>> pgf = FormUtils.parsePropertyGroupsForms();
			Collection<FormElement> tmp = FormUtils.getPropertyGroupForms(pgf, grpName);
			
			// Only return registered property definitions
			for (FormElement fe : tmp) {
				for (int i=0; i < pd.length; i++) {
					if (fe.getName().equals(pd[i].getName())) {
						ret.add(fe);
					}
				}
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.info("getPropertyGroupForm: {}", ret);
		return ret;
	}
}