package com.openkm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.InputMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final int MAX_DOCUMENTS = 6;
	private static final int MAX_FOLDERS = 3;
	private static final int MAX_DEPTH = 3;
	private int maxDocuments = 0;
	private int maxFolders = 0;
	private int maxDepth = 0;
	private int totalFolders = 0;
	private int totalDocuments = 0;
	private int row = 0;
	
	/**
	 * Main method for testing purposes
	 */
	public static void main(String[] args) {
	}
	
	public Benchmark() {
		this.maxDocuments = MAX_DOCUMENTS;
		this.maxFolders = MAX_FOLDERS;
		this.maxDepth = MAX_DEPTH;
	}
	
	public Benchmark(int maxDocuments, int maxFolders, int maxDepth) {
		this.maxDocuments = maxDocuments;
		this.maxFolders = maxFolders;
		this.maxDepth = maxDepth;
	}
	
	public int getMaxDocuments() {
		return maxDocuments;
	}
	
	public int getMaxFolders() {
		return maxFolders;
	}
	
	public int getMaxDepth() {
		return maxDepth;
	}
	
	public int getTotalFolders() {
		return totalFolders;
	}
	
	public int getTotalDocuments() {
		return totalDocuments;
	}
	
	/**
	 * Run text document insertions
	 */
	public void populateText(String token, Folder root, PrintWriter out) throws IOException,
			InputMismatchException, ItemExistsException, PathNotFoundException, UserQuotaExceededException, 
			AccessDeniedException, UnsupportedMimeTypeException, FileSizeExceededException,
			VirusDetectedException, RepositoryException, DatabaseException {
		FileInputStream fis = new FileInputStream(SEED);
		Generator gen = new Generator(fis);	
		fis.close();
		
		// Repository insertion
		long begin = Calendar.getInstance().getTimeInMillis();
		populateTextHelper(token, root, out, gen, 0);
		long end = Calendar.getInstance().getTimeInMillis();
		String elapse = FormatUtil.formatSeconds(end - begin);
		log.info("Total Time: {} - Folders: {}, Documents: {}", new Object[] { elapse, totalFolders, totalDocuments });
	}
	
	/**
	 * Helper
	 */
	private void populateTextHelper(String token, Folder root, PrintWriter out, Generator gen, int depth) throws 
			InputMismatchException, IOException, ItemExistsException, PathNotFoundException,
			UserQuotaExceededException,	AccessDeniedException, UnsupportedMimeTypeException, 
			FileSizeExceededException, VirusDetectedException, RepositoryException, DatabaseException {
		log.debug("populateTextHelper({}, {}, {}, {})", new Object[] { token, root, gen, depth });
		
		if (depth < maxDepth) {
			for (int i=0; i<maxFolders; i++) {
				long begin = Calendar.getInstance().getTimeInMillis();
				Folder fld = new Folder();
				fld.setPath(root.getPath() + "/" + Calendar.getInstance().getTimeInMillis());
				fld = new DirectFolderModule().create(token, fld);
				totalFolders++;
				log.info("At depth {}, created folder {}", depth, fld.getPath());
				
				for (int j=0; j<maxDocuments; j++) {
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
				String elapse = FormatUtil.formatSeconds(end - begin);
				log.info("Partial Time: {} - Folders: {}, Documents: {}", new Object[] { elapse, totalFolders, totalDocuments });
				out.print("<tr class=\""+(row++%2==0?"even":"odd")+"\">");
				out.print("<td>"+FormatUtil.formatDate(Calendar.getInstance())+"</td>");
				out.print("<td>"+elapse+"</td>");
				out.print("<td>"+(end - begin)+"</td>");
				out.print("<td>"+totalFolders+"</td>");
				out.print("<td>"+totalDocuments+"</td>");
				out.println("</tr>");
				out.flush();
				
				// Go depth
				populateTextHelper(token, fld, out, gen, depth+1);
			}
		} else {
			log.info("Max depth reached: {}", depth);
			return;
		}
	}
}
