package com.andi.DungeonExplorer.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.map.TileMap;
import com.andi.DungeonExplorer.actor.ActorBehavior;
import com.andi.DungeonExplorer.actor.Character.Character;
import com.andi.DungeonExplorer.world.InteractableItems.Boulder;
import com.andi.DungeonExplorer.world.InteractableItems.ClueSign;
import com.andi.DungeonExplorer.world.InteractableItems.Crystal;
import com.andi.DungeonExplorer.world.InteractableItems.SnowStairs;
import com.andi.DungeonExplorer.world.InteractableItems.SpikeTrapObject;
import com.andi.DungeonExplorer.world.Equipment.Bow;
import com.andi.DungeonExplorer.world.Equipment.Key;
import com.andi.DungeonExplorer.world.Equipment.Sword;
import com.andi.DungeonExplorer.world.Equipment.Weapon;
import com.andi.DungeonExplorer.world.InteractableItems.Portal;
import com.andi.DungeonExplorer.world.SpecialTiles.IceTile;
import com.andi.DungeonExplorer.world.SpecialTiles.SpikeTrap;
import com.andi.DungeonExplorer.world.SpecialTiles.Teleporter;
import com.andi.DungeonExplorer.world.editor.Index;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.GridPoint2;
import com.andi.DungeonExplorer.actor.Actor;

import static java.lang.Math.abs;

/**
 * Contains data about the game world, such as references to Actors, and WorldObjects.
 * Query the world from here.
 */
public class World {

	/** Unique name used to refer to this world */
	private String name;

	private AssetManager assetManager;

	private TileMap map;
	private List<Actor> actors;
	private HashMap<Actor,ActorBehavior> brains;
	private List<WorldObject> objects;

	private Index index;	//index for editing

	public Key key;

	//animations
	public Animation flowerAnimation;
	public Animation doorOpen;
	public Animation doorClose;
	public Animation chestOpen;
	public Animation crystalChange;
	public Animation flippedChestOpen;
	public Animation spikeUp;
	public Animation spikeDown;
	private int width;
	private int height;



	public World(String name, int width, int height, AssetManager AssetManager) {
		this.name = name;
		this.assetManager = AssetManager;
		this.map = new TileMap(width, height);

		actors = new ArrayList<Actor>();
		brains = new HashMap<Actor,ActorBehavior>();
		objects = new ArrayList<WorldObject>();

		this.width = width;
		this.height = height;

		//set textures and animations
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		flowerAnimation = new Animation(0.8f, atlas.findRegions("flowers"), Animation.PlayMode.LOOP_PINGPONG);
		doorOpen = new Animation(0.8f/4f, atlas.findRegions("woodenDoor"), Animation.PlayMode.NORMAL);
		doorClose = new Animation(0.5f/4f, atlas.findRegions("woodenDoor"), Animation.PlayMode.REVERSED);
		chestOpen = new Animation(0.8f/4f, atlas.findRegions("chest"), Animation.PlayMode.NORMAL);
		flippedChestOpen = new Animation(0.8f/4f, atlas.findRegions("flippedChest"), Animation.PlayMode.NORMAL);
		crystalChange = new Animation(0.8f/4f, atlas.findRegions("crystalChange"), Animation.PlayMode.NORMAL);
		spikeUp = new Animation(0.8f/4f, atlas.findRegions("spike"), Animation.PlayMode.NORMAL);
		spikeDown = new Animation(0.8f/4f, atlas.findRegions("spike"), Animation.PlayMode.REVERSED);

	}

	/**
	 * Add (floor) Tiles to this map
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 * @param type
	 */
	public void addTiles(int startX, int startY, int width, int height, String type){
		int i,j;	//counters

		TERRAIN terrain = TERRAIN.ds_WALL_MIDDLE_MIDDLE;
		if(type == "blank"){
			terrain = TERRAIN.ds_WALL_MIDDLE_MIDDLE;
		}
		if(type == "grass"){
			terrain = TERRAIN.GRASS_0;
		}
		if(type == "ice"){
			terrain = TERRAIN.ice;
		}
		if(type == "snowTile"){
			terrain = TERRAIN.SNOW_TILE;
		}
		if(type == "dungeonFloor"){
			terrain = TERRAIN.DUNGEON_FLOOR;
		}
		if(type == "lava"){
			terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
		}

		//cycle through tiles
		for(i = startX; i < startX + width; i++){
			for(j = startY; j < startY + height; j++){
				Tile tile = new Tile(type,terrain,true);
				if(this.getMap().getTile(i,j).getObject() != null){
					//set object on previous tile
					tile.setObject(this.getMap().getTile(i,j).getObject());
				}
				this.getMap().setTile(tile, i, j);
			}
		}
	}
	public AnimationSet addAnimationSet(String name){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		AnimationSet animations = new AnimationSet(
				new Animation(0.3f/2f, atlas.findRegions(name+"_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions(name+"_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions(name+"_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions(name+"_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
				atlas.findRegion(name+"_stand_north"),
				atlas.findRegion(name+"_stand_south"),
				atlas.findRegion(name+"_stand_east"),
				atlas.findRegion(name+"_stand_west"),
				atlas.findRegion(name+"_dying_north"),
				atlas.findRegion(name+"_dying_south"),
				atlas.findRegion(name+"_dying_east"),
				atlas.findRegion(name+"_dying_west")
		);
		return animations;
	}

	/**
	 * adds a sign post
	 * @param x
	 * @param y
	 * @param text
	 */
	public void addClueSign(int x, int y,String text){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion clueRegion = atlas.findRegion("signpost");

		ClueSign clue = new ClueSign(x,y,clueRegion,text);
		this.addObject(clue);
	}

	/**
	 * add a tree at x,y
	 * @param x x position to place tree
	 * @param y y position to place tree
	 */
	public void addTree(int x, int y) {
		//get texture
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion treeRegion = atlas.findRegion("large_tree");
		//set gridpoints
		GridPoint2[] gridArray = new GridPoint2[2*2];
		gridArray[0] = new GridPoint2(0,0);
		gridArray[1] = new GridPoint2(0,1);
		gridArray[2] = new GridPoint2(1,1);
		gridArray[3] = new GridPoint2(1,0);
		WorldObject tree = new WorldObject("tree",x, y, false, treeRegion, 2f, 3f, gridArray,true);
		this.addObject(tree);
	}

	public void addBow(int x, int y){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion bowTex = atlas.findRegion("bow");
        Bow bow = new Bow(11, 11, bowTex, 1, 1, new GridPoint2(1, 1), true, new int[]{15,0,0,0,0,0,0,0,0,0}, "Bow");
        this.addObject(bow);
    }

	/**
	 * adds a 6x5 array of snow trees
	 * @param x x pos for snow trees
	 * @param y y pos for snow trees
	 */
    public void add6x5SnowTreeRegion(int x, int y) {
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion treeRegion = atlas.findRegion("5x6SnowTrees");
        GridPoint2[] gridArray = new GridPoint2[(int)6*(int)4];
        int index = 0;
        for(int i = 0;i<6;i++){
            for(int j = 0;j<4;j++){
                gridArray[index] = new GridPoint2(i,j);
                index++;
            }
        }
        WorldObject tree = new WorldObject("5x6_snow_trees",x, y, false, treeRegion, 6f, 5f, gridArray,true);
        this.addObject(tree);
    }

	public void add1x5SnowTreeRegion(int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion treeRegion = atlas.findRegion("1x2SnowTrees");
		GridPoint2[] gridArray = new GridPoint2[(int)2*(int)4];
		int index = 0;
		for(int i = 0;i<2;i++){
			for(int j = 0;j<4;j++){
				gridArray[index] = new GridPoint2(i,j);
				index++;
			}
		}
		WorldObject tree = new WorldObject("5x6_snow_trees",x, y, false, treeRegion, 2f, 5f, gridArray,true);
		this.addObject(tree);
	}

	/**
	 * adds a 4x19 array of snow trees
	 * @param x x pos for bottom left of snow trees
	 * @param y y pos for bottom left of snow trees
	 */
    public void add4x19SnowTreeRegion(int x, int y) {
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion treeRegion = atlas.findRegion("4x19SnowTrees");
        GridPoint2[] gridArray = new GridPoint2[4*19];
        int index = 0;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<19;j++){
                gridArray[index] = new GridPoint2(i,j);
                index++;
            }
        }
        WorldObject tree = new WorldObject("5x6_snow_trees",x, y, false, treeRegion, 4f, 19f, gridArray,true);
        this.addObject(tree);
    }

	/**
	 * add snow stairs
	 * @param x x position
	 * @param y y position
	 * @param actId for switch activation
	 */
	public void addSnowStairs(int x, int y,int actId) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion snowRegion = atlas.findRegion("snowStairs");
		SnowStairs stairs = new SnowStairs(x, y,snowRegion,actId,this);
		this.addObject(stairs);
	}

	/**
	 * add a house at x,y
	 * @param x x pos for house
	 * @param y y pos for house
	 */
    public void addHouse( int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion houseRegion = atlas.findRegion("small_house");
		GridPoint2[] gridArray = new GridPoint2[5*4-1];
		int index = 0;
		for (int loopX = 0; loopX < 5; loopX++) {
			for (int loopY = 0; loopY < 4; loopY++) {
				if (loopX==3&&loopY==0) {
					continue;
				}
				gridArray[index] = new GridPoint2(loopX, loopY);
				index++;
			}
		}
		WorldObject house = new WorldObject("house",x, y, false, houseRegion, 5f, 5f, gridArray,true);
		this.addObject(house);
	}

	/**
	 * add animating flowers
	 * @param x x pos
	 * @param y y pos
	 */
	public void addFlowers(int x, int y) {
		GridPoint2[] gridArray = new GridPoint2[1];
		gridArray[0] = new GridPoint2(0,0);
		WorldObject flowers = new WorldObject("flowers",x, y, true, flowerAnimation, 1f, 1f, gridArray,false);
		this.addObject(flowers);
	}

	/**
	 * Adds a self-contained wall. That is, properly adds edges for this section of wall.
	 * Don't use this for sections of wall smaller than 2x2, or it'll look weird.
	 * @param startX bottom left x coord
	 * @param startY bottom left y coord
	 * @param width
	 * @param height
	 * @param tileType use to distinguish between different tilesets
	 *                 0 = purple
	 *                 1 = dungeon cave
	 *                 2 = border
	 */
	public void addWall(int startX, int startY, int width, int height, int tileType){
		//TODO different paths for different tileTypes
		String type;
		if(tileType==0){
			for(int i=startX; i<startX+width; i++){
				for(int j=startY; j<startY+height; j++){
					TERRAIN terrain;
					//left edge
					if(i==startX && j==startY){
						terrain = TERRAIN.INDOOR_WALL_LEFT_BOTTOM;
						type = "purpleWallLeftBottom";
					}
					else if(i==startX && j==startY+height-1){
						terrain = TERRAIN.INDOOR_WALL_LEFT_TOP;
						type = "purpleWallLeftTop";
					}
					else if(i==startX){
						terrain = TERRAIN.INDOOR_WALL_LEFT_MIDDLE;
						type = "purpleWallLeftMiddle";
					}
					//top edge
					else if(j==startY+height-1 && i==startX+width-1){
						terrain = TERRAIN.INDOOR_WALL_RIGHT_TOP;
						type = "purpleWallRightTop";
					}
					else if(j==startY+height-1){
						terrain = TERRAIN.INDOOR_WALL_MIDDLE_TOP;
						type = "purpleWallMiddleTop";
					}
					//right edge
					else if(i==startX+width-1 && j==startY){
						terrain = TERRAIN.INDOOR_WALL_RIGHT_BOTTOM;
						type = "purpleWallRightBottom";
					}
					else if(i==startX+width-1){
						terrain = TERRAIN.INDOOR_WALL_RIGHT_MIDDLE;
						type = "purpleWallRightMiddle";
					}
					//bottom edge
					else if(j==startY){
						terrain = TERRAIN.INDOOR_WALL_MIDDLE_BOTTOM;
						type = "purpleWallMiddleBottom";
					}
					//fill center
					else{
						terrain = TERRAIN.INDOOR_WALL_MIDDLE_MIDDLE;
						type = "purpleWallMiddleMiddle";
					}
					//add the tile
					this.getMap().setTile(new Tile(type,terrain, false), i, j);
				}
			}
		}
		else if(tileType==1){
			for(int i=startX; i<startX+width; i++){
				for(int j=startY; j<startY+height; j++){
					TERRAIN terrain;
					//left edge
					if(i==startX && j==startY){
						terrain = TERRAIN.DUNG_WALL_LEFT_BOTTOM;
						type = "dungWallLeftBottom";
					}
					else if(i==startX && j==startY+height-1){
						terrain = TERRAIN.DUNG_WALL_LEFT_TOP;
						type = "dungWallLeftTop";
					}
					else if(i==startX){
						terrain = TERRAIN.DUNG_WALL_LEFT_MIDDLE;
						type = "dungWallLeftTop";
					}
					//top edge
					else if(j==startY+height-1 && i==startX+width-1){
						terrain = TERRAIN.DUNG_WALL_RIGHT_TOP;
						type = "dungWallRightTop";
					}
					else if(j==startY+height-1){
						terrain = TERRAIN.DUNG_WALL_MIDDLE_TOP;
						type = "dungWallMiddleTop";
					}
					//right edge
					else if(i==startX+width-1 && j==startY){
						terrain = TERRAIN.DUNG_WALL_RIGHT_BOTTOM;
						type = "dungWallRightBottom";
					}
					else if(i==startX+width-1){
						terrain = TERRAIN.DUNG_WALL_RIGHT_MIDDLE;
						type = "dungWallRightMiddle";
					}
					//bottom edge
					else if(j==startY){
						terrain = TERRAIN.DUNG_WALL_MIDDLE_BOTTOM;
						type = "dungWallMiddleBottom";
					}
					//fill center
					else{
						terrain = TERRAIN.DUNG_WALL_MIDDLE_MIDDLE;
						type = "dungWallMiddleMiddle";
					}
					//add the tile
					this.getMap().setTile(new Tile(type,terrain, false), i, j);
				}
			}
		}
		else if(tileType==2){
			for(int i=startX; i<startX+width; i++){
				for(int j=startY; j<startY+height; j++){
					TERRAIN terrain;
					//left edge
					if(i==startX && j==startY){
						terrain = TERRAIN.ds_WALL_LEFT_BOTTOM;
					}
					else if(i==startX && j==startY+height-1){
						terrain = TERRAIN.ds_WALL_LEFT_TOP;
					}
					else if(i==startX){
						terrain = TERRAIN.ds_WALL_LEFT_MIDDLE;
					}
					//top edge
					else if(j==startY+height-1 && i==startX+width-1){
						terrain = TERRAIN.ds_WALL_RIGHT_TOP;
					}
					else if(j==startY+height-1){
						terrain = TERRAIN.ds_WALL_MIDDLE_TOP;
					}
					//right edge
					else if(i==startX+width-1 && j==startY){
						terrain = TERRAIN.ds_WALL_RIGHT_BOTTOM;
					}
					else if(i==startX+width-1){
						terrain = TERRAIN.ds_WALL_RIGHT_MIDDLE;
					}
					//bottom edge
					else if(j==startY){
						terrain = TERRAIN.ds_WALL_MIDDLE_BOTTOM;
					}
					//fill center
					else{
						terrain = TERRAIN.ds_WALL_MIDDLE_MIDDLE;
					}
					//add the tile
					this.getMap().setTile(new Tile("blackWall",terrain, false), i, j);
				}
			}
		}
        if(tileType==3){
            for(int i=startX; i<startX+width; i++){
                for(int j=startY; j<startY+height; j++){
                    TERRAIN terrain;
                    //left edge
                    if(i==startX && j==startY){
                        terrain = TERRAIN.SNOW_WALL_BOTTOM_LEFT_CORNER;
						type = "snowWallBottomLeft";
                    }
                    else if(i==startX && j==startY+height-1){
                        terrain = TERRAIN.SNOW_WALL_TOP_LEFT_CORNER;
						type = "snowWallTopLeft";
                    }
                    else if(i==startX){
                        terrain = TERRAIN.SNOW_WALL_LEFT;
						type = "snowWallLeft";
                    }
                    //top edge
                    else if(j==startY+height-1 && i==startX+width-1){
                        terrain = TERRAIN.SNOW_WALL_TOP_RIGHT_CORNER;
						type = "snowWallTopRight";
                    }
                    else if(j==startY+height-1){
                        terrain = TERRAIN.SNOW_WALL_TOP;
						type = "snowWallTop";
                    }
                    //right edge
                    else if(i==startX+width-1 && j==startY){
                        terrain = TERRAIN.SNOW_WALL_BOTTOM_RIGHT_CORNER;
						type = "snowWallBottomRight";
                    }
                    else if(i==startX+width-1){
                        terrain = TERRAIN.SNOW_WALL_RIGHT;
						type = "snowWallRight";
                    }
                    //bottom edge
                    else if(j==startY){
                        terrain = TERRAIN.SNOW_WALL_BOTTOM;
						type = "snowWallBottom";
                    }
                    //fill center
                    else{
                        terrain = TERRAIN.SNOW_WALL_FILLER;
						type = "snowWallCenter";

                    }
					if (terrain.equals(TERRAIN.SNOW_WALL_FILLER)) {
						this.getMap().setTile(new Tile(type,terrain, true), i, j);
					}
					else{
						this.getMap().setTile(new Tile(type,terrain, false), i, j);
					}

                }
            }
        }
	}

	/**
	 * add a key to this world
	 * @param x x pos of desired tile
	 * @param y y pos of desired tile
	 * @param name name of this key
	 * @param id keys id
	 * @return
	 */
	public Key addKey(int x, int y, String name,int id){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion keyRegion = atlas.findRegion(name);
		Key key = new Key(x,y,keyRegion,id,name);
		return key;
	}

	/**
	 * add lava tiles
	 * @param startX starting x position
	 * @param startY starting y position
	 * @param width width of area
	 * @param height height of area
	 */
	public void addLava( int startX, int startY,int width,int height){
		for(int i=startX; i<startX+width; i++) {
			for (int j = startY; j < startY + height; j++) {
				TERRAIN terrain = TERRAIN.DUNGEON_FLOOR;
				boolean walkable = false;
				//left edge
				if (i == startX && j == startY) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (i == startX && j == startY + height - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (i == startX) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				//top edge
				else if (j == startY + height - 1 && i == startX + width - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (j == startY + height - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				//right edge
				else if (i == startX + width - 1 && j == startY) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (i == startX + width - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				//bottom edge
				else if (j == startY) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				else{
					terrain = TERRAIN.DUNGEON_FLOOR;
					 walkable = true;
				}
				//add the tile
				this.getMap().setTile(new Tile("lava",terrain, walkable), i, j);
			}
		}
	}

	public void addTeleporters(World world,int startX,int startY,int bottomLeftX,int bottomLeftY,int topLeftX,int topLeftY,
							   int topRightX,int topRightY,int bottomRightX, int bottomRightY){
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,bottomLeftX,bottomLeftY),startX+1,startY+1);
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,topLeftX,topLeftY),startX+1,startY+3);
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,topRightX,topRightY),startX+3,startY+3);
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,bottomRightX,bottomRightY),startX+3,startY+1);
	}

	public void addCrystal(int x, int y, int id, CutsceneEventQueuer q){
		Crystal crystal = new Crystal(x,y,crystalChange,id,q);
		this.addObject(crystal);
	}

	public void addBoulderActor(int x, int y){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion boulderRegion = atlas.findRegion("boulder");
		Boulder boulder = new Boulder(this,x,y,boulderRegion);
		this.addActor(boulder);
	}

	public void addSpawnTile(int x, int y){
		this.getMap().setTile(new Tile("spawn",TERRAIN.TELEPORTER, true), x, y);
	}


	public void addPortal(World world, int x, int y, String name, float sizeX, float sizeY){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion portalRegion = atlas.findRegion(name);
		Portal Portal = new Portal(x,y,true,portalRegion,sizeX,sizeY,new GridPoint2(0,0),false);
		world.addObject(Portal);
	}

	public void addWeapon(Weapon weapon,String texture,String weaponName, int x, int y){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion weaponRegion = atlas.findRegion(texture);
		if(weapon instanceof Sword){
			int[] stats = {5,0,0,0,0,0,0,0,0,0,0};
			Sword sword = new Sword(x,y,weaponRegion,1f,1f,new GridPoint2(0,0), true, stats,weaponName);
			this.addObject(sword);
		}
		if(weapon instanceof Bow){
            int[] stats = {5,0,0,0,0,0,0,0,0,0,0};
            Bow bow = new Bow(x,y,weaponRegion,1f,1f,new GridPoint2(0,0), true, stats,weaponName);
            this.addObject(bow);
        }

	}
	/**
	 * adds spikes to the map
	 */
	public void addSpikes(TERRAIN t, CutscenePlayer player, CutsceneEventQueuer broadcaster, int x, int y){
		this.getMap().setTile(new SpikeTrap(t,player,broadcaster,x,y),x,y);
		SpikeTrapObject spike = new SpikeTrapObject(x,y,spikeUp,spikeDown);
		this.addObject(spike);
	}

	/**
	 * adds a rug
	 * @param world
	 * @param x
	 * @param y
	 */
	public void addRug(World world, int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion rugRegion = atlas.findRegion("rug");
		GridPoint2[] gridArray = new GridPoint2[3*2];
		gridArray[0] = new GridPoint2(0,0);
		gridArray[1] = new GridPoint2(0,1);
		gridArray[2] = new GridPoint2(0,2);
		gridArray[3] = new GridPoint2(1,0);
		gridArray[4] = new GridPoint2(1,1);
		gridArray[5] = new GridPoint2(1,2);
		WorldObject rug = new WorldObject("rug",x, y, true, rugRegion, 3f, 2f, gridArray,true);
		world.addObject(rug);
	}

	/**
	 * add a visual object in the world
	 * @param x x pos
	 * @param y y pos
	 * @param name name of the object
	 * @param walkable walkable? y/n
	 * @param sizeX width of the object
	 * @param sizeY height of the object
	 */
	public void addVisualObject(int x,int y,String name,boolean walkable,float sizeX,float sizeY){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion region = atlas.findRegion(name);
		GridPoint2[] gridArray = new GridPoint2[(int)sizeX*(int)sizeY];
		int index = 0;
		for(int i = 0;i<sizeX;i++){
			for(int j = 0;j<sizeY;j++){
				gridArray[index] = new GridPoint2(i,j);
				index++;
			}
		}
		WorldObject obj = new WorldObject(name,x,y,walkable,region,sizeX,sizeY,gridArray,true);
		this.addObject(obj);
	}

	/**
	 * adds an object which is next to a wall
	 * @param x
	 * @param y
	 * @param name
	 * @param walkable
	 * @param sizeX
	 * @param sizeY
	 */
	public void addWallObject(int x,int y,String name,boolean walkable,float sizeX,float sizeY){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion region = atlas.findRegion(name);

		WorldObject obj = new WorldObject(name,x,y,walkable,region,sizeX,sizeY,new GridPoint2(0,0),false);
		this.addObject(obj);
	}

	/**
	 * creates an visual object
	 * @param type
	 * @param x
	 * @param y
	 * @param walkable
	 * @param name
	 * @param sizeX
	 * @param sizeY
	 * @param visible
	 * @return
	 */
	public WorldObject createObject(String type, int x,int y,boolean walkable, String name,float sizeX,float sizeY,boolean visible){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion region = atlas.findRegion(name);
		WorldObject obj;
		if(walkable){
			obj = new WorldObject(type,x,y,walkable,region,sizeX,sizeY,new GridPoint2(0,0),visible);
		}
		else{
			GridPoint2[] gridArray = new GridPoint2[(int)sizeX*(int)sizeY];
			int index = 0;
			for(int i = 0;i<sizeX;i++){
				for(int j = 0;j<sizeY;j++){
					gridArray[index] = new GridPoint2(i,j);
					index++;
				}
			}
			obj = new WorldObject(type,x,y,walkable,region,sizeX,sizeY,gridArray,visible);
			return obj;
		}
		return obj;
	}

	/**
	 * add an actor to this world
	 * @param a actor
	 */
	public void addActor(Actor a) {
		map.getTile(a.getX(), a.getY()).setActor(a);
		actors.add(a);
	}

	/**
	 * add an actor with a behavior to this world
	 * @param a actor
	 */
	public void addActor(Actor a, ActorBehavior b) {
		addActor(a);
		brains.put(a, b);
	}

	/**
	 * add an index to this world
	 * @param i index
	 */
	public void addIndex(Index i){
		map.getTile(i.getX(), i.getY()).setIndex(i);
	}

	/**
	 * add a worldObject
	 * @param o object
	 */
	public void addObject(WorldObject o) {
		for (GridPoint2 p : o.getTiles()) {
			map.getTile(o.getX()+p.x, o.getY()+p.y).setObject(o);
		}
		objects.add(o);
	}

	/**
	 * remove a worldObject
	 * @param o object
	 * @return removed object
	 */
	public WorldObject removeObject(WorldObject o){
		if(o == null){
			return null;
		}
		for (GridPoint2 p : o.getTiles()) {
			map.getTile(o.getX()+p.x, o.getY()+p.y).setObject(null);
		}
		objects.remove(o);
		return o;
	}

	/**
	 * removes the actor from the map
	 * @param actor
	 */
	public void removeActor(Actor actor) {
		map.getTile(actor.getX(), actor.getY()).setActor(null);
		actors.remove(actor);
		if (brains.containsKey(actor)) {
			brains.remove(actor);
		}
	}

	/**
	 * removes the index from the map
	 * @param index
	 */
	public void removeIndex(Index index) {
		map.getTile(index.getX(), index.getY()).setIndex(null);
		this.index = null;
	}

	/**
	 * updates ais
	 * @param delta
	 */
	public void update(float delta) {
		for (Actor a : actors) {
			if (brains.containsKey(a)) {
				brains.get(a).update(delta);
			}
			a.update(delta);
		}
		for (WorldObject o : objects) {
			o.update(delta);
		}
		if(index != null){
			index.update(delta);
		}

	}

	/**
	 * get the worlds TileMap
	 * @return TileMap
	 */
	public TileMap getMap() {
		return map;
	}

	/**
	 * Get actors present in this world
	 * @return list of actors
	 */
	public List<Actor> getActors() {
		return actors;
	}

	/**
	 * (probably not used)
	 * Get objects present in this world
	 * @return list of objects
	 */
	public List<WorldObject> getWorldObjects() {
		return objects;
	}
	
	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * get the actor playing this world
	 * @return player actor
	 */
	public Character getPlayer(){
        for(Actor actor : actors){
            if(actor.isPlayer){
                return actor.character;
            }
        }
        return null;
    }

    public AssetManager getAssetManager(){
		return this.assetManager;
	}



	/**
	 * Creates a line of path Terrain
	 * @param startX
	 * @param startY
	 * @param length
	 * @param rowOrCol
	 *        true for row false for columns.
	 */
	public void setPath(int startX, int startY, int length, boolean rowOrCol){
		//rowOrCol is true so we make a row path. Row will be constant increment Columns
		if(rowOrCol==true){
			for(int i=0;i<length;i++){
				getMap().getTile(startX,startY).setTerrain(TERRAIN.DIRT_PATH_MIDDLE);
				startX++;
			}

		}
		else{
			for(int i=0;i<length;i++){
				getMap().getTile(startX,startY).setTerrain(TERRAIN.DIRT_PATH_MIDDLE);
				startY++;
			}
		}

	}

    /**
     * Creates a line of ice Terrain
     * @param startX
     * @param startY
     * @param length
     * @param row
     *        true for row false for columns.
     */
    public void setIcePath(int startX, int startY, int length, boolean row){
        //rowOrCol is true so we make a row path. Row will be constant increment Columns
        if(row==true){
            for(int i=0;i<length;i++){
                getMap().getTile(startX,startY).setTerrain(TERRAIN.ice);
                getMap().setTile(new IceTile(TERRAIN.ice),startX,startY);;
                startX++;
            }

        }
        else{
            for(int i=0;i<length;i++){
                getMap().getTile(startX,startY).setTerrain(TERRAIN.ice);
                getMap().setTile(new IceTile(TERRAIN.ice),startX,startY);;
                startY++;
            }
        }

    }

	/**
	 * Creates a boulder puzzle
	 */
    public void addBoulderPuzzle1(){
		addBoulderActor(37,43);addBoulderActor(37,44);addBoulderActor(37,45);addBoulderActor(37,46);addBoulderActor(37,47);addBoulderActor(39,43);addBoulderActor(39,44);addBoulderActor(39,45);
		addBoulderActor(39,46);addBoulderActor(39,47);addBoulderActor(38,48);addBoulderActor(38,49);addBoulderActor(40,48);addBoulderActor(40,47);addBoulderActor(37,51);addBoulderActor(37,52);
		addBoulderActor(38,52);addBoulderActor(37,53);addBoulderActor(37,54);addBoulderActor(37,55);addBoulderActor(40,51);addBoulderActor(40,52);addBoulderActor(39,53);addBoulderActor(39,54);addBoulderActor(38,55);
    }

    public void addMaze(){
		addVisualObject(37,33,"snowRock",false,1f,1f);
		addVisualObject(37,32,"snowRock",false,1f,1f);
		addVisualObject(37,31,"snowRock",false,1f,1f);
		addVisualObject(37,30,"snowRock",false,1f,1f);
		addVisualObject(37,28,"snowRock",false,1f,1f);
		addVisualObject(37,26,"snowRock",false,1f,1f);

		addVisualObject(36,34,"snowRock",false,1f,1f);
		addVisualObject(40,33,"snowRock",false,1f,1f);
		addVisualObject(40,32,"snowRock",false,1f,1f);
		addVisualObject(40,31,"snowRock",false,1f,1f);
		addVisualObject(40,29,"snowRock",false,1f,1f);
		addVisualObject(40,28,"snowRock",false,1f,1f);
		addVisualObject(40,27,"snowRock",false,1f,1f);
		addVisualObject(40,26,"snowRock",false,1f,1f);
		addVisualObject(40,25,"snowRock",false,1f,1f);


		addVisualObject(30,31,"snowRock",false,1f,1f);
		addVisualObject(30,28,"snowRock",false,1f,1f);
		addVisualObject(30,26,"snowRock",false,1f,1f);

		addVisualObject(31,33,"snowRock",false,1f,1f);
		addVisualObject(31,31,"snowRock",false,1f,1f);
		addVisualObject(31,29,"snowRock",false,1f,1f);
		addVisualObject(31,28,"snowRock",false,1f,1f);
		addVisualObject(31,26,"snowRock",false,1f,1f);

		addVisualObject(32,33,"snowRock",false,1f,1f);
		addVisualObject(32,31,"snowRock",false,1f,1f);
		addVisualObject(32,29,"snowRock",false,1f,1f);
		addVisualObject(32,28,"snowRock",false,1f,1f);
		addVisualObject(32,26,"snowRock",false,1f,1f);

		addVisualObject(33,33,"snowRock",false,1f,1f);
		addVisualObject(33,31,"snowRock",false,1f,1f);

		addVisualObject(34,33,"snowRock",false,1f,1f);
		addVisualObject(34,31,"snowRock",false,1f,1f);
		addVisualObject(34,29,"snowRock",false,1f,1f);
		addVisualObject(34,28,"snowRock",false,1f,1f);
		addVisualObject(34,27,"snowRock",false,1f,1f);
		addVisualObject(34,26,"snowRock",false,1f,1f);
		addVisualObject(34,25,"snowRock",false,1f,1f);

		addVisualObject(35,33,"snowRock",false,1f,1f);
		addVisualObject(35,26,"snowRock",false,1f,1f);

		addVisualObject(36,33,"snowRock",false,1f,1f);
		addVisualObject(36,32,"snowRock",false,1f,1f);
		addVisualObject(36,30,"snowRock",false,1f,1f);
		addVisualObject(36,28,"snowRock",false,1f,1f);
		addVisualObject(36,26,"snowRock",false,1f,1f);

		addVisualObject(38,28,"snowRock",false,1f,1f);
		addVisualObject(38,27,"snowRock",false,1f,1f);
		addVisualObject(38,26,"snowRock",false,1f,1f);

		addVisualObject(41,31,"snowRock",false,1f,1f);
		addVisualObject(41,29,"snowRock",false,1f,1f);

		addVisualObject(42,34,"snowRock",false,1f,1f);
		addVisualObject(42,33,"snowRock",false,1f,1f);
		addVisualObject(42,29,"snowRock",false,1f,1f);
		addVisualObject(42,27,"snowRock",false,1f,1f);
		addVisualObject(42,26,"snowRock",false,1f,1f);

		addVisualObject(43,33,"snowRock",false,1f,1f);
		addVisualObject(43,31,"snowRock",false,1f,1f);
		addVisualObject(43,30,"snowRock",false,1f,1f);
		addVisualObject(43,29,"snowRock",false,1f,1f);
		addVisualObject(43,27,"snowRock",false,1f,1f);
		addVisualObject(43,26,"snowRock",false,1f,1f);
		addVisualObject(43,25,"snowRock",false,1f,1f);

		addVisualObject(44,33,"snowRock",false,1f,1f);
		addVisualObject(44,31,"snowRock",false,1f,1f);
		addVisualObject(44,30,"snowRock",false,1f,1f);

		addVisualObject(45,33,"snowRock",false,1f,1f);
		addVisualObject(45,30,"snowRock",false,1f,1f);
		addVisualObject(45,29,"snowRock",false,1f,1f);
		addVisualObject(45,28,"snowRock",false,1f,1f);
		addVisualObject(45,26,"snowRock",false,1f,1f);

		addVisualObject(46,33,"snowRock",false,1f,1f);
		addVisualObject(46,32,"snowRock",false,1f,1f);
		addVisualObject(46,26,"snowRock",false,1f,1f);
		addVisualObject(46,25,"snowRock",false,1f,1f);

		addVisualObject(47,32,"snowRock",false,1f,1f);
		addVisualObject(47,31,"snowRock",false,1f,1f);
		addVisualObject(47,30,"snowRock",false,1f,1f);
		addVisualObject(47,29,"snowRock",false,1f,1f);
		addVisualObject(47,28,"snowRock",false,1f,1f);

    }

	public int[] getSwitchIDs(){
		int[] result = new int[11];
		Random rand = new Random();
		int x = rand.nextInt(11);
		result[x]=1;
		for(int i =0;i<11;i++){
			if(result[i]!=1){
				result[i]=2;
			}
		}
		return result;
	}




}
