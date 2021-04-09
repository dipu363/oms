package com.aait.oms.commission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.MyOrdersAdapter;
import com.aait.oms.orders.OrderMasterModel;
import com.aait.oms.orders.OrderService;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView balancetext,textfildid;
    CardView card1,card2;

    List<CommissionModel> commissionModelList;
    List<CommissionWithdrawModel> commissionWithdrawModelList;

    float netBalance = 0.0f;
    float commissionBalance = 0.0f;
    float withdrawBalance = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_account);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("  My Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        balancetext = findViewById(R.id.account_balanceid);
        textfildid = findViewById(R.id.commisiontextid);
        card1 = findViewById(R.id.cardview_account_Optino1);
        card2 = findViewById(R.id.cardview_account_Optino2);
        commissionModelList = new ArrayList<>();
        commissionWithdrawModelList = new ArrayList<>();

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor =  sqLiteDB.getUserInfo();
        String user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = cursor.getString(1);
            getusercommission(user);
            getuserWithdrawcommission(user);
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.cardview_account_Optino1:
                Intent intent1 = new Intent(UsersAccountActivity.this, TransactionHistoryActivity.class);
                startActivity(intent1);


                break;
            case R.id.cardview_account_Optino2:

                if(netBalance>0){
                    Intent intent2 = new Intent(UsersAccountActivity.this, CommissionWithdrawActivity.class);
                    intent2.putExtra("balance",String.valueOf(netBalance));
                    startActivity(intent2);

                }else{
                    Toast.makeText(UsersAccountActivity.this, "You have not available balance for withdraw", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }


    public void getusercommission(String uname){
        CommissionService commissionService = ApiClient.getRetrofit().create(CommissionService.class);
        Call<BaseResponse> call = commissionService.getuserCommission(uname);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){

                    BaseResponse baseResponse = response.body();
                    commissionModelList = baseResponse.getItems();
                    Gson gson = new Gson();
                    String json = gson.toJson(commissionModelList);
                    Type typeMyType = new TypeToken<ArrayList<CommissionModel>>(){}.getType();
                    ArrayList<CommissionModel> myObject = gson.fromJson(json, typeMyType);
                    ArrayList<CommissionModel> myObject1 = gson.fromJson(json, typeMyType);
                    for (int i= 0 ;i<myObject.size();i++){

                        commissionBalance += myObject.get(i).getComBlance();

                    }
                    netBalance = commissionBalance;
                    netBalance = commissionBalance;

                  /*  if(netBalance>0){
                        balancetext.setText("Balance RM  "+String.valueOf(doubleResult));
                        textfildid.setText("Available");


                    }else {
                        balancetext.setText("RM  0.0");
                        textfildid.setText("You have no Available Commission Balance. please Refer this app to your friends and purchase product useing this app for getting your commission.");



                    }*/


                    //Log.d("Response",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }


    public void getuserWithdrawcommission(String uname){
        CommissionService commissionService = ApiClient.getRetrofit().create(CommissionService.class);
        Call<BaseResponse> call = commissionService.getuserWithdrawCommission(uname);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){

                    BaseResponse baseResponse = response.body();
                    commissionWithdrawModelList = baseResponse.getItems();

                    Gson gson = new Gson();
                    String json = gson.toJson(commissionWithdrawModelList);
                    Type typeMyType = new TypeToken<ArrayList<CommissionWithdrawModel>>(){}.getType();
                    ArrayList<CommissionWithdrawModel> myObject = gson.fromJson(json, typeMyType);
                    for (int i= 0 ;i<myObject.size();i++){
                        withdrawBalance += myObject.get(i).getTransAmount();

                    }

                    netBalance = commissionBalance - withdrawBalance;

                    double doubleResult = Float.valueOf(netBalance);
                    DecimalFormat d = new DecimalFormat("#.00");
                    d.setRoundingMode(RoundingMode.CEILING);
                    String v = d.format(doubleResult);



                    if(netBalance>0){
                        balancetext.setText("Balance RM  "+ v);
                        textfildid.setText("Available");


                    }else {
                        balancetext.setText("RM  0.0");
                        textfildid.setText("You have no Available Commission Balance. please Refer this app to your friends and purchase product useing this app for getting your commission.");



                    }

                   // Log.d("Response",response.body().toString());
                }
                else{
                    Toast.makeText(UsersAccountActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Toast.makeText(UsersAccountActivity.this, "Server Not found,root cause"+ t.getMessage(), Toast.LENGTH_SHORT).show();

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