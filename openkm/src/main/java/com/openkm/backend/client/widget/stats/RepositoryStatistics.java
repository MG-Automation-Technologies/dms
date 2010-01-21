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

package com.openkm.backend.client.widget.stats;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * RepositoryStatistics
 * 
 * @author jllort
 *
 */
public class RepositoryStatistics extends Composite {
	private FlexTable table;
	
	/**
	 * RepositoryStatistics
	 */
	public RepositoryStatistics() {
		table = new FlexTable();
		table.setHTML(0, 0, "Tamaño");
		table.setHTML(1, 0, "Ficheros");
		table.setHTML(2, 0, "Carpetas");
		table.setHTML(3, 0, "Grupos de propiedades");
		table.setHTML(4, 0, "Ficheros indexados");
		table.setHTML(5, 0, "Tamaño personales");
		table.setHTML(6, 0, "Ficheros personales");
		table.setHTML(7, 0, "Carpetas personales");
		table.setHTML(8, 0, "Tamaño plantillas");
		table.setHTML(9, 0, "Ficheros plantillas");
		table.setHTML(10, 0, "Carpetas plantillas");
		table.setHTML(11, 0, "Tamaño papeleras");
		table.setHTML(12, 0, "Ficheros papeleras");
		table.setHTML(13, 0, "Carpetas papeleras");
		table.setHTML(14, 0, "Ficheros auditados");
		table.setHTML(15, 0, "Carpetas auditadas");
		table.setHTML(16, 0, "Ficheros editados");
		table.setHTML(17, 0, "Ficheros bloqueados");
		table.setHTML(18, 0, "Version");
		table.setHTML(19, 0, "UUID");
		
		initWidget(table);
	}
}