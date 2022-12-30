package com.rn.android.model;

public class ModelTakenPrescription {

    /**
     * tran_id : 600026
     * dose_date : 2015-01-01T00:00:00
     * patient_id : 38261
     * facility_id : 707
     * dose_time : 11:00 AM
     * dose_Qty : 0.5
     * dose_status : D
     * note :
     * updated_by : sid
     * updated_date : 2019-10-10T09:55:48.76
     * dose_given_time :
     * dose_given_date : 2014-10-10T00:00:00
     * med_type : C
     * sig_code : ht (0.05mg) po at 12p/4p
     * sig_detail : Take 1/2 tablet (0.05mg) by mouth at 12p/4p
     * internal_rx_id : 1168824
     * rx_number : 3538161
     * drug : CLONIDINE HCL
     * patient_first : ANDREA
     * patient_last : JULIANO (SK)
     * DOB : 2004-07-05T00:00:00
     * Gender : Female
     * giventime : 09:55 AM
     */

    private int tran_id;
    private String dose_date;
    private int patient_id;
    private int facility_id;
    private String dose_time;
    private float dose_Qty;
    private String dose_status;
    private String note;
    private String updated_by;
    private String updated_date;
    private String dose_given_time;
    private String dose_given_date;
    private String med_type;
    private String sig_code;
    private String sig_detail;
    private int internal_rx_id;
    private String rx_number;
    private String drug;
    private String patient_first;
    private String patient_last;
    private String DOB;
    private String Gender;
    private String giventime;
    private String ndc;
    private String MedImagePath;
    private boolean IsLOAHospital;
    private String LOAHospitalNote;
    private boolean IsMissedDose;
    private String UserName;
    private String LOAType;
    private String LOAHospitalDate;
    private String LOAHospitalTime;
    private boolean IsPRN;

    public boolean isPRN() {
        return IsPRN;
    }

    public void setPRN(boolean PRN) {
        IsPRN = PRN;
    }

    public String getLOAHospitalTime() {
        return LOAHospitalTime;
    }

    public void setLOAHospitalTime(String LOAHospitalTime) {
        this.LOAHospitalTime = LOAHospitalTime;
    }

    public String getLOAHospitalDate() {
        return LOAHospitalDate;
    }

    public void setLOAHospitalDate(String LOAHospitalDate) {
        this.LOAHospitalDate = LOAHospitalDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLOAType() {
        return LOAType;
    }

    public void setLOAType(String LOAType) {
        this.LOAType = LOAType;
    }

    public boolean isMissedDose() {
        return IsMissedDose;
    }

    public void setMissedDose(boolean missedDose) {
        IsMissedDose = missedDose;
    }

    public boolean isLOAHospital() {
        return IsLOAHospital;
    }

    public void setLOAHospital(boolean LOAHospital) {
        IsLOAHospital = LOAHospital;
    }

    public String getLOAHospitalNote() {
        return LOAHospitalNote;
    }

    public void setLOAHospitalNote(String LOAHospitalNote) {
        this.LOAHospitalNote = LOAHospitalNote;
    }

    public String getNdc() {
        return ndc;
    }

    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    public String getMedImagePath() {
        return MedImagePath;
    }

    public void setMedImagePath(String medImagePath) {
        MedImagePath = medImagePath;
    }

    public int getTran_id() {
        return tran_id;
    }

    public void setTran_id(int tran_id) {
        this.tran_id = tran_id;
    }

    public String getDose_date() {
        return dose_date;
    }

    public void setDose_date(String dose_date) {
        this.dose_date = dose_date;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(int facility_id) {
        this.facility_id = facility_id;
    }

    public String getDose_time() {
        return dose_time;
    }

    public void setDose_time(String dose_time) {
        this.dose_time = dose_time;
    }

    public float getDose_Qty() {
        return dose_Qty;
    }

    public void setDose_Qty(float dose_Qty) {
        this.dose_Qty = dose_Qty;
    }

    public String getDose_status() {
        return dose_status;
    }

    public void setDose_status(String dose_status) {
        this.dose_status = dose_status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getDose_given_time() {
        return dose_given_time;
    }

    public void setDose_given_time(String dose_given_time) {
        this.dose_given_time = dose_given_time;
    }

    public String getDose_given_date() {
        return dose_given_date;
    }

    public void setDose_given_date(String dose_given_date) {
        this.dose_given_date = dose_given_date;
    }

    public String getMed_type() {
        return med_type;
    }

    public void setMed_type(String med_type) {
        this.med_type = med_type;
    }

    public String getSig_code() {
        return sig_code;
    }

    public void setSig_code(String sig_code) {
        this.sig_code = sig_code;
    }

    public String getSig_detail() {
        return sig_detail;
    }

    public void setSig_detail(String sig_detail) {
        this.sig_detail = sig_detail;
    }

    public int getInternal_rx_id() {
        return internal_rx_id;
    }

    public void setInternal_rx_id(int internal_rx_id) {
        this.internal_rx_id = internal_rx_id;
    }

    public String getRx_number() {
        return rx_number;
    }

    public void setRx_number(String rx_number) {
        this.rx_number = rx_number;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getGiventime() {
        return giventime;
    }

    public void setGiventime(String giventime) {
        this.giventime = giventime;
    }
}
