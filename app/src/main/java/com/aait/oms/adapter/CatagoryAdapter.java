package com.aait.oms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.model.ProdCatagoryModel;
import com.aait.oms.model.ProductModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class CatagoryAdapter extends BaseAdapter {
    Context mContext;
    List<ProdCatagoryModel> prodCatagoryModels;

    public CatagoryAdapter(Context mContext, List<ProdCatagoryModel> prodCatagoryModels) {
        this.mContext = mContext;
        this.prodCatagoryModels = prodCatagoryModels;
    }

    @Override
    public int getCount() {
        return prodCatagoryModels.size();
    }

    @Override
    public Object getItem(int position) {
        return prodCatagoryModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object getrow =prodCatagoryModels.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.support_simple_spinner_dropdown_item,null);

        }



        TextView productname = convertView.findViewById(R.id.supname);
        productname.setText(String.valueOf(t.get("productname")));
        return convertView;


    }
}
