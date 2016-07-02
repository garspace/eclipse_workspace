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
		
		//�����ļ�����
		String dataPath="F:\\MyLucense\\data";
		String indexPath="F:\\MyLucense\\index";
		Indexer indexer=new Indexer(dataPath,indexPath);
		//���ر�indexwriter��������������ʧ��
		indexer.close();
		
		//�����ļ�
		System.out.println("**************************");
		Searcher searcher=new Searcher();
		searcher.search(indexPath, "solar");
		
		//String str="this is a message:ũҵ��һ��ǳ��м��������ĿƼ�";
		//new AnalizerTest().analize(new StandardAnalyzer(), str);
	}
}

