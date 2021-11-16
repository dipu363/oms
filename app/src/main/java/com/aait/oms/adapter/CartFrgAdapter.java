package com.aait.oms.adapter;

import android.content.Context;
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
import com.aait.oms.product.ProductModel;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;

import java.util.List;

public class CartFrgAdapter extends BaseAdapter {


    Context context;
    List<ProductModel> productModels;
    SQLiteDB sqLiteDB;
    AppUtils appUtils;

    public CartFrgAdapter(Context context, List<ProductModel> productModels) {
        this.context = context;
        this.productModels = productModels;
        sqLiteDB = new SQLiteDB(context);
        appUtils = new AppUtils(context);
    }


    @Override
    public int getCount() {
        return productModels.size();
    }

    @Override
    public Object getItem(int i) {
        return productModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProductModel product = productModels.get(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.cart_sample_layout, null);

        }


        TextView productname = view.findViewById(R.id.cart_fr_prod_nameid);
        TextView prodcode = view.findViewById(R.id.cart_fr_prod_codeid);
        TextView price = view.findViewById(R.id.cart_fr_prod_Priceid);
        TextView stock = view.findViewById(R.id.cart_fr_prod_stockstatusid);
        ImageView productImage = view.findViewById(R.id.cart_fr_prod_Imageid);
        ImageButton imageButton = view.findViewById(R.id.cart_fr_btn_delete_Id);
        productname.setText(product.getProductname());
        prodcode.setText(product.getL4code());
        stock.setText("Available");
        price.setText("TK. " + product.getSalesrate());

        byte[] bytes = Base64.decode(product.getPicByte(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        productImage.setImageBitmap(bitmap);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDB.deleteSingleProduct(product.getL4code());
                productModels.remove(i); // remove the item
                notifyDataSetChanged();

            }
        });


        return view;


    }
}
