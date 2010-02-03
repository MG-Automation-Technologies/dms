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

package com.openkm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.VirusDetectedException;

public class JCRUtils {
	private static Logger log = LoggerFactory.getLogger(JCRUtils.class);
	private static int total = 0;
	
	/**
	 * 
	 * @param token
	 * @param root
	 * @param directory
	 * @param extensions
	 * @param recursive
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws javax.jcr.RepositoryException
	 * @throws AccessDeniedException
	 */
	public static void importFolder(String token, String root, File directory,
			String[] extensions) throws RepositoryException, AccessDeniedException, 
			ItemExistsException, PathNotFoundException, UnsupportedMimeTypeException,
			FileSizeExceededException, VirusDetectedException, IOException {
		log.debug("importFolder("+token+", "+root+", "+directory+")");
		Folder fld = new Folder();
		fld.setPath(root+"/"+directory.getName());
		Folder newFolder = OKMFolder.getInstance().create(token, fld);
		log.info("Create folder '" + directory.getName() + 
				"' in '" + root + " -> " + newFolder.getPath()+"'");
		File[] files = directory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				importFolder(token, newFolder.getPath(), files[i], extensions);
			} else {
				boolean extOk = false;

				if (extensions != null) {
					for (int j = 0; j < extensions.length; j++) {
						if (files[i].getName().endsWith(extensions[j])) {
							extOk = true;
						}
					}
				} else {
					extOk = true;
				}
				
				if (extOk) {
					log.info((total++)+": Import document '" + files[i] + "' in '"
						+ newFolder.getPath() + "'");
					Document doc = new Document();
					FileInputStream fis = new FileInputStream(files[i].getAbsolutePath());
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(files[i].lastModified());
					doc.setKeywords(Arrays.asList(new String[]{"Automatically imported"}));
					doc.setPath(newFolder.getPath()+"/"+files[i].getName());
					OKMDocument.getInstance().create(token, doc, fis);
					fis.close();
				}
			}
		}
	}

	/**
	 * @param values
	 * @param val
	 * @return
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws javax.jcr.RepositoryException
	 */
	public static String[] usrValue2String(Value[] values, String val) throws ValueFormatException, IllegalStateException, javax.jcr.RepositoryException {
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i=0; i<values.length; i++) {
			// Admin and System user is not propagated across the child nodes
			if (!values[i].getString().equals(Config.SYSTEM_USER) && 
					!values[i].getString().equals(Config.ADMIN_USER)) {
				list.add(values[i].getString()); 
			}
		}
		
		// No add an user twice
		if (!list.contains(val)) {
			list.add(val);
		}
		
		return (String[]) list.toArray(new String[0]);
	}
	
	/**
	 * @param values
	 * @return
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws javax.jcr.RepositoryException
	 */
	public static String[] rolValue2String(Value[] values) throws ValueFormatException, IllegalStateException, javax.jcr.RepositoryException {
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i=0; i<values.length; i++) {
			// Do not propagate private OpenKM roles
			if (!values[i].getString().equals(Config.DEFAULT_ADMIN_ROLE)) {
				list.add(values[i].getString()); 
			}
		}
		
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * @param values
	 * @return
	 * @throws ValueFormatException
	 * @throws IllegalStateException
	 * @throws javax.jcr.RepositoryException
	 */
	public static String[] value2String(Value[] values) throws ValueFormatException, IllegalStateException, javax.jcr.RepositoryException {
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i=0; i<values.length; i++) {
			list.add(values[i].getString()); 
		}
		
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * This method discards all pending changes currently recorded in this
	 * Session that apply to this Node or any of its descendants.
	 *  
	 * @param node The node to cancel.
	 */
	public static void discardsPendingChanges(Node node) {
		try {
			// JSR-170: page 173
			// http://www.day.com/maven/jsr170/javadocs/jcr-1.0/javax/jcr/Item.html#refresh(boolean)
			if (node != null) {
				node.refresh(false);
			} else {
				log.warn("node == NULL");
			}
		} catch (javax.jcr.RepositoryException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method discards all pending changes currently recorded in this
	 * Session that apply to this Session.
	 *  
	 * @param node The node to cancel.
	 */
	public static void discardsPendingChanges(Session session) {
		try {
			// http://www.day.com/maven/jsr170/javadocs/jcr-1.0/javax/jcr/Session.html#refresh(boolean)
			if (session != null) {
				session.refresh(false);
			} else {
				log.warn("session == NULL");
			}
		} catch (javax.jcr.RepositoryException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @param id
	 * @return
	 */
	public static String getLockToken(String id) {
		StringBuffer buf = new StringBuffer();
		buf.append(id.toString());
		buf.append('-');
		buf.append(getCheckDigit(id.toString()));
		return buf.toString();
	}
	
	 /**
	 * @param uuid
	 * @return
	 */
	private static char getCheckDigit(String uuid) {
        int result = 0;

        int multiplier = 36;
        for (int i = 0; i < uuid.length(); i++) {
            char c = uuid.charAt(i);
            if (c >= '0' && c <= '9') {
                int num = c - '0';
                result += multiplier * num;
                multiplier--;
            } else if (c >= 'A' && c <= 'F') {
                int num = c - 'A' + 10;
                result += multiplier * num;
                multiplier--;
            } else if (c >= 'a' && c <= 'f') {
                int num = c - 'a' + 10;
                result += multiplier * num;
                multiplier--;
            }
        }

        int rem = result % 37;
        if (rem != 0) {
            rem = 37 - rem;
        }
        if (rem >= 0 && rem <= 9) {
            return (char) ('0' + rem);
        } else if (rem >= 10 && rem <= 35) {
            return (char) ('A' + rem - 10);
        } else {
            return '+';
        }
    }
}
