package com.aait.oms.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aait.oms.R;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierAdapterVH> {

    Context mContext;
    List<SupplierModel> supplierlist;

    public SupplierAdapter(Context mContext, List<SupplierModel> supplierlist) {
        this.mContext = mContext;
        this.supplierlist = supplierlist;
    }

    @NonNull
    @Override
    public SupplierAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new SupplierAdapter.SupplierAdapterVH(LayoutInflater.from(mContext).inflate(R.layout.supplier_samplelist_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapterVH holder, int position) {

        Object getrow =supplierlist.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;


       // String sid = String.valueOf(supplierlist.get(position).getSupplierId());
        String sname = (String) t.get("supName");

       // holder.supplierid.setText(sid);
        holder.suppliername.setText(sname);

    }

    @Override
    public int getItemCount() {
        return supplierlist.size();
    }

    public static class  SupplierAdapterVH extends RecyclerView.ViewHolder {

        TextView supplierid;
        TextView suppliername;
        public SupplierAdapterVH(@NonNull View itemView) {
            super(itemView);
           // supplierid = itemView.findViewById(R.id.supid);
           // suppliername = itemView.findViewById(R.id.supname);
            //suppliername = itemView.findViewById(R.id.supnameid);


        }
    }
}
