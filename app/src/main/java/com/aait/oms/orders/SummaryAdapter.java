package com.aait.oms.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.ArrayList;

public class SummaryAdapter extends BaseAdapter {
    Context context;
    ArrayList<CardModel> orderdprodlist;

    public SummaryAdapter(Context context, ArrayList<CardModel> orderdprodlist) {
        this.context = context;
        this.orderdprodlist = orderdprodlist;
    }

    @Override
    public int getCount() {
        return orderdprodlist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderdprodlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CardModel cardModel = orderdprodlist.get(position);

        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < orderdprodlist.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.order_productlist_sample_layout, parent, false);

        }
        TextView serialno = convertView.findViewById(R.id.sum_prod_sl_id);
        TextView productname = convertView.findViewById(R.id.sum_prod_name_id);
        TextView rate = convertView.findViewById(R.id.sum_prod_rate_id);
        TextView code = convertView.findViewById(R.id.sum_prod_code_id);
        TextView qty = convertView.findViewById(R.id.sum_prod_qty_id);
        TextView price = convertView.findViewById(R.id.sum_total_price_id);

        productname.setText(cardModel.getProductname());
        code.setText(cardModel.getL4code());
        rate.setText(cardModel.getSalesrate());
        qty.setText(String.valueOf(cardModel.getQty()));
        serialno.setText(String.valueOf(listWithSerialNumber.get(position)));
        int q = cardModel.getQty();
        float p = Float.parseFloat(cardModel.getSalesrate());
        price.setText(String.valueOf(p * q));

        return convertView;
    }
}
