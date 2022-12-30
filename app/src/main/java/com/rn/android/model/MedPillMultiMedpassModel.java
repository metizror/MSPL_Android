package com.rn.android.model;

import java.util.List;

public class MedPillMultiMedpassModel {

    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"objdetails":[{"tran_id":887024,"dose_date":"2022-04-13T00:00:00","patient_id":36905,"facility_id":348,"dose_time":"05:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":null,"updated_date":null,"dose_given_time":"","dose_given_date":"2014-10-10T00:00:00","med_type":"C         ","sig_code":"1C PO TID","sig_detail":"Take 1 capsule by mouth three times daily","internal_rx_id":1961350,"rx_number":"4214153","drug":"DOCUSATE SODIUM","patient_first":"PATIENT","patient_last":"TEST 5","DOB":"1989-11-11T00:00:00","Gender":"Male","ndc":"00904699880","IsLOAHospital":false,"IsMissedDose":false,"MedpassID":11842,"IsTaken":false}],"objdetailsInactive":[{"tran_id":887024,"dose_date":"2022-04-13T00:00:00","patient_id":36905,"facility_id":348,"dose_time":"05:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":null,"updated_date":null,"dose_given_time":"","dose_given_date":"2014-10-10T00:00:00","med_type":"C         ","sig_code":"1C PO TID","sig_detail":"Take 1 capsule by mouth three times daily","internal_rx_id":1961350,"rx_number":"4214153","drug":"DOCUSATE SODIUM","patient_first":"PATIENT","patient_last":"TEST 5","DOB":"1989-11-11T00:00:00","Gender":"Male","ndc":"00904699880","IsLOAHospital":false,"IsMissedDose":false,"MedpassID":11842,"IsTaken":false},{"tran_id":887027,"dose_date":"2022-04-13T00:00:00","patient_id":36905,"facility_id":348,"dose_time":"05:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":null,"updated_date":null,"dose_given_time":"","dose_given_date":"2014-10-10T00:00:00","med_type":"C         ","sig_code":"1T PO QHS","sig_detail":"Take 1 tablet by mouth every day at bedtime","internal_rx_id":1961350,"rx_number":"4214159","drug":"TRAZODONE HCL","patient_first":"PATIENT","patient_last":"TEST 5","DOB":"1989-11-11T00:00:00","Gender":"Male","ndc":"50111056002","IsLOAHospital":false,"IsMissedDose":false,"MedpassID":11862,"IsTaken":false}],"IsLOAHospital":false,"Msg":null,"MissdoseMsg":"","FetureMedpassMsg":null,"IsTaken":false,"IsLOA":false,"IsLOAType":null}
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * objdetails : [{"tran_id":887024,"dose_date":"2022-04-13T00:00:00","patient_id":36905,"facility_id":348,"dose_time":"05:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":null,"updated_date":null,"dose_given_time":"","dose_given_date":"2014-10-10T00:00:00","med_type":"C         ","sig_code":"1C PO TID","sig_detail":"Take 1 capsule by mouth three times daily","internal_rx_id":1961350,"rx_number":"4214153","drug":"DOCUSATE SODIUM","patient_first":"PATIENT","patient_last":"TEST 5","DOB":"1989-11-11T00:00:00","Gender":"Male","ndc":"00904699880","IsLOAHospital":false,"IsMissedDose":false,"MedpassID":11842,"IsTaken":false}]
     * objdetailsInactive : [{"tran_id":887024,"dose_date":"2022-04-13T00:00:00","patient_id":36905,"facility_id":348,"dose_time":"05:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":null,"updated_date":null,"dose_given_time":"","dose_given_date":"2014-10-10T00:00:00","med_type":"C         ","sig_code":"1C PO TID","sig_detail":"Take 1 capsule by mouth three times daily","internal_rx_id":1961350,"rx_number":"4214153","drug":"DOCUSATE SODIUM","patient_first":"PATIENT","patient_last":"TEST 5","DOB":"1989-11-11T00:00:00","Gender":"Male","ndc":"00904699880","IsLOAHospital":false,"IsMissedDose":false,"MedpassID":11842,"IsTaken":false},{"tran_id":887027,"dose_date":"2022-04-13T00:00:00","patient_id":36905,"facility_id":348,"dose_time":"05:00 PM","dose_Qty":1,"dose_status":"N         ","note":"","updated_by":null,"updated_date":null,"dose_given_time":"","dose_given_date":"2014-10-10T00:00:00","med_type":"C         ","sig_code":"1T PO QHS","sig_detail":"Take 1 tablet by mouth every day at bedtime","internal_rx_id":1961350,"rx_number":"4214159","drug":"TRAZODONE HCL","patient_first":"PATIENT","patient_last":"TEST 5","DOB":"1989-11-11T00:00:00","Gender":"Male","ndc":"50111056002","IsLOAHospital":false,"IsMissedDose":false,"MedpassID":11862,"IsTaken":false}]
     * IsLOAHospital : false
     * Msg : null
     * MissdoseMsg :
     * FetureMedpassMsg : null
     * IsTaken : false
     * IsLOA : false
     * IsLOAType : null
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
        private boolean IsLOAHospital;
        private Object Msg;
        private String MissdoseMsg;
        private Object FetureMedpassMsg;
        private boolean IsTaken;
        private boolean IsLOA;
        private Object IsLOAType;
        private boolean IsErly;
        private boolean IsLate;
        /**
         * tran_id : 887024
         * dose_date : 2022-04-13T00:00:00
         * patient_id : 36905
         * facility_id : 348
         * dose_time : 05:00 PM
         * dose_Qty : 1.0
         * dose_status : N
         * note :
         * updated_by : null
         * updated_date : null
         * dose_given_time :
         * dose_given_date : 2014-10-10T00:00:00
         * med_type : C
         * sig_code : 1C PO TID
         * sig_detail : Take 1 capsule by mouth three times daily
         * internal_rx_id : 1961350
         * rx_number : 4214153
         * drug : DOCUSATE SODIUM
         * patient_first : PATIENT
         * patient_last : TEST 5
         * DOB : 1989-11-11T00:00:00
         * Gender : Male
         * ndc : 00904699880
         * IsLOAHospital : false
         * IsMissedDose : false
         * MedpassID : 11842
         * IsTaken : false
         */

        private List<ObjdetailsBean> objdetails;
        /**
         * tran_id : 887024
         * dose_date : 2022-04-13T00:00:00
         * patient_id : 36905
         * facility_id : 348
         * dose_time : 05:00 PM
         * dose_Qty : 1.0
         * dose_status : N
         * note :
         * updated_by : null
         * updated_date : null
         * dose_given_time :
         * dose_given_date : 2014-10-10T00:00:00
         * med_type : C
         * sig_code : 1C PO TID
         * sig_detail : Take 1 capsule by mouth three times daily
         * internal_rx_id : 1961350
         * rx_number : 4214153
         * drug : DOCUSATE SODIUM
         * patient_first : PATIENT
         * patient_last : TEST 5
         * DOB : 1989-11-11T00:00:00
         * Gender : Male
         * ndc : 00904699880
         * IsLOAHospital : false
         * IsMissedDose : false
         * MedpassID : 11842
         * IsTaken : false
         */

        private List<ObjdetailsInactiveBean> objdetailsInactive;

        public boolean isErly() {
            return IsErly;
        }

        public void setErly(boolean erly) {
            IsErly = erly;
        }

        public boolean isLate() {
            return IsLate;
        }

        public void setLate(boolean late) {
            IsLate = late;
        }

        public boolean isIsLOAHospital() {
            return IsLOAHospital;
        }

        public void setIsLOAHospital(boolean IsLOAHospital) {
            this.IsLOAHospital = IsLOAHospital;
        }

        public Object getMsg() {
            return Msg;
        }

        public void setMsg(Object Msg) {
            this.Msg = Msg;
        }

        public String getMissdoseMsg() {
            return MissdoseMsg;
        }

        public void setMissdoseMsg(String MissdoseMsg) {
            this.MissdoseMsg = MissdoseMsg;
        }

        public Object getFetureMedpassMsg() {
            return FetureMedpassMsg;
        }

        public void setFetureMedpassMsg(Object FetureMedpassMsg) {
            this.FetureMedpassMsg = FetureMedpassMsg;
        }

        public boolean isIsTaken() {
            return IsTaken;
        }

        public void setIsTaken(boolean IsTaken) {
            this.IsTaken = IsTaken;
        }

        public boolean isIsLOA() {
            return IsLOA;
        }

        public void setIsLOA(boolean IsLOA) {
            this.IsLOA = IsLOA;
        }

        public Object getIsLOAType() {
            return IsLOAType;
        }

        public void setIsLOAType(Object IsLOAType) {
            this.IsLOAType = IsLOAType;
        }

        public List<ObjdetailsBean> getObjdetails() {
            return objdetails;
        }

        public void setObjdetails(List<ObjdetailsBean> objdetails) {
            this.objdetails = objdetails;
        }

        public List<ObjdetailsInactiveBean> getObjdetailsInactive() {
            return objdetailsInactive;
        }

        public void setObjdetailsInactive(List<ObjdetailsInactiveBean> objdetailsInactive) {
            this.objdetailsInactive = objdetailsInactive;
        }

        public static class ObjdetailsBean {
            private int tran_id;
            private String dose_date;
            private int patient_id;
            private int facility_id;
            private String dose_time;
            private double dose_Qty;
            private String dose_status;
            private String note;
            private Object updated_by;
            private Object updated_date;
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
            private String ndc;
            private boolean IsLOAHospital;
            private boolean IsMissedDose;
            private int MedpassID;
            private boolean IsTaken;
            private boolean IsErly;
            private boolean IsLate;

            public boolean isErly() {
                return IsErly;
            }

            public void setErly(boolean erly) {
                IsErly = erly;
            }

            public boolean isLate() {
                return IsLate;
            }

            public void setLate(boolean late) {
                IsLate = late;
            }

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

            public Object getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(Object updated_by) {
                this.updated_by = updated_by;
            }

            public Object getUpdated_date() {
                return updated_date;
            }

            public void setUpdated_date(Object updated_date) {
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

            public String getNdc() {
                return ndc;
            }

            public void setNdc(String ndc) {
                this.ndc = ndc;
            }

            public boolean isIsLOAHospital() {
                return IsLOAHospital;
            }

            public void setIsLOAHospital(boolean IsLOAHospital) {
                this.IsLOAHospital = IsLOAHospital;
            }

            public boolean isIsMissedDose() {
                return IsMissedDose;
            }

            public void setIsMissedDose(boolean IsMissedDose) {
                this.IsMissedDose = IsMissedDose;
            }

            public int getMedpassID() {
                return MedpassID;
            }

            public void setMedpassID(int MedpassID) {
                this.MedpassID = MedpassID;
            }

            public boolean isIsTaken() {
                return IsTaken;
            }

            public void setIsTaken(boolean IsTaken) {
                this.IsTaken = IsTaken;
            }
        }

        public static class ObjdetailsInactiveBean {
            private int tran_id;
            private String dose_date;
            private int patient_id;
            private int facility_id;
            private String dose_time;
            private double dose_Qty;
            private String dose_status;
            private String note;
            private Object updated_by;
            private Object updated_date;
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
            private String ndc;
            private boolean IsLOAHospital;
            private boolean IsMissedDose;
            private int MedpassID;
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

            public Object getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(Object updated_by) {
                this.updated_by = updated_by;
            }

            public Object getUpdated_date() {
                return updated_date;
            }

            public void setUpdated_date(Object updated_date) {
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

            public String getNdc() {
                return ndc;
            }

            public void setNdc(String ndc) {
                this.ndc = ndc;
            }

            public boolean isIsLOAHospital() {
                return IsLOAHospital;
            }

            public void setIsLOAHospital(boolean IsLOAHospital) {
                this.IsLOAHospital = IsLOAHospital;
            }

            public boolean isIsMissedDose() {
                return IsMissedDose;
            }

            public void setIsMissedDose(boolean IsMissedDose) {
                this.IsMissedDose = IsMissedDose;
            }

            public int getMedpassID() {
                return MedpassID;
            }

            public void setMedpassID(int MedpassID) {
                this.MedpassID = MedpassID;
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
