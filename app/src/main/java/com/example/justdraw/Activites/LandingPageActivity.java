package com.example.justdraw.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.Constant;

public class LandingPageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView google,facebook;
    Button mobile_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Comman.Statusbar(this,getWindow());
        google=findViewById(R.id.google);
        facebook=findViewById(R.id.facebook);
        mobile_no=findViewById(R.id.mobile_cont_butt);

        google.setOnClickListener(this);
        facebook.setOnClickListener(this);
        mobile_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mobile_cont_butt:
                startActivity(new Intent(this,UserLoginActivity.class));
                Animatoo.animateSwipeLeft(this);
                break;
            case R.id.google:
                Comman.topSnakBar(this,v, Constant.Features);
                break;
            case R.id.facebook:
                Comman.topSnakBar(this,v, Constant.Features);
                break;
        }
    }
}