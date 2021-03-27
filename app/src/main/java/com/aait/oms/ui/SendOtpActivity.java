package com.aait.oms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.commission.CommissionModel;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.users.UserModel;
import com.aait.oms.users.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOtpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSendOTPBtn ,otpsignupbtn;
    Spinner spinner;
    private TextView processText;
    private EditText countryCodeEdit , phoneNumberEdit;
    List<UserModel> userModelList;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        mSendOTPBtn = findViewById(R.id.send_codebtn);
        processText = findViewById(R.id.text_process);
        countryCodeEdit = findViewById(R.id.input_country_code);
        phoneNumberEdit = findViewById(R.id.input_phone);
        otpsignupbtn = findViewById(R.id.otp_signup_btn);
        userModelList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        mSendOTPBtn.setOnClickListener(this);
        otpsignupbtn.setOnClickListener(this);




    }


    @Override
    protected void onStart() {
        super.onStart();
       /* FirebaseUser user = auth.getCurrentUser();
        if (user !=null){
            sendToMain();
        }*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_codebtn:
                String phone =phoneNumberEdit.getText().toString().trim();
                String country_code =countryCodeEdit.getText().toString().trim();

                if (!country_code.isEmpty() || !phone.isEmpty()){
                    chackusername(phone);
                }else{
                    processText.setText("Please Enter Country Code and Phone Number");
                    processText.setTextColor(Color.RED);
                    processText.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.otp_signup_btn:
                startActivity(new Intent(SendOtpActivity.this,SignUpActivity.class));
                finish();


        }
    }
    private void sendToMain(){
        Intent mainIntent = new Intent(SendOtpActivity.this , HomeActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private  void chackusername(String userid){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = service.getuser(userid);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    Object obj = baseResponse.getObj();
                   // boolean size = obj.equals(null);

                    if(baseResponse.getObj()!= null){
                        Toast.makeText(SendOtpActivity.this, " Customer found", Toast.LENGTH_LONG).show();
                        String country_code = countryCodeEdit.getText().toString();
                        String phone = phoneNumberEdit.getText().toString();
                        String phoneNumber = "+" + country_code + "" + phone;
                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber(phoneNumber)
                                .setTimeout(60L , TimeUnit.SECONDS)
                                .setActivity(SendOtpActivity.this)
                                .setCallbacks(
                                        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                signIn(phoneAuthCredential);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                processText.setText(e.getMessage());
                                                processText.setTextColor(Color.RED);
                                                processText.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                super.onCodeSent(s, forceResendingToken);
                                                //sometime the code is not detected automatically
                                                //so user has to manually enter the code
                                                processText.setText("OTP has been send please verify your mobile device");
                                                processText.setVisibility(View.VISIBLE);
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent otpIntent = new Intent(SendOtpActivity.this , OtpActivity.class);
                                                        otpIntent.putExtra("auth" , s);
                                                        startActivity(otpIntent);
                                                    }
                                                }, 10000);

                                            }
                                        })
                                .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    }else{
                        //Toast.makeText(SignInActivity.this, "Customer ID not available", Toast.LENGTH_LONG).show();
                        countryCodeEdit.setText("");
                        phoneNumberEdit.setText("");
                        phoneNumberEdit.setError("You are not registered our system. Please click sign up button for your registration.");


                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Failure massage",t.getMessage());

            }
        });
    }

    private void signIn(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendToMain();
                }else{
                    processText.setText(task.getException().getMessage());
                    processText.setTextColor(Color.RED);
                    processText.setVisibility(View.VISIBLE);
                }
            }
        });
    }





}