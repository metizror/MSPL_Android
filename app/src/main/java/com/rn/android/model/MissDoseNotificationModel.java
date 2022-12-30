package com.rn.android.model;

import java.util.List;

public class MissDoseNotificationModel {


    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"BENZTROPINE MESYLATE   1 MG    TABLET"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"FLOVENT HFA"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"FUROSEMIDE   40 MG    TABLET"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"LISINOPRIL   20 MG    TABLET"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"lisinopriL 10 mg tablet"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"LORATADINE   10 MG    TABLET"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"ONE DAILY WITH IRON"},{"Patientname":"TEST 1 PATIENT","DoseDate":"2022-04-13T00:00:00","strDoseDate":"04/13/22","Dosetime":"08:00:00.000","Drug":"QUETIAPINE FUMARATE"}]
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * Patientname : TEST 1 PATIENT
     * DoseDate : 2022-04-13T00:00:00
     * strDoseDate : 04/13/22
     * Dosetime : 08:00:00.000
     * Drug : BENZTROPINE MESYLATE   1 MG    TABLET
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
        private String Patientname;
        private String DoseDate;
        private String strDoseDate;
        private String Dosetime;
        private String Drug;

        public String getPatientname() {
            return Patientname;
        }

        public void setPatientname(String Patientname) {
            this.Patientname = Patientname;
        }

        public String getDoseDate() {
            return DoseDate;
        }

        public void setDoseDate(String DoseDate) {
            this.DoseDate = DoseDate;
        }

        public String getStrDoseDate() {
            return strDoseDate;
        }

        public void setStrDoseDate(String strDoseDate) {
            this.strDoseDate = strDoseDate;
        }

        public String getDosetime() {
            return Dosetime;
        }

        public void setDosetime(String Dosetime) {
            this.Dosetime = Dosetime;
        }

        public String getDrug() {
            return Drug;
        }

        public void setDrug(String Drug) {
            this.Drug = Drug;
        }
    }
}
