package com.aait.oms.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ImageButton nextbtn;
    ListView listView;
    List <CardModel>cardproductlist;
    CartAdapter cartAdapter;
    CardModel cardprod ;
    ArrayList<String> lOrderItems=new ArrayList<>();
    ArrayList<CardModel> productOrders = new ArrayList<>();
    ArrayList <String> prodidlist =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Selected Products");



        listView = findViewById(R.id.ordercartlistid);
        nextbtn = findViewById(R.id.btncartnextid);
        cardproductlist = new ArrayList<>();
        cardprod = new CardModel();
        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){

            prodidlist.clear();
             String dataFromSqlite = bundle.getString("SQ");
             if(dataFromSqlite.equals("")){
                 prodidlist = bundle.getStringArrayList("checkvalue");
                 for (int i = 0 ;i< prodidlist.size();i++){
                     getsingleproduct(this,prodidlist.get(i));
                 }

             }else{
                 Cursor cursor = sqLiteDB.getAllCardProduct();
                 if (cursor.moveToFirst()){
                     do {
                         prodidlist.add(cursor.getString(0));
                     }  while (cursor.moveToNext());

                     for (int i = 0 ;i< prodidlist.size();i++){
                         getsingleproduct(this,prodidlist.get(i));
                     }
                 }else{
                  appUtils.appToast("Cart Is Empty");
                 }
             }

        }

        nextbtn.setOnClickListener(this);
    }



    private void getsingleproduct(Context context, String id) {
        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        Call<BaseResponse> productlist = apiService.getsingleproduct(id);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e("success",response.body().toString());
                if(response.isSuccessful()){
                    Log.e("success",response.body().toString());
                    BaseResponse baseResponse = response.body();
                    // assert baseResponse != null;

                    Object row =  baseResponse.getObj();
                    LinkedTreeMap<Object,Object> t = (LinkedTreeMap) row;
                        String l1code = String.valueOf(t.get("l1code"));
                        String l2code = String.valueOf(t.get("l2code"));
                        String l3code = String.valueOf(t.get("l3code"));
                        String l4code = String.valueOf(t.get("l4code"));
                        String salesrate = String.valueOf(t.get("salesrate"));
                        String uomid = String.valueOf(t.get("uomid"));
                        String productname = String.valueOf(t.get("productname"));
                        String activeStatus = String.valueOf(t.get("activeStatus"));
                        String ledgername = String.valueOf(t.get("ledgername"));

                 /*   String pcode = String.valueOf(t.get("pcode"));
                    String uomName = String.valueOf(t.get("uomName"));
                    String soldQty = String.valueOf(t.get("soldQty"));
                    String totalQty = String.valueOf(t.get("totalQty"));
                    String currentQty = String.valueOf(t.get("currentQty"));
                    String avgPurRate = String.valueOf(t.get("avgPurRate"));
                    String salesRate = String.valueOf(t.get("salesRate"));
                    String currentTotalPrice = String.valueOf(t.get("currentTotalPrice"));
                    String pname = String.valueOf(t.get("pname"));
                    String cumTotalPrice = String.valueOf(t.get("cumTotalPrice"));*/

                        cardprod = new CardModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername,0);
                        Log.d("name",cardprod.getProductname());
                        cardproductlist.add(cardprod);

                }

                cartAdapter = new CartAdapter(context,cardproductlist);
                listView.setAdapter(cartAdapter);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("failure",t.getLocalizedMessage());

            }
        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btncartnextid:
                placeOrder();
                break;


        }
    }

    private void placeOrder() {
        productOrders.clear();
        lOrderItems.clear();
        for(int i=0;i<cartAdapter.prodlist.size();i++)
        {
            if(cartAdapter.prodlist.get(i).getQty() > 0)
            {
                String l1code = cartAdapter.prodlist.get(i).getL1code();
                String l2code = cartAdapter.prodlist.get(i).getL2code();
                String l3code = cartAdapter.prodlist.get(i).getL3code();
                String l4code = cartAdapter.prodlist.get(i).getL4code();
                String salesrate = cartAdapter.prodlist.get(i).getSalesrate();
                String uomid = cartAdapter.prodlist.get(i).getUomid();
                String productname = cartAdapter.prodlist.get(i).getProductname();
                String activeStatus = cartAdapter.prodlist.get(i).getActiveStatus();
                String ledgername = cartAdapter.prodlist.get(i).getLedgername();
                int qty = cartAdapter.prodlist.get(i).getQty();

                CardModel products = new CardModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername,qty);
                productOrders.add(products);
                lOrderItems.add(products.getJsonObject().toString());
            }
        }

        /* Convert String ArrayList into JSON Array */
        JSONArray jsonArray = new JSONArray(lOrderItems);
        /* Open Summary with JSONArray String */
        openSummary(jsonArray.toString());

    }

    public void openSummary(String orderItems)
    {



            Intent summaryIntent = new Intent(this,SummaryActivity.class);
            summaryIntent.putExtra("orderItems",orderItems);
            startActivity(summaryIntent);
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