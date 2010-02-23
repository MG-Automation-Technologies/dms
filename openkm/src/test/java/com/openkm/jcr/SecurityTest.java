package com.openkm.jcr;

import java.io.File;
import java.io.IOException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.jackrabbit.api.jsr283.security.AccessControlManager;
import org.apache.jackrabbit.api.jsr283.security.AccessControlPolicyIterator;
import org.apache.jackrabbit.api.jsr283.security.Privilege;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.security.authorization.JackrabbitAccessControlList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityTest extends TestCase {
	private static Logger log = LoggerFactory.getLogger(SecurityTest.class);

	public SecurityTest(String name) {
		super(name);
	}

	public static void main(String[] args) throws Exception {
		SecurityTest test = new SecurityTest("main");
		test.setUp();
		test.testSimple();
		test.tearDown();
	}
	
	@Override
	protected void setUp() {
	}

	@Override
	protected void tearDown() {
		log.info("Delete repository: " + Config.REPOSITORY_HOME);
		FileUtils.deleteQuietly(new File(Config.REPOSITORY_HOME));
	}

	public void testSimple() throws IOException, LoginException, RepositoryException {
		Repository repository = new TransientRepository(Config.REPOSITORY_CONFIG, Config.REPOSITORY_HOME);
		Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		Node rootNode = session.getRootNode();
		Node restrictedNode = rootNode.addNode("restricted");
		log.info("Restricted node: " + restrictedNode.getPath());

		// User management
		UserManager userManager = ((SessionImpl) session).getUserManager();
		User anonymous = (User) userManager.getAuthorizable("anonymous");
		log.info("User anonymous: "+anonymous.getID());
		User test = (User) userManager.getAuthorizable("test");
		log.info("User test: "+test);
		if (test == null) {
			test = userManager.createUser("test", "test");
			log.info("Created user: "+test.getID());
		}

		// Right management
		AccessControlManager accessControlManager = ((SessionImpl) session).getAccessControlManager();

		// forbid the view of the restricted area to anonymous
		String restrictedArea = restrictedNode.getPath();
		AccessControlPolicyIterator restrictedPolicies = accessControlManager
				.getApplicablePolicies(restrictedArea);
		JackrabbitAccessControlList restrictedPolicy = (JackrabbitAccessControlList) restrictedPolicies
				.nextAccessControlPolicy();
		Privilege[] previewPrivileges = accessControlManager.getSupportedPrivileges(restrictedArea);

		// Also possible to set a map of restrictions
		restrictedPolicy.addEntry(anonymous.getPrincipal(), previewPrivileges, false);
		accessControlManager.setPolicy(restrictedArea, restrictedPolicy);

		// apply the policy
		session.save();
		session.logout();
	}
}
