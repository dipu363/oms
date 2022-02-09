package com.aait.oms.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aait.oms.R;
import com.aait.oms.adapter.FavoriteProductAdapter;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductFilterRequest;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.StockViewModel;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite_Product_Fragment extends Fragment {
    FavoriteProductAdapter fabAdapter;
    ListView listView;
    List<StockViewModel> productList;
    ArrayList<String> prodidlist = new ArrayList<>();
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ProgressDialog progressDialog;

    public Favorite_Product_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();
        sqLiteDB = new SQLiteDB(getContext());
        appUtils = new AppUtils(getContext());
        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_product, container, false);
        listView = view.findViewById(R.id.fav_productlist_id);
        getFabProdId();
        return view;
    }

    public void getFabProdId() {
        Cursor cursor = sqLiteDB.getallfavoriteProduct();
        prodidlist.clear();
        if (cursor.moveToFirst()) {
            do {
                prodidlist.add(cursor.getString(0));
            } while (cursor.moveToNext());

            for (int i = 0; i < prodidlist.size(); i++) {
                getsingleproduct(prodidlist.get(i));
            }
        } else {
            appUtils.appToast("There Have No Favorite Product");
        }
    }

    private void getsingleproduct(String id) {
        ProductFilterRequest filterRequest = new ProductFilterRequest();
        filterRequest.setL4Code(id);
        Gson gson = new Gson();
        String json = gson.toJson(filterRequest);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.productFilter(jsonObject);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                assert response.body() != null;
                if(response.isSuccessful()) {

                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getItems().size() == 0) {
                        appUtils.appToast("Data Note found");
                    } else {
                        productList = baseResponse.getItems();
                        Gson gson = new Gson();
                        String json = gson.toJson(productList);
                        Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                        }.getType();
                        ArrayList<StockViewModel> product = gson.fromJson(json, typeMyType);
                        fabAdapter = new FavoriteProductAdapter(getContext(), product);
                        listView.setAdapter(fabAdapter);
                    }

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Log.d("failure", t.getLocalizedMessage());

            }
        });

    }
}