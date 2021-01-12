package com.aait.oms.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import com.aait.oms.R;
import com.aait.oms.adapter.ProductAdapter;
import com.aait.oms.adapter.SupplierAdapter;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    List<ProductModel> allproductlist;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PRODUCTS");
        listView = findViewById(R.id.productlistviewid);

        allproductlist = new ArrayList<>();

        getallproduct(this);






    }

    private void getallproduct(Context context) {
        Call<BaseResponse> productlist = ApiClient.getProductService().getallproduct();
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                Log.e("success",response.body().toString());

                BaseResponse baseResponse = response.body();
                allproductlist = baseResponse.getData();
                productAdapter = new ProductAdapter(context,allproductlist);
                listView.setAdapter(productAdapter);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });


    }
}