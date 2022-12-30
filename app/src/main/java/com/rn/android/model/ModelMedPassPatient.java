package com.rn.android.model;

import java.io.Serializable;

public class ModelMedPassPatient implements Serializable {


    /**
     * PatientId : 36902
     * patient_first : CHARLIE
     * patient_last : BROWN
     * Gender : Male
     * DOB : 1984-03-17T00:00:00
     */

    private int PatientId;
    private String patient_first;
    private String patient_last;
    private String Gender;
    private String DOB;
    //private boolean IsLOAHospital;
    private boolean Is_PrescriptionImg;

    public boolean isIs_PrescriptionImg() {
        return Is_PrescriptionImg;
    }

    public void setIs_PrescriptionImg(boolean is_PrescriptionImg) {
        Is_PrescriptionImg = is_PrescriptionImg;
    }

    /*public boolean isLOAHospital() {
        return IsLOAHospital;
    }

    public void setLOAHospital(boolean LOAHospital) {
        IsLOAHospital = LOAHospital;
    }*/

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatient_first() {
        return patient_first;
    }

    public void setPatient_first(String patient_first) {
        this.patient_first = patient_first;
    }

    public String getPatient_last() {
        return patient_last;
    }

    public void setPatient_last(String patient_last) {
        this.patient_last = patient_last;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
