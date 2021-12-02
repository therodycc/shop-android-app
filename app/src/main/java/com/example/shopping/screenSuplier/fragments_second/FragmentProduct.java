package com.example.shopping.screenSuplier.fragments_second;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenSuplier.AddCategoryActivity;
import com.example.shopping.screenSuplier.AddProductActivty;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityProductModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCategory;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterProduct;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentProduct extends Fragment {

    public FragmentProduct() {
        // Required empty public constructor
    }

    View v;

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    FactoryMaker Facade;
    Button addProduct;
    RecyclerView recyclerView;
    String url = "https://startbuying.000webhostapp.com/allProduct.php";

    RecyclerViewAdapterProduct recyclerViewAdapterProduct;
    LinearLayoutManager linearLayoutManager;

    ArrayList<EntityProductModelo> productModeloArrayList;
    String idCompany;

    SwipeRefreshLayout swipeRefreshLayout;

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_product, container, false);

            addProduct = v.findViewById(R.id.btn_addCompany_product);
            recyclerView = v.findViewById(R.id.recyclerCompany_product);
            swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
            imageView = v.findViewById(R.id.activity_empty_imageview);


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onStart();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    },5000);

                }
            });



            idCompany = String.valueOf(GlobalUsuario.idCompany);

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent start = new Intent(getActivity(), AddProductActivty.class);
                    startActivity(start);
                }
            });


            return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Facade = new FactoryMaker(getActivity(), url);
        Facade.FactoryProductMethodAll(idCompany);
    }

    @Override
    public void onResume() {
        super.onResume();
        Progress();
    }

    private void Progress() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Loading data...");
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
                    productModeloArrayList = Facade.getProduct();

                    if (productModeloArrayList != null && !productModeloArrayList.isEmpty()) {
                        imageView.setVisibility(View.GONE);
                        linearLayoutManager= new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);

                        recyclerViewAdapterProduct = new RecyclerViewAdapterProduct(productModeloArrayList,getActivity());
                        recyclerView.setAdapter(recyclerViewAdapterProduct);

                    } else {

                        imageView.setVisibility(View.VISIBLE);

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
        }, 2000, 100);
    }


}