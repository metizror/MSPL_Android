package com.rn.android.model;

import java.util.List;

public class ChangedPrescriptionModel {

    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : [{"external_patient_id":"28466","external_facility_id":"348","external_doctor_id":"5652","external_drug_id":"32378","external_prescription_id":"1975532","pharmacy_order_id":"4226059","drug":"QUETIAPINE FUMARATE","strength_value":"300","strength":"MG","ndc":"68001018303","prescribe_date":"5/9/2022 12:00:00 AM","sig_code":"2t po qam and qpm","sig_english":"Take 2 tablets by mouth every morning and every evening","original_qty":"60.0000","qty":"60.0000","days_supply":"30","no_of_refill":"5","morning":"1.000","noon":"0.000","evening":"1.000","night":"0.000","start_date":"5/9/2022 12:00:00 AM","stop_date":null,"med_type":"C","daw":"0","last_qty_billed":"0.0000","last_qty_approved":"360.0000","tran_date":"5/9/2022 12:00:00 AM","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","last_pickedup_date":null,"discontinue_date":null,"discontinue_note":null,"rx_expire_date":"5/9/2023 12:00:00 AM","Remain_Refill":"6"},{"external_patient_id":"28466","external_facility_id":"348","external_doctor_id":"5652","external_drug_id":"32337","external_prescription_id":"1975531","pharmacy_order_id":"4226058","drug":"ARIPIPRAZOLE","strength_value":"15","strength":"MG","ndc":"62332010030","prescribe_date":"5/9/2022 12:00:00 AM","sig_code":"1t po qam","sig_english":"Take 1 tablet by mouth every morning","original_qty":"30.0000","qty":"30.0000","days_supply":"30","no_of_refill":"5","morning":"1.000","noon":"0.000","evening":"0.000","night":"0.000","start_date":"5/9/2022 12:00:00 AM","stop_date":null,"med_type":"C","daw":"0","last_qty_billed":"0.0000","last_qty_approved":"180.0000","tran_date":"5/9/2022 12:00:00 AM","origin_code":"4","is_active":"Y","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","last_pickedup_date":null,"discontinue_date":null,"discontinue_note":null,"rx_expire_date":"5/9/2023 12:00:00 AM","Remain_Refill":"6"},{"external_patient_id":"28466","external_facility_id":"348","external_doctor_id":"5652","external_drug_id":"16035","external_prescription_id":"1955275","pharmacy_order_id":"4206198","drug":"OMEPRAZOLE","strength_value":"20","strength":"mg","ndc":"00113091555","prescribe_date":"3/25/2022 12:00:00 AM","sig_code":"take 1 t po d ","sig_english":"Take 1 tablet by mouth daily","original_qty":"30.0000","qty":"30.0000","days_supply":"30","no_of_refill":"11","morning":"0.000","noon":"0.000","evening":"0.000","night":"1.000","start_date":"3/25/2022 12:00:00 AM","stop_date":null,"med_type":"C","daw":"0","last_qty_billed":"0.0000","last_qty_approved":"360.0000","tran_date":"3/25/2022 12:00:00 AM","origin_code":"4","is_active":"N","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"1569854","last_pickedup_date":null,"discontinue_date":"5/9/2022 12:00:00 AM","discontinue_note":"dc per jugrag","rx_expire_date":"3/25/2023 12:00:00 AM","Remain_Refill":"12"},{"external_patient_id":"28466","external_facility_id":"348","external_doctor_id":"5652","external_drug_id":"26175","external_prescription_id":"1955271","pharmacy_order_id":"4206194","drug":"ACETAMINOPHEN","strength_value":"325","strength":"mg","ndc":"00536132710","prescribe_date":"3/25/2022 12:00:00 AM","sig_code":"1t po qid ","sig_english":"Take 1 tablet by mouth four times daily","original_qty":"120.0000","qty":"120.0000","days_supply":"30","no_of_refill":"0","morning":"1.000","noon":"1.000","evening":"1.000","night":"1.000","start_date":"3/25/2022 12:00:00 AM","stop_date":null,"med_type":"C","daw":"0","last_qty_billed":"0.0000","last_qty_approved":"120.0000","tran_date":"3/25/2022 12:00:00 AM","origin_code":"4","is_active":"N","mar_flag":"Y","tar_flag":"N","po_flag":"Y","last_tran_id":"0","last_pickedup_date":null,"discontinue_date":"5/9/2022 12:00:00 AM","discontinue_note":"dc per jugrag","rx_expire_date":"3/25/2023 12:00:00 AM","Remain_Refill":"1"}]
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * external_patient_id : 28466
     * external_facility_id : 348
     * external_doctor_id : 5652
     * external_drug_id : 32378
     * external_prescription_id : 1975532
     * pharmacy_order_id : 4226059
     * drug : QUETIAPINE FUMARATE
     * strength_value : 300
     * strength : MG
     * ndc : 68001018303
     * prescribe_date : 5/9/2022 12:00:00 AM
     * sig_code : 2t po qam and qpm
     * sig_english : Take 2 tablets by mouth every morning and every evening
     * original_qty : 60.0000
     * qty : 60.0000
     * days_supply : 30
     * no_of_refill : 5
     * morning : 1.000
     * noon : 0.000
     * evening : 1.000
     * night : 0.000
     * start_date : 5/9/2022 12:00:00 AM
     * stop_date : null
     * med_type : C
     * daw : 0
     * last_qty_billed : 0.0000
     * last_qty_approved : 360.0000
     * tran_date : 5/9/2022 12:00:00 AM
     * origin_code : 4
     * is_active : Y
     * mar_flag : Y
     * tar_flag : N
     * po_flag : Y
     * last_tran_id : 0
     * last_pickedup_date : null
     * discontinue_date : null
     * discontinue_note : null
     * rx_expire_date : 5/9/2023 12:00:00 AM
     * Remain_Refill : 6
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
        private String external_patient_id;
        private String external_facility_id;
        private String external_doctor_id;
        private String external_drug_id;
        private String external_prescription_id;
        private String pharmacy_order_id;
        private String drug;
        private String strength_value;
        private String strength;
        private String ndc;
        private String prescribe_date;
        private String sig_code;
        private String sig_english;
        private String original_qty;
        private String qty;
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
        private String last_qty_billed;
        private String last_qty_approved;
        private String tran_date;
        private String origin_code;
        private String is_active;
        private String mar_flag;
        private String tar_flag;
        private String po_flag;
        private String last_tran_id;
        private Object last_pickedup_date;
        private Object discontinue_date;
        private Object discontinue_note;
        private String rx_expire_date;
        private String Remain_Refill;
        private String DrugImage;
        private String color;
        private String shape;
        private String imprint;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getShape() {
            return shape;
        }

        public void setShape(String shape) {
            this.shape = shape;
        }

        public String getImprint() {
            return imprint;
        }

        public void setImprint(String imprint) {
            this.imprint = imprint;
        }

        public String getDrugImage() {
            return DrugImage;
        }

        public void setDrugImage(String drugImage) {
            DrugImage = drugImage;
        }

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

        public String getExternal_drug_id() {
            return external_drug_id;
        }

        public void setExternal_drug_id(String external_drug_id) {
            this.external_drug_id = external_drug_id;
        }

        public String getExternal_prescription_id() {
            return external_prescription_id;
        }

        public void setExternal_prescription_id(String external_prescription_id) {
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

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
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

        public String getLast_qty_billed() {
            return last_qty_billed;
        }

        public void setLast_qty_billed(String last_qty_billed) {
            this.last_qty_billed = last_qty_billed;
        }

        public String getLast_qty_approved() {
            return last_qty_approved;
        }

        public void setLast_qty_approved(String last_qty_approved) {
            this.last_qty_approved = last_qty_approved;
        }

        public String getTran_date() {
            return tran_date;
        }

        public void setTran_date(String tran_date) {
            this.tran_date = tran_date;
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

        public Object getLast_pickedup_date() {
            return last_pickedup_date;
        }

        public void setLast_pickedup_date(Object last_pickedup_date) {
            this.last_pickedup_date = last_pickedup_date;
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

        public String getRemain_Refill() {
            return Remain_Refill;
        }

        public void setRemain_Refill(String Remain_Refill) {
            this.Remain_Refill = Remain_Refill;
        }
    }
}
