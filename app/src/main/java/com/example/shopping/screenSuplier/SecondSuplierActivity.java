package com.example.shopping.screenSuplier;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.activity.MainConsumer;
import com.example.shopping.screenSuplier.fragments_second.FragmentCategory;
import com.example.shopping.screenSuplier.fragments_second.FragmentOrder;
import com.example.shopping.screenSuplier.fragments_second.FragmentProduct;
import com.example.shopping.screenSuplier.fragments_second.FragmentSetting;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SecondSuplierActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView title;
    BottomNavigationView bottomNavigationView;

    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentProduct fragmentProduct = new FragmentProduct();
    FragmentOrder fragmentOrder = new FragmentOrder();
    FragmentSetting fragmentSetting = new FragmentSetting();

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    String url_countOrdersPending = "https://startbuying.000webhostapp.com/CountOrderPending.php";
    String idCompany = "";

    int countOrders = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_suplier);


        idCompany = String.valueOf(GlobalUsuario.idCompany);

        bottomNavigationView = findViewById(R.id.bottom_navegation_view_suplier_second);
        title = findViewById(R.id.title_second_main_suplier);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_company_category);

    }

    @Override
    protected void onStart() {
        super.onStart();
        countOrder();
    }

    @Override
    protected void onResume() {
        super.onResume();
       Progress();
    }

    private void Progress() {

        progressDialog = new ProgressDialog(SecondSuplierActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMax(100);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                e = e + 5;
                if (e <= 100) {
                    progressDialog.setProgress(e);
                } else {
                    timer.cancel();
                    progressDialog.dismiss();
                    e = 0;

                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_company_order);
                    if(countOrders > 0){
                        badge.setVisible(true);
                    }else{
                        badge.setVisible(false);
                    }
                    badge.setNumber(countOrders);

                }

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1000, 200);

    }


    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                .replace(R.id.container_supplier_second, fragmentSetting).commit();
        title.setText("Setting");
        bottomNavigationView.setSelectedItemId(R.id.navigation_company_setting);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_company_category:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier_second, fragmentCategory).commit();
                title.setText("Category");
                return true;

            case R.id.navigation_company_product:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier_second, fragmentProduct).commit();
                title.setText("Product");
                return true;

            case R.id.navigation_company_order:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier_second, fragmentOrder).commit();
                title.setText("Order");
                return true;

            case R.id.navigation_company_setting:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier_second, fragmentSetting).commit();
                title.setText("Setting");
                return true;

        }

        return false;
    }


    private void countOrder(){
        StringRequest con = new StringRequest(StringRequest.Method.POST, url_countOrdersPending, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        for(int i=0; i < array.length(); i++){

                            JSONObject row = array.getJSONObject(i);

                            countOrders = row.getInt("pending");
                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondSuplierActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_company", idCompany);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SecondSuplierActivity.this);
        requestQueue.add(con);
    }





}