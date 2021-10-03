package com.aait.oms.product;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.product.common.CommonFunction;
import com.google.gson.Gson;

public class Product_Details_view_Activity extends AppCompatActivity {

    private  TextView prodname, prodcode,prodprice,proddetails ,prodstock;
    private ImageView prodimageview;
    private ImageButton addcard,favorite,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Products Details");

        prodname = findViewById(R.id.cardproductnameid);
        prodcode = findViewById(R.id.cardproductcodeCodeid);
        prodprice = findViewById(R.id.cardproductpriceid);
        proddetails = findViewById(R.id.cardproductdetailsid);
        prodstock = findViewById(R.id.cardproductinStockid);
        prodimageview = findViewById(R.id.productimageid);
        addcard = findViewById(R.id.cardproductAddtTocartbottonId);
        favorite = findViewById(R.id.productFavouritebottonId);
        feedback = findViewById(R.id.productfeedbackbottonId);

        Gson gson = new Gson();
        ProductModel prodmodel = gson.fromJson(getIntent().getStringExtra("product"), ProductModel.class);
   /*     CommonFunction commonFunction = new CommonFunction(this);
        long uodid = Math.round(Float.parseFloat(prodmodel.uomid));
        String stringid = String.valueOf(uodid);
        String uomname = commonFunction.getuomname(Integer.parseInt(stringid));*/

        prodname.setText(prodmodel.getProductname());
        prodcode.setText("Code : "+prodmodel.getL4code());
        prodprice.setText(" TK. "+prodmodel.getSalesrate());
        proddetails.setText(prodmodel.getLedgername());
        prodstock.setText("Stock Available  ");



        Gson gsonf = new Gson();
        //System.out.println(prodmodel.toString());

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