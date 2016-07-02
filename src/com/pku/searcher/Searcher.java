package com.pku.searcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.QueryBuilder;

public class Searcher {
	public Searcher(){}
	public void search(String path,String feild) {
		IndexSearcher indexSearcher=null;
		IndexReader indexReader=null;
		try {
			Directory directory=FSDirectory.open(Paths.get(path));
			indexReader=DirectoryReader.open(directory);
			indexSearcher=new IndexSearcher(indexReader);
			Analyzer analyzer=new StandardAnalyzer();
			//BufferedReader bufferedReader;
			TopDocs hits = null;
			if(feild!=null){
				//Term t=new Term("content", "hello");
				QueryParser queryParser=new QueryParser("content",analyzer);
			//	Query term=new TermQuery(t);
				Query query=queryParser.parse(feild);
				WildcardQuery wildcardQuery=new WildcardQuery(new Term("content", "motor*"));	//WildcardQuery实现通配符查询
			//	MultiFieldQueryParser multiFieldQueryParser=new MultiFieldQueryParser(new String[]{"path","filename"}, analyzer);
				hits=indexSearcher.search(wildcardQuery, 20);		//输出5条记录
			}
			for(ScoreDoc scoreDoc:hits.scoreDocs){
				Document document=indexSearcher.doc(scoreDoc.doc);
				System.out.println("path:"+document.get("path")+"\t")	;
				System.out.print("filename:"+document.get("filename").toString()+"\t")	;
				//System.out.print(document.get("content").toString()+"\t")	;

			}
			indexReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TermRangeQuery getTermRangeQuery(){
		//指定范围查询
		TermRangeQuery termRangeQuery=new TermRangeQuery("content", new BytesRef("collapsesa".getBytes()), new BytesRef("google".getBytes()), true, true);
		return termRangeQuery;
	}
	
	public PrefixQuery getPrefixQuery(){
		//指定字符串开头查询,
		PrefixQuery prefixQuery=new PrefixQuery(new Term("content","rot"));
		return prefixQuery;
	}
	
	public BooleanQuery.Builder getBooleanQueryBuilder(){
		//多种方式组合查询,
		PrefixQuery prefixQuery=new PrefixQuery(new Term("content","rot"));
		TermRangeQuery termRangeQuery=new TermRangeQuery("content", new BytesRef("collapsesa".getBytes()), new BytesRef("google".getBytes()), true, true);

		//QueryBuilder factory=new QueryBuilder(analyzer);
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		//builder.add(prefixQuery, new C);
		//BooleanQueryBuilder booleanQueryBuilder=new BooleanQueryBuilder(factory);
		return builder;
	}
}
