package com.aait.oms.commission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
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

public class TransactionHistoryActivity extends AppCompatActivity {

    List<CommissionWithdrawModel> commissionWithdrawModelList;

    TransactionHistoryAdapter transactionHistoryAdapter;

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Transaction History");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.transactionhislist_id);
        commissionWithdrawModelList = new ArrayList<>();

        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname ="";

        if(cursor.moveToFirst()){
            uname = cursor.getString(1);
        }

        getuserWithdrawcommission(this,uname);


    }



    public void getuserWithdrawcommission(Context context, String uname){
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


                    transactionHistoryAdapter = new TransactionHistoryAdapter(context,myObject);
                    listView.setAdapter(transactionHistoryAdapter);
                    // Log.d("Response",response.body().toString());


                } else{
                    Toast.makeText(TransactionHistoryActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Toast.makeText(TransactionHistoryActivity.this, "Server Not found,root cause"+ t.getMessage(), Toast.LENGTH_SHORT).show();

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