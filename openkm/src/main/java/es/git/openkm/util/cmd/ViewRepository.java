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

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.WorkspaceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Note;
import es.git.openkm.bean.Version;
import es.git.openkm.core.OKMSystemSession;

public class ViewRepository {
	private static Logger log = LoggerFactory.getLogger(ViewRepository.class);
	private static long totalDocuments = 0; 
	private static long totalFolders = 0;
	private static long total = 0;
	private static long count = 0;
	
	/**
	 * @param args
	 * @throws RepositoryException 
	 */
	public static void main(String[] args) throws RepositoryException {
		if (args.length != 5 || args[0] == null || args[1] == null || args[2] == null || args[3] == null || args[4] == null) {
			System.err.println("Usage: ViewRepository <RepositoryConfig> <RepositoryHome> <showProperties> <showNotes> <showVersionHistory>");
			return;
		}
		
		boolean showProperties = Boolean.parseBoolean(args[2]);
		boolean showComments = Boolean.parseBoolean(args[3]);
		boolean showVersionHistory = Boolean.parseBoolean(args[4]);
		RepositoryConfig repConfig = RepositoryConfig.create(args[0], args[1]);
		Repository rep = RepositoryImpl.create(repConfig);
		WorkspaceConfig wc = repConfig.getWorkspaceConfig(repConfig.getDefaultWorkspaceName());
		Session se = OKMSystemSession.create((RepositoryImpl)rep, wc);
		Node rn = se.getRootNode();
		
		if (rn.hasNode(es.git.openkm.bean.Repository.ROOT)) {
			Node okmRoot = rn.getNode(es.git.openkm.bean.Repository.ROOT);
			viewRepository(okmRoot, showProperties, showComments, showVersionHistory);	
		} else {
			System.err.println("This is not an OpenKM repository");
		}
				
		// Repositgory shutdown
		se.logout();
		((RepositoryImpl)rep).shutdown();
	}
	
	public static void viewRepository(Node okmRoot, boolean showProperties, boolean showComments, 
			boolean showVersionHistory) throws RepositoryException {
		// Retreaving node counting
		log.info("Retreaving node counting...");
		for (NodeIterator it = okmRoot.getNodes(); it.hasNext(); ) {
			Node child = it.nextNode();
			counterHelper(child);
		}
		total = totalFolders + totalDocuments;
		log.info("============================");
		log.info("BEGIN -> Nodes: "+total+", Folders: "+totalFolders+", Documents: "+totalDocuments);
		log.info("============================");
		
		// okm:root iteration
		for (NodeIterator it = okmRoot.getNodes(); it.hasNext(); ) {
			Node child = it.nextNode();
			viewRepositoryHelper(child, showProperties, showComments, showVersionHistory);
		}
		
		log.info("============================");
		log.info("END");
		log.info("============================");
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
	private static void viewRepositoryHelper(Node node, boolean showProperties, boolean showComments, 
			boolean showVersionHistory) throws RepositoryException {
		if (node.isNodeType(Document.TYPE)) {
			count++;
			log.info("("+count*100/total+"%) DOCUMENT: "+node.getPath());
						
			// Show properties
			if (showProperties) {
				showProperties(node);
			}

			if (showComments) {
				showComments(node);
			}
			
			Node contentNode = node.getNode(Document.CONTENT);
			log.info("("+count*100/total+"%) CONTENT: "+contentNode.getPath());
			
			if (showProperties) {
				showProperties(contentNode);
			}
			
			
			if (showVersionHistory) {
				viewVersionHistory(contentNode);
			}
		} else if (node.isNodeType(Folder.TYPE)) {
			count++;
			log.info("("+count*100/total+"%) FOLDER: "+node.getPath());
			
			// Show properties
			if (showProperties) {
				showProperties(node);
			}
			
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				viewRepositoryHelper(child, showProperties, showComments, showVersionHistory);
			}
		} else {
			throw new RepositoryException("Unknown node type");
		}
	}
	
	/**
	 * @param docNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	private static void showComments(Node docNode) throws PathNotFoundException, RepositoryException {
		Node notesNode = docNode.getNode(Note.LIST);
		
		for (NodeIterator nit = notesNode.getNodes(); nit.hasNext(); ) {
			Node noteNode = nit.nextNode();
			Note note = new Note();
			note.setDate(noteNode.getProperty(Note.DATE).getDate());
			note.setUser(noteNode.getProperty(Note.USER).getString());
			note.setText(noteNode.getProperty(Note.TEXT).getString());
			log.info(note.toString());
		}
	}
	
	/**
	 * @param node
	 * @throws RepositoryException 
	 * @throws IllegalStateException 
	 * @throws ValueFormatException 
	 */
	private static void viewVersionHistory(Node contentNode) throws ValueFormatException, IllegalStateException, RepositoryException {
		VersionHistory vh = contentNode.getVersionHistory();
		String baseVersion = contentNode.getBaseVersion().getName();

		for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
			javax.jcr.version.Version ver = vi.nextVersion();
			String versionName = ver.getName();

			// The rootVersion is not a "real" version node.
			if (!versionName.equals("jcr:rootVersion")) {
				Version version = new Version();
				Node frozenNode = ver.getNode("jcr:frozenNode");
				version.setAuthor(frozenNode.getProperty(Document.AUTHOR).getString());
				version.setSize(frozenNode.getProperty(Document.SIZE).getLong());
				version.setComment(frozenNode.getProperty(Document.VERSION_COMMENT).getString());
				version.setName(ver.getName());
				version.setCreated(ver.getCreated());

				if (versionName.equals(baseVersion)) {
					version.setActual(true);
				} else {
					version.setActual(false);
				}

				log.info(version.toString());
			}
		}
	}
	
	/**
	 * @param node
	 * @throws RepositoryException 
	 * @throws IllegalStateException 
	 * @throws ValueFormatException 
	 */
	private static void showProperties(Node node) throws ValueFormatException, IllegalStateException, RepositoryException {
		for (PropertyIterator pi = node.getProperties(); pi.hasNext(); ) {
			Property p = pi.nextProperty();
			PropertyDefinition pd = p.getDefinition();
			Value[] v = null;
			StringBuffer sb = new StringBuffer();
			sb.append(" + "+p.getName()+" : ");
			
			if (pd.isMultiple()) {
				v = p.getValues();
			}
			
			switch (pd.getRequiredType()) {
				case PropertyType.STRING:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE STRING) ");
						
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getString());
						}
					} else {
						sb.append("(STRING) "+p.getString());
					}
			
					break;
				
				case PropertyType.BINARY:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE BINARY) ");
						
						for (int i=0; i<v.length-1; i++) {
							sb.append("DATA, ");
						}
						
						if (v.length > 0) {
							sb.append("DATA");
						}
					} else {
						sb.append("(BINARY) DATA");
					}
					break;
				
				case PropertyType.DATE:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE DATE) ");
						
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getDate().getTime()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getDate().getTime());
						}
					} else {
						sb.append("(DATE) "+p.getDate().getTime());
					}
					break;
				
				case PropertyType.DOUBLE:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE DOUBLE) ");
	
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getDouble()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getDouble());
						}
					} else {
						sb.append("(DOUBLE) "+p.getDouble());
					}
					break;
				
				case PropertyType.LONG:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE LONG) ");
	
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getLong()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getLong());
						}
					} else {
						sb.append("(LONG) "+p.getLong());
					}
					break;
				
				case PropertyType.BOOLEAN:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE BOOLEAN) ");
	
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getBoolean()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getBoolean());
						}
					} else {
						sb.append("(BOOLEAN) "+p.getBoolean());
					}
					
					break;
				
				case PropertyType.NAME:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE NAME) ");
	
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getString());
						}
					} else {
						sb.append("(NAME) "+p.getString());
					}
					break;
				
				case PropertyType.PATH:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE PATH) ");
	
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getString());
						}
					} else {
						sb.append("(PATH) "+p.getString());
					}
					break;
				
				case PropertyType.REFERENCE:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE REFERENCE) ");
						
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getString());
						}
					} else {
						sb.append("(REFERENCE) "+p.getString());
					}
					break;
				
				case PropertyType.UNDEFINED:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE UNDEFINED) ");
	
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getString());
						}
					} else {
						sb.append("(UNDEFINED) "+p.getString());
					}
					break;
				
				default:
					if (pd.isMultiple()) {
						sb.append("(MULTIPLE UNKNOWN) ");
						
						for (int i=0; i<v.length-1; i++) {
							sb.append(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							sb.append(v[v.length-1].getString());
						}
					} else {
						sb.append("(UNKNOWN) "+p.getType());
					}
					break;
			}
			
			log.info(sb.toString());
		}
	}
}
