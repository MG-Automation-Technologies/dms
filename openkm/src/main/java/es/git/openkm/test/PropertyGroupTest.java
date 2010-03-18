package es.git.openkm.test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.jcr.Session;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMAuth;
import es.git.openkm.api.OKMDocument;
import es.git.openkm.api.OKMPropertyGroup;
import es.git.openkm.api.OKMRepository;
import es.git.openkm.api.OKMSearch;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.QueryParams;
import es.git.openkm.bean.QueryResult;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.direct.DirectRepositoryModule;

public class PropertyGroupTest {
	private static Logger log = LoggerFactory.getLogger(PropertyGroupTest.class);
	
	public void simple() throws Exception {
		new DirectRepositoryModule().remove();
		String okmRoot = DirectRepositoryModule.initialize();
		String token = OKMAuth.getInstance().login("paco", "pepe");
		log.info("Root: "+okmRoot);
		Folder rootFolder = OKMRepository.getInstance().getRootFolder(token);
		log.info("Root: "+rootFolder);
		
		// Add document
		Document doc = new Document();
		doc.setPath(okmRoot+"/prueba.txt");
		doc = OKMDocument.getInstance().create(token, doc, new ByteArrayInputStream("Esto es una prueba".getBytes()));
		log.info("Path: "+doc);
		
		Session session = SessionManager.getInstance().get(token);
		FileInputStream fis = new FileInputStream("test_property_groups/my_groups.cnd");
		DirectRepositoryModule.registerCustomNodeTypes(session, fis);
		Collection<PropertyGroup> grps = OKMPropertyGroup.getInstance().getAllGroups(token);
		log.info("Registered groups: "+grps);
		
		log.info("Groups assigned to document: "+OKMPropertyGroup.getInstance().getGroups(token, doc.getPath()));
		log.info("***** ASSIGN PROPERTY GROUP *****");
		OKMPropertyGroup.getInstance().addGroup(token, doc.getPath(), "okg:tecnologia");
		log.info("Groups assigned to document: "+OKMPropertyGroup.getInstance().getGroups(token, doc.getPath()));
						
		session.exportDocumentView("/okm:root", System.out, false, false);
		System.out.println();
		HashMap<String, String[]> props = OKMPropertyGroup.getInstance().getProperties(token, doc.getPath(), "okg:tecnologia");
		log.info("* Propiedades de grupo del documento: "+props.toString());
		String[] tipo = (String[]) props.get("okp:tecnologia.tipo");
		log.info("* Tipo.length: "+tipo.length);
		for (int i=0; i<tipo.length; i++) {
			log.info("* Tipo: "+tipo[i]);
		}
		
		log.info("***** SET PROPERTIES *****");
		props.put("okp:tecnologia.comentario", new String[] {"un comentario"});
		props.put("okp:tecnologia.lenguaje", new String[] {"java"});
		props.put("okp:tecnologia.tipo", new String[] {"uno", "tres"});
		
		OKMDocument.getInstance().lock(token, doc.getPath());
		OKMPropertyGroup.getInstance().setProperties(token, doc.getPath(), "okg:tecnologia", props);
		log.info("* Propiedades del documento: "+OKMDocument.getInstance().getProperties(token, doc.getPath()));
		
		props = OKMPropertyGroup.getInstance().getProperties(token, doc.getPath(), "okg:tecnologia");
		log.info("* Propiedades de grupo del documento: "+props.toString());
		tipo = (String[]) props.get("okp:tecnologia.tipo");
		log.info("* Tipo.length: "+tipo.length);
		for (int i=0; i<tipo.length; i++) {
			log.info("* Tipo: "+tipo[i]);
		}
		
		log.info("***** SEARCH *****");
		QueryParams qp = new QueryParams();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("okp:tecnologia.lenguaje", "java");
		qp.setProperties(hm);
		log.info("ComplexSearch for '"+qp+"'...");
		for (Iterator<QueryResult> it = OKMSearch.getInstance().find(token, qp).iterator(); it.hasNext(); ) {
			QueryResult qr = it.next();
			doc = qr.getDocument();
			InputStream content = OKMDocument.getInstance().getContent(token, doc.getPath(), false);
			log.info(" * ["+qr.getScore()+"] "+doc.getPath()+" -> "+IOUtils.toString(content));
		}
		
		log.info("***** REMOVE PROPERTY GROUP *****");
		OKMPropertyGroup.getInstance().removeGroup(token, doc.getPath(), "okg:tecnologia");		
		session.exportDocumentView("/okm:root", System.out, false, false);
		log.info(OKMPropertyGroup.getInstance().getGroups(token, doc.getPath()).toString());
		
		OKMAuth.getInstance().logout(token);
	}
}
