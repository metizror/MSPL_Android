package com.rn.android.model;

import java.util.List;

public class AlloyHospital {
    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"LOAHospital_ID":1,"LOAHospitalType":"LOA"},{"LOAHospital_ID":2,"LOAHospitalType":"In hospital"},{"LOAHospital_ID":3,"LOAHospitalType":"Out of home"},{"LOAHospital_ID":4,"LOAHospitalType":"Other"}]
     */

    private int ResponseStatus;
    private String ErrorMessage;
    private String Message;
    /**
     * LOAHospital_ID : 1
     * LOAHospitalType : LOA
     */

    private List<LOAHospital> loaHospitalList;

    public int getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(int ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<LOAHospital> getData() {
        return loaHospitalList;
    }

    public void setData(List<LOAHospital> loaHospitalList) {
        this.loaHospitalList = loaHospitalList;
    }

    public static class LOAHospital {
        private int LOAHospital_ID;
        private String LOAHospitalType;

        public int getLOAHospital_ID() {
            return LOAHospital_ID;
        }

        public void setLOAHospital_ID(int LOAHospital_ID) {
            this.LOAHospital_ID = LOAHospital_ID;
        }

        public String getLOAHospitalType() {
            return LOAHospitalType;
        }

        public void setLOAHospitalType(String LOAHospitalType) {
            this.LOAHospitalType = LOAHospitalType;
        }
    }




    /*private int id;
    private String name;
    private boolean isChecked = false;

    public AlloyHospital() {
    }

    public AlloyHospital(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }*/
}
