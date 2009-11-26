/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.kea;

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

import es.git.openkm.bean.kea.Term;
import es.git.openkm.core.Config;

/**
 * @author jllort
 *
 */
public class RDFVocabulary {

	private static Logger log = LoggerFactory.getLogger(RDFVocabulary.class);

    private static Repository repository = null;
    private static RDFVocabulary instance;
    
    /**
     * getConnection
     * 
     * @return
     * @throws RepositoryException 
     */
    public RepositoryConnection getConnection() throws RepositoryException {
    	return repository.getConnection();
    }
    
    /**
     * RDFVocabulary
     */
    private RDFVocabulary() {
        repository = getMemStoreRepository();
    }
    
    /**
     * getInstance
     * 
     * @return
     */
    public static RDFVocabulary getInstance() {
        if (instance == null) {
            instance = new RDFVocabulary();
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

        try {
            con = repository.getConnection();
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
        return terms;
    }

    /**
     * getMemStoreRepository
     * 
     * @return
     */
    private Repository getMemStoreRepository() {
        InputStream is;
        Repository repository = null;
        String baseURL = Config.KEA_THESAURUS_BASE_URL;
        
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("vocabulary/"+ Config.KEA_THESAURUS_FILE);
            repository = new SailRepository(new MemoryStore());
            repository.initialize();
            RepositoryConnection con = repository.getConnection();
            con.add(is, baseURL, RDFFormat.RDFXML);
            con.close();
            log.info("New SAIL memstore created for RDF");

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
