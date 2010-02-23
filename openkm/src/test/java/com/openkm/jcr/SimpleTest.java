package com.openkm.jcr;

import java.io.IOException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import junit.framework.TestCase;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.TransientRepository;

public class SimpleTest extends TestCase {
	public SimpleTest(String name) {
		super(name);
	}
	
	public void testSimple() throws IOException, LoginException, RepositoryException {
		Repository r = new TransientRepository("src/test/resources/repository.xml", "repository");
		Session s = r.login(new SimpleCredentials("userid", "".toCharArray()));
		Node rn = s.getRootNode();
		//UserManager um = ((SessionImpl) s).getUserManager();
		//User user = um.createUser("john", "doe");
			
		/*
		AccessControlManager acm = ((SessionImpl) s).getAccessControlManager();
		for (AccessControlPolicyIterator it = acm.getApplicablePolicies(root.getPath()); it.hasNext(); ) {
			AccessControlPolicy acp = it.nextAccessControlPolicy();
			Privilege[] privileges = new Privilege[]{ acm.privilegeFromName(Privilege.JCR_WRITE) };
			((AccessControlList) acp).addAccessControlEntry(new PrincipalImpl(user.getID()), privileges);
			acm.setPolicy(root.getPath(), acp);
		
		*/
			
		// apply the policy 
		s.save();
		s.logout();
	}
}
