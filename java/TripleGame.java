package com.exercise.triplegame;


public class TripleGame {

    public static int countTriple() {
        int cnt = 0;
        
        for(int i=1; i<=1000; i++){
        	String value = String.valueOf(i);
        	for(int j=0; j<value.length(); j++){
        		if(value.charAt(j) == '3' || value.charAt(j) == '6' || value.charAt(j) == '9'){
        			cnt++;
        		}
        	}
//        	System.out.println();
        }
        
        return cnt;
    }

}
