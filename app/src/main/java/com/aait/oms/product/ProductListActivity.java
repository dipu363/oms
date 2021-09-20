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
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.CommonFunctions;
import com.google.gson.internal.LinkedTreeMap;

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
    List<StockViewModel> allproductlist;

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
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Products");
        appUtils = new AppUtils(this);

        listView = findViewById(R.id.productlist_id);
        //recyclerView = findViewById(R.id.product_list_recy_id);
        // searchView1 = findViewById(R.id.serchviewid);
        // searchView1.setOnQueryTextListener(this);
        netWorkCheck(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();


    }

    private void getallproduct(Context context) {
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getstockview();
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if (response.code() == 500) {
                    showMaseage("Data Note found");
                } else {


                    // Log.e("success",response.body().toString());
                    // BaseResponse baseResponse = response.body();
                    assert baseResponse != null;
                    allproductlist = baseResponse.getData();
                    List<StockViewModel> prodname = new ArrayList();
                    StockViewModel prod;


                    for (int i = 0; i < allproductlist.size(); i++) {
                        Object getrow = allproductlist.get(i);
                        LinkedTreeMap<Object, Object> t = (LinkedTreeMap) getrow;

                   /* String l1code = String.valueOf(t.get("l1code"));
                    String l2code = String.valueOf(t.get("l2code"));
                    String l3code = String.valueOf(t.get("l3code"));
                    String l4code = String.valueOf(t.get("l4code"));
                    String salesrate = String.valueOf(t.get("salesrate"));
                    String uomid = String.valueOf(t.get("uomid"));
                    String productname = String.valueOf(t.get("productname"));
                    String activeStatus = String.valueOf(t.get("activeStatus"));
                    String ledgername = String.valueOf(t.get("ledgername"));*/

                        String pcode = String.valueOf(t.get("pcode"));
                        String uomName = String.valueOf(t.get("uomName"));
                        String soldQty = String.valueOf(t.get("soldQty"));
                        String totalQty = String.valueOf(t.get("totalQty"));
                        String currentQty = String.valueOf(t.get("currentQty"));
                        String avgPurRate = String.valueOf(t.get("avgPurRate"));
                        String salesRate = String.valueOf(t.get("salesRate"));
                        String currentTotalPrice = String.valueOf(t.get("currentTotalPrice"));
                        String pname = String.valueOf(t.get("pname"));
                        String cumTotalPrice = String.valueOf(t.get("cumTotalPrice"));

                        prod = new StockViewModel(pcode, uomName, soldQty, totalQty, currentQty, avgPurRate, salesRate, currentTotalPrice, pname, cumTotalPrice);
                        prodname.add(prod);

                    }

                    Log.d("prodname", prodname.toString());

                    productAdapter = new ProductAdapter(context, prodname);
                    listView.setAdapter(productAdapter);

                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

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