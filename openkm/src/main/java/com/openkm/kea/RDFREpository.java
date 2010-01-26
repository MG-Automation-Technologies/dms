/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.kea;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.kea.Term;
import com.openkm.core.Config;
import com.openkm.kea.metadata.WorkspaceHelper;

/**
 * @author jllort
 *
 */
public class RDFREpository {

	private static Logger log = LoggerFactory.getLogger(RDFREpository.class);

    private static Repository SKOSRepository = null;
    private static Repository OWLRepository = null;
    private static RDFREpository instance;
    
    /**
     * getConnection
     * 
     * @return
     * @throws RepositoryException 
     */
    public RepositoryConnection getSKOSConnection() throws RepositoryException {
    	if (SKOSRepository!=null) {
    		return SKOSRepository.getConnection();
    	} else throw new RepositoryException("SKOS Repository not started");
    }
    
    /**
     * getConnection
     * 
     * @return
     * @throws RepositoryException 
     */
    public RepositoryConnection getOWLConnection() throws RepositoryException {
    	if (OWLRepository!=null) {
    		return OWLRepository.getConnection();
    	} else throw new RepositoryException("OWL Repository not started");
    }
    
    /**
     * RDFVocabulary
     */
    private RDFREpository() {
    	if (!Config.KEA_THESAURUS_SKOS_FILE.equals("")) {
    		SKOSRepository = getSKOSMemStoreRepository();
    	}
        if (!Config.KEA_THESAURUS_OWL_FILE.equals("")) {
        	OWLRepository = getOWLMemStoreRepository();
        }
    }
    
    /**
     * getInstance
     * 
     * @return
     */
    public static RDFREpository getInstance() {
        if (instance == null) {
            instance = new RDFREpository();
        }
        return instance;
    }

    /**
     * getTerms
     * 
     * @return
     */
    public List<Term> getTerms() {

        List<Term> terms = new ArrayList<Term>();
        RepositoryConnection con = null;
        TupleQuery query;
        
        if (SKOSRepository!=null) {
	        try {
	            con = SKOSRepository.getConnection();
				query = con.prepareTupleQuery(QueryLanguage.SERQL, Config.KEA_THESAURUS_VOCABULARY_SERQL); 
	            log.info("query:"+Config.KEA_THESAURUS_VOCABULARY_SERQL);
	            TupleQueryResult result;
				result = query.evaluate();
	            while (result.hasNext()) {
	                BindingSet bindingSet = result.next();
	                terms.add(new Term(bindingSet.getValue("lab").stringValue(),""));
	            }
	        } catch (RepositoryException e) {
	            log.error("could not obtain connection to respository",e);
	        } catch (MalformedQueryException e) {
	        	log.error(e.getMessage(),e);
			} catch (QueryEvaluationException e) {
				log.error(e.getMessage(),e);
			} finally {
	            try {
	                 con.close();
	            } catch (Throwable e) {
	                log.error("Could not close connection....", e);
	            }
	        }
        }
        return terms;
    }

    /**
     * getSKOSMemStoreRepository
     * 
     * @return
     */
    private Repository getSKOSMemStoreRepository() {
        InputStream is;
        Repository repository = null;
        String baseURL = Config.KEA_THESAURUS_BASE_URL;
        
        log.info("Loading skos file in memory");
        try {
        	log.info(WorkspaceHelper.RDF_SKOS_VOVABULARY_PATH);
        	is = new FileInputStream(WorkspaceHelper.RDF_SKOS_VOVABULARY_PATH);
            repository = new SailRepository(new MemoryStore());
            repository.initialize();
            RepositoryConnection con = repository.getConnection();
            con.add(is, baseURL, RDFFormat.RDFXML);
            con.close();
            log.info("New SAIL memstore created for SKOS RDF");

        } catch (RepositoryException e) {
            log.error("Cannot make connection to RDF repository.", e);
        } catch (IOException e) {
            log.error("cannot locate/read file", e);
            e.printStackTrace();
        } catch (RDFParseException e) {
            log.error("Cannot parse file", e);
        } catch (Throwable t) {
            log.error("Unexpected exception loading repository",t);
        } 
        return repository;
    }
    
    /**
     * getOWLMemStoreRepository
     * 
     * @return
     */
    private Repository getOWLMemStoreRepository() {
        InputStream is;
        Repository repository = null;
        String baseURL = Config.KEA_THESAURUS_BASE_URL;
        
        log.info("Loading owl file in memory");
        try {
        	log.info(WorkspaceHelper.RDF_OWL_VOVABULARY_PATH);
        	is = new FileInputStream(WorkspaceHelper.RDF_OWL_VOVABULARY_PATH);
            repository = new SailRepository(new MemoryStore());
            repository.initialize();
            RepositoryConnection con = repository.getConnection();
            con.add(is, baseURL, RDFFormat.RDFXML);
            con.close();
            log.info("New SAIL memstore created for OWL RDF");

        } catch (RepositoryException e) {
            log.error("Cannot make connection to RDF repository.", e);
        } catch (IOException e) {
            log.error("cannot locate/read file", e);
            e.printStackTrace();
        } catch (RDFParseException e) {
            log.error("Cannot parse file", e);
        } catch (Throwable t) {
            log.error("Unexpected exception loading repository",t);
        } 
        return repository;
    }
}
