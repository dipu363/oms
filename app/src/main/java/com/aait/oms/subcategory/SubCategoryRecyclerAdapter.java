package com.aait.oms.subcategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aait.oms.R;
import com.aait.oms.util.OnclickeventListener;

public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter <SubCategoryRecyclerAdapter.ViewHolder> {
    ProdSubCatagoryModel[] subcatagory;
    private Context context;
    OnclickeventListener itemClickListener;



    public SubCategoryRecyclerAdapter(ProdSubCatagoryModel[] subcatagory, Context context ,OnclickeventListener onclickeventListener) {
        this.subcatagory = subcatagory;
        this.context = context;
        this.itemClickListener = onclickeventListener;
    }



    @NonNull
    @Override
    public SubCategoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.root_catagory_sample_layout, parent, false);
        return new SubCategoryRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryRecyclerAdapter.ViewHolder holder, int position){
        char catname = subcatagory[position].getL2Name().charAt(0);

        holder.textView1.setText(subcatagory[position].getL2Name());
        holder.textView2.setText(String.valueOf(catname));
        // holder.imgThumbnail.setImageResource(numberImage.get(position));




    }

    @Override
    public int getItemCount() {
        return subcatagory.length;
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView textView1;
        TextView textView2;
        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.rootcatagoritextView);
            textView2 = itemView.findViewById(R.id.catfontid);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getLayoutPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getLayoutPosition(), true);
            return true;
        }
    }
}
