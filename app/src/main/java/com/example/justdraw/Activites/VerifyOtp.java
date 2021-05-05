package com.example.justdraw.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.justdraw.R;
import com.example.justdraw.Utilities.Comman;
import com.example.justdraw.Utilities.Constant;
import com.example.justdraw.Utilities.MySharedPrefrence;
import com.example.justdraw.widget.Segow_UI_Font;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {

    String mVerificationId;
    EditText OTP_code;
    ProgressBar progressBar;
    Button verify;
    FirebaseAuth mAuth;
    MySharedPrefrence m;
    View view;
    Segow_UI_Font user_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        m = MySharedPrefrence.instanceOf(VerifyOtp.this);
        mAuth = FirebaseAuth.getInstance();
        view = getWindow().getDecorView().getRootView();
        OTP_code = findViewById(R.id.otp_edit);
        progressBar = findViewById(R.id.progress_bar);
        verify = findViewById(R.id.verify_butt);
        user_number=findViewById(R.id.user_mobile_number);
        user_number.setText(m.getUserPhone());

        if (!OTP_code.getText().toString().isEmpty()){
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.VISIBLE);
        }
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = OTP_code.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                 Comman.BottomSnackBar(VerifyOtp.this,v, Constant.ValidInput);
                    OTP_code.requestFocus();
                    return;
                }
                //verifying the code entered manually
                verifyCode (code);
            }
        });
    }



    private void sendVerificationCode(String mobile) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                OTP_code.setText(code);
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };

    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, codeByUser);
        SignInByUserCredential(credential);

    }

    private void SignInByUserCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerifyOtp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(),MaindashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Comman.BottomSnackBar(VerifyOtp.this, view, task.getException().getMessage());
                }
            }
        });
    }
}