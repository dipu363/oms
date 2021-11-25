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
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite_Product_Fragment extends Fragment {
    FavoriteProductAdapter adapter;
    ListView listView;
    List<ProductModel> productModelList;
    ArrayList<String> prodidlist = new ArrayList<>();
    ProductModel productModel;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ProgressDialog progressDialog;

    public Favorite_Product_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productModelList = new ArrayList<>();
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

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getsingleproduct(id);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                assert response.body() != null;
                Log.e("success", response.body().toString());
                if (response.isSuccessful()) {
                    Log.e("success", response.body().toString());
                    BaseResponse baseResponse = response.body();
                    assert baseResponse != null;
                    Object row = baseResponse.getObj();
                    LinkedTreeMap<Object, Object> t = (LinkedTreeMap) row;
                    String l1code = String.valueOf(t.get("l1code"));
                    String l2code = String.valueOf(t.get("l2code"));
                    String l3code = String.valueOf(t.get("l3code"));
                    String l4code = String.valueOf(t.get("l4code"));
                    String salesrate = String.valueOf(t.get("salesrate"));
                    String uomid = String.valueOf(t.get("uomid"));
                    String productname = String.valueOf(t.get("productname"));
                    String activeStatus = String.valueOf(t.get("activeStatus"));
                    String ledgername = String.valueOf(t.get("ledgername"));
                    String producPhoto = String.valueOf(t.get("productPhoto"));
                    String picbyte = String.valueOf(t.get("picByte"));
                    String imagetypt = String.valueOf(t.get("imageType"));

                    productModel = new ProductModel(l1code, l2code, l3code, l4code, salesrate, uomid, productname, activeStatus, ledgername, producPhoto, picbyte, imagetypt);
                    productModelList.add(productModel);

                }

                adapter = new FavoriteProductAdapter(getContext(), productModelList);
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Log.d("failure", t.getLocalizedMessage());

            }
        });

    }
}