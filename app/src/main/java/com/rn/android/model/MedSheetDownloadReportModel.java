package com.rn.android.model;

import java.util.List;

public class MedSheetDownloadReportModel {


    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"MedsheetID":3,"PatientName":"HUDSON, LEONARD","FileName":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637878802102642204.pdf","External_Facility_Id":2619,"External_Patient_Id":1946950,"Month":"May","Year":"2022"},{"MedsheetID":4,"PatientName":"MELLO, STEVEN","FileName":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637878802232994937.pdf","External_Facility_Id":2619,"External_Patient_Id":1946952,"Month":"May","Year":"2022"},{"MedsheetID":5,"PatientName":"OUDAN, MATTHEW","FileName":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637878802347710998.pdf","External_Facility_Id":2619,"External_Patient_Id":1947044,"Month":"May","Year":"2022"},{"MedsheetID":6,"PatientName":"DELTA-13 ANNETTE RD, NURSING","FileName":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637878802382292584.pdf","External_Facility_Id":2619,"External_Patient_Id":1954902,"Month":"May","Year":"2022"}]
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * MedsheetID : 3
     * PatientName : HUDSON, LEONARD
     * FileName : http://apitaskmanagement.metizcloud.in/exportmedsheet/637878802102642204.pdf
     * External_Facility_Id : 2619
     * External_Patient_Id : 1946950
     * Month : May
     * Year : 2022
     */

    private List<DataBean> Data;

    public int getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(int ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public Object getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(Object ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private int MedsheetID;
        private String PatientName;
        private String FileName;
        private int External_Facility_Id;
        private int External_Patient_Id;
        private String Month;
        private String Year;

        public int getMedsheetID() {
            return MedsheetID;
        }

        public void setMedsheetID(int MedsheetID) {
            this.MedsheetID = MedsheetID;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
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

        public String getMonth() {
            return Month;
        }

        public void setMonth(String Month) {
            this.Month = Month;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String Year) {
            this.Year = Year;
        }
    }
}
