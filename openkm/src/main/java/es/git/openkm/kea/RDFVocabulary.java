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

/**
 * @author jllort
 *
 */
public class RDFVocabulary {

	private static Logger log = LoggerFactory.getLogger(RDFVocabulary.class);

    private static Repository repository = null;
    private static RDFVocabulary instance;

    private static String NAMESPACE = " USING NAMESPACE "
                            + "dc=<http://purl.org/dc/elements/1.1/>,"
                            + "dct=<http://purl.org/dc/terms/>,"
                            + "rdf=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>,"
                            + "skos=<http://www.w3.org/2004/02/skos/core#>";


    private static String queryStr = "SELECT X,lab FROM {X} prefLabel {lab}"
                                     + " WHERE lang(lab) = \"en\"" + NAMESPACE;
    
    private static String NAMESPACE2 = " USING NAMESPACE "
    						+ "rdf=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>,"
    						+ "skos=<http://www.w3.org/2004/02/skos/core#>,"
    						+ "rdfs=<http://www.w3.org/2000/01/rdf-schema#>,"
    						+ "dc=<http://purl.org/dc/elements/1.1/>,"
    						+ "dcterms=<http://purl.org/dc/terms/>,"
    						+ "foaf=<http://xmlns.com/foaf/0.1/>";

    private static String queryStr2 = "SELECT X,lab FROM {X} skos:prefLabel {lab}"
        				 + " WHERE lang(lab) = \"en\"" + NAMESPACE2;
    
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
     * getISMTTerms
     * 
     * @return
     */
    public List<Term> getISMTTerms() {

        List<Term> terms = new ArrayList<Term>();
        RepositoryConnection con = null;
        TupleQuery query;

        try {
            con = repository.getConnection();
			query = con.prepareTupleQuery(QueryLanguage.SERQL, queryStr2); 
            log.info("query:"+queryStr2);
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
        String baseURL = "http://cain.nbii.org/thesauri/ismt.rdf";
        baseURL = "http://www.fao.org/aos/agrovoc";
        
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("vocabulary/ismt.rdf");
            is = this.getClass().getClassLoader().getResourceAsStream("vocabulary/agrovoc.rdf");
            repository = new SailRepository(new MemoryStore());
            repository.initialize();
            RepositoryConnection con = repository.getConnection();
            con.add(is, baseURL, RDFFormat.RDFXML);
            con.close();
            log.info("New SAIL memstore created for ISMT");

        } catch (RepositoryException e) {
            log.error("Cannot make connection to RDF repository.", e);
        } catch (IOException e) {
            log.error("cannot locate/read file ismt.rdfs", e);
        } catch (RDFParseException e) {
            log.error("Cannot parse file ipsv-skos.rdf");
        } catch (Throwable t) {
            log.error("Unexpected exception loading repository",t);
        } 
        return repository;
    }
}
