package com.example.shopping.ScreenConsumer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.Factory.Creator;
import com.example.shopping.ScreenConsumer.Factory.IFactory;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterProduct;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterStore;
import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;
import com.example.shopping.ScreenConsumer.entity.ProductModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShopsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterProduct adapterProduct;
    //LinearLayoutManager linearLayoutManager;
    ArrayList<ProductModelo> productModeloArrayList;

    String urlProduct = "https://startbuying.000webhostapp.com/allProductshops.php";

    //Dialog progress
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    //Factory
    Creator creator;
    IFactory product;

    String datos = "";
    StringBuffer buffer = null;

    String idCompany;

    FloatingActionButton floatingActionButton;

    ArrayList<BuyProductModelo> buyProductModelos;
    String idorden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        recyclerView = findViewById(R.id.recyclerShops_product);
        floatingActionButton = findViewById(R.id.fab_shop_product);



        GlobalUsuario.buyProductModelos = new ArrayList<>();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (ProductModelo pd: adapterProduct.checkprodutArrayList) {
                    GlobalUsuario.buyProductModelos.add(new BuyProductModelo(pd.getId(),pd.getName(),pd.getPrice(),
                            "1",idorden));
                }

                if(GlobalUsuario.buyProductModelos.size() > 0){
                    Bundle var = new Bundle();
                    Intent start = new Intent(ShopsActivity.this, BuyActivity.class);
                    var.putString("idorden", idorden);
                    var.putString("idcompany", idCompany);
                    start.putExtras(var);
                    startActivity(start);
                }else{
                    Snackbar.make(v,"No product in cart",Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        TextInputLayout search = findViewById(R.id.tf_search_product);
        search.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        Bundle var = getIntent().getExtras();
        idCompany = var.getString("idcompany");
        
    }

    private void filter(String toString) {
        ArrayList<ProductModelo> filterlist = new ArrayList<>();
        for (ProductModelo item : productModeloArrayList){
            if (item.getName().toLowerCase().contains(toString.toLowerCase())){
                filterlist.add(item);
            }
        }
        adapterProduct.filterList(filterlist);

    }

    @Override
        public void onStart() {
            super.onStart();
            creator = new Creator(urlProduct,this);
            product = creator.getFactiry(2);
            product.all(idCompany);
            idorden = idOrdenGenerated();
        }

        @Override
        public void onResume() {
            super.onResume();
            Progress();
    }

    private void Progress() {

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

                    productModeloArrayList = new ArrayList<>();
                    productModeloArrayList = product.productmodelolist();

                    if ( productModeloArrayList != null && ! productModeloArrayList.isEmpty()) {

                        layoutManager = new GridLayoutManager(ShopsActivity.this,2);
                        recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setHasFixedSize(true);

                        adapterProduct = new RecyclerAdapterProduct(productModeloArrayList, ShopsActivity.this);
                        recyclerView.setAdapter(adapterProduct);
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

    private String idOrdenGenerated(){

        char [] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVXWZ".toCharArray();
        Random random = new Random();

        String clave = "";

        for (int i=0;i<12;i++){
            clave += chars[random.nextInt(chars.length)];
        }

        return clave;
    }


}