package com.rn.android.model;

import java.util.List;

public class AllPatientMedSheetModel {

    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230666021149.pdf","PatientID":"21428","PatientName":"PATIENT TEST 1"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230717971342.pdf","PatientID":"28466","PatientName":"PATIENT TEST 2"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230762901378.pdf","PatientID":"36905","PatientName":"PATIENT TEST 5"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230826083856.pdf","PatientID":"37076","PatientName":"PATIENT TEST 3"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230862121487.pdf","PatientID":"39743","PatientName":"PATIENT TEST 4"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230885834203.pdf","PatientID":"59076","PatientName":"TEST PATIENT 1"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230927332035.pdf","PatientID":"62744","PatientName":"JOSEPH 1 TEST"},{"URL":"http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230951201094.pdf","PatientID":"1946106","PatientName":"TEST TEST8"}]
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * URL : http://apitaskmanagement.metizcloud.in/exportmedsheet/637829230666021149.pdf
     * PatientID : 21428
     * PatientName : PATIENT TEST 1
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
        private String URL;
        private String PatientID;
        private String PatientName;

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getPatientID() {
            return PatientID;
        }

        public void setPatientID(String PatientID) {
            this.PatientID = PatientID;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }
    }
}
