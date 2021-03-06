package com.talos.invertedindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talos.invertedindex.datastructure.Index;
import com.talos.invertedindex.util.StringFunction;

public class Indexer {
	
	private Map<String, List<Index>> fullInvertedIndex = new HashMap<String, List<Index>>();
	
	public Map<String, List<Index>> index(String[] documentListList) throws Exception{
		if (documentListList != null && documentListList.length > 0){
			for (int documentIndex=0; documentIndex<documentListList.length; documentIndex++){
				String[] splitPhrase = documentListList[documentIndex].split(" ");
				for (int wordIndex=0; wordIndex<splitPhrase.length; wordIndex++){
					String word = splitPhrase[wordIndex];
					word = StringFunction.deleteSpecialCharacters(word);
					List<Index> indexList = fullInvertedIndex.get(word);
					if (indexList == null){
						fullInvertedIndex.put(word, generateIndexList(documentIndex, wordIndex));
					}else {
						indexList.add(new Index(documentIndex, wordIndex));
					}
				}
			}
			
		}else {
			throw new Exception("Document list can't be empty");
		}
		return fullInvertedIndex;
	}
	
	private List<Index> generateIndexList(int indexPhrase, int indexWord){
		Index index = new Index(indexPhrase, indexWord);
		List<Index> indexList = new ArrayList<Index>();
		indexList.add(index);
		return indexList;
	}

}
