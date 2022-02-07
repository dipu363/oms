package com.aait.oms.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.CartActivity;
import com.aait.oms.orders.OrderMasterModel;
import com.aait.oms.orders.OrderService;
import com.aait.oms.rootcategory.Prod1L;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.rootcategory.RootCatagoryRecyclerAdapter;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.ApplicationData;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInGridViewActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    GridView gridView;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ProgressDialog progressDialog;
    ApplicationData applicationData;
    ProductGridAdapter productgridAdapter;
    RecyclerView.LayoutManager layoutManager;
    RootCatagoryRecyclerAdapter adapter;
    List<StockViewModel> allproductlist;
    List<Prod1L> allcatgorylist;
    ProdCatagoryModel[] catagory;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_in_grid_view);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("  All Products");
        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);
        progressDialog = new ProgressDialog(this);
        applicationData = new ApplicationData(this);
        allcatgorylist = new ArrayList<>();
        gridView = findViewById(R.id.product_grid_view_id);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //all time call net work check method as last line in hare ;
        netWorkCheck(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }

    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            getcatList(context);
            getproduct(context);

        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(" NO NetWork")
                    .setMessage("Enable Mobile Network ")
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
                    finish();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    // getting product category list
    private void getcatList(Context context) {
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getCatList();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        allcatgorylist = baseResponse.getData();
                        if (allcatgorylist.size() == 0) {
                            appUtils.appToast("Data Not Found");
                        } else {
                            String jsons = new Gson().toJson(allcatgorylist);
                            Type listType = new TypeToken<ProdCatagoryModel[]>() {
                            }.getType();
                            catagory = new Gson().fromJson(jsons, listType);
                            catagory = new Gson().fromJson(jsons, listType);
                            adapter = new RootCatagoryRecyclerAdapter(catagory, context);
                            recyclerView.setAdapter(adapter);
                            Log.d("good", "onResponse: " + catagory);
                        }
                    } else {
                        appUtils.appToast("Request Not Response");
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.d("Exception", "onResponse: " + e);
        }
    }

    // check category item click or not
    // if category item click then call category wise product method ;
    //if category item not click then call  get all products method ;
    private void getproduct(Context context) {
        int catid = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            catid = extras.getInt("catid");
        }

        if (catid != 0) {
            getcatagorywiseproduct(context, catid);
        } else {
            allProductlist(context);
        }
    }

    //getting all products
    private void allProductlist(Context context) {

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getstockview();
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if (baseResponse.getMessage().equals("")) {
                    appUtils.appToast("Data Note found");
                } else {
                    allproductlist = baseResponse.getData();
                    Gson gson = new Gson();
                    String json = gson.toJson(allproductlist);
                    Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                    }.getType();
                    ArrayList<StockViewModel> productlist = gson.fromJson(json, typeMyType);
                    productgridAdapter = new ProductGridAdapter(context, productlist);
                    gridView.setAdapter(productgridAdapter);
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });

    }

    //getting category wise products
    private void getcatagorywiseproduct(Context context, int id) {
        ProductFilterRequest filterRequest = new ProductFilterRequest();
        filterRequest.setL1Code(id);
        Gson gson = new Gson();
        String json = gson.toJson(filterRequest);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.productFilter(jsonObject);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if (baseResponse.getMessage().equals("")) {

                    appUtils.appToast("Data Note found");
                } else {
                    assert baseResponse != null;
                    allproductlist = baseResponse.getItems();
                    List<StockViewModel> prodname = new ArrayList();
                    StockViewModel prod;
                    Gson gson = new Gson();
                    String json = gson.toJson(allproductlist);
                    Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                    }.getType();
                    ArrayList<StockViewModel> productlist = gson.fromJson(json, typeMyType);
                    productgridAdapter = new ProductGridAdapter(context, productlist);
                    gridView.setAdapter(productgridAdapter);
                    progressDialog.dismiss();
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
        getMenuInflater().inflate(R.menu.card_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.tabCartId);
        View actionView = menuItem.getActionView();
        Cursor cursor = sqLiteDB.getAllCardProduct();
        textView = actionView.findViewById(R.id.cart_badge_text_view);
        textView.setText(String.valueOf(cursor.getCount()));
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.tabCartId) {
            SQLiteDB sqLiteDB = new SQLiteDB(this);
            Cursor cursor1 = sqLiteDB.getUserInfo();
            Cursor cursor2 = sqLiteDB.getAllCardProduct();
            int loginstatus = 0;

            if (cursor1.moveToFirst()) {
                loginstatus = cursor1.getInt(4);
            }
            if (cursor2.getCount() > 0) {
                if (loginstatus == 1) {
                    Intent intent = new Intent(ProductInGridViewActivity.this, CartActivity.class);
                    intent.putExtra("SQ", "SQ");
                    startActivity(intent);
                } else {
                    applicationData.savePageState("prodgridview", "p1"); // SharedPreferences
                    Intent intent = new Intent(ProductInGridViewActivity.this, LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            } else {
                appUtils.appToast("Cart is Empty");
            }


        }

        return super.onOptionsItemSelected(item);
    }
}