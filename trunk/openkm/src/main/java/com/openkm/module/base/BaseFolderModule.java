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

package com.openkm.module.base;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.util.TraversingItemVisitor;
import javax.jcr.version.VersionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.ContentInfo;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Mail;

public class BaseFolderModule {
	private static Logger log = LoggerFactory.getLogger(BaseFolderModule.class);
	
	/**
	 * Quicker version of getContentInfo but surpases user permissions
	 * 
	 * Could be a replacement of OKMFolder.getContentInfo() at:
	 * 
	 * @see com.openkm.servlet.admin.BenchmarkServlet
	 * @see com.openkm.servlet.admin.RepositoryCheckerServlet
	 * @see com.openkm.servlet.admin.RepositoryViewServlet
	 * 
	 * And in getCount(QueryManager, String) at :
	 * 
	 * @see com.openkm.module.direct.DirectStatsModule
	 */
	public static void getContentInfoVisitor(Session session, Node node) throws NoSuchNodeTypeException,
			VersionException, ConstraintViolationException, LockException, RepositoryException {
		log.debug("getContentInfoVisitor({}, {})", new Object[] { session, node });
		final ContentInfo ci = new ContentInfo();
		
		TraversingItemVisitor visitor = new TraversingItemVisitor.Default() {
			@Override
			protected void entering(Node node, int level) throws javax.jcr.RepositoryException {
				if (node.isNodeType(Folder.TYPE)) {
					ci.setFolders(ci.getFolders() + 1);
				} else if (node.isNodeType(Document.TYPE)) {
					Node docContent = node.getNode(Document.CONTENT);
					long size = docContent.getProperty(Document.SIZE).getLong();
					ci.setDocuments(ci.getDocuments() + 1);
					ci.setSize(ci.getSize() + size);
				} else if (node.isNodeType(Mail.TYPE)) {
					long size = node.getProperty(Mail.SIZE).getLong();
					ci.setMails(ci.getMails() + 1);
					ci.setSize(ci.getSize() + size);
				}
			}
		};
	
		node.accept(visitor);
	}
}
