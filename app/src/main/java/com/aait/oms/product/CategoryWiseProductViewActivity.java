package com.aait.oms.product;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.OrderService;
import com.aait.oms.rootcategory.ItemClickListener;
import com.aait.oms.rootcategory.Prod1L;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.rootcategory.RootCatagoryRecyclerAdapter;
import com.aait.oms.subcategory.ProdSubCatagoryModel;
import com.aait.oms.subcategory.SubCategoryRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryWiseProductViewActivity extends AppCompatActivity  {

    //for grid view
    GridView gridView;
    ProductGridAdapter productgridAdapter;
    List<ProductModel> allproductlist;
    //for recycler view
    RecyclerView recyclerView;
    TextView subrecycletextid;
    RecyclerView.LayoutManager layoutManager;
    SubCategoryRecyclerAdapter subrecycleradapter;
    List<ProdSubCatagoryModel> allsubcatgorylist;
    ProdSubCatagoryModel[] subcatagory;


    int rootcatid=0;
    int subcatid=0;
    String rootcatname="  Category Item";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product_view);
        Bundle bundle = getIntent().getExtras();

        if(bundle !=null){
            rootcatid = bundle.getInt("catid");
            rootcatname = bundle.getString("catname");

        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(rootcatname);



        allsubcatgorylist = new ArrayList<>();
        gridView = findViewById(R.id.subcat_product_grid_view_id);
        recyclerView = findViewById(R.id.subcatrecyclerView_id);
        subrecycletextid = findViewById(R.id.subcattextHaddingname_id);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //subrecycletextid.setText("All "+rootcatname+" 's");
        netWorkCheck(this);
    }

    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            //getsubcatList(context);
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


    // check category item click or not
    // if category item click then call category wise product method ;
    //if category item not click then call  get all products method ;
    private void getproduct(Context context) {
        int subcat = 0;
        String subcatname="Sub Category Items";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            subcat = extras.getInt("subcatid");
            subcatname = extras.getString("subcatname");

        }

        if (subcat != 0){
            //getsubcatList(context);
            allsubCatWiseProductlist(context,subcat);
            subrecycletextid.setText("All "+subcatname +"'s");
        } else{
            getsubcatList(context);
            getcatagorywiseproduct(context);
            subrecycletextid.setText("All "+rootcatname+" 's");
        }
    }

    // getting product subcategory list
    private  void getsubcatList(Context context){
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getSubCatList(rootcatid);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if(response.isSuccessful()){
                        BaseResponse baseResponse = response.body();
                        allsubcatgorylist = baseResponse.getItems();
                        if(allsubcatgorylist.size() == 0){
                            showMaseage("Data Not Found");
                        }else {
                            String jsons = new Gson().toJson(allsubcatgorylist);
                            Type listType = new TypeToken<ProdSubCatagoryModel[]>() {}.getType();
                            subcatagory = new Gson().fromJson(jsons, listType);
                            subcatagory = new Gson().fromJson(jsons, listType);
                            subrecycleradapter = new SubCategoryRecyclerAdapter(subcatagory,context);
                            recyclerView.setAdapter(subrecycleradapter);
                            Log.d("good", "onResponse: " + subcatagory);
                        }
                    }
                    else{

                        showMaseage("Request Not Response");

                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });

        }catch (Exception e){
            Log.d("Exception", "onResponse: " + e);
        }
    }


    //getting all products
    private void allsubCatWiseProductlist(Context context,int subcatid){
        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getproductbyl2id(subcatid);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if(baseResponse.getMessage().equals("") ) {
                    showMaseage("Data Note found");
                } else{
                    // Log.e("success",response.body().toString());
                    // BaseResponse baseResponse = response.body();
                    assert baseResponse != null;
                    allproductlist = baseResponse.getItems();
                    List<ProductModel> prodname = new ArrayList();
                    ProductModel prod;


                    for(int i = 0 ; i<allproductlist.size(); i++){
                        Object getrow =allproductlist.get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;

                        String l1code = String.valueOf(t.get("l1code"));
                        String l2code = String.valueOf(t.get("l2code"));
                        String l3code = String.valueOf(t.get("l3code"));
                        String l4code = String.valueOf(t.get("l4code"));
                        String salesrate = String.valueOf(t.get("salesrate"));
                        String uomid = String.valueOf(t.get("uomid"));
                        String productname = String.valueOf(t.get("productname"));
                        String activeStatus = String.valueOf(t.get("activeStatus"));
                        String ledgername = String.valueOf(t.get("ledgername"));


                        // if call stockviewmodel class than set as below type

                      /*  String pcode = String.valueOf(t.get("pcode"));
                        String uomName = String.valueOf(t.get("uomName"));
                        String soldQty = String.valueOf(t.get("soldQty"));
                        String totalQty = String.valueOf(t.get("totalQty"));
                        String currentQty = String.valueOf(t.get("currentQty"));
                        String avgPurRate = String.valueOf(t.get("avgPurRate"));
                        String salesRate = String.valueOf(t.get("salesRate"));
                        String currentTotalPrice = String.valueOf(t.get("currentTotalPrice"));
                        String pname = String.valueOf(t.get("pname"));
                        String cumTotalPrice = String.valueOf(t.get("cumTotalPrice"));*/

                        // prod = new StockViewModel(pcode,uomName,soldQty,totalQty,currentQty,avgPurRate,salesRate,currentTotalPrice,pname,cumTotalPrice);
                        prod = new ProductModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername);
                        prodname.add(prod);
                    }

                    Log.d("prodname",prodname.toString());

                    productgridAdapter = new ProductGridAdapter(context,prodname);
                    gridView.setAdapter(productgridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CategoryWiseProductViewActivity.this,Product_Details_view_Activity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });

    }
    //getting category wise products
    private void getcatagorywiseproduct(Context context){

        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getproductbyl1id(rootcatid);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if(baseResponse.getMessage().equals("") ) {
                    showMaseage("Data Note found");
                } else{
                    // Log.e("success",response.body().toString());
                    // BaseResponse baseResponse = response.body();
                    assert baseResponse != null;
                    allproductlist = baseResponse.getItems();
                    List<ProductModel> prodname = new ArrayList();
                    ProductModel prod;


                    for(int i = 0 ; i<allproductlist.size(); i++){
                        Object getrow =allproductlist.get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;

                        String l1code = String.valueOf(t.get("l1code"));
                        String l2code = String.valueOf(t.get("l2code"));
                        String l3code = String.valueOf(t.get("l3code"));
                        String l4code = String.valueOf(t.get("l4code"));
                        String salesrate = String.valueOf(t.get("salesrate"));
                        String uomid = String.valueOf(t.get("uomid"));
                        String productname = String.valueOf(t.get("productname"));
                        String activeStatus = String.valueOf(t.get("activeStatus"));
                        String ledgername = String.valueOf(t.get("ledgername"));

                        prod = new ProductModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername);
                        prodname.add(prod);

                    }

                    // Log.d("prodname",prodname.toString());

                    productgridAdapter = new ProductGridAdapter(context,prodname);
                    gridView.setAdapter(productgridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CategoryWiseProductViewActivity.this,Product_Details_view_Activity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    // for toast massage showing
    private void showMaseage( String msg){

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}