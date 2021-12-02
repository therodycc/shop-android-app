package com.example.shopping.screenSuplier.fragments_second;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityOrderModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCategory;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterOrder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentOrder extends Fragment {

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    View v;

    FactoryMaker Facade;

    RecyclerView recyclerView;
    RecyclerViewAdapterOrder recyclerViewAdapterOrder;
    LinearLayoutManager linearLayoutManager;

    ArrayList<EntityOrderModelo> orderModeloArrayList;

    String urls = "https://startbuying.000webhostapp.com/AllOrderSuplier.php";
    String idCompany;

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order, container, false);

        idCompany = String.valueOf(GlobalUsuario.idCompany);

        recyclerView = v.findViewById(R.id.recyclerOrder_suplier);

        imageView = v.findViewById(R.id.activity_empty_imageview);


        return  v;
    }
    @Override
    public void onStart() {
        super.onStart();
        Facade = new FactoryMaker(getActivity(), urls);
        Facade.FactoryOrdersMethodAll(idCompany);
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
        progressDialog.setTitle("Loading data...");
        progressDialog.setMessage("Wait while your data loads");
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

                    orderModeloArrayList= new ArrayList<>();
                    orderModeloArrayList = Facade.getOrders();

                  if (orderModeloArrayList != null && !orderModeloArrayList.isEmpty()) {
                        imageView.setVisibility(View.GONE);
                        linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);

                        recyclerViewAdapterOrder = new RecyclerViewAdapterOrder(orderModeloArrayList, getActivity());
                        recyclerView.setAdapter(recyclerViewAdapterOrder);

                    }else{
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
        }, 2000, 300);

    }

}