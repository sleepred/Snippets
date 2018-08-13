/**
2. Atbash
 - 입력받은 값을 아래와 같이 간단하게 암호화하시오
 - Ex) abc ==> zyx
 - 변환규칙
   Plain :   abcdefghijklmnopqrstuvwxyz
   Cipher : zyxwvutsrqponmlkjihgfedcba
 - A부터 Z에 해당하지 않는 문자는 원래문자를 리턴합니다.
 - 대소문자는 구분하여 작성합니다.
*/
package com.exercise.mytest2;

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
