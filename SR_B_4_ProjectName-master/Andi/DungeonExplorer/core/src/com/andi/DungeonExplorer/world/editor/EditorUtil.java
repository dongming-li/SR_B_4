package com.andi.DungeonExplorer.world.editor;

import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.world.SpecialTiles.IceTile;
import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;



/**
 * Created by brytonhayes on 11/27/17.
 *
 * Packs a world into csv files for tiles, objects, and actors
 *
 *
 */

public class EditorUtil {

    private World world;    //world to be packed

    private AssetManager assetManager;  //for referencing textures

    int[][] tileArr;    //array representing present tiles
    int[][] objArr;     //array representing present objects
    int[][] actorArr;   //array representing present actors

    File tileFile;
    File objFile;
    File actorFile;

    private int spawnX;
    private int spawnY;

    String tileFileName;
    String objFileName;
    String actorFileName;

    String[][] tiles;   //array of strings representing tiles
    String[] objects;   //array of strings representing each object
    String[] actors;    //array of strings representing actors

    int objCount;       //number of objects in the world
    int actorCount;     //number of actors in the world

    /**
     * Basic constructor
     * @param world world to be packed
     */
    public EditorUtil(World world){
        this.world = world;
        this.assetManager = world.getAssetManager();

        //initialize arrays
        tileArr = new int[world.getWidth()][world.getHeight()];
        objArr = new int[world.getWidth()][world.getHeight()];
        actorArr = new int[world.getWidth()][world.getHeight()];

        tileFileName = new String();
        objFileName = new String();
        actorFileName = new String();


        String path = "/Users/brytonhayes/desktop/";    //TODO Change this to correct path!

        objFileName = world.getName() + "_obj.txt";
        tileFileName = world.getName() + "_tile.txt";
        actorFileName = world.getName() + "_act.txt";

        actors = new String[50];
        tiles = new String[world.getWidth()][world.getHeight()];

        for (int i = 0; i < world.getMap().getWidth(); i++) {
            for (int j = 0; j < world.getMap().getHeight(); j++) {
                tileArr[i][j] = -1;
                objArr[i][j] = -1;
                actorArr[i][j] = -1;

            }
        }

        objects = new String[1000];

    }

    /**
     * pack tiles as chars into tileArr
     */
    public void packTiles(){
        for (int i = 0; i < world.getMap().getWidth(); i++) {
            for (int j = 0; j < world.getMap().getHeight(); j++) {
                tiles[i][j] = world.getMap().getTile(i, j).toString();

            }
        }
    }

    /**
     * Pack this worlds objects as strings in objects[]
     */
    public void packObjects(){
        objCount = 0;
        String objString;
        //System.out.println(world.getMap().getTile(2,2).getObject());

        //loop through all tiles
        for (int i = 0; i < world.getMap().getWidth(); i++) {
            for (int j = 0; j < world.getMap().getHeight(); j++) {
                //if an object exists
                if(world.getMap().getTile(i,j).getObject() != null){
                    //object found
                    objString = world.getMap().getTile(i,j).getObject().toString();
                    //if(!Arrays.asList(objects).contains(objString)){
                        System.out.println(objString);
                        objects[objCount] = objString;
                        addObject(objString);
                        objCount++;
                    //}


                }

            }
        }
    }

    /**
     * Pack this worlds actors
     */
    public void packActors(){
        actorCount = 0;
        for(Actor actor : world.getActors()){
            actors[actorCount] = actor.toString();
            actorCount++;
        }






        /*
        actorCount = 0;
        String actString;
        //System.out.println(world.getMap().getTile(2,2).getObject());

        //loop through all tiles
        for (int i = 0; i < world.getMap().getWidth(); i++) {
            for (int j = 0; j < world.getMap().getHeight(); j++) {
                //if an object exists
                if(world.getMap().getTile(i,j).getObject() != null){
                    //object found
                    actString = world.getMap().getTile(i,j).getActor().toString();
                    //if(!Arrays.asList(objects).contains(objString)){
                    System.out.println(actString);
                    objects[objCount] = actString;
                    addObject(actString);
                    objCount++;
                    //}


                }

            }
        }
        */
    }
/*
    public void readObjectFile() throws IOException {

        int i = 0;

        File file = new File("C:\\Users\\pankaj\\Desktop\\test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while((st = br.readLine()) != null){
            objects[i] = st;
            i++;
        }

    }
*/

    public void internalSaveObjectFile(){
        FileHandle objFile = Gdx.files.local(objFileName);

        objFile.writeString("", false);

        for(int i = 0; i < objCount; i++){
            objFile.writeString(objects[i], true);
            objFile.writeString("\n", true);
        }

        System.out.println(objFileName + " saved");

    }

    public void internalReadObjectFile(){
        FileHandle file = Gdx.files.local(objFileName);
        String text = file.readString();

        int objCount = 0;

        String wordsArray[] = text.split("\\r?\\n");
        for(String word : wordsArray) {
            objects[objCount] = word;
            objCount++;

            if(word.length() > 2){
                addObject(word);
            }

        }

    }



    public void internalSaveTileFile(){
        FileHandle tileFile = Gdx.files.local(tileFileName);

        tileFile.writeString("", false);

        for(int i = 0; i < world.getWidth(); i++){
            for(int j = 0; j < world.getHeight(); j++){
                tileFile.writeString(tiles[i][j], true);
                tileFile.writeString("\n", true);
            }

        }

        System.out.println(tileFileName + " saved");
    }

    public void internalReadTileFile(){
        FileHandle file = Gdx.files.internal(tileFileName);
        String text = file.readString();
        int i,j;

        int arrCount = 0;

        String arr[] = text.split("\\r?\\n");
        for(i = 0; i < world.getWidth(); i++){
            for(j = 0; j < world.getHeight(); j++){
                if(arr[arrCount].contains("spawn")){
                    spawnX = i;
                    spawnY = j;
                }
                world.getMap().setTile(strToTile(arr[arrCount]),i,j);
                arrCount++;
            }
        }

    }

    public int getSpawnX(){
        return spawnX;
    }

    public int getSpawnY(){
        return spawnY;
    }

    public void internalSaveActorFile(){
        FileHandle actFile = Gdx.files.local(actorFileName);

        actFile.writeString("", false);

        for(int i = 0; i < actorCount; i++){
            actFile.writeString(actors[i], true);
            actFile.writeString("\n", true);


        }

        System.out.println(actorFileName + " saved");

    }

    public void internalReadActorFile(){
        FileHandle file = Gdx.files.local(actorFileName);
        String text = file.readString();

        int objCount = 0;

        String wordsArray[] = text.split("\\r?\\n");
        for(String word : wordsArray) {
            objects[objCount] = word;
            objCount++;

            if(word.length() > 2){
                addActor(word);
            }

        }
    }

    public void addActor(String string){
        //System.out.println(string);

        String[] tokens = string.split(":");
        String[] coords = tokens[1].split(",");

        int xPos = Integer.parseInt(coords[0]);
        int yPos = Integer.parseInt(coords[1]);

        String type = tokens[0];

        //for (String t : tokens)
        //    System.out.println(t);

        WorldObject object = null;

        if(type.contains("boulder")){
            world.addBoulderActor(xPos,yPos);
        }

    }


    public Tile strToTile(String packedTile){
        int flag = 0;
        String type = packedTile;
        Tile tile = null;
        TERRAIN terrain = TERRAIN.ds_WALL_MIDDLE_MIDDLE;
        Boolean walkable = false;

        //floors
        if(packedTile.contains("blank")){
            terrain = TERRAIN.ds_WALL_MIDDLE_MIDDLE;
            walkable = false;
        }
        if(packedTile.contains("grass")){

            terrain = TERRAIN.GRASS_0;
            walkable = true;
        }
        if(packedTile.contains("dungeonFloor")){
            terrain = TERRAIN.DUNGEON_FLOOR;
            walkable = true;
        }
        if(packedTile.contains("snowTile")){
            terrain = TERRAIN.SNOW_TILE;
            walkable = true;
        }

        //purple wall
        if(packedTile.contains("purpleWallLeftBottom")){
            terrain = TERRAIN.INDOOR_WALL_LEFT_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("purpleWallLeftTop")){
            terrain = TERRAIN.INDOOR_WALL_LEFT_TOP;
            walkable = false;
        }
        if(packedTile.contains("purpleWallLeftMiddle")){
            terrain = TERRAIN.INDOOR_WALL_LEFT_MIDDLE;
            walkable = false;
        }
        if(packedTile.contains("purpleWallRightTop")){
            terrain = TERRAIN.INDOOR_WALL_RIGHT_TOP;
            walkable = false;
        }
        if(packedTile.contains("purpleWallMiddleTop")){
            terrain = TERRAIN.INDOOR_WALL_MIDDLE_TOP;
            walkable = false;
        }
        if(packedTile.contains("purpleWallRightBottom")){
            terrain = TERRAIN.INDOOR_WALL_RIGHT_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("purpleWallRightMiddle")){
            terrain = TERRAIN.INDOOR_WALL_RIGHT_MIDDLE;
            walkable = false;
        }
        if(packedTile.contains("purpleWallMiddleBottom")){
            terrain = TERRAIN.INDOOR_WALL_MIDDLE_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("purpleWallMiddleMiddle")){
            terrain = TERRAIN.INDOOR_WALL_MIDDLE_MIDDLE;
            walkable = false;
        }

        //dungeon wall
        if(packedTile.contains("dungWallLeftBottom")){
            terrain = TERRAIN.DUNG_WALL_LEFT_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("dungWallLeftTop")){
            terrain = TERRAIN.DUNG_WALL_LEFT_TOP;
            walkable = false;
        }
        if(packedTile.contains("dungWallLeftMiddle")){
            terrain = TERRAIN.DUNG_WALL_LEFT_MIDDLE;
            walkable = false;
        }
        if(packedTile.contains("dungWallRightTop")){
            terrain = TERRAIN.DUNG_WALL_RIGHT_TOP;
            walkable = false;
        }
        if(packedTile.contains("dungWallMiddleTop")){
            terrain = TERRAIN.DUNG_WALL_MIDDLE_TOP;
            walkable = false;
        }
        if(packedTile.contains("dungWallRightBottom")){
            terrain = TERRAIN.DUNG_WALL_RIGHT_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("dungWallRightMiddle")){
            terrain = TERRAIN.DUNG_WALL_RIGHT_MIDDLE;
            walkable = false;
        }
        if(packedTile.contains("dungWallMiddleBottom")){
            terrain = TERRAIN.DUNG_WALL_MIDDLE_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("dungWallMiddleMiddle")){
            terrain = TERRAIN.DUNG_WALL_MIDDLE_MIDDLE;
            walkable = false;
        }

        //snow wall
        if(packedTile.contains("snowWallBottomLeft")){
            terrain = TERRAIN.SNOW_WALL_TOP_LEFT_CORNER;
            walkable = false;
        }
        if(packedTile.contains("snowWallTopLeft")){
            terrain = TERRAIN.SNOW_WALL_TOP_LEFT_CORNER;
            walkable = false;
        }
        if(packedTile.contains("snowWallLeft")){
            terrain = TERRAIN.SNOW_WALL_LEFT;
            walkable = false;
        }
        if(packedTile.contains("snowWallTopRight")){
            terrain = TERRAIN.SNOW_WALL_TOP_RIGHT_CORNER;
            walkable = false;
        }
        if(packedTile.contains("snowWallTop")){
            terrain = TERRAIN.SNOW_WALL_TOP;
            walkable = false;
        }
        if(packedTile.contains("snowWallBottomRight")){
            terrain = TERRAIN.SNOW_WALL_BOTTOM_RIGHT_CORNER;
            walkable = false;
        }
        if(packedTile.contains("snowWallRight")){
            terrain = TERRAIN.SNOW_WALL_RIGHT;
            walkable = false;
        }
        if(packedTile.contains("snowWallBottom")){
            terrain = TERRAIN.SNOW_WALL_BOTTOM;
            walkable = false;
        }
        if(packedTile.contains("snowWallCenter")){
            terrain = TERRAIN.SNOW_WALL_FILLER;
            walkable = false;
        }

        //special tiles
        if(packedTile.contains("ice")){
            tile = new IceTile(TERRAIN.ice);
            flag = 1;

        }
        if(packedTile.contains("lava")){
            terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
            walkable = false;
        }
        if(packedTile.contains("spawn")){
            terrain = TERRAIN.TELEPORTER;
            walkable = true;
        }

        if(flag == 0){
            tile = new Tile(type, terrain, walkable);
        }


        return tile;
    }

    public void addObject(String string){

        //System.out.println(string);

        String[] tokens = string.split(":");
        String[] coords = tokens[1].split(",");

        int xPos = Integer.parseInt(coords[0]);
        int yPos = Integer.parseInt(coords[1]);

        String type = tokens[0];

        //for (String t : tokens)
        //    System.out.println(t);

        WorldObject object = null;

        if(type.contains("flowers")){
            world.addFlowers(xPos,yPos);
        }
        if(type.contains("tree")){
            world.addTree(xPos,yPos);
        }
        if(type.contains("bow")){
            world.addBow(xPos,yPos);
        }



    }


    public void saveObjectFile(){

        BufferedWriter bw = null;
        BufferedWriter bwClear = null;
        FileWriter fw = null;
        FileWriter fwClear = null;

        try {
            File file = new File("/Users/brytonhayes/desktop/" + objFileName);

            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                fwClear = new FileWriter(file.getAbsoluteFile(), false);
                bwClear = new BufferedWriter(fwClear);
                bwClear.write("");
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            for(int i = 0; i < objCount; i++){
                bw.write(objects[i]);
                bw.write('\n');
            }




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }


    }

    public void saveTilesFile(){

        BufferedWriter bw = null;
        BufferedWriter bwClear = null;
        FileWriter fw = null;
        FileWriter fwClear = null;

        try {
            File file = new File("/Users/brytonhayes/desktop/" + tileFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                fwClear = new FileWriter(file.getAbsoluteFile(), false);
                bwClear = new BufferedWriter(fwClear);
                bwClear.write("");
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            for(int i = 0; i < world.getWidth(); i++){
                for(int j = 0; j < world.getHeight(); j++){
                    bw.write(tiles[i][j]);
                    bw.write('\n');
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }

    }

    public void saveActorsFile(){

        BufferedWriter bw = null;
        BufferedWriter bwClear = null;
        FileWriter fw = null;
        FileWriter fwClear = null;

        try {
            File file = new File("/Users/brytonhayes/desktop/" + actorFileName);

            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                fwClear = new FileWriter(file.getAbsoluteFile(), false);
                bwClear = new BufferedWriter(fwClear);
                bwClear.write("");
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            for(int i = 0; i < actorCount; i++){
                bw.write(actors[i]);
                bw.write('\n');
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }

    }



}
