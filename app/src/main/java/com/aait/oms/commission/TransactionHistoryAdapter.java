package com.aait.oms.commission;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.List;

public class TransactionHistoryAdapter extends BaseAdapter {

    Context context;
    List<CommissionWithdrawModel> commissionWithdrawModelList;

    public TransactionHistoryAdapter(Context context, List<CommissionWithdrawModel> commissionWithdrawModelList) {
        this.context = context;
        this.commissionWithdrawModelList = commissionWithdrawModelList;
    }

    @Override
    public int getCount() {
        return commissionWithdrawModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommissionWithdrawModel model = commissionWithdrawModelList.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.commission_history_semple_layout, parent, false);
        }

        TextView trsacid = convertView.findViewById(R.id.his_transaction_id);
        TextView trsacamount = convertView.findViewById(R.id.tr_his_amount_id);
        TextView trdate = convertView.findViewById(R.id.tr_his_date_id);
        String reqdate = model.getTransDate().substring(0, 10);
        trsacid.setText(model.getTransectionId());
        trsacamount.setText("RM  " + model.getAfterTexBalance());
        trdate.setText(reqdate);
        return convertView;
    }
}
