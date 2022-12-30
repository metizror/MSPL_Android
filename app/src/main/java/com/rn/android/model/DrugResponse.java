package com.rn.android.model;

import androidx.annotation.NonNull;

import com.rn.android.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 22/2/18.
 */

public class DrugResponse implements Comparable<DrugResponse> {

    public String external_patient_id;
    public String external_facility_id;
    public String external_doctor_id;
    public String external_drug_id;
    public String external_prescription_id;
    public String pharmacy_order_id;
    public String drug;
    public String strength_value;
    public String strength;
    public String ndc;
    public String prescribe_date;
    public String sig_code;
    public String sig_english;
    public String original_qty;
    public String qty;
    public String days_supply;
    public String no_of_refill;
    public String morning;
    public String noon;
    public String evening;
    public String night;
    public String start_date;
    public Object stop_date;
    public String med_type;
    public String daw;
    public String origin_code;
    public String is_active;
    public String mar_flag;
    public String tar_flag;
    public String po_flag;
    public String last_tran_id;
    public Object discontinue_date;
    public String discontinue_note;
    public String rx_expire_date;
    public String docter_name;

    public String getRemain_Refill() {
        return Remain_Refill;
    }

    public void setRemain_Refill(String remain_Refill) {
        Remain_Refill = remain_Refill;
    }

    public String Remain_Refill;

    public String getDocter_name() {
        return docter_name;
    }

    public void setDocter_name(String docter_name) {
        this.docter_name = docter_name;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String tran_date;

    public String getOriginal_prescribe_date() {
        return original_prescribe_date;
    }

    public void setOriginal_prescribe_date(String original_prescribe_date) {
        this.original_prescribe_date = original_prescribe_date;
    }

    public String original_prescribe_date;
    public String last_qty_approved;
    public String last_qty_billed;

    public String getLast_pickedup_date() {
        return last_pickedup_date;
    }

    public void setLast_pickedup_date(String last_pickedup_date) {
        this.last_pickedup_date = last_pickedup_date;
    }

    public String last_pickedup_date;

    public boolean isChecked;

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

    public String getLast_qty_approved() {
        return last_qty_approved;
    }

    public void setLast_qty_approved(String last_qty_approved) {
        this.last_qty_approved = last_qty_approved;
    }

    public String getLast_qty_billed() {
        return last_qty_billed;
    }

    public void setLast_qty_billed(String last_qty_billed) {
        this.last_qty_billed = last_qty_billed;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @Override
    public int compareTo(@NonNull DrugResponse drugResponse) {
        Date date = null;
        Date compareDate = null;
        try {
            date = new SimpleDateFormat("MM/dd/yy").parse(Utils.addDay(getLast_pickedup_date(), "MM/dd/yyyy hh:mm:ss aaa", Integer.parseInt(getDays_supply())));
            compareDate = new SimpleDateFormat("MM/dd/yy").parse(Utils.addDay(drugResponse.getLast_pickedup_date(), "MM/dd/yyyy hh:mm:ss aaa", Integer.parseInt(drugResponse.getDays_supply())));
            return date.compareTo(compareDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }



    }
}
