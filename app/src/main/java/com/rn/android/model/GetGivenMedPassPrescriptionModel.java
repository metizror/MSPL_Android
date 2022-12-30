package com.rn.android.model;

import java.util.List;

public class GetGivenMedPassPrescriptionModel {


    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"objdetails":[{"tran_id":886920,"dose_date":"04/11/22","patient_id":1946106,"facility_id":348,"dose_time":"06:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":"","updated_date":"2014-10-10T00:00:00","dose_given_time":"","dose_given_date":"","med_type":"C         ","sig_code":"1t","sig_detail":"1","internal_rx_id":1956008,"rx_number":"4206923","drug":"CLOBAZAM   2.5 mg/mL    ORAL SUSP","patient_first":"PATIENT","patient_last":"TEST 8","DOB":"2014-10-10T00:00:00","Gender":"Male","giventime":"","ndc":"66689005804","MedImagePath":"","IsLOAHospital":false,"LOAHospitalNote":"","IsMissedDose":true,"UserName":"","LOAType":"","LOAHospitalDate":"","LOAHospitalTime":"","IsPRN":false,"IsTaken":false},{"tran_id":886919,"dose_date":"04/11/22","patient_id":1946106,"facility_id":348,"dose_time":"06:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":"","updated_date":"2014-10-10T00:00:00","dose_given_time":"","dose_given_date":"","med_type":"C         ","sig_code":"1t","sig_detail":"1","internal_rx_id":1956007,"rx_number":"4206922","drug":"LORazepam 2 mg tablet","patient_first":"PATIENT","patient_last":"TEST 8","DOB":"2014-10-10T00:00:00","Gender":"Male","giventime":"","ndc":"69315090610","MedImagePath":"","IsLOAHospital":false,"LOAHospitalNote":"","IsMissedDose":true,"UserName":"","LOAType":"","LOAHospitalDate":"","LOAHospitalTime":"","IsPRN":false,"IsTaken":false}],"IsMissedDose":true,"IsTaken":false}
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * objdetails : [{"tran_id":886920,"dose_date":"04/11/22","patient_id":1946106,"facility_id":348,"dose_time":"06:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":"","updated_date":"2014-10-10T00:00:00","dose_given_time":"","dose_given_date":"","med_type":"C         ","sig_code":"1t","sig_detail":"1","internal_rx_id":1956008,"rx_number":"4206923","drug":"CLOBAZAM   2.5 mg/mL    ORAL SUSP","patient_first":"PATIENT","patient_last":"TEST 8","DOB":"2014-10-10T00:00:00","Gender":"Male","giventime":"","ndc":"66689005804","MedImagePath":"","IsLOAHospital":false,"LOAHospitalNote":"","IsMissedDose":true,"UserName":"","LOAType":"","LOAHospitalDate":"","LOAHospitalTime":"","IsPRN":false,"IsTaken":false},{"tran_id":886919,"dose_date":"04/11/22","patient_id":1946106,"facility_id":348,"dose_time":"06:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":"","updated_date":"2014-10-10T00:00:00","dose_given_time":"","dose_given_date":"","med_type":"C         ","sig_code":"1t","sig_detail":"1","internal_rx_id":1956007,"rx_number":"4206922","drug":"LORazepam 2 mg tablet","patient_first":"PATIENT","patient_last":"TEST 8","DOB":"2014-10-10T00:00:00","Gender":"Male","giventime":"","ndc":"69315090610","MedImagePath":"","IsLOAHospital":false,"LOAHospitalNote":"","IsMissedDose":true,"UserName":"","LOAType":"","LOAHospitalDate":"","LOAHospitalTime":"","IsPRN":false,"IsTaken":false}]
     * IsMissedDose : true
     * IsTaken : false
     */

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
        private boolean IsMissedDose;
        private boolean IsTaken;
        /**
         * tran_id : 886920
         * dose_date : 04/11/22
         * patient_id : 1946106
         * facility_id : 348
         * dose_time : 06:00 PM
         * dose_Qty : 1.0
         * dose_status : N
         * note :
         * updated_by :
         * updated_date : 2014-10-10T00:00:00
         * dose_given_time :
         * dose_given_date :
         * med_type : C
         * sig_code : 1t
         * sig_detail : 1
         * internal_rx_id : 1956008
         * rx_number : 4206923
         * drug : CLOBAZAM   2.5 mg/mL    ORAL SUSP
         * patient_first : PATIENT
         * patient_last : TEST 8
         * DOB : 2014-10-10T00:00:00
         * Gender : Male
         * giventime :
         * ndc : 66689005804
         * MedImagePath :
         * IsLOAHospital : false
         * LOAHospitalNote :
         * IsMissedDose : true
         * UserName :
         * LOAType :
         * LOAHospitalDate :
         * LOAHospitalTime :
         * IsPRN : false
         * IsTaken : false
         */

        private List<ModelTakenPrescription> objdetails;

        public boolean isIsMissedDose() {
            return IsMissedDose;
        }

        public void setIsMissedDose(boolean IsMissedDose) {
            this.IsMissedDose = IsMissedDose;
        }

        public boolean isIsTaken() {
            return IsTaken;
        }

        public void setIsTaken(boolean IsTaken) {
            this.IsTaken = IsTaken;
        }

        public List<ModelTakenPrescription> getObjdetails() {
            return objdetails;
        }

        public void setObjdetails(List<ModelTakenPrescription> objdetails) {
            this.objdetails = objdetails;
        }

        public static class ModelTakenPrescription {
            private int tran_id;
            private String dose_date;
            private int patient_id;
            private int facility_id;
            private String dose_time;
            private double dose_Qty;
            private String dose_status;
            private String note;
            private String updated_by;
            private String updated_date;
            private String dose_given_time;
            private String dose_given_date;
            private String med_type;
            private String sig_code;
            private String sig_detail;
            private int internal_rx_id;
            private String rx_number;
            private String drug;
            private String patient_first;
            private String patient_last;
            private String DOB;
            private String Gender;
            private String giventime;
            private String ndc;
            private String MedImagePath;
            private boolean IsLOAHospital;
            private String LOAHospitalNote;
            private boolean IsMissedDose;
            private String UserName;
            private String LOAType;
            private String LOAHospitalDate;
            private String LOAHospitalTime;
            private boolean IsPRN;
            private boolean IsTaken;

            public int getTran_id() {
                return tran_id;
            }

            public void setTran_id(int tran_id) {
                this.tran_id = tran_id;
            }

            public String getDose_date() {
                return dose_date;
            }

            public void setDose_date(String dose_date) {
                this.dose_date = dose_date;
            }

            public int getPatient_id() {
                return patient_id;
            }

            public void setPatient_id(int patient_id) {
                this.patient_id = patient_id;
            }

            public int getFacility_id() {
                return facility_id;
            }

            public void setFacility_id(int facility_id) {
                this.facility_id = facility_id;
            }

            public String getDose_time() {
                return dose_time;
            }

            public void setDose_time(String dose_time) {
                this.dose_time = dose_time;
            }

            public double getDose_Qty() {
                return dose_Qty;
            }

            public void setDose_Qty(double dose_Qty) {
                this.dose_Qty = dose_Qty;
            }

            public String getDose_status() {
                return dose_status;
            }

            public void setDose_status(String dose_status) {
                this.dose_status = dose_status;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(String updated_by) {
                this.updated_by = updated_by;
            }

            public String getUpdated_date() {
                return updated_date;
            }

            public void setUpdated_date(String updated_date) {
                this.updated_date = updated_date;
            }

            public String getDose_given_time() {
                return dose_given_time;
            }

            public void setDose_given_time(String dose_given_time) {
                this.dose_given_time = dose_given_time;
            }

            public String getDose_given_date() {
                return dose_given_date;
            }

            public void setDose_given_date(String dose_given_date) {
                this.dose_given_date = dose_given_date;
            }

            public String getMed_type() {
                return med_type;
            }

            public void setMed_type(String med_type) {
                this.med_type = med_type;
            }

            public String getSig_code() {
                return sig_code;
            }

            public void setSig_code(String sig_code) {
                this.sig_code = sig_code;
            }

            public String getSig_detail() {
                return sig_detail;
            }

            public void setSig_detail(String sig_detail) {
                this.sig_detail = sig_detail;
            }

            public int getInternal_rx_id() {
                return internal_rx_id;
            }

            public void setInternal_rx_id(int internal_rx_id) {
                this.internal_rx_id = internal_rx_id;
            }

            public String getRx_number() {
                return rx_number;
            }

            public void setRx_number(String rx_number) {
                this.rx_number = rx_number;
            }

            public String getDrug() {
                return drug;
            }

            public void setDrug(String drug) {
                this.drug = drug;
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

            public String getDOB() {
                return DOB;
            }

            public void setDOB(String DOB) {
                this.DOB = DOB;
            }

            public String getGender() {
                return Gender;
            }

            public void setGender(String Gender) {
                this.Gender = Gender;
            }

            public String getGiventime() {
                return giventime;
            }

            public void setGiventime(String giventime) {
                this.giventime = giventime;
            }

            public String getNdc() {
                return ndc;
            }

            public void setNdc(String ndc) {
                this.ndc = ndc;
            }

            public String getMedImagePath() {
                return MedImagePath;
            }

            public void setMedImagePath(String MedImagePath) {
                this.MedImagePath = MedImagePath;
            }

            public boolean isIsLOAHospital() {
                return IsLOAHospital;
            }

            public void setIsLOAHospital(boolean IsLOAHospital) {
                this.IsLOAHospital = IsLOAHospital;
            }

            public String getLOAHospitalNote() {
                return LOAHospitalNote;
            }

            public void setLOAHospitalNote(String LOAHospitalNote) {
                this.LOAHospitalNote = LOAHospitalNote;
            }

            public boolean isIsMissedDose() {
                return IsMissedDose;
            }

            public void setIsMissedDose(boolean IsMissedDose) {
                this.IsMissedDose = IsMissedDose;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getLOAType() {
                return LOAType;
            }

            public void setLOAType(String LOAType) {
                this.LOAType = LOAType;
            }

            public String getLOAHospitalDate() {
                return LOAHospitalDate;
            }

            public void setLOAHospitalDate(String LOAHospitalDate) {
                this.LOAHospitalDate = LOAHospitalDate;
            }

            public String getLOAHospitalTime() {
                return LOAHospitalTime;
            }

            public void setLOAHospitalTime(String LOAHospitalTime) {
                this.LOAHospitalTime = LOAHospitalTime;
            }

            public boolean isIsPRN() {
                return IsPRN;
            }

            public void setIsPRN(boolean IsPRN) {
                this.IsPRN = IsPRN;
            }

            public boolean isIsTaken() {
                return IsTaken;
            }

            public void setIsTaken(boolean IsTaken) {
                this.IsTaken = IsTaken;
            }
        }
    }
}
