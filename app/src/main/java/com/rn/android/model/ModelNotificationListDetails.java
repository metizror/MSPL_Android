package com.rn.android.model;

public class ModelNotificationListDetails {

    private String DeliveryNo;
    private String drug;
    private String qty;
    private String Rx;
    private String Patient;
    private String Driver;

    public String getDeliveryNo() {
        return DeliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        DeliveryNo = deliveryNo;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRx() {
        return Rx;
    }

    public void setRx(String rx) {
        Rx = rx;
    }

    public String getPatient() {
        return Patient;
    }

    public void setPatient(String patient) {
        Patient = patient;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }
}
