package com.example.justdraw.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.Constant;
import com.example.justdraw.Utilities.MySharedPrefrence;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mobile_card;
    Button login_butt,ok_butt;
    EditText mobile_edit;
    LinearLayout internet_layout;
    RelativeLayout main_layout;
    MySharedPrefrence m;
    FirebaseAuth firebaseAuth;
    String codeSent;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        m=MySharedPrefrence.instanceOf(UserLoginActivity.this);
        view = getWindow().getDecorView().getRootView();
        mobile_edit=findViewById(R.id.mobile_no);
        mobile_card=findViewById(R.id.mobile_layout);
        login_butt=findViewById(R.id.login_butt);
        internet_layout=findViewById(R.id.internet_layout);
        main_layout=findViewById(R.id.main_layout);
        ok_butt=findViewById(R.id.retry_butt);

        login_butt.setOnClickListener(this);
        ok_butt.setOnClickListener(this);

        firebaseAuth= FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Comman.isConnectedToInternet(this)){
            internet_layout.setVisibility(View.VISIBLE);
            main_layout.setVisibility(View.GONE);
        }else {
            internet_layout.setVisibility(View.GONE);
            main_layout.setVisibility(View.VISIBLE);
        }
    }

    public void MobileCard() {
        mobile_card.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter);
        animation.setDuration(1000);
        mobile_card.setAnimation(animation);
        mobile_card.animate();
        animation.start();
        internet_layout.setVisibility(View.GONE);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_butt:
                UserSignUp();
                break;
            case R.id.retry_butt:
                if (!Comman.isConnectedToInternet(this)){
                    Comman.BottomSnackBar(this,v, Constant.NOInternet);

                }else {
                    MobileCard();
                }
                break;
        }
    }

    public void UserSignUp(){
        if (!Comman.isConnectedToInternet(this)){
            Comman.BottomSnackBar(this,view,Constant.NOInternet);
        }else {
            String mobile = mobile_edit.getText().toString().trim();

            if (mobile.isEmpty() || mobile.length()>10){
                Comman.BottomSnackBar(this,view,Constant.NumberLength);
            }else {
                Intent intent = new Intent(UserLoginActivity.this, VerifyOtp.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                Animatoo.animateSwipeLeft(this);
                m.setUserPhone("We just sent you a verification code on this "+ mobile);
                m.setLoggedIn(true);

            }

        }
    }
}