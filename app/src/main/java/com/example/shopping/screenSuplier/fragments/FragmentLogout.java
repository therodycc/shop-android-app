package com.example.shopping.screenSuplier.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopping.R;
import com.example.shopping.screenMain.Login;
import com.example.shopping.screenSuplier.MainSuplierActivity;


public class FragmentLogout extends Fragment {

    View v;
    Button leave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_logout, container, false);
        leave = v.findViewById(R.id.btn_logout_suplier);

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sc = new Intent(getContext(), Login.class);
                sc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sc);
            }
        });

        return v;
    }

}