package com.andi.DungeonExplorer.world.SpecialTiles;


import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.InteractableItems.Boulder;
import com.andi.DungeonExplorer.world.WorldObject;


public class SwitchTile extends Tile {



    private int switchId;
    private WorldObject obj;

    public SwitchTile(TERRAIN terrain, int id) {
        super("switchTile",terrain);
        this.switchId = id;


    }
    public SwitchTile(TERRAIN terrain, WorldObject obj){
        super("switchTile",terrain);
        this.obj = obj;
    }


    @Override
    public void actorStep(Actor a) {
        if (a instanceof PlayerActor) {
            if(obj == null){
                com.andi.DungeonExplorer.world.World targetWorld = a.getWorld();
                for(int i = 0;i<targetWorld.getMap().getWidth();i++){
                    for(int j = 0;j<targetWorld.getMap().getHeight();j++){
                        WorldObject obj = targetWorld.getMap().getTile(i,j).getObject();
                        if(obj!= null && obj.isActivatable()
                                &&obj.getActivateId() == this.switchId){
                            obj.activate();
                        }

                    }
                }
            }
            else{
                com.andi.DungeonExplorer.world.World targetWorld = a.getWorld();
                targetWorld.addObject(obj);
            }



        }
        if(a instanceof Boulder){
            com.andi.DungeonExplorer.world.World targetWorld = a.getWorld();
            for(int i = 0;i<targetWorld.getMap().getWidth();i++){
                for(int j = 0;j<targetWorld.getMap().getHeight();j++){
                    WorldObject obj = targetWorld.getMap().getTile(i,j).getObject();
                    if(obj!= null && obj.isActivatable()
                            &&obj.getActivateId() == this.switchId){
                        obj.boulderActivate();
                    }
                }
            }
        }

    }

    @Override
    public void boulderDeactivate(Boulder a){
        com.andi.DungeonExplorer.world.World targetWorld = a.getWorld();
        for(int i = 0;i<targetWorld.getMap().getWidth();i++){
            for(int j = 0;j<targetWorld.getMap().getHeight();j++){
                WorldObject obj = targetWorld.getMap().getTile(i,j).getObject();
                if(obj!= null && obj.isActivatable()
                        &&obj.getActivateId() == this.switchId){
                    obj.boulderDeactivate();
                }
            }
        }
    }


}