package com.rn.android.model;

/**
 * Created by hp on 9/10/17.
 */

public class Delivery {


    /**
     * deliveryID : 1
     * DeliverytypeName : Stat
     */

    private int deliveryID;
    private String DeliverytypeName;

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    private String DeliveryDate;

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getDeliverytypeName() {
        return DeliverytypeName;
    }

    public void setDeliverytypeName(String DeliverytypeName) {
        this.DeliverytypeName = DeliverytypeName;
    }

    @Override
    public String toString() {
        return getDeliverytypeName();
    }
}
