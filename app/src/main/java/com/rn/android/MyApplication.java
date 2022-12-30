package com.rn.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by espl on 27/9/16.
 */
public class MyApplication extends android.app.Application implements LifecycleObserver {
    public static final String FONT = "fonts/Roboto-Regular.ttf";

    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication _instance;
    private static Gson gson = null;
    private static SharedPreferences _preferences;
    private static Context context;
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;

    public static MyApplication get() {

        return _instance;
    }

    public static Gson getGson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }

    /**
     * Gets shared preferences.
     *
     * @return the shared preferences
     */
    private static SharedPreferences getSharedPreferences() {
        if (_preferences == null)
            _preferences = PreferenceManager.getDefaultSharedPreferences(_instance);
        return _preferences;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void cancelRequestInQueue(String tag) {
        getRequestQueue().cancelAll(tag);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the no_user tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static void clearPrefrences() {
        getSharedPreferences().edit().clear().commit();
    }

    /**
     * Sets shared preferences.
     *
     * @return the shared preferences
     */
    public static void setPreferences(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static void setPreferences(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static void setPreferencesLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static void setPreferencesBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static String getPrefranceData(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static int getPrefranceDataInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public static long getPrefranceDataLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public static boolean getPrefranceDataBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    /**
     * Gets typeface of the FontAwesome
     *
     * @return the typeface
     */
    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("PelmedsDatabase")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        //     FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Fresco.initialize(this);
        FirebaseApp.initializeApp(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        MyApplication.context = getApplicationContext();
        try {
            _instance = this;
            /*setUpFonts();*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        Log.e("onMoveToForeground:", "onMoveToForeground:00000");


    }

}


