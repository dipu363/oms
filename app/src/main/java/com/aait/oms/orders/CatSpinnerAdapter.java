package com.aait.oms.orders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.aait.oms.rootcategory.ProdCatagoryModel;

public class CatSpinnerAdapter extends ArrayAdapter<ProdCatagoryModel> {

    Context context;
    ProdCatagoryModel[]prodCatagoryModels;


    public CatSpinnerAdapter( Context context,  int textViewResourceId,  ProdCatagoryModel[] catmodel) {
        super(context, textViewResourceId, catmodel);
        this.context= context;
        this.prodCatagoryModels = catmodel;
    }

    @Override
    public int getCount() {
        return prodCatagoryModels.length;

    }

    @Nullable
    @Override
    public ProdCatagoryModel getItem(int position) {
        return prodCatagoryModels[position];
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
        label.setText(prodCatagoryModels[position].getL1Name());

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
        label.setText(prodCatagoryModels[position].getL1Name());

        return label;
    }
}
