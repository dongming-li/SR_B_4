package com.andi.DungeonExplorer.dialogue;

/**
 * moves through the dialogue
 */
public class DialogueTraverser {
	
	private Dialogue dialogue;
	private DialogueNode currentNode;
	
	public DialogueTraverser(Dialogue dialogue) {
		this.dialogue = dialogue;
		currentNode = dialogue.getNode(dialogue.getStart());
	}

	/**
	 * gets the next node in the dialogue
	 * @param pointerIndex
	 * @return
	 */
	public DialogueNode getNextNode(int pointerIndex) {
		if (currentNode.getPointers().isEmpty()) {
			return null;
		}
		DialogueNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
		currentNode = nextNode;
		return nextNode;
	}

	/**
	 * gets the node from the dialogue
	 * @return
	 */
	public DialogueNode getNode() {
		return currentNode;
	}
}
