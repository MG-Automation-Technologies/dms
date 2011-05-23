/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.LegacyDAO;
import com.openkm.util.UserActivity;

/**
 * Database query
 */
public class DatabaseQueryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DatabaseQueryServlet.class);
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		String method = request.getMethod();
		
		if (checkMultipleInstancesAccess(request, response)) {
			if (method.equals(METHOD_GET)) {
				doGet(request, response);
			} else if (method.equals(METHOD_POST)) {
				doPost(request, response);
			}
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		updateSessionManager(request);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			ServletContext sc = getServletContext();
			sc.setAttribute("qs", null);
			//sc.setAttribute("sql", null);
			sc.setAttribute("method", null);
			sc.setAttribute("globalResults", null);
			sc.setAttribute("tables", listTables(session));
			sc.getRequestDispatcher("/admin/database_query.jsp").forward(request, response);
		} catch (Exception e) {
			sendErrorRedirect(request,response, e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doPost({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		Session session = null;
		updateSessionManager(request);
		
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				FileItemFactory factory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				InputStream is = null;
				String method = "";
				String qs = "";
				byte[] data = null;
				
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
					
					if (item.isFormField()) {
						if (item.getFieldName().equals("qs")) {
							qs = item.getString("UTF-8");
						} else if (item.getFieldName().equals("method")) {
							method = item.getString("UTF-8");
						}
					} else {
						is = item.getInputStream();
						data = IOUtils.toByteArray(is);
						is.close();
					}
				}
				
				if (!qs.equals("") && !method.equals("")) {
					session = HibernateUtil.getSessionFactory().openSession();
					
					if (method.equals("jdbc")) {
						executeJdbc(session, qs, request, response);
					} else if (method.equals("hibernate")) {
						executeHibernate(session, qs, request, response);
					}
					
					// Activity log
					UserActivity.log(request.getRemoteUser(), "ADMIN_DATABASE_QUERY", null, qs);
				} else if (data != null && data.length > 0) {
					session = HibernateUtil.getSessionFactory().openSession();
					executeUpdate(session, data, request, response);
					
					// Activity log
					UserActivity.log(request.getRemoteUser(), "ADMIN_DATABASE_UPDATE", null, new String(data));
				} else {
					ServletContext sc = getServletContext();
					sc.setAttribute("qs", qs);
					sc.setAttribute("method", null);
					//sc.setAttribute("tables", listTables(session));
					sc.setAttribute("globalResults", new ArrayList<DatabaseQueryServlet.GlobalResult>());
					sc.getRequestDispatcher("/admin/database_query.jsp").forward(request, response);
				}
			}
		} catch (FileUploadException e) {
			sendErrorRedirect(request,response, e);
		} catch (SQLException e) {
			sendErrorRedirect(request,response, e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Execute Hibernate query
	 */
	@SuppressWarnings("unchecked")
	private void executeHibernate(Session session, String qs, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		StringTokenizer st = new StringTokenizer(qs, "\n");
		List<GlobalResult> globalResults = new ArrayList<DatabaseQueryServlet.GlobalResult>();
		
		// For each query line
		while (st.hasMoreTokens()) {
			String tk = st.nextToken();
			
			if (tk.toUpperCase().startsWith("SELECT") || tk.toUpperCase().startsWith("FROM")) {
				Query q = session.createQuery(tk);
				List<Object> ret = q.list();
				List<String> columns = new ArrayList<String>();
				List<List<String>> results = new ArrayList<List<String>>();
				Type[] rt = q.getReturnTypes();
				int i = 0;
				
				for (i=0; i<rt.length; i++) {
					columns.add(rt[i].getName());
				}
				
				for (Iterator<Object> it = ret.iterator(); it.hasNext() && i++ < Config.MAX_SEARCH_RESULTS; ) {
					List<String> row = new ArrayList<String>();
					Object obj = it.next();
					
					if (obj instanceof Object[]) {
						Object[] ao = (Object[]) obj;
						for (int j=0; j<ao.length; j++) {
							row.add(ao[j].toString());
						}
					} else {
						row.add(obj.toString());	
					}
					
					results.add(row);
				}
				
				GlobalResult gr = new GlobalResult();
				gr.setColumns(columns);
				gr.setResults(results);
				gr.setRows(null);
				gr.setSql(tk);
				globalResults.add(gr);
			} else {
				GlobalResult gr = new GlobalResult();
				int rows = session.createQuery(qs).executeUpdate();
				gr.setColumns(null);
				gr.setResults(null);
				gr.setRows(rows);
				gr.setSql(tk);
				globalResults.add(gr);
			}
		}
		
		//sc.setAttribute("sql", HibernateUtil.toSql(qs));
		sc.setAttribute("qs", qs);
		sc.setAttribute("method", "hibernate");
		//sc.setAttribute("tables", listTables(session));
		sc.setAttribute("globalResults", globalResults);
		sc.getRequestDispatcher("/admin/database_query.jsp").forward(request, response);
	}
	
	/**
	 * Execute JDBC query
	 */
	private void executeJdbc(Session session, String qs, HttpServletRequest request, 
			HttpServletResponse response) throws SQLException, ServletException, IOException {
		ServletContext sc = getServletContext();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<GlobalResult> globalResults = new ArrayList<DatabaseQueryServlet.GlobalResult>();
		
		try {
			con = session.connection();
			stmt = con.createStatement();
			StringTokenizer st = new StringTokenizer(qs, "\n");
			
			// For each query line
			while (st.hasMoreTokens()) {
				String tk = st.nextToken();
				
				if (tk.toUpperCase().startsWith("--") || tk.equals("") || tk.equals("\r")) {
					// Is a comment, so ignore it
				} else if (tk.toUpperCase().startsWith("SELECT")) {
					rs = stmt.executeQuery(tk);
					ResultSetMetaData md = rs.getMetaData();
					List<String> columns = new ArrayList<String>();
					List<List<String>> results = new ArrayList<List<String>>();
										
					for (int i=1; i<md.getColumnCount()+1; i++) {
						columns.add(md.getColumnName(i));
					}
					
					for (int i=0; rs.next() && i++ < Config.MAX_SEARCH_RESULTS; ) {
						List<String> row = new ArrayList<String>();
						for (int j=1; j<md.getColumnCount()+1; j++) {
							row.add(rs.getString(j));
						}
						results.add(row);
					}
					
					GlobalResult gr = new GlobalResult();
					gr.setColumns(columns);
					gr.setResults(results);
					gr.setRows(null);
					gr.setSql(tk);
					globalResults.add(gr);
				} else {
					GlobalResult gr = new GlobalResult();
					int rows = stmt.executeUpdate(tk);
					gr.setColumns(null);
					gr.setResults(null);
					gr.setRows(rows);
					gr.setSql(tk);
					globalResults.add(gr);
				}
			}
		} finally {
			LegacyDAO.close(rs);
			LegacyDAO.close(stmt);
			LegacyDAO.close(con);
		}
		
		//sc.setAttribute("sql", null);
		sc.setAttribute("qs", qs);
		sc.setAttribute("method", "jdbc");
		//sc.setAttribute("tables", listTables(session));
		sc.setAttribute("globalResults", globalResults);
		sc.getRequestDispatcher("/admin/database_query.jsp").forward(request, response);
	}
	
	/**
	 * Import into database
	 */
	private void executeUpdate(Session session, byte[] data, HttpServletRequest request, 
			HttpServletResponse response) throws SQLException, ServletException, IOException {
		log.debug("executeUpdate({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<HashMap<String, String>> errors = new ArrayList<HashMap<String, String>>();
		List<GlobalResult> globalResults = new ArrayList<DatabaseQueryServlet.GlobalResult>();
		int rows = 0;
		
		try {
			con = session.connection();
			stmt = con.createStatement();
			InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(data));
			BufferedReader br = new BufferedReader(is);
			String sql;
			int ln = 0;
			
			while ((sql = br.readLine()) != null) {
				ln++;
				
				if (sql.length() > 0 && !sql.startsWith("--")) {
					try {
						rows += stmt.executeUpdate(sql);
					} catch (SQLException e) {
						HashMap<String, String> error = new HashMap<String, String>();
						error.put("ln", Integer.toString(ln));
						error.put("sql", sql);
						error.put("msg", e.getMessage());
						errors.add(error);
					}
				}
			}
		} finally {
			LegacyDAO.close(rs);
			LegacyDAO.close(stmt);
			LegacyDAO.close(con);
		}
		
		GlobalResult gr = new GlobalResult();
		gr.setColumns(null);
		gr.setResults(null);
		gr.setRows(rows);
		gr.setErrors(errors);
		globalResults.add(gr);
		
		sc.setAttribute("qs", null);
		sc.setAttribute("method", null);
		//sc.setAttribute("tables", listTables(session));
		sc.setAttribute("globalResults", globalResults);
		sc.getRequestDispatcher("/admin/database_query.jsp").forward(request, response);
		
		log.debug("executeUpdate: void");
	}
	
	/**
	 * List tables from database
	 */
	private List<String> listTables(Session session) {
		final List<String> tables = new ArrayList<String>();
		
		session.doWork(
			new Work() {
				@Override
				public void execute(Connection con) throws SQLException {
					DatabaseMetaData md = con.getMetaData();
					ResultSet rs = md.getTables(null, null, "%", null);
					
					while (rs.next()) {
						tables.add(rs.getString(3));
					}
				}
			}
		);
		
		return tables;
	}
	
	/**
	 * Container helper class
	 */
	public class GlobalResult {
		private List<HashMap<String, String>> errors = new ArrayList<HashMap<String, String>>();
		private List<List<String>> results = new ArrayList<List<String>>();
		private List<String> columns = new ArrayList<String>();
		private Integer rows = new Integer(0);
		private String sql = new String();
		
		public List<String> getColumns() {
			return columns;
		}
		
		public void setColumns(List<String> columns) {
			this.columns = columns;
		}
		
		public List<List<String>> getResults() {
			return results;
		}
		
		public void setResults(List<List<String>> results) {
			this.results = results;
		}
		
		public Integer getRows() {
			return rows;
		}
		
		public void setRows(Integer rows) {
			this.rows = rows;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

		public List<HashMap<String, String>> getErrors() {
			return errors;
		}

		public void setErrors(List<HashMap<String, String>> errors) {
			this.errors = errors;
		}
	}
}
