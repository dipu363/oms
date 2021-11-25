package com.aait.oms.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.OrderService;
import com.aait.oms.product.ProductGridAdapter;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.Product_Details_view_Activity;
import com.aait.oms.rootcategory.Prod1L;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.rootcategory.RootCatagoryRecyclerAdapter;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.ApplicationData;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment {

    RecyclerView recyclerView;
    GridView gridView;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ApplicationData applicationData;
    ProductGridAdapter productgridAdapter;
    RecyclerView.LayoutManager layoutManager;
    RootCatagoryRecyclerAdapter adapter;
    List<ProductModel> allproductlist;
    List<Prod1L> allcatgorylist;
    ProdCatagoryModel[] catagory;
    ArrayList<String> cardList ;

    ProgressDialog progressDialog;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqLiteDB = new SQLiteDB(getContext());
        appUtils = new AppUtils(getContext());
        applicationData = new ApplicationData(getContext());
        progressDialog = new ProgressDialog(getContext());

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product, container, false);

        allcatgorylist = new ArrayList<>();
        gridView = view.findViewById(R.id.product_grid_view_id);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Cursor cursor = sqLiteDB.getAllCardProduct();
        cardList = new ArrayList<>();


        if (cursor.moveToFirst()){
            do {
                cardList.add(cursor.getString(0));
            }  while (cursor.moveToNext());
        }

        // invalidateOptionsMenu();
        //all time call net work check method as last line in hare ;
        netWorkCheck(requireContext());


        return view;
    }


    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            getcatList(context);
            allProductlist(context);

        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

    }



    // getting product category list
    private  void getcatList(Context context){

        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getCatList();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if(response.isSuccessful()){
                        BaseResponse baseResponse = response.body();
                        allcatgorylist = baseResponse.getData();
                        if(allcatgorylist.size() == 0){
                            appUtils.appToast("Data Not Found");
                        }else {
                            String jsons = new Gson().toJson(allcatgorylist);
                            Type listType = new TypeToken<ProdCatagoryModel[]>() {}.getType();
                            catagory = new Gson().fromJson(jsons, listType);
                            catagory = new Gson().fromJson(jsons, listType);
                            adapter = new RootCatagoryRecyclerAdapter(catagory,context);
                            recyclerView.setAdapter(adapter);
                            Log.d("good", "onResponse: " + catagory);
                        }
                    }
                    else{

                        appUtils.appToast("Request Not Response");

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
    private void allProductlist(Context context){
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_prograess_dialog_layout);
        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getallproduct();
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if(baseResponse.getMessage().equals("") ) {
                    appUtils.appToast("Data Note found");
                } else{
                    allproductlist = baseResponse.getData();
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
                        String producPhoto = String.valueOf(t.get("productPhoto"));
                        String picbyte =   String.valueOf(t.get("picByte"));
                        String imagetypt = String.valueOf(t.get("imageType"));


                        // if call stockviewmodel class than set as below type

                   /*     String pcode = String.valueOf(t.get("pcode"));
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
                        prod = new ProductModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername,producPhoto,picbyte,imagetypt);
                        prodname.add(prod);
                    }
                    productgridAdapter = new ProductGridAdapter(context,prodname);
                    gridView.setAdapter(productgridAdapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });


    }
}