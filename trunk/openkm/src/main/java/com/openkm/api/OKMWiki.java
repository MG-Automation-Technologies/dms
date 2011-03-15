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

package com.openkm.api;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Lock;
import com.openkm.bean.Version;
import com.openkm.bean.Wiki;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VersionException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.ModuleManager;
import com.openkm.module.WikiModule;

/**
 * @author pavila
 *
 */
public class OKMWiki implements WikiModule {
	private static Logger log = LoggerFactory.getLogger(OKMWiki.class);
	private static OKMWiki instance = new OKMWiki();

	private OKMWiki() {}
	
	public static OKMWiki getInstance() {
		return instance;
	}
	
	@Override
	public Wiki create(String token, Wiki wiki, String content) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException,
			DatabaseException {
		log.debug("create({}, {}, {})", new Object[] { token, wiki, content });
		WikiModule wm = ModuleManager.getWikiModule();
		Wiki newWiki = wm.create(token, wiki, content);
		log.debug("create: {}", newWiki);
		return newWiki;
	}
	
	public Wiki createSimple(String token, String wikiPath, String content) throws 
			VirusDetectedException, ItemExistsException, PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException, DatabaseException {
		log.debug("createSimple({}, {}, {})", new Object[] { token, wikiPath, content});
		WikiModule wm = ModuleManager.getWikiModule();
		Wiki wiki = new Wiki();
		wiki.setPath(wikiPath);
		Wiki newWiki = wm.create(token, wiki, content);
		log.debug("createSimple: {}", newWiki);
		return newWiki;
	}
	
	@Override
	public void delete(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("delete({})", wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.delete(token, wikiPath);
		log.debug("delete: void");
	}
	
	@Override
	public Wiki getProperties(String token, String wikiPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("getProperties({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		Wiki wiki = wm.getProperties(token, wikiPath);
		log.debug("getProperties: {}", wiki);
		return wiki;
	}

	@Override
	public String getContent(String token, String wikiPath, boolean checkout) throws 
			PathNotFoundException, RepositoryException, IOException, DatabaseException {
		log.debug("getContent({}, {}, {})", new Object[] { token, wikiPath, checkout });
		WikiModule wm = ModuleManager.getWikiModule();
		String ret = wm.getContent(token, wikiPath, checkout);
		log.debug("getContent: {}", ret);
		return ret;
	}
	
	@Override
	public String getContentByVersion(String token, String wikiPath, String versionId) throws
			RepositoryException, PathNotFoundException, IOException, DatabaseException {
		log.debug("getContentByVersion({}, {}, {})", new Object[] { token, wikiPath, versionId });
		WikiModule wm = ModuleManager.getWikiModule();
		String ret = wm.getContentByVersion(token, wikiPath, versionId);
		log.debug("getContentByVersion: {}", ret);
		return ret;
	}
		
	@Override
	public List<Wiki> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({}, {})", token, fldPath);
		WikiModule wm = ModuleManager.getWikiModule();
		List<Wiki> col = wm.getChilds(token, fldPath);
		log.debug("getChilds: {}", col);
		return col;
	}

	@Override
	public Wiki rename(String token, String wikiPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("rename({}, {}, {})", new Object[] { token, wikiPath, newName });
		WikiModule wm = ModuleManager.getWikiModule();
		Wiki renamedWiki = wm.rename(token, wikiPath, newName);
		log.debug("rename: {}", renamedWiki);
		return renamedWiki;
	}

	@Override
	public void setProperties(String token, Wiki wiki) throws LockException, VersionException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setProperties({}, {})", token, wiki);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.setProperties(token, wiki);
		log.debug("setProperties: void");
	}

	@Override
	public void checkout(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("checkout({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.checkout(token, wikiPath);
		log.debug("checkout: void");
	}

	@Override
	public void cancelCheckout(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("cancelCheckout({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.cancelCheckout(token, wikiPath);
		log.debug("cancelCheckout: void");
	}
	
	@Override
	public boolean isCheckedOut(String token, String wikiPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("isCheckedOut({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		boolean checkedOut = wm.isCheckedOut(token, wikiPath);
		log.debug("isCheckedOut: {}", checkedOut);
		return checkedOut;
	}
	
	@Override
	public Version checkin(String token, String wikiPath, String comment) throws LockException,
			VersionException, PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("checkin({}, {}, {})", new Object[] { token, wikiPath, comment });
		WikiModule wm = ModuleManager.getWikiModule();
		Version version = wm.checkin(token, wikiPath, comment);
		log.debug("checkin: {}", version);
		return version;
	}

	@Override
	public List<Version> getVersionHistory(String token, String wikiPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getVersionHistory({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		List<Version> history = wm.getVersionHistory(token, wikiPath);
		log.debug("getVersionHistory: {}", history);
		return history;
	}

	@Override
	public void setContent(String token, String wikiPath, String content) throws VersionException,
			LockException, PathNotFoundException, AccessDeniedException, RepositoryException,
			IOException, DatabaseException {
		log.debug("setContent({}, {}, {})", new Object[] { token, wikiPath, content });
		WikiModule wm = ModuleManager.getWikiModule();
		wm.setContent(token, wikiPath, content);
		log.debug("setContent: void");
	}

	@Override
	public void lock(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("lock({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.lock(token, wikiPath);
		log.debug("lock: void");
	}

	@Override
	public void unlock(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("unlock({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.unlock(token, wikiPath);
		log.debug("unlock: void");
	}

	@Override
	public boolean isLocked(String token, String wikiPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("isLocked({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		boolean locked = wm.isLocked(token, wikiPath);
		log.debug("isLocked: {}", locked);
		return locked;
	}

	@Override
	public Lock getLock(String token, String wikiPath) throws LockException, PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getLock({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		Lock lock = wm.getLock(token, wikiPath);
		log.debug("getLock: {}", lock);
		return lock;
	}

	@Override
	public void purge(String token, String wikiPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("purge({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		wm.purge(token, wikiPath);
		log.debug("purge: void");
	}

	@Override
	public void move(String token, String wikiPath, String destPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {}, {})", new Object[] { token, wikiPath, destPath });
		WikiModule wm = ModuleManager.getWikiModule();
		wm.move(token, wikiPath, destPath);
		log.debug("move: void");
	}

	@Override
	public void copy(String token, String wikiPath, String destPath) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException,
			DatabaseException, UserQuotaExceededException {
		log.debug("copy({}, {}, {})", new Object[] { token, wikiPath, destPath });
		WikiModule wm = ModuleManager.getWikiModule();
		wm.copy(token, wikiPath, destPath);
		log.debug("copy: void");
	}
	
	@Override
	public void restoreVersion(String token, String wikiPath, String versionId) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("restoreVersion({}, {}, {})", new Object[] { token, wikiPath, versionId });
		WikiModule wm = ModuleManager.getWikiModule();
		wm.restoreVersion(token, wikiPath, versionId);
		log.debug("restoreVersion: void");
	}

	@Override
	public boolean isValid(String token, String wikiPath) throws PathNotFoundException, AccessDeniedException, 
			RepositoryException, DatabaseException {
		log.debug("isValid({}, {})", token, wikiPath);
		WikiModule wm = ModuleManager.getWikiModule();
		boolean valid = wm.isValid(token, wikiPath);
		log.debug("isValid: {}", valid);
		return valid;
	}

	@Override
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("getPath({}, {})", token, uuid);
		WikiModule wm = ModuleManager.getWikiModule();
		String path = wm.getPath(token, uuid);
		log.debug("getPath: {}", path);
		return path;
	}
}
