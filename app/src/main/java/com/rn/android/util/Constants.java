package com.rn.android.util;

import com.rn.android.MyApplication;

public class Constants {
    public static final String ChangePasswordDayCount = "ChangePasswordDayCount";
    public static final int PasswordChangeNotificationPeriods = 5;
    public static final String LastPasswordChangeDate = "LastpasswordChangeDate";
    public static final String PasswordChangeNotified = "PasswordChangeNotified";

    public static int getPasswordChangeDuration() {
        if (MyApplication.getPrefranceData(Constants.ChangePasswordDayCount).isEmpty()){
            MyApplication.setPreferences(Constants.ChangePasswordDayCount,"90");
            return 90;
        }else {
            return Integer.parseInt(MyApplication.getPrefranceData(Constants.ChangePasswordDayCount));
        }
    }
}
