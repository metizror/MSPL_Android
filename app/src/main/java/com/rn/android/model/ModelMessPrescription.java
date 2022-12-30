package com.rn.android.model;

public class ModelMessPrescription {
    /**
     * tran_id : 0
     * dose_date : null
     * patient_id : 0
     * facility_id : 0
     * dose_time : null
     * dose_Qty : 0.0
     * dose_status : null
     * note : null
     * updated_by : null
     * updated_date : null
     * dose_given_time : null
     * dose_given_date : null
     * med_type : P
     * sig_code : TAKE 2 PUFFS PO Q6H PRN F SOB
     * sig_detail : null
     * internal_rx_id : 0
     * rx_number : null
     * drug : ALBUTEROL SULFATE HFA 90 mcg/actuation
     * patient_first : null
     * patient_last : null
     * DOB : null
     * Gender : null
     * ndc : 68180096301
     * IsLOAHospital : false
     * IsMissedDose : false
     * MedpassID : 0
     * IsTaken : false
     * external_patient_id : 36905
     * external_facility_id : 348
     * external_doctor_id : 22873
     * external_prescription_id : 1963533
     * pharmacy_order_id : 4214303
     * strength_value : 90
     * strength : mcg/actuation
     * prescribe_date : 4/12/2022 12:00:00 AM
     * sig_english : Take 2 puffs by mouth every 6 hours as needed for shortness of breath
     * original_qty : 8.5000
     * days_supply : 17
     * no_of_refill : 0
     * morning : 2.000
     * noon : 2.000
     * evening : 2.000
     * night : 2.000
     * start_date : 4/12/2022 12:00:00 AM
     * stop_date : null
     * daw : 0
     * origin_code : 4
     * is_active : Y
     * mar_flag : Y
     * tar_flag : N
     * po_flag : Y
     * last_tran_id : 0
     * discontinue_date : null
     * discontinue_note : null
     * rx_expire_date : 4/12/2023 12:00:00 AM
     * qty : 8.5000
     */

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
    private String ndc;
    private boolean IsLOAHospital;
    private boolean IsMissedDose;
    private int MedpassID;
    private boolean IsTaken;
    private String external_patient_id;
    private String external_facility_id;
    private String external_doctor_id;
    private int external_prescription_id;
    private String pharmacy_order_id;
    private String strength_value;
    private String strength;
    private String prescribe_date;
    private String sig_english;
    private String original_qty;
    private String days_supply;
    private String no_of_refill;
    private String morning;
    private String noon;
    private String evening;
    private String night;
    private String start_date;
    private String stop_date;
    private String daw;
    private String origin_code;
    private String is_active;
    private String mar_flag;
    private String tar_flag;
    private String po_flag;
    private String last_tran_id;
    private String discontinue_date;
    private String discontinue_note;
    private String rx_expire_date;
    private String qty;
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

    public String getPrescribe_date() {
        return prescribe_date;
    }

    public void setPrescribe_date(String prescribe_date) {
        this.prescribe_date = prescribe_date;
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

    public String getStop_date() {
        return stop_date;
    }

    public void setStop_date(String stop_date) {
        this.stop_date = stop_date;
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

    public String getDiscontinue_date() {
        return discontinue_date;
    }

    public void setDiscontinue_date(String discontinue_date) {
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


    /**
     * tran_id : 887491
     * dose_date : 2022-04-19T00:00:00
     * patient_id : 36905
     * facility_id : 348
     * dose_time : 08:00 AM
     * dose_Qty : 1.0
     * dose_status : N
     * note :
     * updated_by : null
     * updated_date : null
     * dose_given_time :
     * dose_given_date : 2014-10-10T00:00:00
     * med_type : O
     * sig_code : APPLY A PEA SIZED AMOUNT TO AFFECTED AREA ON LEFT ARM BID
     * sig_detail : Apply a pea sized amount to affected area on left arm twice daily
     * internal_rx_id : 1963532
     * rx_number : 4214302
     * drug : BETAMETH DIPROP AUGMENTED   0.05 %    OINT. (G)
     * patient_first : PATIENT
     * patient_last : TEST 5
     * DOB : 1989-11-11T00:00:00
     * Gender : Male
     * ndc : 00472038245
     * IsLOAHospital : false
     * IsMissedDose : false
     * MedpassID : 0
     * IsTaken : false
     */
/*

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
*/







    /*
    *//**
     * tran_id : 600728
     * dose_date : 2015-01-01T00:00:00
     * patient_id : 38977
     * facility_id : 707
     * dose_time : 08:00 AM
     * dose_Qty : 1
     * dose_status : N
     * note :
     * updated_by : null
     * updated_date : null
     * dose_given_time :
     * dose_given_date : 2014-10-10T00:00:00
     * med_type : C
     * sig_code : 1TAM AT 8AM
     * sig_detail : 1
     * internal_rx_id : 1156002
     * rx_number : 3529663
     * drug : LAMOTRIGINE   100 MG    TABLET
     * patient_first : ASHLEE
     * patient_last : HOSMER (SK)
     * DOB : 2003-06-09T00:00:00
     * Gender : Female
     *//*

    private int tran_id;
    private String dose_date;
    private int patient_id;
    private int facility_id;
    private String dose_time;
    private float dose_Qty;
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
    private String ndc;
    private boolean IsLOAHospital;
    private boolean IsMissedDose;

    public boolean isMissedDose() {
        return IsMissedDose;
    }

    public void setMissedDose(boolean missedDose) {
        IsMissedDose = missedDose;
    }

    public boolean isLOAHospital() {
        return IsLOAHospital;
    }

    public void setLOAHospital(boolean LOAHospital) {
        IsLOAHospital = LOAHospital;
    }

    public String getNdc() {
        return ndc;
    }

    public void setNdc(String ndc) {
        this.ndc = ndc;
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

    public float getDose_Qty() {
        return dose_Qty;
    }

    public void setDose_Qty(float dose_Qty) {
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
    }*/
}
