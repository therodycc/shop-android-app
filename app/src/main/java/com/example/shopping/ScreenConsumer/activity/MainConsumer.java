package com.example.shopping.ScreenConsumer.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterOrder;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;
import com.example.shopping.ScreenConsumer.fragment.OrdersFragment;
import com.example.shopping.ScreenConsumer.fragment.SettingConsumerFragment;
import com.example.shopping.ScreenConsumer.fragment.ShopsFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainConsumer extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    ShopsFragment shopsFragment = new ShopsFragment();
    OrdersFragment ordersFragment = new OrdersFragment();
    SettingConsumerFragment settingConsumerFragment = new SettingConsumerFragment();

    String urlCount_orders = "https://startbuying.000webhostapp.com/CountOrders.php";
    String idUser;
    int countOrders;

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_consumer);

        idUser = String.valueOf(GlobalUsuario.idusuario);
        countOrder();
        Progress();

        bottomNavigationView = findViewById(R.id.bottom_navegation_view_consumer_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_shops_c);


    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                .replace(R.id.container_consumer, settingConsumerFragment).commit();
    }

    private void Progress() {

        progressDialog = new ProgressDialog(MainConsumer.this);
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

                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_orders_c);
                    badge.setVisible(true);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_shops_c:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_consumer, shopsFragment).commit();
                return true;

            case R.id.navigation_orders_c:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_consumer, ordersFragment).commit();
                return true;

            case R.id.navigation_setting_c:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_consumer, settingConsumerFragment).commit();
                return true;

        }

        return false;
    }


    private void countOrder(){
        StringRequest con = new StringRequest(StringRequest.Method.POST, urlCount_orders, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        for(int i=0; i < array.length(); i++){

                            JSONObject row = array.getJSONObject(i);
                            countOrders = row.getInt("count_order");

                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainConsumer.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_user", idUser);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainConsumer.this);
        requestQueue.add(con);
    }



}