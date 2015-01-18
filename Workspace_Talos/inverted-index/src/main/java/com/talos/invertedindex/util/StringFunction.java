package com.talos.invertedindex.util;

import java.util.HashSet;
import java.util.List;

public class StringFunction {
	
	public static List<Integer> deleteDuplictedInteger(List<Integer> integerList){
		HashSet<Integer> set = new HashSet<Integer>(integerList);
		integerList.clear();
		integerList.addAll(set);
		return integerList;
	}
	
	public static String deleteSpecialCharacters(String word){
		String[] characters = new String[]{",", "'", "/./"};
		for (int i=0; i<characters.length; i++){
			word = word.replaceAll(characters[i], "");
		}
		return word;
	}

}
