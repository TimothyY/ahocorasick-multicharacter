package timothyyudi.ahocorasickmulticharacter.model;

import java.util.HashMap;
import java.util.List;

public class State {

	String stateOriginalContentCharacter;
	State parent, failState;
	boolean hasFullKeyword;
	HashMap<String, State> nextStateCollection;
	List<String> outputCollection;

	/**Called when root is created.*/
	public State(){
		super();
		this.parent = null;
		this.stateContentCharacter = null;
		this.failState = null;
		this.nextStateCollection = new HashMap<>();
		this.outputCollection = null;
		this.hasFullKeyword = false;
	}

	/**Called each time a new state is created*/
	public State(State parent, String stateContentCharacter, State failState){
		super();
		this.parent = parent;
		this.stateContentCharacter = stateContentCharacter;
		this.failState = failState;
		this.nextStateCollection = new HashMap<>();
		this.outputCollection = null;
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

	public List<String> getOutputCollection() {
		return outputCollection;
	}

	public void setOutputCollection(List<String> outputCollection) {
		this.outputCollection = outputCollection;
	}

	public boolean isHasFullKeyword() {
		return hasFullKeyword;
	}

	public void setHasFullKeyword(boolean hasFullKeyword) {
		this.hasFullKeyword = hasFullKeyword;
	}
		
	
}
