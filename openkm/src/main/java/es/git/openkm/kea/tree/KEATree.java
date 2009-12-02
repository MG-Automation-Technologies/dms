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

package es.git.openkm.kea.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;

import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMFolder;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.kea.Term;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.kea.RDFREpository;

/**
 * KEA Tree
 * 
 * @author jllort
 *
 */
public class KEATree {
	
	private static Logger log = LoggerFactory.getLogger(KEATree.class);
	
	/**
	 * Generate tree
	 */
	public static void recursiveGenerateTree(String termID, int level, String token, String parentPath, Vector<String> parentUIDS) {        
        for (ListIterator<Term> it = getParentTerms(termID).listIterator(); it.hasNext();) {
        	try {
        		Vector<String> newParentUIDS = (Vector<String>) parentUIDS.clone();
	        	String path = parentPath;
	        	Term term = it.next();
	        	if (level==0) {
	        		drawTerm(term, level);
	        	}
	        	path += "/" + term.getText();
	        	Folder folder = new Folder();
	    		folder.setPath(path);
				OKMFolder.getInstance().create(token, folder);
				// To solve infinite loop ( nodes must not be in a infinite cycle )
				if (!newParentUIDS.contains(term.getUid())) {
					newParentUIDS.add(term.getUid());
					recursiveGenerateTree(term.getUid(), level+1, token, path, newParentUIDS); // recursive generation
				} 
			} catch (PathNotFoundException e) {
				log.error("path not found",e);
			} catch (ItemExistsException e) {
				// Silent error ( not creating twice the folder )
			} catch (AccessDeniedException e) {
				log.error("access denied",e);
			} catch (es.git.openkm.core.RepositoryException e) {
				log.error("openkm repository exception",e);
			}
        }
	}
	
	/**
	 * drawTerm
	 * 
	 * @param term The term
	 * @param level The level
	 */
	private static void drawTerm(Term term, int level) {
		String levelSeparator = "";
		for (int i=0; i<level; i++) {
			levelSeparator += "-";
		}
		log.info("term " + levelSeparator +">" + term.getUid() + " - " + term.getText());
	}
	
	/**
	 * getParentTerms
	 * 
	 * @param termID The term id
	 * 
	 * @return List of child terms
	 */
	private static ArrayList<Term> getParentTerms(String termID) {
		ArrayList<Term> childTerms = new ArrayList<Term>();
		RepositoryConnection con = null;
		TupleQuery query;

		try {
			
			con = RDFREpository.getInstance().getOWLConnection();
			
			if (termID==null) {
				query = QueryBank.getInstance().getTreeTopQuery(con);
			} else {
				query = QueryBank.getInstance().getTreeNextLayerQuery(termID, con);
			}
	        
	        TupleQueryResult result = query.evaluate();
	        
	        while (result.hasNext()) {
	
	            BindingSet bindingSet = result.next();
	            Term term = new Term(bindingSet.getValue("UID").stringValue(), bindingSet.getValue("TEXT").stringValue());
	            // need to ignore duplicates casued by grandchild problem
	            if (!childTerms.contains(term)) {
	                childTerms.add(term);
	            }
	        }
        
		} catch (QueryEvaluationException e) {
			log.error("Query evaluation exception",e);
		} catch (RepositoryException e) {
			log.error("RDFVocabulary repository exception",e);
		}
        Collections.sort(childTerms, new TermComparator());
		
		return childTerms;
	}
}