package com.aait.oms.orders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.aait.oms.subcategory.ProdSubCatagoryModel;

public class SubCatSpinnerAdapter extends ArrayAdapter<ProdSubCatagoryModel> {

    Context context;
    ProdSubCatagoryModel[] subCatagoryModels;
    public SubCatSpinnerAdapter(Context context,int textViewResourceId, ProdSubCatagoryModel[] subcatmodel) {
        super(context, textViewResourceId, subcatmodel);
        this.context= context;
        this.subCatagoryModels= subcatmodel;
    }

    @Override
    public int getCount() {
        return subCatagoryModels.length;

    }

    @Nullable
    @Override
    public ProdSubCatagoryModel getItem(int position) {
        return subCatagoryModels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(subCatagoryModels[position].getL2Name());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(subCatagoryModels[position].getL2Name());

        return label;
    }
}
