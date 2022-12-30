package com.rn.android.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChangeActiveStatus  implements Serializable {


    int FacilityID;
    String PatientLast;
    String PatientFirst;
    int UserId;
    int FacilityPatientStatus;
    int HeaderID;


    public int getFacilityID() {
        return FacilityID;
    }

    public void setFacilityID(int facilityID) {
        FacilityID = facilityID;
    }

    public String getPatientLast() {
        return PatientLast;
    }

    public void setPatientLast(String patientLast) {
        PatientLast = patientLast;
    }

    public String getPatientFirst() {
        return PatientFirst;
    }

    public void setPatientFirst(String patientFirst) {
        PatientFirst = patientFirst;
    }

    public int getUserId() {
        return UserId;
    }

    public int getHeaderID() {
        return HeaderID;
    }

    public void setHeaderID(int headerID) {
        HeaderID = headerID;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getFacilityPatientStatus() {
        return FacilityPatientStatus;
    }

    public void setFacilityPatientStatus(int facilityPatientStatus) {
        FacilityPatientStatus = facilityPatientStatus;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("FacilityID", FacilityID);
            obj.put("PatientLast", PatientLast);
            obj.put("PatientFirst", PatientFirst);
            obj.put("UserId", UserId);
            obj.put("FacilityPatientStatus", FacilityPatientStatus);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;

    }
}





