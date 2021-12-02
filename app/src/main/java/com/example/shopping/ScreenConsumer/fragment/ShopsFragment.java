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
import android.widget.Toolbar;

import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.Factory.Creator;
import com.example.shopping.ScreenConsumer.Factory.IFactory;
import com.example.shopping.ScreenConsumer.adapter.RecyclerAdapterStore;
import com.example.shopping.ScreenConsumer.entity.StoreModelo;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCategory;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ShopsFragment extends Fragment {

    View v;

    //Recylcer Compone;
    RecyclerAdapterStore adapterStore;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    //Dialog progress
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    //list
    String urlAll="https://startbuying.000webhostapp.com/allStore.php";
    ArrayList<StoreModelo> storeModeloArrayList;

    //Factory
    Creator creator;
    IFactory shops;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_shops, container, false);

        recyclerView = v.findViewById(R.id.recyclerShops);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        creator = new Creator(urlAll,getActivity());
        shops = creator.getFactiry(1);
        shops.all("all");
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
        progressDialog.setMessage("Loading Shops...");
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

                    storeModeloArrayList = new ArrayList<>();
                    storeModeloArrayList = shops.storemodelolist();

                    if ( storeModeloArrayList != null && ! storeModeloArrayList.isEmpty()) {

                        linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);

                        adapterStore = new RecyclerAdapterStore(storeModeloArrayList, getActivity());
                        recyclerView.setAdapter(adapterStore);
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