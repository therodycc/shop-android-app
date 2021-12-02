package com.example.shopping.screenMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopping.MainActivity;
import com.example.shopping.R;

public class ScreenLogin extends AppCompatActivity {

    private long backpressdtime;
    Toast alert;
    Button enter;

    Animation bottom_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_login);

        enter = findViewById(R.id.btn_enter);
        bottom_animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        enter.setAnimation(bottom_animation);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(ScreenLogin.this, Login.class);
                startActivity(start);
                overridePendingTransition(R.anim.in_bottom,R.anim.out_bottom);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if(backpressdtime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            alert.cancel();
            return;
        }else{
            alert = Toast.makeText(getApplicationContext(),"you must press the exit button",Toast.LENGTH_SHORT);
            alert.show();
        }
        backpressdtime = System.currentTimeMillis();
    }



}