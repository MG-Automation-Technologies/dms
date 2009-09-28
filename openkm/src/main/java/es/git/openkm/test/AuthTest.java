package es.git.openkm.test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMAuth;
import es.git.openkm.api.OKMDocument;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Permission;
import es.git.openkm.module.direct.DirectRepositoryModule;

public class AuthTest {
	private static Logger log = LoggerFactory.getLogger(BasicTest.class);

	/**
	 * 
	 */
	public void basic() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Document doc = new Document();
		doc.setPath(okmRoot+"/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		HashMap<String, Byte> grantedUsers = OKMAuth.getInstance().getGrantedUsers(token, newDocument.getPath());
		log.info("Granted Users: "+grantedUsers);
		
		// Grant an other user
		OKMAuth.getInstance().grantUser(token, newDocument.getPath(), "pepe", Permission.READ, false);
		grantedUsers = OKMAuth.getInstance().getGrantedUsers(token, newDocument.getPath());
		log.info("Granted Users: "+grantedUsers);

		// Grant an other user (again the same user)
		OKMAuth.getInstance().grantUser(token, newDocument.getPath(), "pepe", Permission.READ, false);
		grantedUsers = OKMAuth.getInstance().getGrantedUsers(token, newDocument.getPath());
		log.info("Granted Users: "+grantedUsers);

		// Revoke user
		OKMAuth.getInstance().revokeUser(token, newDocument.getPath(), "pepez", Permission.READ, false);
		grantedUsers = OKMAuth.getInstance().getGrantedUsers(token, newDocument.getPath());
		log.info("Granted Users: "+grantedUsers);

		OKMAuth.getInstance().logout(token);
	}

	/**
	 * 
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String tokenPaco = OKMAuth.getInstance().login("paco", "pepex");
		String tokenPepe = OKMAuth.getInstance().login("pepe", "pepex");
		
		Document doc = new Document();
		doc.setPath(okmRoot+"/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenPaco, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		HashMap<String, Byte> grantedUsers = OKMAuth.getInstance().getGrantedUsers(tokenPaco, newDocument.getPath());
		log.info("Granted Users: "+grantedUsers);

		// Paco read document permissions
		Document pacoDocument = OKMDocument.getInstance().getProperties(tokenPaco, newDocument.getPath());
		log.info("Paco Document READ: "+((pacoDocument.getPermissions() & Permission.READ) == Permission.READ));
		log.info("Paco Document WRITE: "+((pacoDocument.getPermissions() & Permission.WRITE) == Permission.WRITE));
		
		// Pepe read document permissions
		Document pepeDocument = OKMDocument.getInstance().getProperties(tokenPepe, newDocument.getPath());
		log.info("Pepe Document READ: "+((pepeDocument.getPermissions() & Permission.READ) == Permission.READ));
		log.info("Pepe Document WRITE: "+((pepeDocument.getPermissions() & Permission.WRITE) == Permission.WRITE));
		
		// Grant an other user
		//new OKMAuth().grantUser(tokenPaco, newDocument.getPath(), "pepe", Permission.READ);
		//grantedUsers = new OKMAuth().getGrantedUsers(tokenPaco, newDocument.getPath());
		//log.info("Granted Users: "+grantedUsers);

		OKMAuth.getInstance().logout(tokenPaco);
		OKMAuth.getInstance().logout(tokenPepe);
	}

	/**
	 * 
	 */
	public void complex() throws Exception {
	}
}
