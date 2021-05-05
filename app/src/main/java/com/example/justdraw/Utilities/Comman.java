package com.example.justdraw.Utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.justdraw.R;
import com.google.android.material.snackbar.Snackbar;
import com.sun.easysnackbar.EasySnackBar;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class Comman {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            @SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public  static boolean Check_Login(Context context)
    {
        com.example.justdraw.Utilities.MySharedPrefrence mySharedPrefrence= com.example.justdraw.Utilities.MySharedPrefrence.instanceOf(context);
        if(mySharedPrefrence.isLoggedIn())
        {
            return true;
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    public static void topSnakBar(Context context, View mView, String msg)
    {
        // Must create custom view in this way,so it can display normally
        if(mView!=null){
            View contentView = EasySnackBar.convertToContentView(mView, R.layout.top_snakbar);
            TextView textView=contentView.findViewById(R.id.mgs);
            if(msg!=null)
                textView.setText(msg);
            // true represent show at top,false at bottom
            EasySnackBar.make(mView, contentView, EasySnackBar.LENGTH_INDEFINITE, true).setDuration(3000).show();}
    }


    public static void BottomSnackBar(Context context,View mView,String mgs){
        if (mView!=null){
            Snackbar snackbar = Snackbar.make(mView, mgs, Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.RED);
            snackbar.show();
        }
    }

    public  static  void  Statusbar(Context context, Window window){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.black));}
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,"IMG_" + Calendar.getInstance().getTime(),null);
        return Uri.parse(path);
    }
    public static void log(String TAG,String TEXT)
    {
        if(TEXT!=null);
        Log.d(TAG,TEXT);
    }
}
