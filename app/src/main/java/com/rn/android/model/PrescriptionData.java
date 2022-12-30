package com.rn.android.model;

/**
 * Created by hp on 9/11/17.
 */

public class PrescriptionData {


    /**
     * external_prescription_id : 1417560
     * drug : DIPHENHYDRAMINE HCL 50 MG
     */

    private String external_prescription_id;
    private String drug;
    private String qty;

    public String getExternal_prescription_id() {
        return external_prescription_id;
    }

    public void setExternal_prescription_id(String external_prescription_id) {
        this.external_prescription_id = external_prescription_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }
}
