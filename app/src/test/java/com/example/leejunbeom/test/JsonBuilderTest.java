package com.example.leejunbeom.test;

import com.example.leejunbeom.bookMarker.util.json.NetworkJsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.*;

/**
 * Created by Jun on 16. 3. 17..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class JsonBuilderTest {

    @Before
    public void setUp(){
    }

    @Test
    public void putTest() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", new JSONObject().put("name", "jun"));
        JSONObject requestData=null;
        JSONObject reqData=null;
        try {
            requestData = NetworkJsonBuilder.buildRequestData(jsonObject, "100");

        }catch (JSONException e){
            e.printStackTrace();
        }
        assertNotNull("reqDataNOTNuLL", requestData);
        assertEquals("jsonBuild code", "100", requestData.get("reqCode"));
        reqData=requestData.getJSONObject("reqData");
        JSONObject user=reqData.getJSONObject("user");
        assertEquals("jsonBuild Success", "jun", user.get("name"));
    }

}
