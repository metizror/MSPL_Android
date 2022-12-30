package com.rn.android.network;

/**
 * Created by espl on 27/9/16.
 */

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * The type Json parser.
 */
public class JSONParser {
    /**
     * Gets json object.
     *
     * @param data the data
     * @return the json object
     */
    public static JSONObject getJSONObject(Map<String, String> data) {
        try {
            JSONObject json = new JSONObject();
            Set<String> keys = data.keySet();
            for (String string : keys) {
                json.put(string, data.get(string).replace("\\\"", "\""));
            }
            Log.i("params", json.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encode data string.
     *
     * @param json the json
     * @return the string
     */
    public static String encodeData(JSONObject json) {
        StringBuffer encodedData = null;
        try {
            encodedData = new StringBuffer();
            encodedData.append(new String(Base64.encode(json.toString().getBytes(), Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedData.toString();
    }


}
