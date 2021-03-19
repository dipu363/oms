package com.aait.oms.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.aait.oms.util.BaseModel;

public class OrderDetailsModel implements Parcelable {

/*    id:number;
    orderId: number;
    l4Code: number;
    qty: number;
    rate: number;
    itemTotal: number;
    productName: string;*/

/*    int id;
    int orderId;
    String l4Code;
   int qty;
   float rate;
   float itemTotal;
   double avg_pur_rate;*/



    int id;
    int orderId;
    String l4Code;
    int qty;
    float rate;
    float itemTotal;
    double avgPurRate;


   /* public OrderDetailsModel(int orderId, String l4Code, int qty, float rate, float itemTotal) {
        this.orderId = orderId;
        this.l4Code = l4Code;
        this.qty = qty;
        this.rate = rate;
        this.itemTotal = itemTotal;
    }*/

    public OrderDetailsModel(int id, int orderId, String l4Code, int qty, float rate, float itemTotal, double avgPurRate) {
        this.id = id;
        this.orderId = orderId;
        this.l4Code = l4Code;
        this.qty = qty;
        this.rate = rate;
        this.itemTotal = itemTotal;
        this.avgPurRate = avgPurRate;
    }

    public OrderDetailsModel() {
    }

    public static final Creator<OrderDetailsModel> CREATOR = new Creator<OrderDetailsModel>() {
        @Override
        public OrderDetailsModel createFromParcel(Parcel in) {
            return new OrderDetailsModel(in);
        }

        @Override
        public OrderDetailsModel[] newArray(int size) {
            return new OrderDetailsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getL4Code() {
        return l4Code;
    }

    public void setL4Code(String l4Code) {
        this.l4Code = l4Code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(float itemTotal) {
        this.itemTotal = itemTotal;
    }

    public double getAvgPurRate() {
        return avgPurRate;
    }

    public void setAvgPurRate(double avgPurRate) {
        this.avgPurRate = avgPurRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(orderId);
        dest.writeString(l4Code);
        dest.writeInt(qty);
        dest.writeFloat(rate);
        dest.writeFloat(itemTotal);
        dest.writeDouble(avgPurRate);
    }


    private OrderDetailsModel(Parcel in) {
        id = in.readInt();
        orderId = in.readInt();
        l4Code = in.readString();
        qty = in.readInt();
        rate = in.readFloat();
        itemTotal = in.readFloat();
        avgPurRate = in.readDouble();

    }

}
