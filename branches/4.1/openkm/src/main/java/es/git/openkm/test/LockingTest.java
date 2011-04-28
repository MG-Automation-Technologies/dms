package es.git.openkm.test;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMAuth;
import es.git.openkm.api.OKMDocument;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Lock;
import es.git.openkm.core.LockException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.VersionException;
import es.git.openkm.module.direct.DirectAuthModule;
import es.git.openkm.module.direct.DirectRepositoryModule;

public class LockingTest {
	private static Logger log = LoggerFactory.getLogger(LockingTest.class);
	
	/**
	 * 
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String tokenUno = OKMAuth.getInstance().login("paco", "pepe");
		String tokenDos = OKMAuth.getInstance().login("linus", "tux");

		// Create document
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenUno, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		boolean locked = OKMDocument.getInstance().isLocked(tokenUno, newDocument.getPath());
		System.out.println("isLocked: "+locked);
		log.info("************ LOCK: BEGIN ********");
		OKMDocument.getInstance().lock(tokenDos, newDocument.getPath());
		log.info("************ LOCK: END ********");
		locked = OKMDocument.getInstance().isLocked(tokenUno, newDocument.getPath());
		System.out.println("isLocked: "+locked);
		
		Lock lock = OKMDocument.getInstance().getLock(tokenUno, newDocument.getPath());
		System.out.println("lock: "+lock);
		
		// Try to modify document
		try {
			OKMDocument.getInstance().checkout(tokenDos, newDocument.getPath());
		} catch (LockException e) {
			log.error(e.getMessage());
		}
		
		doc.setKeywords("versión nueva");
		
		try {
			OKMDocument.getInstance().setProperties(tokenUno, doc);
		} catch (LockException e) {
			log.error(e.getMessage());
		}
		
		try {
			OKMDocument.getInstance().setContent(tokenUno, newDocument.getPath(), new ByteArrayInputStream("Esto es una prueba de versión 1".getBytes()));
		} catch (VersionException e) {
			log.error(e.getMessage());
		}
		
		OKMAuth.getInstance().logout(tokenUno);
		OKMAuth.getInstance().logout(tokenDos);
	}
	
	/**
	 * 
	 */
	public void multiple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String tokenUno = OKMAuth.getInstance().login("paco", "pepe");
		String tokenDos = OKMAuth.getInstance().login("paco", "pepe");

		// Create document
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenUno, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		boolean locked = OKMDocument.getInstance().isLocked(tokenUno, newDocument.getPath());
		System.out.println("isLocked: "+locked);
		log.info("************ LOCK: BEGIN ********");
		OKMDocument.getInstance().lock(tokenDos, newDocument.getPath());
		log.info("************ LOCK: END ********");
		locked = OKMDocument.getInstance().isLocked(tokenUno, newDocument.getPath());
		System.out.println("isLocked: "+locked);
		Lock lock = OKMDocument.getInstance().getLock(tokenUno, newDocument.getPath());
		System.out.println("lock: "+lock);
		log.info("************ UNLOCK: BEGIN ********");
		OKMDocument.getInstance().unlock(tokenDos, newDocument.getPath());
		log.info("************ UNLOCK: END ********");
	
		try {
			OKMAuth.getInstance().logout(tokenUno);
			OKMAuth.getInstance().logout(tokenDos);
		} catch (RepositoryException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 *
	 */
	public void intersession() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Token: "+token);
		
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		// Lock
		log.info("*************************************************");
		new DirectAuthModule().view(token);
		log.info("Lock...");
		OKMDocument.getInstance().lock(token, newDocument.getPath());
		new DirectAuthModule().view(token);
		Lock lock = OKMDocument.getInstance().getLock(token, newDocument.getPath());
		log.info("Lock: "+lock);
		log.info("Unlock...");
		OKMDocument.getInstance().unlock(token, newDocument.getPath());
		new DirectAuthModule().view(token);
		OKMAuth.getInstance().logout(token);
		log.info("*************************************************");
				
		// Lock
		token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Lock...");
		OKMDocument.getInstance().lock(token, newDocument.getPath());
		new DirectAuthModule().view(token);

		log.info("Logout...");
		OKMAuth.getInstance().logout(token);
		
		log.info("Login...");
		token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Token: "+token);
		new DirectAuthModule().view(token);
		
		// Unlock
		lock = OKMDocument.getInstance().getLock(token, newDocument.getPath());
		log.info("Lock: "+lock);
		log.info("Unlock...");
		OKMDocument.getInstance().unlock(token, newDocument.getPath());
		new DirectAuthModule().view(token);
		
		// Logout
		OKMAuth.getInstance().logout(token);
	}
}