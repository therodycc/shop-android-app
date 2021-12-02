package com.example.shopping.ScreenConsumer.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.Factory.Creator;
import com.example.shopping.ScreenConsumer.Factory.IFactory;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterOrder;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterStore;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;
import com.example.shopping.ScreenConsumer.entity.StoreModelo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OrdersFragment extends Fragment {

   View v;


    //Recylcer Compone;
    RecyclerAdapterOrder adapterOrder;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    //Dialog progress
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    //list
    String urlAll="https://startbuying.000webhostapp.com/AllOrderConsumer.php";
    ArrayList<OrderModelo> orderModeloArrayList;

    //Factory
    Creator creator;
    IFactory order;

    String idUser="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = v.findViewById(R.id.recyclerOrder_consumer);
        idUser = String.valueOf(GlobalUsuario.idusuario);

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        creator = new Creator(urlAll,getActivity());
        order = creator.getFactiry(3);
        order.all(idUser);

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
        progressDialog.setMessage("Loading Orders...");
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

                    orderModeloArrayList = new ArrayList<>();
                    orderModeloArrayList = order.ordersmodelolist();

                    if ( orderModeloArrayList != null && ! orderModeloArrayList.isEmpty()) {
                        linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);

                        adapterOrder = new RecyclerAdapterOrder(orderModeloArrayList, getActivity());
                        recyclerView.setAdapter(adapterOrder);
                    }else {
                        Toast.makeText(getActivity(),"Vacio!",Toast.LENGTH_LONG).show();
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