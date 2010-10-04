package com.openkm.servlet;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.jackrabbit.server.BasicCredentialsProvider;
import org.apache.jackrabbit.server.CredentialsProvider;

import com.openkm.module.direct.DirectRepositoryModule;

public class BasicSecuredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CredentialsProvider cp = new BasicCredentialsProvider(null);
	
	/**
	 * Get JCR session
	 */
	public synchronized Session getSession(HttpServletRequest request)	throws LoginException,
			javax.jcr.RepositoryException, ServletException {
		Credentials creds = cp.getCredentials(request);
		Repository rep = DirectRepositoryModule.getRepository();

		if (creds == null) {
			return rep.login();
		} else {
			return rep.login(creds);
		}
	}
}
