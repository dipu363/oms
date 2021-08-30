package com.aait.oms.subcategory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aait.oms.R;
import com.aait.oms.product.CategoryWiseProductViewActivity;
import com.aait.oms.product.ProductInGridViewActivity;
import com.aait.oms.rootcategory.ItemClickListener;
import com.aait.oms.rootcategory.ProdCatagoryModel;
import com.aait.oms.rootcategory.RootCatagoryRecyclerAdapter;

public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter <SubCategoryRecyclerAdapter.ViewHolder> {
    ProdSubCatagoryModel[] subcatagory;
    private Context context;

    public SubCategoryRecyclerAdapter(ProdSubCatagoryModel[] subcatagory, Context context) {
        this.subcatagory = subcatagory;
        this.context = context;
    }


    @NonNull
    @Override
    public SubCategoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.root_catagory_sample_layout, parent, false);
        return new SubCategoryRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryRecyclerAdapter.ViewHolder holder, int position) {
        char catname = subcatagory[position].getL2Name().charAt(0);

        holder.textView1.setText(subcatagory[position].getL2Name());
        holder.textView2.setText(String.valueOf(catname));
        // holder.imgThumbnail.setImageResource(numberImage.get(position));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(context, "#" + position + " - " + subcatagory[position].getL2Name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CategoryWiseProductViewActivity.class);
                intent.putExtra("subcatid",subcatagory[position].getL2Code());
                intent.putExtra("subcatname",subcatagory[position].getL2Name());
                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return subcatagory.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView textView1;
        TextView textView2;
        private ItemClickListener clickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.rootcatagoritextView);
            textView2 = itemView.findViewById(R.id.catfontid);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view,getPosition(), true);
            return true;
        }
    }
}
