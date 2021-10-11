package com.aait.oms.product;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aait.oms.R;
import com.aait.oms.product.common.CommonFunction;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Product_Details_view_Activity extends AppCompatActivity implements View.OnClickListener {

    private  TextView prodname, prodcode,prodprice,proddetails ,prodstock;
    private ImageView prodimageview;
    private ImageButton addcard,favorite,feedback;

    ProductModel prodmodel;
    SQLiteDB sqLiteDB;

    AppUtils appUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
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

        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);
        addcard.setOnClickListener(this);
        favorite.setOnClickListener(this);
        feedback.setOnClickListener(this);

        Gson gson = new Gson();
        prodmodel = gson.fromJson(getIntent().getStringExtra("product"), ProductModel.class);
        prodname.setText(prodmodel.getProductname());
        prodcode.setText("Code : "+prodmodel.getL4code());
        prodprice.setText(" TK. "+prodmodel.getSalesrate());
        proddetails.setText(prodmodel.getLedgername());
        prodstock.setText("Stock Available  ");

        byte[] bytes = Base64.decode(prodmodel.getPicByte(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        prodimageview.setImageBitmap(bitmap);

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.productFavouritebottonId:
               Cursor cursor = sqLiteDB.getallfavoriteProduct();
                List<String> productlist = new ArrayList<>();
               if (cursor.moveToFirst()){
                   productlist.add(cursor.getString(1));
                   for (int i = 0 ; i<productlist.size(); i++){
                       if (productlist.get(i) == prodmodel.l4code){

                           appUtils.appToast("This Product already added as your favorite Product ");
                       }
                       else {
                           sqLiteDB.insertProduct(prodmodel.getL4code());
                           appUtils.appToast("A New Product added as your favorite Product");
                       }

                   }
               }else {
                   sqLiteDB.insertProduct(prodmodel.getL4code());
                   appUtils.appToast("A New Product added as your favorite Product");
               }


            //    sqLiteDB.insertProduct(prodmodel.getL4code());


                break;
            case R.id.cardproductAddtTocartbottonId:
                break;
            case R.id.productfeedbackbottonId:
                break;

        }

    }
}