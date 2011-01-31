package com.openkm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.util.Calendar;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
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
	private static Logger log = LoggerFactory.getLogger(Benchmark.class);
	private static final String SEED = Config.HOME_DIR + File.separator + "benchmark.txt";
	private static final int PARAGRAPH = 250;
	private static final int LINE_WIDTH = 80;
	private static final int TOTAL_CHARS = 27500;
	private static final int DOCUMENTS = 6;
	private static final int FOLDERS = 3;
	private static final int DEPTH = 3;
	private int totalFolders = 0;
	private int totalDocuments = 0;
	
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
			FileInputStream fis = new FileInputStream(SEED);
			Generator gen = new Generator(fis);	
			fis.close();
			
			// Repository insertion
			long begin = Calendar.getInstance().getTimeInMillis();
			populateTextHelper(token, root, gen, 0);
			long end = Calendar.getInstance().getTimeInMillis();
			log.info("Total Time: {} - Folders: {}, Documents: {}", new Object[] { 
					FormatUtil.formatSeconds(end - begin), totalFolders, totalDocuments });
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
	private void populateTextHelper(String token, Folder root, Generator gen, int depth) throws 
			InputMismatchException, IOException, ItemExistsException, PathNotFoundException,
			UserQuotaExceededException,	AccessDeniedException, UnsupportedMimeTypeException, 
			FileSizeExceededException, VirusDetectedException, RepositoryException, DatabaseException {
		log.debug("populateTextHelper({}, {}, {}, {})", new Object[] { token, root, gen, depth });
		
		if (depth < DEPTH) {
			for (int i=0; i<FOLDERS; i++) {
				long begin = Calendar.getInstance().getTimeInMillis();
				Folder fld = new Folder();
				fld.setPath(root.getPath() + "/" + Calendar.getInstance().getTimeInMillis());
				fld = new DirectFolderModule().create(token, fld);
				totalFolders++;
				log.info("At depth {}, created folder {}", depth, fld.getPath());
				
				for (int j=0; j<DOCUMENTS; j++) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					gen.generateText(PARAGRAPH, LINE_WIDTH, TOTAL_CHARS, baos);
					baos.close();
					
					// Repository insertion
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					Document doc = new Document();
					doc.setMimeType("text/plain");
					doc.setPath(fld.getPath() + "/" + Calendar.getInstance().getTimeInMillis() + ".txt");
					new DirectDocumentModule().create(token, doc, bais);
					totalDocuments++;
				}
				
				long end = Calendar.getInstance().getTimeInMillis();
				log.info("Partial Time: {} - Folders: {}, Documents: {}", new Object[] {
						FormatUtil.formatSeconds(end - begin), totalFolders, totalDocuments });
				
				// Go depth
				populateTextHelper(token, fld, gen, depth+1);
			}
		} else {
			log.info("Max depth reached: {}", depth);
			return;
		}
	}
}
