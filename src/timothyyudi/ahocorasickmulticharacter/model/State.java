package timothyyudi.ahocorasickmulticharacter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class State {

	String stateContentCharacter;
	State parent, failState;
	HashMap<String, State> nextStateCollection;
	HashMap<String, ArrayList<String>> nextOutputCollection;
	boolean hasFullKeyword;

	/**Called when root is created.*/
	public State(){
		super();
		this.parent = null;
		this.stateContentCharacter = null;
		this.failState = null;
		this.nextStateCollection = new HashMap<>();
		this.nextOutputCollection = null;
		this.hasFullKeyword = false;
	}

	/**Called each time a new state is created*/
	public State(State parent, String stateContentCharacter, State failState){
		super();
		this.parent = parent;
		this.stateContentCharacter = stateContentCharacter;
		this.failState = failState;
		this.nextStateCollection = new HashMap<>();
		this.nextOutputCollection = null;
		this.hasFullKeyword = false;
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

	public HashMap<String, ArrayList<String>> getNextOutputCollection() {
		return nextOutputCollection;
	}

	public void setNextOutputCollection(HashMap<String, ArrayList<String>> nextOutputCollection) {
		this.nextOutputCollection = nextOutputCollection;
	}

	public boolean isHasFullKeyword() {
		return hasFullKeyword;
	}

	public void setHasFullKeyword(boolean hasFullKeyword) {
		this.hasFullKeyword = hasFullKeyword;
	}
		
	
}
