package timothyyudi.ahocorasickmulticharacter.model;

import java.util.HashMap;
import java.util.HashSet;

public class State {

	String stateContentCharacter;
	State parent, failState;
	HashMap<String, State> nextStateCollection;
	HashSet<String> localKeywordSet;
//	HashMap<String, State> componentState; //to handle preparing output from skipeed state

	/**Called when root is created.*/
	public State(){
		super();
		this.parent = null;
		this.stateContentCharacter = null;
		this.failState = null;
		this.nextStateCollection = new HashMap<>();
		this.localKeywordSet = null;
//		this.componentState = null;
	}

	/**Called each time a new state is created*/
	public State(State parent, String stateContentCharacter, State failState){
		super();
		this.parent = parent;
		this.stateContentCharacter = stateContentCharacter;
		this.failState = failState;
		this.nextStateCollection = new HashMap<>();
		this.localKeywordSet = null;
	}

	public String getStateContentCharacter() {
		return stateContentCharacter;
	}

	public void setStateContentCharacter(String stateContentCharacter) {
		this.stateContentCharacter = stateContentCharacter;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public State getFailState() {
		return failState;
	}

	public void setFailState(State failState) {
		this.failState = failState;
	}

	public HashMap<String, State> getNextStateCollection() {
		return nextStateCollection;
	}

	public void setNextStateCollection(HashMap<String, State> nextStateCollection) {
		this.nextStateCollection = nextStateCollection;
	}
		
	public HashSet<String> getLocalKeywordSet() {
		return localKeywordSet;
	}

	public void setLocalKeywordSet(HashSet<String> localKeywordSet) {
		this.localKeywordSet = localKeywordSet;
	}
	
}
