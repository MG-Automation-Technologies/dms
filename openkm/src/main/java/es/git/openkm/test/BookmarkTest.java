package es.git.openkm.test;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMAuth;
import es.git.openkm.api.OKMBookmark;
import es.git.openkm.api.OKMDocument;
import es.git.openkm.bean.Bookmark;
import es.git.openkm.bean.Document;
import es.git.openkm.module.direct.DirectRepositoryModule;

public class BookmarkTest {
	private static Logger log = LoggerFactory.getLogger(BookmarkTest.class);

	/**
	 * 
	 */
	public void basic() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		log.info("### CREATE NEW FOLDER ###");
		Document doc = new Document();
		doc.setPath(okmRoot+"/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		
		// Add bookmark
		log.info("### ADD BOOKMARK ###");
		Bookmark bm = OKMBookmark.getInstance().add(token, doc.getPath(), "pruebas *");
		log.info("New bookmark: "+bm);
		Collection<Bookmark> col = OKMBookmark.getInstance().getAll(token);
		for (Iterator<Bookmark> it = col.iterator(); it.hasNext(); ) {
			log.info("Bookmark: "+it.next());
		}
		
		// Remove bookmark
		log.info("### REMOVE BOOKMARK ###");
		OKMBookmark.getInstance().remove(token, "pruebas *");
		col = OKMBookmark.getInstance().getAll(token);
		for (Iterator<Bookmark> it = col.iterator(); it.hasNext(); ) {
			log.info("Bookmark: "+it.next());
		}
		
		// Get/set home
		log.info("### GET HOME ###");
		log.info("UserHome: "+OKMBookmark.getInstance().getUserHome(token));
		log.info("### SET HOME ###");
		OKMBookmark.getInstance().setUserHome(token, doc.getPath());
		log.info("### GET HOME ###");
		log.info("UserHome: "+OKMBookmark.getInstance().getUserHome(token));
		
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
		Document doc = new Document();
		doc.setPath(okmRoot+"/perico.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es nada".getBytes()));
		log.info("New Document: "+newDocument);
		
		// Add bookmark
		log.info("### ADD BOOKMARK ###");
		Bookmark bm = OKMBookmark.getInstance().add(token, doc.getPath(), "prueba's");
		log.info("New bookmark: "+bm);
		Collection<Bookmark> col = OKMBookmark.getInstance().getAll(token);
		for (Iterator<Bookmark> it = col.iterator(); it.hasNext(); ) {
			log.info("Bookmark: "+it.next());
		}
		
		// Rename bookmark
		log.info("### RENAME BOOKMARK ###");
		bm = OKMBookmark.getInstance().rename(token, "prueba's", "otra cosa *");
		log.info("Renamed bookmark: "+bm);
		
		// Add bookmark
		log.info("### ADD BOOKMARK ###");
		bm = OKMBookmark.getInstance().add(token, doc.getPath(), "pruebas");
		log.info("New bookmark: "+bm);
		col = OKMBookmark.getInstance().getAll(token);
		for (Iterator<Bookmark> it = col.iterator(); it.hasNext(); ) {
			log.info("Bookmark: "+it.next());
		}

		OKMAuth.getInstance().logout(token);
	}
}
