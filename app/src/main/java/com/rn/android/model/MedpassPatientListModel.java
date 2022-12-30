package com.rn.android.model;

import java.util.List;

public class MedpassPatientListModel {
    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"medpassdetails":[{"PatientId":1956810,"patient_first":"PATIENT","patient_last":"DEMO 2","Gender":"Male","DOB":"1982-07-16T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1956811,"patient_first":"PATIENT","patient_last":"DEMO 3","Gender":"Female","DOB":"1984-10-19T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1956812,"patient_first":"PATIENT","patient_last":"DEMO 4","Gender":"Male","DOB":"1961-05-25T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1956813,"patient_first":"PATIENT","patient_last":"DEMO 5","Gender":"Female","DOB":"1991-06-01T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1956818,"patient_first":"PATIENT","patient_last":"DEMO 6","Gender":"Male","DOB":"1967-08-21T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1957015,"patient_first":"PATIENT","patient_last":"DEMO 7","Gender":"Female","DOB":"1985-04-29T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1957016,"patient_first":"PATIENT","patient_last":"DEMO 8","Gender":"Male","DOB":"1976-06-22T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1957017,"patient_first":"PATIENT","patient_last":"DEMO 9","Gender":"Male","DOB":"1989-12-31T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1957018,"patient_first":"PATIENT","patient_last":"DEMO 10","Gender":"Male","DOB":"1976-06-08T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1957020,"patient_first":"PATIENT","patient_last":"DEMO 11","Gender":"Male","DOB":"1982-07-16T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1957021,"patient_first":"PATIENT","patient_last":"DEMO 12","Gender":"Male","DOB":"1992-08-31T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1962366,"patient_first":"PATIENT","patient_last":"DEMO 13","Gender":"Female","DOB":"1966-05-23T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1962458,"patient_first":"PATIENT","patient_last":"DEMO 14","Gender":"Male","DOB":"1983-07-06T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1962460,"patient_first":"PATIENT","patient_last":"DEMO 15","Gender":"Male","DOB":"1975-11-11T00:00:00","Is_PrescriptionImg":true,"dose_time":"03:00 PM"},{"PatientId":1956809,"patient_first":"PATIENT","patient_last":"DEMO 1","Gender":"Female","DOB":"1984-02-10T00:00:00","Is_PrescriptionImg":true,"dose_time":"04:00 PM"}]}
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * PatientId : 1956810
         * patient_first : PATIENT
         * patient_last : DEMO 2
         * Gender : Male
         * DOB : 1982-07-16T00:00:00
         * Is_PrescriptionImg : true
         * dose_time : 03:00 PM
         */

        private List<MedpassdetailsBean> medpassdetails;

        public List<MedpassdetailsBean> getMedpassdetails() {
            return medpassdetails;
        }

        public void setMedpassdetails(List<MedpassdetailsBean> medpassdetails) {
            this.medpassdetails = medpassdetails;
        }

        public static class MedpassdetailsBean {
            private int PatientId;
            private String patient_first;
            private String patient_last;
            private String Gender;
            private String DOB;
            private boolean Is_PrescriptionImg;
            private String dose_time;

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
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

            public String getGender() {
                return Gender;
            }

            public void setGender(String Gender) {
                this.Gender = Gender;
            }

            public String getDOB() {
                return DOB;
            }

            public void setDOB(String DOB) {
                this.DOB = DOB;
            }

            public boolean isIs_PrescriptionImg() {
                return Is_PrescriptionImg;
            }

            public void setIs_PrescriptionImg(boolean Is_PrescriptionImg) {
                this.Is_PrescriptionImg = Is_PrescriptionImg;
            }

            public String getDose_time() {
                return dose_time;
            }

            public void setDose_time(String dose_time) {
                this.dose_time = dose_time;
            }
        }
    }


    /* *//**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"medpassdetails":[{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":21428},{"dose_time":"20:00:00.000","PatientID":21428}],"PatientId":21428,"patient_first":"PATIENT","patient_last":"TEST 1","Gender":"Male","DOB":"1985-02-02T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":28466},{"dose_time":"16:00:00.000","PatientID":28466},{"dose_time":"20:00:00.000","PatientID":28466}],"PatientId":28466,"patient_first":"PATIENT","patient_last":"TEST 2","Gender":"Male","DOB":"1980-02-02T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":37076},{"dose_time":"20:00:00.000","PatientID":37076}],"PatientId":37076,"patient_first":"PATIENT","patient_last":"TEST 3","Gender":"Male","DOB":"1942-10-14T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":39743},{"dose_time":"16:00:00.000","PatientID":39743},{"dose_time":"20:00:00.000","PatientID":39743}],"PatientId":39743,"patient_first":"PATIENT","patient_last":"TEST 4","Gender":"Male","DOB":"1942-10-14T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":36905},{"dose_time":"16:00:00.000","PatientID":36905},{"dose_time":"20:00:00.000","PatientID":36905}],"PatientId":36905,"patient_first":"PATIENT","patient_last":"TEST 5","Gender":"Male","DOB":"1989-11-11T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":59076},{"dose_time":"16:00:00.000","PatientID":59076},{"dose_time":"20:00:00.000","PatientID":59076}],"PatientId":59076,"patient_first":"PATIENT","patient_last":"TEST 6","Gender":"Male","DOB":"2000-01-01T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":1955389},{"dose_time":"17:00:00.000","PatientID":1955389},{"dose_time":"21:00:00.000","PatientID":1955389}],"PatientId":1955389,"patient_first":"PATIENT","patient_last":"TEST 7","Gender":"Male","DOB":"1984-01-05T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":1946106},{"dose_time":"17:00:00.000","PatientID":1946106},{"dose_time":"21:00:00.000","PatientID":1946106}],"PatientId":1946106,"patient_first":"PATIENT","patient_last":"TEST 8","Gender":"Male","DOB":"2014-10-10T00:00:00","Is_PrescriptionImg":true},{"dosetimedetails":[{"dose_time":"12:00:00.000","PatientID":1961317},{"dose_time":"21:00:00.000","PatientID":1961317}],"PatientId":1961317,"patient_first":"DATA ENTRY","patient_last":"TRAINING","Gender":"Male","DOB":"1985-01-23T00:00:00","Is_PrescriptionImg":true}]}
     *//*

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        *//**
         * dosetimedetails : [{"dose_time":"12:00:00.000","PatientID":21428},{"dose_time":"20:00:00.000","PatientID":21428}]
         * PatientId : 21428
         * patient_first : PATIENT
         * patient_last : TEST 1
         * Gender : Male
         * DOB : 1985-02-02T00:00:00
         * Is_PrescriptionImg : true
         *//*

        private List<MedpassdetailsBean> medpassdetails;

        public List<MedpassdetailsBean> getMedpassdetails() {
            return medpassdetails;
        }

        public void setMedpassdetails(List<MedpassdetailsBean> medpassdetails) {
            this.medpassdetails = medpassdetails;
        }

        public static class MedpassdetailsBean {
            private int PatientId;
            private String patient_first;
            private String patient_last;
            private String Gender;
            private String DOB;
            private boolean Is_PrescriptionImg;
            *//**
             * dose_time : 12:00:00.000
             * PatientID : 21428
             *//*

            private List<DosetimedetailsBean> dosetimedetails;

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
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

            public String getGender() {
                return Gender;
            }

            public void setGender(String Gender) {
                this.Gender = Gender;
            }

            public String getDOB() {
                return DOB;
            }

            public void setDOB(String DOB) {
                this.DOB = DOB;
            }

            public boolean isIs_PrescriptionImg() {
                return Is_PrescriptionImg;
            }

            public void setIs_PrescriptionImg(boolean Is_PrescriptionImg) {
                this.Is_PrescriptionImg = Is_PrescriptionImg;
            }

            public List<DosetimedetailsBean> getDosetimedetails() {
                return dosetimedetails;
            }

            public void setDosetimedetails(List<DosetimedetailsBean> dosetimedetails) {
                this.dosetimedetails = dosetimedetails;
            }

            public static class DosetimedetailsBean {
                private String dose_time;
                private int PatientID;

                public String getDose_time() {
                    return dose_time;
                }

                public void setDose_time(String dose_time) {
                    this.dose_time = dose_time;
                }

                public int getPatientID() {
                    return PatientID;
                }

                public void setPatientID(int PatientID) {
                    this.PatientID = PatientID;
                }
            }
        }
    }*/


    /*  *//**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"PatientId":21428,"patient_first":"PATIENT","patient_last":"TEST 1","Gender":"Male","DOB":"1985-02-02T00:00:00","Is_PrescriptionImg":true},{"PatientId":28466,"patient_first":"PATIENT","patient_last":"TEST 2","Gender":"Male","DOB":"1980-02-02T00:00:00","Is_PrescriptionImg":true},{"PatientId":39743,"patient_first":"PATIENT","patient_last":"TEST 4","Gender":"Male","DOB":"1942-10-14T00:00:00","Is_PrescriptionImg":true},{"PatientId":36905,"patient_first":"PATIENT","patient_last":"TEST 5","Gender":"Male","DOB":"1989-11-11T00:00:00","Is_PrescriptionImg":true},{"PatientId":59076,"patient_first":"PATIENT","patient_last":"TEST 6","Gender":"Male","DOB":"2000-01-01T00:00:00","Is_PrescriptionImg":true},{"PatientId":1946106,"patient_first":"PATIENT","patient_last":"TEST 8","Gender":"Male","DOB":"2014-10-10T00:00:00","Is_PrescriptionImg":true},{"PatientId":62744,"patient_first":"JOSEPH 1","patient_last":"TEST","Gender":"Male","DOB":"2001-10-01T00:00:00","Is_PrescriptionImg":true}]
     *//*

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    *//**
     * PatientId : 21428
     * patient_first : PATIENT
     * patient_last : TEST 1
     * Gender : Male
     * DOB : 1985-02-02T00:00:00
     * Is_PrescriptionImg : true
     *//*

    private List<ModelMedPassPatient> Data;

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

    public List<ModelMedPassPatient> getData() {
        return Data;
    }

    public void setData(List<ModelMedPassPatient> Data) {
        this.Data = Data;
    }

    public static class ModelMedPassPatient {
        private int PatientId;
        private String patient_first;
        private String patient_last;
        private String Gender;
        private String DOB;
        private boolean Is_PrescriptionImg;

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int PatientId) {
            this.PatientId = PatientId;
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

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public boolean isIs_PrescriptionImg() {
            return Is_PrescriptionImg;
        }

        public void setIs_PrescriptionImg(boolean Is_PrescriptionImg) {
            this.Is_PrescriptionImg = Is_PrescriptionImg;
        }
    }*/
}
