package com.aait.oms.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersAdapter extends BaseAdapter {

    Context context;
    List<OrderMasterModel> myorderlist;

    public MyOrdersAdapter(Context context, List<OrderMasterModel> myorderlist) {
        this.context = context;
        this.myorderlist = myorderlist;
    }

    @Override
    public int getCount() {
        return myorderlist.size();
    }

    @Override
    public Object getItem(int position) {
        return myorderlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderMasterModel orderMasterModel= myorderlist.get(position);
// for get serial no of list item
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < myorderlist.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }


        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.my_orders_semple_layout,parent,false);
        }
        TextView orderserialid = convertView.findViewById(R.id.my_orders_order_serialid);
        TextView orderid = convertView.findViewById(R.id.my_orders_orderid);
        TextView orderdate = convertView.findViewById(R.id.my_orders_order_date);
        TextView orderstatus = convertView.findViewById(R.id.my_orders_order_status);
        TextView shipaddress = convertView.findViewById(R.id.my_orders_delivery_ship_add);
        TextView delstatus = convertView.findViewById(R.id.my_orders_delivery_status);
        String status = "";
        if (orderMasterModel.getDeliveryStatus().equals("2")){
            status = "Delivered";
        }else {
            status = "Processing";
        }

        orderserialid.setText(listWithSerialNumber.get(position));// for get serial no of list item
        orderid.setText(String.valueOf(orderMasterModel.getOrderId()));
        orderdate.setText(String.valueOf(orderMasterModel.getOrderDate()).substring(0,10));
        orderstatus.setText(orderMasterModel.getActivStatus());
        shipaddress.setText(orderMasterModel.getShippingAddress());
        delstatus.setText(status);

        return convertView;

    }
}
