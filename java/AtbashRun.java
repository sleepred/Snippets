package com.lgcns.exercise.mytest2;

public class AtbashRun {
	public static void main(String args[]){
		Atbash atbash = new Atbash();
		System.out.println(atbash.encode("vcvix rhn"));
		System.out.println(atbash.encode("zmlyh gzxov rhlug vmzhg vkkrm thglm v"));
		System.out.println(atbash.encode("gvhgr mt123 gvhgr mt"));
		System.out.println(atbash.encode("no"));
		System.out.println(atbash.encode("yes"));
		System.out.println(atbash.encode("OMG"));
		System.out.println(atbash.encode("mindblowingly"));
		System.out.println(atbash.encode("Testing, 1 2 3, testing."));
		System.out.println(atbash.encode("Truth is fiction."));
		System.out.println(atbash.encode("The quick brown fox jumps over the lazy dog."));
	}
}
