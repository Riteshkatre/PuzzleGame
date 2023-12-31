package com.example.puzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.puzzlegame.R;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo=findViewById(R.id.logo);
        tv=findViewById(R.id.tv);
        Animation Animation = AnimationUtils.loadAnimation(this, R.anim.slide_from_left);


        tv.startAnimation(Animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);

    }
}