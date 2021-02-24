package com.aait.oms.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductAdapter;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.rootcategory.Prod1L;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.subcategory.ProdSubCatagoryModel;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Spinner rootcat,subcat;
   ImageButton cartbutton ,btnnext;
   TextView textView;
    ListView orderlistView;
    List<ProductModel> allproductlist;
    List<Prod1L> allcatgorylist;
    List<ProdSubCatagoryModel> allsubcatlist;
    OrderProductAdapter orderProductAdapter;
    CatSpinnerAdapter catSpinnerAdapter;
    SubCatSpinnerAdapter subCatSpinnerAdapter;
    List<String> categories;
    ProdCatagoryModel[] catagory;
    ProdSubCatagoryModel[] subcatagory;
    ArrayList <String> checkedValue;

    int subcatid ;
    int l2codeid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Produts");
        rootcat = findViewById(R.id.catagoryid);
        subcat = findViewById(R.id.subcatagoryid);
        cartbutton = findViewById(R.id.cartbuttonid);
        btnnext = findViewById(R.id.btnordernextid);
        textView = findViewById(R.id.listsizeid);
        orderlistView = findViewById(R.id.order_productList_id);
        allproductlist = new ArrayList<>();
        allcatgorylist = new ArrayList<>();
        allsubcatlist = new ArrayList<>();
        checkedValue = new ArrayList<>();

        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkedValue.size()>0){

                    Intent intent = new Intent(OrderActivity.this,CartActivity.class);
                    intent.putExtra("checkvalue",checkedValue);
                    startActivity(intent);
                }else {

                    Toast.makeText(OrderActivity.this, "Item Not Found", Toast.LENGTH_LONG).show();
                }

               // Toast.makeText(OrderActivity.this,"" + checkedValue,Toast.LENGTH_LONG).show();
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedValue.size()>0){

                    Intent intent = new Intent(OrderActivity.this,CartActivity.class);
                    intent.putExtra("checkvalue",checkedValue);
                    startActivity(intent);
                }else {

                    Toast.makeText(OrderActivity.this, "Item Not Found", Toast.LENGTH_LONG).show();
                }
            }
        });

        orderlistView.setOnItemClickListener(this);



      //  getcatList(this);
       // getSubcatList(this ,2);
       // getallproduct(this,2);




    }

    @Override
    protected void onStart() {
        super.onStart();

        if(checkedValue.size()>0){
            int size = checkedValue.size();
            textView.setText(String.valueOf(size));
        }
        getcatList(this);

    }



    private  void getcatList(Context context){
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getCatList();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.isSuccessful()){
                        BaseResponse baseResponse = response.body();
                        allcatgorylist= baseResponse.getData();

                        String jsons = new Gson().toJson(allcatgorylist);
                        Type listType = new TypeToken<ProdCatagoryModel[]>() {}.getType();
                        catagory = new Gson().fromJson(jsons , listType);
                        catagory = new Gson().fromJson(jsons,listType);


                        catSpinnerAdapter =new CatSpinnerAdapter(context, android.R.layout.simple_spinner_item,catagory);
                        catSpinnerAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        rootcat.setAdapter(catSpinnerAdapter);


                        rootcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                             ProdCatagoryModel prodCatagoryModel = catSpinnerAdapter.getItem(position);
                             assert prodCatagoryModel != null;
                             subcatid = prodCatagoryModel.getL1Code();
                             String catname = prodCatagoryModel.getL1Name();
                             Toast.makeText(OrderActivity.this,catname, Toast.LENGTH_SHORT).show();
                             getSubcatList(context,subcatid);

                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> parent) {

                         }
                        });

                        Log.d("good", "onResponse: "+ catagory);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });

        }catch (Exception e){

        }


    }

    private  void getSubcatList(Context context,int subid){
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getSubCatList(subid);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.isSuccessful()){
                        BaseResponse baseResponse = response.body();
                        allsubcatlist = baseResponse.getItems();
                        Log.d("good", "onResponse: "+ allsubcatlist);

                        String jsons = new Gson().toJson(allsubcatlist);
                        Type listType = new TypeToken<ProdSubCatagoryModel[]>() {}.getType();
                        subcatagory = new Gson().fromJson(jsons , listType);
                        subcatagory = new Gson().fromJson(jsons,listType);
                        subCatSpinnerAdapter =new SubCatSpinnerAdapter(context, android.R.layout.simple_spinner_item,subcatagory);
                        subCatSpinnerAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subcat.setAdapter(subCatSpinnerAdapter);


                        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                               ProdSubCatagoryModel prodSubCatagoryModel= subCatSpinnerAdapter.getItem(position);
                                assert prodSubCatagoryModel != null;
                                l2codeid = prodSubCatagoryModel.getL2Code();
                                getallproduct(context,l2codeid);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        Log.d("good", "onResponse: "+ subcatagory);

                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Log.d("exception",e.getMessage());

        }


    }




    private void getallproduct(Context context, int id) {
        OrderService apiService =  ApiClient.getRetrofit().create(OrderService.class);
        Call<BaseResponse> productlist = apiService.getproductbyl2id(id);
        productlist.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e("success",response.body().toString());
                if(response.isSuccessful()){
                     Log.e("success",response.body().toString());
                     BaseResponse baseResponse = response.body();
                     // assert baseResponse != null;
                     allproductlist = baseResponse.getItems();
                     List<ProductModel> prodname = new ArrayList();
                     ProductModel prod = new ProductModel();
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

                    prod = new ProductModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername);

                    prodname.add(prod);

                }



                orderProductAdapter = new OrderProductAdapter(context,prodname);
                orderlistView.setAdapter(orderProductAdapter);



                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("failure",t.getLocalizedMessage());

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        CheckBox cb =  view.findViewById(R.id.prodlistchackboxid);
        TextView tv =  view.findViewById(R.id.order_product_name_id);
        TextView prodid = view.findViewById(R.id.order_prod_codeid);
        cb.performClick();
        if (cb.isChecked()) {
           // Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
            checkedValue.add(prodid.getText().toString());
            // Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
           String listsiz = String.valueOf(checkedValue.size());
           textView.setText(listsiz);




        } else if (!cb.isChecked()) {
            //Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
            checkedValue.remove(prodid.getText().toString());
            String listsiz = String.valueOf(checkedValue.size());
            textView.setText(listsiz);
        }


    }

/*    private  void getcatname(){

        CatSpinnerAdapter dataAdapter = new CatSpinnerAdapter(this, android.R.layout.simple_spinner_item, catagory);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        rootcat.setAdapter(dataAdapter);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, catagorylist);
        catagoryAutotext.setAdapter(adapter);
        catagoryAutotext.setThreshold(0);
        catagoryAutotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            getItemList();
                catagoryAutotext.clearFocus();
                catagoryAutotext.setEnabled(false);
                subCatagoryAutotext.setText("");
                subCatagoryAutotext.requestFocus();

                //getSubCat();
                //itemNameListAv.showDropDown();
//             Toast.makeText(getContext(), "" + grouNameListAv.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }*/

/*    private void getSubCat() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCatagorylist);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        subcat.setAdapter(dataAdapter);

        String rootcat = catagoryAutotext.getText().toString().trim();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, subCatagorylist);
        subCatagoryAutotext.setAdapter(adapter);

        subCatagoryAutotext.setThreshold(0);
        subCatagoryAutotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            getItemList();
                subCatagoryAutotext.clearFocus();
                subCatagoryAutotext.setEnabled(false);
//                subCatagoryAutotext.setText("");
//                subCatagoryAutotext.requestFocus();
                //getSubCat();
                //itemNameListAv.showDropDown();

//             Toast.makeText(getContext(), "" + grouNameListAv.getText(), Toast.LENGTH_SHORT).show();

            }
        });


    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}