package com.aait.oms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.MainActivity;
import com.aait.oms.R;
import com.aait.oms.orders.OrderActivity;
import com.aait.oms.users.UserRequest;
import com.aait.oms.util.SQLiteDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivity extends AppCompatActivity {
    private Button mVerifyCodeBtn;
    private EditText otpEdit;
    private String OTP;
    TextView otpverifytext, resendotp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    //remove the title ber
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //remove the notification ber
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_otp);

        mVerifyCodeBtn = findViewById(R.id.verifycode_btn);
        otpEdit = findViewById(R.id.verify_code_edit);
        otpverifytext= findViewById(R.id.verify_otp_textid);
        resendotp = findViewById(R.id.resend_otp_id);



        firebaseAuth = FirebaseAuth.getInstance();

        OTP = getIntent().getStringExtra("auth");
        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verification_code = otpEdit.getText().toString();
                if(!verification_code.isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP , verification_code);
                    signIn(credential);
                }else{
                    otpverifytext.setText("Please Enter Verification Code That is Send to your phone number");
                    otpverifytext.setTextColor(Color.RED);
                    otpverifytext.setVisibility(View.VISIBLE);
                    Toast.makeText(OtpActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpActivity.this,SendOtpActivity.class));
                finish();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
     /*   FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser !=null){
            sendToMain();
        }*/
    }

    private void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendToMain();
                }else{
                    Toast.makeText(OtpActivity.this, "Verification Failed,please Resend ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void sendToMain(){
        SQLiteDB sqLiteDBHelper = new SQLiteDB(getApplicationContext());
        UserRequest request = new UserRequest(OTP);
        sqLiteDBHelper.insertUserinfo(request);

        Toast.makeText(OtpActivity.this,"Congratulation ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(OtpActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}