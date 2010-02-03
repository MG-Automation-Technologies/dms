package com.openkm.test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMRepository;
import com.openkm.api.OKMSearch;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.QueryResult;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.direct.DirectRepositoryModule;

public class RemoveTest {
	private static Logger log = LoggerFactory.getLogger(RemoveTest.class);
	
	/**
	 * Un sólo documento
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Document doc = new Document();
		doc.setKeywords(Arrays.asList("texto prístino".split(" ")));
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));

		listDocs(token);

		log.info("************ DELETE: BEGIN ********");
		OKMDocument.getInstance().delete(token, newDocument.getPath());
		log.info("************ DELETE: END ********");
		
		
		try {
			doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
			System.out.println("Document: "+doc);
		} catch (PathNotFoundException e) {
			System.out.println("Document: No se puede ver porque está borrado");
		}
		
		listDocs(token);

		OKMAuth.getInstance().logout(token);
	}

	/**
	 *
	 */
	public void multimpleDocs() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Document doc = new Document();
		doc.setKeywords(Arrays.asList("texto prístino".split(" ")));
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));

		listDocs(token);
		OKMDocument.getInstance().delete(token, newDocument.getPath());

		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		listDocs(token);
		
		OKMDocument.getInstance().delete(token, newDocument.getPath());
		listDocs(token);

		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		listDocs(token);

		OKMDocument.getInstance().delete(token, newDocument.getPath());
		listDocs(token);

		OKMAuth.getInstance().logout(token);	
	}
	
	/**
	 * Carpeta y documento
	 */
	public void complex() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/test manolo 2");
		Folder newFolder = OKMFolder.getInstance().create(token, fld); 
		
		Document doc = new Document();
		doc.setKeywords(Arrays.asList("texto prístino".split(" ")));
		doc.setPath(newFolder.getPath()+"/prueba num 2.txt");
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));

		listFlds(token);

		log.info("************ DELETE: BEGIN ********");
		OKMFolder.getInstance().delete(token, newFolder.getPath());
		log.info("************ DELETE: END ********");

		listFlds(token);

		OKMAuth.getInstance().logout(token);
	}

	/**
	 *
	 */
	public void multimpleFolders() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/test manolo 2");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld); 
		
		Document doc = new Document();
		doc.setKeywords(Arrays.asList("texto prístino".split(" ")));
		doc.setPath(newFolder1.getPath()+"/prueba2.txt");
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		listFlds(token);

		System.out.println("******* BORRAR");
		OKMFolder.getInstance().delete(token, newFolder1.getPath());
		listFlds(token);
		
		Folder newFolder2 = OKMFolder.getInstance().create(token, fld);
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		listDocs(token);
		
		System.out.println("******* BORRAR");
		OKMFolder.getInstance().delete(token, newFolder2.getPath());
		listFlds(token);

		Folder newFolder3 = OKMFolder.getInstance().create(token, fld);
		OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		listDocs(token);

		System.out.println("******* BORRAR");
		OKMFolder.getInstance().delete(token, newFolder3.getPath());
		listFlds(token);

		OKMAuth.getInstance().logout(token);	
	}

	/**
	 * Borrado de verdad
	 */
	public void purge() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Document doc = new Document();
		doc.setKeywords(Arrays.asList("texto prístino".split(" ")));
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));

		listDocs(token);

		log.info("************ PURGE: BEGIN ********");
		OKMDocument.getInstance().purge(token, newDocument.getPath());
		log.info("************ PURGE: END ********");
		
		listDocs(token);

		OKMAuth.getInstance().logout(token);
	}

	/**
	 * Borrado de verdad
	 */
	public void purgeTrash() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		
		Document doc = new Document();
		doc.setKeywords(Arrays.asList("texto prístino".split(" ")));
		doc.setPath(okmRoot+"/prueba2.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));

		listDocs(token);

		log.info("************ DELETE: BEGIN ********");
		OKMDocument.getInstance().delete(token, newDocument.getPath());
		log.info("************ DELETE: END ********");
		
		listDocs(token);

		log.info("************ PURGE: BEGIN ********");
		OKMRepository.getInstance().purgeTrash(token);
		log.info("************ PURGE: END ********");
		
		listDocs(token);

		OKMAuth.getInstance().logout(token);
	}

	/**
	 * Lista los documentos que hay borrados y sin borrar 
	 */
	private void listDocs(String token) throws AccessDeniedException, RepositoryException {
		System.out.println("\nDocumentos buenos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			Document doc = it.next().getDocument();
			System.out.println(" * "+doc);
		}
	}
	
	/**
	 * Lista las carpetas que hay borrados y sin borrar 
	 * @throws PathNotFoundException 
	 */
	private void listFlds(String token) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		System.out.println("\nCarpetas buenas...");
		String okmRoot = OKMRepository.getInstance().getRootFolder(token).getPath();
		for (Iterator<Folder> it = OKMFolder.getInstance().getChilds(token, okmRoot).iterator(); it.hasNext(); ) {
			Folder fld = it.next();
			System.out.println(" * "+fld);
		}
	}
}
