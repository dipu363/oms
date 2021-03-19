package com.aait.oms.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.orders.MyOrdersActivity;
import com.aait.oms.orders.OrderActivity;
import com.aait.oms.product.ProductListActivity;
import com.aait.oms.users.MyReferenceActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomePragment extends Fragment implements View.OnClickListener {

    CardView card_option1,card_option2,card_option3,card_option4;
    TextView textView1;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_pragment, container, false);

          card_option1 = view.findViewById(R.id.cardviewadmin_Optino1);
        card_option2 = view.findViewById(R.id.cardviewadmin_Optino2);
        card_option3 = view.findViewById(R.id.cardviewadmin_Optino3);
        card_option4 = view.findViewById(R.id.cardviewadmin_Optino4);
        textView1= view.findViewById(R.id.dashboard_comnameid);
        textView1.setText("K & T TRADING");
        card_option1.setOnClickListener(this);
        card_option2.setOnClickListener(this);
        //card_option3.setOnClickListener(this);
        card_option4.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardviewadmin_Optino1:
                Intent intent1 = new Intent(getContext(),ProductListActivity.class);
                startActivity(intent1);
                break;
            case R.id.cardviewadmin_Optino2:
                Intent intent2 = new Intent(getContext(), OrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.cardviewadmin_Optino3:
            Intent intent3 = new Intent(getContext(), MyReferenceActivity.class);
            startActivity(intent3);
            break;
            case R.id.cardviewadmin_Optino4:
                Intent intent4 = new Intent(getContext(), MyOrdersActivity.class);
                startActivity(intent4);
                break;

        }

    }
}