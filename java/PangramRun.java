package com.exercise.mytest1;

public class PangramRun {
	public static void main(String args[]){
		Pangram pangram = new Pangram();
		System.out.println("isPangram : " + pangram.isPangram("the quick brown fox jumps over the lazy dog"));
		System.out.println("isPangram : " + pangram.isPangram("a quick movement of the enemy will jeopardize five gunboats"));
		System.out.println("isPangram : " + pangram.isPangram("\"Five quacking Zephyrs jolt my wax bed.\""));
		System.out.println("isPangram : " + pangram.isPangram("Victor jagt zwölf Boxkämpfer quer über den großen Sylter Deich."));
	}
}
