package com.lgcns.exercise.mytest2;

import java.util.HashMap;
import java.util.Map;



public class Atbash {
	private Map<Character, Character> cipherMap;
	
	public Atbash(){
		cipherMap = new HashMap<Character, Character>();
		
		char plainChar = 'A';
		char cipherChar = 'Z';
		for(int i=1; i<=26; i++){
			cipherMap.put(plainChar, cipherChar);
			plainChar++;
			cipherChar--;
		}
		
		plainChar = 'a';
		cipherChar = 'z';
		for(int i=1; i<=26; i++){
			cipherMap.put(plainChar, cipherChar);
			plainChar++;
			cipherChar--;
		}
	}
	
	public String encode(String plainText){
		String ret = "";
		
		// start
		
		for(int i=0; i<plainText.length(); i++){
			char encode = cipherMap.get(plainText.charAt(i)) == null ? plainText.charAt(i) : cipherMap.get(plainText.charAt(i)); 
			ret += encode;
		}
		
		// end

		return ret;
	}
}
