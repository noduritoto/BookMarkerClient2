package com.example.leejunbeom.bookMarker.util.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by Jun on 16. 3. 18..
 */
public class JsonParser {

    public static JSONObject ParseStringToJson(String responseString) throws JSONException{
        JSONObject jsonObject= new JSONObject(responseString);
        boolean success=(boolean)jsonObject.get("success");

        if(!success)
            throw new JSONException("fail http request"+(String)jsonObject.get("reason"));

        return jsonObject;
    }
}
