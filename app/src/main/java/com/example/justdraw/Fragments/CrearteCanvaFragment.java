package com.example.justdraw.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.justdraw.Activites.CanvasActivity;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.Constant;


public class CrearteCanvaFragment extends Fragment implements View.OnClickListener {

    CardView blank_canvas,image_layout,collage;

    public CrearteCanvaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_crearte_canva, container, false);
        blank_canvas=rootView.findViewById(R.id.blank_canvas);
        image_layout=rootView.findViewById(R.id.image_layout);
        collage=rootView.findViewById(R.id.Collage_layout);
        image_layout.setOnClickListener(this);
        blank_canvas.setOnClickListener(this);
        collage.setOnClickListener(this);
        return  rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blank_canvas:
                startActivity(new Intent(getContext(), CanvasActivity.class));
                Animatoo.animateSlideLeft(getContext());
                break;
            case R.id.image_layout:
                Comman.topSnakBar(getContext(),v, Constant.Features);
                break;
            case R.id.Collage_layout:
                Comman.topSnakBar(getContext(),v, Constant.Features);
                break;
        }
    }
}