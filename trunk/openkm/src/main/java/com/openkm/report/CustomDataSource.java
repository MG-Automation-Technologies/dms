package com.openkm.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

import org.apache.jackrabbit.JcrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.Report;
import com.openkm.dao.bean.User;
import com.openkm.module.direct.DirectRepositoryModule;

public class CustomDataSource {
	private static Logger log = LoggerFactory.getLogger(CustomDataSource.class);
	
	public static Collection<Map<String, String>> getDataSource(Report rp) throws Exception {
		log.info("getDataSource({})", rp);
		
		if (rp.getFileName().equals("RegisteredUsers.jrxml")) {
			return registeredUsers();
		} else if (rp.getFileName().equals("LockedDocuments.jrxml")) {
			return lockedDocuments();
		} else if (rp.getFileName().equals("SubscribedDocuments.jrxml")) {
			return subscribedDocuments();
		} else {
			return new ArrayList<Map<String, String>>();
		}
	}
	
	/**
	 * Gets all the users data
	 */
	public static Collection<Map<String, String>> registeredUsers() throws DatabaseException {
		log.debug("reportUsers()");
		List<Map<String, String>> al = new ArrayList<Map<String, String>>();
		
		for (User user : AuthDAO.findAllUsers(false)) {
			Map<String, String> usr = new HashMap<String, String>();
			usr.put("id", user.getId());
			usr.put("name", user.getName());
			usr.put("email", user.getEmail());
			usr.put("roles", user.getRoles().toString());
			al.add(usr);
		}
		
		log.debug("reportUsers: {}", al);
		return al;
	}
	
	/**
	 * Gets all the locked documents data
	 */
	public static Collection<Map<String, String>> lockedDocuments() {
		log.debug("reportLockedDocuments()");
		List<Map<String, String>> al = new ArrayList<Map<String, String>>();
		String statement = "/jcr:root/okm:root//element(*,okm:document)[@jcr:lockOwner]/@jcr:lockOwner";
		String type = "xpath";
		
		Session jcrSession = DirectRepositoryModule.getSystemSession();
		Workspace workspace = jcrSession.getWorkspace();
		QueryManager queryManager;
		
		try {
			queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, type);
			QueryResult result = query.execute();
			
			for (RowIterator it = result.getRows(); it.hasNext();) {
				Map<String, String> ld = new HashMap<String, String>();
				Row row = it.nextRow();
				
				Value v = row.getValue(JcrConstants.JCR_LOCKOWNER);
				ld.put("owner", v==null?"NULL":v.getString());
				v = row.getValue(JcrConstants.JCR_PATH);
				ld.put("path", v==null?"NULL":v.getString());
				
				al.add(ld);
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		log.debug("reportLockedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Gets all the documents with subscriptors data
	 */
	public static Collection<Map<String, String>> subscribedDocuments() {
		log.debug("reportSubscribedDocuments()");
		List<Map<String, String>> al = new ArrayList<Map<String, String>>();
		String statement = "/jcr:root/okm:root//element(*, mix:notification)/@okm:subscriptors";
		String type = "xpath";
		
		Session jcrSession = DirectRepositoryModule.getSystemSession();
		Workspace workspace = jcrSession.getWorkspace();
		QueryManager queryManager;
		
		try {
			queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, type);
			QueryResult result = query.execute();
			
			for (RowIterator it = result.getRows(); it.hasNext();) {
				Map<String, String> dob = new HashMap<String, String>();
				Row row = it.nextRow();
				
				Value v = row.getValue(JcrConstants.JCR_PATH);
				dob.put("path", v==null?"NULL":v.getString());
				
				String path = row.getValue(JcrConstants.JCR_PATH).getString();
				Node node = jcrSession.getRootNode().getNode(path.substring(1));
				Property prop = node.getProperty("okm:subscriptors");
				Value[] values = prop.getValues();
				StringBuilder sb = new StringBuilder();
				
				for (int i=0; i<values.length-1; i++) {
					sb.append(values[i].getString());
					sb.append(", ");
				}
				sb.append(values[values.length-1].getString());
				dob.put("subscriptors", sb.toString());
				
				al.add(dob);
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		log.debug("reportSubscribedDocuments: {}", al);
		return al;
	}
}
