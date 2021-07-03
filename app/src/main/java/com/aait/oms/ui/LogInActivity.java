package com.aait.oms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.users.UserRequest;
import com.aait.oms.users.UserService;
import com.aait.oms.util.SQLiteDB;
import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText useremail,userpasswordid;
    private Button login, signup;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private String username = null;



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
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();

        String otpcode =null;
        String currentuser =null;
        int loginstatus = 0;

     // FirebaseUser  user = mAuth.getCurrentUser();
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        //sqLiteDB.updateuserotp(currentuser,1);
        Cursor cursor =  sqLiteDB.getUserInfo();
        if(cursor.moveToFirst()){
            username = cursor.getString(1);
             otpcode = cursor.getString(2);
             loginstatus = cursor.getInt(4);
        }

        if (loginstatus == 1){
            Intent intent =new Intent(LogInActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }



     /*   if(user != null) {
            //user is already login  so we need to redirect him to home pag
          *//* String usern= user.getPhoneNumber();
            String uid = user.getUid();
            mAuth.signOut();*//*


            if (loginstatus == 1){

                Intent intent =new Intent(LogInActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);

            }

        }else{
            Intent intent =new Intent(LogInActivity.this,SendOtpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);

        }*/


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
            builder.setTitle(" No Network")
                    .setMessage("Enable Mobile Network")
                    .setIcon(R.drawable.logopng40)
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

    public void signIn(String uname ,String pass){

        UserService userService = ApiClient.getRetrofit().create(UserService.class);
        Call <BaseResponse> usercall = userService.getuser(uname);
        usercall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    String massage = baseResponse.getMessage();
                    if(baseResponse.getObj() == null){
                        Toast.makeText(LogInActivity.this, "User Name not mach", Toast.LENGTH_LONG).show();
                    }else{
                        loginProgress.setVisibility(View.VISIBLE);
                        login.setVisibility(View.INVISIBLE);

                        Gson gson = new Gson();
                        String json = gson.toJson(baseResponse.getObj());
                   /* JsonObject jsonObject = null;
                    jsonObject = new JsonParser().parse(json).getAsJsonObject();*/
                        Type typeMyType = new TypeToken<UserRequest>(){}.getType();
                        UserRequest user = gson.fromJson(json, typeMyType);
                        String  userPassword = user.getMobiPassword();
                        String userRole = user.getRoleId();
                        if (userPassword != null && userPassword.equals(pass) && userRole.equals("102.0")) {
                            //save username to sqlite db  for getting session;

                            SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
                            Cursor cursor =  sqLiteDB.getUserInfo();

                            if (cursor != null && cursor.moveToFirst()){
                                sqLiteDB.updateuserunamepassstatus(uname,pass,true,1);
                            }else{

                                UserRequest userRequest = new UserRequest();
                                userRequest.setUserName(uname);
                                userRequest.setMobiPassword(pass);
                                userRequest.setLogin_status(true);
                                sqLiteDB.insertUserinfo(userRequest);

                            }
                            Toast.makeText(LogInActivity.this,"Congratulation",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LogInActivity.this,HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            clear();

                        } else {

                            Toast.makeText(LogInActivity.this, "Password or Role not mach", Toast.LENGTH_SHORT).show();
                            login.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.INVISIBLE);

                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
   /*     loginProgress.setVisibility(View.VISIBLE);
        login.setVisibility(View.INVISIBLE);

        if (username.equals("admin")&& pass.equals("admin") ){

            Toast.makeText(LogInActivity.this,"Congratulation ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LogInActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            clear();

        }else{
            login.setVisibility(View.VISIBLE);
            loginProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(LogInActivity.this,"Failed",Toast.LENGTH_LONG).show();
        }*/
    }

    private void clear() {
        useremail.setText("");
        userpasswordid.setText("");
    }

}