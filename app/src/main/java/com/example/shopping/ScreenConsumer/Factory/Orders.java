package com.example.shopping.ScreenConsumer.Factory;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;
import com.example.shopping.ScreenConsumer.entity.DetalleOrderModelo;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;
import com.example.shopping.ScreenConsumer.entity.ProductModelo;
import com.example.shopping.ScreenConsumer.entity.StoreModelo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Orders implements IFactory {

    ArrayList<OrderModelo> orderModeloArrayList ;
    Context context;
    String url;

    public Orders(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    @Override
    public void create(final String[] datos) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("data",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_order", datos[0]);
                parametros.put("date_order", datos[1]);
                parametros.put("comment_order", datos[2]);
                parametros.put("subtotal_order", datos[3]);
                parametros.put("user_order", datos[4]);
                parametros.put("company_order", datos[5]);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(con);
    }

    @Override
    public void all(final String id) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        orderModeloArrayList = new ArrayList<>();

                        for(int i=0; i < array.length(); i++){

                              JSONObject row = array.getJSONObject(i);

                                String id = row.getString("id_order");
                                String company = row.getString("name_company");
                                String subtotal = row.getString("subtotal");
                                String status = row.getString("status");
                                String phone = row.getString("phone");
                                String[] date = row.getString("date").split("-");

                                String dateToday = date[2]+"/"+date[1]+"/"+date[0];

                                orderModeloArrayList.add(new OrderModelo(id,company,
                                        subtotal,status,dateToday,phone));

                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_user", id);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(con);
    }

    @Override
    public void createarraylist(final String[] details) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
                }
              //  Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("idorder", details[0]);
                parametros.put("idproduct", details[1]);
                parametros.put("price", details[2]);
                parametros.put("quantify", details[3]);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(con);
    }

    @Override
    public ArrayList<StoreModelo> storemodelolist() {
        return null;
    }

    @Override
    public ArrayList<ProductModelo> productmodelolist() {
        return null;
    }

    @Override
    public ArrayList<OrderModelo> ordersmodelolist() {
        return orderModeloArrayList;
    }

    @Override
    public ArrayList<DetalleOrderModelo> detalleorderlist() {
        return null;
    }
}
