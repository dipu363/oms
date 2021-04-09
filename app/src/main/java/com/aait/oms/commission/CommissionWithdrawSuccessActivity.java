package com.aait.oms.commission;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.orders.OrderInvoiceActivity;
import com.aait.oms.ui.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommissionWithdrawSuccessActivity extends AppCompatActivity {

    TextView trandate,transecid,reqamount,charge,totalamount;
    FloatingActionButton fab;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_withdraw_success);
        fab = findViewById(R.id.btn_commissionfab_id);

        trandate = findViewById(R.id.com_req_dateid);
        transecid = findViewById(R.id.com_req_transacid);
        reqamount = findViewById(R.id.com_req__amountid);
        charge = findViewById(R.id.com_req__chargeid);
        totalamount = findViewById(R.id.com_req__totalamountid);
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String reqdate=formeter.format(date);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String tranid = bundle.getString("comtranid");
        String amount = bundle.getString("reqamount");
        String chargeamount = bundle.getString("charge");
        String totalreqamount = bundle.getString("total");
        trandate.setText(reqdate);
        transecid.setText(tranid);
        reqamount.setText(amount);
        charge.setText(chargeamount);
        totalamount.setText(totalreqamount);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommissionWithdrawSuccessActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });









    }
}