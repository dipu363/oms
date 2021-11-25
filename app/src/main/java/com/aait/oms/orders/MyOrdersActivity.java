package com.aait.oms.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;

import com.aait.oms.util.SQLiteDB;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {

    ListView myorderlistview;
    MyOrdersAdapter myOrdersAdapter;
    List<OrderMasterModel> myOrderList;
    TextView textView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("  My Orders");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        myorderlistview = findViewById(R.id.myorderlistviewid);
        textView = findViewById(R.id.ordernotfound_id);
        myOrderList = new ArrayList<>();
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        progressDialog = new ProgressDialog(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname = "";

        if (cursor.moveToFirst()) {
            uname = cursor.getString(1);

        }
        getmyorderlist(this, uname);

        myorderlistview.setOnItemClickListener((parent, view, position, id) -> {
            OrderMasterModel omm = myOrdersAdapter.myorderlist.get(position);
            String orderid = String.valueOf(omm.getOrderId());
            Intent intent = new Intent(MyOrdersActivity.this, OrderDetailsActivity.class);
            intent.putExtra("orderid", orderid);
            startActivity(intent);
        });
    }

    public void getmyorderlist(Context context, String uname) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        Call<BaseResponse> call = service.getUserOrders(uname);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    assert baseResponse != null;
                    myOrderList = baseResponse.getItems();
                    if (myOrderList.size() == 0) {
                        textView.setVisibility(View.VISIBLE);
                        Snackbar.make(textView, "Data Not Found", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } else {
                        Gson gson = new Gson();
                        String json = gson.toJson(myOrderList);
                        Type typeMyType = new TypeToken<ArrayList<OrderMasterModel>>() {
                        }.getType();
                        ArrayList<OrderMasterModel> myObject = gson.fromJson(json, typeMyType);
                        myOrdersAdapter = new MyOrdersAdapter(context, myObject);
                        myorderlistview.setAdapter(myOrdersAdapter);
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {

                Log.d("Failure", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}