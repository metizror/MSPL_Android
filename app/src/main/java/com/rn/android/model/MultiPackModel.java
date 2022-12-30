package com.rn.android.model;

public class MultiPackModel {


    /**
     * ResponseStatus : 1
     * ErrorMessage : null
     * Message : successful
     * Data : {"RegisterID":0,"UserID":77,"FirstName":null,"LastName":null,"UserName":"sid","IsConfirm":true,"RoleName":"Admin","Email":null,"Password":null,"device_token":"eeP8wuDZR3O21agSy82iuj:APA91bGLNBaWP40bNbLf5TmZl9Kp1atmVPCqgdjolUzj2tdrHZIswbPumEW_uqktvGpxJa5fKbP_K05v2dnO02X-WN096jRndnh0k0dFjsACFlnh1Cmev3pkBYszPQOaunzuCY-oecbM","Authtoken":"50bc352e-80b4-4606-b98a-11cb621d086c","Facility":[{"GroupID":13,"FacilityID":321,"ExFacilityID":883,"FacilityName":"ADVOCATES - 26 DANIELS RD","Address":"26 DANIEL ROAD","City":"FRAMINGHAM"},{"GroupID":13,"FacilityID":462,"ExFacilityID":1159,"FacilityName":"ADVOCATES INC-285 DAY ST","Address":"285 DAY ST","City":"LEOMINISTER"},{"GroupID":12,"FacilityID":70,"ExFacilityID":348,"FacilityName":"TEST FACILITY","Address":"196 BEAR HILL ROAD","City":"WALTHAM"}],"PasswordDayCount":0,"NotificationID":1,"SessionTimeOut":10,"LastpasswordChangeDate":"2022-03-25T02:08:21.07","ChangePasswordDayCount":90,"IsChangePassword":false,"UserRoleID":null}
     */

    private int ResponseStatus;
    private Object ErrorMessage;
    private String Message;
    /**
     * RegisterID : 0
     * UserID : 77
     * FirstName : null
     * LastName : null
     * UserName : sid
     * IsConfirm : true
     * RoleName : Admin
     * Email : null
     * Password : null
     * device_token : eeP8wuDZR3O21agSy82iuj:APA91bGLNBaWP40bNbLf5TmZl9Kp1atmVPCqgdjolUzj2tdrHZIswbPumEW_uqktvGpxJa5fKbP_K05v2dnO02X-WN096jRndnh0k0dFjsACFlnh1Cmev3pkBYszPQOaunzuCY-oecbM
     * Authtoken : 50bc352e-80b4-4606-b98a-11cb621d086c
     * Facility : [{"GroupID":13,"FacilityID":321,"ExFacilityID":883,"FacilityName":"ADVOCATES - 26 DANIELS RD","Address":"26 DANIEL ROAD","City":"FRAMINGHAM"},{"GroupID":13,"FacilityID":462,"ExFacilityID":1159,"FacilityName":"ADVOCATES INC-285 DAY ST","Address":"285 DAY ST","City":"LEOMINISTER"},{"GroupID":12,"FacilityID":70,"ExFacilityID":348,"FacilityName":"TEST FACILITY","Address":"196 BEAR HILL ROAD","City":"WALTHAM"}]
     * PasswordDayCount : 0
     * NotificationID : 1
     * SessionTimeOut : 10
     * LastpasswordChangeDate : 2022-03-25T02:08:21.07
     * ChangePasswordDayCount : 90
     * IsChangePassword : false
     * UserRoleID : null
     */

    private MultiPackData Data;

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

    public MultiPackData getData() {
        return Data;
    }

    public void setData(MultiPackData Data) {
        this.Data = Data;
    }

    public static class MultiPackData {
        private int GivenPack;
        private int TotalPack;
        private int TotalPendingPack;
        private String PendingPackMsg;

        /**
         * GroupID : 13
         * FacilityID : 321
         * ExFacilityID : 883
         * FacilityName : ADVOCATES - 26 DANIELS RD
         * Address : 26 DANIEL ROAD
         * City : FRAMINGHAM
         */

        public int getGivenPack() {
            return GivenPack;
        }

        public void setGivenPack(int givenPack) {
            GivenPack = givenPack;
        }

        public int getTotalPack() {
            return TotalPack;
        }

        public void setTotalPack(int totalPack) {
            TotalPack = totalPack;
        }

        public int getTotalPendingPack() {
            return TotalPendingPack;
        }

        public void setTotalPendingPack(int totalPendingPack) {
            TotalPendingPack = totalPendingPack;
        }

        public String getPendingPackMsg() {
            return PendingPackMsg;
        }

        public void setPendingPackMsg(String pendingPackMsg) {
            PendingPackMsg = pendingPackMsg;
        }
    }
}
