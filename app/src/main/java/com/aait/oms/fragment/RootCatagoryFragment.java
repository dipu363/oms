package com.aait.oms.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductGridAdapter;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.Product_Details_view_Activity;
import com.aait.oms.product.StockViewModel;
import com.aait.oms.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RootCatagoryFragment extends Fragment {

    GridView gridView;
    ProductGridAdapter productgridAdapter;
    List<StockViewModel> allproductlist;
    AppUtils appUtils;
    ProgressDialog progressDialog;

    public RootCatagoryFragment() {
        // Required empty public constructor
    }

    public static RootCatagoryFragment newInstance() {
        RootCatagoryFragment fragment = new RootCatagoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appUtils = new AppUtils(getContext());
        progressDialog = new ProgressDialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int rootid = getArguments().getInt("rootcatid");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_root_catagory, container, false);
        gridView = view.findViewById(R.id.subcat_allproduct_grid_view_id);
        getcatagorywiseproduct(getContext(), rootid);
        return view;
    }


    //getting category wise products
    private void getcatagorywiseproduct(Context context, int rootid) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        ProductInterface apiService = ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getproductbyl1id(rootid);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                assert baseResponse != null;
                if (baseResponse.getMessage().equals("")) {
                    appUtils.appToast("Data Note found");
                } else {
                    assert baseResponse != null;
                    allproductlist = baseResponse.getItems();
                    List<StockViewModel> prodname = new ArrayList();
                    StockViewModel prod;
/*                    for (int i = 0; i < allproductlist.size(); i++) {
                        Object getrow = allproductlist.get(i);
                        LinkedTreeMap t = (LinkedTreeMap) getrow;

//                        String l1code = String.valueOf(t.get("l1code"));
//                        String l2code = String.valueOf(t.get("l2code"));
//                        String l3code = String.valueOf(t.get("l3code"));
//                        String l4code = String.valueOf(t.get("l4code"));
//                        String salesrate = String.valueOf(t.get("salesrate"));
//                        String uomid = String.valueOf(t.get("uomid"));
//                        String productname = String.valueOf(t.get("productname"));
//                        String activeStatus = String.valueOf(t.get("activeStatus"));
//                        String ledgername = String.valueOf(t.get("ledgername"));
//                        String producPhoto = String.valueOf(t.get("productPhoto"));
//                        String picbyte = String.valueOf(t.get("picByte"));
//                        String imagetypt = String.valueOf(t.get("imageType"));

                        String pcode = String.valueOf(t.get("pcode"));
                        String uomName = String.valueOf(t.get("uomName"));
                        String picbyte = String.valueOf(t.get("picByte"));
                        String prodDetails = String.valueOf(t.get("prodDetails"));
                        String soldQty = String.valueOf(t.get("soldQty"));
                        String totalQty = String.valueOf(t.get("totalQty"));
                        String currentQty = String.valueOf(t.get("currentQty"));
                        String avgPurRate = String.valueOf(t.get("avgPurRate"));
                        String salesRate = String.valueOf(t.get("salesRate"));
                        String currentTotalPrice = String.valueOf(t.get("currentTotalPrice"));
                        String pname = String.valueOf(t.get("pname"));
                        String cumTotalPrice = String.valueOf(t.get("cumTotalPrice"));

                      //  prod = new StockViewModel(pcode,picbyte,uomName,prodDetails,soldQty,totalQty,currentQty,avgPurRate,salesRate,currentTotalPrice,pname,cumTotalPrice);
                       // prod = new StockViewModel(l1code, l2code, l3code, l4code, salesrate, uomid, productname, activeStatus, ledgername, producPhoto, picbyte, imagetypt);
                       // prodname.add(prod);

                    }*/


                    Gson gson = new Gson();
                    String json = gson.toJson(allproductlist);
                    Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                    }.getType();
                    ArrayList<StockViewModel> productlist = gson.fromJson(json, typeMyType);

                    productgridAdapter = new ProductGridAdapter(context, productlist);
                    gridView.setAdapter(productgridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            StockViewModel productModel = prodname.get(position);
                            Gson gson = new Gson();
                            String product = gson.toJson(productModel);
                            System.out.println(product);
                            Intent intent = new Intent(getActivity(), Product_Details_view_Activity.class);
                            intent.putExtra("product", product);
                            startActivity(intent);
                        }
                    });

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }


}