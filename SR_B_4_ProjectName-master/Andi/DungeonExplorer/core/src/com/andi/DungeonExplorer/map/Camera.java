package com.andi.DungeonExplorer.map;

/**
 * Camera for the game
 */
public class Camera {
	
	private float cameraX = 0f;
	private float cameraY = 0f;

	/**
	 * updates the characters camera
	 * @param newCamX
	 * @param newCamY
	 */
	public void update(float newCamX, float newCamY) {
		this.cameraX = newCamX;
		this.cameraY = newCamY;
	}

	/**
	 * get the camera x position
	 * @return
	 */
	public float getCameraX() {
		return cameraX;
	}
	/**
	 * get the camera x position
	 * @return
	 */
	public float getCameraY() {
		return cameraY;
	}
}
