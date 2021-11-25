package com.aait.oms.orders;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    Context context;
    List<CardModel> prodlist;
    ImageButton cartincreasebtn, cartdecreasebtn;

    public CartAdapter(Context context, List<CardModel> prodlist) {
        this.context = context;
        this.prodlist = prodlist;
    }

    @Override
    public int getCount() {
        return prodlist.size();
    }

    @Override
    public Object getItem(int position) {
        return prodlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CardModel cardModel = prodlist.get(position);
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < prodlist.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cardlist, parent, false);
        }

        TextView serial = convertView.findViewById(R.id.cartserialid);
        TextView prodname = convertView.findViewById(R.id.cartproducttextid);
        TextView price = convertView.findViewById(R.id.cartprodpriceid);
        EditText editText = convertView.findViewById(R.id.cartproduct_qty);
        cartincreasebtn = convertView.findViewById(R.id.btn_increase);
        cartdecreasebtn = convertView.findViewById(R.id.btn_decrease);
        editText.setClickable(false);

        cartincreasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(position, editText, 5);
            }
        });
        cartdecreasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateQuantity(position, editText, -1);
            }
        });

        serial.setText(String.valueOf(listWithSerialNumber.get(position) + "."));
        prodname.setText(cardModel.getProductname());
        price.setText("RM :" + cardModel.getSalesrate());
        editText.setText(String.valueOf(cardModel.getQty()));
        editText.setText(String.valueOf(cardModel.getQty()));
        return convertView;
    }

    private void updateQuantity(int position, EditText editText, int value) {

        CardModel cardModel = prodlist.get(position);
        if (value > 0) {
            cardModel.setQty(cardModel.getQty() + value);
        } else {
            if (cardModel.getQty() > 0) {
                cardModel.setQty(cardModel.getQty() - 1);
            }

        }
        editText.setText(String.valueOf(cardModel.getQty()));

    }
}
