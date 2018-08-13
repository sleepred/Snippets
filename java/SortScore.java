package com.exercise.sortscore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class SortScore {

	private BufferedReader reader;
	private ArrayList<Score> list = new ArrayList<Score>();
	
	public SortScore() {
		try {
			reader = new BufferedReader(new FileReader("data/data.txt"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String code = line.substring(0, line.indexOf(","));
				int total = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.length()));
				list.add( new Score(code,total) );
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException Error");
		} catch (IOException e) {
			System.out.println("IOException Error");
		}
	}	
	
    public void sort() {
    	ArrayList<Score> sortedList = new ArrayList<Score>();
    	
    	TOP_LOOP:
    	for (int i = 0; i < list.size(); i++) {
	    	Score curScore = list.get(i);
	    	
	    	if(sortedList.size() == 0){
	    		sortedList.add(curScore);
	    		continue;
	    	}
	    	
	    	for(int j=0 ; j<sortedList.size(); j++){
	    		Score score = sortedList.get(j);
	    		
	    		if(score.getTotal() < curScore.getTotal()){
	    			sortedList.add(j, curScore);
	    			continue TOP_LOOP;
	    		}
	    	}
	    	sortedList.add(curScore);
    	}
    	list = sortedList;
    }
    
    public void display() {
    	for (int i = 0; i < list.size(); i++) {
	    	System.out.print(((Score)list.get(i)).getCode());
	    	System.out.println("\t" +((Score)list.get(i)).getTotal());
    	}
    }

}

class Score {

	private String code;
	private int total;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	Score(String code, int total) {
		this.code = code;
		this.total = total;
	}

}
