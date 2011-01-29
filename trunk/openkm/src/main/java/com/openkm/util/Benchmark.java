package com.openkm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.UUID;

import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.direct.DirectDocumentModule;
import com.openkm.module.direct.DirectFolderModule;
import com.openkm.util.markov.Generator;

public class Benchmark {
	private static final int PARAGRAPH = 250;
	private static final int LINE_WIDTH = 80;
	private static final int TOTAL_CHARS = 275000;
	private static final int DOCUMENTS = 256;
	private static final int FOLDERS = 64;
	
	/**
	 * Main method for testing purposes
	 */
	public static void main(String[] args) {

	}
	
	/**
	 * Run text document insertions
	 */
	public void populateText(String token, Folder root) {
		try {
			FileInputStream fis = new FileInputStream("lorem.txt");
			Generator gen = new Generator(fis);	
			fis.close();
			
			// Repository insertion
			for (int i=0; i<FOLDERS; i++) {
				populateTextFolder(token, root, gen);
			}
		} catch (InputMismatchException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} catch (ItemExistsException e) {
			System.err.println(e);
		} catch (PathNotFoundException e) {
			System.err.println(e);
		} catch (AccessDeniedException e) {
			System.err.println(e);
		} catch (RepositoryException e) {
			System.err.println(e);
		} catch (DatabaseException e) {
			System.err.println(e);
		} catch (UserQuotaExceededException e) {
			System.err.println(e);
		} catch (UnsupportedMimeTypeException e) {
			System.err.println(e);
		} catch (FileSizeExceededException e) {
			System.err.println(e);
		} catch (VirusDetectedException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Helper
	 */
	private void populateTextFolder(String token, Folder root, Generator gen) throws 
			InputMismatchException, IOException, ItemExistsException, PathNotFoundException,
			UserQuotaExceededException,	AccessDeniedException, UnsupportedMimeTypeException, 
			FileSizeExceededException, VirusDetectedException, RepositoryException, DatabaseException {
		Folder fld = new Folder();
		fld.setPath(root.getPath() + "/" + UUID.randomUUID().toString());
		fld = new DirectFolderModule().create(token, fld);
		
		for (int i=0; i<DOCUMENTS; i++) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			gen.generateText(PARAGRAPH, LINE_WIDTH, TOTAL_CHARS, baos);
			baos.close();
			
			// Repository insertion
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			Document doc = new Document();
			doc.setMimeType("text/plain");
			doc.setPath(fld.getPath() + "/" + UUID.randomUUID().toString() + ".txt");
			new DirectDocumentModule().create(token, doc, bais);
		}
	}
	
	/**
	 * Run pdf document insertions  
	 * @throws  
	 */
	public void populatePdf(String token, Folder root) {
		try {
			FileInputStream fis = new FileInputStream("lorem.txt");
			Generator gen = new Generator(fis);	
			fis.close();
			
			// Repository insertion
			for (int i=0; i<FOLDERS; i++) {
				populatePdfFolder(token, root, gen);
			}
		} catch (InputMismatchException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} catch (ItemExistsException e) {
			System.err.println(e);
		} catch (PathNotFoundException e) {
			System.err.println(e);
		} catch (AccessDeniedException e) {
			System.err.println(e);
		} catch (RepositoryException e) {
			System.err.println(e);
		} catch (DatabaseException e) {
			System.err.println(e);
		} catch (UserQuotaExceededException e) {
			System.err.println(e);
		} catch (UnsupportedMimeTypeException e) {
			System.err.println(e);
		} catch (FileSizeExceededException e) {
			System.err.println(e);
		} catch (VirusDetectedException e) {
			System.err.println(e);
		} catch (DocumentException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Helper
	 */
	private void populatePdfFolder(String token, Folder root, Generator gen) throws 
			InputMismatchException, IOException, ItemExistsException, PathNotFoundException,
			UserQuotaExceededException,	AccessDeniedException, UnsupportedMimeTypeException, 
			FileSizeExceededException, VirusDetectedException, RepositoryException, DatabaseException, 
			DocumentException {
		Folder fld = new Folder();
		fld.setPath(root.getPath() + "/" + UUID.randomUUID().toString());
		fld = new DirectFolderModule().create(token, fld);
		
		for (int i=0; i<DOCUMENTS; i++) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			com.lowagie.text.Document pdf = new com.lowagie.text.Document(PageSize.A4, 25, 25, 25, 25);
			PdfWriter.getInstance(pdf, baos);
			pdf.open();
			
			for (int j=0; j<PARAGRAPH; j++) {
				ByteArrayOutputStream tmp = new ByteArrayOutputStream();
				gen.generateParagraph(LINE_WIDTH, TOTAL_CHARS, tmp);
				pdf.add(new Paragraph(tmp.toString()));
				tmp.close();
			}
			
			pdf.close();
			baos.close();
			
			// Repository insertion
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			Document doc = new Document();
			doc.setMimeType("application/pdf");
			doc.setPath(fld.getPath() + "/" + UUID.randomUUID().toString() + ".pdf");
			new DirectDocumentModule().create(token, doc, bais);
		}
	}
}
