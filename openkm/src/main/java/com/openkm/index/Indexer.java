package com.openkm.index;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.LockObtainFailedException;

public class Indexer {
	private static Analyzer analyzer = null;
	private static IndexWriter indexWriter = null;
	private static IndexSearcher indexSearcher = null;
	private static final String INDEX_PATH = "index";
	public static final int HITS_PER_PAGE = 32;
	
	/**
	 * Instance analyzer
	 */
	public static synchronized Analyzer getAnalyzer() {
		if (analyzer == null) {
			analyzer = new StandardAnalyzer();
		}
		
		return analyzer;
	}
	
	/**
	 * Obtain index writer 
	 */
	public static synchronized IndexWriter getIndexWriter() throws CorruptIndexException,
			LockObtainFailedException, IOException  {
		if (indexWriter == null) {
			indexWriter = new IndexWriter(INDEX_PATH, getAnalyzer(), true, MaxFieldLength.LIMITED);
		}
		
		return indexWriter;
	}
	
	/**
	 * Close index writer
	 */
	public static synchronized void closeIndexWriter() throws CorruptIndexException, IOException  {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}
	
	/**
	 * Obtain index searcher
	 */
	public static synchronized IndexSearcher getIndexSearcher() throws CorruptIndexException, IOException {
		if (indexSearcher == null) {
			indexSearcher = new IndexSearcher(INDEX_PATH);
		}
		
		return indexSearcher;
	}
	
	/**
	 * Close index searcher
	 */
	public static synchronized void closeIndexSearcher() throws CorruptIndexException, IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
		}
	}
}
