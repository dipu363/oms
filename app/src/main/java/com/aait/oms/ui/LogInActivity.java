package com.aait.oms.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.CartActivity;
import com.aait.oms.users.UserRequest;
import com.aait.oms.users.UserService;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.ApplicationData;
import com.aait.oms.util.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText useremail, userpasswordid;
    private Button signup;
    private FloatingActionButton login;
    private ProgressBar loginProgress;
    // private FirebaseAuth mAuth;
    private String username = null;
    private AppUtils appUtils;
    private ApplicationData applicationData;




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

        appUtils = new AppUtils(this);
        applicationData= new ApplicationData(this);


        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);
        loginProgress = findViewById(R.id.login_progress);
        useremail = findViewById(R.id.emailedittextid);
        userpasswordid = findViewById(R.id.edtPassword);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        loginProgress.setVisibility(View.INVISIBLE);
       // mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();

        int loginstatus = 0;

        // FirebaseUser  user = mAuth.getCurrentUser();
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        //sqLiteDB.updateuserotp(currentuser,1);
        Cursor cursor = sqLiteDB.getUserInfo();
        if (cursor.moveToFirst()) {
            username = cursor.getString(1);
            loginstatus = cursor.getInt(4);
        }



        if (loginstatus == 1) {
            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                netWorkCheck();
                break;
            case R.id.btn_signup:
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }


    // check mobile net work status and then call checkvalidity method ;
    public void netWorkCheck() {
        if (appUtils.deviceNetwork() != null && appUtils.deviceNetwork().isConnectedOrConnecting()) {
            checkValidity();
        } else {
            appUtils.networkAlertDialog();
        }
    }



    private void checkValidity() {
        String uemail = useremail.getText().toString();
        String upass = userpasswordid.getText().toString();

        if (TextUtils.isEmpty(uemail)) {
            useremail.setError("Please Type valid Email Address");
            useremail.requestFocus();
        } else if (TextUtils.isEmpty(upass)) {
            userpasswordid.setError("Pleases Type Password");
            userpasswordid.requestFocus();
        } else {
            signIn(uemail, upass);
        }
    }



    public void signIn(String uname, String pass) {

        UserService userService = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> usercall = userService.getuser(uname);

        usercall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                   // String massage = baseResponse.getMessage();
                    if (baseResponse.getObj() == null) {
                        appUtils.appToast("User not match");
                    } else {
                        loginProgress.setVisibility(View.VISIBLE);
                        login.setVisibility(View.INVISIBLE);

                        Gson gson = new Gson();
                        String json = gson.toJson(baseResponse.getObj());
                   /* JsonObject jsonObject = null;
                    jsonObject = new JsonParser().parse(json).getAsJsonObject();*/
                        Type typeMyType = new TypeToken<UserRequest>() {
                        }.getType();
                        UserRequest user = gson.fromJson(json, typeMyType);
                        String userPassword = user.getMobiPassword();
                        String userRole = user.getRoleId();
                        if (userPassword != null && userPassword.equals(pass) && userRole.equals("102.0")) {
                            //save username to sqlite db  for getting session;

                            SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
                            Cursor cursor = sqLiteDB.getUserInfo();

                            if (cursor != null && cursor.moveToFirst()) {
                                sqLiteDB.updateuserunamepassstatus(uname, pass, true, 1);
                            } else {

                                UserRequest userRequest = new UserRequest();
                                userRequest.setUserName(uname);
                                userRequest.setMobiPassword(pass);
                                userRequest.setLogin_status(true);
                                sqLiteDB.insertUserinfo(userRequest);

                            }

                            String page = applicationData.getPageState("prodgridview");
                            if (page != null && page.equals("p1")){
                                appUtils.appToast("Congratulation");
                                Intent intent = new Intent(LogInActivity.this, CartActivity.class);
                                intent.putExtra("SQ","SQ");
                                startActivity(intent);
                                clear();
                            }else {
                                appUtils.appToast("Congratulation");
                                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                clear();
                            }


                        } else {
                            appUtils.appToast("Password or Role not mach");
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
    }



    private void clear() {
        useremail.setText("");
        userpasswordid.setText("");
    }

}