package com.talos.invertedindex;

import java.util.List;
import java.util.Map;

import com.talos.invertedindex.datastructure.Index;


public class InvertedIndex {
	
	private String[] documentList;
	private Map<String, List<Index>> fullInvertedIndex = null;
	
	public InvertedIndex(String[] documentList) throws Exception{
		this.documentList = documentList;
		Indexer indexer = new Indexer();
		fullInvertedIndex = indexer.index(documentList);
	}
	
	public String[] get(String phrase) throws Exception{
		PhraseSearch phraseSearch = new PhraseSearch();
		return phraseSearch.search(phrase, fullInvertedIndex, documentList);
	}
	
}