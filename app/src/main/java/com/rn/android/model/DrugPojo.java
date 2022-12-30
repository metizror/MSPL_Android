package com.rn.android.model;

/**
 * Created by hp on 2/2/18.
 */

public class DrugPojo {

    private String name;
    private String time;
    private String qty;
    private String refill_remaining;
    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRefill_remaining() {
        return refill_remaining;
    }

    public void setRefill_remaining(String refill_remaining) {
        this.refill_remaining = refill_remaining;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
