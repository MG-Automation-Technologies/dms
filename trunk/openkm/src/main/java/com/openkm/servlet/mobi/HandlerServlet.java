package com.openkm.servlet.mobi;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMSearch;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserAlreadyLoggerException;

/**
 * Servlet implementation class HandlerServlet
 */
public class HandlerServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(HandlerServlet.class);
	private static final long serialVersionUID = 1L;
    
    public HandlerServlet() {
        super();
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
			ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action")!=null?request.getParameter("action"):"";
		HttpSession session = request.getSession();
		 
		try {
			if (Config.SESSION_MANAGER) {
				if (session.getAttribute("token") == null) {
					String token = OKMAuth.getInstance().login();
					session.setAttribute("token", token);
				}
			}
			
			if (action.equals("") || action.equals("browse")) {
				browse(request, response);
			} else if (action.equals("fldprop")) {
				fldProperties(request, response);
			} else if (action.equals("docprop")) {
				docProperties(request, response);
			} else if (action.equals("search")) {
				search(request, response);
			} else if (action.equals("logout")) {
				logout(request, response);
			}
		} catch (UserAlreadyLoggerException e) {
			sendErrorRedirect(request,response, e);
		} catch (AccessDeniedException e) {
			sendErrorRedirect(request,response, e);
		} catch (PathNotFoundException e) {
			sendErrorRedirect(request,response, e);
		} catch (ParseException e) {
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			sendErrorRedirect(request,response, e);
		}
	}
	
	/**
	 * Dispatch errors 
	 */
	private void sendErrorRedirect(HttpServletRequest request, HttpServletResponse response,
			Throwable e) throws ServletException, IOException {
		request.setAttribute ("javax.servlet.jsp.jspException", e);
		ServletContext sc = getServletConfig().getServletContext();
		sc.getRequestDispatcher("/error.jsp").forward(request, response);
	}
	
	/**
	 * List contents
	 */
	private void browse(HttpServletRequest request, HttpServletResponse response) throws 
			PathNotFoundException, RepositoryException, IOException, ServletException {
		log.info("browse({}, {})", request, response);
		ServletContext sc = getServletContext();
		String token = (String) request.getSession().getAttribute("token");
		String userId = request.getRemoteUser();
		String path = request.getParameter("path");
		
		if (path == null || path.equals("")) {
			path = "/okm:root";
		}
		  
		path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
		sc.setAttribute("folderChilds", OKMFolder.getInstance().getChilds(token, path));
		sc.setAttribute("documentChilds", OKMDocument.getInstance().getChilds(token, path));
		sc.setAttribute("userId", userId);
		sc.setAttribute("path", path);
		sc.getRequestDispatcher("/mobi/browse.jsp").forward(request, response);
	}
	
	/**
	 * Folder properties
	 */
	private void fldProperties(HttpServletRequest request, HttpServletResponse response) throws
			PathNotFoundException, RepositoryException, IOException, ServletException {
		log.info("fldProperties({}, {})", request, response);
		ServletContext sc = getServletContext();
		String token = (String) request.getSession().getAttribute("token");
		String path = request.getParameter("path");
		
		if (path == null || path.equals("")) {
			path = "/okm:root";
		}
		
		path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
		sc.setAttribute("fld", OKMFolder.getInstance().getProperties(token, path));
		sc.setAttribute("path", path);
		sc.getRequestDispatcher("/mobi/fld-properties.jsp").forward(request, response);
	}
	
	/**
	 * Document properties
	 */
	private void docProperties(HttpServletRequest request, HttpServletResponse response) throws
			PathNotFoundException, RepositoryException, IOException, ServletException {
		log.info("docProperties({}, {})", request, response);
		ServletContext sc = getServletContext();
		String token = (String) request.getSession().getAttribute("token");
		String path = request.getParameter("path");
		
		if (path == null || path.equals("")) {
			path = "/okm:root";
		}
		
		path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
		sc.setAttribute("doc", OKMDocument.getInstance().getProperties(token, path));
		sc.setAttribute("path", path);
		sc.getRequestDispatcher("/mobi/doc-properties.jsp").forward(request, response);
	}
	
	/**
	 * Search documents
	 */
	private void search(HttpServletRequest request, HttpServletResponse response) throws
			PathNotFoundException, ParseException, RepositoryException, IOException, 
			ServletException {
		log.info("search({}, {})", request, response);
		ServletContext sc = getServletContext();
		String token = (String) request.getSession().getAttribute("token");
		String query = request.getParameter("query");
						
		if (query != null && !query.equals("")) {
			query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
			sc.setAttribute("queryResult", OKMSearch.getInstance().findByContent(token, query));	
		}

		sc.getRequestDispatcher("/mobi/search.jsp").forward(request, response);
	}
	
	/**
	 * Logout
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws 
			AccessDeniedException, RepositoryException, IOException {
		log.info("logout({}, {})", request, response);
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		OKMAuth okmAuth = OKMAuth.getInstance();
		okmAuth.logout(token);
		session.invalidate();
		response.sendRedirect(request.getContextPath());
	}
}
