package com.rn.android.model;

import androidx.annotation.NonNull;

import com.rn.android.util.Utils;

public class CycleStartDate {

    /**
     * Id : 3073
     * FacilityID : 688
     * CycleStartDate : 2019-10-16T00:00:00
     */
    private int Id;
    private int FacilityID;
    private String CycleStartDate;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getFacilityID() {
        return FacilityID;
    }

    public void setFacilityID(int FacilityID) {
        this.FacilityID = FacilityID;
    }

    public String getCycleStartDate() {
        return CycleStartDate;
    }

    public void setCycleStartDate(String CycleStartDate) {
        this.CycleStartDate = CycleStartDate;
    }

    @NonNull
    @Override
    public String toString() {
        return Utils.formatDateFromOnetoAnother(CycleStartDate,"yyyy-MM-dd'T'HH:mm:ss","MM-dd-yy");
    }
}
