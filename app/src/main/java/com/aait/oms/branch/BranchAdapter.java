package com.aait.oms.branch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class BranchAdapter extends ArrayAdapter<BranchModel> {

    Context context;
    BranchModel[] branchModels;

    public BranchAdapter( Context context,  int textViewResourceId,  BranchModel[] branchModels) {
        super(context, textViewResourceId, branchModels);
        this.context= context;
        this.branchModels = branchModels;
    }


    @Override
    public int getCount() {
        return branchModels.length;

    }

    @Nullable
    @Override
    public BranchModel getItem(int position) {
        return branchModels[position];
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
        label.setText(branchModels[position].getBname());

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
        label.setText(branchModels[position].getBname());

        return label;
    }
}
