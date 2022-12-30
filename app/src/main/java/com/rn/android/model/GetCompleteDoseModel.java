package com.rn.android.model;

import java.util.List;

/**
 * Created by hp on 10/10/17.
 */

public class GetCompleteDoseModel {


    /**
     * GroupID : 19
     * FacilityID : 290
     * ExFacilityID : 971
     * FacilityName : CRS- 109 NELSON ST - 971
     * Address : 109 NELSON ST
     * City : HOLDEN
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    private List<GetCompleteDoseData> Data;

    public static class GetCompleteDoseData {
        private boolean Istaken;

        public boolean isIstaken() {
            return Istaken;
        }

        public void setIstaken(boolean istaken) {
            Istaken = istaken;
        }
    }

    public int getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        ResponseStatus = responseStatus;
    }

    public Object getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<GetCompleteDoseData> getData() {
        return Data;
    }

    public void setData(List<GetCompleteDoseData> data) {
        Data = data;
    }
}
