package com.example.shopping.screenSuplier.Facade.EntityFactory;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.screenSuplier.Facade.IFactory;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCompanyModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityOrderModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityProductModelo;
import com.example.shopping.singlenton.SinglentonDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FactoryOrder implements IFactory {

    Context context;
    ArrayList<EntityOrderModelo> orderModeloArrayList;
    String url;

    public FactoryOrder(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public void Create(String[] datos) {

    }

    @Override
    public void Delete(String id) {

    }

    @Override
    public void Edit(String[] datos) {

    }

    @Override
    public void AllSpecific(final String idCompany) {

        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (response != null) {

                     try {
                        JSONArray array = new JSONArray(response);
                        orderModeloArrayList = new ArrayList<>();

                        for(int i=0; i < array.length(); i++){
                            /*
                            * {
"idorder": "H0FCrwdGbn9u",
"idcliente": 16,
"fullname": "Esmeralda Tavarez",
"status": "pending",
"total": "520.00",
"phone": "+1 580-874-5656",
"date": "2020-11-22"
},
                            * */

                            JSONObject row = array.getJSONObject(i);

                            String id = row.getString("idorder");
                            String idCliente = String.valueOf(row.getInt("idcliente"));
                            String fullnameclient = row.getString("fullname");
                            String phone = row.getString("phone");
                            String state = row.getString("status");
                            String date= row.getString("date");
                            String total = row.getString("total");
                            String comment = row.getString("commentc");

                            String[] dateSplit = date.split("-");
                            String completedate = dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];

                            orderModeloArrayList.add(new EntityOrderModelo(id,
                                    fullnameclient,phone,state,completedate,total,
                                    idCliente,comment));

                        }

                    } catch (JSONException ex) {
                        Log.i("data", ex.getMessage());

                    }
                }
               // Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
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
                parametros.put("id", idCompany);
                return parametros;

            }
        };

        con.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //RequestQueue requestQueue = Volley.newRequestQueue(context);
        //requestQueue.add(con);
        SinglentonDB.getInstance(context).addToRequestQueue(con);
    }

    @Override
    public ArrayList<EntityCategoryModelo> categorysArrayList() {
        return null;
    }

    @Override
    public ArrayList<EntityCompanyModelo> companyArrayList() {
        return null;
    }

    @Override
    public ArrayList<EntityProductModelo> productArrayList() {
        return null;
    }

    @Override
    public ArrayList<EntityOrderModelo> orderArrayList() {
        return orderModeloArrayList;
    }
}
