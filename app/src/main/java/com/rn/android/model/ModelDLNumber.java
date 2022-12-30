package com.rn.android.model;

import androidx.annotation.NonNull;

public class ModelDLNumber {
    private String delivery_no;

    public String getDelivery_no() {
        return delivery_no;
    }

    public void setDelivery_no(String delivery_no) {
        this.delivery_no = delivery_no;
    }

    @NonNull
    @Override
    public String toString() {
        return delivery_no;
    }
}
