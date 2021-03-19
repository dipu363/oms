package com.aait.oms.commission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.ui.HomeActivity;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionWithdrawActivity extends AppCompatActivity implements View.OnClickListener {

    EditText transectionamount,transectionpassword;
    TextView massagetextview ,balancetextview;

    Button btn_send,btn_next1,btn_next2;
    float ablebalance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_withdraw);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("Commission Withdraw");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        transectionamount = findViewById(R.id.transection_amount_edittextid);
        transectionpassword = findViewById(R.id.transection_password_edittextid);

        massagetextview = findViewById(R.id.withdrawmassegeid);
        balancetextview = findViewById(R.id.withdrawbalancetextid);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            String bal = bundle.getString("balance");
            ablebalance = Float.parseFloat(bal);
            balancetextview.setText("Available Amount RM " + bal);
        }

        btn_send = findViewById(R.id.withdraw_btn_send);
        btn_next1 = findViewById(R.id.withdraw_btn_next1);
       // btn_next2 = findViewById(R.id.withdraw_btn_next2);
        btn_next1.setOnClickListener(this);
        btn_send.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.withdraw_btn_next1:
                String req_amount= transectionamount.getText().toString();
               float reqbalance = Float.parseFloat(req_amount);
                if(TextUtils.isEmpty(req_amount)){
                    transectionamount.setError("Please Enter Request amount");
                    transectionamount.requestFocus();
                }else if (reqbalance > ablebalance){
                    transectionamount.setError(" You have not Enough amount available");
                    transectionamount.requestFocus();

                }
                else {
                    transectionamount.setVisibility(View.INVISIBLE);
                    btn_next1.setVisibility(View.INVISIBLE);
                    transectionpassword.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.withdraw_btn_send:
                String pass= transectionpassword.getText().toString().trim();
                if (TextUtils.isEmpty(pass)){
                    transectionpassword.setError("Enter Your Password");
                    transectionpassword.requestFocus();

                }else {

                    SQLiteDB sqLiteDB = new SQLiteDB(this);
                    Cursor cursor =  sqLiteDB.getUserInfo();

                    if (cursor != null && cursor.moveToFirst()){
                        String password = cursor.getString(2);
                        if (password.equals(pass)){
                            transectionpassword.setVisibility(View.INVISIBLE);
                            btn_send.setVisibility(View.INVISIBLE);
                            massagetextview.setVisibility(View.VISIBLE);
                            massagetextview.setText("Your Request has been sent Please Wait a moment for processing");
                            massagetextview.setTextColor(Color.DKGRAY);
                            Toast.makeText(CommissionWithdrawActivity.this, "Request Successful", Toast.LENGTH_SHORT).show();
                            saveWithdrawCommission();

                        }else {
                            transectionpassword.setText("");
                            transectionpassword.setError("Enter Your Password");
                            transectionpassword.requestFocus();
                            Toast.makeText(CommissionWithdrawActivity.this, "your password not mach", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                break;

        }
    }





    private void saveWithdrawCommission() {
        String username = "";
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("yyyy-MM-dd");
        String reqdate=formeter.format(date);

        String withtype ="Cash";
        float req_amount= Float.parseFloat(transectionamount.getText().toString().trim()) ;
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor =  sqLiteDB.getUserInfo();
        if (cursor != null && cursor.moveToFirst()) {
             username = cursor.getString(1);
        }




        CommissionWithdrawModel commissionWithdrawModel = new CommissionWithdrawModel(username,reqdate,req_amount,withtype);
        CommissionService service = ApiClient.getRetrofit().create(CommissionService.class);
        Gson gson = new Gson();
        String json = gson.toJson(commissionWithdrawModel);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Call<String> call = service.saveCommissionWithdrawRequest(jsonObject);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){


                    String um = response.body();
                    Log.d("Success","Balance Withdraw Request Success");
                    Log.d("Success",um);
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Failure","Balance Withdraw Request Fail" + t.getMessage());
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}