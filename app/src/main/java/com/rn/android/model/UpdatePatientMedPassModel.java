package com.rn.android.model;

public class UpdatePatientMedPassModel {

    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : true
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    private boolean Data;

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

    public boolean isData() {
        return Data;
    }

    public void setData(boolean Data) {
        this.Data = Data;
    }
}
