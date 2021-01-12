package com.aait.oms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.aait.oms.R;
import com.aait.oms.adapter.SupplierAdapter;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.model.SupplierModel;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierListActivity extends AppCompatActivity {

    List<SupplierModel> allsupplierlist;
    SupplierAdapter supplieradapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);
        recyclerView = findViewById(R.id.suprecyclerviewid);
        allsupplierlist = new ArrayList<SupplierModel>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        getAllSupplier(this);
    }


    public void getAllSupplier(Context context){
        Call<BaseResponse> supplierlist = ApiClient.getSupplierservice().getallsupplier();

        supplierlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e("success",response.body().toString());

                BaseResponse baseResponse = response.body();
                allsupplierlist = baseResponse.getData();
               supplieradapter = new SupplierAdapter(context,allsupplierlist);
                recyclerView.setAdapter(supplieradapter);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });

    }
}