package com.example.shopping.screenSuplier.Facade.EntityFactory;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.example.shopping.screenSuplier.UtilidadesListView.EntityProductModelo;
import com.example.shopping.singlenton.SinglentonDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FactoryProduct implements IFactory {

    Context context;
    ArrayList<EntityProductModelo> productModeloArrayList;
    String url;

    public FactoryProduct(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public void Create(final String[] datos) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
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
                parametros.put("image", datos[0]);
                parametros.put("name_product", datos[1]);
                parametros.put("description_product", datos[2]);
                parametros.put("price_product", datos[3]);
                parametros.put("id_category", datos[4]);
                return parametros;

            }
        };
        SinglentonDB.getInstance(context).addToRequestQueue(con);
    }

    @Override
    public void Delete(final String id) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", id);
                return parametros;

            }
        };
        SinglentonDB.getInstance(context).addToRequestQueue(con);
    }

    @Override
    public void Edit(final String[] datos) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
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
                parametros.put("id", datos[0]);
                parametros.put("name", datos[1]);
                parametros.put("description", datos[2]);
                parametros.put("price", datos[3]);
                parametros.put("idcategory", datos[4]);
                parametros.put("image", datos[5]);
                return parametros;

            }
        };
        SinglentonDB.getInstance(context).addToRequestQueue(con);
    }

    @Override
    public void AllSpecific(final String idCompany) {
        final StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        productModeloArrayList = new ArrayList<>();

                        for(int i=0; i < array.length(); i++){

                            JSONObject row = array.getJSONObject(i);
                            String id = String.valueOf(row.getInt("id"));
                            String description = row.getString("description");
                            String name = row.getString("name");
                            String price = row.getString("price");
                            String image = row.getString("image");
                            String namecategory = row.getString("category");

                            String url = "https://startbuying.000webhostapp.com/Images/"+image;

                            productModeloArrayList.add(new EntityProductModelo(id,description,name,price,
                                    url,namecategory));

                        }

                    } catch (JSONException ex) {
                        Log.i("Error View Data", ex.toString());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_company", idCompany);
                return parametros;

            }
        };

        con.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
        Log.i("Data", "Mostrando Company");
        return productModeloArrayList;
    }

    @Override
    public ArrayList<EntityOrderModelo> orderArrayList() {
        return null;
    }

}
