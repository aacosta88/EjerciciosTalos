package com.talos.invertedindex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talos.invertedindex.datastructure.Index;
import com.talos.invertedindex.util.StringFunction;

public class PhraseSearch {
	
	public String[] search(String phrase, Map<String, List<Index>> fullInvertedIndex, String[] documentList) throws Exception{
		List<Integer> intersectionList = null;
		if (phrase != null && !phrase.trim().equals("")){
			List<List<Index>> ocurrenceList = obtainOccurrences(phrase, fullInvertedIndex);
			
			if (!ocurrenceList.isEmpty()){
				intersectionList = obtainIntersectionList(ocurrenceList);
				intersectionList = StringFunction.deleteDuplictedInteger(intersectionList);
			}
		}else {
			throw new Exception("The phrase can't be empty");
		}
		return findResultInDocumentList(intersectionList, documentList);
	}
	
	private String[] findResultInDocumentList(List<Integer> intersectionList, String[] documentList){
		if (intersectionList == null){
			return null;
		}
		String[] result = new String[intersectionList.size()];
		if (intersectionList != null && !intersectionList.isEmpty()){
			for (int i=0; i<intersectionList.size(); i++ ){
				Integer idDocument = intersectionList.get(i);
				result[i] = documentList[idDocument];
			}
		}
		return result;
	}
	
	private List<Integer> obtainIntersectionList(List<List<Index>> ocurrenceList) {
		List<Integer> result = null;
		if (ocurrenceList.size() == 1){
			result = ocurrenceForOneWord(ocurrenceList);
		}else {
			result = ocurrenceMoreThanOneWord(ocurrenceList);
		}
		return result;
	}
	
	private List<Integer> ocurrenceMoreThanOneWord(List<List<Index>> ocurrenceList){
		List<Index> firstOcurrence = ocurrenceList.get(0);
		List<Integer> ocurrenceResult = new ArrayList<Integer>();
		for (Index index : firstOcurrence){
			boolean hit = true;
			int numberOfOcurrences = ocurrenceList.size();
			for (int i=1; i<=numberOfOcurrences-1 && hit; i++){
				hit = appearsAtLeastOnce(index, ocurrenceList.get(i));
			}
			ocurrenceResult.add(index.getIdDocument());
		}
		return ocurrenceResult;
	}
	
	private boolean appearsAtLeastOnce(Index pivotIndex, List<Index> currentIndexes){
		boolean exist = false;
		for (int i=0; i<currentIndexes.size() && !exist; i++){
			Index index = currentIndexes.get(i);
			if (pivotIndex.getIdDocument() == index.getIdDocument()){
				exist = true;
			}
		}
		return exist;
	}

	private List<Integer> ocurrenceForOneWord(List<List<Index>> ocurrenceList) {
		List<Index> ocurrence = ocurrenceList.get(0);
		List<Integer> ocurrenceResult = new ArrayList<Integer>();
		for (int i=0; i<ocurrence.size(); i++){
			ocurrenceResult.add(ocurrence.get(i).getIdDocument());
		}
		return ocurrenceResult;
	}

	private List<List<Index>> obtainOccurrences(String phrase, Map<String, List<Index>> fullInvertedIndex){
		String[] splitPhrase = phrase.split(" ");
		List<List<Index>> ocurrenceList = new ArrayList<List<Index>>();
		for (String word : splitPhrase){
			List<Index> indexList = fullInvertedIndex.get(word);
			if (indexList != null){
				ocurrenceList.add(indexList);
			}
		}
		return ocurrenceList;
	}

}
