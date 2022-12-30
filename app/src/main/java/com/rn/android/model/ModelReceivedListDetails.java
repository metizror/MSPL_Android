package com.rn.android.model;

public class ModelReceivedListDetails {
    private String delivery_no;
    private String tran_id;
    private String patient_id;
    private String Patient;
    private String Rx_No;
    private String drug;
    private String Qty;
    private String Note;
    private String CreatedBy;
    private String CreatedName;
    private String CreaetdOn;
    private String FacilityName;

    public String getDelivery_no() {
        return delivery_no;
    }

    public void setDelivery_no(String delivery_no) {
        this.delivery_no = delivery_no;
    }

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
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

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedName() {
        return CreatedName;
    }

    public void setCreatedName(String createdName) {
        CreatedName = createdName;
    }

    public String getCreaetdOn() {
        return CreaetdOn;
    }

    public void setCreaetdOn(String creaetdOn) {
        CreaetdOn = creaetdOn;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String facilityName) {
        FacilityName = facilityName;
    }
}
