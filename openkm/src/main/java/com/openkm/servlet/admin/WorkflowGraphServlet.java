package com.openkm.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMWorkflow;
import com.openkm.core.RepositoryException;

/**
 * Workflow graphic servlet
 */
public class WorkflowGraphServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WorkflowGraphServlet.class);
		
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String token = (String) req.getSession().getAttribute("token");
		long id = Long.parseLong(req.getParameter("id"));
		String node = req.getParameter("node");
		ServletOutputStream sos = resp.getOutputStream();

		if (node != null && !node.equals("")) {
			node = new String(node.getBytes("ISO-8859-1"), "UTF-8");
		}
		
		try {
			// Get image
			byte[] data = OKMWorkflow.getInstance().getProcessDefinitionImage(token, id, node);
						
			if (data != null) {
				// Disable browser cache
				resp.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
				resp.setHeader("Cache-Control", "max-age=0, must-revalidate");
				resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
				resp.setHeader("Pragma", "no-cache");
				
				// Send data
				resp.setContentType("image/jpeg");
				resp.setContentLength(data.length);
				sos.write(data);
			} else {
				resp.setContentType("text/plain");
				sos.write("Null process definition image".getBytes());
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			sos.flush();
			sos.close();
		}
	}
}
