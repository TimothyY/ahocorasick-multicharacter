//package timothyyudi.ahocorasickmulticharacter.controller;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.regex.Pattern;
//
//import timothyyudi.ahocorasickmulticharacter.model.LiteratedStatePointer;
//import timothyyudi.ahocorasickmulticharacter.model.Output;
//import timothyyudi.ahocorasickmulticharacter.model.State;
//
//public class CopyOfAhoCorasick {
//
//	State root;
//	LiteratedStatePointer literatedRoot;
//	State currState;
//	int keywordInsertionCounter, lineNumberCounter, columnNumberCounter;
//	
//	public static HashMap<Integer, String> fullKeywordMap = new HashMap<>();
//	public static ArrayList<Output> outputList = new ArrayList<>();
//	
//	long ahoCorasickTimeTotal;
//	long ahoCorasickTimeFragment;
//	long algoStart, algoEnd;
//	
//	String inputString, bufferStr0, bufferStr1;
//	
//	public CopyOfAhoCorasick(){
//		root= new State();
//		literatedRoot = new LiteratedStatePointer();
//		literatedRoot.setState(root);
//	}
//	
//	/**A function to move from 1 node of a trie to the others based on next input character*/
//	private State goTo(State node, String nextInputChar){
//		return node.getNextStateCollection().get(nextInputChar);
//	}
//		
//	/**Prepare AhoCorasick goto function/ successful state of AhoCorasick trie*/
//	public void prepareGoToFunction(HashMap<Integer, String> keywords){
//		for (Integer hashCodeKey : keywords.keySet()) {
//			enterKeyword(keywords.get(hashCodeKey));
//		}
//	}
//	
//	/**insert keywords to trie*/
//	private void enterKeyword(String keyword){
//		currState = root;
//		keywordInsertionCounter = 0;
//
//		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))!=null){ //while state already exist then go there.
//			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
//			keywordInsertionCounter++;
//		}
//	
//		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))==null){ //while state doesnt exist then create new node and go there
//			currState.getNextStateCollection().put(Character.toString(keyword.charAt(keywordInsertionCounter)), new State(currState, Character.toString(keyword.charAt(keywordInsertionCounter)), root));
//			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
//			if(keywordInsertionCounter==keyword.length()-1){
//				currState.setFullKeywordHashCode(keyword.hashCode());
//			}
//			keywordInsertionCounter++;
//		}
//		
//	}
//	
//	/**A function to move from 1 node of a trie to it's fail node*/
//	private State failFrom(State node){
//		return node.getFailState();
//	}
//	
//	/**Create the fail fall back state of AhoCorasick trie*/
//	public void prepareFailFromFunction(){
//LinkedList<State> queue = new LinkedList<State>(); //a linked list is needed for BFS
//		
//		for (State state : root.getNextStateCollection().values()) {
//			queue.add(state);
//			state.setFailState(root);
//		}
//		
//		State tempState;
//		
//		while(!queue.isEmpty()){
//			tempState = queue.pop(); //pop node and get the childrens
//			for (State state: tempState.getNextStateCollection().values()) { //implementation differ based on nextStateCollection data structure
//				queue.add(state);
//				currState=failFrom(tempState);
//				while(goTo(currState, state.getStateContentCharacter())==null&&!currState.equals(root)){ //while fail 
//					currState = failFrom(currState); //current state = failState	
//				}//exit while when found a match from goTo of a failState or when it reach root
//				if(goTo(currState, state.getStateContentCharacter())!=null){
//					state.setFailState(goTo(currState, state.getStateContentCharacter()));
//				}
//			}
//		}
//	}
//	
//	public void prepareNTrie(int multicharNumber){
//		
//		//HashMap<String, State> originalAncestors = new HashMap<>(root.getNextStateCollection());// ambil original ancestor
//		HashMap<String, LiteratedStatePointer> originalAncestors = new HashMap<>(root.getNextLiteratedStateCollection());// ambil original ancestor
//		
//		LinkedList<State> oriStateQueue = walkthroughTrie();
//		LinkedList<State> oriStateQueueForPhase1 = new LinkedList<>(oriStateQueue); 
//		LinkedList<State> oriStateQueueForPhase2 = new LinkedList<>(oriStateQueue); 
//		
//		State tempState; //aka Si, placeholder for loop.
//		HashMap<String, LiteratedStatePointer> tempNextLiteratedStatePointerMap;
//		
////		// [Phase 1] Enhance original trie as preparation before creating derviation node
////		while(!oriStateQueueForPhase1.isEmpty()){
////			tempState=oriStateQueueForPhase1.pop();
////			//create a copy so the hashmap can be added, avoid concurrency problem
////			//HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
////			if(tempState.getNextLiteratedStateCollection()==null){
////				tempState.setNextLiteratedStateCollection(new HashMap<>());
////			}
////			HashMap<String,LiteratedStatePointer> cpOriChildStateHM = new HashMap<>(tempState.getNextLiteratedStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
////			
////			for (String key : originalAncestors.keySet()) { //menambahkan originalAncestors lv1
////				if (!cpOriChildStateHM.containsKey(key)) {
////					cpOriChildStateHM.put(key, originalAncestors.get(key));
////				}
////			}
////			
////			//add negative original node as a regex pattern
////			String negativeOriginalPattern = "[^";
////			for (String key : cpOriChildStateHM.keySet()) {
////				negativeOriginalPattern += key;				
////			}
////			negativeOriginalPattern+="]";
////			//point negative regex to root
////			cpOriChildStateHM.put(negativeOriginalPattern, literatedRoot);
////			
////			tempState.getNextLiteratedStateCollection().putAll(cpOriChildStateHM);
////		}
//		
//		// [Phase 2] Start creating derivation node
//		String failPattern;
//		while(!oriStateQueueForPhase2.isEmpty()) {
//			tempState=oriStateQueueForPhase2.pop();
//			failPattern = "";
//			tempNextLiteratedStatePointerMap = new HashMap<>();
//			//create a copy so the hashmap can be added, avoid concurrency problem
//			HashMap<String,LiteratedStatePointer> cpOriChildStateHM;
//			if(tempState.getNextLiteratedStateCollection()!=null){
//				cpOriChildStateHM = new HashMap<String,LiteratedStatePointer>(tempState.getNextLiteratedStateCollection()); //jika curr state =0 maka ini adalah node lvl 1	
//			
//			//for (int m=0; m<multicharNumber-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali. useless for now
//				for (LiteratedStatePointer lStateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase
//					State stateNXi = lStateNXi.getState();
//					failPattern = Pattern.quote(stateNXi.getStateContentCharacter())+"[^";
//					HashMap<String, LiteratedStatePointer> lStateNXiChilds = stateNXi.getNextLiteratedStateCollection();//contoh anak node level 1
//					if(lStateNXiChilds!=null){//???
//						for (LiteratedStatePointer lStateNXj : lStateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
//							State stateNXj = lStateNXj.getState();
//							failPattern+=Pattern.quote(stateNXj.getStateContentCharacter());
//							String newPattern = Pattern.quote(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter());
//							tempNextLiteratedStatePointerMap.put(newPattern, new LiteratedStatePointer());
//							tempNextLiteratedStatePointerMap.get(newPattern).setState(stateNXj);
//							if(stateNXi.getFullKeywordHashCode()!=null||stateNXj.getFullKeywordHashCode()!=null){
//								tempNextLiteratedStatePointerMap.get(newPattern).setFullKeywordPointerList(new ArrayList<Integer>());
//								tempNextLiteratedStatePointerMap.get(newPattern).getFullKeywordPointerList().add(stateNXi.getFullKeywordHashCode());
//								tempNextLiteratedStatePointerMap.get(newPattern).getFullKeywordPointerList().add(stateNXj.getFullKeywordHashCode());	
//							}
//						}//END 5th phase
//					}
//					failPattern +="]";
//					if(stateNXi.getFullKeywordHashCode()!=null){
//						tempNextLiteratedStatePointerMap.put(failPattern, new LiteratedStatePointer());
//						tempNextLiteratedStatePointerMap.get(failPattern).setState(null);
//						tempNextLiteratedStatePointerMap.get(failPattern).setFullKeywordPointerList(new ArrayList<Integer>());
//						tempNextLiteratedStatePointerMap.get(failPattern).getFullKeywordPointerList().add(stateNXi.getFullKeywordHashCode());
//					}
//				}//END 4th phase
//			//}//END 3rd phase
//			tempState.getNextStateCollection().putAll(tempNextLiteratedStatePointerMap);
//			}
//		}//END 2nd Phase
//	}//END
//	
//	/**used to convert the trie state as a linkedlist).
//	 * Only use this on original trie. Usage on derivated trie will cause loop. 
//	 * This includes root state.*/
//	private LinkedList<State> walkthroughTrie(){
//		LinkedList<State> queue = new LinkedList<State>(); //a linked list is needed for BFS
//		LinkedList<State> resultQueue = new LinkedList<State>(); //a linked list is needed for BFS
//		resultQueue.add(root);
//		for (State state : root.getNextStateCollection().values()) {
//			queue.add(state);
//			resultQueue.add(state);
//		}
//		
//		State tempState;
//		
//		while(!queue.isEmpty()){
//			tempState = queue.pop(); //pop node and get the childrens
//			if(tempState.getNextStateCollection()!=null){
//				for (State state : tempState.getNextStateCollection().values()) {
//					queue.add(state);
//					resultQueue.add(state);
//				}
//			}
//		}
//		return resultQueue;
//	}
//	
//	/**A function to match input string against constructed AhoCorasick trie*/
//	public void nPatternMatching(String inputString){
//		
//		this.inputString = inputString;
//		
//		currState = root;
//		lineNumberCounter=1;
//		columnNumberCounter=1;
//		String inputStringBuffer="";
//		int inputStringLength = inputString.length();
//		int inputStringLastPosition = inputStringLength -1;
//		
//		for (int i = 0; i < inputStringLength; i++) { //as long as there is an input
//			
//			columnNumberCounter++;
//			if(inputString.charAt(i)=='\n'){
////				System.out.println("processing line: "+lineNumberCounter);
//				lineNumberCounter++;
//				columnNumberCounter=1;
//			}
//
//			bufferStr1 = ""+inputString.charAt(i);
//			inputStringBuffer = inputStringBuffer + bufferStr1;
//			
//			if(inputStringBuffer.length()==2 || i==inputStringLastPosition){
//				
//				algoStart=System.currentTimeMillis();
//				
//				bufferStr0 = ""+inputStringBuffer.charAt(0);
//				
//				while (goTo(currState, inputStringBuffer)==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
//					prepareOutput(currState.getNextStateCollection().get(bufferStr0).getFullKeywordHashCode(), lineNumberCounter, columnNumberCounter);
//					currState= failFrom(currState);
//				}
//				if(goTo(currState, inputStringBuffer)!=null){
//					currState = goTo(currState, inputStringBuffer); //set the current node to the result of go to function
//					prepareOutput(currState.getFullKeywordHashCode(), lineNumberCounter, columnNumberCounter);
//				}
//
//				algoEnd=System.currentTimeMillis();
//				ahoCorasickTimeFragment=algoEnd-algoStart;
//				ahoCorasickTimeTotal+=ahoCorasickTimeFragment;
//				
//				//do some xploration and search
//				inputStringBuffer = ""; //reset buffer
//			}
//
//		}
//		
//		Utility util = new Utility();
//		util.writeAhoCorasickTime(ahoCorasickTimeTotal);
//	}
//	
//	/**prepare output for the matching keywords found*/
//	private void prepareOutput(Integer keywordHashCode,int lineNumber, int endColumnNumber){
//		if(keywordHashCode!=null)
//			outputList.add(new Output(CopyOfAhoCorasick.fullKeywordMap.get(keywordHashCode), lineNumber, endColumnNumber-(CopyOfAhoCorasick.fullKeywordMap.get(keywordHashCode).length()), endColumnNumber-1));
//		
//		
//	}
//	
//}