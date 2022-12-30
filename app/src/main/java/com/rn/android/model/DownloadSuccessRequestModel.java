package com.rn.android.model;

public class DownloadSuccessRequestModel {
    public DownloadSuccessRequestModel(int medsheetID, int userId, int external_Facility_Id, int external_Patient_Id, String downloadDate, int isDownload,String device_id) {
        MedsheetID = medsheetID;
        UserId = userId;
        External_Facility_Id = external_Facility_Id;
        External_Patient_Id = external_Patient_Id;
        DownloadDate = downloadDate;
        Device_id = device_id;

        IsDownload = isDownload;
    }

    /**
     * MedsheetID : 1
     * UserId : 77
     * External_Facility_Id : 2619
     * External_Patient_Id : 1946752
     * DownloadDate : 05/11/2022 4:38:00
     * IsDownload : 1
     */

    private int MedsheetID;
    private int UserId;
    private String Device_id;
    private int External_Facility_Id;
    private int External_Patient_Id;
    private String DownloadDate;
    private int IsDownload;

    public String getDevice_id() {
        return Device_id;
    }

    public void setDevice_id(String device_id) {
        Device_id = device_id;
    }

    public int getMedsheetID() {
        return MedsheetID;
    }

    public void setMedsheetID(int MedsheetID) {
        this.MedsheetID = MedsheetID;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getExternal_Facility_Id() {
        return External_Facility_Id;
    }

    public void setExternal_Facility_Id(int External_Facility_Id) {
        this.External_Facility_Id = External_Facility_Id;
    }

    public int getExternal_Patient_Id() {
        return External_Patient_Id;
    }

    public void setExternal_Patient_Id(int External_Patient_Id) {
        this.External_Patient_Id = External_Patient_Id;
    }

    public String getDownloadDate() {
        return DownloadDate;
    }

    public void setDownloadDate(String DownloadDate) {
        this.DownloadDate = DownloadDate;
    }

    public int getIsDownload() {
        return IsDownload;
    }

    public void setIsDownload(int IsDownload) {
        this.IsDownload = IsDownload;
    }
}
