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
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.fragment.RootCatagoryFragment;
import com.aait.oms.fragment.SubCatagoryFragment;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.CartActivity;
import com.aait.oms.orders.OrderService;
import com.aait.oms.subcategory.ProdSubCatagoryModel;
import com.aait.oms.subcategory.SubCategoryRecyclerAdapter;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.OnclickeventListener;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryWiseProductViewActivity extends AppCompatActivity implements OnclickeventListener {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SubCategoryRecyclerAdapter subrecycleradapter;
    List<ProdSubCatagoryModel> allsubcatgorylist;
    ProdSubCatagoryModel[] subcatagory;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    ArrayList<String> cardList;
    int rootcatid = 0;
    String rootcatname = "  Category Item";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            rootcatid = bundle.getInt("catid");
            rootcatname = bundle.getString("catname");

        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(rootcatname);

        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);
        allsubcatgorylist = new ArrayList<>();
        recyclerView = findViewById(R.id.subcatrecyclerView_id);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Cursor cursor = sqLiteDB.getAllCardProduct();
        cardList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                cardList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        invalidateOptionsMenu();
        netWorkCheck(this, this);
    }

    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context, OnclickeventListener onclickeventListener) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            getsubcatList(context, onclickeventListener);

            Bundle bundle = new Bundle();
            bundle.putInt("rootcatid", rootcatid);
            Fragment fragment = RootCatagoryFragment.newInstance();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.prodctfragmentontainer, fragment);
            transaction.commit();

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

    // getting product subcategory list
    private void getsubcatList(Context context, OnclickeventListener onclickeventListener) {
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getSubCatList(rootcatid);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        allsubcatgorylist = baseResponse.getItems();
                        if (allsubcatgorylist.size() == 0) {
                            appUtils.appToast("Data Not Found");
                        } else {
                            String jsons = new Gson().toJson(allsubcatgorylist);
                            Type listType = new TypeToken<ProdSubCatagoryModel[]>() {
                            }.getType();
                            subcatagory = new Gson().fromJson(jsons, listType);
                            subcatagory = new Gson().fromJson(jsons, listType);
                            subrecycleradapter = new SubCategoryRecyclerAdapter(subcatagory, context, onclickeventListener);
                            recyclerView.setAdapter(subrecycleradapter);
                            Log.d("good", "onResponse: " + subcatagory);
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
            Log.d("Exception", "onResponse: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_menu, menu);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.tabCartId) {
            int loginstatus = 0;
            SQLiteDB sqLiteDB = new SQLiteDB(this);
            Cursor cursor = sqLiteDB.getUserInfo();
            if (cursor.moveToFirst()) {
                loginstatus = cursor.getInt(4);
            }

            if (loginstatus == 1) {
                int cartsize = cardList.size();
                if (cartsize > 0) {
                    Intent intent = new Intent(CategoryWiseProductViewActivity.this, CartActivity.class);
                    intent.putExtra("SQ", "SQ");
                    startActivity(intent);
                } else {
                    appUtils.appToast("Cart is Empty");
                }
            } else {
                Intent intent = new Intent(CategoryWiseProductViewActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if (isLongClick) {
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("subcatcatid", subcatagory[position].getL2Code());
            Fragment fragment = SubCatagoryFragment.newInstance();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.prodctfragmentontainer, fragment);
            transaction.commit();
        }
    }
}