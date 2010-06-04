package com.openkm.test;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDashboard;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMNotification;
import com.openkm.api.OKMSearch;
import com.openkm.bean.DashboardDocumentResult;
import com.openkm.bean.DashboardFolderResult;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.QueryParams;
import com.openkm.module.direct.DirectDashboardModule;
import com.openkm.module.direct.DirectRepositoryModule;

public class DashboardTest {
	private static Logger log = LoggerFactory.getLogger(DashboardTest.class);

	/**
	 * 
	 */
	public void basic() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba.txt");
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Pruebas".getBytes()));
		log.info("DOC: "+doc);

		// Lock
		OKMDocument.getInstance().lock(token, doc.getPath());

		// Add Folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/pruebas");
		OKMFolder.getInstance().create(token, fld);
		log.info("FLD: "+fld);

		// Subcription
		OKMNotification.getInstance().subscribe(token, fld.getPath());

		// Add document
		doc = new Document();
		doc.setPath(fld.getPath()+"/prueba2.txt");
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("DOC: "+doc);
		
		// Subcription
		OKMNotification.getInstance().subscribe(token, doc.getPath());
		
		// Checkout
		OKMDocument.getInstance().checkout(token, doc.getPath());
		
		// DashBoard
		log.info(" *** Locked documents *** ");
		Collection<DashboardDocumentResult> documents = new DirectDashboardModule().getUserLockedDocuments(token);
				
		for (Iterator<DashboardDocumentResult> it = documents.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}

		log.info(" *** CheckedOut documents *** ");
		documents = new DirectDashboardModule().getUserCheckedOutDocuments(token);
				
		for (Iterator<DashboardDocumentResult> it = documents.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}

		log.info(" *** Subscribed folders *** ");
		Collection<DashboardFolderResult> folders = new DirectDashboardModule().getUserSubscribedFolders(token);
				
		for (Iterator<DashboardFolderResult> it = folders.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}

		log.info(" *** Subscribed documents *** ");
		documents = new DirectDashboardModule().getUserSubscribedDocuments(token);
				
		for (Iterator<DashboardDocumentResult> it = documents.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}
		
		log.info(" *** Last modified documents *** ");
		documents = new DirectDashboardModule().getUserLastModifiedDocuments(token);
				
		for (Iterator<DashboardDocumentResult> it = documents.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}
		
		log.info(" *** Document size *** ");
		log.info(new DirectDashboardModule().getUserDocumentsSize(token)+"");
		
		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 * 
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba.txt");
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Pruebas".getBytes()));
		log.info("DOC: "+doc);

		// Lock
		OKMDocument.getInstance().lock(token, doc.getPath());

		// Add Folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/pruebas");
		OKMFolder.getInstance().create(token, fld);
		log.info("FLD: "+fld);

		// Add document
		doc = new Document();
		doc.setPath(fld.getPath()+"/prueba2.txt");
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("DOC: "+doc);
		
		log.info(" *** Save query *** ");
		QueryParams qp = new QueryParams();
		qp.setDashboard(true);
		qp.setContent("prueba");
		qp.setProperties(new HashMap<String, String>());
		OKMSearch.getInstance().saveSearch(token, qp, "prueba");

		log.info(" *** Get all search *** ");
		Collection<String> searchs = OKMDashboard.getInstance().getUserSearchs(token);
		
		for (Iterator<String> it = searchs.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}
		
		log.info(" *** Find documents *** ");
		Collection<DashboardDocumentResult> documents = OKMDashboard.getInstance().find(token, "prueba");
		
		for (Iterator<DashboardDocumentResult> it = documents.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}

		Thread.sleep(5000);
		
		log.info(" *** Find documents *** ");
		documents = OKMDashboard.getInstance().find(token, "prueba");
		
		for (Iterator<DashboardDocumentResult> it = documents.iterator(); it.hasNext(); ) {
			log.info("# "+it.next());
		}

		OKMAuth.getInstance().logout(token);
	}
}
