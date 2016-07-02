package com.pku.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	String dataPath;
	String indexPath;
	IndexWriter indexWriter;
	boolean create;
	public Indexer(String dataPath,String indexPath) {
		this.dataPath=dataPath;
		this.indexPath=indexPath;
		try {
			Path path=Paths.get(indexPath);
			Directory indexDir=FSDirectory.open(path);
			StandardAnalyzer analyzer=new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);
			//indexWriterConfig.setOpenMode(OpenMode.APPEND);
			indexWriter=new IndexWriter(indexDir, indexWriterConfig);
			if (create) {
		      // Create a new index in the directory, removing any
		      // previously indexed documents:
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
			} else {
			  // Add new documents to an existing index:
				indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			indexDocs(indexWriter, dataPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void indexDocs(IndexWriter indexWriter2, String dataPath) {
		//is a directory or not
		//Path path=Paths.get(dataPath);
		if(new File(dataPath).isDirectory()){
			File files[]=new File(dataPath).listFiles();
			for(File file:files){
				if(!file.isDirectory()&&file.exists()&&file.canRead()){
					indexDocument(indexWriter, file);
				}
			}
		}
	}
	public void indexDocument(IndexWriter indexWriter,File file){
		Document document=new Document();
		try {
			document.add(new StringField("filename",file.toString(),Field.Store.YES));
			document.add(new StringField("path",file.getName(),Field.Store.YES));
			document.add(new TextField("content", new BufferedReader(new InputStreamReader(new FileInputStream(file),StandardCharsets.UTF_8))));
			//document.add(new TextField("内容", new BufferedReader(new InputStreamReader(new FileInputStream(file),StandardCharsets.UTF_8))));
			//if(indexWriter.getConfig().getOpenMode()==OpenMode.CREATE){
				System.out.println("add file "+file);
				indexWriter.addDocument(document);
			//}else{
				//System.out.println("update file "+file);
				//indexWriter.updateDocument(new Term("DOC", file.toString()),document);
			//}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void close(){
		//必需关闭indexWriter，索引才会从内存写入索引文件中
		try {
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
