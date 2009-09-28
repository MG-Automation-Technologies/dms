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
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.WorkspaceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.core.OKMSystemSession;

public class Traverse {
	private static Logger log = LoggerFactory.getLogger(Traverse.class);
	private static long totalDocuments = 0; 
	private static long totalFolders = 0;
	private static long total = 0;
	private static long count = 0;
	
	/**
	 * @param args
	 * @throws RepositoryException 
	 */
	public static void main(String[] args) throws RepositoryException {
		if (args.length != 2 || args[0] == null || args[1] == null) {
			System.err.println("Usage: Traverse <RepositoryConfig> <RepositoryHome>");
			return;
		}
		
		RepositoryConfig repConfig = RepositoryConfig.create(args[0], args[1]);
		Repository rep = RepositoryImpl.create(repConfig);
		WorkspaceConfig wc = repConfig.getWorkspaceConfig(repConfig.getDefaultWorkspaceName());
		Session se = OKMSystemSession.create((RepositoryImpl)rep, wc);
		Node rn = se.getRootNode();
		
		if (rn.hasNode(es.git.openkm.bean.Repository.ROOT)) {
			Node okmRoot = rn.getNode(es.git.openkm.bean.Repository.ROOT);
						
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
				traverseHelper(child);
			}
			
			log.info("============================");
			log.info("END");
			log.info("============================");
		} else {
			System.err.println("This is not an OpenKM repository");
		}
				
		// Repositgory shutdown
		se.logout();
		((RepositoryImpl)rep).shutdown();
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
	private static void traverseHelper(Node node) throws RepositoryException {
		if (node.isNodeType(Document.TYPE)) {
			count++;
			log.info("("+count*100/total+"%) DOCUMENT: "+node.getPath());
						
			// Perform some operation
			Node contentNode = node.getNode(Document.CONTENT);
			log.info("DocAuthor: "+node.getProperty("okm:author").getString());
			log.info("ContentAuthor: "+contentNode.getProperty("okm:author").getString());
			//node.save();
		} else if (node.isNodeType(Folder.TYPE)) {
			count++;
			log.info("("+count*100/total+"%) FOLDER: "+node.getPath());
			
			// Perform some operation
			//node.save();
			
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				traverseHelper(child);
			}
		} else {
			throw new RepositoryException("Unknown node type");
		}
	}
}
