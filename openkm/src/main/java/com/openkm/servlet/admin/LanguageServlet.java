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

package com.openkm.servlet.admin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.LanguageDAO;
import com.openkm.dao.LegacyDAO;
import com.openkm.dao.bean.Language;
import com.openkm.dao.bean.Translation;
import com.openkm.util.JCRUtils;
import com.openkm.util.SecureStore;
import com.openkm.util.WarUtils;
import com.openkm.util.WebUtil;

/**
 * Language servlet
 */
public class LanguageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(LanguageServlet.class);
	private final String LANG_BASE_CODE = "en-GB";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("edit")) {
				edit(session, request, response);
			} else if (action.equals("delete")) {
				delete(session, request, response);
			} else if (action.equals("create")) {
				create(session, request, response);
			} else if (action.equals("translate")) {
				translate(session, request, response);
			} else if (action.equals("flag")) {
				flag(session, request, response);
			} else if (action.equals("export")) {
				export(session, request, response);
			} 
			
			if (action.equals("") || WebUtil.getBoolean(request, "persist")) {
				list(session, request, response);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (com.openkm.core.RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.debug("doPost({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		boolean persist = false;
		Session session = null;
		org.hibernate.classic.Session hibernateSession = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (ServletFileUpload.isMultipartContent(request)) {
				session = JCRUtils.getSession();
				InputStream is = null;
				FileItemFactory factory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				String lgId = "";
				String name = "";
				String mimeType = "";
				byte data[] = null;
				
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
					
					if (item.isFormField()) {
						if (item.getFieldName().equals("action")) {
							action = item.getString("UTF-8");
						} else if (item.getFieldName().equals("lg_id")) {
							lgId = item.getString("UTF-8");
						} else if (item.getFieldName().equals("lg_name")) {
							name = item.getString("UTF-8");
						} else if (item.getFieldName().equals("persist")) {
							persist = true;
						} 
					} else {
						if (item.getSize()>0) {
							mimeType = Config.mimeTypes.getContentType(item.getName());
							is = item.getInputStream();
							data = IOUtils.toByteArray(is);
							is.close();
						}
					}
				}

				if (action.equals("create")) {
					Language language = new Language();
					language.setName(name);
					language.setImageMime(mimeType);
					if (data != null && data.length>0) {
						language.setImageContent(SecureStore.b64Encode(data));
					}
					LanguageDAO.create(language);
				} else if (action.equals("edit")) {
					Language language = LanguageDAO.findByPk(lgId);
					language.setName(name);
					language.setImageMime(mimeType);
					if (data != null && data.length>0) {
						language.setImageContent(SecureStore.b64Encode(data));
					}
					LanguageDAO.update(language);
				} else if (action.equals("import")) {
					hibernateSession = HibernateUtil.getSessionFactory().openSession();
					importLanguage(session, request, response, data, hibernateSession);
				}
				
			} else  if (action.equals("translate")) {
				translate(session, request, response);
			}
			
			if (action.equals("") || action.equals("import") || WebUtil.getBoolean(request, "persist") || persist ) {
				list(session, request, response);
			}
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (com.openkm.core.RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			if (hibernateSession!=null) {
				HibernateUtil.close(hibernateSession);
			}
			JCRUtils.logout(session);
			
		}
	}
	
	/**
	 * List languages
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, com.openkm.core.RepositoryException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("langs", LanguageDAO.findAll());	
		sc.setAttribute("max", LanguageDAO.findByPk(LANG_BASE_CODE).getTranslations().size()); // Translations reference is english
		sc.getRequestDispatcher("/admin/language_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Delete language
	 */
	private void delete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("delete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String lgId = WebUtil.getString(request, "lg_id");
			LanguageDAO.delete(lgId);
		} else {
			ServletContext sc = getServletContext();
			String lgId = WebUtil.getString(request, "lg_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", LanguageDAO.findByPk(lgId));
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Edit language
	 */
	private void edit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String lgId = WebUtil.getString(request, "lg_id");
			String lgName = WebUtil.getString(request, "lg_name");
			Language language = LanguageDAO.findByPk(lgId);
			language.setName(lgName);
			LanguageDAO.update(language);
		} else {
			ServletContext sc = getServletContext();
			String lgId = WebUtil.getString(request, "lg_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", LanguageDAO.findByPk(lgId));
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}
	
	/**
	 * Create language
	 */
	private void create(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (!WebUtil.getBoolean(request, "persist")) {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", null);
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}
	
	/**
	 * Translate language
	 */
	private void translate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("translate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			Set<Translation> newTranslations = new HashSet<Translation>();
			Language langBase = LanguageDAO.findByPk(LANG_BASE_CODE);
			for (Translation translation : langBase.getTranslations()) {
				String text = WebUtil.getString(request, String.valueOf(translation.getKey()));
				if (!text.equals("")) {
					Translation newTranslation = new Translation();
					newTranslation.setModule(translation.getModule());
					newTranslation.setKey(translation.getKey());
					newTranslation.setText(text);
					newTranslations.add(newTranslation);
				}
			}
			String lgId = WebUtil.getString(request, "lg_id");
			Language language = LanguageDAO.findByPk(lgId);
			language.setTranslations(newTranslations);
			LanguageDAO.update(language);
		} else {
			ServletContext sc = getServletContext();
			String lgId = WebUtil.getString(request, "lg_id");
			Language langToTranslate = LanguageDAO.findByPk(lgId);
			Map<String, String> translations = new HashMap<String, String>();
			for (Translation translation : langToTranslate.getTranslations()) {
				translations.put(translation.getKey(), translation.getText());
			}
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lg_id", lgId);
			sc.setAttribute("langToTranslateName", langToTranslate.getName());
			sc.setAttribute("translations",translations);
			sc.setAttribute("langBase", LanguageDAO.findByPk(LANG_BASE_CODE)); // English always it'll be used as a translations base
			sc.getRequestDispatcher("/admin/translation_edit.jsp").forward(request, response);
		}
		
		log.debug("translate: void");
	}
	
	private void flag(Session session, HttpServletRequest request, HttpServletResponse response) throws DatabaseException, IOException {
		log.debug("flag({}, {}, {})", new Object[] { session, request, response });
		String lgId = WebUtil.getString(request, "lg_id");
		Language language = LanguageDAO.findByPk(lgId);
		
		response.setContentType(language.getImageMime());
		ServletOutputStream out = response.getOutputStream();
		out.write(SecureStore.b64Decode(new String(language.getImageContent())));
		out.flush();
		log.debug("falg: flag");
	}
	
	private void export(Session session, HttpServletRequest request, HttpServletResponse response) throws DatabaseException, IOException {
		log.debug("export({}, {}, {})", new Object[] { session, request, response });
		String lgId = WebUtil.getString(request, "lg_id");
		Language language = LanguageDAO.findByPk(lgId);
		String agent = request.getHeader("USER-AGENT");
		
		// Disable browser cache
		response.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		response.setHeader("Cache-Control", "max-age=0, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		String fileName = "OpenKM_" + WarUtils.getAppVersion() + "_" +language.getId() + "_" + language.getTranslations().size() + ".sql";
		
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			log.debug("Agent: Explorer");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (null != agent && -1 != agent.indexOf("Mozilla"))	{
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}
		
		response.setHeader("Content-disposition", "inline; filename=\""+fileName+"\"");		
		response.setContentType("text/x-sql; charset=UTF-8");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
		out.println("DELETE FROM OKM_TRANSLATION WHERE TR_LANGUAGE='" + language.getId() +"';" );
		out.println("DELETE FROM OKM_LANGUAGE WHERE LG_ID='" + language.getId() +"';" );
		StringBuffer insertLang = new StringBuffer("INSERT INTO OKM_LANGUAGE (LG_ID, LG_NAME, LG_IMAGE_CONTENT, LG_IMAGE_MIME ) VALUES ('");
		insertLang.append(language.getId() + "', '" + language.getName() + "', '" + language.getImageContent() + "', '");
		insertLang.append(language.getImageMime()+"');");
		out.println(insertLang);
		for (Translation translation : language.getTranslations()) {
			StringBuffer insertTranslation = new StringBuffer("INSERT INTO OKM_TRANSLATION (TR_MODULE, TR_KEY, TR_TEXT, TR_LANGUAGE) VALUES ('");
			insertTranslation.append(translation.getModule() + "', '" +translation.getKey() + "', '");
			insertTranslation.append(translation.getText() + "', '" +language.getId() + "');");
			out.println(insertTranslation);
		}
		out.flush();
		log.debug("export: sql-file");
	}
	
	private void importLanguage(Session session, HttpServletRequest request, HttpServletResponse response, byte[] data,
								org.hibernate.classic.Session hibernateSession) throws DatabaseException, IOException, SQLException {
		log.debug("import({}, {}, {})", new Object[] { session, request, response });
		Connection con = hibernateSession.connection();
		Statement stmt = con.createStatement();
		InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(data));
		BufferedReader br = new BufferedReader(is);
		String query;
		
		while ((query = br.readLine())!=null) {
			stmt.executeUpdate(query);
		}
		
		LegacyDAO.close(stmt);
		LegacyDAO.close(con);
		
		HibernateUtil.close(hibernateSession);

		log.debug("import: void");
	}
}
