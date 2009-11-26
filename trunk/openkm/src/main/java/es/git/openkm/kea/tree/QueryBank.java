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

import org.openrdf.model.ValueFactory;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryBank {
	
	private static Logger log = LoggerFactory.getLogger(QueryBank.class);

    private static String namespace = " USING NAMESPACE "
                                    + "ipsv=<http://www.esd.org.uk/standards/ipsv/2.00/ipsv-schema#>,"
                                    + "dc=<http://purl.org/dc/elements/1.1/>,"
                                    + "egms =<http://www.esd.org.uk/standards/egms/3.0/egms-schema#>";


    private static String searchQueryStr = new StringBuilder()
                            .append("SELECT target, lbl, id ")
                            .append("FROM {Target} rdfs:label {lbl}; dc:identifier {id} ")
                            .append("WHERE lbl LIKE ").toString();

    private static String searchQueryStr1 = "SELECT " + Names.TERM + "," + Names.LABEL + "," + Names.ID + "," + Names.PREF
                                + " FROM {" + Names.TERM + "} rdfs:label {" + Names.LABEL + "}; dc:identifier {" + Names.ID + "},"
                                + "[{" + Names.TERM + "} ipsv:useItem {} dc:identifier {" + Names.PREF + "}]"
                                + " WHERE " + Names.LABEL + " LIKE ";

    private static String getIdFromlabelQueryStr = "SELECT " + Names.TERM + "," + Names.LABEL + "," + Names.ID
                                                 + " FROM {" + Names.TERM + "} rdfs:label {" + Names.LABEL + "}; dc:identifier {" + Names.ID + "}"
                                                 + " WHERE " + Names.LABEL + " LIKE ";

    private static String rMapQueryStr = "SELECT " + Names.TERM + "," + Names.LABEL + "," + Names.ID
                                + " FROM {" + Names.TERM + "} rdf:type {rdfs:Class}, "
                                + "{" + Names.TERM + "} dc:identifier {p_termID};"
                                + "rdfs:label {" + Names.LABEL + "}; dc:identifier {" + Names.ID + "}"
                                + " UNION SELECT " + Names.SUB + "," + Names.LABEL + "," + Names.ID
                                + " FROM {" + Names.SUB + "} rdfs:subClassOf {"+ Names.TERM + "},"
                                + "{" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.SUB + "} rdfs:label {" + Names.LABEL + "},"
                                + "{" + Names.SUB + "} dc:identifier {" + Names.ID + "}"
                                + " UNION SELECT " + Names.REL + "," + Names.LABEL + "," + Names.ID
                                + " FROM {" + Names.REL + "} dc:relation  {"+ Names.TERM + "},"
                                + "{" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.REL + "} rdfs:label {" + Names.LABEL + "},"
                                + "{" + Names.REL + "} dc:identifier {" + Names.ID + "}"
                                + " UNION SELECT DISTINCT " + Names.SIB + "," + Names.LABEL + "," + Names.ID
                                + " FROM {" + Names.SIB + "," + Names.TERM + "} rdfs:subClassOf {"+ Names.SUPER + "},"
                                + "{" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.SIB + "} rdfs:label {" + Names.LABEL + "},"
                                + "{" + Names.SIB + "} dc:identifier {" + Names.ID + "}"
                                + " UNION SELECT " + Names.SUPER + "," + Names.LABEL + "," + Names.ID
                                + " FROM {" + Names.TERM + "} rdfs:subClassOf {" + Names.SUPER + "},"
                                + "{" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.SUPER + "} rdfs:label {" + Names.LABEL + "},"
                                + "{" + Names.SUPER + "} dc:identifier {" + Names.ID + "}"
                                ;

    private static String treeTopQueryStr =
                                "SELECT " + Names.TERM + "," + Names.LABEL + "," + Names.ID + "," + Names.SUPER + "," + Names.GRANDCHILD
                                + " FROM {" + Names.TERM + "} rdf:type {rdfs:Class},"
                                + "{" + Names.TERM + "} rdfs:subClassOf {" + Names.SUPER + "},"
                                + "{" + Names.TERM + "} rdfs:label {" + Names.LABEL + "},"
                                + "{" + Names.TERM + "} dc:identifier {" + Names.ID + "},"
                                + "[{" + Names.GRANDCHILD + "} rdfs:subClassOf {" + Names.TERM + "}]"
                                + " WHERE " + Names.SUPER + " = egms:SubjectCategoryClass";

    private static final String treeNextLayerQueryStr =
                                "SELECT " + Names.SUB + "," + Names.LABEL + "," + Names.ID + "," + Names.GRANDCHILD
                                + " FROM {" + Names.SUB + "} rdfs:subClassOf {" + Names.TERM + "},"
                                + "{" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.SUB + "} rdfs:label {" + Names.LABEL + "},"
                                + "{" + Names.SUB + "} dc:identifier {" + Names.ID + "},"
                                + "[{" + Names.GRANDCHILD + "} rdfs:subClassOf {" + Names.SUB + "}]";


    private static final String superClassQueryStr =
                                "SELECT " + Names.TERM + "," + Names.ID + "," + Names.SUPER
                                + " FROM {" + Names.TERM + "} rdfs:subClassOf {" + Names.SUPER + "},"
                                + "{" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.SUPER + "} dc:identifier {" + Names.ID + "}";

    private static final String nodeToRootQueryStr1 =
                                "SELECT " + Names.TERM + ",1,2,3,4,5,6,7,8,9"
                                + " FROM {" + Names.TERM + "} dc:identifier {p_termID},"
                                + "{" + Names.TERM + "} rdfs:subClassOf {" + Names.SUPER + "},"
                                + "{" + Names.SUPER + "} dc:identifier {" + Names.ID + "}";



    private static QueryBank ourInstance;

    private QueryBank() {

    }

    public static QueryBank getInstance() {
        if (ourInstance==null) {
            ourInstance = new QueryBank();
        }
        return ourInstance;
    }

    public TupleQuery getSearchQuery(String searchArg, RepositoryConnection con) {
        TupleQuery searchQuery = null;
        try {
            String querySuffix;
            if (searchArg.length() < 3) {
                querySuffix = "\""+searchArg + "*\" IGNORE CASE" + namespace;
            } else {
                querySuffix = "\"*"+searchArg + "*\" IGNORE CASE" + namespace;
            }

            searchQuery = con.prepareTupleQuery(QueryLanguage.SERQL, searchQueryStr1 + querySuffix);

        } catch (RepositoryException e) {
            log.error("Error preparing search query",e);
        } catch (MalformedQueryException e) {
            log.error("malformed search query",e);
        }

        return searchQuery;
    }
    public TupleQuery getIdFromLabelQuery(String label, RepositoryConnection con) {
        TupleQuery idFromLabelQuery = null;
        try {
            String querySuffix = "\""+ label + "\" IGNORE CASE" + namespace;
            idFromLabelQuery = con.prepareTupleQuery(QueryLanguage.SERQL, searchQueryStr1 + querySuffix);

        } catch (RepositoryException e) {
            log.error("Error preparing search query",e);
        } catch (MalformedQueryException e) {
            log.error("malformed search query",e);
        }

        return idFromLabelQuery;
    }


    public TupleQuery getRMapQuery(String targetID, RepositoryConnection con) {
        TupleQuery rMapQuery = null;
        try {
            rMapQuery = con.prepareTupleQuery(QueryLanguage.SERQL, rMapQueryStr + namespace);
            ValueFactory valFac = con.getRepository().getValueFactory();
            rMapQuery.setBinding("p_termID", valFac.createLiteral(targetID));
        } catch (RepositoryException e) {
            log.error("Error preparing rMap query",e);
        } catch (MalformedQueryException e) {
            log.error("malformed rMap query",e);
        }
        return rMapQuery;
    }

    public TupleQuery getTreeTopQuery(RepositoryConnection con) {
        try {
            return con.prepareTupleQuery(QueryLanguage.SERQL, treeTopQueryStr + namespace);
        } catch (RepositoryException e) {
            log.error("Error preparing tree top query",e);
        } catch (MalformedQueryException e) {
            log.error("malformed rMap query",e);
        }
        return null;
    }

    public TupleQuery getTreeNextLayerQuery(String parentID, RepositoryConnection con) {
        TupleQuery treeNextLayerQuery = null;
        try {
            treeNextLayerQuery = con.prepareTupleQuery(QueryLanguage.SERQL, treeNextLayerQueryStr + namespace);
            ValueFactory valFac = con.getRepository().getValueFactory();
            treeNextLayerQuery.setBinding("p_termID", valFac.createLiteral(parentID));
        } catch (RepositoryException e) {
            log.error("Error preparing rMap query",e);
        } catch (MalformedQueryException e) {
            log.error("malformed next tree layer query",e);
        }
        return treeNextLayerQuery;
    }

    public TupleQuery getSuperClassQuery(String nodeID, RepositoryConnection con) {
        TupleQuery superClassQuery = null;
        try {
            superClassQuery = con.prepareTupleQuery(QueryLanguage.SERQL, superClassQueryStr + namespace);
            ValueFactory valFac = con.getRepository().getValueFactory();
            superClassQuery.setBinding("p_termID", valFac.createLiteral(nodeID));
        } catch (RepositoryException e) {
            log.error("Error preparing super class query",e);
        } catch (MalformedQueryException e) {
            log.error("malformed super class query",e);
        }
        return superClassQuery;
    }
}
