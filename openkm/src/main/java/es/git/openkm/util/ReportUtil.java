/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;

/**
 * Utilidades para jasper reports
 * 
 * @author jllort
 * 
 */
public class ReportUtil {
	public static Map<String, JasperReport> JasperCharged = new HashMap<String, JasperReport>();
	public static final int REPORT_HTML_OUTPUT = 1;
	public static final int REPORT_PDF_OUTPUT = 2;
	public static final int REPORT_RTF_OUTPUT = 3;

	/**
	 * Generates a report based on a bean collection.
	 */
	@SuppressWarnings("unchecked")
	public static OutputStream generateReport(OutputStream out, String fileReport, 
			Map<String, String> parameters, int outputType,	Collection colleccion) throws Exception {
		if (!JasperCharged.containsKey(fileReport)) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			try {
				JasperReport jasperReport = JasperCompileManager.compileReport(cl.getResourceAsStream(fileReport));
				JasperCharged.put(fileReport, jasperReport);
			} catch (Exception e) {
				throw new Exception("Compiling error: " + e.getMessage());
			}
		}

		try {
			JasperReport jasperReport = (JasperReport) JasperCharged.get(fileReport);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(colleccion));
			export(out, outputType, print);
		} catch (JRException je) {
			throw new JRException("Error generating report", je);
		}

		return out;
	}

	/**
	 * Generates a report based on a jdbc connection.
	 */
	public static OutputStream generateReport(OutputStream out, String fileReport, 
			Map<String, String> parameters, int outputType,	Connection con) throws Exception {
		if (!JasperCharged.containsKey(fileReport)) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			try {
				JasperReport jasperReport = JasperCompileManager.compileReport(cl.getResourceAsStream(fileReport));
				JasperCharged.put(fileReport, jasperReport);
			} catch (Exception e) {
				throw new Exception("Compiling error: " + e.getMessage());
			}
		}

		try {
			JasperReport jasperReport = (JasperReport) JasperCharged.get(fileReport);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
			export(out, outputType, print);
		} catch (JRException je) {
			throw new JRException("Error generating report", je);
		}

		return out;
	}

	/**
	 * Export report 
	 */
	private static void export(OutputStream out, int outputType, JasperPrint print) throws JRException {
		switch (outputType) {
		case REPORT_PDF_OUTPUT:
			JRPdfExporter pdfExp = new JRPdfExporter();
			pdfExp.setParameter(JRExporterParameter.JASPER_PRINT, print);
			pdfExp.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			pdfExp.exportReport();
			break;

		case REPORT_HTML_OUTPUT:
			JRHtmlExporter htmlExp = new JRHtmlExporter();
			htmlExp.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
			htmlExp.setParameter(JRExporterParameter.JASPER_PRINT, print);
			htmlExp.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			htmlExp.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,	Boolean.FALSE);
			htmlExp.exportReport();
			break;
			
		case REPORT_RTF_OUTPUT:
			JRRtfExporter rtfExp = new JRRtfExporter();
			rtfExp.setParameter(JRExporterParameter.JASPER_PRINT, print);
			rtfExp.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			rtfExp.exportReport();
		}
	}
}
