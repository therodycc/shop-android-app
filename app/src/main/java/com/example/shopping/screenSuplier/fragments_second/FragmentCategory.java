package com.example.shopping.screenSuplier.fragments_second;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenMain.Login;
import com.example.shopping.screenSuplier.AddCategoryActivity;
import com.example.shopping.screenSuplier.AddCompanyActivity;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.example.shopping.screenSuplier.MainSuplierActivity;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCompanyModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCategory;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCompany;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentCategory extends Fragment {

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    View v;

    FactoryMaker Facade;
    Button addCategory;

    RecyclerView recyclerView;
    RecyclerViewAdapterCategory recyclerViewAdapterCategory;
    LinearLayoutManager linearLayoutManager;

    ArrayList<EntityCategoryModelo> categoryModeloArrayList;

    String url = "https://startbuying.000webhostapp.com/Allcategory.php";
    String idCompany;

    SwipeRefreshLayout swipeRefreshLayout;

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_category, container, false);



        imageView = v.findViewById(R.id.activity_empty_imageview);
        addCategory = v.findViewById(R.id.btn_addCompany_category);
        recyclerView = v.findViewById(R.id.recyclerCompany_category);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);

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

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(start);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Facade = new FactoryMaker(getActivity(), url);
        Facade.FactoryCategoryMethodAll(idCompany);
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

                        categoryModeloArrayList = new ArrayList<>();
                        categoryModeloArrayList = Facade.getCategory();

                        if (categoryModeloArrayList != null && !categoryModeloArrayList.isEmpty()) {
                            imageView.setVisibility(View.GONE);
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerViewAdapterCategory = new RecyclerViewAdapterCategory(categoryModeloArrayList, getActivity());
                            recyclerView.setAdapter(recyclerViewAdapterCategory);
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
            }, 1000, 300);

        }

    }






