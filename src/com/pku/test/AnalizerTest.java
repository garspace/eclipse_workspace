package com.pku.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.PrefixCodedTerms.TermIterator;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.FieldComparator.TermOrdValComparator;

public class AnalizerTest {
	public void analize(Analyzer analyzer,String str){
		TokenStream tokenStream=analyzer.tokenStream("content", new StringReader(str));
		//tokenStream.addAttributeImpl();
		try {
			while(tokenStream.incrementToken()) {
				TermToBytesRefAttribute termToBytesRefAttribute = (TermToBytesRefAttribute) tokenStream.getAttribute(TermToBytesRefAttribute.class);
				System.out.println(termToBytesRefAttribute.getBytesRef().toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
