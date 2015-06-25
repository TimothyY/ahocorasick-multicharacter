package timothyyudi.ahocorasickmulticharacter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import timothyyudi.ahocorasickmulticharacter.model.LiteratedStatePointer;
import timothyyudi.ahocorasickmulticharacter.model.Output;
import timothyyudi.ahocorasickmulticharacter.model.State;

public class AhoCorasick {

	State root;
	LiteratedStatePointer literatedRoot;
	State currState;
	int keywordInsertionCounter, lineNumberCounter, columnNumberCounter;
	
	public static HashMap<Integer, String> fullKeywordMap = new HashMap<>();
	public static ArrayList<Output> outputList = new ArrayList<Output>();
	static int SUPPORTSKIPPEDNODEFORFAIL=1;
	static int SUPPORTSKIPPEDNODEFORSUCCESS=2;
	
	long ahoCorasickTimeTotal;
	long ahoCorasickTimeFragment;
	long algoStart, algoEnd;
	
	String inputString, bufferStr0, bufferStr1;
	
	public AhoCorasick(){
		root= new State();
		literatedRoot = new LiteratedStatePointer();
		literatedRoot.setState(root);
	}
	
	/**A function to move from 1 node of a trie to the others based on next input character*/
	private State goTo(State node, String nextInputChar){
		try {
			return node.getNextStateCollection().get(nextInputChar);
		} catch (Exception e) {
			return null;
		}
	}
		
	/**Prepare AhoCorasick goto function/ successful state of AhoCorasick trie*/
	public void prepareGoToFunction(HashMap<Integer, String> keywords){
		for (Integer hashCodeKey : keywords.keySet()) {
			enterKeyword(keywords.get(hashCodeKey));
		}
	}
	
	/**insert keywords to trie*/
	private void enterKeyword(String keyword){
		currState = root;
		keywordInsertionCounter = 0;

		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))!=null){ //while state already exist then go there.
			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
			keywordInsertionCounter++;
		}
	
		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))==null){ //while state doesnt exist then create new node and go there
			if(currState.getNextStateCollection()==null)currState.setNextStateCollection(new HashMap<String,State>());
			currState.getNextStateCollection().put(Character.toString(keyword.charAt(keywordInsertionCounter)), new State(currState, Character.toString(keyword.charAt(keywordInsertionCounter)), root));
			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
			if(keywordInsertionCounter==keyword.length()-1){
				currState.setFullKeywordHashCode(keyword.hashCode());
			}
			keywordInsertionCounter++;
		}
		
	}
	
	/**A function to move from 1 node of a trie to it's fail node*/
	private State failFrom(State node){
		return node.getFailState();
	}
	
	/**Create the fail fall back state of AhoCorasick trie*/
	public void prepareFailFromFunction(){
		LinkedList<State> queue = new LinkedList<State>(); //a linked list is needed for BFS
		
		for (State state : root.getNextStateCollection().values()) {
			queue.add(state);
			state.setFailState(root);
		}
		
		State tempState;
		
		while(!queue.isEmpty()){
			tempState = queue.pop(); //pop node and get the childrens
			if(tempState.getNextStateCollection()!=null){
				for (State state: tempState.getNextStateCollection().values()) { //implementation differ based on nextStateCollection data structure
					queue.add(state);
					currState=failFrom(tempState);
					while(goTo(currState, state.getStateContentCharacter())==null&&!currState.equals(root)){ //while fail 
						currState = failFrom(currState); //current state = failState	
					}//exit while when found a match from goTo of a failState or when it reach root
					if(goTo(currState, state.getStateContentCharacter())!=null){
						state.setFailState(goTo(currState, state.getStateContentCharacter()));
					}
				}
			}
		}
	}
	
	public void prepareNTrie(int multicharNumber){
		
		LinkedList<State> oriStateQueue = walkthroughTrie();
		LinkedList<State> oriStateQueueForPhase1 = new LinkedList<>(oriStateQueue); 
		LinkedList<State> oriStateQueueForPhase2 = new LinkedList<>(oriStateQueue); 
		
		State tempState; //aka Si, placeholder for loop.
		HashMap<String, State> tempNextLiteratedStatePointerMap;
				
		// [Phase 2] Start creating derivation node
		while(!oriStateQueueForPhase2.isEmpty()) {
			tempState=oriStateQueueForPhase2.pop();
			tempNextLiteratedStatePointerMap = new HashMap<String,State>();
			//create a copy so the hashmap can be added, avoid concurrency problem
			HashMap<String,State> cpOriChildStateHM;
			if(tempState.getNextStateCollection()!=null){
				cpOriChildStateHM = new HashMap<String,State>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1	
			
				for (State stateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase
					HashMap<String, State> stateNXiChilds = stateNXi.getNextStateCollection();//contoh anak node level 1
					if(stateNXiChilds!=null){//???
						for (State stateNXj : stateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
							String newPattern = stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter();
							tempNextLiteratedStatePointerMap.put(newPattern, stateNXj);
							//bisa improve dengan masukin implementasi fullKeywordHashCodeList
						}//END 5th phase
					}
					//mungkin bisa improve dengan add fullKeyword dari failnode di sini kekny salah tempat
				}//END 4th phase
				tempState.getNextStateCollection().putAll(tempNextLiteratedStatePointerMap);
			}
		}//END 2nd Phase
	}//END
	
	/**used to convert the trie state as a linkedlist).
	 * Only use this on original trie. Usage on derivated trie will cause loop. 
	 * This includes root state.*/
	private LinkedList<State> walkthroughTrie(){
		LinkedList<State> queue = new LinkedList<State>(); //a linked list is needed for BFS
		LinkedList<State> resultQueue = new LinkedList<State>(); //a linked list is needed for BFS
		resultQueue.add(root);
		for (State state : root.getNextStateCollection().values()) {
			queue.add(state);
			resultQueue.add(state);
		}
		
		State tempState;
		
		while(!queue.isEmpty()){
			tempState = queue.pop(); //pop node and get the childrens
			if(tempState.getNextStateCollection()!=null){
				for (State state : tempState.getNextStateCollection().values()) {
					queue.add(state);
					resultQueue.add(state);
				}
			}
		}
		return resultQueue;
	}
	
	/**A function to match input string against constructed AhoCorasick trie*/
	public void nPatternMatching(String inputString){
		
		this.inputString = inputString;
		
		currState = root;
		lineNumberCounter=1;
		columnNumberCounter=1;
		String inputStringBuffer="";
		int inputStringLength = inputString.length();
		int inputStringLastPosition = inputStringLength -1;
		
		for (int i = 0; i < inputStringLength; i++) { //as long as there is an input
			
			columnNumberCounter++;
			if(inputString.charAt(i)=='\n'){
//				System.out.println("processing line: "+lineNumberCounter);
				lineNumberCounter++;
				columnNumberCounter=1;
			}

			bufferStr1 = ""+inputString.charAt(i);
			inputStringBuffer = inputStringBuffer + bufferStr1;
			
			if(inputStringBuffer.length()==2 || i==inputStringLastPosition){
				
				algoStart=System.currentTimeMillis();
				
				bufferStr0 = ""+inputStringBuffer.charAt(0);
				
				while (goTo(currState, inputStringBuffer)==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
					try {
						if(inputStringBuffer.length()==2)
//							prepareOutput(currState.getNextStateCollection().get(bufferStr0), lineNumberCounter, columnNumberCounter,1, AhoCorasick.SUPPORTSKIPPEDNODEFORFAIL);
							prepareOutput(currState.getNextStateCollection().get(bufferStr0), lineNumberCounter, columnNumberCounter, 1);
					} catch (Exception e) {}
					
					currState= failFrom(currState);
				}
				if(goTo(currState, inputStringBuffer)!=null){
//					System.out.println("currState: "+currState.getStateContentCharacter());
					try {
						if(inputStringBuffer.length()==2)
//							prepareOutput(currState.getNextStateCollection().get(bufferStr0), lineNumberCounter, columnNumberCounter, AhoCorasick.SUPPORTSKIPPEDNODEFORSUCCESS);// tadinya buat cetak yang ke skip tapi malah kena yang emang cuman 1 input.
							prepareOutput(currState.getNextStateCollection().get(bufferStr0), lineNumberCounter, columnNumberCounter,2);// tadinya buat cetak yang ke skip tapi malah kena yang emang cuman 1 input.
					} catch (Exception e) {}
					currState = goTo(currState, inputStringBuffer); //set the current node to the result of go to function
					try {
//						prepareOutput(currState, lineNumberCounter, columnNumberCounter, AhoCorasick.SUPPORTSKIPPEDNODEFORSUCCESS);
						prepareOutput(currState, lineNumberCounter, columnNumberCounter, 2);
					} catch (Exception e) {}
//					System.out.println("input: "+inputStringBuffer);
				}else if(goTo(currState, inputStringBuffer)==null&&currState.equals(root)){
					//shifting enforcer
					try {
						currState = goTo(currState, bufferStr1); //set the current node to the result of go to function
//						prepareOutput(currState, lineNumberCounter, columnNumberCounter, AhoCorasick.SUPPORTSKIPPEDNODEFORSUCCESS);
						prepareOutput(currState, lineNumberCounter, columnNumberCounter, 2);
					} catch (Exception e) {
						currState = root;
					}
				}
				

				algoEnd=System.currentTimeMillis();
				ahoCorasickTimeFragment=algoEnd-algoStart;
				ahoCorasickTimeTotal+=ahoCorasickTimeFragment;
				
				//do some xploration and search
				inputStringBuffer = ""; //reset buffer
			}

		}
		
		Utility util = new Utility();
		util.writeAhoCorasickTime(ahoCorasickTimeTotal);
	}
	
	/**prepare output for the matching keywords found*/
	private void prepareOutput(State state,int lineNumber, int endColumnNumber, int failNodeCase){
		if(state.getFullKeywordHashCode()!=null){//jika currNode = fullword
//			System.out.println("["+AhoCorasick.fullKeywordMap.get(state.getFullKeywordHashCode())+"] Output phase 1 called at stage "+stage);
			outputList.add(new Output(state.getFullKeywordHashCode(), lineNumber, endColumnNumber));
		}
		
//		while(!failFrom(state).equals(root)&&failNodeCase==AhoCorasick.SUPPORTSKIPPEDNODEFORSUCCESS){//jika state tersebut punya fail node yang bukan root
		while(!failFrom(state).equals(root)&&failNodeCase==2){//jika state tersebut punya fail node yang bukan root
			state = failFrom(state);
			if(state.getFullKeywordHashCode()!=null){//jika failState == fullword
//				System.out.println("["+AhoCorasick.fullKeywordMap.get(state.getFullKeywordHashCode())+"] Output phase 2 called at stage "+stage);
				outputList.add(new Output(state.getFullKeywordHashCode(), lineNumber, endColumnNumber));
			}
		}
	}
}
