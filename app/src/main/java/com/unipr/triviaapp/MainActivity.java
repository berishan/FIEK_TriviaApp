package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.widget.TextView;

import com.unipr.triviaapp.helpers.ExtrasHelper;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;
    private Animation topAnimation, bottomAnimation;

    private ImageView image;
    private TextView twAppName, twAppSlogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

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
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                String checkbox = preferences.getString("remember", "");
                if(checkbox.equals("true")){
                    startActivity(new Intent(MainActivity.this, CoreActivity.class));
                    finish();

                } else{
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }

            }
        }, SPLASH_SCREEN);
    }
}