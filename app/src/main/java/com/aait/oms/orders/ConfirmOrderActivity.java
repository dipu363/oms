package com.aait.oms.orders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.branch.BranchAdapter;
import com.aait.oms.branch.BranchModel;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductInGridViewActivity;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.CommonFunctions;
import com.aait.oms.util.SQLiteDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity implements View.OnClickListener, CommonFunctions {
    TextView selectdelioption, branchadd, paytextviewmassage;
    RadioButton radioButton1, radioButton2;
    ImageButton btnMap;
    EditText shipaddress, cmobile;
    Spinner branchspinner, paymentspinner;
    Button btnsubmint, btnContinue, btnInvoice;
    List<BranchModel> allbranchlist;
    BranchModel[] branchsarraylist;
    List<OrderDetailsModel> orderDetailsModels = new ArrayList<>();
    BranchAdapter branchAdapter;
    String deliverty, bname, baddress, bmobile;
    int branchid, orderID;
    String option;
    ArrayList<String> senddatatoinvoice;
    FusedLocationProviderClient fusedLocationProviderClient;
    AppUtils appUtils;
    Dialog fullScreen_dialog_1;
    View fullScreenView_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        btnMap = findViewById(R.id.btn_map_info);

        deliverty = "";
        btnsubmint.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        allbranchlist = new ArrayList<>();
        senddatatoinvoice = new ArrayList<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        appUtils = new AppUtils(this);

        fullScreenView_1 = getLayoutInflater().inflate(R.layout.full_screen_dailog_1, null);
        fullScreen_dialog_1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        fullScreen_dialog_1.setContentView(fullScreenView_1);
        btnContinue = fullScreenView_1.findViewById(R.id.dialog_btn_continue);
        btnInvoice = fullScreenView_1.findViewById(R.id.dialog_btn_invoice);
        btnContinue.setOnClickListener(this);
        btnInvoice.setOnClickListener(this);

        //for spinner
        String[] payoption = {"Select", "Cash On Delivery", "Online Banking"};
        ArrayAdapter<CharSequence> payAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, payoption);
        payAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        paymentspinner.setAdapter(payAdapter);
        paymentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = (String) payAdapter.getItem(position);
                if (option.equals("Online Banking")) {
                    paytextviewmassage.setVisibility(View.VISIBLE);
                    paytextviewmassage.setText("Please sent your payment at PUBLIC BANK, Account no 3145263227, if you have any queries please Contact +60 143043600");
                    //paytextviewmassage.setTextColor(Color.WHITE);
                } else if (option.equals("Cash On Delivery")) {
                    paytextviewmassage.setVisibility(View.VISIBLE);
                    paytextviewmassage.setText("Please give your payment to our service provider. if you have any queries please Contact +60 143043600");
                    //   paytextviewmassage.setTextColor(Color.WHITE);
                } else {
                    option = "Select";
                    paytextviewmassage.setText("");
                    paytextviewmassage.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @SuppressLint("ResourceAsColor")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radiobtn1:
                if (checked)
                    selectdelioption.setText(R.string.selectbranch);
                selectdelioption.setVisibility(View.VISIBLE);
                shipaddress.setVisibility(View.INVISIBLE);
                cmobile.setVisibility(View.INVISIBLE);
                getbrnachList(this);
                deliverty = radioButton1.getText().toString().trim();

                break;
            case R.id.radiobtn2:
                if (checked)
                    getbrnachList(this);
                getCurrrentLocation();
                selectdelioption.setText(R.string.selecthome);
                selectdelioption.setVisibility(View.VISIBLE);
                deliverty = radioButton2.getText().toString().trim();

                break;
        }
    }


    private void getbrnachList(Context context) {

        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        try {
            Call<BaseResponse> call = service.get_branch_List();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        allbranchlist = baseResponse.getData();
                        String jsons = new Gson().toJson(allbranchlist);
                        Type listType = new TypeToken<BranchModel[]>() {
                        }.getType();
                        branchsarraylist = new Gson().fromJson(jsons, listType);
                        branchAdapter = new BranchAdapter(context, android.R.layout.simple_spinner_item, branchsarraylist);
                        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        branchspinner.setAdapter(branchAdapter);
                        branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                BranchModel branchModel = branchAdapter.getItem(position);
                                assert branchModel != null;
                                branchadd.setText(
                                        "Please Collect Your Product form " + branchModel.getAddress() +
                                                ". Contact Please " + branchModel.getMobile1()
                                );
                                branchadd.setVisibility(View.VISIBLE);
                                branchid = branchModel.getBranchID();
                                bname = branchModel.getBname();
                                baddress = branchModel.getAddress();
                                bmobile = branchModel.getMobile1();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                    Log.d("Failure", "onResponse: " + t.getMessage());
                }
            });

        } catch (Exception ignored) {

        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnconfirmorderid) {

            if (deliverty.equals("")) {
                Toast.makeText(this, "Please Choose a Delivery Option", Toast.LENGTH_LONG).show();

            } else if (option.equals("Select")) {
                Toast.makeText(this, "Please Choose a Payment Option", Toast.LENGTH_LONG).show();

            } else {

                String shipadd;
                String mobile;
                String total = "";
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    total = bundle.getString("Total", null);

                }
                if (radioButton2.isChecked()) {
                    shipadd = shipaddress.getText().toString().trim();
                    mobile = cmobile.getText().toString().trim();
                    if (TextUtils.isEmpty(shipadd)) {
                        shipaddress.setError("Please Enter Shipping Address");
                        shipaddress.requestFocus();
                    } else if (TextUtils.isEmpty(mobile)) {
                        cmobile.setError("Please Enter Shipping Address");
                        cmobile.requestFocus();
                    } else {

                        double grandtotal = Double.parseDouble(total) + 5;
                        senddatatoinvoice.clear();
                        senddatatoinvoice.add(deliverty);
                        senddatatoinvoice.add(option);
                        senddatatoinvoice.add(bname);
                        senddatatoinvoice.add(baddress);
                        senddatatoinvoice.add(bmobile);
                        senddatatoinvoice.add(shipadd);
                        senddatatoinvoice.add(mobile);
                        senddatatoinvoice.add(total);
                        senddatatoinvoice.add("5.0");
                        senddatatoinvoice.add(String.valueOf(grandtotal));
                        Log.d("SEND Data", senddatatoinvoice.toString());
                        saveOrder(deliverty + ", " + shipadd + ", " + mobile + ", " + option);
                    }

                } else {

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
                    saveOrder(deliverty + ", " + bname + ", " + baddress + ", " + bmobile + ", " + option);
                }
            }
        } else if (v.getId() == R.id.dialog_btn_continue) {
            Intent intent = new Intent(ConfirmOrderActivity.this, ProductInGridViewActivity.class);
            startActivity(intent);
            finish();
            fullScreen_dialog_1.dismiss();
        } else if (v.getId() == R.id.dialog_btn_invoice) {
            Intent intent = new Intent(ConfirmOrderActivity.this, OrderInvoiceActivity.class);
            intent.putExtra("alldata", senddatatoinvoice);
            startActivity(intent);
            finish();
            fullScreen_dialog_1.dismiss();

        }else if (v.getId() == R.id.btn_map_info){
            Intent intent = new Intent(this,MapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("address",baddress);
            startActivity(intent);


        }

    }


    public void saveOrder(final String shippingAddress) {


        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        String orderdate = formeter.format(date);

        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname = "";
        if (cursor.moveToFirst()) {
            uname = cursor.getString(1);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderDetailsModels = bundle.getParcelableArrayList("myObj");
        }


        // OrderMasterModel orderMasterModel = new OrderMasterModel(666666667,"101",branchid,uname,shippingAddress,"1","1");

        OrderMasterModel orderMasterModel = new OrderMasterModel("101", branchid, uname, orderdate, shippingAddress, "Ordered", "1", orderDetailsModels);
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
                     //Error say's you want to get result in String body .
                     //Or this problem occur for response type .
                     // solution : please see your response type in your api
                    //If you want to do this, Just add ScalarsConverterFactory.create() in your Retrofit.Builder.
                    //Use retrofit Implementation in app level build.gradle.
                    //implementation 'com.squareup.retrofit2:converter-scalars:2.1.0' update version
                    // .baseUrl(BASE_URL)
                    // .addConverterFactory(ScalarsConverterFactory.create())
                    // .addConverterFactory(GsonConverterFactory.create())
                    // .build();*/

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String um = response.body();
                        BaseResponse baseResponse = objectMapperReadValue(um, BaseResponse.class);
                        Object row = baseResponse.getObj();
                        String jsons = new Gson().toJson(row);
                        Type listType = new TypeToken<OrderMasterModel>() {
                        }.getType();
                        OrderMasterModel omm = new Gson().fromJson(jsons, listType);
                        orderID = omm.getOrderId();
                        senddatatoinvoice.add(String.valueOf(orderID));
                        fullScreen_dialog_1.show();
                    }else{
                       appUtils.appToast("Order Submit Failed");
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ConfirmOrderActivity.this, "Failure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("failure", "Save Fail"+t.getMessage());

                }
            });

        } catch (Exception e) {

            Log.d("exception", e.getMessage());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void getCurrrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ConfirmOrderActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        } else {
            SQLiteDB sqLiteDB = new SQLiteDB(this);
            Cursor cursor = sqLiteDB.getUserInfo();
            String usermobile = "";
            if (cursor.moveToFirst()) {
                usermobile = cursor.getString(1);
            }

            String finalUsermobile = usermobile;
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    //initial location
                    Location location = task.getResult();
                    if (location != null) {

                        try {

                            //initial address list
                            Geocoder geocoder = new Geocoder(ConfirmOrderActivity.this,
                                    Locale.getDefault());
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            shipaddress.setVisibility(View.VISIBLE);
                            shipaddress.setText(addressList.get(0).getAddressLine(0));
                            cmobile.setVisibility(View.VISIBLE);
                            cmobile.setText(finalUsermobile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
        }
    }
}