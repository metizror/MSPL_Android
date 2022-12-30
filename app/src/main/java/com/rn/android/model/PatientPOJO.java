package com.rn.android.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by abc on 9/26/2017.
 */

public class PatientPOJO implements Serializable {

    /**
     * __type : Entity.tblpatients
     * external_patient_id : 48474
     * external_facility_id : 968
     * external_facility_name : CRS- 18 DANA ST
     * Firstname : FRANK
     * lastname : BOSCO
     * Middlename : null
     * gender : Male
     * dob : 5/21/1955 12:00:00 AM
     * strdob : 05/21/1955
     * ssno : 034467645
     * phone : 9782492153
     * fax : 9782493170
     * tabindex : 1
     */

    private String __type;
    private String external_patient_id;
    private String external_facility_id;
    private String external_facility_name;
    private String firstname;
    private String lastname;
    private Object Middlename;
    private String gender;
    private String dob;
    private String strdob;
    private String ssno;
    private String phone;
    private String fax;
    private int tabindex;
    int _id;

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getExternal_patient_id() {
        return external_patient_id;
    }

    public void setExternal_patient_id(String external_patient_id) {
        this.external_patient_id = external_patient_id;
    }

    public String getExternal_facility_id() {
        return external_facility_id;
    }

    public void setExternal_facility_id(String external_facility_id) {
        this.external_facility_id = external_facility_id;
    }

    public String getExternal_facility_name() {
        return external_facility_name;
    }

    public void setExternal_facility_name(String external_facility_name) {
        this.external_facility_name = external_facility_name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Object getMiddlename() {
        return Middlename;
    }

    public void setMiddlename(Object Middlename) {
        this.Middlename = Middlename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStrdob() {
        return strdob;
    }

    public void setStrdob(String strdob) {
        this.strdob = strdob;
    }

    public String getSsno() {
        return ssno;
    }

    public void setSsno(String ssno) {
        this.ssno = ssno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getTabindex() {
        return tabindex;
    }

    public void setTabindex(int tabindex) {
        this.tabindex = tabindex;
    }

    @NonNull
    @Override
    public String toString() {
        return firstname+lastname;
    }


}

