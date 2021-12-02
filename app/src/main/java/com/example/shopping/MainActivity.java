package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.shopping.screenMain.ScreenLogin;

public class MainActivity extends AppCompatActivity {

    Animation top_animation;
    ImageView iv_intro;

    static int duration_screen = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        top_animation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        iv_intro = findViewById(R.id.iv_intro);

        iv_intro.setAnimation(top_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(MainActivity.this, ScreenLogin.class);
                startActivity(start);
                overridePendingTransition(R.anim.to_right,R.anim.out_left);

            }
        },duration_screen);




    }







}