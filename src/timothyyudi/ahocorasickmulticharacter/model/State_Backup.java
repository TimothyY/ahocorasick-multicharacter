package timothyyudi.ahocorasickmulticharacter.model;

import java.util.HashMap;
import java.util.List;

public class State_Backup {

	String stateContentCharacter;
	State_Backup parent, failState;
	boolean hasFullKeyword;
	HashMap<String, State_Backup> nextStateCollection;
	List<String> outputCollection;

	/**Called when root is created.*/
	public State_Backup(){
		super();
		this.parent = null;
		this.stateContentCharacter = null;
		this.failState = null;
		this.nextStateCollection = new HashMap<>();
		this.outputCollection = null;
		this.hasFullKeyword = false;
	}

	/**Called each time a new state is created*/
	public State_Backup(State_Backup parent, String stateContentCharacter, State_Backup failState){
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

	public State_Backup getParent() {
		return parent;
	}

	public void setParent(State_Backup parent) {
		this.parent = parent;
	}

	public State_Backup getFailState() {
		return failState;
	}

	public void setFailState(State_Backup failState) {
		this.failState = failState;
	}

	public HashMap<String, State_Backup> getNextStateCollection() {
		return nextStateCollection;
	}

	public void setNextStateCollection(HashMap<String, State_Backup> nextStateCollection) {
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
