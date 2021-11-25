package com.aait.oms.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.ui.HomeActivity;
import com.aait.oms.ui.SignUpActivity;
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

public class LogInFragment extends Fragment implements View.OnClickListener {

    // private FirebaseAuth mAuth;
    private final String username = null;
    private EditText useremail, userpasswordid;
    Button signup;
    private FloatingActionButton login;
    private ProgressBar loginProgress;
    private AppUtils appUtils;
    ApplicationData applicationData;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUtils = new AppUtils(getContext());
        applicationData = new ApplicationData(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        login = view.findViewById(R.id.btn_login_frag);
        signup = view.findViewById(R.id.btn_signup_login_frag);
        loginProgress = view.findViewById(R.id.login_frag_progress);
        useremail = view.findViewById(R.id.login_frag_emailedittextid);
        userpasswordid = view.findViewById(R.id.login_frag_edtPassword);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        loginProgress.setVisibility(View.INVISIBLE);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login_frag:
                netWorkCheck();
                break;
            case R.id.btn_signup_login_frag:
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                        Type typeMyType = new TypeToken<UserRequest>() {
                        }.getType();
                        UserRequest user = gson.fromJson(json, typeMyType);
                        String userPassword = user.getMobiPassword();
                        String userRole = user.getRoleId();
                        if (userPassword != null && userPassword.equals(pass) && userRole.equals("102.0")) {
                            //save username to sqlite db  for getting session;
                            SQLiteDB sqLiteDB = new SQLiteDB(getContext());
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

                            appUtils.appToast("Congratulation");
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            clear();
                            getActivity().finish();
                            loginProgress.setVisibility(View.INVISIBLE);

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