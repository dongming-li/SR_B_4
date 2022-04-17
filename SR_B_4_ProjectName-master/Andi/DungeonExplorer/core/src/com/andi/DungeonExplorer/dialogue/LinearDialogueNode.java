package com.andi.DungeonExplorer.dialogue;

import com.andi.DungeonExplorer.actor.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class LinearDialogueNode implements DialogueNode {
	
	private String text;
	private int id;
	private List<Integer> pointers = new ArrayList<Integer>();
	private boolean action;
	private Actor actor;




	public LinearDialogueNode(String text, int id, boolean act) {
		this.text = text;
		this.id = id;
		this.action = act;


	}
	
	public void setPointer(int id) {
		pointers.add(id);
	}
	
	public String getText() {
		return text;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public List<Integer> getPointers() {
		return pointers;
	}

	@Override
	public boolean isAction() {
		return action;
	}
	public void addActor(Actor a) {
		actor = a;
	}
	public Actor getActor() {
		return actor;
	}

}
