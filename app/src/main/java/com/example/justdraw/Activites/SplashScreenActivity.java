package com.example.justdraw.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.Constant;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    private Runnable callback;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        view = getWindow().getDecorView().getRootView();
        Comman.Statusbar(this,getWindow());

        handler = new Handler();
        callback = () -> {
            if (Comman.Check_Login(this)){
                startActivity(new Intent(SplashScreenActivity.this,MaindashboardActivity.class));
                finish();
            }else {
                startActivity(new Intent(SplashScreenActivity.this, LandingPageActivity.class));
                finish();
            }

        };
        handler.postDelayed(callback, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(callback);
    }
}