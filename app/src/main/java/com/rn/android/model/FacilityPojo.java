package com.rn.android.model;

/**
 * Created by hp on 6/3/18.
 */

public class FacilityPojo {
    /**
     * FacilityId : 1
     * FacilityName : VINFEN-181 BROADWAY
     */
    private int FacilityId;
    private String FacilityName;

    public int getFacilityId() {
        return FacilityId;
    }

    public void setFacilityId(int FacilityId) {
        this.FacilityId = FacilityId;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String FacilityName) {
        this.FacilityName = FacilityName;
    }

    @Override
    public String toString() {
        return getFacilityName();
    }
}
