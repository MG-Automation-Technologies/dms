package com.openkm.frontend.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMNotification;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.widget.upload.FancyFileUpload;
import com.openkm.util.FileUtils;
import com.openkm.util.impexp.ImpExpStats;
import com.openkm.util.impexp.RepositoryImporter;
import com.openkm.util.impexp.TextInfoDecorator;

import de.schlichtherle.io.File;
import de.schlichtherle.io.FileOutputStream;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMFileUploadServlet" display-name="Name for OKMFileUploadServlet"
 *              description="Description for OKMFileUploadServlet"
 * @web.servlet-mapping url-pattern="/OKMFileUploadServlet"
 * @web.servlet-init-param name="A parameter" value="A value"
 * 
 * @author pavila
 *
 */
public class OKMFileUploadServlet extends OKMHttpServlet {
	private static Logger log = LoggerFactory.getLogger(OKMFileUploadServlet.class);
	private static final long serialVersionUID = 1L;
	public static final int INSERT = 0;
	public static final int UPDATE = 1;
	private final String returnOKMessage = "OKM_OK";
	public static final String FILE_UPLOAD_STATUS = "file_upload_status";
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws 
			ServletException, IOException {
		log.info("doPost({}, {})", request, response);
		String fileName = null;
		InputStream is = null;
		String path = null;
		int action = 0;
		boolean notify = false;
		boolean importZip = false;
		String users = null;
		String message = null;
		String comment = null;
		String folder = null;
		PrintWriter out = null;
		String uploadedDocPath = null;

		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			response.setContentType("text/plain");
			out = response.getWriter();	
			log.info("isMultipart: {}", isMultipart);
			
			// Create a factory for disk-based file items
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(factory);
				FileUploadListener listener = new FileUploadListener();
				
				// Saving listener to session
				request.getSession().setAttribute(FILE_UPLOAD_STATUS, listener);
				upload.setHeaderEncoding("UTF-8");
				
				// upload servlet allows to set upload listener
		        upload.setProgressListener(listener);
				List<FileItem> items = upload.parseRequest(request);

				// Parse the request and get all parameters and the uploaded file
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
									
					if (item.isFormField()) { 
						if (item.getFieldName().equals("path")) { path = item.getString("UTF-8"); }
						if (item.getFieldName().equals("action")) { action = Integer.parseInt(item.getString("UTF-8")); }
						if (item.getFieldName().equals("users")) { users = item.getString("UTF-8"); }
						if (item.getFieldName().equals("notify")) { notify = true; } 
						if (item.getFieldName().equals("importZip")) { importZip = true; }
						if (item.getFieldName().equals("message")) { message = item.getString("UTF-8"); }
						if (item.getFieldName().equals("comment")) { comment = item.getString("UTF-8"); }
						if (item.getFieldName().equals("folder")) { folder = item.getString("UTF-8"); }
					} else {
						fileName = item.getName();
						is = item.getInputStream();
					}
				}

				// Now, we have read all parameters and the uploaded file  
				if (action == FancyFileUpload.ACTION_INSERT) {
					if (fileName != null && !fileName.equals("")) {
						if (importZip && FilenameUtils.getExtension(fileName).equalsIgnoreCase("zip")) {
							log.info("Import zip file '{}' into '{}'", fileName, path);
							String erroMsg = importZip(path, is);
							log.warn("erroMsg: {}", erroMsg);
							
							if (erroMsg == null) {
								out.print(returnOKMessage);
							} else {
								out.print(erroMsg);
							}
						} else {
							fileName = FilenameUtils.getName(fileName);
							log.info("Upload file '{}' into '{}'", fileName, path);
							Document doc = new Document();
							doc.setPath(path+"/"+fileName);
							OKMDocument.getInstance().create(doc, is);
							uploadedDocPath = doc.getPath();
							out.print(returnOKMessage + " path["+uploadedDocPath+"]path"); // Return the path of the inserted document in response
						}
					}
				} else if (action == FancyFileUpload.ACTION_UPDATE) {
					log.info("File updated: {}", path);
					OKMDocument document = OKMDocument.getInstance();
					document.setContent(path, is);
					document.checkin(path, comment);
					uploadedDocPath = path;
					out.print(returnOKMessage + " path["+uploadedDocPath+"]path"); // Return the path of the inserted document in response
				} else if (action == FancyFileUpload.ACTION_FOLDER) {
					log.info("Folder create: {}", path);
					Folder fld = new Folder();
					fld.setPath(path+"/"+folder);
					OKMFolder.getInstance().create(fld);
					out.print(returnOKMessage);
				}
				
				listener.setUploadFinish(true); // Mark uploading operation has finished

				// If the document have been added to the repository, perform user notification
				if ((action == FancyFileUpload.ACTION_INSERT || action == FancyFileUpload.ACTION_UPDATE) & notify) {
					List<String> col = Arrays.asList(users.split(","));
					OKMNotification.getInstance().notify(uploadedDocPath, col, message);
				}
			}
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_AccessDenied));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_PathNotFound));
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_ItemExists));
		} catch (UnsupportedMimeTypeException e) {
			log.warn(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_UnsupportedMimeType));
		} catch (FileSizeExceededException e) {
			log.warn(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_FileSizeExceeded));
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_Repository));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_DatabaseException));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			out.print(ErrorCode.get(ErrorCode.ORIGIN_OKMUploadService, ErrorCode.CAUSE_IOException));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("Exception: "+e.toString());
		} finally {
			IOUtils.closeQuietly(is);
			out.flush();
			IOUtils.closeQuietly(out);
			System.gc();
		}
	}
	
	/**
	 * Import zipped documents
	 * 
	 * @param token User session token.
	 * @param path Where import into the repository.
	 * @param zip The zip file to import.
	 */
	private String importZip(String path, InputStream is) throws 
			PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException, IOException, DatabaseException {
		log.debug("importZip({}, {})", path, is);
        java.io.File tmpIn = null;
        java.io.File tmpOut = null;
        String errorMsg = null;
       						
		try {
			// Create temporal
			tmpIn = File.createTempFile("okm", ".zip");
	        tmpOut = FileUtils.createTempDir();
	        FileOutputStream fos = new FileOutputStream(tmpIn);
			IOUtils.copy(is, fos);
			fos.close();
			
			// Unzip files
			new File(tmpIn).archiveCopyAllTo(tmpOut);
			
			// Import files
			StringWriter out = new StringWriter();
			ImpExpStats stats = RepositoryImporter.importDocuments(tmpOut, path, out, new TextInfoDecorator(tmpOut));
			if (!stats.isOk()) {
				errorMsg = out.toString();
			}
			out.close();
		} catch (IOException e) {
			log.error("Error importing zip", e);
			throw e;
		} finally {
			if (tmpIn != null) {
				org.apache.commons.io.FileUtils.deleteQuietly(tmpIn);
			}

			if (tmpOut != null) {
				org.apache.commons.io.FileUtils.deleteQuietly(tmpOut);
			}

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("Error closing zip input stream", e);
					throw e;
				}
			}
		}
		
		log.debug("importZip: {}", errorMsg);
		return errorMsg;
	}
}
