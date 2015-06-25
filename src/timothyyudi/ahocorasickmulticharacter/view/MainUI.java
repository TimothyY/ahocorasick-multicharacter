package timothyyudi.ahocorasickmulticharacter.view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import timothyyudi.ahocorasickmulticharacter.controller.AhoCorasick;
import timothyyudi.ahocorasickmulticharacter.controller.Utility;

public class MainUI {

	public static void main(String[] args){
		
		long timer;
		
		Utility util = new Utility();
		
		try {
//			keywords = util.readKeyword("c:/temp/snortrules.txt"); //load keywords from file
//			keywords = util.readKeyword("c:/temp/kjvkeyword_simple.txt"); //load keywords from file
//			File f = new File("src/timothyyudi/ahocorasick/asset/kjvkeyword_simple.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortrules.txt");
			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0100.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0200.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0300.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0400.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0500.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0600.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0700.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0800.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0900.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1000.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1100.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1200.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1300.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1400.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1500.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1600.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1700.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1800.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1900.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2000.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2100.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2200.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2300.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2400.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2500.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2600.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2700.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2800.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2900.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3000.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3100.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3200.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3300.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3400.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3500.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3600.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3700.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3800.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3900.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword4000.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortrulessimplekeyword.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/SimpleDatabase.txt");
			util.readKeyword(f); //load keywords from file
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		AhoCorasick ahoCorasick = new AhoCorasick();
		
		timer=System.currentTimeMillis();
		ahoCorasick.prepareGoToFunction(AhoCorasick.fullKeywordMap); //prepare ahocorasick goTo function
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish creating trie in "+timer + " millisecond(s)");
		
		timer=System.currentTimeMillis();
		ahoCorasick.prepareFailFromFunction(); //prepare ahocorasick fail function
		timer = System.currentTimeMillis() - timer;
		System.out.println("Finish creating fail function in "+timer + " millisecond(s)");

		String inputString="";	//prepare input string
		try {
//			File f = new File("src/timothyyudi/ahocorasick/asset/kjv.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/kjv_bug.txt");			
			File f = new File("src/timothyyudi/ahocorasick/asset/snortrulesInputFile.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortrulesSimpleInputFile.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/SimpleInputString.txt");
			inputString = util.readInputString(f, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

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
			util.writeOutput(AhoCorasick.outputList);
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
