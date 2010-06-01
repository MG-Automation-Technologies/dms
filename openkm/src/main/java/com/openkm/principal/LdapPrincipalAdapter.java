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

package com.openkm.principal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

/**
 * http://forums.sun.com/thread.jspa?threadID=581444
 * http://java.sun.com/docs/books/tutorial/jndi/ops/filter.html
 * http://www.openkm.com/Configuration/903-Rejavac-cannot-find-symbol-PrincipalAdapter.html
 */
public class LdapPrincipalAdapter implements PrincipalAdapter {
	private static Logger log = LoggerFactory.getLogger(LdapPrincipalAdapter.class);

	@Override
	public Collection<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> ldap = ldapSearch(
				Config.PRINCIPAL_LDAP_USER_SEARCH_BASE,
				Config.PRINCIPAL_LDAP_USER_SEARCH_FILTER,
				Config.PRINCIPAL_LDAP_USER_ATTRIBUTE);
		
		for (Iterator<String> it = ldap.iterator(); it.hasNext(); ) {
			String user = it.next();
			if (!Config.ADMIN_USER.equals(user) && !Config.SYSTEM_USER.equals(user)) {
				if (Config.SYSTEM_LOGIN_LOWERCASE) {
					user = user.toLowerCase();
				}
				
				list.add(user);
			}
		}

		log.debug("getUsers: {}", list);
		return list;
	}

	@Override
	public Collection<String> getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> ldap = ldapSearch(
				Config.PRINCIPAL_LDAP_ROLE_SEARCH_BASE, 
				Config.PRINCIPAL_LDAP_ROLE_SEARCH_FILTER,
				Config.PRINCIPAL_LDAP_ROLE_ATTRIBUTE);
		
		for (Iterator<String> it = ldap.iterator(); it.hasNext(); ) {
			String role = it.next();
			if (!Config.DEFAULT_ADMIN_ROLE.equals(role)) {
				list.add(role);
			}
		}

		log.debug("getRoles: {}", list);
		return list;
	}

	@Override
	public Collection<String> getMails(Collection<String> users)
			throws PrincipalAdapterException {
		log.debug("getMails()");
		ArrayList<String> list = new ArrayList<String>();
		
		for (Iterator<String> it = users.iterator(); it.hasNext();) {
			String user = it.next();
			ArrayList<String> ldap = ldapSearch(
					MessageFormat.format(Config.PRINCIPAL_LDAP_MAIL_SEARCH_BASE, user), 
					Config.PRINCIPAL_LDAP_MAIL_SEARCH_FILTER, 
					Config.PRINCIPAL_LDAP_MAIL_ATTRIBUTE);
			if (!ldap.isEmpty()) {
				list.add(ldap.get(0));
			}
		}

		log.debug("getMails: {}", list);
		return list;
	}
	
	/**
	 * LDAP Search
	 */
	private ArrayList<String> ldapSearch(String searchBase, String searchFilter, String attribute) {
		ArrayList<String> al = new ArrayList<String>();
		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.PROVIDER_URL, Config.PRINCIPAL_LDAP_SERVER);

		// Optional is some cases (Max OS/X)
		if (!Config.PRINCIPAL_LDAP_SECURITY_PRINCIPAL.equals(""))
			env.put(Context.SECURITY_PRINCIPAL, Config.PRINCIPAL_LDAP_SECURITY_PRINCIPAL);
		if (!Config.PRINCIPAL_LDAP_SECURITY_CREDENTIALS.equals(""))
			env.put(Context.SECURITY_CREDENTIALS, Config.PRINCIPAL_LDAP_SECURITY_CREDENTIALS);			
		
		try {
			DirContext ctx = new InitialDirContext(env);
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchCtls);

			while (results.hasMore()) {
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attrib = attributes.get(attribute);
				
				if (attrib != null) {
					String item = (String) attrib.get();
					al.add(item);
				}
			}

			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return al;
	}
}
