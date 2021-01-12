package com.aait.oms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.model.ProductModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    Context mContext;
    List<ProductModel> productModelList;

    public ProductAdapter(Context mContext, List<ProductModel> productModelList) {
        this.mContext = mContext;
        this.productModelList = productModelList;
    }

    @Override
    public int getCount() {
        return productModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return productModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object getrow =productModelList.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.supplier_samplelist_layout,null);

        }

        TextView productname = convertView.findViewById(R.id.supname);
        productname.setText(String.valueOf(t.get("productname")));
        return convertView;
    }
}
