package com.aait.oms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.product.ProductModel;
import com.aait.oms.product.StockViewModel;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;

import java.util.List;

public class FavoriteProductAdapter extends BaseAdapter {
    Context context;
    List<StockViewModel> productList;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;

    public FavoriteProductAdapter(Context context, List<StockViewModel> productList) {
        this.context = context;
        this.productList = productList;
        sqLiteDB = new SQLiteDB(context);
        appUtils = new AppUtils(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        StockViewModel product = productList.get(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.favorite_product_sample_layout, null);

        }
        TextView productname = view.findViewById(R.id.fav_prod_nameid);
        TextView prodcode = view.findViewById(R.id.fav_prod_codeid2);
        TextView price = view.findViewById(R.id.fav_prod_Priceid);
        TextView stock = view.findViewById(R.id.fav_prod_stockstatusid);
        ImageView productImage = view.findViewById(R.id.fav_prod_Imageid);
        Button btn_add = view.findViewById(R.id.fav_prod__add_to_cart_id);
        ImageButton imageButton = view.findViewById(R.id.fab_btn_delete_Id);
        productname.setText(product.getProdName());
        prodcode.setText(product.getPcode());
        stock.setText(String.format("In Stock :%s %s", product.getCurrentQty(), product.getUomName()));
        price.setText(String.format(" RM :%s", product.getSalesRate()));

        byte[] bytes = Base64.decode(product.getPicByte(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        productImage.setImageBitmap(bitmap);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor1 = sqLiteDB.getSingleProduct(product.getPcode());

                if (cursor1.moveToFirst()) {
                    appUtils.appToast("This product already added to your cart");

                } else {

                    sqLiteDB.insertCardProduct(product.getPcode());
                    productList.remove(i); // remove the item
                    notifyDataSetChanged();
                    appUtils.appToast("A New Product added in your Cart");
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDB.deleteFavSingleProduct(product.getPcode());
                productList.remove(i); // remove the item
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
