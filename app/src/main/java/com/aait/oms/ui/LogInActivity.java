package com.aait.oms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aait.oms.R;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText useremail,userpasswordid;
    private Button login, signup;
    private ProgressBar loginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove the title ber
        //(Window.FEATURE_NO_TITLE);
        //remove the notification ber
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
      /*  ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setLogo(R.drawable.ic_home_icon);
        actionBar.setTitle("K And T Trading");*/



        login= findViewById(R.id.btn_login);
        signup= findViewById(R.id.btn_signup);
        loginProgress= findViewById(R.id.login_progress);
        useremail= findViewById(R.id.emailedittextid);
        userpasswordid = findViewById(R.id.edtPassword);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        loginProgress.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onStart() {
        super.onStart();

//        user = auth.getCurrentUser();
//        if(user != null) {
//
//            if(user.isEmailVerified()){
//                updateUI();
//
//            }else{
//                Toast.makeText(LoginActivity.this, "Your Email is not verified Please verify your email", Toast.LENGTH_SHORT).show();
//            }
//
//            //user is already login  so we need to redirect him to home pag
//        }else{
//
//            Toast.makeText(this, "there have no current user", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:

                netWorkCheck();
                break;

            case R.id.btn_signup:
                Intent intent =new Intent(LogInActivity.this,SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;


        }

    }


    // check mobile net work status and then call checkvalidity method ;
    public void netWorkCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){

            checkValidity();
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(" NO NetWork")
                    .setMessage("Enable Mobile Network")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

    }

    private void checkValidity(){
        String uemail = useremail.getText().toString();
        String upass = userpasswordid.getText().toString();

        int pass = upass.length();


        if(TextUtils.isEmpty(uemail)){
            useremail.setError("Please Type valid Email Address");
            useremail.requestFocus();
        }
        else if(TextUtils.isEmpty(upass)){
            userpasswordid.setError("Pleases Type Password");
            userpasswordid.requestFocus();
        }


        else{
            signIn(uemail,upass);
        }


    }

    public void signIn(String username ,String pass){
        loginProgress.setVisibility(View.VISIBLE);
        login.setVisibility(View.INVISIBLE);
        if (username.equals("admin")&& pass.equals("admin") ){
            Toast.makeText(LogInActivity.this,"Congratulation ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LogInActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            clear();

        }else{

//            login.setVisibility(View.VISIBLE);
//            loginProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(LogInActivity.this,"Failed",Toast.LENGTH_LONG).show();
        }
    }

    private void clear() {
        useremail.setText("");
        userpasswordid.setText("");
    }
}