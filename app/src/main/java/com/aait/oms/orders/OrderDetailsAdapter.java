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

public class OrderDetailsAdapter extends BaseAdapter {

    Context context;
    List<OrderDetailsModel> orderdetailslist;

    public OrderDetailsAdapter(Context context, List<OrderDetailsModel> orderdetailslist) {
        this.context = context;
        this.orderdetailslist = orderdetailslist;
    }

    @Override
    public int getCount() {
        return orderdetailslist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderdetailslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        OrderDetailsModel orderdetailsmodel = orderdetailslist.get(position);

        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < orderdetailslist.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.orderdetails_emple_layout,parent,false);

        }


        TextView serialno = convertView.findViewById(R.id.detail_prod_sl_id);
        TextView code = convertView.findViewById(R.id.detail_prod_code_id);
        TextView rate = convertView.findViewById(R.id.detail_prod_rate_id);
        TextView qty = convertView.findViewById(R.id.detail_prod_qty_id);
        TextView price = convertView.findViewById(R.id.detail_total_price_id);


        code.setText(orderdetailsmodel.getL4Code());
        rate.setText(String.valueOf(orderdetailsmodel.getRate()));
        qty.setText(String.valueOf(orderdetailsmodel.getQty()));
        serialno.setText(String.valueOf(listWithSerialNumber.get(position)+"."));
        int q  = orderdetailsmodel.getQty();
        float p = orderdetailsmodel.getRate();
        price.setText(String.valueOf(q*p));
        return convertView;
    }
}
