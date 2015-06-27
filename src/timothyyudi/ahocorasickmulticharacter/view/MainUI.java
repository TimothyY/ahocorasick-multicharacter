package timothyyudi.ahocorasickmulticharacter.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import timothyyudi.ahocorasickmulticharacter.controller.AhoCorasick;
import timothyyudi.ahocorasickmulticharacter.controller.Utility;

public class MainUI {

	public static long preprocessingTimer, processingTimer;
	
	public static void main(String[] args){
	
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
		
		preprocessingTimer = System.currentTimeMillis();
		ahoCorasick.prepareGoToFunction(AhoCorasick.fullKeywordMap); //prepare ahocorasick goTo function
		ahoCorasick.prepareFailFromFunction(); //prepare ahocorasick fail function
		ahoCorasick.prepare2Trie();
		preprocessingTimer = System.currentTimeMillis() - preprocessingTimer;
		System.out.println("Finish preprocessing "+2+"-multi character trie in "+preprocessingTimer + " millisecond(s)");
		
		// Get the Java runtime
		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();
		// Calculate the used memory
		long preprocessingMemory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory for preprocessing in Bytes: " + preprocessingMemory);
				
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
		
		System.out.println(""+2+"-multi character Aho Corasick is READY....BEGIN pattern matching...");
		ahoCorasick.nPatternMatching(inputString);
		System.out.println("Finish multi-pattern matching in "+processingTimer + " millisecond(s)");
		
		System.out.println("DONE matching...WRITING results now...");
		
		try {
			util.writeOutput(AhoCorasick.outputList);
		} catch (Exception e) {
			System.out.println("writeOutput Error: "+e);
		}
		System.out.println("COMPLETED");
		
		try {
		    PrintWriter preprocessTimerWriter = new PrintWriter(new BufferedWriter(new FileWriter("preprocessTimerAhoCorasickMultiCharacter.txt", true)));
		    preprocessTimerWriter.println(""+preprocessingTimer);
		    preprocessTimerWriter.close();
		    PrintWriter preprocessMemoryWriter = new PrintWriter(new BufferedWriter(new FileWriter("preprocessMemoryAhoCorasickMultiCharacter.txt", true)));
		    preprocessMemoryWriter.println(""+preprocessingMemory);
		    preprocessMemoryWriter.close();
		    PrintWriter processTimerWriter = new PrintWriter(new BufferedWriter(new FileWriter("processTimerAhoCorasickMultiCharacter.txt", true)));
		    processTimerWriter.println(""+processingTimer);
		    processTimerWriter.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		
	}
	
}
