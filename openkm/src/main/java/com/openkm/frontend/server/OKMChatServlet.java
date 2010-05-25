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

package com.openkm.frontend.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.openkm.frontend.client.service.OKMChatService;
import com.openkm.util.UUIDGenerator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMChatServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMChatServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMChatServlet extends OKMRemoteServiceServlet implements OKMChatService {
	
	private static final long serialVersionUID = 3780857624687394918L;
	private static final int DELAY = 100; // mseg
	
	private static List<String> usersLogged;
	private static Map<String, List<String>> usersRooms= new HashMap<String, List<String>>(); // user is the key
	private static Map<String, List<String>> pendingUsersRooms= new HashMap<String, List<String>>(); // user is the key
	private static Map<String, HashMap<String,List<String>>> msgUsersRooms= new HashMap<String, HashMap<String,List<String>>>(); // room is the key
															// user is the subkey, messages are copied to each user
	
	/**
     * Init
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
    	super.init(config);
    	usersLogged = new ArrayList<String>();
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#login()
     */
    public void login() {
    	loginUser();
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#logout()
     */
    public void logout() {
    	logoutUser();
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#getLogedUsers()
     */
    public List<String> getLogedUsers() {
    	return usersLogged;
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#createNewChatRoom(java.lang.String)
     */
    public String createNewChatRoom(String user) {
    	String room = UUIDGenerator.generate(""); // Used to unique identifying room
    	String actualUser = getThreadLocalRequest().getRemoteUser();
    	// Add users to rooms
    	addRoomToUser(room, user);
    	addPendingRoomToUser(room, user);
    	addRoomToUser(room, actualUser);
    	createMessageRoom(room);
    	createUserMessageRoom(room, user);
    	createUserMessageRoom(room, actualUser);
    	return room;
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#getPendingMessage(java.lang.String)
     */
    public List<String> getPendingMessage(String room) {
    	int countCycle = 0;
		List<String> pendingMessages = new ArrayList<String>();
		
		// 10 * Delay = 1000 = 1 second ( we want a 10 seconds waiting mantaining RPC comunication) that's 10*10=100 cycles
    	// With it mechanism
    	do  {
    		pendingMessages = getPendingUserRoomMessage(room);
			countCycle++;
    		try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
    	} while (pendingMessages.isEmpty() && (countCycle<100));
    	
    	return pendingMessages;
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#getPendingChatRoomUser()
     */
    public List<String> getPendingChatRoomUser() {
    	int countCycle = 0;
    	List<String> pendingRooms = new ArrayList<String>();
    	
    	// 10 * Delay = 1000 = 1 second ( we want a 10 seconds waiting mantaining RPC comunication) that's 10*10=100 cycles
    	// With it mechanism
    	do  {
    		pendingRooms = getPendingUserRoom();
			countCycle++;
    		try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
    	} while (pendingRooms.isEmpty() && (countCycle<100));
    	
    	return pendingRooms;
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#addMessageToRoom(java.lang.String, java.lang.String)
     */
    public void addMessageToRoom(String room, String msg) {
    	addUserMessageToRoom(room, msg);
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#closeRoom(java.lang.String)
     */
    public void closeRoom(String room) {
    	removeUserRoom(room);
    	removeUserMessageRoom(room);
    	deleteEmptyMessageRoom(room); // Evaluates if message room must be deleted
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#addUserToChatRoom(java.lang.String, java.lang.String)
     */
    public void addUserToChatRoom(String room, String user) {
    	addPendingRoomToUser(room, user);
    	addUserToChatRoom(room, user);
    }
    
    /* (non-Javadoc)
     * @see com.openkm.frontend.client.service.OKMChatService#usersInRoom(java.lang.String)
     */
    public String usersInRoom(String room) {
		System.out.println("number of users in room " + getNumberOfUsersInRoom(room));
    	return String.valueOf(getNumberOfUsersInRoom(room));
    }

    /**
     * loginUser
     */
    private synchronized void loginUser() {
    	String user = getThreadLocalRequest().getRemoteUser();
    	if (!usersLogged.contains(user)) {
    		usersLogged.add(user);
    	}
    	Collections.sort(usersLogged); // Always we sort logged users
    }
    
    /**
     * logoutUser
     */
    private synchronized void logoutUser() {
    	String user = getThreadLocalRequest().getRemoteUser();
    	if (usersLogged.contains(user)) {
    		usersLogged.remove(user);
    	}
    	if (pendingUsersRooms.containsKey(user)) {
    		pendingUsersRooms.remove(user);
    	}
    	if (usersRooms.containsKey(user)) {
    		List<String> rooms = usersRooms.get(user);
    		for (Iterator<String> it = rooms.iterator(); it.hasNext();) {
    			String room = it.next();
    			if (msgUsersRooms.containsKey(room)) {
    				Map<String, List<String>> roomMessages = msgUsersRooms.get(room);
    				if (roomMessages.containsKey(user)) {
    					roomMessages.remove(user);
    				}
    			}
    		}
    	}
    	// Could be needed some garbage collector ( because meanwhile finishing it could be added some message etc ... )
    	// Need more control on logout operation.
    	// Need to be removed msgUserRoom too !!!
    }
    
    /**
     * addUserToRoom
     * 
     * @param room
     * @param user
     */
    private synchronized void addRoomToUser(String room, String user) {
    	if (!usersRooms.keySet().contains(user)) {
    		List<String> userRoomList = new ArrayList<String>();
    		userRoomList.add(room);
    		usersRooms.put(user, userRoomList);
    	} else {
    		List<String> userRoomList = usersRooms.get(user);
    		if (!userRoomList.contains(room)) {
    			userRoomList.add(room);
    		}
    	}
    }
    
    /**
     * removeUserRoom
     * 
     * @param room
     */
    private synchronized void removeUserRoom(String room) {
    	String user = getThreadLocalRequest().getRemoteUser();
    	if (usersRooms.keySet().contains(user)) {
    		List<String> userRoomList = usersRooms.get(user);
    		if (userRoomList.contains(room)) {
    			userRoomList.remove(room);
    		}
    	}
    }
    
    /**
     * addPendingUserRoom
     * 
     * @param room
     * @param user
     */
    private synchronized void addPendingRoomToUser(String room, String user) {
    	if (!pendingUsersRooms.keySet().contains(user)) {
    		List<String> userPendingRoomList = new ArrayList<String>();
    		userPendingRoomList.add(room);
    		pendingUsersRooms.put(user, userPendingRoomList);
    	} else {
    		List<String> userPendingRoomList = pendingUsersRooms.get(user);
    		if (!userPendingRoomList.contains(room)) {
    			userPendingRoomList.add(room);
    		}
    	}
    }
    
    /**
     * getPendingUserRoom
     * 
     * @return
     */
    private synchronized List<String> getPendingUserRoom() {
    	String actualUser = getThreadLocalRequest().getRemoteUser();
    	if (pendingUsersRooms.keySet().contains(actualUser)) {
    		List<String> userRooms = pendingUsersRooms.get(actualUser);
    		pendingUsersRooms.remove(actualUser);
    		return userRooms;
    	} else {
    		return new ArrayList<String>();
    	}
    }
    
    /**
     * getPendingUserRoomMessage
     * 
     * @param room
     * @return
     */
    private synchronized List<String> getPendingUserRoomMessage(String room) {
    	String user = getThreadLocalRequest().getRemoteUser();
    	if (msgUsersRooms.containsKey(room) && msgUsersRooms.get(room).containsKey(user)) {
    		List<String> messages = msgUsersRooms.get(room).get(user);
    		msgUsersRooms.get(room).put(user, new ArrayList<String>()); // Empty messages
    		return messages;
    	} else {
    		return new ArrayList<String>();
    	}
    }
    
    /**
     * addUserMessageToRoom
     * 
     * @param room
     * @param msg
     */
    private synchronized void addUserMessageToRoom(String room, String msg) {
    	String actualUser = getThreadLocalRequest().getRemoteUser();
    	String message =  actualUser + ": " + msg;
    	if (msgUsersRooms.containsKey(room)) {
    		Map<String, List<String>> roomMap = msgUsersRooms.get(room);
    		for (Iterator<String> it = roomMap.keySet().iterator(); it.hasNext();) {
    			String user = it.next();
    			// Pending message is not added to himself ( that's done by UI )
    			if (!user.equals(actualUser)) {
    				roomMap.get(user).add(message); // Adding message for each user available
    			}
    		}
    	}
    }
    
    /**
     * createMessageRoom
     * 
     * @param room
     */
    private synchronized void createMessageRoom(String room) {
    	if (!msgUsersRooms.containsKey(room)) {
    		msgUsersRooms.put(room, new HashMap<String, List<String>>());
    	}
    }
    
    
    /**
     * createUserMessageRoom
     * 
     * @param room
     * @param user
     */
    private synchronized void createUserMessageRoom(String room, String user) {
    	if (msgUsersRooms.containsKey(room)) {
    		if (!msgUsersRooms.get(room).containsKey(user)) {
    			msgUsersRooms.get(room).put(user, new ArrayList<String>());
    		}
    	}
    }
    
    /**
     * removeUserMessageRoom
     * 
     * @param room
     */
    private synchronized void removeUserMessageRoom(String room) {
    	String user = getThreadLocalRequest().getRemoteUser();
    	if (msgUsersRooms.containsKey(room)) {
    		if (msgUsersRooms.get(room).containsKey(user)) {
    			msgUsersRooms.get(room).remove(user);
    		}
    	}
    }
    
    /**
     * evaluateDeleteMessageRoom
     * 
     * @param room
     */
    private synchronized void deleteEmptyMessageRoom(String room) {
    	// Room message without users must be deleted
    	if (msgUsersRooms.containsKey(room)) {
    		if (msgUsersRooms.get(room).keySet().size()==0) {
    			msgUsersRooms.remove(room);
    		}
    	}
    }
    
    /**
     * getNumberOfUsersInRoom
     * 
     * @param room
     * @return
     */
    private synchronized int getNumberOfUsersInRoom(String room) {
    	if (msgUsersRooms.containsKey(room)) {
    		return msgUsersRooms.get(room).keySet().size();
    	} else {
    		return 0;
    	}
    }
}