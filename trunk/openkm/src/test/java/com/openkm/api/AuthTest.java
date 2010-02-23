package com.openkm.api;

import junit.framework.TestCase;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserAlreadyLoggerException;
import com.openkm.module.direct.DirectRepositoryModule;

public class AuthTest extends TestCase {

	public AuthTest(String name) {
		super(name);
	}

	protected void setUp() {
		Config.load();
		Config.HOME_DIR = "src/test/resources";
	}

	public void testLogin() throws UserAlreadyLoggerException, AccessDeniedException, RepositoryException {
		OKMAuth okmAuth = OKMAuth.getInstance();
		String token = okmAuth.login("okmAdmin", "admin");
		okmAuth.logout(token);
	}
	
	protected void tearDown() {
		DirectRepositoryModule.shutdown();
	}
}
