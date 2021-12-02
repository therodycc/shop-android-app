package com.example.shopping.screenSuplier;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.MainActivity;
import com.example.shopping.R;
import com.example.shopping.screenMain.Login;
import com.example.shopping.screenSuplier.fragments.FragmentAccount;
import com.example.shopping.screenSuplier.fragments.FragmentBusiness;
import com.example.shopping.screenSuplier.fragments.FragmentLogout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainSuplierActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    //Fragments
    FragmentBusiness company = new FragmentBusiness();
    FragmentAccount profile = new FragmentAccount();
    FragmentLogout logout = new FragmentLogout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_suplier);

        bottomNavigationView = findViewById(R.id.bottom_navegation_view_suplier_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_company);

    }

    @Override
    public void onBackPressed() {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                    .replace(R.id.container_supplier, logout).commit();
        bottomNavigationView.setSelectedItemId(R.id.navigation_logout);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_company:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier, company).commit();
                return true;

            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier, profile).commit();
                return true;

            case R.id.navigation_logout:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_fade, R.anim.out_fade)
                        .replace(R.id.container_supplier, logout).commit();
                return true;

        }

        return false;
    }




}