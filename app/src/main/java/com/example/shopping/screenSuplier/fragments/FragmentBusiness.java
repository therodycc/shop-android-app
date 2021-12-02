package com.example.shopping.screenSuplier.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenMain.ScreenLogin;
import com.example.shopping.screenSuplier.AddCompanyActivity;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCompanyModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCategory;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCompany;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentBusiness extends Fragment {

    View v;
    Context context;

    public FragmentBusiness(){

    }
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    FactoryMaker Facade;

    String idUsuario;

    String url = "https://startbuying.000webhostapp.com/allCompany.php";
    FloatingActionButton addCompany;

    RecyclerView recyclerViewcompany;

    RecyclerViewAdapterCompany recyclerViewAdapterCompany;
    ArrayList<EntityCompanyModelo> companyModeloArrayList;
    LinearLayoutManager linearLayoutManager;

    ImageView imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_business, container, false);

        addCompany = v.findViewById(R.id.floating_add_company);
        recyclerViewcompany = (RecyclerView) v.findViewById(R.id.recyclerCompany);
        imageView = (ImageView) v.findViewById(R.id.activity_empty_imageview);


        idUsuario = String.valueOf(GlobalUsuario.idusuario);

        addCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getContext(),AddCompanyActivity.class);
                startActivity(start);


            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Facade = new FactoryMaker(getActivity(), url);
        Facade.FactoryCompanyMethodAll(idUsuario);
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

                    companyModeloArrayList = new ArrayList<>();
                    companyModeloArrayList = Facade.getCompany();

                    if (companyModeloArrayList != null && !companyModeloArrayList.isEmpty()) {
                        imageView.setVisibility(View.GONE);
                        linearLayoutManager= new LinearLayoutManager(getActivity());
                        recyclerViewcompany.setLayoutManager(linearLayoutManager);
                        recyclerViewAdapterCompany = new RecyclerViewAdapterCompany(getActivity(),companyModeloArrayList);
                        recyclerViewcompany.setAdapter(recyclerViewAdapterCompany);

                    } else {
                        imageView.setVisibility(View.VISIBLE);
                        Log.e("Error", "Arraylist null o int 0 JsonError!!");
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
        }, 1000, 300);

    }



}