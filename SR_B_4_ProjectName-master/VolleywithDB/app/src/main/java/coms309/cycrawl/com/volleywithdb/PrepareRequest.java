package coms309.cycrawl.com.volleywithdb;

/**
 * Created by Nate on 10/6/2017.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class PrepareRequest {
    private static PrepareRequest mInstance;
    private RequestQueue requestQueue;
    private static Context myContext;


    private PrepareRequest(Context context){
        myContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized PrepareRequest getmInstance(Context context){
        if(mInstance==null){
            mInstance = new PrepareRequest(context);

        }
        return mInstance;
    }


    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T>void addToRequestQue(Request<T> request){
        requestQueue.add(request);

    }


}
