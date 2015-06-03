//package timothyyudi.ahocorasickmulticharacter.controller;
//
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedList;
//
//import timothyyudi.ahocorasickmulticharacter.model.Output;
//import timothyyudi.ahocorasickmulticharacter.model.State;
//
//public class AhoCorasick_BAK {
//
//	State root = new State();
//	State currState;
//	int keywordInsertionCounter, lineNumberCounter;
//	String tempOutputStr = "";
//	String reversedTempOutputStr = "";
//	HashMap<String, Output> outputMap = new HashMap<>();
//	
//	ArrayList<Output> outputList = new ArrayList<>();
//	State destState;
//	
//	static boolean isCaseSensitive = false;
//	HashMap<Integer,String> globalKeywordMap = new HashMap<>();
//	
//	int columnNumberCounter;
//	
//	String bufferStr0, bufferStr1;
//	long ahoCorasickTimeTotal=0;
//	long ahoCorasickTimeFragment=0;
//	long algoStart, algoEnd;
//	
////	/**A function to match input string against constructed AhoCorasick trie*/
////	public void nPatternMatching(String inputString){
////		currState = root;
////		lineNumberCounter=1;
////		for (int i = 0; i < inputString.length(); i++) { //as long as there is an input
////			if(inputString.charAt(i)=='\n')lineNumberCounter++;
////			while (goTo(currState, Character.toString(inputString.charAt(i)))==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
////				currState= failFrom(currState);
////			}
////			if(goTo(currState, Character.toString(inputString.charAt(i)))!=null){
////				currState = goTo(currState, Character.toString(inputString.charAt(i))); //set the current node to the result of go to function
////				prepareOutput(currState,lineNumberCounter, i);
////			}
////		}
////	}
//	
//	/**A function to match input string against constructed AhoCorasick trie*/
//	public void nPatternMatching(String inputString){
//		currState = root;
//		lineNumberCounter=1;
//		columnNumberCounter=0;
//		String inputStringBuffer="";
//		int inputStringLength = inputString.length();
//		int inputStringLastPosition = inputStringLength -1;
//		
//		for (int i = 0; i < inputStringLength; i++) { //as long as there is an input
//			
//			columnNumberCounter++;
//			if(inputString.charAt(i)=='\n'){
//				lineNumberCounter++;
//				columnNumberCounter=1;
//			}
//			
//			inputStringBuffer = inputStringBuffer + inputString.charAt(i);
//			
//			if(inputStringBuffer.length()==2 || i==inputStringLastPosition){
//				
//				algoStart=System.currentTimeMillis();
//				
//				while (goTo(currState, inputStringBuffer)==null&&!currState.equals(root)) { //repeat fail function as long goTo function is failing
//					currState= failFrom(currState);
//				}
//				if(goTo(currState, inputStringBuffer)!=null){
//					currState = goTo(currState, inputStringBuffer); //set the current node to the result of go to function
//					prepareOutput(currState, lineNumberCounter, columnNumberCounter);
//				}
//				
//				//apakah butuh shiftExtend?
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
//		writeAhoCorasickTime(ahoCorasickTimeTotal);
//	}
//	
//	/**A function to move from 1 node of a trie to the others based on next input character*/
//	private State goTo(State node, String nextInputChar){
//		return node.getNextStateCollection().get(nextInputChar);
//	}
//	
//	/**A function to move from 1 node of a trie to it's fail node*/
//	private State failFrom(State node){
//		return node.getFailState();
//	}
//	
//	/**Prepare AhoCorasick goto function/ successful state of AhoCorasick trie*/
//	public void prepareGoToFunction(ArrayList<String> keywords){
//		//State currNode = root;
//		for (int i = 0; i < keywords.size(); i++) {
//			enterKeyword(keywords.get(i));
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
//				currState.setFullKeyword(keyword);
//			}
//			keywordInsertionCounter++;
//		}
//	}
//	
//	/**Create the fail fall back state of AhoCorasick trie*/
//	public void prepareFailFromFunction(){
//		LinkedList<State> queue = new LinkedList<State>(); //a linked list is needed for BFS
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
//	public void prepareNTrie(int n){
//		LinkedList<State> queueNXSET = walkthroughTrie();
//		State tempState; //aka Si
//		while(!queueNXSET.isEmpty()) {//BEGIN 2nd phase
//			tempState=queueNXSET.pop();
//			HashMap<String, State> queueNSET= new HashMap<>(); //all 1 char transition of Si in nxSet
//			queueNSET=tempState.getNextStateCollection();//jika curr state =0 maka ini adalah node lvl 1
//			
//			HashMap<String,State> cpQueueNSET = new HashMap<>(queueNSET); //create a copy so the hashmap can be added, avoid concurrency problem
//			for (int m=0; m<n-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=3 maka akan jalan 2 kali//untuk membuat node 2 char dan node 3 char di curr state0
//				HashMap<String, State> queueTMPSET=new HashMap<>(); //clear queuetmpSet
//				//for(State stateNXi : queueNSET.values()) {//BEGIN 4th phase
//				for(State stateNXi : cpQueueNSET.values()) {//BEGIN 4th phase
//					HashMap<String, State> queueNX_ST = stateNXi.getNextStateCollection();//contoh anak node level 1
//					for (State stateNXj : queueNX_ST.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
//						//State stateNEW_TR= new State(stateNXi.getParent(), stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj.getFailState()); //Concatenate state child with grandchild
//						//stateNEW_TR.setNextStateCollection(stateNXj.getNextStateCollection()); //set next state collection of new state
//						stateNXi.getParent().getNextStateCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add to main trie
//						queueTMPSET.put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add for next derivation loop.
//					}//END 5th phase
//				}//END 4th phase
//				//queueNSET = queueTMPSET;
//				cpQueueNSET = queueTMPSET;
//			}//END 3rd phase
//			//[implementation]root.setNextStateCollection(nRoot.getNextStateCollection().putAll(queueNSET));
//			//TRSET=TRSET U NSET //merge with root? is this needed?
//		}//END 2nd Phase
//	}//END
//	
//	/**used to prepare the trie state as a linkedlist*/
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
//			for (State state: tempState.getNextStateCollection().values()) { //implementation differ based on nextStateCollection data structure
//				queue.add(state);
//				resultQueue.add(state);
//			}
//		}
//		return resultQueue;
//	}
//	
//	/**used to print the trie state as a linkedlist
//	 * @throws UnsupportedEncodingException 
//	 * @throws FileNotFoundException */
//	public void walkthroughTrieAndPrint() throws FileNotFoundException, UnsupportedEncodingException{
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
//			for (State state: tempState.getNextStateCollection().values()) { //implementation differ based on nextStateCollection data structure
//				queue.add(state);
//				resultQueue.add(state);
//			}
//		}
//		
//		tempOutputStr="";
//		while(!resultQueue.isEmpty()){
//			tempState = resultQueue.pop();
//			tempOutputStr=tempOutputStr+"->"+tempState.getStateContentCharacter();
//		}
//		PrintWriter writer = new PrintWriter("c:/temp/AhoCorasickNTrieOutput.txt", "UTF-8");
//		writer.println(tempOutputStr);
//		writer.close();
//		tempOutputStr="";
//	}
//	
//	/**prepare output for the matching keywords found*/
//	private void prepareOutput(State state,int lineNumber, int endPoint){
//		if(state.getLocalKeywordSet()!=null){//jika currNode = fullword
//			for (Integer hash : state.getLocalKeywordSet()) {
//				outputList.add(new Output(globalKeywordMap.get(hash), lineNumber, endPoint-(globalKeywordMap.get(hash).length()), endPoint));
//			}
//		}
//		
//		while(!failFrom(state).equals(root)){//jika state tersebut punya fail node yang bukan root
//			state = failFrom(state);
//			if(state.getLocalKeywordSet()!=null){//jika failState == fullword
//				for (Integer hash : state.getLocalKeywordSet()) {
//					outputList.add(new Output(globalKeywordMap.get(hash), lineNumber, endPoint-(globalKeywordMap.get(hash).length()), endPoint));				
//				}
//			}
//		}
//	}
//	
////	/**Trace the ancestor of a node and print it sequentially*/
////	private void traceAncestorAndPrint(State state, int lineNumber, int endPoint){
////		while(!state.equals(root)){
////			tempOutputStr=tempOutputStr+state.getStateContentCharacter();
////			state=state.getParent();
////		}
////		reversedTempOutputStr = new StringBuilder(tempOutputStr).reverse().toString();
////		outputMap.put(reversedTempOutputStr+"l"+lineNumber+"c"+endPoint, new Output(reversedTempOutputStr, lineNumber, endPoint-reversedTempOutputStr.length()+2, endPoint+1));
////	}
//	
//	/**Write output to output.txt
//	 * @throws UnsupportedEncodingException 
//	 * @throws FileNotFoundException */
//	public void writeOutput() throws FileNotFoundException, UnsupportedEncodingException{
//		PrintWriter writer = new PrintWriter("src/timothyyudi/ahocorasick/asset/AhoCorasickOutput.txt", "UTF-8");
//		for (Output output : outputMap.values()) {
//			writer.println("Found "+output.getOutputString()+" @line: "+output.getLineNumber()+"("+output.getOutputStartPoint()+"-"+output.getOutputEndPoint()+")");
//		}
//		writer.close();
//	}
//	
//	public void writeAhoCorasickTime(long ahoCorasickTime){
//		System.out.println("[TRUE] AhoCorasick Time: "+ahoCorasickTime+" ms");
//	}
//	
//}