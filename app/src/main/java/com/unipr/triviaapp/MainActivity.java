package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    private Animation topAnimation, bottomAnimation;
    private ImageView image;
    private TextView twAppName, twAppSlogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        image = findViewById(R.id.iwLogo);
        twAppName = findViewById(R.id.twAppName);
        twAppSlogan = findViewById(R.id.twAppSlogan);

        topAnimation = AnimationUtils.loadAnimation(
                MainActivity.this,
                R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(
                MainActivity.this,
                R.anim.bottom_animation);


        image.setAnimation(topAnimation);
        twAppName.setAnimation(bottomAnimation);
        twAppSlogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_SCREEN);
    }
}