package com.pku.test;

import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.pku.crawler.Crawler;
import com.pku.indexer.Indexer;
import com.pku.searcher.Searcher;

public class Test {
	public static void main(String[] args) {
//		Crawler crawler=new Crawler("http://www.baidu.com");
//		String res=crawler.getResponse();
//		System.out.println(res);
		
		//建立文件索引
		String dataPath="F:\\MyLucense\\data";
		String indexPath="F:\\MyLucense\\index";
		Indexer indexer=new Indexer(dataPath,indexPath);
		//不关闭indexwriter将导致索引创建失败
		indexer.close();
		
		//搜索文件
		System.out.println("**************************");
		Searcher searcher=new Searcher();
		searcher.search(indexPath, "solar");
		
		//String str="this is a message:农业是一项非常有技术含量的科技";
		//new AnalizerTest().analize(new StandardAnalyzer(), str);
	}
}

