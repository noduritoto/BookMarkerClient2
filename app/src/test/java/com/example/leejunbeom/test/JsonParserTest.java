package com.example.leejunbeom.test;

import com.example.leejunbeom.bookMarker.util.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static org.junit.Assert.*;
/**
 * Created by Jun on 16. 3. 22..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class JsonParserTest {

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(){

    }

    @Test
    public void testJsonParserWhenCorrectData(){
        JSONObject jsonObject= new JSONObject();
        String testString = "{\"success\": true, \"author\": \"[\\u4e2d\\u592e\\u65e5\\u5831\\u793e \\u767c\\u884c]\", " +
                "\"title\": \"\\uc138\\uacc4\\ub97c\\uac04\\ub2e4.  6: \\ub3c5\\uc77c\", " +
                "\"feature\": \"\", \"mark\": \"910.202 \\uc911\\uc559\\uc77c\\uc138 v.6\", \"bookshelf\": \"102-2\"}";
        try {
            jsonObject = JsonParser.ParseStringToJson(testString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals("fail parser correct jsonObject", "{\"success\":true,\"author\":\"[中央日報社 發行]\",\"title\":\"세계를간다.  6: 독일\",\"feature\":\"\",\"mark\":\"910.202 중앙일세 v.6\",\"bookshelf\":\"102-2\"}",jsonObject.toString());
    }

    @Test(expected=JSONException.class)
    public void testJsonParserWhenFailData() throws JSONException {
        JSONObject jsonObject= new JSONObject();
        String testString = "{\"map\":\"\",\"success\":false,\"author\":\"문근찬 저.\",\"title\":\"경영학 : 피터 드러커 관점\",\"feature\":\"\",\"mark\":\"658문근찬경2\"}";
        JsonParser.ParseStringToJson(testString);

    }
}
