package com.openkm.index;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.extension.dao.bean.WikiPage;

public class WikiPageIdx {
	private static Logger log = LoggerFactory.getLogger(WikiPageIdx.class);
	
	/**
	 * Index item
	 */
	public static void index(WikiPage item) throws IOException {
		log.info("index({})", item);
		IndexWriter writer = Indexer.getIndexWriter();
		Document doc = new Document();
		doc.add(new Field("id", Integer.toString(item.getId()), Field.Store.YES, Field.Index.NO));
		doc.add(new Field("user", item.getUser(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", item.getContent(), Field.Store.NO, Field.Index.ANALYZED));
		writer.addDocument(doc);
	}
	
	/**
	 * Perform search
	 */
	public static TopDocs performSearch(String field, String qs) throws IOException, ParseException {		
		Searcher searcher = Indexer.getIndexSearcher();
		QueryParser parser = new QueryParser(field, Indexer.getAnalyzer());
		Query query = parser.parse(qs);
		TopDocs result = searcher.search(query, Indexer.HITS_PER_PAGE);
		return result;
	}
}
