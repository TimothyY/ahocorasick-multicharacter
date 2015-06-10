package timothyyudi.ahocorasickmulticharacter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
	private State goToMatch(State node, String nextInputChar, String firstNextInputChar, String secondNextInputChar){
		State destState=node.getNextStateCollection().get(nextInputChar);
		if(destState==null){
			destState=node.getNextStateCollection().get(firstNextInputChar);
			if(destState!=null){
				if(destState.getFullKeyword()==null){
					destState=null;
				}	
			}
		}		
		if(destState==null){
			destState=node.getNextStateCollection().get(secondNextInputChar);
		}
		return destState;
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

		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))!=null){ //while state already exist then go there.
			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
			keywordInsertionCounter++;
		}
	
		while(keywordInsertionCounter<keyword.length() && goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)))==null){ //while state doesnt exist then create new node and go there
			currState.getNextStateCollection().put(Character.toString(keyword.charAt(keywordInsertionCounter)), new State(currState, Character.toString(keyword.charAt(keywordInsertionCounter)), root));
			currState = goTo(currState, Character.toString(keyword.charAt(keywordInsertionCounter)));
			if(keywordInsertionCounter==keyword.length()-1){
				currState.setFullKeyword(keyword);
			}
			keywordInsertionCounter++;
		}
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

	public void prepareNTrie(int n){
		
		LinkedList<State> oriStateQueue = walkthroughTrie();
		State tempState; //aka Si
		
		while(!oriStateQueue.isEmpty()) {//BEGIN 2nd phase
			
			tempState=oriStateQueue.pop();
			HashMap<String, State> oriChildStateQueue = tempState.getNextStateCollection();//jika curr state =0 maka ini adalah node lvl 1
			HashMap<String,State> cpOriChildStateQueue = new HashMap<>(oriChildStateQueue); //create a copy so the hashmap can be added, avoid concurrency problem
			
			for (int m=0; m<n-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali
				
				HashMap<String, State> queueTMPSET=new HashMap<>(); //clear queuetmpSet
				
				for(State stateNXi : cpOriChildStateQueue.values()) {//BEGIN 4th phase
					
					HashMap<String, State> stateNXiChilds = stateNXi.getNextStateCollection();//contoh anak node level 1
					for (State stateNXj : stateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
						//State stateNEW_TR= new State(stateNXi.getParent(), stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj.getFailState()); //Concatenate state child with grandchild
						//stateNEW_TR.setNextStateCollection(stateNXj.getNextStateCollection()); //set next state collection of new state
						stateNXi.getParent().getNextStateCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add to main trie
						queueTMPSET.put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add for next derivation loop.
					}//END 5th phase
				}//END 4th phase
				//queueNSET = queueTMPSET;
				cpOriChildStateQueue = queueTMPSET; //prepare for next derivation loop
			}//END 3rd phase
			//[implementation]root.setNextStateCollection(nRoot.getNextStateCollection().putAll(queueNSET));
			//TRSET=TRSET U NSET //merge with root? is this needed? apprently not since it was directly putted there when formed.
		}//END 2nd Phase
	}//END
	
	/**used to prepare the trie state as a linkedlist*/
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
				
				while (goToMatch(currState, inputStringBuffer, bufferStr0, bufferStr1)==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
					currState= failFrom(currState);
				}
				if(goToMatch(currState, inputStringBuffer, bufferStr0, bufferStr1)!=null){
					currState = goToMatch(currState, inputStringBuffer, bufferStr0, bufferStr1); //set the current node to the result of go to function
//					System.out.println("Matching "+inputStringBuffer+" & Gone to "+currState.getStateContentCharacter());
					prepareOutput(currState, lineNumberCounter, columnNumberCounter);
				}
//				else {
//					shiftToExtTrie(currState, bufferStr1);
//				}
				
				//apakah butuh shiftExtend?
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
	
	private void shiftToExtTrie(State state, String lastChar) {
		if(goTo(state, lastChar)!=null)
		this.currState = goTo(state, lastChar);
	}
	
	/**prepare output for the matching keywords found*/
	private void prepareOutput(State state,int lineNumber, int endColumnNumber){
		if(state.getFullKeyword()!=null){//jika currNode = fullword
			if(inputString.substring(endColumnNumber-1-(state.getFullKeyword().length()), endColumnNumber-1).compareToIgnoreCase(state.getFullKeyword())!=0){
				endColumnNumber--;
			}
			outputList.add(new Output(state.getFullKeyword(), lineNumber, endColumnNumber-(state.getFullKeyword().length()), endColumnNumber-1));
		}
		
		while(!failFrom(state).equals(root)){//jika state tersebut punya fail node yang bukan root
			state = failFrom(state);
			if(state.getFullKeyword()!=null){//jika failState == fullword
				if(inputString.substring(endColumnNumber-1-(state.getFullKeyword().length()), endColumnNumber-1).compareToIgnoreCase(state.getFullKeyword())!=0){
					endColumnNumber--;
				}
				outputList.add(new Output(state.getFullKeyword(), lineNumber, endColumnNumber-(state.getFullKeyword().length()), endColumnNumber-1));
			}
		}
	}
	
}
