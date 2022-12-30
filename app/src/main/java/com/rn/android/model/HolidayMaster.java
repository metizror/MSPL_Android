package com.rn.android.model;

public class HolidayMaster {
    /**
     * HolidayID : 15
     * HolidayDate : 2019-07-19T00:00:00
     * Description : We will be closed on 07/19/19 (we are going on tour with whole the team so, no staff on duty.)
     */

    private int HolidayID;
    private String HolidayDate;
    private String Description;

    public int getHolidayID() {
        return HolidayID;
    }

    public void setHolidayID(int HolidayID) {
        this.HolidayID = HolidayID;
    }

    public String getHolidayDate() {
        return HolidayDate;
    }

    public void setHolidayDate(String HolidayDate) {
        this.HolidayDate = HolidayDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
