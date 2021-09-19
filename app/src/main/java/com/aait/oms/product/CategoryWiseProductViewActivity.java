package com.aait.oms.product;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.fragment.RootCatagoryFragment;
import com.aait.oms.fragment.SubCatagoryFragment;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.OrderService;
import com.aait.oms.util.OnclickeventListener;
import com.aait.oms.subcategory.ProdSubCatagoryModel;
import com.aait.oms.subcategory.SubCategoryRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryWiseProductViewActivity extends AppCompatActivity implements OnclickeventListener {



    RecyclerView recyclerView;
    TextView subrecycletextid;
    RecyclerView.LayoutManager layoutManager;
    SubCategoryRecyclerAdapter subrecycleradapter;
    List<ProdSubCatagoryModel> allsubcatgorylist;
    ProdSubCatagoryModel[] subcatagory;


    int rootcatid=0;

    String rootcatname="  Category Item";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product_view);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            rootcatid = bundle.getInt("catid");
            rootcatname = bundle.getString("catname");

        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(rootcatname);



        allsubcatgorylist = new ArrayList<>();
       // gridView = findViewById(R.id.subcat_product_grid_view_id);
        recyclerView = findViewById(R.id.subcatrecyclerView_id);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



        //subrecycletextid.setText("All "+rootcatname+" 's");
        netWorkCheck(this,this);
    }
    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(Context context ,OnclickeventListener onclickeventListener){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            getsubcatList(context ,onclickeventListener);


            Bundle bundle = new Bundle();
            bundle.putInt("rootcatid",rootcatid);
            Fragment fragment = RootCatagoryFragment.newInstance();
            fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.prodctfragmentontainer,fragment);
        transaction.commit();
           // getproduct(context);


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


    // check category item click or not
    // if category item click then call category wise product method ;
    //if category item not click then call  get all products method ;
    private void getproduct(Context context,OnclickeventListener onclickeventListener) {
        int subcat = 0;
        String subcatname="Sub Category Items";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            subcat = extras.getInt("subcatid");
            subcatname = extras.getString("subcatname");

        }

        if (subcat != 0){
            //getsubcatList(context);
           // allsubCatWiseProductlist(context,subcat);
            subrecycletextid.setText("All "+subcatname +"'s");
        } else{
            getsubcatList(context ,onclickeventListener);
            //getcatagorywiseproduct(context);
            subrecycletextid.setText("All "+rootcatname+" 's");
        }
    }

    // getting product subcategory list
    private  void getsubcatList(Context context,OnclickeventListener onclickeventListener){
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.getSubCatList(rootcatid);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if(response.isSuccessful()){
                        BaseResponse baseResponse = response.body();
                        allsubcatgorylist = baseResponse.getItems();
                        if(allsubcatgorylist.size() == 0){
                            showMaseage("Data Not Found");
                        }else {
                            String jsons = new Gson().toJson(allsubcatgorylist);
                            Type listType = new TypeToken<ProdSubCatagoryModel[]>() {}.getType();
                            subcatagory = new Gson().fromJson(jsons, listType);
                            subcatagory = new Gson().fromJson(jsons, listType);
                            subrecycleradapter = new SubCategoryRecyclerAdapter(subcatagory,context,onclickeventListener);
                            recyclerView.setAdapter(subrecycleradapter);


                            Log.d("good", "onResponse: " + subcatagory);
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

    // for toast massage showing
    private void showMaseage( String msg){

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        if(isLongClick){
            Toast.makeText(this, "LongClick", Toast.LENGTH_SHORT).show();
        }else{
            Bundle bundle = new Bundle();
            bundle.putInt("subcatcatid",subcatagory[position].getL2Code());

            Fragment fragment = SubCatagoryFragment.newInstance();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.prodctfragmentontainer,fragment);
            transaction.commit();
        }



    }


/*    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "click" +position, Toast.LENGTH_SHORT).show();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.prodcontainer,new HomePragment())
                .commit();

    }*/
}