package timothyyudi.ahocorasickmulticharacter.model;

import java.util.ArrayList;

public class LiteratedStatePointer {

	State state;
	ArrayList<Integer> fullKeywordPointerList;
	
	public LiteratedStatePointer() {
		this.state = null;
		this.fullKeywordPointerList = null;
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public ArrayList<Integer> getFullKeywordPointerList() {
		return fullKeywordPointerList;
	}
	public void setFullKeywordPointerList(ArrayList<Integer> fullKeywordPointerList) {
		this.fullKeywordPointerList = fullKeywordPointerList;
	}
	
}
