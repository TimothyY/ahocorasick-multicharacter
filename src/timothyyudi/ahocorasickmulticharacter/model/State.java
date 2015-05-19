package timothyyudi.ahocorasickmulticharacter.model;

import java.util.HashMap;
import java.util.HashSet;

public class State {

	String stateContentCharacter;
	String fullKeyword;
	State parent, failState;
	HashMap<String, State> nextStateCollection;
	
	/**Called when root is created.*/
	public State(){
		super();
		this.parent = null;
		this.stateContentCharacter = null;
		this.failState = null;
		this.fullKeyword = null;
		this.nextStateCollection = new HashMap<>();
	}

	/**Called each time a new state is created*/
	public State(State parent, String stateContentCharacter, State failState){
		super();
		this.parent = parent;
		this.stateContentCharacter = stateContentCharacter;
		this.failState = failState;
		this.fullKeyword = null;
		this.nextStateCollection = new HashMap<>();
	}

	public String getStateContentCharacter() {
		return stateContentCharacter;
	}

	public void setStateContentCharacter(String stateContentCharacter) {
		this.stateContentCharacter = stateContentCharacter;
	}

	public String getFullKeyword() {
		return fullKeyword;
	}

	public void setFullKeyword(String fullKeyword) {
		this.fullKeyword = fullKeyword;
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
		
}
