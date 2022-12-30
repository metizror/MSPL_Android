package com.rn.android.network;

import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.rn.android.MyApplication;
import com.rn.android.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by espl on 27/9/16.
 */
public class NetworkUtility {

    public static final String appPref = "appPref";
    public static String API_Version = "v1";

    public static void makeJsonObjReq(String url, final ArrayMap<String, String> data, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {

            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
//            String deviceToken = "test";
//            if (deviceToken != null)
//                data.put(APIConstants.device_token, deviceToken);
//            else
            Log.e("URL", url);
            Log.e("params", data.toString());


            CacheRequest cacheRequest = new CacheRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    try {

                        final String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers));
                        JSONObject jsonObject = new JSONObject(jsonString);
                        Log.e("reponce", jsonObject.toString());
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                            JSONObject validateResponse = statusValidations(jsonObject);

                            try {
                                // check response code validations
                                // return "data"object data in return
                                if (validateResponse != null)
                                    volleyCallBack.onSuccess(validateResponse);
                                else
                                    volleyCallBack.onError(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                                volleyCallBack.onError(null);
                                Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                            }
//
                        }
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " +
                                        "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }

                }
            }) {
                //                // this is the relevant method
                @Override
                protected Map<String, String> getParams() {
                    return data;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);
                    return headers;
                }

                @Override
                public String getCacheKey() {
                    return generateCacheKeyWithParam(super.getCacheKey(), data);
                }
            };

            cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }

    public static void makeArrayResponseReq(String url, final ArrayMap<String, String> data, String tag, final VolleyStringBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {

            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
//            String deviceToken = "test";
//            if (deviceToken != null)
//                data.put(APIConstants.device_token, deviceToken);
//            else
            Log.e("URL", url);
            Log.e("params", data.toString());
            CacheRequest cacheRequest = new CacheRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    try {

                        final String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers));
                        Log.e("reponce", jsonString);
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());
                            try {
                                // check response code validations
                                // return "data"object data in return
                                if (jsonString != null && !jsonString.isEmpty())
                                    volleyCallBack.onSuccess(jsonString);
                                else
                                    volleyCallBack.onError(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                                volleyCallBack.onError(null);
                                Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                            }
//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " + "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }

                }
            }) {
                //                // this is the relevant method
                @Override
                protected Map<String, String> getParams() {
                    return data;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);
                    return headers;
                }

                @Override
                public String getCacheKey() {
                    return generateCacheKeyWithParam(super.getCacheKey(), data);
                }
            };

            cacheRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

         /*   cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });*/
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }


    public static void makeJSONObjectRequest(String url, final JSONObject data, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {
            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
            try {
                Log.e("URL", url);
                Log.e("param array", data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            JsonObjectRequest cacheRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Log.e("reponce", response.toString());
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                            JSONObject validateResponse = statusValidations(response);

                            if (validateResponse != null)
                                volleyCallBack.onSuccess(validateResponse);
                            else
                                volleyCallBack.onError(null);
//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " +
                                        "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);

                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }


                }


            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);
                    return headers;
                }


            };
            cacheRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           /* cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });*/
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }

    public static void makeJSONObjectRequest11(String url, final JSONObject data, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {
            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
            try {
                Log.e("URL", url);
                Log.e("param array", data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            JsonObjectRequest cacheRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Log.e("reponce", response.toString());
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                            JSONObject validateResponse = statusValidationsForLogin(response);

                            if (validateResponse != null)
                                volleyCallBack.onSuccess(validateResponse);
                            else
                                volleyCallBack.onError(null);
//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " +
                                        "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }


                }


            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);

                    return headers;
                }


            };
            cacheRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
     /*       cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });*/
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }

    public static void makeJSONObjectRequest1(String url, final JSONObject data, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {
            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
            try {
                Log.e("URL", url);
                Log.e("param array", data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            JsonObjectRequest cacheRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Log.e("reponce", response.toString());
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                            JSONObject validateResponse = statusValidations(response);

                            if (validateResponse != null)
                                volleyCallBack.onSuccess(validateResponse);
                            else
                                volleyCallBack.onError(null);
//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " +
                                        "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }


                }


            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);
                    return headers;
                }


            };
            cacheRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            /*cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });*/
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }


    public static void getJSONObjectRequest(String url, JSONObject data, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {
            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
            try {

                String deviceToken = "";
                if (deviceToken != null)
                    data.put(APIConstants.device_token, deviceToken);
                else
                    data.put(APIConstants.device_token, "");
                Log.e("URL", url);
                Log.e("param array", data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            JsonObjectRequest cacheRequest = new JsonObjectRequest(Request.Method.GET, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Log.e("reponce", response.toString());
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                            JSONObject validateResponse = statusValidations(response);

                            if (validateResponse != null)
                                volleyCallBack.onSuccess(validateResponse);
                            else
                                volleyCallBack.onError(null);
//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " +
                                        "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);
                    return headers;
                }
            };
            cacheRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          /*  cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });*/
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }

    public static void makeStringRequest(String url, JSONObject data, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {
//            if (tag != null)
//                VolleySingleton.getInstance(Application.get()).cancelPendingRequests(tag);
//            try {
//                data.put(Constants.device_type, Constants.DEVICE_TYPE);
//                data.put(APIConstants.app_version, ConstantFunctions.appVersion());
//                data.put(APIConstants.api_version, API_Version);
//                data.put(APIConstants.udid, ConstantFunctions.getUDID());
//                String deviceToken = "";
//                if (deviceToken != null)
//                    data.put(APIConstants.device_token, deviceToken);
//                else
//                    data.put(APIConstants.device_token, "");
//                Log.e("URL", url);
//                Log.e("param array", data.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            try {
                if (tag != null)
                    VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);

                final String requestBody = data.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);

                        try {

                            Log.e("reponce", response.toString());
                            if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                                JSONObject validateResponse = statusValidations(new JSONObject(response));

                                if (validateResponse != null)
                                    volleyCallBack.onSuccess(validateResponse);
                                else
                                    volleyCallBack.onError(null);
//
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            error.printStackTrace();
                            volleyCallBack.onSuccess(null);
                            VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                            if (error.networkResponse.statusCode == 429) {
                                String retryAfter = error.networkResponse.headers.get("Retry-After");
                                if (retryAfter != null && !retryAfter.equals("0")) {
                                    String volleyMessageTime = "Too many attempts. " +
                                            "Please, try after " + retryAfter + " seconds";
                                    Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                                }

                            } else {
                                Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                            }
                        } catch (Exception e) {
                            Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                            e.printStackTrace();
                        }

                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();

                        headers.put("deviceType", APIConstants.deviceType);

                        return headers;
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };


                VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public static void cancelRequestQue(String tag) {
        VolleySingleton.getInstance(MyApplication.get()).getRequestQueue().cancelAll(tag);
    }

    public static void makeJSONArrayRequest(String url, final JSONArray array, String tag, final VolleyCallBack volleyCallBack) {
        // check internet connection. if internet is available then will call this method else display internet connection Message
        if (Utils.checkInternetConnection(MyApplication.get())) {
            if (tag != null)
                VolleySingleton.getInstance(MyApplication.get()).cancelPendingRequests(tag);
            JSONObject data = new JSONObject();
            try {
                String deviceToken = "";
                if (deviceToken != null)
                    data.put(APIConstants.device_token, deviceToken);
                else
                    data.put(APIConstants.device_token, "");
                Log.e("URL", url);
                Log.e("param array", array.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            JsonObjectRequest cacheRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Log.e("reponce", response.toString());
                        if (response != null) {
//                            Log.e("response NetworkUtility", jsonObject.toString());

                            JSONObject validateResponse = statusValidations(response);

                            if (validateResponse != null)
                                volleyCallBack.onSuccess(validateResponse);
                            else
                                volleyCallBack.onError(null);
//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                        volleyCallBack.onSuccess(null);
                        VolleyLog.d(NetworkConstatnt.data + "Error: " + error.getMessage());
                        if (error.networkResponse.statusCode == 429) {
                            String retryAfter = error.networkResponse.headers.get("Retry-After");
                            if (retryAfter != null && !retryAfter.equals("0")) {
                                String volleyMessageTime = "Too many attempts. " +
                                        "Please, try after " + retryAfter + " seconds";
                                Utils.showAlertToast(MyApplication.get(), volleyMessageTime);
                            }

                        } else {
                            Utils.showAlertToast(MyApplication.get(), NetworkConstatnt.volleyMessage);
                        }
                    } catch (Exception e) {
                        Utils.showAlertToast(MyApplication.get(), "something went wrong!");
                        e.printStackTrace();
                    }

                }
            }) {

                @Override
                public byte[] getBody() {
                    try {
                        return array.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("deviceType", APIConstants.deviceType);

                    return headers;
                }

//                    @Override
//                    protected Map<String, String> getParams() {
//                        return paramsArrayMap;
//                    }

            };
            cacheRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          /*  cacheRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }

            });*/
            if (tag != null)
                cacheRequest.setTag(tag);

            VolleySingleton.getInstance(MyApplication.get()).addToRequestQueue(cacheRequest);
        }

    }


    public static JSONObject statusValidations(JSONObject jsonObject) {
        JSONObject response = null;
        response = jsonObject;
        try {


            if (jsonObject.has("d")) {
                response = jsonObject.getJSONObject("d");
            }
            if (response.has("ResponseStatus")) {
                int ResponseStatus = response.getInt("ResponseStatus");
                if (ResponseStatus == 0) {
                    String ErrorMessage = response.getString("ErrorMessage");
                    if (ErrorMessage != null && !ErrorMessage.isEmpty()) {
                        Utils.showAlertToast(MyApplication.get(), ErrorMessage);
                        return null;
                    } else {
                        Utils.showAlertToast(MyApplication.get(), response.getString("Message"));
                    }
                    return null;
                }

                if (ResponseStatus == 1) {
                    return response;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public static JSONObject statusValidationsForLogin(JSONObject jsonObject) {
        try {

            JSONObject response = jsonObject;
            if (response.has("status")) {
                if (!response.getBoolean("status")) {
                    String ErrorMessage = response.getString("message");
                    if (ErrorMessage != null && !ErrorMessage.isEmpty()) {
//                        if (response.has("detail") && !response.getString("detail").isEmpty()) {
//                            Utils.showAlertToastLong(Application.get(), ErrorMessage +" "+ response.getString("detail"));
//                        }else {
//                        }

                        return response;
                    }
                    return response;
                }

                if (response.getBoolean("status")) {
                    return response;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private static void openUpdateDialog(boolean status, String Message) {
//        Context context = Application.get();
//        Intent i1 = new Intent(context, UpdateApplicationActivity.class);
//        i1.putExtra(Constants.update_message, Message);
//        i1.putExtra(Constants.update_status, status);
//
//        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i1);
    }


    public static String generateCacheKeyWithParam(String url, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url += entry.getKey() + "=" + entry.getValue();
        }
        return url;
    }


}

