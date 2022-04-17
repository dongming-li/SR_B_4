package com.andi.DungeonExplorer.screen.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.andi.DungeonExplorer.Settings;
import com.andi.DungeonExplorer.map.Camera;
import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.YSortable;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.WorldObject;
import com.andi.DungeonExplorer.world.editor.Index;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * renders the world
 */
public class WorldRenderer {
	
	private AssetManager assetManager;
	private World world;
//region TEXTURES
	private TextureRegion grass;
	private TextureRegion grass1;
	private TextureRegion grass2;
	private TextureRegion indoorTiles;
	private TextureRegion indoorTilesBlood;
	private TextureRegion rightWood;
	private TextureRegion dungeonFloor;
	private TextureRegion indoorLeftTop;
	private TextureRegion indoorLeftMiddle;
	private TextureRegion indoorLeftBottom;
	private TextureRegion indoorMiddleTop;
	private TextureRegion indoorMiddleMiddle;
	private TextureRegion indoorMiddleBottom;
	private TextureRegion indoorRightTop;
	private TextureRegion indoorRightMiddle;
	private TextureRegion indoorRightBottom;
	private TextureRegion dungLeftTop;
	private TextureRegion dungLeftMiddle;
	private TextureRegion dungLeftBottom;
	private TextureRegion dungMiddleTop;
	private TextureRegion dungMiddleMiddle;
	private TextureRegion dungMiddleBottom;
	private TextureRegion dungRightTop;
	private TextureRegion dungRightMiddle;
	private TextureRegion dungRightBottom;
	private TextureRegion dsLeftTop;
	private TextureRegion dsLeftMiddle;
	private TextureRegion dsLeftBottom;
	private TextureRegion dsMiddleTop;
	private TextureRegion dsMiddleMiddle;
	private TextureRegion dsMiddleBottom;
	private TextureRegion dsRightTop;
	private TextureRegion dsRightMiddle;
	private TextureRegion dsRightBottom;
	private TextureRegion lavaLeftTop;
	private TextureRegion lavaLeftMiddle;
	private TextureRegion lavaLeftBottom;
	private TextureRegion lavaMiddleTop;
	private TextureRegion lavaMiddleBottom;
	private TextureRegion lavaRightTop;
	private TextureRegion lavaRightMiddle;
	private TextureRegion lavaRightBottom;
	private TextureRegion lavaMiddleMiddle;
	private TextureRegion teleporter;
	private TextureRegion ice;
    private TextureRegion centerPath;
	private  TextureRegion snowTile;
	private TextureRegion snowWallLeft;
	private TextureRegion snowWallRight;
	private TextureRegion snowWallTop;
	private TextureRegion snowWallBottom;
	private TextureRegion snowWallTopRightCorner;
	private TextureRegion snowWallTopLeftCorner;
	private TextureRegion snowWallBottomRightCorner;
	private TextureRegion snowWallBottomLeftCorner;
	private TextureRegion snowWallFiller;
	private TextureRegion snowSwitch;
	//endregion


	
	private List<Integer> renderedObjects = new ArrayList<Integer>();
	private List<YSortable> forRendering = new ArrayList<YSortable>();
	
	public WorldRenderer(AssetManager assetManager, World world) {
		this.assetManager = assetManager;
		this.world = world;;
		
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		grass = atlas.findRegion("grass");
		grass1 = atlas.findRegion("grass1");
		grass2 = atlas.findRegion("grass2");
		rightWood = atlas.findRegion("Right_Wood");

		dungeonFloor = atlas.findRegion("dungeonfloor");
		indoorTiles = atlas.findRegion("indoor_tiles");
		indoorTilesBlood = atlas.findRegion("indoor_tiles_blood");
		indoorLeftTop = atlas.findRegion("indoorLeftTop");
		indoorLeftMiddle = atlas.findRegion("indoorLeftMiddle");
		indoorLeftBottom= atlas.findRegion("indoorLeftBottom");
		indoorMiddleTop= atlas.findRegion("indoorMiddleTop");
		indoorMiddleMiddle= atlas.findRegion("indoorMiddleMiddle");
		indoorMiddleBottom= atlas.findRegion("indoorMiddleBottom");
		indoorRightTop= atlas.findRegion("indoorRightTop");
		indoorRightMiddle= atlas.findRegion("indoorRightMiddle");
		indoorRightBottom= atlas.findRegion("indoorRightBottom");
		dungLeftTop = atlas.findRegion("dungLeftTop");
		dungLeftMiddle = atlas.findRegion("dungLeftMiddle");
		dungLeftBottom= atlas.findRegion("dungLeftBottom");
		dungMiddleTop= atlas.findRegion("dungMiddleTop");
		dungMiddleMiddle= atlas.findRegion("dungMiddleMiddle");
		dungMiddleBottom= atlas.findRegion("dungMiddleBottom");
		dungRightTop= atlas.findRegion("dungRightTop");
		dungRightMiddle= atlas.findRegion("dungRightMiddle");
		dungRightBottom= atlas.findRegion("dungRightBottom");
		dsLeftTop = atlas.findRegion("dsLeftTop");
		dsLeftMiddle = atlas.findRegion("dsLeftMiddle");
		dsLeftBottom= atlas.findRegion("dsLeftBottom");
		dsMiddleTop= atlas.findRegion("dsMiddleTop");
		dsMiddleMiddle= atlas.findRegion("dsMiddleMiddle");
		dsMiddleBottom= atlas.findRegion("dsMiddleBottom");
		dsRightTop= atlas.findRegion("dsRightTop");
		dsRightMiddle= atlas.findRegion("dsRightMiddle");
		dsRightBottom= atlas.findRegion("dsRightBottom");
		lavaLeftTop = atlas.findRegion("lavaLeftTop");
		lavaLeftMiddle = atlas.findRegion("lavaLeftMiddle");
		lavaLeftBottom= atlas.findRegion("lavaLeftBottom");
		lavaMiddleTop= atlas.findRegion("lavaMiddleTop");
		lavaMiddleBottom= atlas.findRegion("lavaMiddleBottom");
		lavaRightTop= atlas.findRegion("lavaRightTop");
		lavaRightMiddle= atlas.findRegion("lavaRightMiddle");
		lavaRightBottom= atlas.findRegion("lavaRightBottom");
		lavaMiddleMiddle = atlas.findRegion("lavaMiddleMiddle");
		teleporter = atlas.findRegion("teleporter");
		ice = atlas.findRegion("ice");
        centerPath = atlas.findRegion("pathMiddle");
		snowTile = atlas.findRegion("snowTile");
		snowWallLeft = atlas.findRegion("snowWallLeft");
		snowWallRight = atlas.findRegion("snowWallRight");
		snowWallTop = atlas.findRegion("snowWallTop");
		snowWallBottom = atlas.findRegion("snowWallBottom");
		snowWallTopRightCorner = atlas.findRegion("snowWallTopRightCorner");
		snowWallTopLeftCorner = atlas.findRegion("snowWallTopLeftCorner");
		snowWallBottomRightCorner = atlas.findRegion("snowWallBottomRightCorner");
		snowWallBottomLeftCorner = atlas.findRegion("snowWallBottomLeftCorner");
		snowWallFiller  =atlas.findRegion("snowFiller");
		snowSwitch = atlas.findRegion("snowSwitch");



	}

	/**
	 * Renders each tile on the map
	 * @param batch
	 * @param camera
	 */
	public void render(SpriteBatch batch, Camera camera) {
		float worldStartX = Gdx.graphics.getWidth()/2 - camera.getCameraX()* Settings.SCALED_TILE_SIZE;
		float worldStartY = Gdx.graphics.getHeight()/2 - camera.getCameraY()* Settings.SCALED_TILE_SIZE;
		
		/* render tile terrains */
		for (int x = 0; x < world.getMap().getWidth(); x++) {
			for (int y = 0; y < world.getMap().getHeight(); y++) {
				TextureRegion render;
				//region RENDERTEXTURE
				if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.GRASS_1) {
					render = grass1;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.GRASS_0) {
					render = grass;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.GRASS_2) {
					render = grass2;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_TILES) {
					render = indoorTiles;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_TILES_BLOOD) {
					render = indoorTilesBlood;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_LEFT_BOTTOM) {
					render = indoorLeftBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_LEFT_MIDDLE) {
					render = indoorLeftMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_LEFT_TOP) {
					render = indoorLeftTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_MIDDLE_TOP) {
					render = indoorMiddleTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_MIDDLE_MIDDLE) {
					render = indoorMiddleMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_MIDDLE_BOTTOM) {
					render = indoorMiddleBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_RIGHT_TOP) {
					render = indoorRightTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_RIGHT_MIDDLE) {
					render = indoorRightMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_WALL_RIGHT_BOTTOM) {
					render = indoorRightBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_LEFT_BOTTOM) {
					render = dungLeftBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_LEFT_MIDDLE) {
					render = dungLeftMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_LEFT_TOP) {
					render = dungLeftTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_MIDDLE_TOP) {
					render = dungMiddleTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_MIDDLE_MIDDLE) {
					render = dungMiddleMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_MIDDLE_BOTTOM) {
					render = dungMiddleBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_RIGHT_TOP) {
					render = dungRightTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_RIGHT_MIDDLE) {
					render = dungRightMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNG_WALL_RIGHT_BOTTOM) {
					render = dungRightBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_LEFT_BOTTOM) {
					render = dsLeftBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_LEFT_MIDDLE) {
					render = dsLeftMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_LEFT_TOP) {
					render = dsLeftTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_MIDDLE_TOP) {
					render = dsMiddleTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_MIDDLE_MIDDLE) {
					render = dsMiddleMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_MIDDLE_BOTTOM) {
					render = dsMiddleBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_RIGHT_TOP) {
					render = dsRightTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_RIGHT_MIDDLE) {
					render = dsRightMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ds_WALL_RIGHT_BOTTOM) {
					render = dsRightBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_LEFT_BOTTOM) {
					render = lavaLeftBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_LEFT_MIDDLE) {
					render = lavaLeftMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_LEFT_TOP) {
					render = lavaLeftTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_MIDDLE_TOP) {
					render = lavaMiddleTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_MIDDLE_BOTTOM) {
					render = lavaMiddleBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_RIGHT_TOP) {
					render = lavaRightTop;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_RIGHT_MIDDLE) {
					render = lavaRightMiddle;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_RIGHT_BOTTOM) {
					render = lavaRightBottom;
				}
				else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.lava_WALL_MIDDLE_MIDDLE) {
					render = lavaMiddleMiddle;
				}
				else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.TELEPORTER) {
					render = teleporter;
				}else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.ice) {
					render = ice;
				}
                else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.DIRT_PATH_MIDDLE){
                    render = centerPath;
                }
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_BOTTOM){
					render = snowWallBottom;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_LEFT){
					render = snowWallLeft;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_RIGHT){
					render = snowWallRight;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_TOP){
					render = snowWallTop;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_TILE){
					render = snowTile;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_BOTTOM_LEFT_CORNER){
					render = snowWallBottomLeftCorner;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_BOTTOM_RIGHT_CORNER){
					render = snowWallBottomRightCorner;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_TOP_LEFT_CORNER){
					render = snowWallTopLeftCorner;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_TOP_RIGHT_CORNER){
					render = snowWallTopRightCorner;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_WALL_FILLER){
					render = snowWallFiller;
				}
				else if(world.getMap().getTile(x,y).getTerrain()==TERRAIN.SNOW_SWITCH){
					render = snowSwitch;
				}
                //endregion
				else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.DUNGEON_FLOOR) {
					render = dungeonFloor;
				}
				else {
					render = null;
				}

				if (render != null) {
					batch.draw(render, 
							(int)(worldStartX+x* Settings.SCALED_TILE_SIZE),
							(int)(worldStartY+y* Settings.SCALED_TILE_SIZE),
							(int)(Settings.SCALED_TILE_SIZE),
							(int)(Settings.SCALED_TILE_SIZE));
				}

			}
		}

		
		/* collect objects and actors */
		for (int x = 0; x < world.getMap().getWidth(); x++) {
			for (int y = 0; y < world.getMap().getHeight(); y++) {
				if (world.getMap().getTile(x, y).getActor() != null) {
					Actor actor = world.getMap().getTile(x, y).getActor();
					forRendering.add(actor);
				}
				if (world.getMap().getTile(x, y).getIndex() != null) {
					Index index = world.getMap().getTile(x, y).getIndex();
					forRendering.add(index);
				}
				if (world.getMap().getTile(x, y).getObject() != null) {
					WorldObject object = world.getMap().getTile(x, y).getObject();
					if (renderedObjects.contains(object.hashCode())) { // test if it's already drawn
						continue;
					}
					if (object.isWalkable() && object.isVisible()) {  		// if it's walkable, draw it right away
						batch.draw(object.getSprite(),	// chances are it's probably something on the ground
								worldStartX+object.getWorldX()* Settings.SCALED_TILE_SIZE,
								worldStartY+object.getWorldY()* Settings.SCALED_TILE_SIZE,
								Settings.SCALED_TILE_SIZE*object.getSizeX(),
								Settings.SCALED_TILE_SIZE*object.getSizeY());
						continue;
					} else {	// add it to the list of YSortables
						forRendering.add(object);
						renderedObjects.add(object.hashCode());
					}
				}
			}
		}
		
		Collections.sort(forRendering, new WorldObjectYComparator());
		Collections.reverse(forRendering);
		
		for (YSortable loc : forRendering) {
			if (loc instanceof Actor) {
				Actor a = (Actor)loc;
				if (!a.isVisible()) {
					continue;
				}
			}
			if(loc instanceof WorldObject){
				WorldObject obj = (WorldObject) loc;
				if(!obj.isVisible()){
					continue;
				}
			}
			batch.draw(loc.getSprite(), 
					worldStartX+loc.getWorldX()* Settings.SCALED_TILE_SIZE,
					worldStartY+loc.getWorldY()* Settings.SCALED_TILE_SIZE,
					Settings.SCALED_TILE_SIZE*loc.getSizeX(),
					Settings.SCALED_TILE_SIZE*loc.getSizeY());
		}
		
		renderedObjects.clear();
		forRendering.clear();
	}

	/**
	 * sets the worlds
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}

}
