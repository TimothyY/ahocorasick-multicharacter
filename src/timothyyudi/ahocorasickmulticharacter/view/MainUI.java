package timothyyudi.ahocorasickmulticharacter.view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import timothyyudi.ahocorasickmulticharacter.controller.AhoCorasick;
import timothyyudi.ahocorasickmulticharacter.controller.Utility;

public class MainUI {

	public static void main(String[] args){
		
		long timer;
		Scanner scanner;
		
		Utility util = new Utility();
		
		ArrayList<String> keywords = new ArrayList<String>(); //prepare keywords
		try {
//			keywords = util.readKeyword("c:/temp/snortrules.txt"); //load keywords from file
//			keywords = util.readKeyword("c:/temp/kjvkeyword_simple.txt"); //load keywords from file
//			File f = new File("src/timothyyudi/ahocorasick/asset/kjvkeyword_simple.txt");
			File f = new File("src/timothyyudi/ahocorasick/asset/snortrules.txt");
			keywords = util.readKeyword(f); //load keywords from file
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		AhoCorasick ahoCorasick = new AhoCorasick();
		
		timer=System.currentTimeMillis();
		ahoCorasick.prepareGoToFunction(keywords); //prepare ahocorasick goTo function
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish creating trie in "+timer + " millisecond(s)");
		
		timer=System.currentTimeMillis();
		ahoCorasick.prepareFailFromFunction(); //prepare ahocorasick fail function
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish creating fail function in "+timer + " millisecond(s)");
		
		String inputString="";	//prepare input string
		try {
//			inputString = util.readInputString("c:/temp/inputString2.txt", Charset.defaultCharset()); //load input string from file
//			inputString = util.readInputString("c:/temp/kjv.txt", Charset.defaultCharset()); //load input string from file
//			File f = new File("src/timothyyudi/ahocorasick/asset/kjv.txt");
			File f = new File("src/timothyyudi/ahocorasick/asset/snortrulesInputFile.txt");
			inputString = util.readInputString(f, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.print("Input n for n-multi character Aho Corasick: ");
//		scanner = new Scanner(System.in);
//		int n = scanner.nextInt();
//		scanner.close();
//		if(n<1)n=1;
		int n=2; //force 2 multichar
		System.out.println("Creating "+n+"-multi character trie");
		timer=System.currentTimeMillis();
		ahoCorasick.prepareNTrie(n);
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish creating "+n+"-multi character trie in "+timer + " millisecond(s)");
		
//		System.out.println("Creating "+n+"-multi character trie");
//		timer=System.currentTimeMillis();
//		try {
//			ahoCorasick.walkthroughTrieAndPrint();
//		} catch (Exception e) {
//			System.out.println("walkthroughTrieAndPrint Error: "+e);
//		}
//		timer = System.currentTimeMillis() - timer;
//		System.out.println("Finish creating "+n+"-multi character trie in "+timer + " millisecond(s)");
		
		System.out.println(""+n+"-multi character Aho Corasick is READY....BEGIN pattern matching...");
		timer=System.currentTimeMillis();
		ahoCorasick.nPatternMatching(inputString);
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish multi-pattern matching in "+timer + " millisecond(s)");
		
		System.out.println("DONE matching...WRITING results...");
		
		timer=System.currentTimeMillis();
		try {
			util.writeOutput(ahoCorasick.outputList);
		} catch (Exception e) {
			System.out.println("writeOutput Error: "+e);
		}
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish writing results in "+timer + " millisecond(s)");
		
		System.out.println("COMPLETED");
		
		// Get the Java runtime
		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();
		// Calculate the used memory
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory is bytes: " + memory);
	}
	
}
