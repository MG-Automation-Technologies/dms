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

package com.openkm.servlet;

import javax.jcr.Repository;
import javax.servlet.ServletException;

import org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.RepositoryException;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.webdav.LocatorFactoryImplEx;

/**
 * Servlet Class
 * 
 * @web.servlet name="Webdav" display-name="Name for Webdav"
 *              description="Description for Webdav" load-on-startup = "2"
 * @web.servlet-mapping url-pattern="/repository/*"
 * @web.servlet-init-param name="x-missing-auth-mapping" value="pavila:quickly5"
 * @web.servlet-init-param name="authenticate-header" value="Basic realm=\"OpenKM Webdav Server\""
 * @web.servlet-init-param name="resource-path-prefix" value="/repository"
 * @web.servlet-init-param name="resource-config" value="/WEB-INF/config.xml"
 */
public class WebdavServlet extends SimpleWebdavServlet {
	private static Logger log = LoggerFactory.getLogger(WebdavServlet.class);
	private static final long serialVersionUID = 8388108181983911121L;

	public void init() throws ServletException {
		log.info("*** Webdav initializing... ***");
		super.init();
		setLocatorFactory(new LocatorFactoryImplEx(getPathPrefix()));
		log.info("*** Webdav initialized ***");
	}
	
	@Override
	public Repository getRepository() {
		log.debug("getRepository()");
		Repository repository = null;
		
		try {
			repository = DirectRepositoryModule.getRepository();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		return repository;
	}
}
