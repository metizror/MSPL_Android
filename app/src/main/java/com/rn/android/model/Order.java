package com.rn.android.model;

/**
 * Created by hp on 9/10/17.
 */

public class Order {

    /**
     * OrdertypeID : 1
     * OrderType : Missing Dose
     */

    private int OrdertypeID;
    private String OrderType;

    public int getOrdertypeID() {
        return OrdertypeID;
    }

    public void setOrdertypeID(int OrdertypeID) {
        this.OrdertypeID = OrdertypeID;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String OrderType) {
        this.OrderType = OrderType;
    }

    @Override
    public String toString() {
        return getOrderType();
    }
}
