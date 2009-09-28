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

package es.git.openkm.util.cmd;

import java.io.FileNotFoundException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.WorkspaceConfig;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.ParseException;
import org.apache.jackrabbit.spi.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Notification;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.core.OKMSystemSession;
import es.git.openkm.module.direct.DirectRepositoryModule;

public class RepositoryUpgrade {
	private static Logger log = LoggerFactory.getLogger(RepositoryUpgrade.class);
	private static long totalDocuments = 0; 
	private static long totalFolders = 0;
	private static long total = 0;
	private static long count = 0;
	
	public static void main(String[] args) throws RepositoryException, FileNotFoundException, InvalidNodeTypeDefException, ParseException, es.git.openkm.core.RepositoryException {
		if (args.length != 4 || args[0] == null || args[1] == null || args[2] == null || args[3] == null) {
			System.err.println("Usage: RepositoryUpgrade <RepositoryOldConfig> <RepositoryOldHome> <RepositoryNewConfig> <RepositoryNewHome>");
			return;
		}

		RepositoryConfig repOldConfig = RepositoryConfig.create(args[0], args[1]);
		RepositoryConfig repNewConfig = RepositoryConfig.create(args[2], args[3]);
		Repository repOld = RepositoryImpl.create(repOldConfig);
		Repository repNew = RepositoryImpl.create(repNewConfig);
		WorkspaceConfig wcOld = repOldConfig.getWorkspaceConfig(repOldConfig.getDefaultWorkspaceName());
		WorkspaceConfig wcNew = repNewConfig.getWorkspaceConfig(repNewConfig.getDefaultWorkspaceName());
		Session seOld = OKMSystemSession.create((RepositoryImpl)repOld, wcOld);
		Session seNew = OKMSystemSession.create((RepositoryImpl)repNew, wcNew);
		Node rnOld = seOld.getRootNode();
		Node rnNew = seNew.getRootNode();
		
		if (rnOld.hasNode(es.git.openkm.bean.Repository.ROOT)) {
			Node okmRootOld = rnOld.getNode(es.git.openkm.bean.Repository.ROOT);
			DirectRepositoryModule.create(seNew);
			Node okmRootNew = rnNew.getNode(es.git.openkm.bean.Repository.ROOT);
						
			// Retreaving node counting
			log.info("Retreaving node counting...");
			for (NodeIterator it = okmRootOld.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				counterHelper(child);
			}
			total = totalFolders + totalDocuments;
			log.info("============================");
			log.info("BEGIN -> Nodes: "+total+", Folders: "+totalFolders+", Documents: "+totalDocuments);
			log.info("============================");
			
			// Register property groups			
			NodeTypeManagerImpl ntmOld = (NodeTypeManagerImpl) seOld.getWorkspace().getNodeTypeManager();
			NodeTypeRegistry ntrOld = ntmOld.getNodeTypeRegistry();
			NodeTypeManagerImpl ntmNew = (NodeTypeManagerImpl) seNew.getWorkspace().getNodeTypeManager();
			NodeTypeRegistry ntrNew = ntmNew.getNodeTypeRegistry();
			Name[] rnt = ntrOld.getRegisteredNodeTypes();
			
			for (int i=0; i < rnt.length; i++) {
				if (rnt[i].getNamespaceURI().equals(PropertyGroup.GROUP_URI)) {
					log.debug("REGISTER_PROPERTY_GROUP: "+rnt[i]);
					NodeTypeDef ntd = ntrOld.getNodeTypeDef(rnt[i]);
					ntrNew.registerNodeType(ntd);
				}
			}
			
			// okm:root migration
			for (NodeIterator it = okmRootOld.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				migrationHelper(child, okmRootNew);
			}
			
			log.info("============================");
			log.info("END");
			log.info("============================");
		} else {
			System.err.println("This is not an OpenKM repository");
		}
				
		// Repositgory shutdown
		seNew.logout();
		seOld.logout();
		((RepositoryImpl)repNew).shutdown();
		((RepositoryImpl)repOld).shutdown();
	}
		
	/**
	 * @param node
	 * @return
	 * @throws RepositoryException
	 */
	private static void counterHelper(Node node) throws RepositoryException {
		if (node.isNodeType(Document.TYPE)) {
			totalDocuments++;
		} else if (node.isNodeType(Folder.TYPE)) {
			totalFolders++;
			
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				counterHelper(child);
			}
		}
	}
	
	/**
	 * @param nodeOld
	 * @param nodeNew
	 * @throws RepositoryException
	 */
	private static void migrationHelper(Node nodeOld, Node nodeNew) throws RepositoryException {
		if (nodeOld.isNodeType(Document.TYPE)) {
			count++;
			log.info("("+count*100/total+"%) DOCUMENT: "+nodeOld.getPath()+" -> "+nodeNew.getPath());
						
			// Create and add a new file node
			Node documentNode = nodeNew.addNode(nodeOld.getName(), Document.TYPE);
			documentNode.setProperty(Document.KEYWORDS, nodeOld.getProperty(Document.KEYWORDS).getString());
			documentNode.setProperty(Document.AUTHOR, nodeOld.getProperty(Document.AUTHOR).getString());
			documentNode.setProperty(Document.NAME, nodeOld.getProperty(Document.NAME).getString());
			
			// Set auth info
			documentNode.setProperty(Permission.USERS_READ, nodeOld.getProperty(Permission.USERS_READ).getValues());
			documentNode.setProperty(Permission.USERS_WRITE, nodeOld.getProperty(Permission.USERS_WRITE).getValues());
			documentNode.setProperty(Permission.ROLES_READ, nodeOld.getProperty(Permission.ROLES_READ).getValues());
			documentNode.setProperty(Permission.ROLES_WRITE, nodeOld.getProperty(Permission.ROLES_WRITE).getValues());
			
			// Set notification info
			if (nodeOld.isNodeType(Notification.TYPE)) {
				Value[] notifiedUsers = nodeOld.getProperty(Notification.SUBSCRIPTORS).getValues();
				documentNode.addMixin(Notification.TYPE);
				documentNode.setProperty(Notification.SUBSCRIPTORS, notifiedUsers);
			}
			
			// Set content
			Node contentNodeOld = nodeOld.getNode(Document.CONTENT);
			Node contentNode = documentNode.addNode(Document.CONTENT, Document.CONTENT_TYPE);
			contentNode.setProperty(Document.SIZE, contentNodeOld.getProperty(Document.SIZE).getLong());
			contentNode.setProperty(Document.AUTHOR, contentNodeOld.getProperty(Document.AUTHOR).getString());
			contentNode.setProperty(Document.VERSION_COMMENT, "");
			contentNode.setProperty("jcr:mimeType", contentNodeOld.getProperty("jcr:mimeType").getString());
			contentNode.setProperty("jcr:data", contentNodeOld.getProperty("jcr:data").getStream());
			contentNode.setProperty("jcr:lastModified", contentNodeOld.getProperty("jcr:lastModified").getDate());
			
			// Set property groups
			NodeType[] nt = nodeOld.getMixinNodeTypes();

			for (int i=0; i<nt.length; i++) {
				if (nt[i].getName().startsWith(PropertyGroup.GROUP+":")) {
					log.debug(" + PROPERTY_GROUP: "+nt[i].getName());
					documentNode.addMixin(nt[i].getName());
					PropertyDefinition[] pd = nt[i].getDeclaredPropertyDefinitions();
					
					for (int j=0; j<pd.length; j++) {
						log.debug("   + PROPERTY: "+pd[j].getName());
						Property prop = nodeOld.getProperty(pd[j].getName());
						
						if (pd[j].isMultiple()) {
							Value[] values = prop.getValues();
							documentNode.setProperty(pd[j].getName(), values);
						} else {
							Value value = prop.getValue();
							documentNode.setProperty(pd[j].getName(), value);
						}
					}
				}
			}
			
			// Serialize changes & set initial version
			nodeNew.save();
			contentNode.checkin();
		} else if (nodeOld.isNodeType(Folder.TYPE)) {
			count++;
			log.info("("+count*100/total+"%) FOLDER: "+nodeOld.getPath()+" -> "+nodeNew.getPath());
			
			// Create and add a new file node
			Node folderNode = nodeNew.addNode(nodeOld.getName(), Folder.TYPE);
			folderNode.setProperty(Folder.AUTHOR, nodeOld.getProperty(Document.AUTHOR).getString());
			folderNode.setProperty(Folder.NAME, nodeOld.getProperty(Document.NAME).getString());

			// Set auth info
			folderNode.setProperty(Permission.USERS_READ, nodeOld.getProperty(Permission.USERS_READ).getValues());
			folderNode.setProperty(Permission.USERS_WRITE, nodeOld.getProperty(Permission.USERS_WRITE).getValues());
			folderNode.setProperty(Permission.ROLES_READ, nodeOld.getProperty(Permission.ROLES_READ).getValues());
			folderNode.setProperty(Permission.ROLES_WRITE, nodeOld.getProperty(Permission.ROLES_WRITE).getValues());
			
			// Set notification info
			if (nodeOld.isNodeType(Notification.TYPE)) {
				Value[] notifiedUsers = nodeOld.getProperty(Notification.SUBSCRIPTORS).getValues();
				folderNode.addMixin(Notification.TYPE);
				folderNode.setProperty(Notification.SUBSCRIPTORS, notifiedUsers);
			}
			
			nodeNew.save();
			
			for (NodeIterator it = nodeOld.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				migrationHelper(child, folderNode);
			}
		} else {
			throw new RepositoryException("Unknown node type");
		}
	}
}
