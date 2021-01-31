package com.aait.oms.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.aait.oms.R;
import com.aait.oms.adapter.CatagoryAdapter;
import com.aait.oms.adapter.ProductAdapter;
import com.aait.oms.adapter.SupplierAdapter;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.model.ProdCatagoryModel;
import com.aait.oms.model.ProdSubCatagoryModel;
import com.aait.oms.model.ProductModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    SearchView searchView;
    Spinner rootcat,subcat;
    ListView listView;
    List<ProductModel> allproductlist;
    List<ProdCatagoryModel> allcatgorylist;
    List<ProdSubCatagoryModel> allsubcatlist;
    ProductAdapter productAdapter;
    CatagoryAdapter catagoryAdapter;

    private AutoCompleteTextView doctorNameListAv, grouNameListAv, itemNameListAv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PRODUCTS");
        listView = findViewById(R.id.productlistviewid);
        rootcat = findViewById(R.id.catagoryid);
        subcat = findViewById(R.id.subcatagoryid);

        allproductlist = new ArrayList<>();
        allcatgorylist = new ArrayList<>();
        allsubcatlist = new ArrayList<>();


        getallcatagory(this);
        getallsubcatagory();
        getallproduct(this);


    }

    private void getallcatagory(Context context) {
        Call<BaseResponse>rootcatlist = ApiClient.getProductService().getallrootcatagory();
        rootcatlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               // Log.e("success",response.body().toString());

                BaseResponse baseResponse = response.body();
                allcatgorylist = baseResponse.getData();
//                catagoryAdapter = new CatagoryAdapter(context,allcatgorylist);
//                rootcat.setAdapter(catagoryAdapter);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

    private void getallsubcatagory() {
        Call<BaseResponse>subcatlist = ApiClient.getProductService().getallsubcatagory();
        subcatlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                //Log.e("success",response.body().toString());

                BaseResponse baseResponse = response.body();
                allsubcatlist = baseResponse.getData();
                Log.e("success",allsubcatlist.toString());

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

    private void getallproduct(Context context) {
        Call<BaseResponse> productlist = ApiClient.getProductService().getallproduct();
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

               // Log.e("success",response.body().toString());

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

/*    private void getGroupList() {
        stockGroupslist = new ArrayList<StockGroup>();


        stockGroupslist = dbHelper.getStocGroup();
        final List<String> groupsList = new ArrayList<>();
        for (StockGroup group : stockGroupslist) {
            groupsList.add(group.getGroupName().trim());
        }






        Collections.sort(groupsList);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, groupsList);
        grouNameListAv.setAdapter(adapter);
        grouNameListAv.setThreshold(0);
        grouNameListAv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                getItemList();
                grouNameListAv.clearFocus();
                grouNameListAv.setEnabled(false);
                itemNameListAv.setText("");
                itemNameListAv.requestFocus();

                getItemList();
                itemNameListAv.showDropDown();

//                Toast.makeText(getContext(), "" + grouNameListAv.getText(), Toast.LENGTH_SHORT).show();

            }
        });


    }*/
}