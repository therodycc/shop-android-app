package com.example.shopping.screenSuplier;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.Factory.Creator;
import com.example.shopping.ScreenConsumer.Factory.IFactory;
import com.example.shopping.ScreenConsumer.activity.DetalleOrderActivity;
import com.example.shopping.ScreenConsumer.activity.MainConsumer;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterDetalleOrders;
import com.example.shopping.ScreenConsumer.entity.DetalleOrderModelo;
import com.example.shopping.screenMain.Login;
import com.example.shopping.singlenton.SinglentonDB;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CompleteOrderActivity extends AppCompatActivity {

    TextView idorder, nameclient, phoneclient, totalorder, dateorder, commentorder;
    Button close, completeorder;

    RecyclerAdapterDetalleOrders adapterDetalleOrders;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    ArrayList<DetalleOrderModelo> detalleOrderModeloArrayList;

    String urlAll="https://startbuying.000webhostapp.com/DetalleOrder.php";
    String urlUpdateOrders="https://startbuying.000webhostapp.com/UpdateStateOrders.php";

    //Factory
    Creator creator;
    IFactory order;

    String idOrder="";

    //Dialog progress
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);

        idorder = findViewById(R.id.idorden_complete_order);
        nameclient = findViewById(R.id.namecliente_complete_order);
        phoneclient = findViewById(R.id.orderphone_complete_order);
        totalorder = findViewById(R.id.ordertotal_complete_order);
        dateorder = findViewById(R.id.orderdate_complete_order);
        commentorder = findViewById(R.id.orderComment_complete_order);

        close = findViewById(R.id.btnClose_complete_order);
        completeorder = findViewById(R.id.btnState_complete_order);

        recyclerView = findViewById(R.id.recyclerOrderDetalleSuplier_complete_order);

        Bundle var = getIntent().getExtras();

        idorder.setText(var.getString("id_order"));
        nameclient.setText(var.getString("nameclient"));
        dateorder.setText(var.getString("date"));
        totalorder.setText(var.getString("total"));
        commentorder.setText(var.getString("comment"));
        phoneclient.setText(var.getString("phone"));

        idOrder = var.getString("id_order");

        String states = var.getString("state");

        if(states.equalsIgnoreCase("Pending")){
            completeorder.setVisibility(View.VISIBLE);
        }else{
            completeorder.setVisibility(View.GONE);
        }

        completeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogWarning();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        creator = new Creator(urlAll, CompleteOrderActivity.this);
        order = creator.getFactiry(4);
        order.all(idOrder);
    }

    @Override
    public void onResume() {
        super.onResume();
        Progress();
    }

    private void dialogWarning(){

        final MaterialAlertDialogBuilder build = new MaterialAlertDialogBuilder(CompleteOrderActivity.this)
                .setTitle("Warning!")
                .setIcon(R.drawable.ic_warning)
                .setBackground(getResources().getDrawable(R.drawable.background_alert_term_policy))
                .setMessage("By verifying this order as completed, the order status will automatically be permanently disabled. Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CompleteOrders();
                Progresss();
            }
        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        build.show();

    }


    private void Progresss() {

        progressDialog = new ProgressDialog(CompleteOrderActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Completed order...");
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

                  onBackPressed();

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



    private void Progress() {

        progressDialog = new ProgressDialog(CompleteOrderActivity.this);
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

                    detalleOrderModeloArrayList = new ArrayList<>();
                    detalleOrderModeloArrayList = order.detalleorderlist();

                    if ( detalleOrderModeloArrayList != null && ! detalleOrderModeloArrayList.isEmpty()) {
                        linearLayoutManager = new LinearLayoutManager(CompleteOrderActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);

                        adapterDetalleOrders = new RecyclerAdapterDetalleOrders(detalleOrderModeloArrayList, CompleteOrderActivity.this);
                        recyclerView.setAdapter(adapterDetalleOrders);
                    }else {
                        Toast.makeText(CompleteOrderActivity.this,"Vacio!",Toast.LENGTH_LONG).show();


                    }



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

    private void CompleteOrders(){
        StringRequest con = new StringRequest(StringRequest.Method.POST, urlUpdateOrders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(CompleteOrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_order", idOrder);
                return parametros;

            }
        };
        /*RequestQueue requestQueue = Volley.newRequestQueue(CompleteOrderActivity.this);
        requestQueue.add(con);*/

        SinglentonDB.getInstance(CompleteOrderActivity.this).addToRequestQueue(con);

    }

}