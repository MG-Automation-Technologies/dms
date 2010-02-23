package com.openkm.api;

import junit.framework.TestCase;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserAlreadyLoggerException;

public class AuthTest extends TestCase {

	public void testLogin() throws UserAlreadyLoggerException, AccessDeniedException, RepositoryException {
		OKMAuth okmAuth = OKMAuth.getInstance();
		String token = okmAuth.login("okmAdmin", "admin");
		okmAuth.logout(token);
	}
}
