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

package com.openkm.webdav.resource;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradmcevoy.common.Path;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;

public class ResourceUtils {
	private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);
	
	/**
	 * Create HTML content.
	 */
	public static void createContent(OutputStream out, Path path, List<Folder> fldChilds, List<Document> docChilds) {
		log.info("createContent({}, {}, {}, {})", new Object[] { out, path, fldChilds, docChilds });
		PrintWriter pw = new PrintWriter(out);
		pw.println("<html>");
		pw.println("<header>");
		pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		pw.println("<link rel=\"Shortcut icon\" href=\"/" + path.getFirst() + "/favicon.ico\" />");
		pw.println("<link rel=\"stylesheet\" href=\"/" + path.getFirst() + "/css/style.css\" type=\"text/css\" />");
		pw.println("<title>OpenKM WebDAV</title>");
		pw.println("</header>");
		pw.println("<body>");
		pw.println("<h1>OpenKM WebDAV</h1>");
		pw.println("<table>");
		
		if (!path.getStripFirst().getStripFirst().isRoot()) {
			String url = path.getParent().toPath();
			pw.print("<tr>");
			pw.print("<td><img src='/" + path.getFirst() + "/img/folder.png'/></td>");
			pw.print("<td><a href='" + url + "'>..</a></td>");
			pw.println("<tr>");
		}
		
		if (fldChilds != null) {
			for (Folder fld : fldChilds) {
				Path fldPath = Path.path(fld.getPath());
				String url = path.toPath().concat("/").concat(fldPath.getName());
				pw.print("<tr>");
				pw.print("<td><img src='/" + path.getFirst() + "/img/folder.png'/></td>");
				pw.print("<td><a href='" + url + "'>" + fldPath.getName() + "</a></td>");
				pw.println("<tr>");
			}
		}
		
		if (docChilds != null) {
			for (Document doc : docChilds) {
				Path docPath = Path.path(doc.getPath());
				String url = path.toPath().concat("/").concat(docPath.getName());
				pw.print("<tr>");
				pw.print("<td><img src='/" + path.getFirst() + "/mime/" + doc.getMimeType() + "'/></td>");
				pw.print("<td><a href='" + url + "'>" + docPath.getName() + "</a></td>");
				pw.println("<tr>");
			}
		}
		
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
		pw.flush();
		pw.close();
	}
}
