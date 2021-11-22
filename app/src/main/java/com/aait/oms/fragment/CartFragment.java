package com.aait.oms.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aait.oms.R;
import com.aait.oms.adapter.CartFrgAdapter;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.CartActivity;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.Product_Details_view_Activity;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.ApplicationData;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    CartFrgAdapter adapter;
    ListView listView;
    Button button;
    List<ProductModel> productModelList;
    ArrayList<String> prodidlist = new ArrayList<>();
    ProductModel productModel;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ApplicationData applicationData;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productModelList = new ArrayList<>();
        sqLiteDB = new SQLiteDB(getContext());
        appUtils = new AppUtils(getContext());
        applicationData = new ApplicationData(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listView = view.findViewById(R.id.cart_fr_productlist_id);
        button = view.findViewById(R.id.frag_cart_btn_conti_id);
        button.setOnClickListener(view1 -> {

            int loginstatus = 0;

            // FirebaseUser  user = mAuth.getCurrentUser();
            SQLiteDB sqLiteDB = new SQLiteDB(getContext());
            //sqLiteDB.updateuserotp(currentuser,1);
            Cursor cursor = sqLiteDB.getUserInfo();
            if (cursor.moveToFirst()) {
                loginstatus = cursor.getInt(4);
            }

            if (loginstatus == 1) {

                if (cursor.getCount() > 0) {
                    Intent intent = new Intent(getContext(), CartActivity.class);
                    intent.putExtra("SQ", "SQ");
                    startActivity(intent);
                } else {
                    appUtils.appToast("Cart is Empty");
                }
            } else {
                applicationData.savePageState("prodgridview", "p1"); // SharedPreferences
                Intent intent = new Intent(getContext(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
        getCartProdId();
        return view;
    }


    public void getCartProdId() {
        Cursor cursor = sqLiteDB.getAllCardProduct();
        prodidlist.clear();
        if (cursor.moveToFirst()) {
            do {
                prodidlist.add(cursor.getString(0));
            } while (cursor.moveToNext());

            for (int i = 0; i < prodidlist.size(); i++) {
                getsingleproduct(prodidlist.get(i));
            }
        } else {
            appUtils.appToast("Cart Is Empty");
        }
    }


    private void getsingleproduct(String id) {
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
                    // assert baseResponse != null;

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

                adapter = new CartFrgAdapter(getContext(), productModelList);
                listView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("failure", t.getLocalizedMessage());

            }
        });

    }
}