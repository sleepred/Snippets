package com.lgcns.exercise.mytest1;

import java.util.HashMap;
import java.util.Map;


public class Pangram {
	public boolean isPangram(String sentence){
		boolean ret = false;
		
		// start
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		char currentChar = 'A';
		for(int i=1; i<=26; i++){
			map.put(String.valueOf(currentChar), null);
			currentChar++;
		}
		
		for(int i=0; i<sentence.length(); i++){
			map.remove(String.valueOf(sentence.charAt(i)).toUpperCase());
		}
		
		if(map.size() == 0){
			ret = true;
		}
		else{
			ret = false;
		}
		// end
		
		return ret;
	}
}
