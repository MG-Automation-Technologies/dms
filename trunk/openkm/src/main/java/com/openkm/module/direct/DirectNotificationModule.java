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

package com.openkm.module.direct;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.mail.MessagingException;

import org.apache.commons.httpclient.HttpException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Notification;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.TwitterAccount;
import com.openkm.module.NotificationModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.MailUtils;
import com.openkm.util.UserActivity;

public class DirectNotificationModule implements NotificationModule {
	private static Logger log = LoggerFactory.getLogger(DirectNotificationModule.class);
	
	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#subscribe(java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized void subscribe(String token, String nodePath) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("subscribe(" + token + ", " + nodePath + ")");
		Node node = null;
		Node sNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Session systemSession = DirectRepositoryModule.getSystemSession();
			node = session.getRootNode().getNode(nodePath.substring(1));
			sNode = systemSession.getNodeByUUID(node.getUUID());
			
			// Perform subscription
			if (node.isNodeType(Notification.TYPE)) {
				Value[] actualUsers = node.getProperty(Notification.SUBSCRIPTORS).getValues();
				String[] newUsers = new String[actualUsers.length+1];
				boolean alreadyAdded = false;

				for (int i=0; i<actualUsers.length; i++) {
					newUsers[i] = actualUsers[i].getString();
					
					// Don't add a user twice
					if (actualUsers[i].getString().equals(session.getUserID())) {
						alreadyAdded = true;
					}
				}
				
				if (!alreadyAdded) {
					newUsers[newUsers.length-1] = session.getUserID();
					sNode.setProperty(Notification.SUBSCRIPTORS, newUsers);
				}
			} else {
				sNode.addMixin(Notification.TYPE);
				sNode.setProperty(Notification.SUBSCRIPTORS, new String[] { session.getUserID() });
			}
			
			sNode.save();
			
			// Activity log
			UserActivity.log(session, "SUBSCRIBE_USER", nodePath, null);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("subscribe: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#unsubscribe(java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized void unsubscribe(String token, String nodePath) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("unsubscribe(" + token + ", " + nodePath + ")");
		Node node = null;
		Node sNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			Session systemSession = DirectRepositoryModule.getSystemSession();
			node = session.getRootNode().getNode(nodePath.substring(1));
			sNode = systemSession.getNodeByUUID(node.getUUID());

			// Perform unsubscription
			if (node.isNodeType(Notification.TYPE)) {
				Value[] actualUsers = node.getProperty(Notification.SUBSCRIPTORS).getValues();
				ArrayList<String> newUsers = new ArrayList<String>();
				
				for (int i=0; i<actualUsers.length; i++) {
					if (!actualUsers[i].getString().equals(session.getUserID())) {
						newUsers.add(actualUsers[i].getString());
					}
				}
				
				if (newUsers.isEmpty()) {
					sNode.removeMixin(Notification.TYPE);			
				} else {
					sNode.setProperty(Notification.SUBSCRIPTORS, (String[])newUsers.toArray(new String[0]));
				}			
			}

			sNode.save();

			// Activity log
			UserActivity.log(session, "UNSUBSCRIBE_USER", nodePath, null);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("unsubscribe: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#getSubscriptors(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<String> getSubscriptors(String token, String nodePath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("getSusbcriptions(" + token + ", " + nodePath + ")");
		ArrayList<String> users = new ArrayList<String>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node node = session.getRootNode().getNode(nodePath.substring(1));
			
			if (node.isNodeType(Notification.TYPE)) {
				Value[] notifyUsers = node.getProperty(Notification.SUBSCRIPTORS).getValues();
			
				for (int i=0; i<notifyUsers.length; i++) {
					users.add(notifyUsers[i].getString());
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getSusbcriptions: "+users);
		return users;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#notify(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public void notify(String token, String nodePath, Collection<String> users, String message) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("notify("+token+", "+nodePath+", "+users+", "+message+")");
		
		if (!users.isEmpty()) {
			try {
				Session session = SessionManager.getInstance().get(token);
				log.debug("Nodo: "+nodePath+", Message: "+message);
				Collection<String> emails = new DirectAuthModule().getMails(null, users);
				
				// Get session user email address
				ArrayList<String> dummy = new ArrayList<String>();
				dummy.add(session.getUserID());
				ArrayList<String> from = (ArrayList<String>) new DirectAuthModule().getMails(null, dummy);
				
				if (!emails.isEmpty() && !from.isEmpty()) {
					StringWriter swSubject = new StringWriter();
					StringWriter swBody = new StringWriter();
					
					VelocityContext context = new VelocityContext();
					context.put("documentUrl", Config.APPLICATION_URL+"?docPath=" + URLEncoder.encode(nodePath, "UTF-8"));
					context.put("documentPath", nodePath);
					context.put("documentName", FileUtils.getName(nodePath));
					context.put("userId", session.getUserID());
					context.put("notificationMessage", message);
					
					if (Velocity.resourceExists(Config.NOTIFICATION_MESSAGE_SUBJECT)) {
						Velocity.mergeTemplate(Config.NOTIFICATION_MESSAGE_SUBJECT, "UTF-8", context, swSubject);
					} else {
						Velocity.evaluate(context, swSubject, "NotificationMessageSubject", Config.NOTIFICATION_MESSAGE_SUBJECT);	
					}
					
					if (Velocity.resourceExists(Config.NOTIFICATION_MESSAGE_BODY)) {
						Velocity.mergeTemplate(Config.NOTIFICATION_MESSAGE_BODY, "UTF-8", context, swSubject);
					} else {
						Velocity.evaluate(context, swBody, "NotificationMessageBody", Config.NOTIFICATION_MESSAGE_BODY);	
					}

					MailUtils.send((String) from.get(0), emails, swSubject.toString(), swBody.toString());
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		log.debug("notify: void");
	}
		
	/**
	 * Check for user subscriptions and send an notification
	 * 
	 * @param node Node modified (Document or Folder)
	 * @param user User who generated the modification event
	 * @param eventType Type of modification event
	 */
	public static void checkSubscriptions(Node node, String user, String eventType, String comment) {
		log.debug("checkSubscriptions("+node+", "+user+", "+eventType+", "+comment+")");
		Collection<String> users = null;
		
		try {
			users = checkSubscriptionsHelper(node);
		} catch (javax.jcr.RepositoryException e1) {
			e1.printStackTrace();
		}
		
		/**
		 * Mail notification
		 */
		try {
			if (users != null && !users.isEmpty()) {
				Collection<String> emails = new DirectAuthModule().getMails(null, users);
					
				if (!emails.isEmpty()) {
					if (comment == null) { comment = ""; }
					StringWriter swSubject = new StringWriter();
					StringWriter swBody = new StringWriter();
					
					VelocityContext context = new VelocityContext();
					context.put("documentUrl", Config.APPLICATION_URL+"?docPath=" + URLEncoder.encode(node.getPath(), "UTF-8"));
					context.put("documentPath", node.getPath());
					context.put("documentName", node.getName());
					context.put("userId", user);
					context.put("eventType", eventType);
					context.put("subscriptionComment", comment);
					
					if (Velocity.resourceExists(Config.SUBSCRIPTION_MESSAGE_SUBJECT)) {
						Velocity.mergeTemplate(Config.SUBSCRIPTION_MESSAGE_SUBJECT, "UTF-8", context, swSubject);
					} else {
						Velocity.evaluate(context, swSubject, "SubscriptionMessageSubject", Config.SUBSCRIPTION_MESSAGE_SUBJECT);	
					}
					
					if (Velocity.resourceExists(Config.SUBSCRIPTION_MESSAGE_BODY)) {
						Velocity.mergeTemplate(Config.SUBSCRIPTION_MESSAGE_BODY, "UTF-8", context, swBody);
					} else {
						Velocity.evaluate(context, swBody, "SubscriptionMessageBody", Config.SUBSCRIPTION_MESSAGE_BODY);
					}
					
					MailUtils.send(emails, swSubject.toString(), swBody.toString());
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (ValueFormatException e) {
			e.printStackTrace();
		} catch (javax.jcr.PathNotFoundException e) {
			e.printStackTrace();
		} catch (javax.jcr.RepositoryException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * Twitter notification
		 */
		try {
			if (users != null && !users.isEmpty() && !Config.SUBSCRIPTION_TWITTER_USER.equals("") && !Config.SUBSCRIPTION_TWITTER_PASSWORD.equals("")) {
				Twitter twitter = new Twitter(Config.SUBSCRIPTION_TWITTER_USER, Config.SUBSCRIPTION_TWITTER_PASSWORD);
				StringWriter swStatus = new StringWriter();
				
				VelocityContext context = new VelocityContext();
				context.put("documentUrl", MailUtils.getTinyUrl(Config.APPLICATION_URL+"?docPath="+node.getPath()));
				context.put("documentPath", node.getPath());
				context.put("documentName", node.getName());
				context.put("userId", user);
				context.put("eventType", eventType);
				context.put("subscriptionComment", comment);

				if (Velocity.resourceExists(Config.SUBSCRIPTION_TWITTER_STATUS)) {
					Velocity.mergeTemplate(Config.SUBSCRIPTION_TWITTER_STATUS, "UTF-8", context, swStatus);
				} else {
					Velocity.evaluate(context, swStatus, "SubscriptionTwitterStatus", Config.SUBSCRIPTION_TWITTER_STATUS);	
				}
				
				AuthDAO auth = AuthDAO.getInstance();
				
				for (Iterator<String> itUsers = users.iterator(); itUsers.hasNext(); ) {
					String itUser = itUsers.next();
					Collection<TwitterAccount> twitterAccounts = auth.findTwitterAccountsByUser(itUser, true);
					
					for (Iterator<TwitterAccount> itTwitter = twitterAccounts.iterator(); itTwitter.hasNext(); ) {
						TwitterAccount ta = itTwitter.next();
						log.info("Twitter Notify from "+twitter.getUserId()+" to "+ta.getTwitterUser()+" ("+itUser+") - "+swStatus.toString());
						twitter.sendDirectMessage(ta.getTwitterUser(), swStatus.toString());
					}
				}
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (javax.jcr.RepositoryException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.debug("checkSubscriptions: void");
	}
	
	/**
	 * @param node
	 * @return
	 * @throws javax.jcr.RepositoryException
	 */
	private static Collection<String> checkSubscriptionsHelper(Node node) throws javax.jcr.RepositoryException {
		log.debug("checkSubscriptionsHelper: "+node.getPath());
		ArrayList<String> al = new ArrayList<String>();
		
		if (node.isNodeType(Folder.TYPE) || node.isNodeType(Document.TYPE)) {
			if (node.isNodeType(Notification.TYPE)) {
				Value[] subscriptors = node.getProperty(Notification.SUBSCRIPTORS).getValues();
			
				for (int i=0; i<subscriptors.length; i++) {
					al.add(subscriptors[i].getString());
				}
			}
			
			// An user shouldn't be notified twice
			Collection<String> tmp = checkSubscriptionsHelper(node.getParent());
			for (Iterator<String> it = tmp.iterator(); it.hasNext(); ) {
				String usr = it.next();
				if (!al.contains(usr)) {
					al.add(usr);
				}
			}
		}
		
		return al;
	}
}
