package com.example.leejunbeom.test;

import android.util.JsonReader;

import com.example.leejunbeom.bookMarker.network.okhttp.OkHttp;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jun on 16. 4. 12..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OkHttpTest {
    OkHttp okHttp;

    @Before
    public void setUp(){
        okHttp=new OkHttp();
    }

    @Test
    public void eventTest() throws JSONException {
        try {
            String response=okHttp.doPostRequest("http://52.79.133.224/book/getbook/","34679");
            JSONObject jsonObject=new JSONObject(response);
            System.out.print(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringConverter() throws Exception {
        String string = "abc\u5639";
        byte[] utf8 = string.getBytes("UTF-8");
        string = new String(utf8, "UTF-8");
        System.out.println(string);
    }
}
