package com.aait.oms.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.ui.HomeActivity;
import com.aait.oms.util.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

public class OrderInvoiceActivity extends AppCompatActivity {


    TextView orderdate,orderid, cusid,deltype,paytype,brnchname,baddress,bphone,saddress,cusphon,ordertotal,deliberycharge,totalamount;
    ArrayList<String> alldata = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_invoice);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Invoice ");
        orderdate = findViewById(R.id.order_invoice_dateid);
        fab = findViewById(R.id.btn_invoicefab_id);
        orderid = findViewById(R.id.order_invoice_orderid);
        cusid = findViewById(R.id.order_invoice_customerid);
        deltype = findViewById(R.id.order_invoice_deliverytypeid);
        paytype = findViewById(R.id.order_invoice_paymenttypeid);
        brnchname = findViewById(R.id.order_invoice_Branchnameid);
        baddress = findViewById(R.id.order_invoice_Branchaddressid);
        bphone = findViewById(R.id.order_invoice_phoneid);
        saddress = findViewById(R.id.order_invoice_shippingaddressid);
        cusphon = findViewById(R.id.order_invoice_cus_phoneid);
        ordertotal = findViewById(R.id.order_invoice_ordertotalid);
        deliberycharge = findViewById(R.id.order_invoice_delivarichrgeid);
        totalamount = findViewById(R.id.order_invoice_totalamountid);
    /*    FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
       String username = firebaseUser.getPhoneNumber();*/
       /* int max = 31999999;
        int min =21999999;
        int random = (int )(Math.random() * max + min);*/
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname ="";
        if(cursor.moveToFirst()){
            uname = cursor.getString(1);
        }


        Date date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("dd/MM/yyyy");
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
             alldata = bundle.getStringArrayList("alldata");

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderInvoiceActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        orderdate.setText(formeter.format(date));
        orderid .setText(String.valueOf(alldata.get(10)));
        cusid.setText(uname);
        deltype.setText(alldata.get(0));
        paytype.setText(alldata.get(1));
        brnchname.setText(alldata.get(2));
        baddress.setText(alldata.get(3));
        bphone.setText(alldata.get(4));
        saddress.setText(alldata.get(5));
        cusphon.setText(alldata.get(6));
        ordertotal.setText(alldata.get(7));
        deliberycharge.setText(alldata.get(8));
        totalamount.setText(alldata.get(9));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            Intent intent = new Intent(OrderInvoiceActivity.this,OrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}