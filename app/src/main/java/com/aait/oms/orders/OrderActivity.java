package com.aait.oms.orders;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
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
import com.aait.oms.product.ProductFilterRequest;
import com.aait.oms.product.ProductGridAdapter;
import com.aait.oms.product.ProductInGridViewActivity;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.StockViewModel;
import com.aait.oms.rootcategory.Prod1L;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.subcategory.ProdSubCatagoryModel;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.AppUtils;
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

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Spinner rootcat, subcat;
    ImageButton btnnext;
    TextView textView;
    ListView orderlistView;
    List productlist;
    List<Prod1L> allcatgorylist;
    List<ProdSubCatagoryModel> allsubcatlist;
    OrderProductAdapter orderProductAdapter;
    CatSpinnerAdapter catSpinnerAdapter;
    SubCatSpinnerAdapter subCatSpinnerAdapter;
    ProdCatagoryModel[] catagory;
    ProdSubCatagoryModel[] subcatagory;
    ArrayList<String> checkedValue;
    ProgressDialog progressDialog;
    AppUtils appUtils;

    int subcatid;
    int l2codeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("   Easy Order");
        rootcat = findViewById(R.id.catagoryid);
        subcat = findViewById(R.id.subcatagoryid);
        btnnext = findViewById(R.id.btnordernextid);
        orderlistView = findViewById(R.id.order_productList_id);
       productlist = new ArrayList<>();
        allcatgorylist = new ArrayList<>();
        allsubcatlist = new ArrayList<>();
        checkedValue = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        appUtils = new AppUtils(this);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedValue.size() > 0) {
                    Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                    intent.putExtra("checkvalue", checkedValue);
                    startActivity(intent);
                } else {

                    Toast.makeText(OrderActivity.this, "Item Not Found", Toast.LENGTH_LONG).show();
                }
            }
        });

        orderlistView.setOnItemClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
        netWorkCheck(this);
    }

    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            getcatList(this);

        } else {
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

    private void getcatList(Context context) {
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getCatList();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        allcatgorylist = baseResponse.getData();
                        if (allcatgorylist.size() == 0) {
                            appUtils.appToast("Data Not Found");
                        } else {
                            String jsons = new Gson().toJson(allcatgorylist);
                            Type listType = new TypeToken<ProdCatagoryModel[]>() {
                            }.getType();
                            catagory = new Gson().fromJson(jsons, listType);
                            catagory = new Gson().fromJson(jsons, listType);
                            catSpinnerAdapter = new CatSpinnerAdapter(context, android.R.layout.simple_spinner_item, catagory);
                            catSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            rootcat.setAdapter(catSpinnerAdapter);
                            rootcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    ProdCatagoryModel prodCatagoryModel = catSpinnerAdapter.getItem(position);
                                    assert prodCatagoryModel != null;
                                    subcatid = prodCatagoryModel.getL1Code();
                                    String catname = prodCatagoryModel.getL1Name();
                                    appUtils.appToast(catname);
                                    getSubcatList(context, subcatid);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                    } else {
                        appUtils.appToast("Request Not Response");
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {

        }
    }

    private void getSubcatList(Context context, int subid) {
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getSubCatList(subid);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        allsubcatlist = baseResponse.getItems();
                        String jsons = new Gson().toJson(allsubcatlist);
                        Type listType = new TypeToken<ProdSubCatagoryModel[]>() {
                        }.getType();
                        subcatagory = new Gson().fromJson(jsons, listType);
                        subCatSpinnerAdapter = new SubCatSpinnerAdapter(context, android.R.layout.simple_spinner_item, subcatagory);
                        subCatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subcat.setAdapter(subCatSpinnerAdapter);
                        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                ProdSubCatagoryModel prodSubCatagoryModel = subCatSpinnerAdapter.getItem(position);
                                assert prodSubCatagoryModel != null;
                                l2codeid = prodSubCatagoryModel.getL2Code();
                                getallproduct(context, l2codeid);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }


    }


    private void getallproduct(Context context, int id) {

        ProductFilterRequest filterRequest = new ProductFilterRequest();
        filterRequest.setL2Code(id);
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

                BaseResponse baseResponse = response.body();
                if (response.isSuccessful()&& baseResponse.getItems().size() != 0) {
                    productlist = baseResponse.getItems();
                    Gson gson = new Gson();
                    String json = gson.toJson(productlist);
                    Type typeMyType = new TypeToken<ArrayList<StockViewModel>>() {
                    }.getType();
                    ArrayList<StockViewModel> product = gson.fromJson(json, typeMyType);
                    orderProductAdapter = new OrderProductAdapter(context, product);
                    orderlistView.setAdapter(orderProductAdapter);
                } else {
                    appUtils.appToast("Data Note found");

                }
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Log.d("failure", t.getLocalizedMessage());

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        CheckBox cb = view.findViewById(R.id.prodlistchackboxid);
        TextView tv = view.findViewById(R.id.order_product_name_id);
        TextView prodid = view.findViewById(R.id.order_prod_codeid);
        cb.performClick();
        if (cb.isChecked()) {
            checkedValue.add(prodid.getText().toString());
            invalidateOptionsMenu();
        } else if (!cb.isChecked()) {

            checkedValue.remove(prodid.getText().toString());
            invalidateOptionsMenu();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.tabCartId);
        View actionView = menuItem.getActionView();
        textView = actionView.findViewById(R.id.cart_badge_text_view);
        textView.setText(String.valueOf(checkedValue.size()));

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.tabCartId) {
            if (checkedValue.size() > 0) {
                Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                intent.putExtra("checkvalue", checkedValue);
                startActivity(intent);
            } else {
                appUtils.appToast("Item Not Found");
            }
        }


        return super.onOptionsItemSelected(item);
    }
}