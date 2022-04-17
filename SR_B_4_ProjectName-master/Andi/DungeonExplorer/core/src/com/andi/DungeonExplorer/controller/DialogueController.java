package com.andi.DungeonExplorer.controller;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.DialogueNode;
import com.andi.DungeonExplorer.dialogue.DialogueTraverser;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.ui.DialogueBox;
import com.andi.DungeonExplorer.ui.OptionBox;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.andi.DungeonExplorer.dialogue.ChoiceDialogueNode;

/**
 * controlls the dialogue
 */
public class DialogueController extends InputAdapter {
	
	private DialogueTraverser traverser;
	private DialogueBox dialogueBox;
	private OptionBox optionBox;


	public DialogueController(DialogueBox box, OptionBox optionBox) {
		this.dialogueBox = box;
		this.optionBox = optionBox;
	}

	/**
	 * checks when key is pressed
	 * @param keycode
	 * @return
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (dialogueBox.isVisible()) {
			return true;
		}
		return false;
	}
	/**
	 * checks when key is pressed
	 * @param keycode
	 * @return
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (optionBox.isVisible()) {
			if (keycode == Keys.UP) {
				optionBox.moveUp();
				return true;
			} else if (keycode == Keys.DOWN) {
				optionBox.moveDown();
				return true;
			}
		}
		if (dialogueBox.isVisible() && !dialogueBox.isFinished()) {
			return false;
		}
		if (traverser != null && keycode == Keys.Z) { // continue through tree
			DialogueNode thisNode = traverser.getNode();
			if(thisNode.isAction()){
                if(((LinearDialogueNode)thisNode).getActor().getInventory().isNotEmpty()){
                    ((LinearDialogueNode)thisNode).getActor().dropObject();
                }
            }
			if (thisNode instanceof LinearDialogueNode)  {
				LinearDialogueNode node = (LinearDialogueNode)thisNode;
				if (node.getPointers().isEmpty()) { // blind end, since to pointers
					traverser = null;				// end dialogue
					dialogueBox.setVisible(false);
				} else {

					progress(0); // progress through first pointer
				}
			}
			if (thisNode instanceof ChoiceDialogueNode)  {
				ChoiceDialogueNode node = (ChoiceDialogueNode)thisNode;
				progress(optionBox.getIndex());
			}
			
			return true;
		}
		if (dialogueBox.isVisible()) {
			return true;
		}
		return false;
	}

	/**
	 * updates the dialogue based on time
	 * @param delta
	 */
	public void update(float delta) {
		if (dialogueBox.isFinished() && traverser != null) {
			DialogueNode nextNode = traverser.getNode();
			if (nextNode instanceof ChoiceDialogueNode) {
				optionBox.setVisible(true);
			}
		}
	}

	/**
	 * starts the dialogue
	 * @param dialogue
	 */
	public void startDialogue(Dialogue dialogue) {
		traverser = new DialogueTraverser(dialogue);
		dialogueBox.setVisible(true);
		
		DialogueNode nextNode = traverser.getNode();

		if (nextNode instanceof LinearDialogueNode) {
			LinearDialogueNode node = (LinearDialogueNode)nextNode;
			dialogueBox.animateText(node.getText());

		}
		if (nextNode instanceof ChoiceDialogueNode) {
			ChoiceDialogueNode node = (ChoiceDialogueNode)nextNode;
			dialogueBox.animateText(node.getText());
			optionBox.clear();
			for (String s : node.getLabels()) {
				optionBox.addOption(s);
			}
		}
	}

	/**
	 * progess through the dialogue
	 * @param index
	 */
	private void progress(int index) {
		optionBox.setVisible(false);
		DialogueNode nextNode = traverser.getNextNode(index);
		
		if (nextNode instanceof LinearDialogueNode) {
			LinearDialogueNode node = (LinearDialogueNode)nextNode;
			dialogueBox.animateText(node.getText());
		}
		if (nextNode instanceof ChoiceDialogueNode) {
			ChoiceDialogueNode node = (ChoiceDialogueNode)nextNode;
			dialogueBox.animateText(node.getText());
			optionBox.clearChoices();
			for (String s : node.getLabels()) {
				optionBox.addOption(s);
			}
		}
	}


}
