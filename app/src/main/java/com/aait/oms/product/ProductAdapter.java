package com.aait.oms.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    List<StockViewModel> productModels;
    List<StockViewModel> itemsModelListFiltered;
    private final ArrayList<StockViewModel> arraylist;

    public ProductAdapter(Context mContext, List<StockViewModel> productModels) {
        this.mContext = mContext;
        this.productModels = productModels;
        this.itemsModelListFiltered = productModels;
        this.arraylist = new ArrayList<StockViewModel>();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StockViewModel product = itemsModelListFiltered.get(position);
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.supplier_samplelist_layout, null);

        }


        TextView productname = convertView.findViewById(R.id.cardproductnameid);
        TextView prodcode = convertView.findViewById(R.id.productcodeid2);
        TextView price = convertView.findViewById(R.id.prodPriceid);
        TextView stock = convertView.findViewById(R.id.stockstatusid);
        ImageView productImage = convertView.findViewById(R.id.prodListView_productImageid);


        productname.setText(product.getProdName());
        prodcode.setText(product.getPcode());
        stock.setText(String.format("In Stock :%s %s", product.getCurrentQty(), product.getUomName()));
        price.setText(String.format(" RM :%s", product.getSalesRate()));

        byte[] bytes = Base64.decode(product.getPicByte(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        productImage.setImageBitmap(bitmap);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint.length() == 0) {
                    filterResults.count = productModels.size();
                    filterResults.values = productModels;

                } else {
                    List<StockViewModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (StockViewModel prod : productModels) {
                        if (prod.getProdName().toLowerCase().contains(searchStr)) {
                            resultsModel.add(prod);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemsModelListFiltered = (List<StockViewModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productModels.clear();
        if (charText.length() == 0) {
            productModels.addAll(arraylist);
        } else {
            for (StockViewModel sp : arraylist) {
                if (sp.getProdName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    productModels.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
