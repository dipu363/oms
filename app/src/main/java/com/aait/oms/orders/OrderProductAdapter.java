package com.aait.oms.orders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.product.ProductModel;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends BaseAdapter{

    CheckBox checkBox;
    Context mContext;
    List<ProductModel> productModels;
    List<ProductModel> itemsModelListFiltered;
    private final ArrayList<ProductModel> arraylist;

    boolean[] itemChecked;

    public OrderProductAdapter(Context mContext, List<ProductModel> productModels){
        super();
        this.mContext = mContext;
        this.productModels = productModels;
        this.itemsModelListFiltered = productModels;
        this.arraylist = new ArrayList<ProductModel>();
        itemChecked = new boolean[productModels.size()];

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
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < productModels.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.ordersemplelistitem,null);

        }

        TextView serialno = convertView.findViewById(R.id.prodserialid);
        TextView productname = convertView.findViewById(R.id.order_product_name_id);
        TextView price = convertView.findViewById(R.id.order_prod_rate_id);
        TextView pcode = convertView.findViewById(R.id.order_prod_codeid);
         checkBox = convertView.findViewById(R.id.prodlistchackboxid);

        serialno.setText(listWithSerialNumber.get(position) + ".");
        productname.setText(prodmodel.getProductname());
        pcode.setText(prodmodel.getL4code());
        price.setText("RM "+prodmodel.getSalesrate());
        checkBox.setChecked(false);


        checkBox.setChecked(itemChecked[position]);


      /*  checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (checkBox.isChecked()){
                    Toast.makeText(mContext, "True", Toast.LENGTH_SHORT).show();
                    itemChecked[position] = true;

                } else {
                    Toast.makeText(mContext, "false", Toast.LENGTH_SHORT).show();
                    itemChecked[position] = false;
                }


            }
        });*/





        return convertView;
    }

}
