package com.rn.android.network;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;

import com.android.volley.toolbox.HttpHeaderParser;

import java.util.ArrayList;

/**
 * Created by elitech04i7 on 23/11/16.
 */

public class CacheRequest extends Request<NetworkResponse> {
    private static ArrayList<String> cashStringArray = new ArrayList<>();
    private final Response.Listener<NetworkResponse> mListener;
    private final Response.ErrorListener mErrorListener;
    private String url = null;

    public CacheRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.url = url;
    }

    public static ArrayList<String> cashStringArray() {
        try {
            if (cashStringArray == null || cashStringArray.size() <= 0) {
//                cashStringArray.add(API.STORE_LIKE.getMethod());
//                cashStringArray.add(API.LIKED_STORES.getMethod());
//                cashStringArray.add(API.LIKE.getMethod());
//                cashStringArray.add(API.LOG.getMethod());
//                cashStringArray.add(API.CHECK_STORE_LIKE.getMethod());
//                cashStringArray.add(API.OFFERS_BY_STORES_OFFER_TYPE.getMethod());
//                cashStringArray.add(API.STORES.getMethod());
//                cashStringArray.add(Share.SHORTN_URL_API);


            }
            return cashStringArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashStringArray;


    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {


        if (cashStringArray().contains(url) && response.data != null) {
            try {

                Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                if (cacheEntry == null) {
                    cacheEntry = new Cache.Entry();
                }
                final long cacheHitButRefreshed = 1000; // in 3 minutes cache will be hit, but also refreshed on background
                final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                long now = System.currentTimeMillis();
                final long softExpire = now + cacheHitButRefreshed;
                final long ttl = now + cacheExpired;
                cacheEntry.data = response.data;
                cacheEntry.softTtl = softExpire;
                cacheEntry.ttl = ttl;
                String headerValue;
                headerValue = response.headers.get("Date");
                if (headerValue != null) {
                    cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                headerValue = response.headers.get("Last-Modified");
                if (headerValue != null) {
                    cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                cacheEntry.responseHeaders = response.headers;
                return Response.success(response, cacheEntry);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.success(response, null);
            }

        } else {
            Log.e("Cash na thai..", " ha ha ha ");
            return Response.success(response, null);
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

}