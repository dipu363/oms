package com.aait.oms.commission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.util.CommonFunctions;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.RoundingMode;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionWithdrawActivity extends AppCompatActivity implements View.OnClickListener, CommonFunctions {

    EditText transectionamount, transectionpassword;
    TextView massagetextview, balancetextview;
    Button btn_send, btn_next1;
    float ablebalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_withdraw);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("  Commission Withdraw");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        transectionamount = findViewById(R.id.transection_amount_edittextid);
        transectionpassword = findViewById(R.id.transection_password_edittextid);
        massagetextview = findViewById(R.id.withdrawmassegeid);
        balancetextview = findViewById(R.id.withdrawbalancetextid);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String bal = bundle.getString("balance");
            ablebalance = Float.parseFloat(bal);

            double doubleResult = Float.valueOf(ablebalance);
            DecimalFormat df = new DecimalFormat("#.00");
            df.setRoundingMode(RoundingMode.CEILING);
            String v = df.format(doubleResult);
            balancetextview.setText("Available Amount RM " + v);
        }

        btn_send = findViewById(R.id.withdraw_btn_send);
        btn_next1 = findViewById(R.id.withdraw_btn_next1);
        btn_next1.setOnClickListener(this);
        btn_send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.withdraw_btn_next1:
                String req_amount = transectionamount.getText().toString();
                if (TextUtils.isEmpty(req_amount)) {
                    transectionamount.setError("Please Enter Request amount");
                    transectionamount.requestFocus();
                } else {
                    float reqbalance = Float.parseFloat(req_amount);
                    float tax = reqbalance * 2 / 100;
                    float totalrequst = reqbalance + tax;
                    if (totalrequst > ablebalance) {
                        transectionamount.setError(" You have not Enough amount available");
                        transectionamount.requestFocus();

                    } else {
                        transectionamount.setVisibility(View.INVISIBLE);
                        btn_next1.setVisibility(View.INVISIBLE);
                        transectionpassword.setVisibility(View.VISIBLE);
                        btn_send.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case R.id.withdraw_btn_send:
                String pass = transectionpassword.getText().toString().trim();
                if (TextUtils.isEmpty(pass)) {
                    transectionpassword.setError("Enter Your Password");
                    transectionpassword.requestFocus();

                } else {

                    SQLiteDB sqLiteDB = new SQLiteDB(this);
                    Cursor cursor = sqLiteDB.getUserInfo();

                    if (cursor != null && cursor.moveToFirst()) {
                        String password = cursor.getString(3);
                        if (password.equals(pass)) {
                            saveWithdrawCommission();
                        } else {
                            transectionpassword.setText("");
                            transectionpassword.setError("Enter Your Password");
                            transectionpassword.requestFocus();
                            Toast.makeText(CommissionWithdrawActivity.this, "your password not mach", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                break;
        }
    }


    private void saveWithdrawCommission() {
        String username = "";
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("yyyy-MM-dd");
        String reqdate = formeter.format(date);

        long trsecid = date.getTime();
        String transecid = String.valueOf(trsecid).substring(4, 13);

        String withtype = "Cash";
        float req_amount = Float.parseFloat(transectionamount.getText().toString().trim());
        float tax = req_amount * 2 / 100;
        float totalrequestamount = req_amount + tax;


        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(1);
        }


        CommissionWithdrawModel commissionWithdrawModel = new CommissionWithdrawModel(username, reqdate, totalrequestamount, withtype, "1", req_amount, tax, transecid);
        CommissionService service = ApiClient.getRetrofit().create(CommissionService.class);
        Gson gson = new Gson();
        String json = gson.toJson(commissionWithdrawModel);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Call<String> call = service.saveCommissionWithdrawRequest(jsonObject);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    String um = response.body();
                    // Log.d("um",um);
                    BaseResponse baseResponse = objectMapperReadValue(um, BaseResponse.class);
                    Object row = baseResponse.getObj();
                    String jsons = new Gson().toJson(row);
                    Type listType = new TypeToken<CommissionWithdrawModel>() {
                    }.getType();
                    CommissionWithdrawModel comwithmodel = new Gson().fromJson(jsons, listType);
                    String comtranid = comwithmodel.transectionId;
                    String reqamount = String.valueOf(comwithmodel.getAfterTexBalance());
                    String charge = String.valueOf(comwithmodel.getTexAmount());
                    String total = String.valueOf(comwithmodel.getTransAmount());

                    Intent intent = new Intent(CommissionWithdrawActivity.this, CommissionWithdrawSuccessActivity.class);
                    intent.putExtra("comtranid", comtranid);
                    intent.putExtra("reqamount", reqamount);
                    intent.putExtra("charge", charge);
                    intent.putExtra("total", total);
                    startActivity(intent);
                    finish();

                    Log.d("Success", "Balance Withdraw Request Success");
                    Log.d("save data", um);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Failure", "Balance Withdraw Request Fail" + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}