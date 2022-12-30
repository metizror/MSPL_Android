package com.rn.android.model;

import java.io.Serializable;

public class ModelReceivedDrug implements Serializable {


    private String delivery_no;
    private String tran_id;
    private String Note;
    private String CreatedBy;
    private String CreatedName;
    private String CreaetdOn;

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
}
