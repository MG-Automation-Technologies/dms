package com.openkm.cache;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.UserItemsDAO;
import com.openkm.dao.bean.cache.UserItems;

public class UserItemsManager {
	private static Logger log = LoggerFactory.getLogger(UserItemsManager.class);
	private static Map<String, UserItems> userItemsMgr = new HashMap<String, UserItems>();
	
	/**
	 * 
	 */
	public static UserItems get(String uid) {
		UserItems userItems = userItemsMgr.get(uid);
		
		if (userItems == null) {
			userItems = new UserItems();
			userItems.setUser(uid);
			userItemsMgr.put(uid, userItems);
		}
		
		return userItems;
	}
	
	/**
	 * 
	 */
	public static synchronized void incDocuments(String uid, int value) {
		UserItems userItems = get(uid);
		userItems.setDocuments(userItems.getDocuments() + value);
	}

	/**
	 * 
	 */
	public static synchronized void decDocuments(String uid, int value) {
		UserItems userItems = get(uid);
		userItems.setDocuments(userItems.getDocuments() - value);
	}
	
	/**
	 * 
	 */
	public static synchronized void incFolders(String uid, int value) {
		UserItems userItems = get(uid);
		userItems.setFolders(userItems.getFolders() + value);
	}

	/**
	 * 
	 */
	public static synchronized void decFolders(String uid, int value) {
		UserItems userItems = get(uid);
		userItems.setFolders(userItems.getFolders() - value);
	}

	/**
	 * 
	 */
	public static synchronized void incSize(String uid, long value) {
		UserItems userItems = get(uid);
		userItems.setSize(userItems.getSize() + value);
	}

	/**
	 * 
	 */
	public static synchronized void decSize(String uid, long size) {
		UserItems userItems = get(uid);
		userItems.setSize(userItems.getSize() - size);
	}
	
	/**
	 * TODO: Not fully implemented
	 */
	public static synchronized void refreshUserItems(Session session) throws RepositoryException {
		log.info("refreshUserItems({})", session);
		
		try {
			String statement = "/jcr:root/okm:root//element(*, okm:document)[okm:content/@okm:author='"+session.getUserID()+"']";
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			QueryResult result = query.execute();
			long size = 0;
			
			for (NodeIterator nit = result.getNodes(); nit.hasNext(); ) {
				Node node = nit.nextNode();
				Node contentNode = node.getNode(Document.CONTENT);
				size += contentNode.getProperty(Document.SIZE).getLong();
			}
			
			UserItems userItems = new UserItems();
			userItemsMgr.put(session.getUserID(), userItems);
 		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
 		
 		log.info("refreshUserItems: void");
	}

	/**
	 * 
	 */
	public static synchronized void serialize() throws DatabaseException {
		for (String user : userItemsMgr.keySet()) {
			UserItemsDAO.update(userItemsMgr.get(user));
		}
	}
	
	/**
	 * 
	 */
	public static synchronized void deserialize() throws DatabaseException {
		for (UserItems ui : UserItemsDAO.findAll()) {
			userItemsMgr.put(ui.getUser(), ui);
		}
	}
}
