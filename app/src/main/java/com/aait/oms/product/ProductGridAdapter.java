package com.aait.oms.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.aait.oms.R;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ProductGridAdapter extends BaseAdapter {

    Context mContext;
    List<StockViewModel> productModels;
    List<StockViewModel> itemsModelListFiltered;
    final ArrayList<StockViewModel> arraylist;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;

    public ProductGridAdapter(Context mContext, List<StockViewModel> productModels) {
        this.mContext = mContext;
        this.productModels = productModels;
        this.itemsModelListFiltered = productModels;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(productModels);
        sqLiteDB = new SQLiteDB(mContext);
        appUtils = new AppUtils(mContext);

    }

    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StockViewModel stockViewModel = itemsModelListFiltered.get(position);

        // for get serial no of list item
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < itemsModelListFiltered.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.productgridviewsample, null);

        }

        //findviewbyid
        TextView productname = convertView.findViewById(R.id.cardproductnameid);
        TextView productumlcode = convertView.findViewById(R.id.cardproductUMLcodeid);
        TextView productprice = convertView.findViewById(R.id.cardproductpriceid);
        ImageView productImage = convertView.findViewById(R.id.productimageid);
        ImageButton favoBotton = convertView.findViewById(R.id.productFavouritebottonId);
        ImageButton cartBotton = convertView.findViewById(R.id.cardproductAddtTocartbottonId);


//set data to view
        productname.setText(stockViewModel.getPname());
        productumlcode.setText(stockViewModel.getPcode());
        productprice.setText("TK. " + stockViewModel.getSalesRate());


//        byte[] bytes = Base64.decode(stockViewModel.getPicByte(), Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        productImage.setImageBitmap(bitmap);

        favoBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor1 = sqLiteDB.getSingleFavProduct(stockViewModel.getPcode());

                if (cursor1.moveToFirst()) {
                    appUtils.appToast("This product already added to favorite list");

                } else {
                    sqLiteDB.insertProduct(stockViewModel.getPcode());
                    appUtils.appToast("A New Product added as your favorite Product");
                }

            }
        });
        cartBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor1 = sqLiteDB.getSingleProduct(stockViewModel.getPcode());

                if (cursor1.moveToFirst()) {
                    appUtils.appToast("This product already added to your cart");

                } else {
                    sqLiteDB.insertCardProduct(stockViewModel.getPcode());
                    appUtils.appToast("A New Product added in your Cart");
                    Cursor c = sqLiteDB.getAllCardProduct();
                }
            }
        });
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String product = gson.toJson(stockViewModel);
                Intent intent = new Intent(mContext, Product_Details_view_Activity.class);
                intent.putExtra("product", product);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }


}
