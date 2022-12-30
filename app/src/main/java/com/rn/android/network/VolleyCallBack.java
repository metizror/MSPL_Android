package com.rn.android.network;

import org.json.JSONObject;

/**
 * Created by espl on 27/9/16.
 */
public interface VolleyCallBack {
    /**
     * On success.
     *
     * @param result the result
     */
    // on success result
    void onSuccess(JSONObject result);

    void onError(JSONObject result);

}
