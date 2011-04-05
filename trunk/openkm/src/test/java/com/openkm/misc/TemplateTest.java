package com.openkm.misc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.jooreports.templates.DocumentTemplateException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.openkm.util.OOUtils;
import com.openkm.util.PDFUtils;
import com.openkm.util.TemplateUtils;

import freemarker.template.TemplateException;

/**
 * Test template engines
 */
public class TemplateTest extends TestCase {
	private static Logger log = LoggerFactory.getLogger(TemplateTest.class);
	private static final String BASE_DIR = "src/test/resources";
	
	public TemplateTest(String name) {
		super(name);
	}

	public static void main(String[] args) throws Exception {
		TemplateTest test = new TemplateTest("main");
		test.setUp();
		test.testPdf();
		test.testOpenOffice();
		test.testHtml();
		test.tearDown();
	}

	@Override
	protected void setUp() throws Exception {
		log.debug("setUp()");
	}

	@Override
	protected void tearDown() throws Exception {
		log.debug("tearDown()");
	}

	public void testPdf() throws IOException, DocumentException {
		log.debug("testPdf()");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("okp:form.name", "el nombre");
		model.put("okp:form.bird_date", "el apellido");
		model.put("okp:form.language", "el lenguaje");
		InputStream input = new FileInputStream(BASE_DIR + "/templates/form.pdf");
		OutputStream output = new FileOutputStream(BASE_DIR + "/templates/form_out.pdf");
		PDFUtils.fillForm(input, model, output);
		input.close();
		output.close();
	}

	public void testOpenOffice() throws IOException, DocumentTemplateException {
		log.debug("testOpenOffice()");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("okp:form.name", "el nombre");
		model.put("okp:form.bird_date", "el apellido");
		model.put("okp:form.language", "el lenguaje");
		InputStream input = new FileInputStream(BASE_DIR + "/templates/form.odt");
		OutputStream output = new FileOutputStream(BASE_DIR + "/templates/form_out.odt");
		OOUtils.fillTemplate(input, model, output);
		input.close();
		output.close();
	}
	
	public void testHtml() throws IOException, TemplateException {
		log.debug("testHtml()");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("okp_form_name", "el nombre");
		model.put("okp_form_bird_date", "el apellido");
		model.put("okp_form_language", "el lenguaje");
		InputStream input = new FileInputStream(BASE_DIR + "/templates/form.html");
		OutputStream output = new FileOutputStream(BASE_DIR + "/templates/form_out.html");
		String in = IOUtils.toString(input);
		String out = TemplateUtils.replace("sample", in, model);
		IOUtils.write(out, output);
		input.close();
		output.close();
	}
}
