package com.aait.oms.product;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.OrderProductAdapter;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.CommonFunctions;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView1;
    ListView listView;
    //RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List productlist ;

    AppUtils appUtils;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("  Products");
        appUtils = new AppUtils(this);
        productlist = new ArrayList();
        listView = findViewById(R.id.productlist_id);
        netWorkCheck(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();


    }

    private void getallproduct(Context context) {
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> call = apiService.getallproduct();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if (baseResponse != null&& baseResponse.getData().size()!=0) {
                    productlist = baseResponse.getData();
                    Gson gson = new Gson();
                    String json = gson.toJson(productlist);
                    Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                    }.getType();
                    ArrayList<StockViewModel> product = gson.fromJson(json, typeMyType);
                    productAdapter = new ProductAdapter(context, product);
                    listView.setAdapter(productAdapter);
                } else {
                    appUtils.appToast("Data Note found");

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("Main", " data search" + newText);

                productAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;

    }

    public void showMaseage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context) {

        if (appUtils.deviceNetwork() != null && appUtils.deviceNetwork().isConnectedOrConnecting()) {
            getallproduct(context);

        } else {
            appUtils.networkAlertDialog();

        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.searchView) {

            return true;
        } else if (id == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String text = newText;
        productAdapter.filter(text);
        return false;
    }
}