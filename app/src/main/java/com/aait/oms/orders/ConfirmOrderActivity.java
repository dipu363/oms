package com.aait.oms.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.android.gms.common.internal.Asserts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity implements View.OnClickListener {
    TextView selectdelioption, branchadd;
    RadioButton radioButton1,radioButton2;
    EditText shipaddress, cmobile;
    Spinner branchspinner;
    Button btnsubmint;
    List<BranchModel> allbranchlist;
    BranchModel[] branchsarraylist;
    BranchAdapter branchAdapter;
    String deliverty,bname,baddress ,bmobile;

    ArrayList<String> senddatatoinvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Confirm Order ");

        radioButton1 = findViewById(R.id.radiobtn1);
        radioButton2 = findViewById(R.id.radiobtn2);
        shipaddress = findViewById(R.id.shipping_addid);
        branchspinner = findViewById(R.id.spinner_delivary_optionid);
        btnsubmint = findViewById(R.id.btnconfirmorderid);
        selectdelioption = findViewById(R.id.redioselecttextid);
        branchadd = findViewById(R.id.branch_add_id);
        cmobile = findViewById(R.id.cmobile_noid);

        deliverty="";
        btnsubmint.setOnClickListener(this);


        allbranchlist = new ArrayList<>();
        senddatatoinvoice = new ArrayList<>();




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
                     selectdelioption.setTextColor(Color.BLACK);
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
                    selectdelioption.setTextColor(Color.RED);
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
                                 branchadd.setTextColor(Color.RED);
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

            }else{

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
                        senddatatoinvoice.add(bname);
                        senddatatoinvoice.add(baddress);
                        senddatatoinvoice.add(bmobile);
                        senddatatoinvoice.add(shipadd);
                        senddatatoinvoice .add(mobile);
                        senddatatoinvoice.add(total);
                        senddatatoinvoice.add("5.0");
                        senddatatoinvoice.add(String.valueOf(grandtotal));

                        Log.d("SEND Data", senddatatoinvoice.toString());

                        Intent intent = new Intent(ConfirmOrderActivity.this, OrderInvoiceActivity.class);
                        intent.putExtra("alldata", senddatatoinvoice);
                        startActivity(intent);
                        Toast.makeText(this, "Your Order submitted has been successful", Toast.LENGTH_LONG).show();

                    }

                }
                else{


                    senddatatoinvoice.clear();
                    senddatatoinvoice.add(deliverty);
                    senddatatoinvoice.add(bname);
                    senddatatoinvoice.add(baddress);
                    senddatatoinvoice.add(bmobile);
                    senddatatoinvoice.add("----");
                    senddatatoinvoice.add("----");
                    senddatatoinvoice.add(total);
                    senddatatoinvoice.add("----");
                    senddatatoinvoice.add(total);

                    Log.d("SEND Data", senddatatoinvoice.toString());

                    Intent intent = new Intent(ConfirmOrderActivity.this, OrderInvoiceActivity.class);
                    intent.putExtra("alldata", senddatatoinvoice);
                    startActivity(intent);
                    Toast.makeText(this, "Your Order submitted has been successful", Toast.LENGTH_LONG).show();

                }





            }
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