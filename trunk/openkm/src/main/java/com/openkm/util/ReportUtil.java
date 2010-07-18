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

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Utilidades para jasper reports
 * 
 * @author jllort
 * 
 */
public class ReportUtil {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ReportUtil.class);
	public static Map<String, JasperReport> JasperCharged = new HashMap<String, JasperReport>();
	public static final int HTML_OUTPUT = 1;
	public static final int PDF_OUTPUT = 2;
	public static final int RTF_OUTPUT = 3;

	/**
	 * Generates a report based on a map collection (from file)
	 */
	public static OutputStream generateReport(OutputStream out, String fileReport, 
			Map<String, String> parameters, int outputType,
			Collection<Map<String, String>> list) throws Exception {
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
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, 
					new JRMapCollectionDataSource(list));
			export(out, outputType, print);
		} catch (JRException je) {
			throw new JRException("Error generating report", je);
		}

		return out;
	}
	
	/**
	 * Generates a report based on a map collection (from stream)
	 */
	public static OutputStream generateReport(OutputStream out, InputStream report, 
			Map<String, String> parameters, int outputType, 
			Collection<Map<String, String>> list) throws JRException {
		JasperReport jasperReport = JasperCompileManager.compileReport(report);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, 
				new JRMapCollectionDataSource(list));
		export(out, outputType, print);
		return out;
	}
	
	/**
	 * Generates a report based on a map collection (from stream)
	 */
	public static OutputStream generateReport(OutputStream out, InputStream report, 
			Map<String, String> parameters, int outputType) throws JRException, EvalError {
		JasperReport jasperReport = JasperCompileManager.compileReport(report);
		JRQuery query = jasperReport.getQuery();
		
		if (query != null) {
			Interpreter bsh = new Interpreter(null, System.out, System.err, false);
			@SuppressWarnings("rawtypes")
			Collection list = (Collection) bsh.eval(query.getText());
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, 
					new JRMapCollectionDataSource(list));
			export(out, outputType, print);
		} else {
			throw new JRException("Null report query string");
		}
		
		return out;
	}


	/**
	 * Generates a report based on a JDBC connection (from file)
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
	 * Generates a report based on a JDBC connection (from stream)
	 */
	public static OutputStream generateReport(OutputStream out, InputStream report, 
			Map<String, String> parameters, int outputType,	Connection con) throws JRException {
		JasperReport jasperReport = JasperCompileManager.compileReport(report);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
		export(out, outputType, print);
		return out;
	}
	
	/**
	 * Export report 
	 */
	private static void export(OutputStream out, int outputType, JasperPrint print) throws JRException {
		switch (outputType) {
		case PDF_OUTPUT:
			JRPdfExporter pdfExp = new JRPdfExporter();
			pdfExp.setParameter(JRExporterParameter.JASPER_PRINT, print);
			pdfExp.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			pdfExp.exportReport();
			break;

		case HTML_OUTPUT:
			JRHtmlExporter htmlExp = new JRHtmlExporter();
			htmlExp.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
			htmlExp.setParameter(JRExporterParameter.JASPER_PRINT, print);
			htmlExp.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			htmlExp.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,	Boolean.FALSE);
			htmlExp.exportReport();
			break;
			
		case RTF_OUTPUT:
			JRRtfExporter rtfExp = new JRRtfExporter();
			rtfExp.setParameter(JRExporterParameter.JASPER_PRINT, print);
			rtfExp.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			rtfExp.exportReport();
		}
	}
}
