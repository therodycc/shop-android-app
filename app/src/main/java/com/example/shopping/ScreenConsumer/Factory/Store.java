package com.example.shopping.ScreenConsumer.Factory;

import android.content.Context;
import android.os.Build;
import android.util.Log;

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
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.singlenton.SinglentonDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Store implements IFactory {

    ArrayList<StoreModelo> storeModeloArrayList;
    Context context;
    String url;

    public Store(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    @Override
    public void create(String[] datos) {

    }

    @Override
    public void all(String id) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        storeModeloArrayList = new ArrayList<>();

                        for(int i=0; i < array.length(); i++){

                            JSONObject row = array.getJSONObject(i);

                            int countProdut = row.getInt("product");

                            if(countProdut > 0){
                                String id = String.valueOf(row.getInt("id"));
                                String name = row.getString("name");
                                String social= row.getString("social");
                                String phone = row.getString("phono");
                                String image = row.getString("image_company");

                                String url = "https://startbuying.000webhostapp.com/Images/"+image;

                                storeModeloArrayList.add(new StoreModelo(id,name,social,phone,url));
                            }


                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
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
               // parametros.put("id_company", idCompany);
                return parametros;

            }
        };
        //RequestQueue requestQueue = Volley.newRequestQueue(context);
        //requestQueue.add(con);
        SinglentonDB.getInstance(context).addToRequestQueue(con);

    }

    @Override
    public void createarraylist(String[] details) {

    }

    @Override
    public ArrayList<StoreModelo> storemodelolist() {
        return storeModeloArrayList;
    }

    @Override
    public ArrayList<ProductModelo> productmodelolist() {
        return null;
    }

    @Override
    public ArrayList<OrderModelo> ordersmodelolist() {
        return null;
    }

    @Override
    public ArrayList<DetalleOrderModelo> detalleorderlist() {
        return null;
    }

}
