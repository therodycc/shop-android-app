package com.example.shopping.singlenton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public final class SinglentonDB {

    private static SinglentonDB intanceSingleton;
    private RequestQueue requestQueue;
    private static Context context;

    private SinglentonDB(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized SinglentonDB getInstance(Context context){
        if(intanceSingleton == null){
            intanceSingleton = new SinglentonDB(context);
        }
        return intanceSingleton;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }


}
