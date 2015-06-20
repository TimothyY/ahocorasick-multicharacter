package timothyyudi.ahocorasickmulticharacter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import timothyyudi.ahocorasickmulticharacter.model.LiteratedStatePointer;
import timothyyudi.ahocorasickmulticharacter.model.State;

public class Backup {

//	/**A function to move from 1 node of a trie to the others based on next input character*/
//	private State goTo(State node, String nextInputChar){
//		return node.getNextStateCollection().get(nextInputChar);
//	}

//	/**A function to move from 1 node of a trie to it's fail node*/
//	private State failFrom(State node){
//		return node.getFailState();
//	}
	
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
//		
//		// [Phase 1] Enhance original trie as preparation before creating derviation node
//		while(!oriStateQueueForPhase1.isEmpty()){
//			tempState=oriStateQueueForPhase1.pop();
//			//create a copy so the hashmap can be added, avoid concurrency problem
//			//HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
//			HashMap<String,LiteratedStatePointer> cpOriChildStateHM = new HashMap<>(tempState.getNextLiteratedStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
//			
//			for (String key : originalAncestors.keySet()) { //menambahkan originalAncestors
//				if (!cpOriChildStateHM.containsKey(key)) {
//					cpOriChildStateHM.put(key, originalAncestors.get(key));
//				}
//			}
//			
//			//add negative original node as a regex pattern
//			String negativeOriginalPattern = "[^";
//			for (String key : cpOriChildStateHM.keySet()) {
//				negativeOriginalPattern += key;				
//			}
//			negativeOriginalPattern+="]";
//			//point negative regex to root
//			cpOriChildStateHM.put(negativeOriginalPattern, root);
//			
//			tempState.getNextStateCollection().putAll(cpOriChildStateHM);
//		}
//		
//		// [Phase 2] Start creating derivation node
//		while(!oriStateQueueForPhase2.isEmpty()) {
//			tempState=oriStateQueueForPhase2.pop();
//			//create a copy so the hashmap can be added, avoid concurrency problem
//			HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
//			for (int m=0; m<multicharNumber-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali
//				for (State stateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase	
//					HashMap<String, State> stateNXiChilds = stateNXi.getNextStateCollection();//contoh anak node level 1
//					for (State stateNXj : stateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
//						stateNXi.getParent().getNextStateCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add to main trie
//						stateNXi.getParent().getNextOutputCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), new ArrayList<String>());
//						stateNXi.getParent().getNextOutputCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).addAll(stateNXi.getNextOutputCollection().get(stateNXi.getStateContentCharacter()));
//						stateNXi.getParent().getNextOutputCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).addAll(stateNXj.getNextOutputCollection().get(stateNXj.getStateContentCharacter()));
//						if(stateNXi.getParent().getNextStateCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).getOutputCollection().size()){
//							stateNXi.getParent().getNextStateCollection().get(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter()).setHasFullKeyword(true);
//						}
//					}//END 5th phase
//				}//END 4th phase
//			}//END 3rd phase
//		}//END 2nd Phase
//		
//		/*
//		// [Phase 2] Start creating derivation node
//		while(!oriStateQueueForPhase2.isEmpty()) {
//					
//			tempState=oriStateQueueForPhase2.pop();
//			//create a copy so the hashmap can be added, avoid concurrency problem
//			HashMap<String,State> cpOriChildStateHM = new HashMap<>(tempState.getNextStateCollection()); //jika curr state =0 maka ini adalah node lvl 1
//			
//			for (int m=0; m<n-1; m++) {//loop match with desired n //BEGIN 3rd phase //jika n=2 maka akan jalan 1 kali
//				
//				HashMap<String, State> queueTMPSET = new HashMap<>(); //clear queuetmpSet
//						
//				for (State stateNXi : cpOriChildStateHM.values()) {//BEGIN 4th phase
//					
//					HashMap<String, State> stateNXiChilds = stateNXi.getNextStateCollection();//contoh anak node level 1
//					for (State stateNXj : stateNXiChilds.values()) {//BEGIN 5th phase//ada 2 node baru dengan multichar di queuetmpset
//						//State stateNEW_TR= new State(stateNXi.getParent(), stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj.getFailState()); //Concatenate state child with grandchild
//						//stateNEW_TR.setNextStateCollection(stateNXj.getNextStateCollection()); //set next state collection of new state
//						stateNXi.getParent().getNextStateCollection().put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add to main trie
//						queueTMPSET.put(stateNXi.getStateContentCharacter()+stateNXj.getStateContentCharacter(), stateNXj);//add for next derivation loop.
//					}//END 5th phase
//				}//END 4th phase
//				//queueNSET = queueTMPSET;
//				cpOriChildStateHM = queueTMPSET; //prepare for next derivation loop
//			}//END 3rd phase
//			//[implementation]root.setNextStateCollection(nRoot.getNextStateCollection().putAll(queueNSET));
//			//TRSET=TRSET U NSET //merge with root? is this needed? apprently not since it was directly putted there when formed.
//		}//END 2nd Phase
//		*/
//	}//END
}
