package com.aait.oms.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.users.UserRequest;
import com.aait.oms.users.UserService;
import com.aait.oms.users.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private EditText fname, lname,uname,upassword,reference;
    private Button btncountinu;
    private TextView doclogintaxt;
    private ProgressBar loadingProgress;
    List<UserRequest> userslist;
    Spinner spinner;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String existingUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("REGISTRATION");
        loadingProgress = findViewById(R.id.regProgressBar);
        fname= findViewById(R.id.edit_firstname);
        lname = findViewById(R.id.edit_lastname);
        uname = findViewById(R.id.edit_username);
        reference=findViewById(R.id.reference_id);
        upassword = findViewById(R.id.edt_password);
        btncountinu = findViewById(R.id.btnSignUpContinue);
        doclogintaxt = findViewById(R.id.docLogin);
        btncountinu.setOnClickListener(this);
        doclogintaxt.setOnClickListener(this);
        loadingProgress.setVisibility(View.INVISIBLE);



        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersInfo");
        //for spinner
        spinner = (Spinner) findViewById(R.id.spinner_gender);
        String[] gen = {"Select Gender","Male","Female","Others"};
        ArrayAdapter<CharSequence> genAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, gen );
        genAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(genAdapter);

        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String un = uname.getText().toString().trim();
                if(!hasFocus && !un.equals("")){
                    chackusername(un);
                }else{

                    uname.setError("Enter Your Mobile Number");
                    uname.requestFocus();
                   // Toast.makeText(SignUpActivity.this, "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reference.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String ref = reference.getText().toString().trim();
               if(!hasFocus && !ref.equals("")){
                   chackReference(ref);
               }else{
                   reference.setError("Enter Reference Customer ID");
                   reference.requestFocus();
                  // Toast.makeText(SignUpActivity.this, "Enter Reference Customer ID", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
  /*      currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            sendToMain();

        }*/


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSignUpContinue:
                netWorkCheck();
                break;
            case R.id.docLogin:
                Intent intent =new Intent(SignUpActivity.this,LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;



        }

    }

    private void sendToMain(){
        startActivity(new Intent(SignUpActivity.this , HomeActivity.class));
        finish();
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
        final  String firstname = fname.getText().toString().trim();
        final String lastname = lname.getText().toString().trim();
        final String username = uname.getText().toString().trim();
        final String gender= spinner.getSelectedItem().toString();
        final String ref = reference.getText().toString().trim();
        final String password = upassword.getText().toString().trim();
        int passlenth = password.length();
        //getuserList(username);
        //checking the validity of the email
        if (TextUtils.isEmpty(firstname)) {
            fname.setError("Enter your first name");
            fname.requestFocus();
        } else if (TextUtils.isEmpty(lastname)) {
            lname.setError("Enter your last name");
            lname.requestFocus();

        }
        //checking the validity of the password
        else if (TextUtils.isEmpty(password) ) {
            upassword.setError("Enter your password");
            upassword.requestFocus();
        }
        else if (passlenth < 4 ) {
            upassword.setError("Password Entered At Least 4 Characters ");
            upassword.requestFocus();
        }
        else {


            btncountinu.setVisibility(View.INVISIBLE);
            loadingProgress.setVisibility(View.VISIBLE);
            CreateUserAccount(firstname,lastname,username,gender,password,ref);
            UsersModel userModel = new UsersModel(username,firstname,lastname,102,password,gender,ref);
            databaseReference.child(username).setValue(userModel);
            Intent intent = new Intent(SignUpActivity.this, SendOtpActivity.class);
            startActivity(intent);
            Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            clear();
            btncountinu.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);


        }


    }


    //create user accountmathod

    private void CreateUserAccount(final String fname,final String lname,final String uname, final String gender,final String password, final String ref) {

      UsersModel userModel = new UsersModel(uname,fname,lname,102,password,gender,ref);
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Call<String> submitOrderModelCall = service.saveUser(jsonObject);
        submitOrderModelCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String um = response.body();
                    Log.d("Success","Registration Success");
                    Log.d("Success",um);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Failure","Registration Fail" + t.getMessage());

            }
        });

    }

    //clear text field
    public void clear(){
        fname.setText("");
        lname.setText("");
        uname.setText("");
        upassword.setText("");
        reference.setText("");
    }

    private  void chackusername(String userid){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = service.getuser(userid);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    String massage = baseResponse.getMessage();
                    if(massage.equals("find data Successfully")){

                        uname.setText("");
                        uname.setError("Number has been all ready used Please Choose Another");
                        uname.requestFocus();
                        //Toast.makeText(SignUpActivity.this, "User Name All ready Used Please Choose Another", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SignUpActivity.this, "User Name has been available", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Failure massage",t.getMessage());

            }
        });
    }
    private  void chackReference(String userid){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = service.getuser(userid);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    String massage = baseResponse.getMessage();
                    if(massage.equals("find data Successfully")){
                        Toast.makeText(SignUpActivity.this, "Thank you for your reference", Toast.LENGTH_LONG).show();
                    }else{
                        reference.setText("");
                        reference.setError("Customer ID Not Available Please Enter a Valid Customer ID.");
                        reference.requestFocus();

                        //Toast.makeText(SignUpActivity.this, "Customer ID not available", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Failure massage",t.getMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}