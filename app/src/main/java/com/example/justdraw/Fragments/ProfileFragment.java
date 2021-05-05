package com.example.justdraw.Fragments;

import android.content.Intent;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.justdraw.Activites.EditUserProfileActivity;
import com.example.justdraw.Activites.MaindashboardActivity;
import com.example.justdraw.Activites.UserLoginActivity;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.widget.Segow_UI_Bold_Font;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    Segow_UI_Bold_Font logout,user_name,cancel,logout2;
    CircleImageView user_pic;
    ImageView move_back;
    Button edit_profile;
    RelativeLayout logout_alert;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        user_name=rootView.findViewById(R.id.user_name);
        user_pic=rootView.findViewById(R.id.user_image);
        logout=rootView.findViewById(R.id.logout);
        cancel=rootView.findViewById(R.id.cancel);
        logout2=rootView.findViewById(R.id.logout2);

        move_back=rootView.findViewById(R.id.move_left);
        edit_profile=rootView.findViewById(R.id.edit_profile);
        logout_alert=rootView.findViewById(R.id.logout_alert);

        edit_profile.setOnClickListener(this);
        move_back.setOnClickListener(this);
        logout.setOnClickListener(this);
        logout2.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                LogoutAlert();
                break;
            case R.id.move_left:
                startActivity(new Intent(getContext(), MaindashboardActivity.class));
                Animatoo.animateSwipeRight(getContext());
                break;
            case R.id.edit_profile:
                startActivity(new Intent(getContext(), EditUserProfileActivity.class));
                Animatoo.animateSwipeLeft(getContext());

                break;
            case R.id.logout2:
                startActivity(new Intent(getContext(), UserLoginActivity.class));
                Animatoo.animateSwipeRight(getContext());
                break;
            case R.id.cancel:
                DismissLogoutAlert();
                break;

        }

    }
    public void LogoutAlert() {
        logout_alert.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_enter);
        animation.setDuration(1000);
        logout_alert.setAnimation(animation);
        logout_alert.animate();
        animation.start();

    }
    public void DismissLogoutAlert() {
        logout_alert.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_exit);
        animation.setDuration(1000);
        logout_alert.setAnimation(animation);
        logout_alert.animate();
        animation.start();

    }


}