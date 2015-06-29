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
		File f;
		
		try {
			switch (args[0]) {
			case "100":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0100.txt");
				break;
			case "200":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0200.txt");
				break;
			case "300":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0300.txt");
				break;
			case "400":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0400.txt");
				break;
			case "500":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0500.txt");
				break;
			case "600":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0600.txt");
				break;
			case "700":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0700.txt");
				break;
			case "800":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0800.txt");
				break;
			case "900":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword0900.txt");
				break;
			case "1000":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1000.txt");
				break;
			case "1100":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1100.txt");
				break;
			case "1200":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1200.txt");
				break;
			case "1300":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1300.txt");
				break;
			case "1400":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1400.txt");
				break;
			case "1500":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1500.txt");
				break;
			case "1600":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1600.txt");
				break;
			case "1700":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1700.txt");
				break;
			case "1800":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1800.txt");
				break;
			case "1900":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword1900.txt");
				break;
			case "2000":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2000.txt");
				break;
			case "2100":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2100.txt");
				break;
			case "2200":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2200.txt");
				break;
			case "2300":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2300.txt");
				break;
			case "2400":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2400.txt");
				break;
			case "2500":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2500.txt");
				break;
			case "2600":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2600.txt");
				break;
			case "2700":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2700.txt");
				break;
			case "2800":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2800.txt");
				break;
			case "2900":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword2900.txt");
				break;
			case "3000":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3000.txt");
				break;
			case "3100":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3100.txt");
				break;
			case "3200":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3200.txt");
				break;
			case "3300":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3300.txt");
				break;
			case "3400":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3400.txt");
				break;
			case "3500":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3500.txt");
				break;
			case "3600":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3600.txt");
				break;
			case "3700":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3700.txt");
				break;
			case "3800":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3800.txt");
				break;
			case "3900":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword3900.txt");
				break;
			case "4000":
				f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword4000.txt");
				break;
			default:
				f = new File("src/timothyyudi/ahocorasick/asset/snortrulessimplekeyword.txt");
				break;
			}
			
//			File f = new File("src/timothyyudi/ahocorasick/asset/kjvkeyword_simple.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortrules.txt");
//			File f = new File("src/timothyyudi/ahocorasick/asset/snortruleskeyword.txt");
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
//			f = new File("src/timothyyudi/ahocorasick/asset/kjv.txt");
//			f = new File("src/timothyyudi/ahocorasick/asset/kjv_bug.txt");			
//			f = new File("src/timothyyudi/ahocorasick/asset/SimpleInputString.txt");
			switch (args[0]) {
			case "simple":
				f = new File("src/timothyyudi/ahocorasick/asset/snortrulesSimpleInputFile.txt");
				break;
			default:
				f = new File("src/timothyyudi/ahocorasick/asset/snortrulesInputFile.txt");
				break;
			}
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
