/*
 * Created on 18/07/2007 14:03:15
 */
package net.jforum.search;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.jforum.entities.Post;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearchTestCase.java,v 1.1 2007/07/18 20:19:34 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private static class ObjectMother
	{
		public static Document createPost(String subject)
		{
			Post p = new Post();
			p.setSubject(subject);
			
			Document doc = new Document();
			doc.add(new Field("subject", subject, Store.YES, Index.TOKENIZED));
			
			return doc;
		}
	}
	
	private Directory directory;
	
	public void testSearchByTitleExpectTwoResults() throws Exception
	{
		this.write(ObjectMother.createPost("java"));
		this.write(ObjectMother.createPost("javalandia"));
		this.write(ObjectMother.createPost("rails"));
		
		Hits hits = this.search("java");
		
		Assert.assertEquals(2, hits.length());
		Assert.assertEquals("java", hits.doc(0).get("title"));
		Assert.assertEquals("javalandia", hits.doc(1).get("title"));
	}
	
	private Hits search(String term) throws Exception
	{
		IndexSearcher search = new IndexSearcher(this.directory);
		return search.search(new TermQuery(new Term("subject", term)));
	}

	private void write(Document doc) throws Exception
	{
		IndexWriter writer = new IndexWriter(this.directory, this.newAnalyzer());
		writer.addDocument(doc);
		writer.optimize();
		writer.flush();
		writer.close();
	}
	
	private Analyzer newAnalyzer () 
	{
		return new StandardAnalyzer();
	}
	
	protected void setUp() throws Exception
	{
		this.directory = new RAMDirectory();
	}
	
	protected void tearDown() throws Exception
	{
		this.directory.close();
	}
}
