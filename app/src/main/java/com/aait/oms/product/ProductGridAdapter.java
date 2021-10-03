package com.aait.oms.product;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.common.CommonFunction;
import com.aait.oms.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ProductModel product = itemsModelListFiltered.get(position);

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
        TextView productumlcode = convertView.findViewById(R.id.cardproductUMLcodeid);
        TextView productprice = convertView.findViewById(R.id.cardproductpriceid);
/*        CommonFunction commonFunction = new CommonFunction(mContext);
        long uodid = Math.round(Float.parseFloat(product.uomid));
        String stringid = String.valueOf(uodid);
        String uomname = commonFunction.getuomname(Integer.parseInt(stringid));*/


//set data to view
        productname.setText(product.getProductname());
        productumlcode.setText(product.l4code);
        productprice.setText("TK. "+ product.getSalesrate());

        return convertView;
    }








}
