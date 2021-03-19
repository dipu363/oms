package com.aait.oms.commission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAccountActivity extends AppCompatActivity {

    TextView balancetext,textfildid;
    Button withdrawbtn;

    List<CommissionModel> commissionModelList;

    float balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_account);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("My Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        balancetext = findViewById(R.id.account_balanceid);
        textfildid = findViewById(R.id.commisiontextid);
        withdrawbtn = findViewById(R.id.account_btn_Withdraw);
        commissionModelList = new ArrayList<>();


        withdrawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(balance>0){
                    Intent intent = new Intent(UsersAccountActivity.this, CommissionWithdrawActivity.class);
                    intent.putExtra("balance",String.valueOf(balance));
                    startActivity(intent);

                }else{
                    Toast.makeText(UsersAccountActivity.this, "You have not available balance for withdraw", Toast.LENGTH_SHORT).show();
                }


            }
        });

        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor =  sqLiteDB.getUserInfo();

        if (cursor != null && cursor.moveToFirst()) {
            String user = cursor.getString(1);
            getusercommission(user);
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
                    List<OrderMasterModel> ordermodel = new ArrayList<>();

                    Gson gson = new Gson();
                    String json = gson.toJson(commissionModelList);
                    Type typeMyType = new TypeToken<ArrayList<CommissionModel>>(){}.getType();
                    ArrayList<CommissionModel> myObject = gson.fromJson(json, typeMyType);
                    for (int i= 0 ;i<myObject.size();i++){
                        balance += myObject.get(i).getComBlance();

                    }

                    balancetext.setText("Balance RM  "+String.valueOf(balance));
                    textfildid.setText("Available");

                   /* if(balance>=500.00){

                        balancetext.setText("Balance RM  "+String.valueOf(balance-500));
                        textfildid.setText("Available");
                    }else {

                        balancetext.setText("RM  0.0");
                        textfildid.setText("You have no Available Commission Balance. please Refer this app to your friends and purchase product useing this app for getting your commission.");
                    }*/
                    Log.d("Response",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

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