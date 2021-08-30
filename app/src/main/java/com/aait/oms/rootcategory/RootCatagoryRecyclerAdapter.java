package com.aait.oms.rootcategory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aait.oms.R;
import com.aait.oms.product.CategoryWiseProductViewActivity;
import com.aait.oms.product.ProductInGridViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RootCatagoryRecyclerAdapter  extends RecyclerView.Adapter <RootCatagoryRecyclerAdapter.ViewHolder>{
    ProdCatagoryModel[] catagory;
    private Context context;

    public RootCatagoryRecyclerAdapter(ProdCatagoryModel[] catagory, Context context) {
        this.catagory = catagory;
        this.context = context;
    }

    @NonNull
    @Override
    public RootCatagoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.root_catagory_sample_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( RootCatagoryRecyclerAdapter.ViewHolder holder, int position) {
        char catname = catagory[position].getL1Name().charAt(0);

        holder.textView1.setText(catagory[position].getL1Name());
        holder.textView2.setText(String.valueOf(catname));

       // holder.imgThumbnail.setImageResource(numberImage.get(position));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + catagory[position].getL1Name() + " (Long click)", Toast.LENGTH_SHORT).show();
                   // context.startActivity(new Intent(context, ProductInGridViewActivity.class));
                    Intent intent = new Intent(context,ProductInGridViewActivity.class);
                    intent.putExtra("catid",catagory[position].getL1Code());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "#" + position + " - " + catagory[position].getL1Name(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CategoryWiseProductViewActivity.class);
                    intent.putExtra("catid",catagory[position].getL1Code());
                    intent.putExtra("catname",catagory[position].getL1Name());
                    context.startActivity(intent);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return catagory.length;
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
