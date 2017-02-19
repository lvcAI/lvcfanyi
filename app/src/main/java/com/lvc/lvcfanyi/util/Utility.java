package com.lvc.lvcfanyi.util;

import com.google.gson.Gson;
import com.lvc.lvcfanyi.Test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lvc on 2017/2/18.
 */

public class Utility {

    public static Test handleFanYiResponse(String response){

        try {
            JSONObject  jsonObject = new JSONObject(response);

            return new Gson().fromJson(response,Test.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
