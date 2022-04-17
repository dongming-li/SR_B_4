package com.mygdx.game;

/**
 * Created by Kenneth on 9/24/2017.
 * This class includes all the additional methods needed for the player character in the campaign.
 */

public class CampaignPlayer extends Player {
    public CampaignPlayer(){
        super();
    }

    public CampaignPlayer(dir facing, int maxLife, int life, int row, int col, Inventory inv, Game game, TileGrid grid){
        super(facing, maxLife, life, row, col, inv, game, grid);
    }
}
