package com.openkm.index;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.extension.dao.bean.WikiPage;

public class WikiPageIdx {
	private static Logger log = LoggerFactory.getLogger(WikiPageIdx.class);
	
	public static void index(WikiPage item) throws IOException {
		log.info("index({})", item);
		IndexWriter writer = Indexer.getIndexWriter(false);
		Document doc = new Document();
		doc.add(new Field("id", Integer.toString(item.getId()), Field.Store.YES, Field.Index.NO));
		doc.add(new Field("user", item.getUser(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", item.getContent(), Field.Store.NO, Field.Index.ANALYZED));
		writer.addDocument(doc);
	}   
}
