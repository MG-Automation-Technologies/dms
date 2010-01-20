package com.openkm.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMNotification;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Permission;
import com.openkm.bean.Version;
import com.openkm.module.direct.DirectRepositoryModule;

public class NotificationTest {
	private static Logger log = LoggerFactory.getLogger(BasicTest.class);

	/**
	 * 
	 */
	public void basic() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepex");
		
		Document doc = new Document();
		doc.setPath(okmRoot+"/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		Collection<String> notifyUsers = OKMNotification.getInstance().getSubscriptors(token, newDocument.getPath());
		log.info("Notify Users: "+notifyUsers);
		
		// Notify an user
		OKMNotification.getInstance().subscribe(token, newDocument.getPath());
		notifyUsers = OKMNotification.getInstance().getSubscriptors(token, newDocument.getPath());
		log.info("Notify Users: "+notifyUsers);

		doc.setKeywords("Hola, mundo!");
		OKMDocument.getInstance().setProperties(token, doc);
				
		// Notify an other user (again the same user)
		OKMNotification.getInstance().subscribe(token, newDocument.getPath());
		notifyUsers = OKMNotification.getInstance().getSubscriptors(token, newDocument.getPath());
		log.info("Notify Users: "+notifyUsers);
		
		// Remove notifications
		OKMNotification.getInstance().unsubscribe(token, newDocument.getPath());
		notifyUsers = OKMNotification.getInstance().getSubscriptors(token, newDocument.getPath());
		log.info("Notify Users: "+notifyUsers);

		OKMAuth.getInstance().logout(token);
	}

	/**
	 * 
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		log.info("### CREATE NEW FOLDER ###");
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/prueba");
		Folder newFolder = OKMFolder.getInstance().create(token, fld);
		log.info("New Folder: "+newFolder);
		
		// Notify an user this folder changes
		OKMNotification.getInstance().subscribe(token, fld.getPath());
		newFolder = OKMFolder.getInstance().getProperties(token, fld.getPath());
		log.info("Folder info: "+newFolder);

		// Add a new document
		log.info("### CREATE NEW DOCUMENT ###");
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		
		// Modify document property
		log.info("### MODIFY DOCUMENT PROPERTY ###");
		doc.setKeywords("Hola, mundo!");
		OKMDocument.getInstance().setProperties(token, doc);

		// Modify document content
		log.info("### MODIFY DOCUMENT CONTENT ###");
		OKMDocument.getInstance().checkout(token, doc.getPath());
		InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
		OKMDocument.getInstance().setContent(token, doc.getPath(), new ByteArrayInputStream("Esto ya es algo".getBytes()));
		Version ver = OKMDocument.getInstance().checkin(token, doc.getPath(), "sample comment");
		log.info("### NEW DOCUMENT VERSION: "+ver.getName()+" ###");

		// Delete document
		log.info("### DELETE DOCUMENT ###");
		OKMDocument.getInstance().delete(token, doc.getPath());

		OKMAuth.getInstance().logout(token);
	}

	/**
	 * 
	 */
	public void complex() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		log.info("### CREATE NEW FOLDER ###");
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/prueba");
		Folder newFolder = OKMFolder.getInstance().create(token, fld);
		log.info("New Folder: "+newFolder);
		
		// Add a new document
		log.info("### CREATE NEW DOCUMENT ###");
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		
		// Revoke privilegies
		log.info("### REVOKE PRIVILEGIES ###");
		OKMAuth.getInstance().revokeUser(token, doc.getPath(), "paco", Permission.WRITE, false);
		OKMAuth.getInstance().revokeUser(token, doc.getPath(), "paco", Permission.READ, false);
		log.info("Granted users: "+OKMAuth.getInstance().getGrantedUsers(token, doc.getPath()));
		log.info("### SUBSCRIBE WITH NO WRITE PERMISSION ###");
		log.info("Subscriptors: "+OKMNotification.getInstance().getSubscriptors(token, doc.getPath()));
		OKMNotification.getInstance().subscribe(token, doc.getPath());
		log.info("Subscriptors: "+OKMNotification.getInstance().getSubscriptors(token, doc.getPath()));
		
		// Modify document property
		log.info("### MODIFY DOCUMENT PROPERTY ###");
		doc.setKeywords("Hola, mundo!");
		OKMDocument.getInstance().setProperties(token, doc);

		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 * 
	 */
	public void lock() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String tokenPaco = OKMAuth.getInstance().login("paco", "pepe");
		String tokenJuan = OKMAuth.getInstance().login("juan", "palomo");
		
		log.info("### CREATE NEW FOLDER ###");
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/prueba");
		Folder newFolder = OKMFolder.getInstance().create(tokenPaco, fld);
		log.info("New Folder: "+newFolder);
		
		// Add a new document
		log.info("### CREATE NEW DOCUMENT ###");
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenPaco, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		
		// Lock & logout
		log.info("### LOCK ###");
		OKMDocument.getInstance().lock(tokenPaco, doc.getPath());
		log.info("isLocked: "+OKMDocument.getInstance().isLocked(tokenJuan, doc.getPath()));
		log.info("isLocked: "+OKMDocument.getInstance().getLock(tokenJuan, doc.getPath()));
				
		// Subscription
		log.info("### SUBSCRIBE WITH LOCK ###");
		log.info("Subscriptors: "+OKMNotification.getInstance().getSubscriptors(tokenJuan, doc.getPath()));
		OKMNotification.getInstance().subscribe(tokenJuan, doc.getPath());
		log.info("Subscriptors: "+OKMNotification.getInstance().getSubscriptors(tokenJuan, doc.getPath()));
		
		// Modify document property
		log.info("### MODIFY DOCUMENT PROPERTY ###");
		doc.setKeywords("Hola, mundo!");
		OKMDocument.getInstance().setProperties(tokenPaco, doc);

		OKMAuth.getInstance().logout(tokenJuan);
		OKMAuth.getInstance().logout(tokenPaco);
	}
}
