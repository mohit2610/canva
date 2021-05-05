package com.example.justdraw.Activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.Constant;
import com.example.justdraw.Utilities.MySharedPrefrence;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.grpc.Compressor;

public class SavedDrawingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView drawing, move_back;
    View v;
    Button post;
    Bitmap b;
    EditText drawing_name;
    AlertDialog alertDialog;

    private Uri imageUri;
    private final int PICK_IMAGE_REQUEST = 22;

    private Bitmap compreser;
    private ProgressDialog progressDialog;

    String UserId;


    StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_drawing);
        storageReference = FirebaseStorage.getInstance().getReference();
        drawing = findViewById(R.id.drawing_img);
        v = getWindow().getDecorView().getRootView();
        Comman.Statusbar(SavedDrawingActivity.this, getWindow());
        move_back = findViewById(R.id.move_back);
        post = findViewById(R.id.post_drawing);
        // get the Firebase  storage reference
        progressDialog = new ProgressDialog(this);
        move_back.setOnClickListener(this);
        post.setOnClickListener(this);
        drawing_name = findViewById(R.id.drawing_name);
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();
        Map<String,String> userDrawing =new HashMap<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
//        storageReference= storage.getInstance().getReference();
//
        Comman.BottomSnackBar(this,v,Constant.SavedImage);
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            drawing.setImageBitmap(b);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_back:
                onBackPressed();
                break;
            case R.id.post_drawing:
//                FireBase work
                uploadImage();
                break;

        }

    }

    private void uploadImage() {
        final String drawingName = drawing_name.getText().toString();

        if (drawingName.isEmpty()) {
            Comman.BottomSnackBar(this,v,Constant.FillDetails);
        } else {
            progressDialog.setMessage("Uploading..");
            progressDialog.show();

           // File newFile = new File(imageUri.getPath());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] data = byteArrayOutputStream.toByteArray();
            UploadTask image_path = storageReference.child("drawing_img").child(UserId + ".jpg").putBytes(data);
            image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        StoreDrawingImg(task,drawingName );
                    } else {
                        Toast.makeText(SavedDrawingActivity.this, "error", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }

    }

    private void StoreDrawingImg(Task<UploadTask.TaskSnapshot> task, String drawingName) {
        Uri downloded_uri;
        if (task!=null){
            downloded_uri= task.getResult().getUploadSessionUri();
        }else {
            downloded_uri= imageUri;
        }
        Map<String,String> userDrawing =new HashMap<>();
        userDrawing.put("drawingName",drawingName);
        userDrawing.put("drawingImg",downloded_uri.toString());

        firebaseFirestore.collection("user").document(UserId).set(userDrawing).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();

                    firebaseFirestore.collection("user").document().set(userDrawing).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (task.isSuccessful()){
                               Comman.topSnakBar(SavedDrawingActivity.this,v,Constant.Uploaded);
                            }else {
                                Toast.makeText(SavedDrawingActivity.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(SavedDrawingActivity.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            imageUri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                drawing.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(this);
    }
}