package com.openkm.index;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

public class Indexer {
	private static IndexWriter indexWriter = null;
	private static final int HITS_PER_PAGE = 32;
	/**
	 * Create or obtain index writer 
	 */
	public static IndexWriter getIndexWriter(boolean create) throws IOException {
		if (indexWriter == null) {
			indexWriter = new IndexWriter("index", new StandardAnalyzer(), create, MaxFieldLength.LIMITED);
		}
		
		return indexWriter;
	}
	
	/**
	 * Close index writer
	 */
	public static void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}
	
	/**
	 * Perform search
	 */
	public TopDocs performSearch(String queryString) throws IOException, ParseException {
		Analyzer analyzer = new StandardAnalyzer();
		IndexSearcher is = new IndexSearcher("index");
		QueryParser parser = new QueryParser("content", analyzer);
		Query query = parser.parse(queryString);
		TopDocs result = is.search(query, HITS_PER_PAGE);
		return result;
	}
}
