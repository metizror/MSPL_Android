package com.rn.android.model;

import java.io.Serializable;

public class modelMedication implements Serializable {


    private String delivery_no;
    private String patient_id;
    private String Patient;
    private String Rx_No;
    private String drug;
    private String Qty;

    public String getDelivery_no() {
        return delivery_no;
    }

    public void setDelivery_no(String delivery_no) {
        this.delivery_no = delivery_no;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient() {
        return Patient;
    }

    public void setPatient(String patient) {
        Patient = patient;
    }

    public String getRx_No() {
        return Rx_No;
    }

    public void setRx_No(String rx_No) {
        Rx_No = rx_No;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
