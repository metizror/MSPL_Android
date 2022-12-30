package com.rn.android.model;

public class Allergy {


    /**
     * AllergyID : 2
     * AllergyName : itching
     */

    private int AllergyID;
    private String AllergyName;

    public int getAllergyID() {
        return AllergyID;
    }

    public void setAllergyID(int AllergyID) {
        this.AllergyID = AllergyID;
    }

    public String getAllergyName() {
        return AllergyName;
    }

    public void setAllergyName(String AllergyName) {
        this.AllergyName = AllergyName;
    }
    @Override
    public String toString() {
        return AllergyName;
    }
}
