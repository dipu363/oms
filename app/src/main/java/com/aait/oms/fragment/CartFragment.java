package com.aait.oms.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aait.oms.R;
import com.aait.oms.adapter.CartFrgAdapter;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.CartActivity;
import com.aait.oms.product.ProductFilterRequest;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.Product_Details_view_Activity;
import com.aait.oms.product.StockViewModel;
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


public class CartFragment extends Fragment {

    CartFrgAdapter adapter;
    ListView listView;
    Button button;
    List<StockViewModel> productList;
    ArrayList<String> cardProdIdList = new ArrayList<>();
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ApplicationData applicationData;
    ProgressDialog progressDialog;

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
        productList = new ArrayList<>();
        sqLiteDB = new SQLiteDB(getContext());
        appUtils = new AppUtils(getContext());
        applicationData = new ApplicationData(getContext());
        progressDialog = new ProgressDialog(getContext());
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
               // getActivity().finish();
            }

        });
        getCartProdId();
        return view;
    }


    public void getCartProdId() {
        Cursor cursor = sqLiteDB.getAllCardProduct();
        cardProdIdList.clear();
        if (cursor.moveToFirst()) {
            do {
                cardProdIdList.add(cursor.getString(0));
            } while (cursor.moveToNext());

            for (int i = 0; i < cardProdIdList.size(); i++) {
                getsingleproduct(cardProdIdList.get(i));
            }
        } else {
            appUtils.appToast("Cart Is Empty");
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
        Call<BaseResponse> call = apiService.productFilter(jsonObject);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                assert response.body() != null;
                Log.e("success", response.body().toString());
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getItems().size()==0) {
                        appUtils.appToast("Data Note found");
                    } else {
                        productList = baseResponse.getItems();
                        Gson gson = new Gson();
                        String json = gson.toJson(productList);
                        Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                        }.getType();
                        ArrayList<StockViewModel> product = gson.fromJson(json, typeMyType);
                        adapter = new CartFrgAdapter(getContext(), product);
                        listView.setAdapter(adapter);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("failure", t.getLocalizedMessage());

            }
        });

    }
}