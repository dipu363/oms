package com.aait.oms.product;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

    private  TextView prodname, prodcode,prodprice,proddetails ,prodstock ,textcardlist;
    private ImageView prodimageview;
    private ImageButton addcard,favorite,feedback;

    ProductModel prodmodel;
    SQLiteDB sqLiteDB;
    ArrayList<String> cardList ;
    AppUtils appUtils;

    @SuppressLint("SetTextI18n")
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
        textcardlist = findViewById(R.id.cardlistproductid);
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

        Cursor cursor = sqLiteDB.getAllCardProduct();
        cardList = new ArrayList<>();


        if (cursor.moveToFirst()){
          do {
              cardList.add(cursor.getString(0));
          }  while (cursor.moveToNext());
        }
        textcardlist.setText(String.valueOf(cardList.size()));

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

                String prodId = prodmodel.l4code;
               Cursor cursor1 = sqLiteDB.getSingleFavProduct(prodId);

                if (cursor1.moveToFirst()) {
                    appUtils.appToast("This product already added to favorite list");

                }else {
                    sqLiteDB.insertProduct(prodId);
                    appUtils.appToast("A New Product added as your favorite Product");
                }

                break;
            case R.id.cardproductAddtTocartbottonId:
                String pId = prodmodel.l4code;
                Cursor cursor2 = sqLiteDB.getSingleProduct(pId);

                if (cursor2.moveToFirst()) {
                    appUtils.appToast("This product already added to your Card");

                }else {
                    sqLiteDB.insertCardProduct(pId);

                    Cursor cursor = sqLiteDB.getAllCardProduct();
                    cardList.clear();
                    if (cursor.moveToFirst()){
                        do {
                            cardList.add(cursor.getString(0));
                        }  while (cursor.moveToNext());
                    }
                    textcardlist.setText(String.valueOf(cardList.size()));
                    appUtils.appToast("A New Product added in your Card");
                }

                break;
            case R.id.productfeedbackbottonId:
                break;

        }

    }
}