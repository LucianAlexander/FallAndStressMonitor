package com.example.lux.fallandstressmonitor;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstances;
    private static Context mCtx;
    private RequestQueue requestQueue;

    private MySingleton(Context context){

        mCtx = context;
        requestQueue = getRequestQueue();

    }

    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return requestQueue;
    }

    //method to return an instances of this class
    public static synchronized  MySingleton getmInstances(Context context){
        if(mInstances==null){
            mInstances = new MySingleton(context);
        }
        return mInstances;
    }

    public<T> void addToRequestque(Request<T> request){
        getRequestQueue().add(request);
    }

}
