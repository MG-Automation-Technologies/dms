package com.openkm.cache;

import java.util.HashMap;

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
import com.openkm.bean.cache.UserItems;
import com.openkm.core.RepositoryException;
import com.openkm.util.Serializer;

public class UserItemsManager {
	private static Logger log = LoggerFactory.getLogger(UserItemsManager.class);
	private static final String FILEALIZATION = "UserItemsManager";
	private static HashMap<String, UserItems> userItemsMgr;
	
	/**
	 * 
	 */
	public static UserItems get(String uid) {
		UserItems userItems = userItemsMgr.get(uid);
		
		if (userItems == null) {
			userItems = new UserItems();
		}
		
		return userItems;
	}
	
	/**
	 * 
	 */
	public static synchronized void put(String uid, UserItems userItems) {
		userItemsMgr.put(uid, userItems);
	}
	
	/**
	 * 
	 */
	public static synchronized void incDocuments(String uid, long value) {
		UserItems userItems = get(uid);
		userItems.setDocuments(userItems.getDocuments() + value);
		userItemsMgr.put(uid, userItems);
	}

	/**
	 * 
	 */
	public static synchronized void decDocuments(String uid, long value) {
		UserItems userItems = get(uid);
		userItems.setDocuments(userItems.getDocuments() - value);
		userItemsMgr.put(uid, userItems);
	}
	
	/**
	 * 
	 */
	public static synchronized void incFolders(String uid, long value) {
		UserItems userItems = get(uid);
		userItems.setFolders(userItems.getFolders() + value);
		userItemsMgr.put(uid, userItems);
	}

	/**
	 * 
	 */
	public static synchronized void decFolders(String uid, long value) {
		UserItems userItems = get(uid);
		userItems.setFolders(userItems.getFolders() - value);
		userItemsMgr.put(uid, userItems);
	}

	/**
	 * 
	 */
	public static synchronized void incSize(String uid, long value) {
		UserItems userItems = get(uid);
		userItems.setSize(userItems.getSize() + value);
		userItemsMgr.put(uid, userItems);
	}

	/**
	 * 
	 */
	public static synchronized void decSize(String uid, long size) {
		UserItems userItems = get(uid);
		userItems.setSize(userItems.getSize() - size);
		userItemsMgr.put(uid, userItems);
	}
	
	/**
	 * TODO: Not fully implemented
	 */
	public static synchronized void refreshUserItems(Session session) throws RepositoryException {
		log.info("refreshUserItems("+session+")");
		
		try {
			String statement = "/jcr:root/okm:root//element(*, okm:document)[okm:content/@okm:author='"+session.getUserID()+"']";
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, "xpath");
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
	public static synchronized void serialize() {
		Serializer.write(FILEALIZATION, userItemsMgr);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void deserialize() {
		userItemsMgr = new HashMap<String, UserItems>();
		Object obj = Serializer.read(FILEALIZATION);
		if (obj != null) {
			userItemsMgr = (HashMap<String, UserItems>) obj;
		}
	}
}
