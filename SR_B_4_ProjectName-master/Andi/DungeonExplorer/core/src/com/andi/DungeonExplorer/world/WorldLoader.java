package com.andi.DungeonExplorer.world;

import com.andi.DungeonExplorer.actor.Character.Monster;
import com.andi.DungeonExplorer.actor.LimitedAggressiveBehavior;
import com.andi.DungeonExplorer.actor.RunAwayBehavior;
import com.andi.DungeonExplorer.actor.SeekBehavior;
import com.andi.DungeonExplorer.actor.YetiBehavior;
import com.andi.DungeonExplorer.dialogue.ChoiceDialogueNode;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.Character.*;
import com.andi.DungeonExplorer.actor.LimitedWalkingBehavior;

import com.andi.DungeonExplorer.world.Equipment.Bow;
import com.andi.DungeonExplorer.world.InteractableItems.Chest;
import com.andi.DungeonExplorer.world.InteractableItems.ClueSign;
import com.andi.DungeonExplorer.world.InteractableItems.CrystalPiece;
import com.andi.DungeonExplorer.world.InteractableItems.Door;
import com.andi.DungeonExplorer.world.Equipment.Key;
import com.andi.DungeonExplorer.world.SpecialTiles.IceTile;
import com.andi.DungeonExplorer.world.SpecialTiles.SwitchTile;
import com.andi.DungeonExplorer.world.SpecialTiles.TeleportTileDoor;
import com.andi.DungeonExplorer.world.SpecialTiles.TeleportTilePortal;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

        import java.util.Random;

/**
 * Created by brytonhayes on 10/23/17.
 */

public class WorldLoader {

    public static World snowMultiplayerMap(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){
        World snowMap = new World("snow_Map",250,250, assetManager);

        for (int x = 0; x <100; x++) {
            for (int y = 0; y<100; y++) {
                if (y==0) {
                    snowMap.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                if(y>=1){
                    snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),x,y);
                }
            }
        }

        snowMap.add4x19SnowTreeRegion(14,58);
        snowMap.add4x19SnowTreeRegion(60,58);
        snowMap.add6x5SnowTreeRegion(14,54);
        snowMap.add6x5SnowTreeRegion(20,54);
        snowMap.add6x5SnowTreeRegion(26,54);
        snowMap.add6x5SnowTreeRegion(46,54);
        snowMap.add6x5SnowTreeRegion(52,54);
        snowMap.add6x5SnowTreeRegion(58,54);
        snowMap.add6x5SnowTreeRegion(14,76);
        snowMap.add6x5SnowTreeRegion(20,76);
        snowMap.add6x5SnowTreeRegion(26,76);
        snowMap.add6x5SnowTreeRegion(32,76);
        snowMap.add6x5SnowTreeRegion(38,76);
        snowMap.add6x5SnowTreeRegion(46,76);
        snowMap.add6x5SnowTreeRegion(52,76);
        snowMap.add6x5SnowTreeRegion(58,76);
        snowMap.add6x5SnowTreeRegion(30,21);
        snowMap.add6x5SnowTreeRegion(36,21);
        snowMap.add6x5SnowTreeRegion(42,21);
        snowMap.add6x5SnowTreeRegion(30,35);
        snowMap.add6x5SnowTreeRegion(42,35);
        snowMap.add4x19SnowTreeRegion(26,21);
        snowMap.add4x19SnowTreeRegion(48,21);

        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,1),45,25);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),35,34);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),36,31);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),30,29);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),30,27);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),30,25);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),37,27);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),41,34);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),44,29);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),42,25);
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,2),47,25);
        snowMap.addWall(36,35,6,23,3);
        snowMap.addSnowStairs(37,34,1);
        snowMap.addBoulderPuzzle1();
        snowMap.addMaze();

        snowMap.getMap().getTile(37,57).setWalkable(true);
        snowMap.getMap().getTile(38,57).setWalkable(true);
        snowMap.getMap().getTile(39,57).setWalkable(true);
        snowMap.getMap().getTile(40,57).setWalkable(true);

        for(int i = 18;i<60;i++){
            for(int j = 58;j<76;j++){
                snowMap.getMap().setTile(new IceTile(TERRAIN.ice),i,j);
            }
        }

        snowMap.add1x5SnowTreeRegion(42,54);
        snowMap.add1x5SnowTreeRegion(44,54);
        snowMap.add1x5SnowTreeRegion(32,54);
        snowMap.add1x5SnowTreeRegion(34,54);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),37,58);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),38,58);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),39,58);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),40,58);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),39,64);
        snowMap.addVisualObject(39,64,"snowRock",false,1f,1f);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),55,62);
        snowMap.addVisualObject(55,62,"snowRock",false,1f,1f);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),54,68);
        snowMap.addVisualObject(54,68,"snowRock",false,1f,1f);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,67);
        snowMap.addVisualObject(45,67,"snowRock",false,1f,1f);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),46,72);
        snowMap.addVisualObject(46,72,"snowRock",false,1f,1f);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),23,63);
        snowMap.addVisualObject(23,63,"snowRock",false,1f,1f);
        snowMap.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),24,72);
        snowMap.addVisualObject(24,72,"snowRock",false,1f,1f);
        snowMap.addSpikes(TERRAIN.SNOW_TILE,player,broadcaster,37,59);
        for(int i =19;i<=58;i++){
            if(i==39){
                continue;
            }
            snowMap.addSpikes(TERRAIN.SNOW_TILE,player,broadcaster,i,59);
        }
        for(int i =19;i<=58;i++){
            if(i==45){
                continue;
            }
            snowMap.addSpikes(TERRAIN.SNOW_TILE,player,broadcaster,i,74);
        }
        for(int i =59;i<=74;i++){
            snowMap.addSpikes(TERRAIN.SNOW_TILE,player,broadcaster,19,i);
        }
        for(int i =59;i<=74;i++){
            snowMap.addSpikes(TERRAIN.SNOW_TILE,player,broadcaster,58,i);
        }
        snowMap.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,1),45,78);

        return snowMap;
    }

    /**
     * loads the second level of the game (ice room)
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World Campaign2(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){
        World Campaign2 = new World("C_2",100,100, assetManager);

        for (int x = 0; x <100; x++) {
            for (int y = 0; y<100; y++) {
                if (y==0) {
                    Campaign2.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                if(y>=1){
                    Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),x,y);
                }



            }
        }
        Campaign2.add4x19SnowTreeRegion(14,58);
        Campaign2.add4x19SnowTreeRegion(60,58);
        Campaign2.add6x5SnowTreeRegion(14,54);
        Campaign2.add6x5SnowTreeRegion(20,54);
        Campaign2.add6x5SnowTreeRegion(26,54);
        Campaign2.add6x5SnowTreeRegion(46,54);
        Campaign2.add6x5SnowTreeRegion(52,54);
        Campaign2.add6x5SnowTreeRegion(58,54);
        Campaign2.add6x5SnowTreeRegion(14,76);
        Campaign2.add6x5SnowTreeRegion(20,76);
        Campaign2.add6x5SnowTreeRegion(26,76);
        Campaign2.add6x5SnowTreeRegion(32,76);
        Campaign2.add6x5SnowTreeRegion(38,76);
        Campaign2.add6x5SnowTreeRegion(46,76);
        Campaign2.add6x5SnowTreeRegion(52,76);
        Campaign2.add6x5SnowTreeRegion(58,76);
        Campaign2.add6x5SnowTreeRegion(30,21);
        Campaign2.add6x5SnowTreeRegion(36,21);
        Campaign2.add6x5SnowTreeRegion(42,21);
        Campaign2.add6x5SnowTreeRegion(30,35);
        Campaign2.add6x5SnowTreeRegion(42,35);
        Campaign2.add4x19SnowTreeRegion(26,21);
        Campaign2.add4x19SnowTreeRegion(48,21);


        int[] idArr = Campaign2.getSwitchIDs();
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[0]),45,25);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[1]),35,34);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[2]),36,31);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[3]),30,29);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[4]),30,27);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[5]),30,25);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[6]),37,27);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[7]),41,34);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[8]),44,29);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[9]),42,25);
        Campaign2.getMap().setTile(new SwitchTile(TERRAIN.SNOW_SWITCH,idArr[10]),47,25);
        Campaign2.addWall(36,35,6,23,3);
        Campaign2.addSnowStairs(37,34,1);
        Campaign2.addBoulderPuzzle1();
        Campaign2.addMaze();

        Campaign2.getMap().getTile(37,57).setWalkable(true);
        Campaign2.getMap().getTile(38,57).setWalkable(true);
        Campaign2.getMap().getTile(39,57).setWalkable(true);
        Campaign2.getMap().getTile(40,57).setWalkable(true);

        for(int i = 18;i<60;i++){
            for(int j = 58;j<76;j++){
                if(i>=46 && i<52 && j>=61 && j<=67){
                    if(i==47||i==48){
                        Campaign2.getMap().setTile(new IceTile(TERRAIN.ice),i,j);
                    }
                    else{
                        continue;
                    }
                }
                if(i>=21 && i<29 && j>=61 && j<=62){
                    continue;
                }
                Campaign2.getMap().setTile(new IceTile(TERRAIN.ice),i,j);
            }
        }

        Campaign2.add1x5SnowTreeRegion(42,54);
        Campaign2.add1x5SnowTreeRegion(44,54);
        Campaign2.add1x5SnowTreeRegion(32,54);
        Campaign2.add1x5SnowTreeRegion(34,54);
        for(int i=58;i<76;i++){
            if(i==66||i==73||i==70||i==71){
                continue;
            }
            else{
                Campaign2.addVisualObject(39,i,"snowRock",false,1f,1f);
            }
        }
        for(int i=58;i<76;i++){
            if(i==66||i==73||i==70||i==71){
                continue;
            }
            else{
                Campaign2.addVisualObject(40,i,"snowRock",false,1f,1f);
            }
        }
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),44,73);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),48,74);
        Campaign2.addVisualObject(48,74,"snowRock",false,1f,1f);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),46,71);
        Campaign2.addBoulderActor(38,66);
        Campaign2.addBoulderActor(34,66);
        Campaign2.addBoulderActor(20,74);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),21,73);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),22,73);
        Campaign2.addBoulderActor(22,73);
        Campaign2.addBoulderActor(28,62);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),28,68);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),28,72);
        Campaign2.addVisualObject(28,72,"snowRock",false,1f,1f);
        Campaign2.addSpikes(TERRAIN.SNOW_TILE,player,broadcaster,21,62);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),27,71);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,72);
        Campaign2.addVisualObject(45,72,"snowRock",false,1f,1f);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),49,70);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),28,66);
        Campaign2.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),44,74);
        Campaign2.addVisualObject(44,74,"snowRock",false,1f,1f);
        Campaign2.addVisualObject(42,80,"1x2SnowTrees",false,2f,5f);
        Campaign2.addVisualObject(42,84,"5x6SnowTrees",false,6f,5f);
        Campaign2.addVisualObject(46,80,"1x2SnowTrees",false,2f,5f);


        Campaign2.getMap().setTile(new TeleportTilePortal(TERRAIN.ice,player,broadcaster,"Yeti Room",5,0,DIRECTION.SOUTH,Color.BLACK),45,83);
        Campaign2.addPortal(Campaign2,45,83,"hole",1f,1f);
        return Campaign2;
    }

    /**
     * loads the outdoor level
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World outdoorLevel(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster) {
        World world1 = new World("Tutorial",20,20, assetManager);
        for (int xi = 0; xi < 20; xi++) {
            for (int yi = 0; yi < 20; yi++) {
                if (xi==0 || xi==18) {
                    if (world1.getMap().getTile(xi, yi).getObject() == null) {
                        world1.addTree(xi,yi);
                    }
                }
                if (yi==0||yi==18) {
                    if (world1.getMap().getTile(xi, yi).getObject() == null) {
                        world1.addTree(xi,yi);
                    }
                }
            }
        }


       world1.addBoulderActor(6,7);


        world1.addVisualObject(2,17,"bush",false,2f,2f);
        world1.addVisualObject(4,17,"bush",false,2f,2f);
        world1.addVisualObject(6,17,"bush",false,2f,2f);
        world1.addVisualObject(8,17,"bush",false,2f,2f);
        world1.addVisualObject(10,17,"bush",false,2f,2f);
        world1.addVisualObject(12,17,"bush",false,2f,2f);
        world1.addVisualObject(14,17,"bush",false,2f,2f);
        world1.addVisualObject(16,17,"bush",false,2f,2f);

        world1.addVisualObject(5,10,"multiTree",false,2f,3f);
        world1.addVisualObject(16,15,"woodAxe",false,2f,2f);
        world1.addVisualObject(5,15,"flowerB",true,1f,2f);
        world1.addVisualObject(10,15,"flowerO",true,1f,2f);
        world1.addVisualObject(5,8,"flowerB",true,1f,2f);
        world1.addVisualObject(10,3,"flowerO",true,1f,2f);
        world1.addVisualObject(12,3,"lake",false,4f,3f);



        world1.addHouse(10,10);

        world1.addFlowers(5,6);
        world1.addFlowers(2,8);
        world1.addFlowers(4,12);
        world1.addFlowers(8,3);
        world1.addFlowers(10,5);
        world1.addFlowers(14,7);

        Key key1 = world1.addKey(1,1,"Gold Key",2);
        world1.addObject(world1.addKey(3,3,"Bronze Key",1));

        world1.addClueSign(3,2," Press G to grab objects \n Press d to  drop objects \n Press X to attack npc");

        world1.getMap().setTile(new SwitchTile(TERRAIN.GRASS_0,1),6,6);
        Door door = new Door(13,10, world1.doorOpen, world1.doorClose,2,1,broadcaster,5,0,"Dungeon Entrance");
        world1.addObject(door);
        
        world1.addSpikes(TERRAIN.GRASS_0,player,broadcaster,7,7);

        Chest chest = new Chest(5,5,world1.chestOpen,1,broadcaster);

        chest.addObject(key1);
        world1.addObject(chest);

        world1.setPath(13,8,2,false);
        world1.setPath(16,8,7,false);
        world1.setPath(8,8,9,true);
        world1.setPath(11,3,6,false);
        world1.setPath(8,4,5,false);
        world1.setPath(5,4,4,true);
        return world1;
    }

    /**
     * Loads the star room
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World startRoom(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster) {
        World world2 = new World("Dungeon Entrance",11,11, assetManager);//Define world size 11
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (y==0) {
                    world2.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                if (y > 0) {
                    if (Math.random() > 0.05d) {
                        world2.getMap().setTile(new Tile("indoorTiles",TERRAIN.INDOOR_TILES, true), x, y);
                    } else {
                        world2.getMap().setTile(new Tile("indoorTilesBlood",TERRAIN.INDOOR_TILES_BLOOD, true), x, y);
                    }
                }
            }
        }
        world2.addWall(0, 8, 11, 3, 0);

        Actor enemy = new Actor(world2, 4, 4, world2.addAnimationSet("rick"));

        enemy.name = "Evil Rick";


        //adds dialogue
        Dialogue greeting = new Dialogue();
        LinearDialogueNode node1 = new LinearDialogueNode("Alright Morty, ready to go on an adventure?", 0,false);
        ChoiceDialogueNode node2 = new ChoiceDialogueNode("Let's go Morty?", 1,false);
        LinearDialogueNode node3 = new LinearDialogueNode("Wubba-lubba-dub-dub! ", 2,false);

        LinearDialogueNode node4 = new LinearDialogueNode("I'm sorry, Morty.\n You have no choice.", 3,false);
        node2.addChoice("Oh jeez... okay",2);
        node2.addChoice("No Rick!, I've had enough of your adventures!",3);

        node1.setPointer(1);
        greeting.addNode(node1);
        greeting.addNode(node2);
        greeting.addNode(node3);
        greeting.addNode(node4);
        enemy.setDialogue(greeting);
        world2.addActor(enemy);


        world2.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",18,10, DIRECTION.NORTH, Color.WHITE),5,8);
        world2.getMap().setTile(new TeleportTileDoor(null, player, broadcaster, "Tutorial", 13,10,DIRECTION.SOUTH,Color.WHITE), 5, 0);
        world2.addRug(world2,4,0);
        world2.addPortal(world2,5,8,"portal",1f,2f);

        return world2;
    }

    /**
     * loads the main star room
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World mainRoom(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){

        World mainRoom = new World("mainRoom",38,21, assetManager);
        for (int x = 0; x <38; x++) {
            for (int y = 0; y<21; y++) {
                if (y==0) {
                    mainRoom.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                else{
                    mainRoom.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int blackCounterLeft = 17;
        int blackCounterRight = 20;
        for(int i = 0;i<8;i++){
            for(int j = 1;j<37;j++){
                if(j>=blackCounterLeft&&j<blackCounterRight){
                    mainRoom.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
                }

            }
            blackCounterLeft-=2;
            blackCounterRight+=2;
        }
        int blackCounter = 0;
        int tileCounter = 37;
        for(int i = 8;i<21;i++){
            for(int j=blackCounter;j<tileCounter;j++){
                mainRoom.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter++;
            tileCounter--;
        }
        int startWall = 0;
        int endWall = 36;
        for(int i = 8;i<21;i++){
            for(int j = 0;j<37;j++){
                if(i == 20 && j>startWall && j<endWall){
                    mainRoom.addWallObject(j,i,"middleWall",false,1f,3f);
                }
                if(j == startWall){
                    mainRoom.addWallObject(j,i,"middleWall",false,1f,3f);
                }
                if(j == endWall){
                    mainRoom.addWallObject(j,i,"middleWall",false,1f,3f);
                }
            }
            startWall++;
            endWall--;
        }

        mainRoom.getMap().setTile(new TeleportTilePortal(null, player, broadcaster,"upperStar", 20,0,DIRECTION.NORTH,Color.WHITE), 18, 19);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"topRightStar",7,9,DIRECTION.EAST,Color.BLACK),30,13);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"topLeftStar",53,8,DIRECTION.WEST,Color.BLACK),6,13);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"bottomRightStar",9,27,DIRECTION.SOUTH,Color.BLACK),25,3);
        mainRoom.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"bottomLeftStar",28,27,DIRECTION.SOUTH,Color.BLACK),11,3);
        mainRoom.getMap().setTile(new TeleportTilePortal(TERRAIN.DUNGEON_FLOOR,player,broadcaster,"Reaper Room",5,0,DIRECTION.SOUTH,Color.BLACK),18,0);
        mainRoom.addWallObject(17,19,"angelStatue",false,1f,2f);
        mainRoom.addWallObject(19,19,"angelStatue",false,1f,2f);

        mainRoom.addVisualObject(17,10,"bigStar",true,3f,2f);

        mainRoom.addVisualObject(24,3,"skeleton",false,1f,1f);
        mainRoom.addVisualObject(26,4,"skeleton",false,1f,1f);
        mainRoom.addVisualObject(10,4,"skeleton",false,1f,1f);
        mainRoom.addVisualObject(12,3,"skeleton",false,1f,1f);

        mainRoom.addWallObject(29,14,"armorStatue",false,1f,2f);
        mainRoom.addWallObject(31,12,"armorStatue",false,1f,2f);
        mainRoom.addWallObject(7,14,"armorStatue",false,1f,2f);
        mainRoom.addWallObject(5,12,"armorStatue",false,1f,2f);

        mainRoom.addPortal(mainRoom,18,19,"smallStar",1f,1f);//upperStar
        mainRoom.addPortal(mainRoom,25,3,"smallStar",1f,1f);//bottomRightStar
        mainRoom.addPortal(mainRoom,11,3,"smallStar",1f,1f);//bottomLeftStar
        mainRoom.addPortal(mainRoom,30,13,"smallStar",1f,1f);//topRightStar
        mainRoom.addPortal(mainRoom,6,13,"smallStar",1f,1f);//topLeftStar

        mainRoom.addCrystal(18,12,1,broadcaster);//upper
        mainRoom.addCrystal(20,11,2,broadcaster);//topright
        mainRoom.addCrystal(16,11,3,broadcaster);//topleft
        mainRoom.addCrystal(17,9,4,broadcaster);//bottomleft
        mainRoom.addCrystal(19,9,5,broadcaster);//bottomright


        return mainRoom;
    }

    /**
     * loads the upper star room
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World upperStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster) {
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World upperStar = new World("upperStar",41,30, assetManager);
        for (int x = 0; x <41; x++) {
            for (int y = 0; y<30; y++) {
                if (y==0) {
                    upperStar.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                else{
                    upperStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int blackCounter = 0;
        int tileCounter = 41;
        for(int i = 0;i<41;i++){
            for(int j =blackCounter;j<tileCounter;j++){
                upperStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter++;
            tileCounter--;

        }
        int startWall = 0;
        int endWall = 40;
        for(int i = 0;i<20;i++){
            for(int j = 0;j<41;j++){
                if(j == startWall){
                    upperStar.addVisualObject(j,i,"middleWall",false,1f,3f);
                }
                else if(j == endWall){
                    upperStar.addVisualObject(j,i,"middleWall",false,1f,3f);
                }



            }
            startWall++;
            endWall--;
        }
        upperStar.addVisualObject(20,20,"middleWall",false,1f,3f);
        upperStar.getMap().setTile(new TeleportTilePortal(null, player, broadcaster, "mainRoom", 18,19,DIRECTION.SOUTH,Color.WHITE), 20, 0);
        upperStar.addVisualObject(20,0,"smallStar",true,1f,1f);


        Actor actor = new Actor(upperStar, 20, 16, upperStar.addAnimationSet("rick"));
        LimitedWalkingBehavior brain = new LimitedWalkingBehavior(actor, 1, 1, 1, 1, 0.3f, 1f, new Random());
        upperStar.addActor(actor, brain);
        //adds dialogue
        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,1);
        actor.addObject(crystalPiece);
        Dialogue greeting = new Dialogue();
        LinearDialogueNode node1 = new LinearDialogueNode("Answer this question for the prize", 0,false);
        ChoiceDialogueNode node2 = new ChoiceDialogueNode("Whos is the best TA?", 1,false);
        LinearDialogueNode node3 = new LinearDialogueNode("Correct! ", 2,true);
        LinearDialogueNode node4 = new LinearDialogueNode("Mitra is not a TA.", 3,false);
        LinearDialogueNode node5 = new LinearDialogueNode("Gavin is not the best.", 4,false);
        node2.addChoice("Shaiqur",2);
        node2.addChoice("Mitra",3);
        node2.addChoice("Gavin",4);
        node1.setPointer(1);
        greeting.addNode(node1);
        greeting.addNode(node2);
        greeting.addNode(node3);
        greeting.addNode(node4);
        greeting.addNode(node5);
        node3.addActor(actor);


        greeting.addNode(node1);
        greeting.addNode(node2);
        greeting.addNode(node3);
        greeting.addNode(node4);

        actor.setDialogue(greeting);
        return upperStar;

    }

    /**
     * loads the top right star map
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World topRightStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World topRightStar = new World("topRightStar",62,19, assetManager);
        for (int x = 0; x <62; x++) {
            for (int y = 0; y<19; y++) {
                if (y==0) {
                    topRightStar.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                else{
                    topRightStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }

        }
        int tileCounter = 15;
        for(int i = 0;i<16;i++){
            for(int j = 16;j>tileCounter;j--){
                topRightStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),i,j);
            }
            tileCounter--;
        }
        int blackCounter = 20;
        for(int i = 2;i<16;i++){
            for(int j = 16;j<blackCounter &&j<62;j++){
                topRightStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter+=3;
        }
        topRightStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",30,13,DIRECTION.WEST,Color.BLACK),7,9);
        topRightStar.addPortal(topRightStar,7,9,"smallStar",1f,1f);

        topRightStar.addWall(0,16,62,3,1);
        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,2);
        //enemy1
        Actor enemy = new Actor(topRightStar, 50, 13, topRightStar.addAnimationSet("skeleton"));
        enemy.name = "Ntcarter";
        enemy.character = new Skeleton(8, 1, 3000, enemy);
        //enemy2
        Actor enemy2 = new Actor(topRightStar, 30, 13, topRightStar.addAnimationSet("skeleton"));
        enemy2.name = "Ntcarter?";
        enemy2.character = new Skeleton(8, 1, 3000, enemy2);
        enemy.character.name = "Ntcarter";
        enemy2.character.name = "Ntcarter?";
        //Evil Rick is a level 5 skeleton.
        enemy.addObject(crystalPiece);
        RunAwayBehavior brain = new RunAwayBehavior(enemy, 2, 2, 2, 2, 0.3f, 1f, new Random(), 20);
        LimitedWalkingBehavior brain2 = new SeekBehavior(enemy2, 1, 1, 1, 1, 0.3f, 1f, new Random(), 15, 3);
        topRightStar.addActor(enemy, brain);
        topRightStar.addActor(enemy2, brain2);

        return topRightStar;
    }

    /**
     * loads the top left star map
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World topLeftStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World topLeftStar = new World("topLeftStar",62,19, assetManager);
        for (int x = 0; x <62; x++) {
            for (int y = 0; y<19; y++) {
                if (y==0) {
                    topLeftStar.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                else{
                    topLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }

        }
        int tileCounter = 46;
        for(int i = 0;i<16;i++){
            for(int j = 45;j<tileCounter;j++){
                topLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            tileCounter++;
        }
        int blackCounter = 41;
        for(int i = 1;i<16;i++){
            for(int j = 45;j>blackCounter&&j>0;j--){
                topLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            blackCounter-=3;
        }
        topLeftStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",6,13,DIRECTION.EAST,Color.BLACK),53,8);
        topLeftStar.addPortal(topLeftStar,53,8,"smallStar",1f,1f);

        topLeftStar.addWall(0,16,62,3,1);
        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,3);
        //enemy1
        Actor enemy = new Actor(topLeftStar, 45, 13, topLeftStar.addAnimationSet("skeleton"));
        enemy.name = "Bryton";
        //enemy2
        Actor enemy2 = new Actor(topLeftStar, 20, 13, topLeftStar.addAnimationSet("skeleton"));
        enemy.name = "Bryton?";
        enemy2.character = new Skeleton(8, 1, 3000, enemy2);
        //Evil Rick is a level 8 skeleton.
        enemy.character = new Skeleton(8, 1, 3000, enemy);
        enemy.character.name = "Bryton";
        enemy2.character.name = "Bryton?";
        enemy.addObject(crystalPiece);
        LimitedAggressiveBehavior brain = new LimitedAggressiveBehavior(enemy2, 1, 1, 1, 1, 0.3f, 1f, new Random(), 5, 1);
        SeekBehavior brain2 = new SeekBehavior(enemy, 1, 1, 1, 1, 0.3f, 1f, new Random(), 15, 3);
        topLeftStar.addActor(enemy, brain2);
        topLeftStar.addActor(enemy2, brain);
        return topLeftStar;
    }

    /**
     * Loads the bottom right star
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World bottomRightStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){

        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World bottomRightStar = new World("bottomRightStar",38,38, assetManager);
        for (int x = 0; x <38; x++) {
            for (int y = 0; y<38; y++) {
                if (y==0) {
                    bottomRightStar.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                else{
                    bottomRightStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int tileCounter = 35;
        for(int i = 0;i<38;i++){
            for(int j = 37;j>tileCounter;j--){
                bottomRightStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            if(tileCounter >0){
                tileCounter-=2;
            }

        }
        int blackCounter = 19;
        for(int i = 37;i>2;i--){
            for(int j = blackCounter;j<38;j++){
                bottomRightStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
            }
            if(i%2==0){
                blackCounter++;
            }
        }
        blackCounter = 1;
        for(int i = 19;i<38;i++){
            for(int j = 0;j<blackCounter;j++){
                bottomRightStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
            }
            blackCounter++;
        }
        bottomRightStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),36,2);
        bottomRightStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),37,2);
        int wallCountLeft = 19;
        int wallCountRight = 20;
        for(int i=36;i>0;i-=2){
            for(int j = wallCountLeft;j<wallCountRight;j++){
                bottomRightStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft++;
            wallCountRight++;
        }
        wallCountLeft = 0;
        wallCountRight = 1;
        for(int i=19;i<38;i++){
            for(int j = wallCountLeft;j<wallCountRight;j++){
                bottomRightStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft++;
            wallCountRight++;
        }
        bottomRightStar.addLava(16,29,5,5);
        bottomRightStar.addLava(12,24,5,5);
        bottomRightStar.addLava(8,20,5,5);
        bottomRightStar.addLava(4,16,5,5);

        bottomRightStar.addLava(19,23,5,5);
        bottomRightStar.addLava(15,18,5,5);
        bottomRightStar.addLava(11,13,5,5);

        bottomRightStar.addLava(21,17,5,5);
        bottomRightStar.addLava(17,12,5,5);

        bottomRightStar.addLava(23,11,5,5);
        bottomRightStar.addClueSign(15,26,"Make a square and you're there");

        bottomRightStar.addTeleporters(bottomRightStar,16,29,12,26,12,26,10,22,12,26);//bottomleft,topleft,topright,bottomright
        bottomRightStar.addTeleporters(bottomRightStar,12,24,12,26,12,26,10,22,18,31);
        bottomRightStar.addTeleporters(bottomRightStar,8,20,12,26,6,18,12,26,12,26);
        bottomRightStar.addTeleporters(bottomRightStar,4,16,17,20,12,26,12,26,12,26);

        bottomRightStar.addTeleporters(bottomRightStar,19,23,10,22,18,31,13,15,12,26);
        bottomRightStar.addTeleporters(bottomRightStar,15,18,6,18,12,26,13,15,21,25);
        bottomRightStar.addTeleporters(bottomRightStar,11,13,6,18,23,19,12,26,12,26);

        bottomRightStar.addTeleporters(bottomRightStar,21,17,19,14,12,26,13,15,17,20);
        bottomRightStar.addTeleporters(bottomRightStar,17,12,6,18,13,15,17,20,25,13);

        bottomRightStar.addTeleporters(bottomRightStar,23,11,12,26,12,26,12,26,12,26);

        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,5);


        Chest chest1 = new Chest(25,14,bottomRightStar.chestOpen,0,broadcaster);
        chest1.addObject(crystalPiece);
        bottomRightStar.addObject(chest1);


        bottomRightStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),12,26);
        bottomRightStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",25,3,DIRECTION.NORTH,Color.BLACK),9,27);
        bottomRightStar.addPortal(bottomRightStar,9,27,"smallStar",1f,1f);
        return bottomRightStar;
    }

    /**
     * Loads the bottom left star
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World bottomLeftStar(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster){

        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World bottomLeftStar = new World("bottomLeftStar",38,38, assetManager);
        for (int x = 0; x <38; x++) {
            for (int y = 0; y<38; y++) {
                if (y==0) {
                    bottomLeftStar.getMap().setTile(new Tile("null",null, false), x, y);
                    continue;
                }
                else{
                    bottomLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),x,y);
                }

            }
        }
        int tileCounter = 2;
        for(int i = 0;i<38;i++){
            for(int j = 1;j<tileCounter;j++){
                bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),j,i);
            }
            if(tileCounter<38){
                tileCounter+=2;
            }
        }
        int blackCounter = 20;
        for(int i = 37;i>0;i--){
            for(int j = blackCounter;j>0;j--){
                if(j==0){
                    bottomLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
                }
                bottomLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);

            }
            if(i%2==0){
                blackCounter--;
            }
        }

        blackCounter = 36;
        for(int i = 19;i<38;i++){
            for(int j = 37;j>blackCounter;j--){
                bottomLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),j,i);
            }
            blackCounter--;
        }

        bottomLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),1,0);
        bottomLeftStar.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE,false),3,1);

        int wallCountLeft = 19;
        int wallCountRight = 20;
        for(int i=36;i>0;i-=2){
            for(int j = wallCountRight;j>wallCountLeft;j--){
                bottomLeftStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft--;
            wallCountRight--;
        }

        wallCountLeft = 36;
        wallCountRight = 37;
        for(int i=19;i<36;i++){
            for(int j = wallCountRight;j>wallCountLeft;j--){
                bottomLeftStar.addWallObject(j,i,"middleWall",false,1f,3f);
            }
            wallCountLeft--;
            wallCountRight--;
        }

        for(int i =0;i<38;i++){
            for(int j =0;j<38;j++){
                if(bottomLeftStar.getMap().getTile(i,j).getTerrain() == TERRAIN.DUNGEON_FLOOR){
                    bottomLeftStar.getMap().setTile(new Tile("lavaMiddle",TERRAIN.lava_WALL_MIDDLE_MIDDLE,false),i,j);
                }
            }
        }
        //LAVA PUZZLE

        bottomLeftStar.addLava(18,26,5,5);
        bottomLeftStar.addLava(22,21,5,5);
        bottomLeftStar.addLava(27,16,5,5);

        bottomLeftStar.addLava(15,20,5,5);
        bottomLeftStar.addLava(20,14,5,5);

        bottomLeftStar.addLava(14,14,5,5);

        bottomLeftStar.addLava(10,9,5,5);

        bottomLeftStar.addClueSign(23,25,"Make a square and you're there");
        bottomLeftStar.addTeleporters(bottomLeftStar,18,26,29,18,29,18,29,18,24,23);//bottomleft,topleft,topright,bottomright
        bottomLeftStar.addTeleporters(bottomLeftStar,22,21,20,28,24,23,29,18,24,23);
        bottomLeftStar.addTeleporters(bottomLeftStar,27,16,29,18,24,23,17,22,24,23);

        bottomLeftStar.addTeleporters(bottomLeftStar,15,20,29,18,24,23,29,18,22,16);
        bottomLeftStar.addTeleporters(bottomLeftStar,20,14,16,16,24,23,29,18,22,16);

        bottomLeftStar.addTeleporters(bottomLeftStar,14,14,29,18,12,11,29,18,24,23);

        bottomLeftStar.addTeleporters(bottomLeftStar,10,9,26,23,26,23,26,23,26,23);

        TextureRegion crystal = atlas.findRegion("crystalPiece");
        CrystalPiece crystalPiece = new CrystalPiece(1,1,crystal,1f,1f,new GridPoint2(0,0),true,4);


        Chest chest1 = new Chest(12,12,bottomLeftStar.chestOpen,0,broadcaster);
        chest1.addObject(crystalPiece);
        bottomLeftStar.addObject(chest1);

        bottomLeftStar.getMap().setTile(new TeleportTilePortal(null,player,broadcaster,"mainRoom",11,3,DIRECTION.NORTH,Color.BLACK),28,27);
        bottomLeftStar.addPortal(bottomLeftStar,28,27,"smallStar",1f,1f);
        bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),28,26);
        bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),28,25);
        bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),28,24);
        bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),28,23);
        bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),27,23);
        bottomLeftStar.getMap().setTile(new Tile("dungeonFloor",TERRAIN.DUNGEON_FLOOR,true),26,23);
        return bottomLeftStar;
    }
    public static World reaperRoom(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster) {
        World reaperWorld = new World("Reaper Room",21,21, assetManager);//Define world size 11
        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 21; y++) {
                if (y==0) {
                   reaperWorld.getMap().setTile(new Tile("blank",null, false), x, y);
                    continue;
                }
                if (y > 0) {
                    if (Math.random() > 0.05d) {
                       reaperWorld.getMap().setTile(new Tile("dungFloor",TERRAIN.DUNGEON_FLOOR, true), x, y);
                    } else {
                       reaperWorld.getMap().setTile(new Tile("dungFloor",TERRAIN.DUNGEON_FLOOR, true), x, y);
                    }
                }
            }
        }
        reaperWorld.addWall(0, 18, 21, 3, 1);
        reaperWorld.addWall(3, 6, 2, 7, 1);
        reaperWorld.addWall(6, 7, 2, 5, 1);
        reaperWorld.addWall(9, 8, 3, 3, 1);
        reaperWorld.addWall(13, 7, 2, 5, 1);
        reaperWorld.addWall(16, 6, 2, 7, 1);
        reaperWorld.addSpikes(TERRAIN.DUNGEON_FLOOR, player,broadcaster,5,9);
        reaperWorld.addSpikes(TERRAIN.DUNGEON_FLOOR, player,broadcaster,15,9);
        Key bossKey = reaperWorld.addKey(1,1,"Grey Key",3);
        Actor enemy = new Actor(reaperWorld, 11, 15,reaperWorld.addAnimationSet("reaper"));
        enemy.addObject(bossKey);
        enemy.setSizeX(2);
        enemy.setSizeY(3);

        enemy.name = "Reaper";
        //enemy.character = new Skeleton(5, 1, 3000, enemy);
        //Evil Rick is a level 5 skeleton.

        //To be put in new world
        //Actor placeholder = new Actor(reaperWorld, 1, 1,reaperWorld.addAnimationSet("reaper"));
        //Monster Yeti = new Yeti(5, 1.2f, 20000, enemy);
        Monster Reaper = new Reaper(7, 1.2f, 10000, enemy);
        Reaper.health += 100;
        Reaper.heal(100);
        enemy.character = Reaper;

        //LimitedWalkingBehavior brain = new LimitedWalkingBehavior(enemy, 1, 1, 1, 1, 0.3f, 1f, new Random());
        YetiBehavior brain = new YetiBehavior(enemy, 20, 20, 20, 20, 0.2f, .7f, new Random(), 15, 3, 0);
        //RunAwayBehavior brain = new RunAwayBehavior(enemy, 2, 2, 2, 2, 0.3f, 1f, new Random(), 3);
        //SeekBehavior brain = new SeekBehavior(enemy, 2, 2, 2, 2, 0.3f, 1f, new Random(), 5, 3);
        reaperWorld.addActor(enemy, brain);
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion bowTex = atlas.findRegion("bow");
        Bow bow = new Bow(11, 11, bowTex, 1, 1, new GridPoint2(1, 1), true, new int[]{15,0,0,0,0,0,0,0,0,0}, "Bow");
        Chest chest = new Chest(10,15,reaperWorld.chestOpen,0,broadcaster);
        chest.addObject(bow);

        //chest.addObject(bow);
        reaperWorld.addObject(chest);


       reaperWorld.addRug(reaperWorld,4,0);

        Door door = new Door(11,18,reaperWorld.doorOpen,reaperWorld.doorClose,3,0,broadcaster,39,29,"C_2");
        reaperWorld.addObject(door);
        return reaperWorld;
    }

    /**
     * This method defines the yeti boss room
     * @param assetManager
     * @param player
     * @param broadcaster
     * @return
     */
    public static World yetiRoom(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster) {
        World reaperWorld = new World("Yeti Room",17,20, assetManager);//Define world size 11
        for (int x = 0; x < 17; x++) {
            for (int y = 0; y < 20; y++) {
                if (y==0) {
                    reaperWorld.getMap().setTile(new Tile("blank",null, false), x, y);
                    continue;
                }
                if (y > 0) {
                    if (Math.random() > 0.05d) {
                        reaperWorld.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE, true), x, y);
                    } else {
                        reaperWorld.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE, true), x, y);
                    }
                }
            }
        }
        reaperWorld.addWall(0, 17, 17, 3, 1);
        reaperWorld.addWall(2, 5, 1, 7, 1);
        reaperWorld.addWall(5, 4, 1, 9, 1);
        reaperWorld.addWall(8, 3, 1, 11, 1);
        reaperWorld.addWall(11, 4, 1, 9, 1);
        reaperWorld.addWall(14, 5, 1, 7, 1);
        reaperWorld.setIcePath(3, 2, 13, false);
        reaperWorld.setIcePath(4, 2, 13, false);
        reaperWorld.setIcePath(6, 2, 13, false);
        reaperWorld.setIcePath(7, 2, 13, false);
        reaperWorld.setIcePath(9, 2, 13, false);
        reaperWorld.setIcePath(10, 2, 13, false);
        reaperWorld.setIcePath(12, 2, 13, false);
        reaperWorld.setIcePath(13, 2, 13, false);
        Key bossKey = reaperWorld.addKey(1,1,"Grey Key",3);
        Actor enemy = new Actor(reaperWorld, 8, 15,reaperWorld.addAnimationSet("yeti"));
        enemy.addObject(bossKey);
        enemy.setSizeX(2);
        enemy.setSizeY(3);

        enemy.name = "Yeti";
        //enemy.character = new Skeleton(5, 1, 3000, enemy);
        //Evil Rick is a level 5 skeleton.

        //To be put in new world
        //Actor placeholder = new Actor(reaperWorld, 1, 1,reaperWorld.addAnimationSet("reaper"));
        Monster Yeti = new Yeti(10, 1.2f, 20000, enemy);
        //Monster Reaper = new Reaper(4, 1.2f, 10000, enemy);
        enemy.character = Yeti;

        //LimitedWalkingBehavior brain = new LimitedWalkingBehavior(enemy, 1, 1, 1, 1, 0.3f, 1f, new Random());
        YetiBehavior brain = new YetiBehavior(enemy, 20, 20, 20, 20, 0.2f, .8f, new Random(), 15, 1, 100);
        //RunAwayBehavior brain = new RunAwayBehavior(enemy, 2, 2, 2, 2, 0.3f, 1f, new Random(), 3);
        //SeekBehavior brain = new SeekBehavior(enemy, 2, 2, 2, 2, 0.3f, 1f, new Random(), 5, 3);
        reaperWorld.addActor(enemy, brain);






        return reaperWorld;
    }

    /**
     * Creates a 50x50 blank canvas map for editor
     * @param assetManager
     * @param player
     * @param broadcaster
     * @param npcAnimations
     * @return
     */
    public static World cleanSlate(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World cleanSlate = new World("cleanSlate",50,50, assetManager);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                cleanSlate.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE), x, y);
            }
        }

        return cleanSlate;

    }

    /**
     * Creates a 50x50 blank canvas map for editor
     * @param assetManager
     * @param player
     * @param broadcaster
     * @param npcAnimations
     * @return
     */
    public static World custom1(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World cleanSlate = new World("custom1",50,50, assetManager);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                cleanSlate.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE), x, y);
            }
        }

        return cleanSlate;

    }

    public static World custom2(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World cleanSlate = new World("custom2",50,50, assetManager);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                cleanSlate.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE), x, y);
            }
        }

        return cleanSlate;

    }

    public static World custom3(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World cleanSlate = new World("custom3",50,50, assetManager);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                cleanSlate.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE), x, y);
            }
        }

        return cleanSlate;

    }

    public static World custom4(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World cleanSlate = new World("custom4",50,50, assetManager);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                cleanSlate.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE), x, y);
            }
        }

        return cleanSlate;

    }

    public static World custom5(AssetManager assetManager, CutscenePlayer player, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations){
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        World cleanSlate = new World("custom5",50,50, assetManager);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                cleanSlate.getMap().setTile(new Tile("blank",TERRAIN.ds_WALL_MIDDLE_MIDDLE), x, y);
            }
        }

        return cleanSlate;

    }


}