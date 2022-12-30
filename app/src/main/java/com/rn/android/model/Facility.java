package com.rn.android.model;

/**
 * Created by hp on 10/10/17.
 */

public class Facility {


    /**
     * GroupID : 19
     * FacilityID : 290
     * ExFacilityID : 971
     * FacilityName : CRS- 109 NELSON ST - 971
     * Address : 109 NELSON ST
     * City : HOLDEN
     */

    private int GroupID;
    private int FacilityID;
    private int ExFacilityID;
    private String FacilityName;
    private String Address;
    private String City;

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int GroupID) {
        this.GroupID = GroupID;
    }

    public int getFacilityID() {
        return FacilityID;
    }

    public void setFacilityID(int FacilityID) {
        this.FacilityID = FacilityID;
    }

    public int getExFacilityID() {
        return ExFacilityID;
    }

    public void setExFacilityID(int ExFacilityID) {
        this.ExFacilityID = ExFacilityID;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String FacilityName) {
        this.FacilityName = FacilityName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    @Override
    public String toString() {
        return getFacilityName();
    }
}
