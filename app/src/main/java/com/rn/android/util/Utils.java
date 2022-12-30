package com.rn.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.databinding.DataBindingUtil;

import com.rn.android.LoginActivity;
import com.rn.android.R;
import com.rn.android.databinding.DialogShowAlertBinding;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class Utils {

    private static ProgressDialog pDialog;

    public static byte[] readBytesFromFile(String filePath) {


        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);

            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;


    }

    /**
     * Check Internet Connection
     * <p>
     * base activity
     *
     * @return boolean Utils
     */
    // check internet connection
//    public static boolean checkInternetConnection(Context context) {
//        return true;
//        try {
//            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            boolean status = conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
//            if (status)
//                return status;
//            else {
////                if (!Constants.isNoInternetScreenOpen) {
////                    Constants.isNoInternetScreenOpen = true;
////                    Intent i1 = new Intent(context, NoInternetConnectionActivity.class);
////                    i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(i1);
////                    Utils.showAlertToast(Application.get(), "Internet connection is not available.");
////                }
//                return status;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
    public static String getDeviceId() {

        return "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            @SuppressLint("MissingPermission") NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            } else {
                // buildDialog(context);
            }
        }
        Log.e("LOG_TAG", "You are not connected to Internet!");
        return false;
    }

//    public static AlertDialog.Builder builder = null;
//    public static AlertDialog alert;

    public static void dismissDialog() {
        if (dialogSHowAlert != null) {
            if (dialogSHowAlert.isShowing()) {
                dialogSHowAlert.dismiss();
            }
        }
    }

    public static DialogShowAlertBinding mDialogShowAlertBinding;
    public static Dialog dialogSHowAlert;

    public static void buildDialog(Context context) {
        if (dialogSHowAlert != null) {
            if (dialogSHowAlert.isShowing()) {
                dialogSHowAlert.dismiss();
                dialogSHowAlert = null;
            }
        }

        dialogSHowAlert = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogShowAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_show_alert, null, false);
        dialogSHowAlert.setContentView(mDialogShowAlertBinding.getRoot());
//                            dialogDiscontinue.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogSHowAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogSHowAlert.getWindow().setStatusBarColor(context.getResources().getColor(R.color.white));
        dialogSHowAlert.setCancelable(false);
        dialogSHowAlert.setCanceledOnTouchOutside(false);

        mDialogShowAlertBinding.tvTitle.setText("No Internet connection.");
        mDialogShowAlertBinding.tvMessage.setText(Html.fromHtml("You have no internet connection but you can check medsheet offline" + "<br>" + "Select Your " + "<b>" + "Local file Manager " + "</b>" + "from the App list."));
        mDialogShowAlertBinding.okBtn.setText("Go to files");
        mDialogShowAlertBinding.canleBtn.setText("Logout");

        mDialogShowAlertBinding.imgalert.setVisibility(View.GONE);
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            dialogSHowAlert = null;
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
        mDialogShowAlertBinding.okBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            dialogSHowAlert = null;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String path = Environment.getExternalStorageDirectory() + "Download";
            Uri mydir = Uri.parse(path);
            intent.setDataAndType(mydir, "*/*");    // or use */*
            context.startActivity(intent);
        });
        dialogSHowAlert.show();
    }

    /**
     * Show alert toast.
     *
     * @param context the context
     * @param text    the text
     */
// for display tost in application
    public static void showAlertToast(Context context, String text) {
        try {
            if (text.length() > 0) {
               /* Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if (v != null) v.setGravity(Gravity.CENTER);
                toast.show();*/
                Toast.makeText(context, "" + text, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static String convertDecimalToFraction(double x) {
        if (x < 0) {
            return "-" + convertDecimalToFraction(-x);
        }
        double tolerance = 1.0E-6;
        double h1 = 1;
        double h2 = 0;
        double k1 = 0;
        double k2 = 1;
        double b = x;
        do {
            double a = Math.floor(b);
            double aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            b = 1 / (b - a);
        } while (Math.abs(x - h1 / k1) > x * tolerance);

        return (int) h1 + "/" + (int) k1;
    }

    public static void showDialogwithCallbacks(Context context, String title, String msg, final DialogCallbacks callbacks) {
        AlertDialog alertDialog = new Builder(
                context).create();

        if (title != null && !title.isEmpty()) {
            alertDialog.setTitle(title);
        }
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        // Setting Icon to Dialog
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callbacks.positiveClicked();
            }
        });

        // Setting OK Button

        // Showing Alert Message
        alertDialog.show();
    }


    /**
     * Show alert toast.
     *
     * @param context the context
     * @param text    the text
     */
// for display tost in application
    public static void showAlertToastLong(Context context, String text) {
        try {
            if (text.length() > 0) {
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if (v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Show alert dialog.
     *
     * @param context the context
     * @param title   the title
     * @param text    the text
     */
// display alert dialog box
    // in prameter passed title and messge for the se content in dialog box
    public static void showAlertDialog(Context context, String title, String text) {
        try {
            Builder b = new Builder(context).setMessage(text).setNeutralButton("OK", null);
            if (title == null || text.isEmpty()) {
                AlertDialog d = b.create();
                d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                d.show();
            } else {
                b.setTitle(title);
                b.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initProgressDialog(Context context) {
        if (pDialog == null)
            pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
    }

    public static void showProgressDialog(Context context) {

        try {
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.show();
            } else {
                initProgressDialog(context);
                pDialog.show();
            }
        } catch (Exception ex) {

        }
    }

    /**
     * Gets image.
     *
     * @param src  the src
     * @param opts the opts
     * @return the image
     */
    public static Bitmap getImage(byte[] src, BitmapFactory.Options opts) {
        try {
            if (src != null)
                return BitmapFactory.decodeByteArray(src, 0, src.length, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets bitmap from byte.
     *
     * @param strByte the str byte
     * @return the bitmap from byte
     */
    public static Bitmap getBitmapFromByte(String strByte) {
        try {
            byte[] imgByte = strByte.getBytes("UTF-8");
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Bit map to string string.
     *
     * @param imgurl the imgurl
     * @return the string
     */
    public static String BitMapToString(String imgurl) {

        try {
            URL url = new URL(imgurl);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] arr = baos.toByteArray();
            String result = Base64.encodeToString(arr, Base64.DEFAULT);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String to bit map bitmap.
     *
     * @param image the image
     * @return the bitmap
     */
    public static Bitmap StringToBitMap(String image) {
        try {

            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static long getDiffBetweenDays(String _endDate, String _startDate) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = simpleDateFormat.parse(_startDate);
            date2 = simpleDateFormat.parse(_endDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1 == null || date2 == null) {
            return -1;
        }


        //milliseconds
        long different = date1.getTime() - date2.getTime();

        System.out.println("startDate : " + _startDate);
        System.out.println("endDate : " + _endDate);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        System.out.println("diffdays : " + elapsedDays);
        return elapsedDays;
    }

    /**
     * Copy stream.
     *
     * @param is the is
     * @param os the os
     */
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {

            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                //Read byte from input stream

                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;

                //Write byte from output stream
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Gets asset image.
     *
     * @param context  the context
     * @param filename the filename
     * @return the asset image
     * @throws IOException the io exception
     */
    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        try {
            AssetManager assets = context.getResources().getAssets();
            InputStream buffer = new BufferedInputStream((assets.open("drawables/" + filename + ".png")));
            Bitmap bitmap = BitmapFactory.decodeStream(buffer);
            return new BitmapDrawable(context.getResources(), bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Small screen boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean smallScreen(Context context) {
        try {
            Configuration configuration = context.getResources().getConfiguration();
            int screenWidthDp = configuration.screenWidthDp;
            return screenWidthDp < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showToast(Activity localActivity, String value, int display) {
        try {
            Toast toast = Toast.makeText(localActivity, value, display);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {

        }
    }

    /**
     * Remove the transparency from the image
     *
     * @param - Bitmap the bitmap
     * @return the bitmap
     */
    public static Bitmap cropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0)   // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);


    }


    /**
     * Show progress dialog.
     *
     * @param Title        the title
     * @param Message      the message
     * @param isCancelable the is cancelable
     */
    public static void showProgressDialog(Context context, String Title, String Message, boolean isCancelable) {
        try {
            if (pDialog == null)
                pDialog = new ProgressDialog(context);
            if (Title != null && Title.length() > 0)
                pDialog.setTitle(Title);
            if (Message != null && Message.length() > 0)
                pDialog.setMessage(Message);
            pDialog.setCancelable(isCancelable);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dismiss progress dialog.
     */
    public static void dismissProgressDialog() {
        try {
            if (pDialog != null)
                pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean vlidateEditText(EditText editText, String message) {
        if (!editText.getText().toString().trim().isEmpty())
            return false;
        else {
            editText.setError(message);
            return true;
        }
    }

    public static String convertImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap convertBase64ToImage(String encodedImage) {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String convertDatetoString(String date, String formate) {


        Format formatter = new SimpleDateFormat(formate);
        String s = formatter.format(date);

        return s;

    }

    public static String convertStringtoDate(Date date, String formate) {

        Format formatter = new SimpleDateFormat(formate);
        String s = formatter.format(date);

        return s;

    }

    @SuppressLint("SimpleDateFormat")
    public static String DateFromOnetoAnother(String date, String givenformat, String resultformat) {

        String result = "";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        try {
            sdf = new SimpleDateFormat(givenformat);
            sdf1 = new SimpleDateFormat(resultformat);
            result = sdf1.format(sdf.parse(date));

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            sdf = null;
            sdf1 = null;
        }

        return result;
    }


    public static String formatDateFromOnetoAnother(String date, String givenformat, String resultformat) {

        if (date == null || date.isEmpty()) {
            return "";
        }
        String result = "";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;

        try {
            sdf = new SimpleDateFormat(givenformat);
            sdf1 = new SimpleDateFormat(resultformat);
            result = sdf1.format(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            sdf = null;
            sdf1 = null;
        }
        return result;
    }

    public static String addDay(String _date, String givenformat, int i) {
        if (_date == null || _date.isEmpty()) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(givenformat);

        try {
            c.setTime(sdf.parse(_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, i);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
        String output = sdf1.format(c.getTime());

        return output;
    }


    public static void hideKeybord(Activity activity) {

//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);


        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }


    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String loadJSONFromAssets(Context context, String filenName) {

        String json = null;
        try {
            InputStream is = context.getAssets().open(filenName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static void dateDialoge(Context context, final EditText editText) {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.YEAR, yy);
        calendar.set(Calendar.MONTH, mm);
        calendar.set(Calendar.DAY_OF_MONTH, dd);

        DatePickerDialog datePicker = new DatePickerDialog(context, R.style.datepicker
                , new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month = String.valueOf(monthOfYear + 1);
                if (monthOfYear < 9) {
                    month = "0" + String.valueOf(monthOfYear + 1);
                }
                String day = String.valueOf(dayOfMonth);
                if (dayOfMonth <= 9) {
                    day = "0" + String.valueOf(dayOfMonth);
                }
                String date = String.valueOf(year) + "-" + month
                        + "-" + day;
                editText.setText(date);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePicker.show();

    }

    public static String getFormatedDate(Date date, String formate) {
        SimpleDateFormat format = new SimpleDateFormat(formate);
        return format.format(date);

    }

    public static String getDefaultFormatedDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);

    }

    public static void openDatePicker(Context context, final EditText text, final DatePickerSelected datePickerSelected) {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerSelected.selectedDate(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateString = formatter.format(new Date());
        System.out.println(dateString);
        return dateString;
    }

    public String getFormate(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date);
//        5/21/1955 12:00:00 AM
            Log.d("Date", String.valueOf(d));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String monthName = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
            return monthName;
        } else {
            return "";
        }
    }

    @SuppressLint("NewApi")
    public String DateConvert(String date) {
        String newDateString = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date newDate = spf.parse(date);
            spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
            newDateString = spf.format(newDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;
    }

    public interface DatePickerSelected {
        void selectedDate(String date);
    }

}

