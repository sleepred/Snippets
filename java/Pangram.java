/**
1. Pangram
 - 입력받은 값이 A부터 Z까지 모두 사용했는지를 체크하는 프로그램을 작성하시오
 - 단 알파벳 대소문자는 구분하지 않습니다.
*/

package com.exercise.mytest1;

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
