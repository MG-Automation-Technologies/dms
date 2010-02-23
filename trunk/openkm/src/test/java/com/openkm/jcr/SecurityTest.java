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
import org.apache.jackrabbit.api.jsr283.security.AccessControlEntry;
import org.apache.jackrabbit.api.jsr283.security.AccessControlList;
import org.apache.jackrabbit.api.jsr283.security.AccessControlManager;
import org.apache.jackrabbit.api.jsr283.security.AccessControlPolicy;
import org.apache.jackrabbit.api.jsr283.security.AccessControlPolicyIterator;
import org.apache.jackrabbit.api.jsr283.security.Privilege;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.security.authorization.JackrabbitAccessControlList;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;
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
		//test.testGrant();
		test.testRevoke();
		test.tearDown();
	}

	@Override
	protected void setUp() {
		log.info("setUp()");
		log.info("Delete repository: " + Config.REPOSITORY_HOME);
		FileUtils.deleteQuietly(new File(Config.REPOSITORY_HOME));
	}

	@Override
	protected void tearDown() {
		log.info("tearDown()");
	}

	/**
	 * 
	 */
	public void testGrant() throws IOException, LoginException, RepositoryException {
		log.info("testGrant()");
		Repository repository = new TransientRepository(Config.REPOSITORY_CONFIG, Config.REPOSITORY_HOME);
		Session sAdmin = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		Node rootNode = sAdmin.getRootNode();
		Node grantedNode = rootNode.addNode("granted");
		rootNode.save();
		log.info("Granted node: " + grantedNode.getPath());

		// User creation
		UserManager userManager = ((SessionImpl) sAdmin).getUserManager();
		User test = userManager.createUser("test", "test");
		User testBis = (User) userManager.getAuthorizable("test");
		assertEquals(test.getPrincipal(), testBis.getPrincipal());
		
		// Grant test user
		AccessControlManager acm = ((SessionImpl) sAdmin).getAccessControlManager();
		AccessControlPolicyIterator acpi = acm.getApplicablePolicies(grantedNode.getPath());
		while (acpi.hasNext()) {
			AccessControlPolicy acp = acpi.nextAccessControlPolicy();
			Privilege[] privileges = new Privilege[] { acm.privilegeFromName(Privilege.JCR_WRITE) };
			printPrivileges(log, privileges);
			
			((AccessControlList) acp).addAccessControlEntry(new PrincipalImpl("test"), privileges);
			AccessControlEntry[] ace = ((AccessControlList) acp).getAccessControlEntries();
			
			for (int i=0; i<ace.length; i++) {
				log.info("AccessControlEntry: "+i);
				log.info("Principal: "+ace[i].getPrincipal().getName());
				printPrivileges(log, ace[i].getPrivileges());
			}
			
			acm.setPolicy(grantedNode.getPath(), acp);
		}

		// Apply the policy
		sAdmin.save();

		// Test user login
		Session sessionTest = repository.login(new SimpleCredentials("test", "test".toCharArray()));
		Node testRootNode = sessionTest.getRootNode();
		testRootNode.getNode(grantedNode.getPath().substring(1)).addNode("my node");
		testRootNode.save();
		sessionTest.logout();

		// Admin logout
		sAdmin.logout();
	}

	/**
	 * 
	 */
	public void testRevoke() throws IOException, LoginException, RepositoryException {
		log.info("testRevoke()");
		Repository repository = new TransientRepository(Config.REPOSITORY_CONFIG, Config.REPOSITORY_HOME);
		Session sAdmin = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		Node rootNode = sAdmin.getRootNode();
		Node revokedNode = rootNode.addNode("revoked");
		log.info("Revoked node: " + revokedNode.getPath());

		// User creation
		UserManager userManager = ((SessionImpl) sAdmin).getUserManager();
		User anonymous = (User) userManager.getAuthorizable("anonymous"); 

		// Revoke anonymous user
		AccessControlManager acm = ((SessionImpl) sAdmin).getAccessControlManager();
		AccessControlPolicyIterator acpi = acm.getApplicablePolicies(revokedNode.getPath());
		JackrabbitAccessControlList jacl = (JackrabbitAccessControlList) acpi.nextAccessControlPolicy();
		Privilege[] privileges = acm.getSupportedPrivileges(revokedNode.getPath());
		printPrivileges(log, privileges);

		// Also possible to set a map of restrictions
		jacl.addEntry(anonymous.getPrincipal(), privileges, true);
		acm.setPolicy(revokedNode.getPath(), jacl);
		
		// Apply the policy
		sAdmin.save();
		
		// Test user login
		Session sAnonymous = repository.login(new SimpleCredentials("anonymous", "anonymous".toCharArray()));
		Node anonRootNode = sAnonymous.getRootNode();
		anonRootNode.getNode(revokedNode.getPath().substring(1)).addNode("my node");
		anonRootNode.save();
		sAnonymous.logout();
		
		// Admin logout
		sAdmin.logout();
	}
	
	/**
	 * 
	 */
	private void printPrivileges(Logger log, Privilege[] privileges) {
		for (int i=0; i<privileges.length; i++) {
			log.info("Privilege: "+privileges[i].getName()+", aggregated: "+privileges[i].isAggregate());
			Privilege[] aggregates = privileges[i].getAggregatePrivileges();
			for (int j=0; j<aggregates.length; j++) {
				log.info("Aggregate privilege: "+aggregates[j].getName()+", aggregated: "+aggregates[j].isAggregate());
			}
		}
	}
}
