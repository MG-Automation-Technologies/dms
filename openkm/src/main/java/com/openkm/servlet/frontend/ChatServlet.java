/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.servlet.frontend;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.openkm.core.ChatManager;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.service.OKMChatService;

/**
 * Servlet Class
 */
public class ChatServlet extends OKMRemoteServiceServlet implements OKMChatService {
	private static final long serialVersionUID = 3780857624687394918L;
	private static final int DELAY = 1000; // mseg
	private static final ChatManager manager = new ChatManager();
	
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	public void login() throws OKMException {
		updateSessionManager();
		String user = getThreadLocalRequest().getRemoteUser();
		
		if (user != null) {
			manager.login(user);
		}
	}
	
	@Override
	public void logout() throws OKMException {
		updateSessionManager();
		String user = getThreadLocalRequest().getRemoteUser();
		
		if (user != null) {
			manager.logout(user);
		}
	}
	
	@Override
	public List<String> getLoggedUsers() {
		updateSessionManager();
		return manager.getLoggedUsers();
	}
	
	@Override
	public String createNewChatRoom(String user) {
		updateSessionManager();
		String actualUser = getThreadLocalRequest().getRemoteUser();
		return manager.createNewChatRoom(actualUser, user);
	}
	
	@Override
	public List<String> getPendingMessage(String room) {
		updateSessionManager();
		String user = getThreadLocalRequest().getRemoteUser();
		List<String> pendingMessages = new ArrayList<String>();
		
		if (user != null) {
			int countCycle = 0;
			
			// 10 * Delay = 1000 = 1 second ( we want a 10 seconds waiting
			// mantaining RPC comunication) that's 10*10=100 cycles
			do {
				pendingMessages = manager.getPendingMessage(user, room);
				countCycle++;
				
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (pendingMessages.isEmpty() && (countCycle < 100) && manager.getLoggedUsers().contains(user));
		}
		
		return pendingMessages;
	}
	
	@Override
	public List<String> getPendingChatRoomUser() {
		updateSessionManager();
		String user = getThreadLocalRequest().getRemoteUser();
		List<String> pendingRooms = new ArrayList<String>();
		
		if (user != null) {
			int countCycle = 0;
			
			// 10 * Delay = 1000 = 1 second ( we want a 10 seconds waiting
			// mantaining RPC comunication) that's 10*10=100 cycles
			do {
				pendingRooms = manager.getPendingChatRoomUser(user);
				countCycle++;
				
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (pendingRooms.isEmpty() && (countCycle < 100) && manager.getLoggedUsers().contains(user));
		}
		
		return pendingRooms;
	}
	
	@Override
	public void addMessageToRoom(String room, String msg) {
		updateSessionManager();
		String user = getThreadLocalRequest().getRemoteUser();
		
		if (user != null) {
			manager.addMessageToRoom(user, room, msg);
		}
	}
	
	@Override
	public void closeRoom(String room) {
		updateSessionManager();
		String user = getThreadLocalRequest().getRemoteUser();
		
		if (user != null) {
			manager.closeRoom(user, room);
		}
	}
	
	@Override
	public void addUserToChatRoom(String room, String user) {
		updateSessionManager();
		manager.addUserToChatRoom(user, room);
	}
	
	@Override
	public String usersInRoom(String room) {
		updateSessionManager();
		return String.valueOf(manager.getNumberOfUsersInRoom(room));
	}
	
	@Override
	public List<String> getUsersInRoom(String room) {
		updateSessionManager();
		return manager.getUsersInRoom(room);
	}
	
	/**
	 * getChatManager
	 */
	public static ChatManager getChatManager() {
		return manager;
	}
}
