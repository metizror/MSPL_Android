package com.rn.android.model;

public class ModelCycleDeliveryDate {

    /**
     * PreviousDay : 2019-07-17T00:00:00
     * PreviousDaystr : 07/17/19-Wednesday
     * PreviousDaywithdate : 07/17/19
     * PreviousDaywithdateName : Wednesday
     */

    private String PreviousDay;
    private String PreviousDaystr;
    private String PreviousDaywithdate;
    private String PreviousDaywithdateName;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getPreviousDay() {
        return PreviousDay;
    }

    public void setPreviousDay(String PreviousDay) {
        this.PreviousDay = PreviousDay;
    }

    public String getPreviousDaystr() {
        return PreviousDaystr;
    }

    public void setPreviousDaystr(String PreviousDaystr) {
        this.PreviousDaystr = PreviousDaystr;
    }

    public String getPreviousDaywithdate() {
        return PreviousDaywithdate;
    }

    public void setPreviousDaywithdate(String PreviousDaywithdate) {
        this.PreviousDaywithdate = PreviousDaywithdate;
    }

    public String getPreviousDaywithdateName() {
        return PreviousDaywithdateName;
    }

    public void setPreviousDaywithdateName(String PreviousDaywithdateName) {
        this.PreviousDaywithdateName = PreviousDaywithdateName;
    }
}
