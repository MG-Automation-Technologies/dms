package es.git.openkm.backend.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMWorkflow;
import es.git.openkm.backend.client.config.ErrorCode;
import es.git.openkm.core.Config;
import es.git.openkm.core.RepositoryException;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMWorkflowUploadServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMWorkflowUploadServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMWorkflowUploadServletAdmin extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(OKMWorkflowUploadServletAdmin.class);
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = (String) request.getSession().getAttribute("token");
		String fileName = null;
		byte[] content = null;
		PrintWriter out = null;

		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			out = response.getWriter();	

			// Create a factory for disk-based file items
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				List<FileItem> items = upload.parseRequest(request);

				// Parse the request and get all parameters and the uploaded file
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
									
					if (!item.isFormField()) {
						fileName = item.getName();
						content = item.get();
					}
				}

				if (fileName != null && !fileName.equals("")) {
					fileName = FilenameUtils.getName(fileName);
					log.info("Upload file: " + fileName);
					InputStream is = new ByteArrayInputStream(content);
					ZipInputStream zis = new ZipInputStream(is);
					OKMWorkflow.getInstance().registerProcessDefinition(token, zis);
					zis.close();
					is.close();
				}
				
				response.sendRedirect("/OpenKM"+Config.INSTALL+"/admin/wf_processes.jsp");
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowUploadServiceAdmin, ErrorCode.CAUSE_Repository));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowUploadServiceAdmin, ErrorCode.CAUSE_IOException));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("Exception: "+e.toString());
		} finally {
			out.flush();
			out.close();
		}
	}
}
