package com.example.justdraw.Activites;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.DrawView;
import com.example.justdraw.Utilities.MySharedPrefrence;
import com.example.justdraw.widget.Segow_UI_Bold_Font;
import com.google.android.material.slider.RangeSlider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class CanvasActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    private DrawView paint;

    // creating objects of type button
    private ImageView save, undo, redo,back;
    LinearLayout color, stroke,stickers,eraser;
    RelativeLayout sticker_layout;
    Segow_UI_Bold_Font cancel;

    Canvas canvas;
    // creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;
    MySharedPrefrence m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas_activity);
        m = MySharedPrefrence.instanceOf(CanvasActivity.this);
        Window window = getWindow();
        Comman.Statusbar(this, window);
        // getting the reference of the views from their ids
        paint = (DrawView) findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) findViewById(R.id.rangebar);

        undo = findViewById(R.id.undo);
        save = findViewById(R.id.save);
        color = findViewById(R.id.color_picker);
        stroke = findViewById(R.id.brush);
        redo = findViewById(R.id.redo);
        stickers=findViewById(R.id.sticker);
        eraser=findViewById(R.id.eraser);
        back=findViewById(R.id.move_left);
        cancel=findViewById(R.id.cancel);
        sticker_layout=findViewById(R.id.sticker_layout);
        Paint watermarkPaint = new Paint();
        watermarkPaint.setColor(Color.WHITE);
        watermarkPaint.setAlpha(150);
        watermarkPaint.setTextSize(30);
        watermarkPaint.setTextAlign(Paint.Align.LEFT);
        watermarkPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StickerLayoutDismiss();
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.setEraser(true);
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.redo();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // getting the bitmap from DrawView class
                Bitmap bmp = paint.save();
                // opening a OutputStream to write into the file
                OutputStream imageOutStream = null;
                ContentValues cv = new ContentValues();
                // name of the file
                cv.put(MediaStore.Images.Media.DISPLAY_NAME, "drawing.png");
                // type of the file
                cv.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                // location of the file to be saved
                cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                // get the Uri of the file which is to be created in the storage
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
                try {
                    // open the output stream with the above uri
                    imageOutStream = getContentResolver().openOutputStream(uri);
                    // this method writes the files in storage
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream);
                    Intent intent = new Intent(CanvasActivity.this, SavedDrawingActivity.class);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                    intent.putExtra("byteArray", bs.toByteArray());
                    startActivity(intent);
                    Animatoo.animateSwipeLeft(CanvasActivity.this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker colorPicker = new ColorPicker(CanvasActivity.this);
                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        // get the integer value of color
                        // selected from the dialog box and
                        // set it as the stroke color
                        paint.setColor(color);
                    }

                    @Override
                    public void onCancel() {
                        colorPicker.dismissDialog();
                    }
                })
                        // set the number of color columns
                        // you want to show in dialog.
                        .setColumns(5)
                        // set a default color selected
                        // in the dialog
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });
        // the button will toggle the visibility of the RangeBar/RangeSlider
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rangeSlider.getVisibility() == View.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);
            }
        });

        stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StickerLayoutView();

            }
        });
        // set the range of the RangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);


        // adding a OnChangeListener which will
        // change the stroke width
        // as soon as the user slides the slider
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int) value);
            }
        });



        // pass the height and width of the custom view
        // to the init method of the DrawView object
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });

    }
    public void StickerLayoutView() {
        sticker_layout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter);
        animation.setDuration(1000);
        sticker_layout.setAnimation(animation);
        sticker_layout.animate();
        animation.start();

    }

    public void StickerLayoutDismiss() {
        sticker_layout.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down_exit);
        animation.setDuration(1000);
        sticker_layout.setAnimation(animation);
        sticker_layout.animate();
        animation.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(this);
    }
}
