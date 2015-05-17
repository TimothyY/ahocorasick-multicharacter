package timothyyudi.ahocorasickmulticharacter.model;

import java.util.HashMap;

public class State {

	//char stateContentCharacter;
	String stateContentCharacter;
	boolean fullWord;
	State parent, failState;
	HashMap<String, State> nextStateCollection;
	
	/**Called when root is created.*/
	public State(){
		super();
		//this.stateContentCharacter = '\u0000';
		this.stateContentCharacter = null;
		this.fullWord = false;
		this.parent = this;
		this.nextStateCollection = new HashMap<String, State>();
		this.failState = this;
	}
	
	/**Called each time a new state is created*/
	public State(State parent, String stateContentCharacter, State failState){
		super();
		this.stateContentCharacter = stateContentCharacter;
		this.fullWord = false;
		this.parent = parent;
		this.nextStateCollection = new HashMap<String, State>();
		this.failState = failState;
	}

	public String getStateContentCharacter() {
		return stateContentCharacter;
	}

	public void setStateContentCharacter(String stateContentCharacter) {
		this.stateContentCharacter = stateContentCharacter;
	}

	public boolean isFullWord() {
		return fullWord;
	}

	public void setFullWord(boolean fullWord) {
		this.fullWord = fullWord;
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
