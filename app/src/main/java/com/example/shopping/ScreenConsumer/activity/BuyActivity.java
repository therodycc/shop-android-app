package com.example.shopping.ScreenConsumer.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.Factory.Creator;
import com.example.shopping.ScreenConsumer.Factory.IFactory;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterBuy;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterProduct;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterStore;
import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BuyActivity extends AppCompatActivity {

    //Recylcer Compone;
    RecyclerAdapterBuy adapterBuy;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    ArrayList<BuyProductModelo> buyProductModeloArrayList;


    //Dialog progress
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    TextView idorden, subtotal;
    TextInputLayout comment;

    String $idOrden, $subTotal, $idUser, $date,$idcompany, $comment;

    Double sum = 0.00;

    Button btnBuy, btnCancel;

    //factory
    Creator creator;
    IFactory orders;

    String urlDetalle = "https://startbuying.000webhostapp.com/InsertDetalleOrders.php";
    String urlCreatedOrder = "https://startbuying.000webhostapp.com/InsertOrders.php";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        recyclerView = findViewById(R.id.recyclerBuys);
        btnBuy = findViewById(R.id.btn_consumer_buy);
        idorden = findViewById(R.id.id_order_buy_consumer);
        subtotal = findViewById(R.id.subtotal_buy_consumer);
        comment = findViewById(R.id.et_comment_buy_consumer);
        btnCancel = findViewById(R.id.btn_buyCancel_consumer);

        Bundle var = getIntent().getExtras();

        buyProductModeloArrayList  = new ArrayList<>();
        buyProductModeloArrayList = GlobalUsuario.buyProductModelos;

        if ( buyProductModeloArrayList  != null && ! buyProductModeloArrayList .isEmpty()) {

            linearLayoutManager = new LinearLayoutManager(BuyActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapterBuy = new RecyclerAdapterBuy(buyProductModeloArrayList, BuyActivity.this);
            recyclerView.setAdapter(adapterBuy);
        }

        Double prices;

        for (BuyProductModelo totalprice: buyProductModeloArrayList) {

            if(totalprice.getPrice().equalsIgnoreCase(null)){
                sum+= 0.00;
            }else{
                prices = Double.parseDouble(totalprice.getPrice());
                sum+= prices;
            }

        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        $date = String.valueOf(dtf.format(now));
        $idUser = String.valueOf(GlobalUsuario.idusuario);
        $idOrden = var.getString("idorden");
        $idcompany = var.getString("idcompany");
        $subTotal = String.valueOf(sum);
        $comment = "very good product, I would like to try them ...";

        comment.getEditText().setText($comment);

        subtotal.setText($subTotal);
        idorden.setText($idOrden);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validationComment()){

                    String[] Date ={$idOrden,$date,$comment,$subTotal,$idUser,$idcompany};
                    creator = new Creator(urlCreatedOrder,BuyActivity.this);
                    orders = creator.getFactiry(3);
                    orders.create(Date);

                    Progress();


                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalUsuario.buyProductModelos.clear();
                Intent intent = new Intent(BuyActivity.this,MainConsumer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private boolean validationComment() {
        $comment = comment.getEditText().getText().toString().trim();
        if ($comment.isEmpty()) {
            comment.setError("field empty!");
            return false;
        } else {
            comment.setError(null);
            return true;
        }
    }



    private void Progress() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Loading Detalle Orders...");
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
                    creator = new Creator(urlDetalle,BuyActivity.this);
                    orders = creator.getFactiry(3);
                    for (BuyProductModelo ok: buyProductModeloArrayList) {
                        String[] Dateils ={ok.getId_orden(),ok.getId(),ok.getPrice(),ok.getCantidad()};
                        orders.createarraylist(Dateils);
                    }

                    Progressback();
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


    private void Progressback() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Loading Product...");
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
                    GlobalUsuario.buyProductModelos.clear();
                    Intent intent = new Intent(BuyActivity.this,MainConsumer.class);
                    startActivity(intent);


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
        Toast.makeText(BuyActivity.this,"You must cancel the order to go back",
                Toast.LENGTH_LONG).show();
    }


}