package com.rn.android.model;

import java.util.List;

public class MedPassPrnDetailModel {
    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"objpatientdetails":[{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1716081,"pharmacy_order_id":"4041557","drug":"METOCLOPRAMIDE HCL 10 MG","strength_value":"10","strength":"MG","ndc":"00093220305","prescribe_date":"8/26/2020 12:00:00 AM","sig_code":"1T PO HS PRN ","sig_english":"Take 1 tablet by mouth at bedtime as needed","original_qty":"30.0000","days_supply":"30","no_of_refill":"0","morning":"0.000","noon":"0.000","evening":"0.000","night":"1.000","start_date":"8/26/2020 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","discontinue_date":null,"discontinue_note":null,"rx_expire_date":"8/26/2021 12:00:00 AM","qty":"30.0000","Msg":""},{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1716082,"pharmacy_order_id":"4041558","drug":"LORAZEPAM 1 MG","strength_value":"1","strength":"MG","ndc":"00093342601","prescribe_date":"8/26/2020 12:00:00 AM","sig_code":"1T PO Q12H PRN FOR ANXIETY ","sig_english":"Take 1 tablet by mouth every 12 hours as needed for anxiety","original_qty":"60.0000","days_supply":"30","no_of_refill":"0","morning":"1.000","noon":"0.000","evening":"0.000","night":"0.000","start_date":"8/26/2020 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","discontinue_date":null,"discontinue_note":null,"rx_expire_date":"2/22/2021 12:00:00 AM","qty":"60.0000","Msg":"This dose given by 07/14/22 10:06 AM Are you sure you want to continue?"},{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1816206,"pharmacy_order_id":"4140948","drug":"CALCIUM ANTACID 200 mg calcium (500 MG)","strength_value":"200 mg calcium","strength":"(500 MG)","ndc":"00904641292","prescribe_date":"7/20/2021 12:00:00 AM","sig_code":"1t po q6h prn ","sig_english":"Take 1 tablet by mouth every 6 hours as needed","original_qty":"60.0000","days_supply":"15","no_of_refill":"10","morning":"1.000","noon":"1.000","evening":"1.000","night":"1.000","start_date":"7/20/2021 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"1654967","discontinue_date":null,"discontinue_note":"","rx_expire_date":"7/20/2022 12:00:00 AM","qty":"60.0000","Msg":""}],"Msg":null}
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * objpatientdetails : [{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1716081,"pharmacy_order_id":"4041557","drug":"METOCLOPRAMIDE HCL 10 MG","strength_value":"10","strength":"MG","ndc":"00093220305","prescribe_date":"8/26/2020 12:00:00 AM","sig_code":"1T PO HS PRN ","sig_english":"Take 1 tablet by mouth at bedtime as needed","original_qty":"30.0000","days_supply":"30","no_of_refill":"0","morning":"0.000","noon":"0.000","evening":"0.000","night":"1.000","start_date":"8/26/2020 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","discontinue_date":null,"discontinue_note":null,"rx_expire_date":"8/26/2021 12:00:00 AM","qty":"30.0000","Msg":""},{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1716082,"pharmacy_order_id":"4041558","drug":"LORAZEPAM 1 MG","strength_value":"1","strength":"MG","ndc":"00093342601","prescribe_date":"8/26/2020 12:00:00 AM","sig_code":"1T PO Q12H PRN FOR ANXIETY ","sig_english":"Take 1 tablet by mouth every 12 hours as needed for anxiety","original_qty":"60.0000","days_supply":"30","no_of_refill":"0","morning":"1.000","noon":"0.000","evening":"0.000","night":"0.000","start_date":"8/26/2020 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","discontinue_date":null,"discontinue_note":null,"rx_expire_date":"2/22/2021 12:00:00 AM","qty":"60.0000","Msg":"This dose given by 07/14/22 10:06 AM Are you sure you want to continue?"},{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1816206,"pharmacy_order_id":"4140948","drug":"CALCIUM ANTACID 200 mg calcium (500 MG)","strength_value":"200 mg calcium","strength":"(500 MG)","ndc":"00904641292","prescribe_date":"7/20/2021 12:00:00 AM","sig_code":"1t po q6h prn ","sig_english":"Take 1 tablet by mouth every 6 hours as needed","original_qty":"60.0000","days_supply":"15","no_of_refill":"10","morning":"1.000","noon":"1.000","evening":"1.000","night":"1.000","start_date":"7/20/2021 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"1654967","discontinue_date":null,"discontinue_note":"","rx_expire_date":"7/20/2022 12:00:00 AM","qty":"60.0000","Msg":""}]
     * Msg : null
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
        private Object Msg;
        /**
         * external_patient_id : 21428
         * external_facility_id : 348
         * external_doctor_id : 35988
         * external_prescription_id : 1716081
         * pharmacy_order_id : 4041557
         * drug : METOCLOPRAMIDE HCL 10 MG
         * strength_value : 10
         * strength : MG
         * ndc : 00093220305
         * prescribe_date : 8/26/2020 12:00:00 AM
         * sig_code : 1T PO HS PRN
         * sig_english : Take 1 tablet by mouth at bedtime as needed
         * original_qty : 30.0000
         * days_supply : 30
         * no_of_refill : 0
         * morning : 0.000
         * noon : 0.000
         * evening : 0.000
         * night : 1.000
         * start_date : 8/26/2020 12:00:00 AM
         * stop_date : null
         * med_type : P
         * daw : 0
         * origin_code : 4
         * is_active : Y
         * mar_flag : Y
         * tar_flag : N
         * po_flag : Y
         * last_tran_id : 0
         * discontinue_date : null
         * discontinue_note : null
         * rx_expire_date : 8/26/2021 12:00:00 AM
         * qty : 30.0000
         * Msg :
         */

        private List<ObjpatientdetailsBean> objpatientdetails;

        public Object getMsg() {
            return Msg;
        }

        public void setMsg(Object Msg) {
            this.Msg = Msg;
        }

        public List<ObjpatientdetailsBean> getObjpatientdetails() {
            return objpatientdetails;
        }

        public void setObjpatientdetails(List<ObjpatientdetailsBean> objpatientdetails) {
            this.objpatientdetails = objpatientdetails;
        }

        public static class ObjpatientdetailsBean {
            private String external_patient_id;
            private String external_facility_id;
            private String external_doctor_id;
            private int external_prescription_id;
            private String pharmacy_order_id;
            private String drug;
            private String strength_value;
            private String strength;
            private String ndc;
            private String prescribe_date;
            private String sig_code;
            private String sig_english;
            private String original_qty;
            private String days_supply;
            private String no_of_refill;
            private String morning;
            private String noon;
            private String evening;
            private String night;
            private String start_date;
            private Object stop_date;
            private String med_type;
            private String daw;
            private String origin_code;
            private String is_active;
            private String mar_flag;
            private String tar_flag;
            private String po_flag;
            private String last_tran_id;
            private Object discontinue_date;
            private Object discontinue_note;
            private String rx_expire_date;
            private String qty;
            private String Msg;

            public String getExternal_patient_id() {
                return external_patient_id;
            }

            public void setExternal_patient_id(String external_patient_id) {
                this.external_patient_id = external_patient_id;
            }

            public String getExternal_facility_id() {
                return external_facility_id;
            }

            public void setExternal_facility_id(String external_facility_id) {
                this.external_facility_id = external_facility_id;
            }

            public String getExternal_doctor_id() {
                return external_doctor_id;
            }

            public void setExternal_doctor_id(String external_doctor_id) {
                this.external_doctor_id = external_doctor_id;
            }

            public int getExternal_prescription_id() {
                return external_prescription_id;
            }

            public void setExternal_prescription_id(int external_prescription_id) {
                this.external_prescription_id = external_prescription_id;
            }

            public String getPharmacy_order_id() {
                return pharmacy_order_id;
            }

            public void setPharmacy_order_id(String pharmacy_order_id) {
                this.pharmacy_order_id = pharmacy_order_id;
            }

            public String getDrug() {
                return drug;
            }

            public void setDrug(String drug) {
                this.drug = drug;
            }

            public String getStrength_value() {
                return strength_value;
            }

            public void setStrength_value(String strength_value) {
                this.strength_value = strength_value;
            }

            public String getStrength() {
                return strength;
            }

            public void setStrength(String strength) {
                this.strength = strength;
            }

            public String getNdc() {
                return ndc;
            }

            public void setNdc(String ndc) {
                this.ndc = ndc;
            }

            public String getPrescribe_date() {
                return prescribe_date;
            }

            public void setPrescribe_date(String prescribe_date) {
                this.prescribe_date = prescribe_date;
            }

            public String getSig_code() {
                return sig_code;
            }

            public void setSig_code(String sig_code) {
                this.sig_code = sig_code;
            }

            public String getSig_english() {
                return sig_english;
            }

            public void setSig_english(String sig_english) {
                this.sig_english = sig_english;
            }

            public String getOriginal_qty() {
                return original_qty;
            }

            public void setOriginal_qty(String original_qty) {
                this.original_qty = original_qty;
            }

            public String getDays_supply() {
                return days_supply;
            }

            public void setDays_supply(String days_supply) {
                this.days_supply = days_supply;
            }

            public String getNo_of_refill() {
                return no_of_refill;
            }

            public void setNo_of_refill(String no_of_refill) {
                this.no_of_refill = no_of_refill;
            }

            public String getMorning() {
                return morning;
            }

            public void setMorning(String morning) {
                this.morning = morning;
            }

            public String getNoon() {
                return noon;
            }

            public void setNoon(String noon) {
                this.noon = noon;
            }

            public String getEvening() {
                return evening;
            }

            public void setEvening(String evening) {
                this.evening = evening;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public Object getStop_date() {
                return stop_date;
            }

            public void setStop_date(Object stop_date) {
                this.stop_date = stop_date;
            }

            public String getMed_type() {
                return med_type;
            }

            public void setMed_type(String med_type) {
                this.med_type = med_type;
            }

            public String getDaw() {
                return daw;
            }

            public void setDaw(String daw) {
                this.daw = daw;
            }

            public String getOrigin_code() {
                return origin_code;
            }

            public void setOrigin_code(String origin_code) {
                this.origin_code = origin_code;
            }

            public String getIs_active() {
                return is_active;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public String getMar_flag() {
                return mar_flag;
            }

            public void setMar_flag(String mar_flag) {
                this.mar_flag = mar_flag;
            }

            public String getTar_flag() {
                return tar_flag;
            }

            public void setTar_flag(String tar_flag) {
                this.tar_flag = tar_flag;
            }

            public String getPo_flag() {
                return po_flag;
            }

            public void setPo_flag(String po_flag) {
                this.po_flag = po_flag;
            }

            public String getLast_tran_id() {
                return last_tran_id;
            }

            public void setLast_tran_id(String last_tran_id) {
                this.last_tran_id = last_tran_id;
            }

            public Object getDiscontinue_date() {
                return discontinue_date;
            }

            public void setDiscontinue_date(Object discontinue_date) {
                this.discontinue_date = discontinue_date;
            }

            public Object getDiscontinue_note() {
                return discontinue_note;
            }

            public void setDiscontinue_note(Object discontinue_note) {
                this.discontinue_note = discontinue_note;
            }

            public String getRx_expire_date() {
                return rx_expire_date;
            }

            public void setRx_expire_date(String rx_expire_date) {
                this.rx_expire_date = rx_expire_date;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getMsg() {
                return Msg;
            }

            public void setMsg(String Msg) {
                this.Msg = Msg;
            }
        }
    }


    /*    *//**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"objpatientdetails":[{"external_patient_id":"1946106","external_facility_id":"348","external_doctor_id":"22873","external_prescription_id":1941207,"pharmacy_order_id":"4193129","drug":"ACETAMINOPHEN 650 mg","strength_value":"650","strength":"mg","ndc":"45802073032","prescribe_date":"2/21/2022 12:00:00 AM","sig_code":"supp q6h prnf","sig_english":"Unwrap and insert 1 suppository per rectum every 6 hours as needed for fever","original_qty":"30.0000","days_supply":"30","no_of_refill":"11","morning":"1.000","noon":"1.000","evening":"1.000","night":"1.000","start_date":"2/21/2022 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"2","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"1940277","discontinue_date":null,"discontinue_note":"","rx_expire_date":"2/21/2023 12:00:00 AM","qty":"30.0000"}],"Msg":"This dose given by 07/13/22 03:35 PM Are you sure you want to continue?"}
     */
/*

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    *//**
     * objpatientdetails : [{"external_patient_id":"1946106","external_facility_id":"348","external_doctor_id":"22873","external_prescription_id":1941207,"pharmacy_order_id":"4193129","drug":"ACETAMINOPHEN 650 mg","strength_value":"650","strength":"mg","ndc":"45802073032","prescribe_date":"2/21/2022 12:00:00 AM","sig_code":"supp q6h prnf","sig_english":"Unwrap and insert 1 suppository per rectum every 6 hours as needed for fever","original_qty":"30.0000","days_supply":"30","no_of_refill":"11","morning":"1.000","noon":"1.000","evening":"1.000","night":"1.000","start_date":"2/21/2022 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"2","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"1940277","discontinue_date":null,"discontinue_note":"","rx_expire_date":"2/21/2023 12:00:00 AM","qty":"30.0000"}]
     * Msg : This dose given by 07/13/22 03:35 PM Are you sure you want to continue?
     */
    /*

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
        private String Msg;
        */
    /**
         * external_patient_id : 1946106
         * external_facility_id : 348
         * external_doctor_id : 22873
         * external_prescription_id : 1941207
         * pharmacy_order_id : 4193129
         * drug : ACETAMINOPHEN 650 mg
         * strength_value : 650
         * strength : mg
         * ndc : 45802073032
         * prescribe_date : 2/21/2022 12:00:00 AM
         * sig_code : supp q6h prnf
         * sig_english : Unwrap and insert 1 suppository per rectum every 6 hours as needed for fever
         * original_qty : 30.0000
         * days_supply : 30
         * no_of_refill : 11
         * morning : 1.000
         * noon : 1.000
         * evening : 1.000
         * night : 1.000
         * start_date : 2/21/2022 12:00:00 AM
         * stop_date : null
         * med_type : P
         * daw : 0
         * origin_code : 2
         * is_active : Y
         * mar_flag : Y
         * tar_flag : N
         * po_flag : Y
         * last_tran_id : 1940277
         * discontinue_date : null
         * discontinue_note :
         * rx_expire_date : 2/21/2023 12:00:00 AM
         * qty : 30.0000
         */
    /*

        private List<ObjpatientdetailsBean> objpatientdetails;

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }

        public List<ObjpatientdetailsBean> getObjpatientdetails() {
            return objpatientdetails;
        }

        public void setObjpatientdetails(List<ObjpatientdetailsBean> objpatientdetails) {
            this.objpatientdetails = objpatientdetails;
        }

        public static class ObjpatientdetailsBean {
            private String external_patient_id;
            private String external_facility_id;
            private String external_doctor_id;
            private int external_prescription_id;
            private String pharmacy_order_id;
            private String drug;
            private String strength_value;
            private String strength;
            private String ndc;
            private String prescribe_date;
            private String sig_code;
            private String sig_english;
            private String original_qty;
            private String days_supply;
            private String no_of_refill;
            private String morning;
            private String noon;
            private String evening;
            private String night;
            private String start_date;
            private Object stop_date;
            private String med_type;
            private String daw;
            private String origin_code;
            private String is_active;
            private String mar_flag;
            private String tar_flag;
            private String po_flag;
            private String last_tran_id;
            private Object discontinue_date;
            private String discontinue_note;
            private String rx_expire_date;
            private String qty;

            public String getExternal_patient_id() {
                return external_patient_id;
            }

            public void setExternal_patient_id(String external_patient_id) {
                this.external_patient_id = external_patient_id;
            }

            public String getExternal_facility_id() {
                return external_facility_id;
            }

            public void setExternal_facility_id(String external_facility_id) {
                this.external_facility_id = external_facility_id;
            }

            public String getExternal_doctor_id() {
                return external_doctor_id;
            }

            public void setExternal_doctor_id(String external_doctor_id) {
                this.external_doctor_id = external_doctor_id;
            }

            public int getExternal_prescription_id() {
                return external_prescription_id;
            }

            public void setExternal_prescription_id(int external_prescription_id) {
                this.external_prescription_id = external_prescription_id;
            }

            public String getPharmacy_order_id() {
                return pharmacy_order_id;
            }

            public void setPharmacy_order_id(String pharmacy_order_id) {
                this.pharmacy_order_id = pharmacy_order_id;
            }

            public String getDrug() {
                return drug;
            }

            public void setDrug(String drug) {
                this.drug = drug;
            }

            public String getStrength_value() {
                return strength_value;
            }

            public void setStrength_value(String strength_value) {
                this.strength_value = strength_value;
            }

            public String getStrength() {
                return strength;
            }

            public void setStrength(String strength) {
                this.strength = strength;
            }

            public String getNdc() {
                return ndc;
            }

            public void setNdc(String ndc) {
                this.ndc = ndc;
            }

            public String getPrescribe_date() {
                return prescribe_date;
            }

            public void setPrescribe_date(String prescribe_date) {
                this.prescribe_date = prescribe_date;
            }

            public String getSig_code() {
                return sig_code;
            }

            public void setSig_code(String sig_code) {
                this.sig_code = sig_code;
            }

            public String getSig_english() {
                return sig_english;
            }

            public void setSig_english(String sig_english) {
                this.sig_english = sig_english;
            }

            public String getOriginal_qty() {
                return original_qty;
            }

            public void setOriginal_qty(String original_qty) {
                this.original_qty = original_qty;
            }

            public String getDays_supply() {
                return days_supply;
            }

            public void setDays_supply(String days_supply) {
                this.days_supply = days_supply;
            }

            public String getNo_of_refill() {
                return no_of_refill;
            }

            public void setNo_of_refill(String no_of_refill) {
                this.no_of_refill = no_of_refill;
            }

            public String getMorning() {
                return morning;
            }

            public void setMorning(String morning) {
                this.morning = morning;
            }

            public String getNoon() {
                return noon;
            }

            public void setNoon(String noon) {
                this.noon = noon;
            }

            public String getEvening() {
                return evening;
            }

            public void setEvening(String evening) {
                this.evening = evening;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public Object getStop_date() {
                return stop_date;
            }

            public void setStop_date(Object stop_date) {
                this.stop_date = stop_date;
            }

            public String getMed_type() {
                return med_type;
            }

            public void setMed_type(String med_type) {
                this.med_type = med_type;
            }

            public String getDaw() {
                return daw;
            }

            public void setDaw(String daw) {
                this.daw = daw;
            }

            public String getOrigin_code() {
                return origin_code;
            }

            public void setOrigin_code(String origin_code) {
                this.origin_code = origin_code;
            }

            public String getIs_active() {
                return is_active;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public String getMar_flag() {
                return mar_flag;
            }

            public void setMar_flag(String mar_flag) {
                this.mar_flag = mar_flag;
            }

            public String getTar_flag() {
                return tar_flag;
            }

            public void setTar_flag(String tar_flag) {
                this.tar_flag = tar_flag;
            }

            public String getPo_flag() {
                return po_flag;
            }

            public void setPo_flag(String po_flag) {
                this.po_flag = po_flag;
            }

            public String getLast_tran_id() {
                return last_tran_id;
            }

            public void setLast_tran_id(String last_tran_id) {
                this.last_tran_id = last_tran_id;
            }

            public Object getDiscontinue_date() {
                return discontinue_date;
            }

            public void setDiscontinue_date(Object discontinue_date) {
                this.discontinue_date = discontinue_date;
            }

            public String getDiscontinue_note() {
                return discontinue_note;
            }

            public void setDiscontinue_note(String discontinue_note) {
                this.discontinue_note = discontinue_note;
            }

            public String getRx_expire_date() {
                return rx_expire_date;
            }

            public void setRx_expire_date(String rx_expire_date) {
                this.rx_expire_date = rx_expire_date;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }
        }*/
//    }

    /*
    *//**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1716081,"pharmacy_order_id":"4041557","drug":"METOCLOPRAMIDE HCL 10 MG","strength_value":"10","strength":"MG","ndc":"00093220305","prescribe_date":"8/26/2020 12:00:00 AM","sig_code":"1T PO HS PRN ","sig_english":"Take 1 tablet by mouth at bedtime as needed","original_qty":"30.0000","days_supply":"30","no_of_refill":"0","morning":"0.000","noon":"0.000","evening":"0.000","night":"1.000","start_date":"8/26/2020 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","discontinue_date":null,"discontinue_note":null,"rx_expire_date":"8/26/2021 12:00:00 AM","qty":"30.0000"},{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1716082,"pharmacy_order_id":"4041558","drug":"LORAZEPAM 1 MG","strength_value":"1","strength":"MG","ndc":"00093342601","prescribe_date":"8/26/2020 12:00:00 AM","sig_code":"1T PO Q12H PRN FOR ANXIETY ","sig_english":"Take 1 tablet by mouth every 12 hours as needed for anxiety","original_qty":"60.0000","days_supply":"30","no_of_refill":"0","morning":"1.000","noon":"0.000","evening":"0.000","night":"0.000","start_date":"8/26/2020 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","discontinue_date":null,"discontinue_note":null,"rx_expire_date":"2/22/2021 12:00:00 AM","qty":"60.0000"},{"external_patient_id":"21428","external_facility_id":"348","external_doctor_id":"35988","external_prescription_id":1816206,"pharmacy_order_id":"4140948","drug":"CALCIUM ANTACID 200 mg calcium (500 mg)","strength_value":"200 mg calcium","strength":"(500 mg)","ndc":"00904641292","prescribe_date":"7/20/2021 12:00:00 AM","sig_code":"1t po q6h prn ","sig_english":"Take 1 tablet by mouth every 6 hours as needed","original_qty":"60.0000","days_supply":"15","no_of_refill":"10","morning":"1.000","noon":"1.000","evening":"1.000","night":"1.000","start_date":"7/20/2021 12:00:00 AM","stop_date":null,"med_type":"P","daw":"0","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"1654967","discontinue_date":null,"discontinue_note":"","rx_expire_date":"7/20/2022 12:00:00 AM","qty":"60.0000"}]
     *//*

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    *//**
     * external_patient_id : 21428
     * external_facility_id : 348
     * external_doctor_id : 35988
     * external_prescription_id : 1716081
     * pharmacy_order_id : 4041557
     * drug : METOCLOPRAMIDE HCL 10 MG
     * strength_value : 10
     * strength : MG
     * ndc : 00093220305
     * prescribe_date : 8/26/2020 12:00:00 AM
     * sig_code : 1T PO HS PRN
     * sig_english : Take 1 tablet by mouth at bedtime as needed
     * original_qty : 30.0000
     * days_supply : 30
     * no_of_refill : 0
     * morning : 0.000
     * noon : 0.000
     * evening : 0.000
     * night : 1.000
     * start_date : 8/26/2020 12:00:00 AM
     * stop_date : null
     * med_type : P
     * daw : 0
     * origin_code : 4
     * is_active : Y
     * mar_flag : Y
     * tar_flag : N
     * po_flag : Y
     * last_tran_id : 0
     * discontinue_date : null
     * discontinue_note : null
     * rx_expire_date : 8/26/2021 12:00:00 AM
     * qty : 30.0000
     *//*

    private List<GetMedPassPRNDrug> Data;

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

    public List<GetMedPassPRNDrug> getData() {
        return Data;
    }

    public void setData(List<GetMedPassPRNDrug> Data) {
        this.Data = Data;
    }

    public static class GetMedPassPRNDrug {
        private String external_patient_id;
        private String external_facility_id;
        private String external_doctor_id;
        private int external_prescription_id;
        private String pharmacy_order_id;
        private String drug;
        private String strength_value;
        private String strength;
        private String ndc;
        private String prescribe_date;
        private String sig_code;
        private String sig_english;
        private String original_qty;
        private String days_supply;
        private String no_of_refill;
        private String morning;
        private String noon;
        private String evening;
        private String night;
        private String start_date;
        private Object stop_date;
        private String med_type;
        private String daw;
        private String origin_code;
        private String is_active;
        private String mar_flag;
        private String tar_flag;
        private String po_flag;
        private String last_tran_id;
        private Object discontinue_date;
        private Object discontinue_note;
        private String rx_expire_date;
        private String qty;

        public String getExternal_patient_id() {
            return external_patient_id;
        }

        public void setExternal_patient_id(String external_patient_id) {
            this.external_patient_id = external_patient_id;
        }

        public String getExternal_facility_id() {
            return external_facility_id;
        }

        public void setExternal_facility_id(String external_facility_id) {
            this.external_facility_id = external_facility_id;
        }

        public String getExternal_doctor_id() {
            return external_doctor_id;
        }

        public void setExternal_doctor_id(String external_doctor_id) {
            this.external_doctor_id = external_doctor_id;
        }

        public int getExternal_prescription_id() {
            return external_prescription_id;
        }

        public void setExternal_prescription_id(int external_prescription_id) {
            this.external_prescription_id = external_prescription_id;
        }

        public String getPharmacy_order_id() {
            return pharmacy_order_id;
        }

        public void setPharmacy_order_id(String pharmacy_order_id) {
            this.pharmacy_order_id = pharmacy_order_id;
        }

        public String getDrug() {
            return drug;
        }

        public void setDrug(String drug) {
            this.drug = drug;
        }

        public String getStrength_value() {
            return strength_value;
        }

        public void setStrength_value(String strength_value) {
            this.strength_value = strength_value;
        }

        public String getStrength() {
            return strength;
        }

        public void setStrength(String strength) {
            this.strength = strength;
        }

        public String getNdc() {
            return ndc;
        }

        public void setNdc(String ndc) {
            this.ndc = ndc;
        }

        public String getPrescribe_date() {
            return prescribe_date;
        }

        public void setPrescribe_date(String prescribe_date) {
            this.prescribe_date = prescribe_date;
        }

        public String getSig_code() {
            return sig_code;
        }

        public void setSig_code(String sig_code) {
            this.sig_code = sig_code;
        }

        public String getSig_english() {
            return sig_english;
        }

        public void setSig_english(String sig_english) {
            this.sig_english = sig_english;
        }

        public String getOriginal_qty() {
            return original_qty;
        }

        public void setOriginal_qty(String original_qty) {
            this.original_qty = original_qty;
        }

        public String getDays_supply() {
            return days_supply;
        }

        public void setDays_supply(String days_supply) {
            this.days_supply = days_supply;
        }

        public String getNo_of_refill() {
            return no_of_refill;
        }

        public void setNo_of_refill(String no_of_refill) {
            this.no_of_refill = no_of_refill;
        }

        public String getMorning() {
            return morning;
        }

        public void setMorning(String morning) {
            this.morning = morning;
        }

        public String getNoon() {
            return noon;
        }

        public void setNoon(String noon) {
            this.noon = noon;
        }

        public String getEvening() {
            return evening;
        }

        public void setEvening(String evening) {
            this.evening = evening;
        }

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public Object getStop_date() {
            return stop_date;
        }

        public void setStop_date(Object stop_date) {
            this.stop_date = stop_date;
        }

        public String getMed_type() {
            return med_type;
        }

        public void setMed_type(String med_type) {
            this.med_type = med_type;
        }

        public String getDaw() {
            return daw;
        }

        public void setDaw(String daw) {
            this.daw = daw;
        }

        public String getOrigin_code() {
            return origin_code;
        }

        public void setOrigin_code(String origin_code) {
            this.origin_code = origin_code;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getMar_flag() {
            return mar_flag;
        }

        public void setMar_flag(String mar_flag) {
            this.mar_flag = mar_flag;
        }

        public String getTar_flag() {
            return tar_flag;
        }

        public void setTar_flag(String tar_flag) {
            this.tar_flag = tar_flag;
        }

        public String getPo_flag() {
            return po_flag;
        }

        public void setPo_flag(String po_flag) {
            this.po_flag = po_flag;
        }

        public String getLast_tran_id() {
            return last_tran_id;
        }

        public void setLast_tran_id(String last_tran_id) {
            this.last_tran_id = last_tran_id;
        }

        public Object getDiscontinue_date() {
            return discontinue_date;
        }

        public void setDiscontinue_date(Object discontinue_date) {
            this.discontinue_date = discontinue_date;
        }

        public Object getDiscontinue_note() {
            return discontinue_note;
        }

        public void setDiscontinue_note(Object discontinue_note) {
            this.discontinue_note = discontinue_note;
        }

        public String getRx_expire_date() {
            return rx_expire_date;
        }

        public void setRx_expire_date(String rx_expire_date) {
            this.rx_expire_date = rx_expire_date;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }*/
}
