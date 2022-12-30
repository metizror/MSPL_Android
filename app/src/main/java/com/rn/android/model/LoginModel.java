package com.rn.android.model;

import java.util.List;

public class LoginModel {


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

    private LoginData Data;

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

    public LoginData getData() {
        return Data;
    }

    public void setData(LoginData Data) {
        this.Data = Data;
    }

    public static class LoginData {
        private int RegisterID;
        private int UserID;
        private Object FirstName;
        private Object LastName;
        private String UserName;
        private boolean IsConfirm;
        private String RoleName;
        private Object Email;
        private Object Password;
        private String device_token;
        private String Authtoken;
        private int PasswordDayCount;
        private int NotificationID;
        private int SessionTimeOut;
        private String LastpasswordChangeDate;
        private int ChangePasswordDayCount;
        private boolean IsChangePassword;
        private Object UserRoleID;
        /**
         * GroupID : 13
         * FacilityID : 321
         * ExFacilityID : 883
         * FacilityName : ADVOCATES - 26 DANIELS RD
         * Address : 26 DANIEL ROAD
         * City : FRAMINGHAM
         */

        private List<FacilityBean> Facility;

        public int getRegisterID() {
            return RegisterID;
        }

        public void setRegisterID(int RegisterID) {
            this.RegisterID = RegisterID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public Object getFirstName() {
            return FirstName;
        }

        public void setFirstName(Object FirstName) {
            this.FirstName = FirstName;
        }

        public Object getLastName() {
            return LastName;
        }

        public void setLastName(Object LastName) {
            this.LastName = LastName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public boolean isIsConfirm() {
            return IsConfirm;
        }

        public void setIsConfirm(boolean IsConfirm) {
            this.IsConfirm = IsConfirm;
        }

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String RoleName) {
            this.RoleName = RoleName;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public Object getPassword() {
            return Password;
        }

        public void setPassword(Object Password) {
            this.Password = Password;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getAuthtoken() {
            return Authtoken;
        }

        public void setAuthtoken(String Authtoken) {
            this.Authtoken = Authtoken;
        }

        public int getPasswordDayCount() {
            return PasswordDayCount;
        }

        public void setPasswordDayCount(int PasswordDayCount) {
            this.PasswordDayCount = PasswordDayCount;
        }

        public int getNotificationID() {
            return NotificationID;
        }

        public void setNotificationID(int NotificationID) {
            this.NotificationID = NotificationID;
        }

        public int getSessionTimeOut() {
            return SessionTimeOut;
        }

        public void setSessionTimeOut(int SessionTimeOut) {
            this.SessionTimeOut = SessionTimeOut;
        }

        public String getLastpasswordChangeDate() {
            return LastpasswordChangeDate;
        }

        public void setLastpasswordChangeDate(String LastpasswordChangeDate) {
            this.LastpasswordChangeDate = LastpasswordChangeDate;
        }

        public int getChangePasswordDayCount() {
            return ChangePasswordDayCount;
        }

        public void setChangePasswordDayCount(int ChangePasswordDayCount) {
            this.ChangePasswordDayCount = ChangePasswordDayCount;
        }

        public boolean isIsChangePassword() {
            return IsChangePassword;
        }

        public void setIsChangePassword(boolean IsChangePassword) {
            this.IsChangePassword = IsChangePassword;
        }

        public Object getUserRoleID() {
            return UserRoleID;
        }

        public void setUserRoleID(Object UserRoleID) {
            this.UserRoleID = UserRoleID;
        }

        public List<FacilityBean> getFacility() {
            return Facility;
        }

        public void setFacility(List<FacilityBean> Facility) {
            this.Facility = Facility;
        }

        public static class FacilityBean {
            private int GroupID;
            private int FacilityID;
            private int ExFacilityID;
            private String FacilityName;
            private String Address;
            private String City;

            public int getGroupID() {
                return GroupID;
            }

            public void setGroupID(int GroupID) {
                this.GroupID = GroupID;
            }

            public int getFacilityID() {
                return FacilityID;
            }

            public void setFacilityID(int FacilityID) {
                this.FacilityID = FacilityID;
            }

            public int getExFacilityID() {
                return ExFacilityID;
            }

            public void setExFacilityID(int ExFacilityID) {
                this.ExFacilityID = ExFacilityID;
            }

            public String getFacilityName() {
                return FacilityName;
            }

            public void setFacilityName(String FacilityName) {
                this.FacilityName = FacilityName;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }
        }
    }
}
