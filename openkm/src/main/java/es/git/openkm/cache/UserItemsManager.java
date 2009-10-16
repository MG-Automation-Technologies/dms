package es.git.openkm.cache;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.cache.UserItems;
import es.git.openkm.util.Serializer;

public class UserItemsManager {
	@SuppressWarnings("unused")
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
