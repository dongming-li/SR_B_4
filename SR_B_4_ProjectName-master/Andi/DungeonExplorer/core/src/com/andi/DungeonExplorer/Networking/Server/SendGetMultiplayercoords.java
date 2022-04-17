package com.andi.DungeonExplorer.Networking.Server;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ntcarter on 11/23/2017.
 */

/**
 * Sends the coordinates of the user to the server and gets the Coordinates of the other player from the server
 */
public class SendGetMultiplayercoords {


    public Net.HttpRequest postReq;
    public Net.HttpRequest getReq;
    public String posString2;

    public SendGetMultiplayercoords() {
        //create requests
        postReq = new HttpRequest(Net.HttpMethods.POST);
        getReq = new HttpRequest(Net.HttpMethods.POST);
        //set URLs
        postReq.setUrl("http://proj-309-sr-b-4.cs.iastate.edu/SendGetGameCoords.php");
    }


    /**
     * Sends user information to the database to move players around
     * @param userName
     * @param userPassword
     */
    public void SendMultiplayerStuff(String userName, String userPassword, int coordX, int coordY, String direction){
        Map<String,String> gameInfo = new HashMap<String, String>();
        gameInfo.put("N",userName);
        gameInfo.put("P",userPassword);
        gameInfo.put("X", Integer.toString(coordX));
        gameInfo.put("Y", Integer.toString(coordY));
        gameInfo.put("D", direction);


        postReq.setContent(HttpParametersUtils.convertHttpParameters(gameInfo));
        Gdx.net.sendHttpRequest(postReq, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus status = httpResponse.getStatus();
                // System.out.println(httpResponse.getResultAsString());
                // System.out.println(status.getStatusCode());
                // System.out.println(postReq.getContent());
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("send failed");
            }

            @Override
            public void cancelled() { System.out.println("send cancelled"); }
        });

        getMulStringResponse();
    }

    /**
     * Returns the server response as a string
     * @return
     */
    public String getPos(){
        return posString2;
    }


    /**
     * Gets the server response to the request.
     */
    public void getMulStringResponse(){
        Gdx.net.sendHttpRequest(postReq, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                posString2 = httpResponse.getResultAsString();
                System.out.println();
                System.out.println();
                System.out.println("server response: ---------------------------------------->"+ posString2);
                System.out.println();
                System.out.println();
            }


            @Override
            public void failed(Throwable t) { }

            @Override
            public void cancelled() { }
        });
    }
}
