package com.aait.oms.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aait.oms.R;

import java.util.ArrayList;
import java.util.List;

public class MyReferenceAdapter extends BaseAdapter {

    Context context;
    List<UsersModel> userslist;

    public MyReferenceAdapter(Context context, List<UsersModel> userslist) {
        this.context = context;
        this.userslist = userslist;
    }

    @Override
    public int getCount() {
        return userslist.size();
    }

    @Override
    public Object getItem(int position) {
        return userslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UsersModel usermodel = userslist.get(position);


        // for get serial no of list item
        ArrayList<String> listWithSerialNumber = new ArrayList<>();
        for (int i = 0; i < userslist.size(); i++) {
            listWithSerialNumber.add(String.valueOf(i + 1));
        }

        if(convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.my_ref_semple_list_layout,parent,false);
        }

        TextView serialid = convertView.findViewById(R.id.ref_serialid);
        TextView ufname = convertView.findViewById(R.id.ref_user_fnameid);
        TextView ulname = convertView.findViewById(R.id.ref_user_lnameid);
        TextView ustatus = convertView.findViewById(R.id.ref_user_statusid);
        String status="";

        if(usermodel.getStatus().equals("1")){

            status = "Active";
        }else{
            status = "Terminate";
        }

        serialid.setText(listWithSerialNumber.get(position)+".");
        ufname.setText(usermodel.getFname());
        ulname.setText(usermodel.getLname());
        ustatus.setText(status);



        return convertView;
    }
}
