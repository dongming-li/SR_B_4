package com.andi.DungeonExplorer.dialogue;

import java.util.List;

public interface DialogueNode {
	/**
	 * gets the id of the node
	 * @return
	 */
	public int getID();

	/**
	 * gets the pointer to the node
	 * @return
	 */
	public List<Integer> getPointers();

	/**
	 * is the node currently in action
	 * @return
	 */
	public boolean isAction();


	
}
