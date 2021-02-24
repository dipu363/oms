package com.aait.oms.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.product.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    List<StockViewModel> stockViewModels;
    List<StockViewModel> itemsModelListFiltered;
    private final ArrayList<StockViewModel> arraylist;

    public ProductAdapter(Context mContext, List<StockViewModel> stockViewModels) {
        this.mContext = mContext;
        this.stockViewModels = stockViewModels;
        this.itemsModelListFiltered = stockViewModels;
        this.arraylist = new ArrayList<StockViewModel>();
        this.arraylist.addAll(stockViewModels);

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

      /*  Object getrow =productModelList.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;*/
        StockViewModel stok = itemsModelListFiltered.get(position);

        // for get serial no of list item
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < itemsModelListFiltered.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.supplier_samplelist_layout,null);

        }

        TextView serialid = convertView.findViewById(R.id.prodserialid);
        TextView productname = convertView.findViewById(R.id.cardproductnameid);
        TextView prodcode = convertView.findViewById(R.id.productcodeid2);
        TextView price = convertView.findViewById(R.id.propriceid);
        TextView stock = convertView.findViewById(R.id.stockstatusid);
        TextView unit = convertView.findViewById(R.id.unitid);
        //productname.setText(String.valueOf(t.get("productname")));
        serialid.setText(listWithSerialNumber.get(position)+".");

        productname.setText(stok.getPname());
        prodcode.setText(stok.getPcode());
        stock.setText(stok.getCurrentQty());
        price.setText(stok.getSalesRate());
        unit.setText(stok.getUomName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint.length() == 0){
                    filterResults.count = stockViewModels.size();
                    filterResults.values = stockViewModels;

                }else{
                    List<StockViewModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(StockViewModel stockView:stockViewModels){

                        if(stockView.getPname().toLowerCase().contains(searchStr)){
                            resultsModel.add(stockView);

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
        stockViewModels.clear();
        if (charText.length() == 0) {
            stockViewModels.addAll(arraylist);
        } else {
            for (StockViewModel sp : arraylist) {
                if (sp.getPname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    stockViewModels.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
