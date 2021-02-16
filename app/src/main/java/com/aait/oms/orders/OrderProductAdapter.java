package com.aait.oms.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.aait.oms.R;
import com.aait.oms.product.ProductModel;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends BaseAdapter{


    Context mContext;
    List<ProductModel> productModels;
    List<ProductModel> itemsModelListFiltered;
    private ArrayList<ProductModel> arraylist;

    public OrderProductAdapter(Context mContext, List<ProductModel> productModels){
        this.mContext = mContext;
        this.productModels = productModels;
        this.itemsModelListFiltered = productModels;
        this.arraylist = new ArrayList<ProductModel>();
        this.arraylist.addAll(productModels);
    }

    @Override
    public int getCount() {
        return productModels.size();
    }

    @Override
    public Object getItem(int position) {
        return productModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductModel prodmodel = productModels.get(position);

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cardlist,null);

        }

        TextView productname = convertView.findViewById(R.id.orderlistproduct_name);
        TextView price = convertView.findViewById(R.id.orderlist_product_price);
        productname.setText(prodmodel.getProductname());
        price.setText(prodmodel.getSalesrate());
        return convertView;
    }


}
