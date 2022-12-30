package com.rn.android.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rn.android.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by espl on 4/10/16.
 */
public class ConstantFunctions {


    public static String getUDID() {
        try {
            return Settings.Secure.getString(MyApplication.get().getContentResolver(), Settings.Secure.ANDROID_ID.toString());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    /**
     * App version string.
     *
     * @return the string
     */
    public static String appVersion() {
        try {
            PackageManager manager = MyApplication.get().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.get().getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Set the first letter in capital everywhere needed
    public static String capitalLetterString(String label) {
        return new String(label.toUpperCase().charAt(0) + label.substring(1));
//            label.toUpperCase();
//            StringBuilder stringBuilder = new StringBuilder(label);
//            stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
//            return stringBuilder.toString();
    }

    // Set all the letters in capital everywhere needed
    public static String allCapitalLetters(String label) {
        return new String(label.toUpperCase());
    }

    // hide keyboard from screen
    public static void hideKeyBord(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String html2text(String html) {
        return html.replaceAll("\\<[^>]*>", "");
    }

    public static boolean validateString(String string) {
        try {
            return string != null && !string.trim().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    public static boolean validatePref(String key) {
        String value = MyApplication.getPrefranceData(key);
        return value != null && !value.isEmpty() && !value.equals("null");

    }

    public static String getOrderDate(String createdOn) {
        try {
            Calendar mCalendar = new GregorianCalendar();
            TimeZone mTimeZone = mCalendar.getTimeZone();
            int mGMTOffset = mTimeZone.getRawOffset();
            SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            long time = serverDate.parse(createdOn).getTime() + mGMTOffset;
            return convertDate(time, "dd MMM yyyy\nhh:mm a");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createdOn;

    } public static String getOrderDetailsDate(String createdOn) {
        try {
            Calendar mCalendar = new GregorianCalendar();
            TimeZone mTimeZone = mCalendar.getTimeZone();
            int mGMTOffset = mTimeZone.getRawOffset();
            SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            long time = serverDate.parse(createdOn).getTime() + mGMTOffset;
            return convertDate(time, "dd MMM yyyy\nhh:mm a");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createdOn;

    }

    public static String convertDate(long dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, dateInMilliseconds).toString();
    }



}


