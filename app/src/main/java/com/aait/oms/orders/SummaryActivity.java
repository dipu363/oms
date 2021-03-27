package com.aait.oms.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton btnpre,btnnext;
    ListView lvSummary;
    TextView tvTotal;
    Double Total=0d;
    ArrayList<CardModel> productOrders = new ArrayList<>();
    ArrayList<OrderDetailsModel> orderDetailsModels = new ArrayList<>();

    SummaryAdapter  summaryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Order Summary");

        lvSummary = findViewById(R.id.summaryorderlistid);
        tvTotal = findViewById(R.id.summarytotalpaytextid);
        btnnext = findViewById(R.id.btnsummrynextid);
        btnnext.setOnClickListener(this);
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
                        float price= qty* Float.parseFloat(salesrate);

                         OrderDetailsModel orderDetailsModel= new OrderDetailsModel();
                         orderDetailsModel.setL4Code(l4code);
                         orderDetailsModel.setQty(qty);
                         orderDetailsModel.setRate(Float.parseFloat(salesrate));
                         orderDetailsModel.setItemTotal(price);
                         orderDetailsModels.add(orderDetailsModel);


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
                        showMessage("Item quantity Not found");
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

            case R.id.btnsummrynextid:
                String tot = String.valueOf(Total);
                if(tot.equals("0.0")){
                    Toast.makeText(this,"please Enter Quantity at lest 1 item",Toast.LENGTH_LONG).show();
                }else {

                    Intent intent2 = new Intent(SummaryActivity.this, ConfirmOrderActivity.class);
                    intent2.putExtra("myObj", orderDetailsModels);
                    intent2.putExtra("Total",tot);
                    startActivity(intent2);
                }

                break;
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