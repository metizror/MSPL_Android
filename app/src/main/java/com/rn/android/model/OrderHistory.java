package com.rn.android.model;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 23/1/18.
 */

public class OrderHistory implements Comparable<OrderHistory> {

    /**
     * OrderID : 1
     * OrderNumber :
     * OrderStatus : 1
     * Deliverytype : 1
     * Ordertype : 1
     * FacilityID : 1
     * Rxnumber : 1486526
     * PatientName : PARKER,NATALIE
     * StoreName :
     * CreatedBy : 1
     * CreatedOn : 2018-01-23T11:16:09.063
     * UpdatedBy : 1
     * UpdatedOn : 2018-01-23T11:16:09.063
     * IsDiscard : false
     * ReasonForDiscard :
     * DiscardedBy : 0
     * DiscardedTime : 2014-10-10T00:00:00
     * IsBillingIssue : false
     * Deliverydate : 2018-01-23T00:00:00
     * deliverytime : 01:16 PM
     * drug : CLONAZEPAM 1 MG
     * FromMobile : 1
     * Resthours :
     * PatientID : null
     * MobileOrderStatus : Intake
     * DeliverytypeName : Stat
     * OrderType : Missing Dose
     * FacilityName : VINFEN-181 BROADWAY
     */

    private int OrderID;
    private String OrderNumber;
    private int OrderStatus;
    private int Deliverytype;
    private int Ordertype;
    private int FacilityID;
    private String Rxnumber;
    private String PatientName;
    private String StoreName;
    private int CreatedBy;
    private String CreatedOn;
    private int UpdatedBy;
    private String UpdatedOn;
    private boolean IsDiscard;
    private String ReasonForDiscard;
    private int DiscardedBy;
    private String DiscardedTime;
    private boolean IsBillingIssue;
    private String Deliverydate;
    private String deliverytime;
    private String drug;
    private int FromMobile;
    private String Resthours;
    private Object PatientID;
    private String MobileOrderStatus;
    private String DeliverytypeName;
    private String OrderType;
    private String FacilityName;
    private String DocumentPath;
    private String UpdateOn;
    private String RxID;
    private String tran_id;
    private String drug_qty;

    public String getDrug_qty() {
        return drug_qty;
    }

    public void setDrug_qty(String drug_qty) {
        this.drug_qty = drug_qty;
    }

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    private String delivery_date;


    public String getRxID() {
        return RxID;
    }

    public void setRxID(String rxID) {
        RxID = rxID;
    }

    public String getUpdateOn() {
        return UpdateOn;
    }

    public void setUpdateOn(String updateOn) {
        UpdateOn = updateOn;
    }

    public String getDocumentPath() {
        return DocumentPath;
    }

    public void setDocumentPath(String documentPath) {
        DocumentPath = documentPath;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public int getDeliverytype() {
        return Deliverytype;
    }

    public void setDeliverytype(int Deliverytype) {
        this.Deliverytype = Deliverytype;
    }

    public int getOrdertype() {
        return Ordertype;
    }

    public void setOrdertype(int Ordertype) {
        this.Ordertype = Ordertype;
    }

    public int getFacilityID() {
        return FacilityID;
    }

    public void setFacilityID(int FacilityID) {
        this.FacilityID = FacilityID;
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

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String CreatedOn) {
        this.CreatedOn = CreatedOn;
    }

    public int getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(int UpdatedBy) {
        this.UpdatedBy = UpdatedBy;
    }

    public String getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(String UpdatedOn) {
        this.UpdatedOn = UpdatedOn;
    }

    public boolean isIsDiscard() {
        return IsDiscard;
    }

    public void setIsDiscard(boolean IsDiscard) {
        this.IsDiscard = IsDiscard;
    }

    public String getReasonForDiscard() {
        return ReasonForDiscard;
    }

    public void setReasonForDiscard(String ReasonForDiscard) {
        this.ReasonForDiscard = ReasonForDiscard;
    }

    public int getDiscardedBy() {
        return DiscardedBy;
    }

    public void setDiscardedBy(int DiscardedBy) {
        this.DiscardedBy = DiscardedBy;
    }

    public String getDiscardedTime() {
        return DiscardedTime;
    }

    public void setDiscardedTime(String DiscardedTime) {
        this.DiscardedTime = DiscardedTime;
    }

    public boolean isIsBillingIssue() {
        return IsBillingIssue;
    }

    public void setIsBillingIssue(boolean IsBillingIssue) {
        this.IsBillingIssue = IsBillingIssue;
    }

    public String getDeliverydate() {
        return Deliverydate;
    }

    public void setDeliverydate(String Deliverydate) {
        this.Deliverydate = Deliverydate;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public int getFromMobile() {
        return FromMobile;
    }

    public void setFromMobile(int FromMobile) {
        this.FromMobile = FromMobile;
    }

    public String getResthours() {
        return Resthours;
    }

    public void setResthours(String Resthours) {
        this.Resthours = Resthours;
    }

    public Object getPatientID() {
        return PatientID;
    }

    public void setPatientID(Object PatientID) {
        this.PatientID = PatientID;
    }

    public String getMobileOrderStatus() {
        return MobileOrderStatus;
    }

    public void setMobileOrderStatus(String MobileOrderStatus) {
        this.MobileOrderStatus = MobileOrderStatus;
    }

    public String getDeliverytypeName() {
        return DeliverytypeName;
    }

    public void setDeliverytypeName(String DeliverytypeName) {
        this.DeliverytypeName = DeliverytypeName;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String OrderType) {
        this.OrderType = OrderType;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String FacilityName) {
        this.FacilityName = FacilityName;
    }


    @Override
    public int compareTo(@NonNull OrderHistory orderHistory) {
        Date date = null;
        Date compareDate = null;
        try {
            date = new SimpleDateFormat("MM/dd/yy").parse(getUpdateOn());
            compareDate = new SimpleDateFormat("MM/dd/yy").parse(orderHistory.getUpdateOn());
            return date.compareTo(compareDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }


    }
}
