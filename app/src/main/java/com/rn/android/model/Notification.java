package com.rn.android.model;

/**
 * Created by hp on 13/3/18.
 */

public class Notification {


    /**
     * OrderID : 0
     * Note : Thank you.We have received notification on PelMeds for If census is not done prior to 14 days of cycle. Facility Name: ADVOCATES INC - 24 BROWNLEA RD
     * Time : 2019-08-27T14:07:49.31
     * Timestr : 08/27/19
     * CreatedBy :
     * Drug :
     * Rxnumber :
     * PatientName :
     * CreatedByID : 0
     * OrderCreatedDatestr : 2014-10-10T00:00:00
     * OrderCreatedDate : 10/10/14
     * FirstName :
     * LastName :
     * HeaderID : 2736
     * DailyHoldRxID : 0
     * EmailType : 3
     * PatientID : 0
     * PharmacyClientID : 0
     */

    private int OrderID;
    private String Note;
    private String Time;
    private String Timestr;
    private String CreatedBy;
    private String Drug;
    private String Rxnumber;
    private String PatientName;
    private int CreatedByID;
    private String OrderCreatedDatestr;
    private String OrderCreatedDate;
    private String FirstName;
    private String LastName;
    private String HeaderID;
    private String DailyHoldRxID;
    private String EmailType;
    private String PatientID;
    private String PharmacyClientID;
    private String DoseTime;
    private String DosedateStr;

    public String getDosedateStr() {
        return DosedateStr;
    }

    public void setDosedateStr(String dosedateStr) {
        DosedateStr = dosedateStr;
    }

    public String getDoseTime() {
        return DoseTime;
    }

    public void setDoseTime(String doseTime) {
        DoseTime = doseTime;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getTimestr() {
        return Timestr;
    }

    public void setTimestr(String Timestr) {
        this.Timestr = Timestr;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getDrug() {
        return Drug;
    }

    public void setDrug(String Drug) {
        this.Drug = Drug;
    }

    public String getRxnumber() {
        return Rxnumber;
    }

    public void setRxnumber(String Rxnumber) {
        this.Rxnumber = Rxnumber;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public int getCreatedByID() {
        return CreatedByID;
    }

    public void setCreatedByID(int CreatedByID) {
        this.CreatedByID = CreatedByID;
    }

    public String getOrderCreatedDatestr() {
        return OrderCreatedDatestr;
    }

    public void setOrderCreatedDatestr(String OrderCreatedDatestr) {
        this.OrderCreatedDatestr = OrderCreatedDatestr;
    }

    public String getOrderCreatedDate() {
        return OrderCreatedDate;
    }

    public void setOrderCreatedDate(String OrderCreatedDate) {
        this.OrderCreatedDate = OrderCreatedDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getHeaderID() {
        return HeaderID;
    }

    public void setHeaderID(String HeaderID) {
        this.HeaderID = HeaderID;
    }

    public String getDailyHoldRxID() {
        return DailyHoldRxID;
    }

    public void setDailyHoldRxID(String DailyHoldRxID) {
        this.DailyHoldRxID = DailyHoldRxID;
    }

    public String getEmailType() {
        return EmailType;
    }

    public void setEmailType(String EmailType) {
        this.EmailType = EmailType;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }

    public String getPharmacyClientID() {
        return PharmacyClientID;
    }

    public void setPharmacyClientID(String PharmacyClientID) {
        this.PharmacyClientID = PharmacyClientID;
    }
}
