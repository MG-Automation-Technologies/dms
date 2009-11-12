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

package es.git.openkm.frontend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.willow.ipsv.domain.model.Term;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMISMTServlet."
 *                           display-name="ISMT service"
 *                           description="ISMT service"
 * @web.servlet-mapping      url-pattern="/OKMISMTServlet."
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMISMTServlet extends OKMRemoteServiceServlet {
	private static Logger log = LoggerFactory.getLogger(OKMISMTServlet.class);
	
	
//	public List<Term> getISMTVocabulary() {
//        List terms = InvasiveSpeciesRDF.getInstance().getISMTTerms();
//        Collections.sort(terms,new TermComparator());
//        return terms;
//    }
}