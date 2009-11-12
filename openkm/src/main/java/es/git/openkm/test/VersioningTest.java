package es.git.openkm.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMAuth;
import es.git.openkm.api.OKMDocument;
import es.git.openkm.api.OKMFolder;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Version;
import es.git.openkm.core.LockException;
import es.git.openkm.core.VersionException;
import es.git.openkm.module.direct.DirectAuthModule;
import es.git.openkm.module.direct.DirectRepositoryModule;

public class VersioningTest {
	private static Logger log = LoggerFactory.getLogger(VersioningTest.class);
	
	/**
	 * 
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco","pepe");

		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		// Proceso de versionado
		log.info("************ VERSIONING: BEGIN ********");
		boolean co = OKMDocument.getInstance().isCheckedOut(token, newDocument.getPath());
		System.out.println("isCheckedOut: "+co);
		
		// Checkout
		OKMDocument.getInstance().checkout(token, newDocument.getPath());
		co = OKMDocument.getInstance().isCheckedOut(token, newDocument.getPath());
		System.out.println("isCheckedOut: "+co);
		
		// Cambiamos el valor de las keywords
		Thread.sleep(1000);
		doc.setKeywords("versión nueva");
		OKMDocument.getInstance().setProperties(token, doc);
		OKMDocument.getInstance().setContent(token, newDocument.getPath(), new ByteArrayInputStream("Esto es una prueba de versión 1".getBytes()));
				
		// Checkin
		Version ver = OKMDocument.getInstance().checkin(token, newDocument.getPath(), "alfa");
		System.out.println("\nNueva version: "+ver);

		// Otra versión
		Thread.sleep(1000);
		OKMDocument.getInstance().checkout(token, newDocument.getPath());
		doc.setKeywords("Otra versión aún más nueva");
		OKMDocument.getInstance().setProperties(token, doc);
		OKMDocument.getInstance().setContent(token, newDocument.getPath(), new ByteArrayInputStream("Esto es una prueba de versión 2 y última".getBytes()));
		ver = OKMDocument.getInstance().checkin(token, newDocument.getPath(), "beta");
		System.out.println("Nueva version: "+ver);
		
		System.out.println("\nConjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			ver = it.next();
			System.out.println(ver);
			InputStream content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			System.out.println(" * Content: "+IOUtils.toString(content));
		}
		
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		System.out.println("\nVersión actual: "+doc.getKeywords());
		System.out.println("Versión actual: "+doc.getActualVersion());
		log.info("************ VERSIONING: END ********");
		
		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 * 
	 */
	public void complete() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		// Create Folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/cositas");
		Folder newFolder = OKMFolder.getInstance().create(token, fld); 
		
		// Create Document
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(newFolder.getPath()+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		// Proceso de versionado
		for (int i=1; i<5; i++) {
			Thread.sleep(1000);
			OKMDocument.getInstance().checkout(token, newDocument.getPath());
			doc.setKeywords("versión nueva "+i);
			OKMDocument.getInstance().setProperties(token, doc);
			OKMDocument.getInstance().setContent(token, newDocument.getPath(), new ByteArrayInputStream(("Esto es una prueba de versión "+i).getBytes()));
			Version ver = OKMDocument.getInstance().checkin(token, newDocument.getPath(), "gamma");
			System.out.println("\nNueva version: "+ver);
		}

		// Listado de versiones
		System.out.println("\nConjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			System.out.println("- Versión: "+ver);
			InputStream content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			System.out.println(" * Content: "+IOUtils.toString(content));
		}

		// Borramos el directorio
		OKMFolder.getInstance().delete(token, newFolder.getPath());
		
		// Listado de versiones
		System.out.println("\nConjunto de Versiones...");
		col = OKMDocument.getInstance().getVersionHistory(token, "/okm:home/paco/okm:trash/cositas/prueba2.txt");
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			System.out.println("- Versión: "+ver);
			InputStream content = OKMDocument.getInstance().getContentByVersion(token, "/okm:home/paco/okm:trash/cositas/prueba2.txt", ver.getName());
			System.out.println(" * Content: "+IOUtils.toString(content));
		}

		OKMAuth.getInstance().logout(token);
	}

	/**
	 *
	 */
	public void concurrent() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		
		String tokenUno = OKMAuth.getInstance().login("paco", "pepe");
		String tokenDos = OKMAuth.getInstance().login("linus", "tux");
		System.out.println("[UNO] token: "+tokenUno);
		System.out.println("[DOS] token: "+tokenDos);
		
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenUno, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		// Checkout
		System.out.println("[UNO] CheckedOut...");
		OKMDocument.getInstance().checkout(tokenUno, newDocument.getPath());
		boolean co = OKMDocument.getInstance().isCheckedOut(tokenUno, newDocument.getPath());
		System.out.println("[UNO] isCheckedOut: "+co);
		co = OKMDocument.getInstance().isCheckedOut(tokenDos, newDocument.getPath());
		System.out.println("[DOS] isCheckedOut: "+co);
		
		// Cambiamos el valor de las keywords
		try {
			Thread.sleep(1000);
			doc.setKeywords("versión nueva");
			OKMDocument.getInstance().setProperties(tokenDos, doc);
			OKMDocument.getInstance().setContent(tokenDos, newDocument.getPath(), new ByteArrayInputStream("Esto es una prueba de versión de DOS".getBytes()));
		} catch (LockException e) {
			System.err.println(e.getMessage());
		}
		
		doc.setKeywords("versión nueva de UNO");
		OKMDocument.getInstance().setProperties(tokenUno, doc);
		OKMDocument.getInstance().setContent(tokenUno, newDocument.getPath(), new ByteArrayInputStream("Esto es una prueba de versión de UNO".getBytes()));
		
		// Checkin
		try {
			Version ver = OKMDocument.getInstance().checkin(tokenDos, newDocument.getPath(), "pi");
			System.out.println("Nueva version: "+ver);
		} catch (LockException e) {
			System.err.println(e.getMessage());
		}
		
		Thread.sleep(2000);
		Version ver = OKMDocument.getInstance().checkin(tokenUno, newDocument.getPath(), "theta");
		System.out.println("\nNueva version: "+ver);
		Thread.sleep(2000);
		
		try {
			ver = OKMDocument.getInstance().checkin(tokenUno, newDocument.getPath(), "omega");
			System.out.println("\nNueva version: "+ver);
		} catch (VersionException e) {
			System.err.println(e.getMessage());
		}
		
		//Thread.sleep(2000);
		//ver = new OKMDocument().checkin(tokenUno, docPath);
		//System.out.println("\nNueva version: "+ver);
		//Thread.sleep(2000);
		//ver = new OKMDocument().checkin(tokenUno, docPath);
		//System.out.println("\nNueva version: "+ver);
		
		System.out.println("\nConjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(tokenUno, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			ver = it.next();
			System.out.println(ver);
			InputStream content = OKMDocument.getInstance().getContentByVersion(tokenUno, newDocument.getPath(), ver.getName());
			System.out.println(" * Author: "+ver.getAuthor());
			System.out.println(" * Content: "+IOUtils.toString(content));
		}
		
		// Logout
		OKMAuth.getInstance().logout(tokenUno);
		OKMAuth.getInstance().logout(tokenDos);
	}
	
	/**
	 *
	 */
	public void multiuser() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String tokenUno = OKMAuth.getInstance().login("paco", "pepe");
		String tokenDos = OKMAuth.getInstance().login("linus", "tux");
		log.info("[UNO] token: "+tokenUno);
		log.info("[DOS] token: "+tokenDos);
		
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenUno, doc, new ByteArrayInputStream("[UNO] Esto es una prueba".getBytes()));

		// Checkout & checkin
		log.info("[DOS] CheckedOut...");
		OKMDocument.getInstance().checkout(tokenDos, newDocument.getPath());
		Thread.sleep(1000);
		doc.setKeywords("versión nueva");
		OKMDocument.getInstance().setProperties(tokenDos, doc);
		OKMDocument.getInstance().setContent(tokenDos, newDocument.getPath(), new ByteArrayInputStream("[DOS] Esto es una prueba de versión de DOS".getBytes()));
		log.info("[DOS] CheckinIn...");
		Version ver = OKMDocument.getInstance().checkin(tokenDos, newDocument.getPath(), "sigma");
		log.info("Nueva version: "+ver);

		// Checkout & checkin
		log.info("[UNO] CheckedOut...");
		OKMDocument.getInstance().checkout(tokenUno, newDocument.getPath());
		Thread.sleep(1000);
		doc.setKeywords("versión nueva");
		OKMDocument.getInstance().setProperties(tokenUno, doc);
		OKMDocument.getInstance().setContent(tokenUno, newDocument.getPath(), new ByteArrayInputStream("[UNO] Esto es una prueba de versión de UNO".getBytes()));
		log.info("[UNO] CheckinIn...");
		ver = OKMDocument.getInstance().checkin(tokenUno, newDocument.getPath(), "rho");
		log.info("Nueva version: "+ver);
		
		log.info("Conjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(tokenUno, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			ver = it.next();
			log.info(ver.toString());
			InputStream content = OKMDocument.getInstance().getContentByVersion(tokenUno, newDocument.getPath(), ver.getName());
			log.info(" * Author: "+ver.getAuthor());
			log.info(" * Content: "+IOUtils.toString(content));
		}
		
		// Logout
		OKMAuth.getInstance().logout(tokenUno);
		OKMAuth.getInstance().logout(tokenDos);
	}

	/**
	 *
	 */
	public void restore() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		// Create Folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/cositas");
		Folder newFolder = OKMFolder.getInstance().create(token, fld); 
		
		// Create Document
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(newFolder.getPath()+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		// Proceso de versionado
		for (int i=1; i<5; i++) {
			Thread.sleep(1000);
			OKMDocument.getInstance().checkout(token,  newDocument.getPath());
			doc.setKeywords("versión nueva "+i);
			OKMDocument.getInstance().setProperties(token, doc);
			OKMDocument.getInstance().setContent(token,  newDocument.getPath(), new ByteArrayInputStream(("Esto es una prueba de versión "+i).getBytes()));
			Version ver = OKMDocument.getInstance().checkin(token,  newDocument.getPath(), "delta");
			log.info("Nueva version: "+ver);
		}

		// Listado de versiones
		log.info("Conjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			log.info("- Versión: "+ver);
			InputStream content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Content: "+IOUtils.toString(content));
			log.info(" * Size: "+content.available()+", Otra: "+ver.getSize());
		}

		// Restore
		log.info("**** RESTORE TO 1.2 ****");
		OKMDocument.getInstance().restoreVersion(token, newDocument.getPath(), "1.2");
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		InputStream content = OKMDocument.getInstance().getContent(token, newDocument.getPath(), false);
		log.info("* 1.2 -> Doc: "+doc);
		log.info("* 1.2 -> Content: "+IOUtils.toString(content));
		log.info("* 1.2 -> Keyword: "+doc.getKeywords());
		log.info("* 1.2 -> Version: "+doc.getActualVersion());

		// Listado de versiones
		log.info("Conjunto de Versiones...");
		col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			log.info("- Versión: "+ver);
			content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Content: "+IOUtils.toString(content));
		}

		// Version history size
		long size = OKMDocument.getInstance().getVersionHistorySize(token, newDocument.getPath());
		log.info("Size: "+size);
		
		// Version history compact
		OKMDocument.getInstance().purgeVersionHistory(token, newDocument.getPath());

		// Version history size
		size = OKMDocument.getInstance().getVersionHistorySize(token, newDocument.getPath());
		log.info("Size: "+size);

		// Listado de versiones
		log.info("Conjunto de Versiones...");
		col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			log.info("- Versión: "+ver);
			content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Content: "+IOUtils.toString(content));
		}

		// View actual
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		content = OKMDocument.getInstance().getContent(token, newDocument.getPath(), false);
		log.info("* Doc: "+doc);
		log.info("* Content: "+IOUtils.toString(content));
		log.info("* Keyword: "+doc.getKeywords());
		log.info("* Version: "+doc.getActualVersion());
		
		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 *
	 */
	public void restoreAndCommit() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		// Create Folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/cositas");
		Folder newFolder = OKMFolder.getInstance().create(token, fld); 
		
		// Create Document
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(newFolder.getPath()+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		
		// Proceso de versionado
		for (int i=1; i<5; i++) {
			Thread.sleep(1000);
			OKMDocument.getInstance().checkout(token,  newDocument.getPath());
			doc.setKeywords("versión nueva "+i);
			OKMDocument.getInstance().setProperties(token, doc);
			OKMDocument.getInstance().setContent(token,  newDocument.getPath(), new ByteArrayInputStream(("Esto es una prueba de versión "+i).getBytes()));
			Version ver = OKMDocument.getInstance().checkin(token,  newDocument.getPath(), "sigma");
			log.info("Nueva version: "+ver);
		}

		// Listado de versiones
		log.info("Conjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			log.info("- Versión: "+ver);
			InputStream content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Content: "+IOUtils.toString(content));
			log.info(" * Size: "+content.available()+", Otra: "+ver.getSize());
		}

		// Restore
		log.info("**** RESTORE TO 1.2 ****");
		OKMDocument.getInstance().restoreVersion(token, newDocument.getPath(), "1.2");
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		InputStream content = OKMDocument.getInstance().getContent(token, newDocument.getPath(), false);
		log.info("* 1.2 -> Doc: "+doc);
		log.info("* 1.2 -> Content: "+IOUtils.toString(content));
		log.info("* 1.2 -> Keyword: "+doc.getKeywords());
		log.info("* 1.2 -> Version: "+doc.getActualVersion());

		// Listado de versiones
		log.info("Conjunto de Versiones...");
		col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			Version ver = it.next();
			log.info("- Versión: "+ver);
			content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Content: "+IOUtils.toString(content));
		}

		// Add a new version
		log.info("**** ADD NEW VERSION ****");
		OKMDocument.getInstance().checkout(token,  newDocument.getPath());
		doc.setKeywords("versión nueva ULTIMA");
		OKMDocument.getInstance().setProperties(token, doc);
		OKMDocument.getInstance().setContent(token,  newDocument.getPath(), new ByteArrayInputStream(("Esto es una prueba de versión ULTIMA").getBytes()));
		Version ver = OKMDocument.getInstance().checkin(token,  newDocument.getPath(), "pelo");
		log.info("Nueva version: "+ver);

		// Listado de versiones
		log.info("Conjunto de Versiones...");
		col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			ver = (Version) it.next();
			log.info("- Versión: "+ver);
			content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Content: "+IOUtils.toString(content));
		}

		// View actual
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		content = OKMDocument.getInstance().getContent(token, newDocument.getPath(), false);
		log.info("* Doc: "+doc);
		log.info("* Content: "+IOUtils.toString(content));
		log.info("* Keyword: "+doc.getKeywords());
		log.info("* Version: "+doc.getActualVersion());
		
		OKMAuth.getInstance().logout(token);
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

		// Checkout
		log.info("CheckedOut...");
		OKMDocument.getInstance().checkout(token, newDocument.getPath());
		new DirectAuthModule().view(token);
		
		Thread.sleep(1000);
		OKMAuth.getInstance().logout(token);
		token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Token: "+token);
		new DirectAuthModule().view(token);
		
		// Checkin
		OKMDocument.getInstance().setContent(token, newDocument.getPath(), new ByteArrayInputStream("Esto es una prueba de versión de DOS".getBytes()));
		log.info("CheckinIn...");
		Version ver = OKMDocument.getInstance().checkin(token, newDocument.getPath(), "pico");
		log.info("Nueva version: "+ver);
		
		log.info("Conjunto de Versiones...");
		Collection<Version> col = OKMDocument.getInstance().getVersionHistory(token, newDocument.getPath());
		for (Iterator<Version> it = col.iterator(); it.hasNext(); ) {
			ver = it.next();
			log.info(ver.toString());
			InputStream content = OKMDocument.getInstance().getContentByVersion(token, newDocument.getPath(), ver.getName());
			log.info(" * Author: "+ver.getAuthor());
			log.info(" * Content: "+IOUtils.toString(content));
		}
		
		// Logout
		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 *
	 */
	public void cancelCheckout() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Token: "+token);
		
		Document doc = new Document();
		doc.setKeywords("texto prístino");
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));

		// Checkout
		log.info("CheckedOut...");
		OKMDocument.getInstance().checkout(token, newDocument.getPath());
		log.info("isCheckedOut: "+OKMDocument.getInstance().isCheckedOut(token, newDocument.getPath()));
		log.info("## CANCEL CHECKOUT ##");
		OKMDocument.getInstance().cancelCheckout(token, newDocument.getPath());
		log.info("isCheckedOut: "+OKMDocument.getInstance().isCheckedOut(token, newDocument.getPath()));
				
		try {
			// Esto fallará
			OKMDocument.getInstance().checkin(token, newDocument.getPath(), "pata");
		} catch (VersionException e) {
			log.error(e.getMessage());
		}
		
		log.info("CheckedOut...");
		OKMDocument.getInstance().checkout(token, newDocument.getPath());
		OKMDocument.getInstance().checkin(token, newDocument.getPath(), "pito");
		
		// Logout
		OKMAuth.getInstance().logout(token);
	}
}
