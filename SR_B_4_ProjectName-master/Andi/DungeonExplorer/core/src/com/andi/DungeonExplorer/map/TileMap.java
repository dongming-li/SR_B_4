package com.andi.DungeonExplorer.map;

/**
 * the 2d array of tiles aka the map of the game
 */
public class TileMap {
	
	private int width, height;
	private Tile[][] tiles;
	
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (Math.random() > 0.6d) {
					tiles[x][y] = new Tile("grass0",TERRAIN.GRASS_0);
				}else if(Math.random()>0.3d){
					tiles[x][y] = new Tile("grass1",TERRAIN.GRASS_1);
				}
				else {
					tiles[x][y] = new Tile("grass2",TERRAIN.GRASS_2);
				}
			}
		}
	}

	/**
	 * get the tile at x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * sets the tile at x,y
	 * @param tile
	 * @param x
	 * @param y
	 */
	public void setTile(Tile tile,int x,int y){
		tiles[x][y] = tile;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}


}
