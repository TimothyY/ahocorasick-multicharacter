package timothyyudi.ahocorasickmulticharacter.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import timothyyudi.ahocorasickmulticharacter.model.LiteratedStatePointer;
import timothyyudi.ahocorasickmulticharacter.model.Output;
import timothyyudi.ahocorasickmulticharacter.model.State;

public class AhoCorasick {

	State root;
	LiteratedStatePointer literatedRoot;
	State currState;
	int keywordInsertionCounter, lineNumberCounter, columnNumberCounter;
	
	public static HashMap<Integer, String> fullKeywordMap = new HashMap<>();
	public static ArrayList<Output> outputList = new ArrayList<>();
	
	long ahoCorasickTimeTotal;
	long ahoCorasickTimeFragment;
	long algoStart, algoEnd;
	
	String inputString, bufferStr0, bufferStr1;
	
	public AhoCorasick(){
		root= new State();
		literatedRoot = new LiteratedStatePointer();
		literatedRoot.setState(root);
		currState = root;
	}
	
	/**A function to move from 1 node of a trie to the others based on next input character*/
	private State goToMatch(State originNode, String nextInputChar){
		HashMap<String, LiteratedStatePointer> tempACHM = originNode.getNextLiteratedStateCollection();
		try {
			for (String key : tempACHM.keySet()) {
				if (nextInputChar.matches(key)) {
					originNode.getNextLiteratedStateCollection().get(key).getState().setOriginLiteratedStatePointer(originNode.getNextLiteratedStateCollection().get(key));
					return originNode.getNextLiteratedStateCollection().get(key).getState();
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
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
		String currChar="";
		String literalCurrChar="";
		LiteratedStatePointer tempLiteratedStatePointer = null;
		
		for (int i = 0; i < keyword.length(); i++) {
			
			currChar=Character.toString(keyword.charAt(keywordInsertionCounter));
			if(goToMatch(currState, currChar)!=null){
				currState=goToMatch(currState, currChar);
			}else{
				literalCurrChar = Pattern.quote(currChar);
				currState.getNextLiteratedStateCollection().put(literalCurrChar, new LiteratedStatePointer());
				tempLiteratedStatePointer = currState.getNextLiteratedStateCollection().get(literalCurrChar);
				tempLiteratedStatePointer.setState(new State(currState,currChar, root));
				currState = goToMatch(currState, currChar);
			}
			
			if(i==keyword.length()-1){
				currState.setFullKeywordHashCode(keyword.hashCode());
				if(tempLiteratedStatePointer.getFullKeywordPointerList()==null){
					tempLiteratedStatePointer.setFullKeywordPointerList(new ArrayList<>());
				}
				tempLiteratedStatePointer.getFullKeywordPointerList().add(keyword.hashCode());
			}
		}
		
	}
	
	public void prepareNTrie(int multicharNumber){
		
		//HashMap<String, State> originalAncestors = new HashMap<>(root.getNextStateCollection());// ambil original ancestor
		HashMap<String, LiteratedStatePointer> originalAncestors = new HashMap<>(root.getNextLiteratedStateCollection());// ambil original ancestor
		
		LinkedList<State> oriStateQueue = walkthroughTrie();
		LinkedList<State> oriStateQueueForPhase1 = new LinkedList<>(oriStateQueue); 
		LinkedList<State> oriStateQueueForPhase2 = new LinkedList<>(oriStateQueue); 
		
		State tempState; //aka Si, placeholder for loop.
		
		// [Phase 1] Enhance original trie as preparation before creating derviation node
		while(!oriStateQueueForPhase1.isEmpty()){
			tempState=oriStateQueueForPhase1.pop();
			//create a copy so the hashmap can be added, avoid concurrency problem
			//HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
			HashMap<String,LiteratedStatePointer> cpOriChildStateHM = new HashMap<>(tempState.getNextLiteratedStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
			
			for (String key : originalAncestors.keySet()) { //menambahkan originalAncestors lv1
				if (!cpOriChildStateHM.containsKey(key)) {
					cpOriChildStateHM.put(key, originalAncestors.get(key));
				}
			}
			
			//add negative original node as a regex pattern
			String negativeOriginalPattern = "[^";
			for (String key : cpOriChildStateHM.keySet()) {
				negativeOriginalPattern += key;				
			}
			negativeOriginalPattern+="]";
			//point negative regex to root
			cpOriChildStateHM.put(negativeOriginalPattern, literatedRoot);
			
			tempState.getNextLiteratedStateCollection().putAll(cpOriChildStateHM);
		}
		
		// [Phase 2] Start creating derivation node
		while(!oriStateQueueForPhase2.isEmpty()) {
			tempState=oriStateQueueForPhase2.pop();
			//create a copy so the hashmap can be added, avoid concurrency problem
			HashMap<String,LiteratedStatePointer> cpOriChildStateHM = new HashMap<>(tempState.getNextLiteratedStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
			for (int m=0; m<multicharNumber-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali
				for (LiteratedStatePointer lStateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase
					State stateNXi = lStateNXi.getState();
					HashMap<String, LiteratedStatePointer> lStateNXiChilds = stateNXi.getNextLiteratedStateCollection();//contoh anak node level 1
					for (LiteratedStatePointer lStateNXj : lStateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
						State stateNXj = lStateNXj.getState();
						String newPattern = Pattern.quote(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter());
						tempState.getNextLiteratedStateCollection().put(newPattern, new LiteratedStatePointer());//add to main trie
						tempState.getNextLiteratedStateCollection().get(newPattern).setState(stateNXj);
						tempState.getNextLiteratedStateCollection().get(newPattern).setFullKeywordPointerList(new ArrayList<>());
						tempState.getNextLiteratedStateCollection().get(newPattern).getFullKeywordPointerList().add(stateNXi.getFullKeywordHashCode());
						tempState.getNextLiteratedStateCollection().get(newPattern).getFullKeywordPointerList().add(stateNXj.getFullKeywordHashCode());
					}//END 5th phase
				}//END 4th phase
			}//END 3rd phase
		}//END 2nd Phase
	}//END
	
	/**used to convert the trie state as a linkedlist).
	 * Only use this on original trie. Usage on derivated trie will cause loop. 
	 * This includes root state.*/
	private LinkedList<State> walkthroughTrie(){
		LinkedList<State> queue = new LinkedList<State>(); //a linked list is needed for BFS
		LinkedList<State> resultQueue = new LinkedList<State>(); //a linked list is needed for BFS
		resultQueue.add(root);
		for (LiteratedStatePointer lStatePointer : root.getNextLiteratedStateCollection().values()) {
			queue.add(lStatePointer.getState());
			resultQueue.add(lStatePointer.getState());
		}
		
		State tempState;
		
		while(!queue.isEmpty()){
			tempState = queue.pop(); //pop node and get the childrens
			for (LiteratedStatePointer lStatePointer : tempState.getNextLiteratedStateCollection().values()) {
				queue.add(lStatePointer.getState());
				resultQueue.add(lStatePointer.getState());
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
				lineNumberCounter++;
				columnNumberCounter=1;
			}

			bufferStr1 = ""+inputString.charAt(i);
			inputStringBuffer = inputStringBuffer + bufferStr1;
			
			if(inputStringBuffer.length()==2 || i==inputStringLastPosition){
				
				algoStart=System.currentTimeMillis();
				
				bufferStr0 = ""+inputStringBuffer.charAt(0);
				
//				while (goToMatch(currState, inputStringBuffer)==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
//					currState= failFrom(currState);
//				}
				if(goToMatch(currState, inputStringBuffer)!=null){
					currState = goToMatch(currState, inputStringBuffer); //set the current node to the result of go to function
					prepareOutput(currState.getOriginLiteratedStatePointer(), lineNumberCounter, columnNumberCounter);
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
	private void prepareOutput(LiteratedStatePointer lStatePointer,int lineNumber, int endColumnNumber){
//		if(state.isHasFullKeyword()==true){//jika currNode = fullword
//			if(inputString.substring(endColumnNumber-1-(state.getFullKeyword().length()), endColumnNumber-1).compareToIgnoreCase(state.getFullKeyword())!=0){
//				endColumnNumber--;
//			}
//			outputList.add(new Output(state.getFullKeyword(), lineNumber, endColumnNumber-(state.getFullKeyword().length()), endColumnNumber-1));
//		}
		/*
		while(!failFrom(state).equals(root)){//jika state tersebut punya fail node yang bukan root
			state = failFrom(state);
			if(state.getFullKeyword()!=null){//jika failState == fullword
				if(inputString.substring(endColumnNumber-1-(state.getFullKeyword().length()), endColumnNumber-1).compareToIgnoreCase(state.getFullKeyword())!=0){
					endColumnNumber--;
				}
				outputList.add(new Output(state.getFullKeyword(), lineNumber, endColumnNumber-(state.getFullKeyword().length()), endColumnNumber-1));
			}
		}*/
		
		if(lStatePointer.getFullKeywordPointerList()!=null){
			for (Integer keywordHashCode : lStatePointer.getFullKeywordPointerList()) {
				outputList.add(new Output(AhoCorasick.fullKeywordMap.get(keywordHashCode), lineNumber, endColumnNumber-(AhoCorasick.fullKeywordMap.get(keywordHashCode).length()), endColumnNumber-1));
			}
		}
	}
	
}
