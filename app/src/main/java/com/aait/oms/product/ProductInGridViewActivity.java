package com.aait.oms.product;

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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.aait.oms.orders.OrderService;
import com.aait.oms.rootcategory.Prod1L;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.rootcategory.RootCatagoryRecyclerAdapter;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.AppUtils;
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

public class ProductInGridViewActivity extends AppCompatActivity {
//for grid view
    GridView gridView;
    ProductGridAdapter productgridAdapter;
    List<ProductModel> allproductlist;
//for recycler view
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RootCatagoryRecyclerAdapter adapter;
    List<Prod1L> allcatgorylist;
    ProdCatagoryModel[] catagory;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ArrayList<String> cardList ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_in_grid_view);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("  Products");
        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);
        allcatgorylist = new ArrayList<>();
        gridView = findViewById(R.id.product_grid_view_id);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



        Cursor cursor = sqLiteDB.getAllCardProduct();
        cardList = new ArrayList<>();


        if (cursor.moveToFirst()){
            do {
                cardList.add(cursor.getString(0));
            }  while (cursor.moveToNext());
        }

        invalidateOptionsMenu();
       //all time call net work check method as last line in hare ;
        netWorkCheck(this);
    }

    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            getcatList(context);
            getproduct(context);

        }
        else {
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
                            showMaseage("Data Not Found");
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


// check category item click or not
    // if category item click then call category wise product method ;
    //if category item not click then call  get all products method ;
    private void getproduct(Context context) {
        int catid = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            catid = extras.getInt("catid");
        }

        if (catid != 0){
            getcatagorywiseproduct(context,catid);
        } else{
            allProductlist(context);
        }
    }

//getting all products
    private void allProductlist(Context context){
        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getallproduct();
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if(baseResponse.getMessage().equals("") ) {
                    showMaseage("Data Note found");
                } else{
                    // Log.e("success",response.body().toString());
                    // BaseResponse baseResponse = response.body();
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

                    Log.d("prodname",prodname.toString());

                    productgridAdapter = new ProductGridAdapter(context,prodname);
                    gridView.setAdapter(productgridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ProductModel productModel =   prodname.get(position);
                            Gson gson = new Gson();
                            String product = gson.toJson(productModel);

                           // String Productstring = productModel.toString();

                            //System.out.println(product);
                            Intent intent = new Intent(ProductInGridViewActivity.this,Product_Details_view_Activity.class);
                            intent.putExtra("product" , product);

                       /*     Object product = productgridAdapter.getItem(position);

                            Intent intent = new Intent(ProductInGridViewActivity.this, Product_Details_view_Activity.class);*/
                            // intent.putExtra("product", (Bundle) product);
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
    private void getcatagorywiseproduct(Context context, int id){

        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getproductbyl1id(id);
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

                           // System.out.println(product);
                            Intent intent = new Intent(ProductInGridViewActivity.this,Product_Details_view_Activity.class);
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

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.card_menu,menu);

      final MenuItem menuItem = menu.findItem(R.id.tabCartId);
       View actionView = menuItem.getActionView();


        TextView textView = actionView.findViewById(R.id.cart_badge_text_view);
        textView.setText(String.valueOf(cardList.size()));

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });


        return  true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        else if(item.getItemId() == R.id.tabCartId){


            int loginstatus = 0;

            // FirebaseUser  user = mAuth.getCurrentUser();
            SQLiteDB sqLiteDB = new SQLiteDB(this);
            //sqLiteDB.updateuserotp(currentuser,1);
            Cursor cursor = sqLiteDB.getUserInfo();
            if (cursor.moveToFirst()) {
                loginstatus = cursor.getInt(4);
            }

            if (loginstatus == 1) {
                int cartsize = cardList.size();
                if (cartsize>0){
                    Intent intent = new Intent(ProductInGridViewActivity.this, CartActivity.class);
                    intent.putExtra("SQ","SQ");
                    startActivity(intent);
                }else {
                    appUtils.appToast("Cart is Empty");
                }
            }else{
                Intent intent = new Intent(ProductInGridViewActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }





        }
        return super.onOptionsItemSelected(item);
    }
}