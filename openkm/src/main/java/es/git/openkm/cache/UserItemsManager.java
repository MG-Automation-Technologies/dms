package es.git.openkm.cache;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.cache.UserItems;
import es.git.openkm.util.Serializer;

public class UserItemsManager {
	private static Logger log = LoggerFactory.getLogger(UserItemsManager.class);
	private static final String FILEALIZATION = "UserItemsManager";
	private static HashMap<String, UserItems> userItemsMgr;
	
	static {
		deserialize();
	}
	
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
		serialize();
	}
	
	/**
	 * 
	 */
	private static synchronized void serialize() {
		Serializer.write(FILEALIZATION, userItemsMgr);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static synchronized void deserialize() {
		userItemsMgr = new HashMap<String, UserItems>();
		Object obj = Serializer.read(FILEALIZATION);
		if (obj != null) {
			userItemsMgr = (HashMap<String, UserItems>) obj;
		}
	}
}
