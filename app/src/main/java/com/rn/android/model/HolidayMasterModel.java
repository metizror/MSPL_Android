package com.rn.android.model;

import java.util.List;

public class HolidayMasterModel {


    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"HolidayMaster":[{"HolidayID":25,"HolidayDate":"2022-04-11T00:00:00","Description":"We will be closed on 04/11/22 (Test Holiday By Jugraj)"}],"EmergencyMessage":[{"ID":12,"Message":"Rohan ni bhih pasde vikram bhai nai hoy tyyre","ActiveUntil":"2022-04-11T00:00:00"}]}
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
         * HolidayID : 25
         * HolidayDate : 2022-04-11T00:00:00
         * Description : We will be closed on 04/11/22 (Test Holiday By Jugraj)
         */

        private List<HolidayMasterBean> HolidayMaster;
        /**
         * ID : 12
         * Message : Rohan ni bhih pasde vikram bhai nai hoy tyyre
         * ActiveUntil : 2022-04-11T00:00:00
         */

        private List<EmergencyMessageBean> EmergencyMessage;

        public List<HolidayMasterBean> getHolidayMaster() {
            return HolidayMaster;
        }

        public void setHolidayMaster(List<HolidayMasterBean> HolidayMaster) {
            this.HolidayMaster = HolidayMaster;
        }

        public List<EmergencyMessageBean> getEmergencyMessage() {
            return EmergencyMessage;
        }

        public void setEmergencyMessage(List<EmergencyMessageBean> EmergencyMessage) {
            this.EmergencyMessage = EmergencyMessage;
        }

        public static class HolidayMasterBean {
            private int HolidayID;
            private String HolidayDate;
            private String Description;

            public int getHolidayID() {
                return HolidayID;
            }

            public void setHolidayID(int HolidayID) {
                this.HolidayID = HolidayID;
            }

            public String getHolidayDate() {
                return HolidayDate;
            }

            public void setHolidayDate(String HolidayDate) {
                this.HolidayDate = HolidayDate;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }
        }

        public static class EmergencyMessageBean {
            private int ID;
            private String Message;
            private String ActiveUntil;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getMessage() {
                return Message;
            }

            public void setMessage(String Message) {
                this.Message = Message;
            }

            public String getActiveUntil() {
                return ActiveUntil;
            }

            public void setActiveUntil(String ActiveUntil) {
                this.ActiveUntil = ActiveUntil;
            }
        }
    }
}
