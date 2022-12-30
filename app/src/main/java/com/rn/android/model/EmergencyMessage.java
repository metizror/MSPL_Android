package com.rn.android.model;

public class EmergencyMessage {

    private int ID;
    private String ActiveUntil;
    private String Message;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getActiveUntil() {
        return ActiveUntil;
    }

    public void setActiveUntil(String activeUntil) {
        ActiveUntil = activeUntil;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
