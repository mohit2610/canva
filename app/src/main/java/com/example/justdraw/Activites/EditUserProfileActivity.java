package com.example.justdraw.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView move_back;
    EditText name;
    Button done;
    CircleImageView user_pic;
    RelativeLayout circular_layout;

    private Uri imageUri;
    private Bitmap compresor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        move_back=findViewById(R.id.move_left);
        name=findViewById(R.id.user_name);
        done=findViewById(R.id.done_button);
        user_pic=findViewById(R.id.circle_user_pic);
        circular_layout=findViewById(R.id.select_image);

        move_back.setOnClickListener(this);
        done.setOnClickListener(this);
        circular_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.move_back:

                break;
            case R.id.done_button:
                break;
            case R.id.select_image:

                break;
        }
    }


}