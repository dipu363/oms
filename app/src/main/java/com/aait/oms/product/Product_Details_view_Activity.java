package com.aait.oms.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aait.oms.R;
import com.aait.oms.orders.CartActivity;
import com.aait.oms.ui.LogInActivity;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Product_Details_view_Activity extends AppCompatActivity implements View.OnClickListener {

    Button addToCart, removeCart;
    StockViewModel prodmodel;
    SQLiteDB sqLiteDB;
    ArrayList<String> cardList;
    AppUtils appUtils;
    TextView prodname, prodcode, prodprice, proddetails, prodstock;
    ImageView prodimageview;
    ImageButton favorite, feedback;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("  Products Details");

        prodname = findViewById(R.id.cardproductnameid);
        prodcode = findViewById(R.id.cardproductcodeCodeid);
        prodprice = findViewById(R.id.cardproductpriceid);
        proddetails = findViewById(R.id.cardproductdetailsid);
        prodstock = findViewById(R.id.cardproductinStockid);
        prodimageview = findViewById(R.id.productimageid);
        favorite = findViewById(R.id.productFavouritebottonId);
        feedback = findViewById(R.id.productfeedbackbottonId);
        addToCart = findViewById(R.id.add_to_cart_id);
        removeCart = findViewById(R.id.remove_cart_prod_id);

        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);
        addToCart.setOnClickListener(this);
        removeCart.setOnClickListener(this);
        favorite.setOnClickListener(this);
        feedback.setOnClickListener(this);

        Gson gson = new Gson();
        prodmodel = gson.fromJson(getIntent().getStringExtra("product"), StockViewModel.class);
        prodname.setText(prodmodel.getPname());
        prodcode.setText("Code : " + prodmodel.getPcode());
        prodprice.setText(" TK. " + prodmodel.getSalesRate());
        proddetails.setText(prodmodel.getProdDetails());
        prodstock.setText("Stock Available  ");

/*        byte[] bytes = Base64.decode(prodmodel.getPicByte(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        prodimageview.setImageBitmap(bitmap);*/

        Cursor cursor = sqLiteDB.getAllCardProduct();
        cardList = new ArrayList<>();


        if (cursor.moveToFirst()) {
            do {
                cardList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Cursor c = sqLiteDB.getSingleProduct(prodmodel.getPcode());
        if (c.moveToFirst()) {
            addToCart.setVisibility(View.INVISIBLE);
            removeCart.setVisibility(View.VISIBLE);
        }


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.productFavouritebottonId:

                String prodId = prodmodel.getPcode();
                Cursor cursor1 = sqLiteDB.getSingleFavProduct(prodId);

                if (cursor1.moveToFirst()) {
                    appUtils.appToast("This product already added to favorite list");

                } else {
                    sqLiteDB.insertProduct(prodId);
                    appUtils.appToast("A New Product added as your favorite Product");
                }

                break;
            case R.id.productfeedbackbottonId:
                break;
            case R.id.add_to_cart_id:
                String pId = prodmodel.getPcode();
                sqLiteDB.insertCardProduct(pId);
                addToCart.setVisibility(View.INVISIBLE);
                removeCart.setVisibility(View.VISIBLE);
                appUtils.appToast("A New Product added in your Card");
                Cursor cursor = sqLiteDB.getAllCardProduct();

                if (cursor.moveToFirst()) {
                    cardList.clear();
                    do {
                        cardList.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }
                invalidateOptionsMenu();
                break;
            case R.id.remove_cart_prod_id:
                String productId = prodmodel.getPcode();
                sqLiteDB.deleteSingleProduct(productId);
                addToCart.setVisibility(View.VISIBLE);
                removeCart.setVisibility(View.INVISIBLE);
                appUtils.appToast("Remove a Product From your Card");
                Cursor c = sqLiteDB.getAllCardProduct();
                if (c.moveToFirst()) {
                    cardList.clear();
                    do {
                        cardList.add(c.getString(0));
                    } while (c.moveToNext());
                }
                invalidateOptionsMenu();
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.tabCartId);
        View actionView = menuItem.getActionView();
        TextView textView = actionView.findViewById(R.id.cart_badge_text_view);
        textView.setText(String.valueOf(cardList.size()));
        actionView.setOnClickListener(view -> onOptionsItemSelected(menuItem));
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.tabCartId) {

            int loginstatus = 0;
            SQLiteDB sqLiteDB = new SQLiteDB(this);
            Cursor cursor = sqLiteDB.getUserInfo();
            if (cursor.moveToFirst()) {
                loginstatus = cursor.getInt(4);
            }

            if (loginstatus == 1) {
                int cartsize = cardList.size();
                if (cartsize > 0) {
                    Intent intent = new Intent(Product_Details_view_Activity.this, CartActivity.class);
                    intent.putExtra("SQ", "SQ");
                    startActivity(intent);
                } else {
                    appUtils.appToast("Cart is Empty");
                }
            } else {
                Intent intent = new Intent(Product_Details_view_Activity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }


        }
        return super.onOptionsItemSelected(item);
    }
}