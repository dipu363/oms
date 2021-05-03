package com.aait.oms.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.branch.BranchAdapter;
import com.aait.oms.branch.BranchModel;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductModel;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.users.UserModel;
import com.aait.oms.users.UserService;
import com.aait.oms.util.CommonFunctions;
import com.aait.oms.util.SQLiteDB;
import com.google.android.gms.common.internal.Asserts;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity implements View.OnClickListener , CommonFunctions {
    TextView selectdelioption, branchadd ,paytextviewmassage;
    RadioButton radioButton1,radioButton2;
    EditText shipaddress, cmobile;
    Spinner branchspinner,paymentspinner;
    Button btnsubmint;
    List<BranchModel> allbranchlist;
    BranchModel[] branchsarraylist;
    //OrderDetailsModel[] orderDetailsModels;
    List<OrderDetailsModel> orderDetailsModels=new ArrayList<>();
    BranchAdapter branchAdapter;
    String deliverty,bname,baddress ,bmobile;
    int branchid ,orderID;
    String option;

    ArrayList<String> senddatatoinvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("   Confirm Order ");

        radioButton1 = findViewById(R.id.radiobtn1);
        radioButton2 = findViewById(R.id.radiobtn2);
        paytextviewmassage = findViewById(R.id.select_paymentsystem_textmassege_id);
        shipaddress = findViewById(R.id.shipping_addid);
        branchspinner = findViewById(R.id.spinner_delivary_optionid);
        paymentspinner = findViewById(R.id.spinner_payment_optionid);
        btnsubmint = findViewById(R.id.btnconfirmorderid);
        selectdelioption = findViewById(R.id.redioselecttextid);
        branchadd = findViewById(R.id.branch_add_id);
        cmobile = findViewById(R.id.cmobile_noid);

        deliverty="";
        btnsubmint.setOnClickListener(this);


        allbranchlist = new ArrayList<>();
        senddatatoinvoice = new ArrayList<>();

        //for spinner
        String[] payoption = {"Select","Cash On Delivery","Online Banking"};
        ArrayAdapter<CharSequence> payAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, payoption );
        payAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        paymentspinner.setAdapter(payAdapter);
        paymentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 option = (String) payAdapter.getItem(position);
                if(option.equals("Online Banking")){
                    paytextviewmassage.setVisibility(View.VISIBLE);
                    paytextviewmassage.setText("Please sent your payment at PUBLIC BANK, Account no 3145263227, if you have any queries please Contact +60 95135005");
                    paytextviewmassage.setTextColor(Color.WHITE);
                }else if(option.equals("Cash On Delivery")){
                    paytextviewmassage.setVisibility(View.VISIBLE);
                    paytextviewmassage.setText("Please give your payment to our service provider. if you have any queries please Contact +60 95135005");
                    paytextviewmassage.setTextColor(Color.WHITE);
                }else{
                    option="Select";
                    paytextviewmassage.setText("");
                    paytextviewmassage.setVisibility(View.INVISIBLE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {




            }
        });




      //  getbrnachList(this);



    }

    @SuppressLint("ResourceAsColor")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radiobtn1:
                if (checked)
                    selectdelioption.setText(R.string.selectbranch);
                     selectdelioption.setTextColor(Color.WHITE);
                     selectdelioption.setVisibility(View.VISIBLE);
                     shipaddress.setVisibility(View.INVISIBLE);
                     cmobile.setVisibility(View.INVISIBLE);
                     getbrnachList(this);
                     deliverty = radioButton1.getText().toString().trim();

                break;
            case R.id.radiobtn2:
                if (checked)
                    getbrnachList(this);
                    selectdelioption.setText(R.string.selecthome);
                    selectdelioption.setTextColor(Color.WHITE);
                    selectdelioption.setVisibility(View.VISIBLE);
                    shipaddress.setVisibility(View.VISIBLE);
                    cmobile.setVisibility(View.VISIBLE);
                   // branchadd.setVisibility(View.INVISIBLE);
                    deliverty = radioButton2.getText().toString().trim();

                break;
        }
    }


    private  void getbrnachList(Context context){

        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.get_branch_List();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.isSuccessful()){
                        BaseResponse baseResponse = response.body();
                        allbranchlist= baseResponse.getData();

                        String jsons = new Gson().toJson(allbranchlist);
                        Type listType = new TypeToken<BranchModel[]>() {}.getType();
                        branchsarraylist = new Gson().fromJson(jsons , listType);
                        branchAdapter =new BranchAdapter(context, android.R.layout.simple_spinner_item,branchsarraylist);
                        branchAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        branchspinner.setAdapter(branchAdapter);


                        branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                BranchModel branchModel = branchAdapter.getItem(position);
                                assert branchModel != null;
                                 branchadd .setText(
                                         "Please Collect Your Product form " + branchModel.getAddress() +
                                                 ". Contact Please " + branchModel.getMobile1()
                                 );
                                 branchadd.setVisibility(View.VISIBLE);
                                 branchadd.setTextColor(Color.WHITE);
                                 branchid=branchModel.getBranchID();
                                 bname = branchModel.getBname();
                                 baddress= branchModel.getAddress();
                                 bmobile = branchModel.getMobile1();



                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        Log.d("good", "onResponse: "+ branchsarraylist);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Log.d("Failure", "onResponse: "+ t.getMessage());


                }
            });

        }catch (Exception e){

        }


    }

    @Override
    public void onClick(View v) {

        if (v.getId()== R.id.btnconfirmorderid){

            if(deliverty.equals("")){
                Toast.makeText(this, "Please Choose a Delivery Option", Toast.LENGTH_LONG).show();

            }else if (option.equals("Select")){
                Toast.makeText(this, "Please Choose a Payment Option", Toast.LENGTH_LONG).show();

            } else{

                String shipadd;
                String mobile;
                String total="";
                Bundle bundle = getIntent().getExtras();
                if(bundle!= null) {
                    total = bundle.getString("Total", null);

                }
                if(radioButton2.isChecked()){
                    shipadd= shipaddress.getText().toString().trim();
                    mobile = cmobile.getText().toString().trim();
                    if(TextUtils.isEmpty(shipadd)){
                        shipaddress.setError("Please Enter Shipping Address");
                        shipaddress.requestFocus();
                    }else if(TextUtils.isEmpty(mobile)){
                        cmobile.setError("Please Enter Shipping Address");
                        cmobile.requestFocus();
                    }
                    else {

                        double grandtotal = Double.parseDouble(total)+5;
                        senddatatoinvoice.clear();

                        senddatatoinvoice.add(deliverty);
                        senddatatoinvoice.add(option);
                        senddatatoinvoice.add(bname);
                        senddatatoinvoice.add(baddress);
                        senddatatoinvoice.add(bmobile);
                        senddatatoinvoice.add(shipadd);
                        senddatatoinvoice .add(mobile);
                        senddatatoinvoice.add(total);
                        senddatatoinvoice.add("5.0");
                        senddatatoinvoice.add(String.valueOf(grandtotal));
                        Log.d("SEND Data", senddatatoinvoice.toString());
                        saveOrder(shipadd+","+ mobile +","+option);


                       // Toast.makeText(this, "Your Order submitted has been successful", Toast.LENGTH_LONG).show();

                    }

                }
                else{

                   // saveOrder(bname + baddress + bmobile);

                    senddatatoinvoice.clear();
                    senddatatoinvoice.add(deliverty);
                    senddatatoinvoice.add(option);
                    senddatatoinvoice.add(bname);
                    senddatatoinvoice.add(baddress);
                    senddatatoinvoice.add(bmobile);
                    senddatatoinvoice.add("----");
                    senddatatoinvoice.add("----");
                    senddatatoinvoice.add(total);
                    senddatatoinvoice.add("----");
                    senddatatoinvoice.add(total);

                    Log.d("SEND Data", senddatatoinvoice.toString());
                    saveOrder(bname+"," + baddress +","+ bmobile +","+option);


                   // Toast.makeText(this, "Your Order submitted has been successful", Toast.LENGTH_LONG).show();

                }





            }
        }

    }



    public void saveOrder(final String shippingAddress){


        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String orderdate= formeter.format(date);

        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname ="";
        if(cursor.moveToFirst()){
            uname = cursor.getString(1);
        }
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
             orderDetailsModels = bundle.getParcelableArrayList("myObj");

        }


       // OrderMasterModel orderMasterModel = new OrderMasterModel(666666667,"101",branchid,uname,shippingAddress,"1","1");

        OrderMasterModel orderMasterModel = new OrderMasterModel("101",branchid,uname,orderdate,shippingAddress,"Ordered","1",orderDetailsModels);
        orderMasterModel.setSsCreator(uname);
        orderMasterModel.setSsCreatedOn(orderdate);
        orderMasterModel.setSsModifier(uname);
        orderMasterModel.setSsModifiedOn(orderdate);
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        Gson gson = new Gson();
        String json = gson.toJson(orderMasterModel);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Call<String> submitorderCall = service.saveOrder(jsonObject);
        try {
            submitorderCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                  /*  //when send post request
                    //if error faced Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $
                    //Error say's you want to get result in String body.
                    //If you want to do this, Just add ScalarsConverterFactory.create() in your Retrofit.Builder.
                    //Use retrofit Implementation in app level build.gradle.
                    //implementation 'com.squareup.retrofit2:converter-scalars:2.1.0' update version
                    // .baseUrl(BASE_URL)
                    // .addConverterFactory(ScalarsConverterFactory.create())
                    // .addConverterFactory(GsonConverterFactory.create())
                    // .build();*/

                    if (response.isSuccessful()){
                        assert response.body() != null;
                        String um = response.body();
                        // Log.d("um",um);
                        BaseResponse baseResponse = objectMapperReadValue(um,BaseResponse.class);

                        Object row= baseResponse.getObj();

                        String jsons = new Gson().toJson(row);
                        Type listType = new TypeToken<OrderMasterModel>() {}.getType();
                        OrderMasterModel omm   = new Gson().fromJson(jsons , listType);
                        orderID = omm.getOrderId();
                        senddatatoinvoice.add(String.valueOf(orderID));


                        Intent intent = new Intent(ConfirmOrderActivity.this, OrderInvoiceActivity.class);
                        intent.putExtra("alldata", senddatatoinvoice);
                        startActivity(intent);
                        finish();
                        Toast.makeText(ConfirmOrderActivity.this, "Order submitted successful", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ConfirmOrderActivity.this, "Failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("failure",t.getMessage());

                }
            });



        }catch (Exception e){

            Log.d("exception",e.getMessage());

        }






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