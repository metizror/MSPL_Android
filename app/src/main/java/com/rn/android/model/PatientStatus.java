package com.rn.android.model;

import androidx.annotation.NonNull;

public class PatientStatus {

    /**
     * ID : 1
     * Name : Pending
     */

    private int ID;
    private String Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @NonNull
    @Override
    public String toString() {
        return Name;
    }


}
