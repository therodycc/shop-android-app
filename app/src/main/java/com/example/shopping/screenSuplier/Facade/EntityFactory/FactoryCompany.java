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
import com.example.shopping.singlenton.SinglentonDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FactoryCompany implements IFactory {

    Context context;
    ArrayList<EntityCompanyModelo> companyModeloArrayList;
    String url;

    public FactoryCompany(Context context, String url) {
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
                parametros.put("social_company", datos[1]);
                parametros.put("phone_company", datos[2]);
                parametros.put("country_company", datos[3]);
                parametros.put("addres_company", datos[4]);
                parametros.put("fk_id_usuario", datos[5]);
                parametros.put("name_company", datos[6]);
                return parametros;

            }
        };
        SinglentonDB.getInstance(context).addToRequestQueue(con);
    }

    @Override
    public void Delete(String id) {

    }

    @Override
    public void Edit(String[] datos) {

    }


    @Override
    public void AllSpecific(final String idUsuario) {
            final StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        companyModeloArrayList = new ArrayList<>();

                            for(int i=0; i < array.length(); i++){

                                JSONObject row = array.getJSONObject(i);
                                String id = String.valueOf(row.getInt("id_company"));
                                String image = row.getString("image_company");
                                String name = row.getString("name_company");
                                String social= row.getString("social_company");
                                String phone = row.getString("phone_company");
                                String contry = row.getString("country_company");
                                String addres = row.getString("addres_company");
                                String status = row.getString("status_company");

                                String url = "https://startbuying.000webhostapp.com/Images/"+image;

                                companyModeloArrayList.add(new EntityCompanyModelo(name,phone,contry,addres,
                                        social,url,status,id));

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
                parametros.put("id_user", idUsuario);
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
        Log.i("Data", "Mostrando Company");
        return companyModeloArrayList;
    }

    @Override
    public ArrayList<EntityProductModelo> productArrayList() {
        return null;
    }
    @Override
    public ArrayList<EntityOrderModelo> orderArrayList() {
        return null;
    }
}
