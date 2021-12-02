package com.example.shopping.ScreenConsumer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.Factory.Creator;
import com.example.shopping.ScreenConsumer.Factory.IFactory;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterBuy;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterDetalleOrders;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterOrder;
import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;
import com.example.shopping.ScreenConsumer.entity.DetalleOrderModelo;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DetalleOrderActivity extends AppCompatActivity {

    TextView id,name,phone,state,total;

    RecyclerAdapterDetalleOrders adapterDetalleOrders;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    ArrayList<DetalleOrderModelo> detalleOrderModeloArrayList;

    String urlAll="https://startbuying.000webhostapp.com/DetalleOrder.php";

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

    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_order);

        id = findViewById(R.id.consumer_orderViewDetails_idorden);
        name = findViewById(R.id.consumer_orderViewDetails_nameCompany);
        phone = findViewById(R.id.consumer_orderViewDetails_phone);
        state = findViewById(R.id.consumer_orderViewDetails_status);
        total = findViewById(R.id.consumer_orderViewDetails_total);
        close = findViewById(R.id.consumer_orderViewDetails_btnClose);

        Bundle st = getIntent().getExtras();

        id.setText(st.getString("idorder"));
        name.setText(st.getString("namecompany"));
        phone.setText(st.getString("phone"));
        state.setText(st.getString("state"));
        total.setText(st.getString("total"));
        idOrder = id.getText().toString();

        recyclerView = findViewById(R.id.consumer_orderViewDetails_recycler);

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
        creator = new Creator(urlAll,DetalleOrderActivity.this);
        order = creator.getFactiry(4);
        order.all(idOrder);

    }

    @Override
    public void onResume() {
        super.onResume();
        Progress();
    }

    private void Progress() {

        progressDialog = new ProgressDialog(DetalleOrderActivity.this);
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
                        linearLayoutManager = new LinearLayoutManager(DetalleOrderActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);

                        adapterDetalleOrders = new RecyclerAdapterDetalleOrders(detalleOrderModeloArrayList, DetalleOrderActivity.this);
                        recyclerView.setAdapter(adapterDetalleOrders);
                    }else {
                        Toast.makeText(DetalleOrderActivity.this,"Vacio!",Toast.LENGTH_LONG).show();
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





}