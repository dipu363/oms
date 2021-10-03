package com.aait.oms.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.CategoryWiseProductViewActivity;
import com.aait.oms.product.ProductGridAdapter;
import com.aait.oms.product.ProductInGridViewActivity;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.Product_Details_view_Activity;
import com.aait.oms.subcategory.ProdSubCatagoryModel;
import com.aait.oms.subcategory.SubCategoryRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RootCatagoryFragment extends Fragment {



    //for grid view
    GridView gridView;
    ProductGridAdapter productgridAdapter;
    List<ProductModel> allproductlist;

    SubCategoryRecyclerAdapter subrecycleradapter;
    List<ProdSubCatagoryModel> allsubcatgorylist;
    ProdSubCatagoryModel[] subcatagory;



    int subcatid=0;
    String rootcatname="  Category Item";

    public RootCatagoryFragment() {
        // Required empty public constructor
    }

    public static RootCatagoryFragment newInstance() {
        RootCatagoryFragment fragment = new RootCatagoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int rootid = getArguments().getInt("rootcatid");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_root_catagory, container, false);
        gridView = view.findViewById(R.id.subcat_allproduct_grid_view_id);
        getcatagorywiseproduct(getContext(),rootid);
        return view;
    }




        //getting category wise products
    private void getcatagorywiseproduct(Context context,int rootid){

        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getproductbyl1id(rootid);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                assert baseResponse != null;
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
                        LinkedTreeMap t = (LinkedTreeMap) getrow;

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
                        String picbyte =   String.valueOf(t.get("picByte"));
                        String imagetypt = String.valueOf(t.get("imageType"));

                        prod = new ProductModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername,producPhoto,picbyte,imagetypt);
                        prodname.add(prod);

                    }

                    // Log.d("prodname",prodname.toString());

                    productgridAdapter = new ProductGridAdapter(context,prodname);
                    gridView.setAdapter(productgridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            ProductModel productModel =   prodname.get(position);
                            Gson gson = new Gson();
                            String product = gson.toJson(productModel);

                            // String Productstring = productModel.toString();

                            System.out.println(product);
                            Intent intent = new Intent(getActivity(),Product_Details_view_Activity.class);
                            intent.putExtra("product" , product);
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

        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }


}