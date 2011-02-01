package com.openkm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.InputMismatchException;

import javax.jcr.Node;

import org.apache.jackrabbit.JcrConstants;
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

/**
 * Default values generate text files with about 39 pages.
 * 
 * @author pavila
 */
public class Benchmark {
	private static Logger log = LoggerFactory.getLogger(Benchmark.class);
	private static final String SEED = Config.HOME_DIR + File.separator + "benchmark.txt";
	private static final int PARAGRAPH = 250;
	private static final int LINE_WIDTH = 80;
	private static final int TOTAL_CHARS = 500;
	private static final int MAX_DOCUMENTS = 12;
	private static final int MAX_FOLDERS = 3;
	private static final int MAX_DEPTH = 3;
	private Generator gen = null;
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
	
	public Benchmark() throws IOException {
		this.maxDocuments = MAX_DOCUMENTS;
		this.maxFolders = MAX_FOLDERS;
		this.maxDepth = MAX_DEPTH;
		init();
	}
	
	public Benchmark(int maxDocuments, int maxFolders, int maxDepth) throws IOException {
		this.maxDocuments = maxDocuments;
		this.maxFolders = maxFolders;
		this.maxDepth = maxDepth;
		init();
	}
	
	private void init() throws IOException {
		FileInputStream fis = new FileInputStream(SEED);
		gen = new Generator(fis);	
		fis.close();
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
	 * Calculates the number of folder created
	 */
	public int calculateFolders() {
		int nodesAtLevel = 1;
		int total = 0;
		
		for (int i=1; i<=maxDepth; i++) {
			nodesAtLevel = nodesAtLevel * maxFolders;
			total += nodesAtLevel;
		}
		
		return total;	
	}
	
	/**
	 * Calculates the number of document created
	 */
	public int calculateDocuments() {
		int nodesAtLevel = 1;
		int total = 0;
		
		for (int i=1; i<=maxDepth + 1; i++) {
			nodesAtLevel = nodesAtLevel * maxDocuments;
			total += nodesAtLevel;
		}
		
		return total;	
	}
	
	/**
	 * Run system calibration
	 * @throws IOException 
	 * @throws InputMismatchException 
	 */
	public long runCalibration() throws InputMismatchException, IOException {
		final int ITERATIONS = 10;
		long total = 0;
		
		for (int i=0; i<ITERATIONS; i++) {
			long calBegin = System.currentTimeMillis();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			gen.generateText(PARAGRAPH, LINE_WIDTH, TOTAL_CHARS, baos);
			baos.close();
			long calEnd = System.currentTimeMillis();
			total = calEnd - calBegin;
		}
		
		log.info("Calibration: {} ms", total / ITERATIONS);
		return total / ITERATIONS;
	}
	
	/**
	 * Run OpenKM text document insertions
	 */
	public void okmPopulate(String token, Folder root, PrintWriter out) throws IOException,
			InputMismatchException, ItemExistsException, PathNotFoundException, UserQuotaExceededException, 
			AccessDeniedException, UnsupportedMimeTypeException, FileSizeExceededException,
			VirusDetectedException, RepositoryException, DatabaseException {
		long begin = System.currentTimeMillis();
		okmPopulateHelper(token, root, out, gen, 0);
		long end = System.currentTimeMillis();
		String elapse = FormatUtil.formatSeconds(end - begin);
		log.info("Total Time: {} - Folders: {}, Documents: {}", new Object[] { elapse, totalFolders, totalDocuments });
	}
	
	/**
	 * Helper
	 */
	private void okmPopulateHelper(String token, Folder root, PrintWriter out, Generator gen, int depth) throws 
			InputMismatchException, IOException, ItemExistsException, PathNotFoundException,
			UserQuotaExceededException,	AccessDeniedException, UnsupportedMimeTypeException, 
			FileSizeExceededException, VirusDetectedException, RepositoryException, DatabaseException {
		log.debug("okmPopulateHelper({}, {}, {}, {})", new Object[] { token, root, gen, depth });
		
		if (depth < maxDepth) {
			for (int i=0; i<maxFolders; i++) {
				long begin = System.currentTimeMillis();
				Folder fld = new Folder();
				fld.setPath(root.getPath() + "/" + System.currentTimeMillis());
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
					doc.setPath(fld.getPath() + "/" + System.currentTimeMillis() + ".txt");
					new DirectDocumentModule().create(token, doc, bais);
					totalDocuments++;
				}
				
				long end = System.currentTimeMillis();
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
				okmPopulateHelper(token, fld, out, gen, depth+1);
			}
		} else {
			log.info("Max depth reached: {}", depth);
			return;
		}
	}
	
	/**
	 * Run JCR text document insertions
	 */
	public void jcrPopulate(String token, Node root, PrintWriter out) throws IOException,
			javax.jcr.ItemExistsException, javax.jcr.PathNotFoundException, 
			javax.jcr.nodetype.NoSuchNodeTypeException, javax.jcr.lock.LockException,
			javax.jcr.version.VersionException, javax.jcr.nodetype.ConstraintViolationException, 
			javax.jcr.RepositoryException, InputMismatchException, IOException {
		long begin = System.currentTimeMillis();
		jcrPopulateHelper(token, root, out, gen, 0);
		long end = System.currentTimeMillis();
		String elapse = FormatUtil.formatSeconds(end - begin);
		log.info("Total Time: {} - Folders: {}, Documents: {}", new Object[] { elapse, totalFolders, totalDocuments });
	}
	
	/**
	 * Helper
	 */
	private void jcrPopulateHelper(String token, Node root, PrintWriter out, Generator gen, int depth) throws 
			javax.jcr.ItemExistsException, javax.jcr.PathNotFoundException, 
			javax.jcr.nodetype.NoSuchNodeTypeException, javax.jcr.lock.LockException,
			javax.jcr.version.VersionException, javax.jcr.nodetype.ConstraintViolationException, 
			javax.jcr.RepositoryException, InputMismatchException, IOException {
		log.debug("jcrPopulateHelper({}, {}, {}, {})", new Object[] { token, root, gen, depth });
		
		if (depth < maxDepth) {
			for (int i=0; i<maxFolders; i++) {
				long begin = System.currentTimeMillis();
				Node fld = root.addNode(Long.toString(System.currentTimeMillis()), JcrConstants.NT_FOLDER);
				fld.addMixin(JcrConstants.MIX_REFERENCEABLE);
				root.save();
				totalFolders++;
				log.info("At depth {}, created folder {}", depth, fld.getPath());
				
				for (int j=0; j<maxDocuments; j++) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					gen.generateText(PARAGRAPH, LINE_WIDTH, TOTAL_CHARS, baos);
					baos.close();
					
					// Repository insertion
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					Node doc = fld.addNode(System.currentTimeMillis() + ".txt", JcrConstants.NT_FILE);
					doc.addMixin(JcrConstants.MIX_REFERENCEABLE);
					Node res = doc.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
					res.setProperty(JcrConstants.JCR_MIMETYPE, "text/plain");
					res.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
					res.setProperty(JcrConstants.JCR_DATA, bais);
					res.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
					fld.save();
					totalDocuments++;
				}
				
				long end = System.currentTimeMillis();
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
				jcrPopulateHelper(token, fld, out, gen, depth+1);
			}
		} else {
			log.info("Max depth reached: {}", depth);
			return;
		}
	}
}
