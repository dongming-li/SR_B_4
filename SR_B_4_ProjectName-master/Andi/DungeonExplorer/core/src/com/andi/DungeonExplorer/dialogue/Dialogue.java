package com.andi.DungeonExplorer.dialogue;

import java.util.HashMap;
import java.util.Map;

/**
 * Dialogue for the game
 */
public class Dialogue {
	
	private Map<Integer, DialogueNode> nodes = new HashMap<Integer, DialogueNode>();

	/**
	 * gets the node
	 * @param id
	 * @return
	 */
	public DialogueNode getNode(int id) {
		return nodes.get(id);
	}

	/**
	 * adds a node to the dialogue
	 * @param node
	 */
	public void addNode(DialogueNode node) {
		this.nodes.put(node.getID(), node);
	}

	/**
	 * gets the starting point of the dialogue
	 * @return
	 */
	public int getStart() {
		return 0;
	}
	
	/**
	 * @return Number of nodes in this dialogue
	 */
	public int size() {
		return nodes.size();
	}
}
