package com.andi.DungeonExplorer.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by brytonhayes on 10/11/17.
 */

public class MultiplayerHandler {

    public HttpRequest postReq;     //post request
    public HttpRequest getReq;      //get request
    public HttpRequestBuilder requestBuilder;
    public String posString;        //string representing a players position
    public int xPos, yPos;

    public MultiplayerHandler() {
        //create requests
        postReq = new HttpRequest(Net.HttpMethods.POST);
        getReq = new HttpRequest(Net.HttpMethods.POST);
        //set URLs
        postReq.setUrl("http://proj-309-sr-b-4.cs.iastate.edu/MovementTracker.php");
        getReq.setUrl("http://proj-309-sr-b-4.cs.iastate.edu/MovementRecieve.php");

    }

    /**
     * Get x position of player from the server
     * @return x position of player
     */
    public int getxPos(){
        return xPos;
    }

    /**
     * Get y position of player from the server
     * @return y position of player
     */
    public int getyPos(){
        return yPos;
    }

    /**
     * Post the player's position to the server
     * @param xPos player's x position on the map
     * @param yPos player's y position of the map
     */
    public void postPlayerPosition(int xPos, int yPos){
        /*Map<String,String> playerPos = new HashMap<String, String>();
        playerPos.put("x",Integer.toString(xPos));
        playerPos.put("y",Integer.toString(yPos));

        postReq.setContent(HttpParametersUtils.convertHttpParameters(playerPos));
        Gdx.net.sendHttpRequest(postReq, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                HttpStatus status = httpResponse.getStatus();
                //System.out.println(httpResponse.getResultAsString());
                //System.out.println(status.getStatusCode());
                //System.out.println(postReq.getContent());
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("send failed");
            }

            @Override
            public void cancelled() { System.out.println("send cancelled"); }
        });*/
    }

    /**
     * Get the players position from the server
     */
    public void getPlayerPosition(){
        Gdx.net.sendHttpRequest(getReq, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                posString = httpResponse.getResultAsString();
                xPos = Integer.parseInt(posString.substring(6, posString.indexOf(',') - 1));
                yPos = Integer.parseInt(posString.substring(posString.indexOf(',') + 6, posString.length() - 2));
            }


            @Override
            public void failed(Throwable t) { }

            @Override
            public void cancelled() { }
        });
    }





}
