package com.example.shopping;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getDate {

    Context context;
    String url;
    String prueba;

    public getDate(Context context, String url) {
        this.context = context;
        this.url = url;
    }
    public void today() {

        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(!response.isEmpty()){
                                    try {
                                        JSONArray array = new JSONArray(response);
                                        JSONObject row =
                                                array.getJSONObject(0);
                                        prueba = row.getString("fecha");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(context,"Error date not find.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        },
                new Response.ErrorListener() {
            @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                            throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("titleNote","Primera");
                        return parametros;
                    }
                };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(con);
    }
    public String getPrueba() {
        return prueba;
    }

}
