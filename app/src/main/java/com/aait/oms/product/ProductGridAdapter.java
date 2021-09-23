package com.aait.oms.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.ArrayList;
import java.util.List;

public class ProductGridAdapter extends BaseAdapter {

    Context mContext;
    List<ProductModel> productModels;
    List<ProductModel> itemsModelListFiltered;
    private final ArrayList<ProductModel> arraylist;

    public ProductGridAdapter(Context mContext, List<ProductModel> productModels) {
        this.mContext = mContext;
        this.productModels = productModels;
        this.itemsModelListFiltered = productModels;
        this.arraylist = new ArrayList<ProductModel>();
        this.arraylist.addAll(productModels);

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            /*  Object getrow =productModelList.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;*/
        ProductModel stok = itemsModelListFiltered.get(position);

        // for get serial no of list item
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < itemsModelListFiltered.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.productgridviewsample,null);

        }

        //findviewbyid
        TextView productname = convertView.findViewById(R.id.cardproductnameid);

//set data to view
        productname.setText(stok.getProductname());

        return convertView;
    }
}
