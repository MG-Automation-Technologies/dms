package com.openkm.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMRepository;
import com.openkm.api.OKMSearch;
import com.openkm.bean.ContentInfo;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.QueryParams;
import com.openkm.bean.QueryResult;
import com.openkm.bean.ResultSet;
import com.openkm.module.direct.DirectRepositoryModule;

public class BasicTest {
	private static Logger log = LoggerFactory.getLogger(BasicTest.class);

	/**
	 * 
	 */
	public void basic() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String tokenPaco = OKMAuth.getInstance().login("paco", "pepex");
		//String tokenLinus = new OKMAuth().login("linus", "pepex");
		//String okmRoot = new OKMRepository().getRootPath(token);
		
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba.txt");
		Document newDocument = OKMDocument.getInstance().create(tokenPaco, doc, new ByteArrayInputStream("Pruebas".getBytes()));
		log.info(newDocument.toString());
		
		//doc = new OKMDocument().getProperties(tokenPaco, newDocument.getPath());
		//log.info(doc.toString());
		
		log.info("***** ADD ROLE *********");
		
		//new DirectAuthModule().addUserPermissions(tokenPaco, doc.getPath(), "linus", Permission.READ);
		//new DirectAuthModule().addUserPermissions(tokenLinus, doc.getPath(), "otro", Permission.READ);
		//new DirectAuthModule().addRolePermissions(tokenLinus, doc.getPath(), "user", Permission.READ);

		log.info("***** GET PROPERTIES *********");
		
		//doc = new OKMDocument().getProperties(tokenLinus, docPath);
		//log.info(doc.toString());
		
		log.info("***** LOGOUT *********");
		
		// Add document
		//Document doc = new Document();
		//doc.setPath(okmRoot+"/prueba.txt");
		//String docPath = new OKMDocument().create(token, doc, "Esto es una prueba".getBytes());
		//System.out.println("Path: "+docPath);
				
		OKMAuth.getInstance().logout(tokenPaco);
		//new OKMAuth().logout(tokenLinus);
	}

	/**
	 * 
	 */
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepex");
		log.info("Root: "+okmRoot);
		Folder rootFolder = OKMRepository.getInstance().getRootFolder(token);
		log.info("Root: "+rootFolder);
		
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Path: "+newDocument);
		
		// Get document
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		log.info("Document: "+doc);
		InputStream content = OKMDocument.getInstance().getContent(token, newDocument.getPath(), false);
		log.info("Document.content: "+IOUtils.toString(content));
		
		// List structure
		log.info("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			doc = it.next().getDocument();
			log.info(" * "+doc);
		}
		
		OKMAuth.getInstance().logout(token);
	}

	/**
	 * 
	 */
	public void complex() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Root: "+okmRoot);
		
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba1.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Path 0: "+newDocument.getPath());
		
		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/Prueba");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld);
		log.info("Path 1: "+newFolder1);

		// Add folder
		fld = new Folder();
		fld.setPath(newFolder1.getPath()+"/Otro");
		Folder newFolder2 = OKMFolder.getInstance().create(token, fld);
		log.info("Path 2: "+newFolder2);

		// Get folder
		fld = OKMFolder.getInstance().getProperties(token, newFolder1.getPath());
		log.info("Folder: "+fld);
		fld = OKMFolder.getInstance().getProperties(token, newFolder2.getPath());
		log.info("Folder: "+fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder2.getPath()+"/prueba2.txt");
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es otra prueba".getBytes()));
		log.info("Path 3: "+newDocument.getPath());
				
		// Get document
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		log.info("Document: "+doc);
		
		// List childs
		log.info("Childs...");
		for (Iterator<Document> it = OKMDocument.getInstance().getChilds(token, newFolder2.getPath()).iterator(); it.hasNext(); ) {
			doc = it.next();
			log.info(" * "+doc);
		}
		
		// List structure
		log.info("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			log.info(" * "+it.next());
		}
		
		OKMAuth.getInstance().logout(token);
	}

	/**
	 *
	 */
	public void move() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
	
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba de fe.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		System.out.println("Path: "+newDocument.getPath());
		
		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder = OKMFolder.getInstance().create(token, fld);

		// Move
		OKMDocument.getInstance().move(token, newDocument.getPath(), newFolder.getPath());
		
		// List structure
		System.out.println("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			doc = it.next().getDocument();
			System.out.println(" * "+doc);
		}
		
		OKMAuth.getInstance().logout(token);	
	}

	/**
	 *
	 */
	public void copy() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
	
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba de fe.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		System.out.println("Path: "+newDocument.getPath());
		
		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder = OKMFolder.getInstance().create(token, fld);

		// Copy document
		OKMDocument.getInstance().copy(token, newDocument.getPath(), newFolder.getPath());
		
		// List structure
		System.out.println("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			System.out.println(" * "+qr.getDocument());
		}
		
		// Add folder
		fld = new Folder();
		fld.setPath(okmRoot+"/otra cosa");
		Folder anotherNewFolder = OKMFolder.getInstance().create(token, fld);
		
		// Copy folder
		OKMFolder.getInstance().copy(token, newFolder.getPath(), anotherNewFolder.getPath());
		
		// List structure
		System.out.println("Carpetas...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			System.out.println(" * "+qr.getDocument());
		}
		
		OKMAuth.getInstance().logout(token);	
	}

	/**
	 *
	 */
	public void renameDocument() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
	
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba de fe.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Rename
		Document renamedDocument = OKMDocument.getInstance().rename(token, newDocument.getPath(), "xxx prueba de fe.txt");
		log.info("newPath: "+renamedDocument.getPath());
		
		// List structure
		log.info("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			log.info(" * "+it.next().getDocument().getPath());
		}
		
		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 *
	 */
	public void renameFolder() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");

		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder = OKMFolder.getInstance().create(token, fld);

		// Add document
		Document doc = new Document();
		doc.setPath(newFolder.getPath()+"/prueba de fe.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Rename
		Folder renamedFolder = OKMFolder.getInstance().rename(token, newFolder.getPath(), "otro nombre");
		log.info("newPath: "+renamedFolder);
		Folder theRenamedFolder = OKMFolder.getInstance().getProperties(token, renamedFolder.getPath());
		log.info(theRenamedFolder.toString());
		log.info(theRenamedFolder.getCreated().toString());
		
		// List structure
		log.info("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			doc = it.next().getDocument();
			log.info(" * "+doc);
		}
		
		OKMAuth.getInstance().logout(token);
	}

	/**
	 *
	 */
	public void folderContentInfo() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");

		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld);

		// Add document
		Document doc = new Document();
		doc.setPath(newFolder1.getPath()+"/prueba de fe.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/otra prueba de fe.txt");
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Y esta es otra".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		fld = new Folder();
		fld.setPath(newFolder1.getPath()+"/otras cosas");
		Folder newFolder2  = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder2.getPath()+"/la lista de la compra.txt");
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Pan, naranjas y huevos".getBytes()));
		log.info("Document Path: "+newDocument.getPath());
	
		// List structure
		log.info("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			doc = it.next().getDocument();
			log.info(" * "+doc.getActualVersion().getSize()+" -> "+doc);
		}
		
		// Get Content Info
		ContentInfo ci = OKMFolder.getInstance().getContentInfo(token, okmRoot);
		log.info("ContentInfo: "+ci);
		
		OKMAuth.getInstance().logout(token);
	}

	/**
	 * 
	 */
	public void search() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");

		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/lo básico.txt");
		doc.setKeywords(Arrays.asList("un cosa básica".split(" ")));
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Lo más básico que se pude".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/prueba de fe.txt");
		doc.setKeywords(Arrays.asList("perico de los palotes".split(" ")));
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/otra prueba de fe.txt");
		doc.setKeywords(Arrays.asList("el chico palotes".split(" ")));
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Y esta es otra".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		fld = new Folder();
		fld.setPath(newFolder1.getPath()+"/otras cosas");
		Folder newFolder2 = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder2.getPath()+"/la lista de la compra.txt");
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Pan, naranjas y huevos".getBytes()));
		log.info("Document Path: "+newDocument.getPath());
	
		// Search...
		String search = "*";
		log.info("SearchByName for '"+search+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByName(token, search).iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		// Search...
		search = "prueba";
		log.info("SearchByName for '"+search+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByName(token, search).iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		// Search...
		search = "palotes";
		log.info("SearchByKeywords for '"+search+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByKeywords(token, search).iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		// Search...
		search = "naranjas";
		log.info("SearchByContent for '"+search+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, search).iterator(); it.hasNext(); ) {
			QueryResult qr = (QueryResult) it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		// Search...
		search = "//element(*, okm:document)[jcr:contains(okm:content, 'naran*')]";
		log.info("SearchByQuery for '"+search+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByStatement(token, search, "xpath").iterator(); it.hasNext(); ) {
			QueryResult qr = (QueryResult) it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		QueryParams qp = new QueryParams();
		qp.setKeywords("palotes");
		qp.setContent("prueba");
		log.info("ComplexSearch for '"+qp+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().find(token, qp).iterator(); it.hasNext(); ) {
			QueryResult qr = (QueryResult) it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 * 
	 */
	public void searchSave() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");

		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/lo básico.txt");
		doc.setKeywords(Arrays.asList("un cosa básica".split(" ")));
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Lo más básico que se pude".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/prueba de fe.txt");
		doc.setKeywords(Arrays.asList("perico de los palotes".split(" ")));
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/otra prueba de fe.txt");
		doc.setKeywords(Arrays.asList("el chico palotes".split(" ")));
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Y esta es otra".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		fld = new Folder();
		fld.setPath(newFolder1.getPath()+"/otras cosas");
		Folder newFolder2 = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder2.getPath()+"/la lista de la compra.txt");
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Pan, naranjas y huevos".getBytes()));
		log.info("Document Path: "+newDocument.getPath());
	
		// Search...
		String search = "palotes";
		log.info("SearchByKeywords for '"+search+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByKeywords(token, search).iterator(); it.hasNext(); ) {
			doc = it.next().getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		// Saved searchs
		log.info("Saved searchs (void)");
		for (Iterator<String> it = OKMSearch.getInstance().getAllSearchs(token).iterator(); it.hasNext(); ) {
			log.info(" * "+it.next());
		}

		// Save a search
		String searchName = "prueba";
		QueryParams params = new QueryParams();
		params.setKeywords("palotes");
		params.setContent("contenido");
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("okp:cosa", "rara");
		params.setProperties(hm);
		OKMSearch.getInstance().saveSearch(token, params, searchName);
		
		// Saved searchs
		log.info("Saved searchs (prueba)");
		for (Iterator<String> it = OKMSearch.getInstance().getAllSearchs(token).iterator(); it.hasNext(); ) {
			log.info(" * "+it.next());
		}
		
		// Get search params
		log.info("Get search params");
		params = OKMSearch.getInstance().getSearch(token, searchName);
		log.info(params.toString());

		// Remove search
		log.info("Remove search (prueba)");
		OKMSearch.getInstance().deleteSearch(token, searchName);
		
		// Saved searchs
		log.info("Saved searchs (void)");
		for (Iterator<String> it = OKMSearch.getInstance().getAllSearchs(token).iterator(); it.hasNext(); ) {
			log.info(" * "+it.next());
		}

		OKMAuth.getInstance().logout(token);
	}

	/**
	 *
	 */
	public void utf8() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		System.out.println("Root: "+okmRoot);
		
		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/ЊЉШЖ");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld);
		System.out.println("Path 1: "+newFolder1.getPath());

		// Add folder
		fld = new Folder();
		fld.setPath(newFolder1.getPath()+"/ϣϢϪ");
		Folder newFolder2 = OKMFolder.getInstance().create(token, fld);
		System.out.println("Path 2: "+newFolder2.getPath());

		// Get folder
		fld = OKMFolder.getInstance().getProperties(token, newFolder1.getPath());
		System.out.println("Folder: "+fld);
		fld = OKMFolder.getInstance().getProperties(token, newFolder2.getPath());
		System.out.println("Folder: "+fld);

		// Add document
		Document doc = new Document();
		doc.setPath(newFolder2.getPath()+"/αβγ.txt");
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		System.out.println("Path 3: "+newDocument.getPath());
				
		// Get document
		doc = OKMDocument.getInstance().getProperties(token, newDocument.getPath());
		System.out.println("Document: "+doc);
		
		// List childs
		System.out.println("Childs...");
		for (Iterator<Document> it = OKMDocument.getInstance().getChilds(token, newFolder2.getPath()).iterator(); it.hasNext(); ) {
			doc = it.next();
			System.out.println(" * "+doc);
		}
		
		// List structure
		System.out.println("Documentos...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().findByContent(token, "*").iterator(); it.hasNext(); ) {
			doc = it.next().getDocument();
			System.out.println(" * "+doc);
		}
		
		OKMAuth.getInstance().logout(token);
	}
	
	/**
	 * 
	 */
	public void searchPagination() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");

		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/lo básico.txt");
		doc.setKeywords(Arrays.asList("un cosa básica".split(" ")));
		Document newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Lo más básico que se pude".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		Folder fld = new Folder();
		fld.setPath(okmRoot+"/destino de caballero");
		Folder newFolder1 = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/prueba de fe.txt");
		doc.setKeywords(Arrays.asList("perico de los palotes".split(" ")));
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add document
		doc = new Document();
		doc.setPath(newFolder1.getPath()+"/otra prueba de fe.txt");
		doc.setKeywords(Arrays.asList("el chico palotes".split(" ")));
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Y esta es otra".getBytes()));
		log.info("Document Path: "+newDocument.getPath());

		// Add folder
		fld = new Folder();
		fld.setPath(newFolder1.getPath()+"/otras cosas");
		Folder newFolder2 = OKMFolder.getInstance().create(token, fld);

		// Add document
		doc = new Document();
		doc.setPath(newFolder2.getPath()+"/la lista de la compra.txt");
		newDocument = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Pan, naranjas y huevos".getBytes()));
		log.info("Document Path: "+newDocument.getPath());
	
		// Search...
		String search = "SELECT * FROM okm:document WHERE jcr:path LIKE '/okm:root/%'";
		//String search = "//element(*, okm:document)[jcr:like(@okm:name, '%txt') and (@okm:resource/okm:size > 1)]";
		//String search = "SELECT * FROM okm:document WHERE CONTAINS(okm:content, 'naranjas')";
		log.info("SearchByName for '"+search+"'...");
		ResultSet rs = OKMSearch.getInstance().findByStatementPaginated(token, search, "sql", 0, 2);
		log.info("Total: "+rs.getTotal());
		
		for (Iterator<QueryResult> it = rs.getResults().iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}

		OKMAuth.getInstance().logout(token);
	}
}
