package timothyyudi.ahocorasickmulticharacter.model;

import java.util.HashMap;

public class State {

	String stateContentCharacter;
	//State parent; 
	State failState;
	Integer fullKeywordHashCode;
	HashMap<String, LiteratedStatePointer> nextLiteratedStateCollection;
	LiteratedStatePointer originLiteratedStatePointer;

	/**Called when root is created.*/
	public State(){
		super();
		this.stateContentCharacter = null;
//		this.parent = null;
		this.failState = null;
		this.fullKeywordHashCode = null;
		this.nextLiteratedStateCollection = null;
		this.originLiteratedStatePointer=null;
	}

	/**Called each time a new state is created*/
	public State(State parent, String stateContentCharacter, State failState){
		super();
		this.stateContentCharacter = stateContentCharacter;
//		this.parent = parent;
		this.failState = failState;
		this.fullKeywordHashCode = null;
		this.nextLiteratedStateCollection = null;
		this.originLiteratedStatePointer = null;
	}

	/**Called each time a new state is created*/
	public State(State originalState){
		super();
		this.stateContentCharacter = originalState.getStateContentCharacter();
//		this.parent = parent;
		this.failState = originalState.getFailState();
		this.fullKeywordHashCode = originalState.getFullKeywordHashCode();
		this.nextLiteratedStateCollection = originalState.getNextLiteratedStateCollection();
		this.originLiteratedStatePointer = originalState.getOriginLiteratedStatePointer();
	}
	
	public String getStateContentCharacter() {
		return stateContentCharacter;
	}

	public void setStateContentCharacter(String stateContentCharacter) {
		this.stateContentCharacter = stateContentCharacter;
	}

/*
	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}
*/
	public State getFailState() {
		return failState;
	}

	public void setFailState(State failState) {
		this.failState = failState;
	}

	public Integer getFullKeywordHashCode() {
		return fullKeywordHashCode;
	}

	public void setFullKeywordHashCode(Integer fullKeywordHashCode) {
		this.fullKeywordHashCode = fullKeywordHashCode;
	}

	public HashMap<String, LiteratedStatePointer> getNextLiteratedStateCollection() {
		return nextLiteratedStateCollection;
	}

	public void setNextLiteratedStateCollection(
			HashMap<String, LiteratedStatePointer> nextLiteratedStateCollection) {
		this.nextLiteratedStateCollection = nextLiteratedStateCollection;
	}

	public LiteratedStatePointer getOriginLiteratedStatePointer() {
		return originLiteratedStatePointer;
	}

	public void setOriginLiteratedStatePointer(
			LiteratedStatePointer originLiteratedStatePointer) {
		this.originLiteratedStatePointer = originLiteratedStatePointer;
	}
		
}
