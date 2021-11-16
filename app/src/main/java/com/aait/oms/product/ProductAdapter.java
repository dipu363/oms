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
import com.aait.oms.product.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    List<ProductModel> productModels;
    List<ProductModel> itemsModelListFiltered;
    private final ArrayList<ProductModel> arraylist;

    public ProductAdapter(Context mContext, List<ProductModel> productModels) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      /*  Object getrow =productModelList.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;*/
        ProductModel product = itemsModelListFiltered.get(position);

/*        // for get serial no of list item
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < itemsModelListFiltered.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }*/

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.supplier_samplelist_layout,null);

        }


        TextView productname = convertView.findViewById(R.id.cardproductnameid);
        TextView prodcode = convertView.findViewById(R.id.productcodeid2);
        TextView price = convertView.findViewById(R.id.prodPriceid);
        TextView stock = convertView.findViewById(R.id.stockstatusid);
        ImageView productImage = convertView.findViewById(R.id.prodListView_productImageid);


        productname.setText(product.getProductname());
        prodcode.setText(product.getL4code());
        stock.setText("Available");
        price.setText("TK. "+product.getSalesrate());

        byte[] bytes = Base64.decode(product.getPicByte(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        productImage.setImageBitmap(bitmap);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint.length() == 0){
                    filterResults.count = productModels.size();
                    filterResults.values = productModels;

                }else{
                    List<ProductModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(ProductModel prod:productModels){

                        if(prod.getProductname().toLowerCase().contains(searchStr)){
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

                itemsModelListFiltered = (List<ProductModel>) results.values;
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
            for (ProductModel sp : arraylist) {
                if (sp.getProductname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    productModels.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
