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
 * @version $Id: LuceneSearchTestCase.java,v 1.1 2007/07/18 17:54:28 rafaelsteil Exp $
 */
public class LuceneSearchTestCase extends TestCase
{
	private Directory directory;

	public void testAddPost() throws Exception
	{
		Post p = new Post();
		p.setTopicId(1);
		p.setId(1);
		p.setSubject("post 1");
		p.setText("some post contents");
		
		this.write(this.createDocument(p));
		
		IndexSearcher search = new IndexSearcher(this.directory);
		Hits hits = search.search(new TermQuery(new Term("title", "post")));
		
		Assert.assertEquals(1, hits.length());
		Assert.assertEquals("post 1", hits.doc(0).get("title"));
		Assert.assertEquals(1, Integer.parseInt(hits.doc(0).get("id")));
		Assert.assertEquals("some post contents", hits.doc(0).get("text"));
	}
	
	private Document createDocument(Post p)
	{
		Document d = new Document();
		
		d.add(new Field("id", String.valueOf(p.getId()), Store.YES, Index.UN_TOKENIZED));
		d.add(new Field("topic", String.valueOf(p.getTopicId()), Store.YES, Index.UN_TOKENIZED));
		d.add(new Field("forum", String.valueOf(p.getForumId()), Store.YES, Index.UN_TOKENIZED));
		d.add(new Field("title", p.getSubject(), Store.YES, Index.TOKENIZED));
		d.add(new Field("text", p.getText(), Store.YES, Index.TOKENIZED));
		
		return d;
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
