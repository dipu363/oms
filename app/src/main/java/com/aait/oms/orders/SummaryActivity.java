package com.aait.oms.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton btnpre,btnnext;
    ListView lvSummary;
    TextView tvTotal;
    Double Total=0d;
    ArrayList<CardModel> productOrders = new ArrayList<>();
    SummaryAdapter  summaryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Order Summary");

        lvSummary = findViewById(R.id.summaryorderlistid);
        tvTotal = findViewById(R.id.summarytotalpaytextid);
        btnpre = findViewById(R.id.btnsummrypreid);
        btnnext = findViewById(R.id.btnsummrynextid);
        btnpre.setOnClickListener(this);
        btnnext.setOnClickListener(this);
        getOrderItemData();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getOrderItemData();
    }

    private void getOrderItemData() {

        productOrders.clear();
        Bundle extras = getIntent().getExtras();
        if(extras !=null )
        {
            String orderItems = extras.getString("orderItems",null);
            if(orderItems!=null && orderItems.length()>0)
            {
                try{
                    JSONArray jsonOrderItems = new JSONArray(orderItems);
                    for(int i=0;i<jsonOrderItems.length();i++)
                    {
                        JSONObject jsonObject = new JSONObject(jsonOrderItems.getString(i));

                        String l1code = jsonObject.getString("l1code");
                        String l2code = jsonObject.getString("l2code");
                        String l3code = jsonObject.getString("l3code");
                        String l4code = jsonObject.getString("l4code");
                        String salesrate = jsonObject.getString("salesrate");
                        String uomid = jsonObject.getString("uomid");
                        String productname = jsonObject.getString("productname");
                        String activeStatus = jsonObject.getString("activeStatus");
                        String ledgername = jsonObject.getString("ledgername");
                        int qty = jsonObject.getInt("qty");


                        CardModel products = new CardModel(l1code,l2code,l3code,l4code,salesrate,uomid,productname,activeStatus,ledgername,qty);
                        productOrders.add(products);

                        /* Calculate Total */
                        Total = Total + (products.getQty() * Double.parseDouble(products.getSalesrate()));
                    }

                    if(productOrders.size() > 0)
                    {

                        productOrders.size();
                         summaryAdapter = new SummaryAdapter(this,productOrders);
                        lvSummary.setAdapter(summaryAdapter);
                        tvTotal.setText("Order Total: "+Total);
                    }
                    else
                    {
                        showMessage("Empty");
                    }
                }
                catch (Exception e)
                {
                    showMessage(e.toString());
                }
            }

        }
    }

    public void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnsummrypreid:
                Intent intent1 = new Intent(SummaryActivity.this,CartActivity.class);
                startActivity(intent1);


                break;
            case R.id.btnsummrynextid:
                String tot = String.valueOf(Total);
                Intent intent2 = new Intent(SummaryActivity.this, ConfirmOrderActivity.class);
                intent2.putExtra("Total",tot);
                startActivity(intent2);
                break;
        }
    }
}