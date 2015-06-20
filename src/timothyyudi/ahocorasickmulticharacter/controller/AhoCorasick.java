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

import timothyyudi.ahocorasickmulticharacter.model.Output;
import timothyyudi.ahocorasickmulticharacter.model.State;

public class AhoCorasick {

	State root = new State();
	State currState;
	int keywordInsertionCounter, lineNumberCounter, columnNumberCounter;
	
	static boolean isCaseSensitive = false;
	String bufferStr0, bufferStr1;
	
	
	
	
	public static ArrayList<Output> outputList = new ArrayList<>();
	
	long ahoCorasickTimeTotal=0;
	long ahoCorasickTimeFragment=0;
	long algoStart, algoEnd;
	
	String inputString;
	
	/**A function to move from 1 node of a trie to the others based on next input character*/
	private State goTo(State node, String nextInputChar){
		return node.getNextStateCollection().get(nextInputChar);
	}
	
	/**A function to move from 1 node of a trie to the others based on next input character*/
//	private State goToMatch(State node, String nextInputChar, String firstNextInputChar){
//	private State goToMatch(State node, String nextInputChar, String firstNextInputChar, String secondNextInputChar){
	private State goToMatch(State originNode, String nextInputChar){
		HashMap<String, State> tempACHM = originNode.getNextStateCollection();
		for (String key : tempACHM.keySet()) {
			if (nextInputChar.matches(key)) {
				return originNode.getNextStateCollection().get(key);
			}
		}
		return null;
//		State destState=node.getNextStateCollection().get(nextInputChar);
//		if(destState==null){
//			destState=node.getNextStateCollection().get(firstNextInputChar);
//			if(destState!=null){
//				if(destState.getFullKeyword()==null){
//					destState=null;
//				}	
//			}
//		}		
//		if(destState==null){
//			destState=node.getNextStateCollection().get(secondNextInputChar);
//		}
//		return destState;
	}
	
	/**A function to move from 1 node of a trie to it's fail node*/
	private State failFrom(State node){
		return node.getFailState();
	}
	
	/**Prepare AhoCorasick goto function/ successful state of AhoCorasick trie*/
	public void prepareGoToFunction(ArrayList<String> keywords){
		//State currNode = root;
		for (int i = 0; i < keywords.size(); i++) {
			enterKeyword(keywords.get(i));
		}
	}
	
	/**insert keywords to trie*/
	private void enterKeyword(String keyword){
		currState = root;
		keywordInsertionCounter = 0;
		String currChar="";
		String literalCurrChar="";
		
		for (int i = 0; i < keyword.length(); i++) {
			
			currChar=Character.toString(keyword.charAt(keywordInsertionCounter));
			if(goToMatch(currState, currChar)!=null){
				currState=goToMatch(currState, currChar);
			}else{
				literalCurrChar = Pattern.quote(currChar);
				currState.getNextStateCollection().put(literalCurrChar, new State(currState,literalCurrChar, root));
				currState = goToMatch(currState, currChar);
			}
			
			if(i==keyword.length()-1){
//				if(currState.getNextOutputCollection()==null){
//					currState.setNextOutputCollection(new HashMap<String,ArrayList<String>>());
//				}
//				if(currState.getNextOutputCollection().get(literalCurrChar)==null){
//					currState.getNextOutputCollection().put(literalCurrChar, new ArrayList<String>());  
//				}
//				currState.getNextOutputCollection().get(literalCurrChar).add(keyword);
				
				currState.setHasFullKeyword(true);
				if(currState.getOutputCollection()==null){
					currState.setOutputCollection(new ArrayList<String>());
				}
				currState.getOutputCollection().add(keyword);
			}
		}
		
		/*
		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))!=null){ //while state already exist then go there.
			currChar = Character.toString(keyword.charAt(keywordInsertionCounter));
			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
			keywordInsertionCounter++;
		}
	
		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))==null){ //while state doesnt exist then create new node and go there
			currChar = Character.toString(keyword.charAt(keywordInsertionCounter));
			currState.getNextStateCollection().put(Character.toString(keyword.charAt(keywordInsertionCounter)), new State(currState,Character.toString(keyword.charAt(keywordInsertionCounter)), root));
			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
			if(keywordInsertionCounter==keyword.length()-1){
				currState.setFullKeyword(keyword);
			}
			keywordInsertionCounter++;
		}
		*/
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

	public void prepareNTrie(int multicharNumber){
		
		HashMap<String, State> originalAncestors = new HashMap<>(root.getNextStateCollection());// ambil original ancestor
		
		LinkedList<State> oriStateQueue = walkthroughTrie();
		LinkedList<State> oriStateQueueForPhase1 = new LinkedList<>(oriStateQueue); 
		LinkedList<State> oriStateQueueForPhase2 = new LinkedList<>(oriStateQueue); 
		
		State tempState; //aka Si, placeholder for loop.
		
		// [Phase 1] Enhance original trie as preparation before creating derviation node
		while(!oriStateQueueForPhase1.isEmpty()){
			tempState=oriStateQueueForPhase1.pop();
			//create a copy so the hashmap can be added, avoid concurrency problem
			HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
			
			for (String key : originalAncestors.keySet()) { //menambahkan originalAncestors
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
			cpOriChildStateHM.put(negativeOriginalPattern, root);
			
			tempState.getNextStateCollection().putAll(cpOriChildStateHM);
		}
		
		// [Phase 2] Start creating derivation node
		while(!oriStateQueueForPhase2.isEmpty()) {
			tempState=oriStateQueueForPhase2.pop();
			//create a copy so the hashmap can be added, avoid concurrency problem
			HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
			for (int m=0; m<multicharNumber-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali
				for (State stateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase	
					HashMap<String, State> stateNXiChilds = stateNXi.getNextStateCollection();//contoh anak node level 1
					for (State stateNXj : stateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
						stateNXi.getParent().getNextStateCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add to main trie
						stateNXi.getParent().getNextOutputCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), new ArrayList<String>());
						stateNXi.getParent().getNextOutputCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).addAll(stateNXi.getNextOutputCollection().get(stateNXi.getStateContentCharacter()));
						stateNXi.getParent().getNextOutputCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).addAll(stateNXj.getNextOutputCollection().get(stateNXj.getStateContentCharacter()));
						if(stateNXi.getParent().getNextStateCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).getOutputCollection().size()){
							stateNXi.getParent().getNextStateCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).setHasFullKeyword(true);
						}
					}//END 5th phase
				}//END 4th phase
			}//END 3rd phase
		}//END 2nd Phase
		
		/*
		// [Phase 2] Start creating derivation node
		while(!oriStateQueueForPhase2.isEmpty()) {
					
			tempState=oriStateQueueForPhase2.pop();
			//create a copy so the hashmap can be added, avoid concurrency problem
			HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
			
			for (int m=0; m<n-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali
				
				HashMap<String, State> queueTMPSET = new HashMap<>(); //clear queuetmpSet
						
				for (State stateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase
					
					HashMap<String, State> stateNXiChilds = stateNXi.getNextStateCollection();//contoh anak node level 1
					for (State stateNXj : stateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
						//State stateNEW_TR= new State(stateNXi.getParent(), stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj.getFailState()); //Concatenate state child with grandchild
						//stateNEW_TR.setNextStateCollection(stateNXj.getNextStateCollection()); //set next state collection of new state
						stateNXi.getParent().getNextStateCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add to main trie
						queueTMPSET.put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add for next derivation loop.
					}//END 5th phase
				}//END 4th phase
				//queueNSET = queueTMPSET;
				cpOriChildStateHM = queueTMPSET; //prepare for next derivation loop
			}//END 3rd phase
			//[implementation]root.setNextStateCollection(nRoot.getNextStateCollection().putAll(queueNSET));
			//TRSET=TRSET U NSET //merge with root? is this needed? apprently not since it was directly putted there when formed.
		}//END 2nd Phase
		*/
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
			for (State state: tempState.getNextStateCollection().values()) { //implementation differ based on nextStateCollection data structure
				queue.add(state);
				resultQueue.add(state);
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
				
				while (goToMatch(currState, inputStringBuffer)==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
					currState= failFrom(currState);
				}
				if(goToMatch(currState, inputStringBuffer)!=null){
					currState = goToMatch(currState, inputStringBuffer); //set the current node to the result of go to function
					prepareOutput(currState, lineNumberCounter, columnNumberCounter);
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
	private void prepareOutput(State state,int lineNumber, int endColumnNumber){
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
		
		if(state.isHasFullKeyword()==true){
			for (String keyword : state.getOutputCollection()) {
				outputList.add(new Output(keyword, lineNumber, endColumnNumber-(keyword.length()), endColumnNumber-1));
			}
		}
	}
	
}
