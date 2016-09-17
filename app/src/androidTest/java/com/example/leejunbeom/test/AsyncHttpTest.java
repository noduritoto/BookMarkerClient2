package com.example.leejunbeom.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jun on 16. 3. 19..
 */

public class AsyncHttpTest extends ActivityInstrumentationTestCase2<MainActivity> {

    //public final AsyncHttpClient httpClient = new AsyncHttpClient();
    private Activity myActivity;
    public String aasd;
    int asd=0;

    public AsyncHttpTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myActivity = this.getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void test_should_asynchttpstringpost_work() throws Throwable {
        //given
        /*final CountDownLatch signal = new CountDownLatch(1);
        final AsyncHttpClient httpClient = new AsyncHttpClient();
        JSONObject storeData = new JSONObject();
        storeData.put("store_number", 10011);
        JsonBuilder jsonBuilder = new JsonBuilder_impl();
        JSONObject finalReqData = jsonBuilder.buildRequestData(storeData, "ST00101");
        final RequestParams requseRequestParams = new RequestParams("req",finalReqData.toString());

        //when
        runTestOnUiThread(new Runnable() { // THIS IS THE KEY TO SUCCESS
            @Override
            public void run() {
                httpClient.post("http://54.238.172.227:8080/yeshow/Store/GetList", requseRequestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        asd = 1;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        asd = 1;
                    }

                    public void onFinish() {
                        signal.countDown();
                    }
                });
            }
        });


        try {
            signal.await(30, TimeUnit.SECONDS); // wait for callback
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //then
        assertEquals(1, asd);*/
    }

    /*@SmallTest
    public void test_should_asynchttpjsonpost_work() throws Throwable {
        //given
        final CountDownLatch signal = new CountDownLatch(1);

        JSONObject storeData = new JSONObject();
        storeData.put("store_number", 10011);
        JSONObject finalReqData = NetworkJsonBuilder.buildRequestData(storeData, "ST00101");
        final StringEntity entity = new StringEntity(finalReqData.toString());


        //when
        runTestOnUiThread(new Runnable() { // THIS IS THE KEY TO SUCCESS
            @Override
            public void run() {
                if (Network_impl.getInstance().isOnline(myActivity))
                    Network_impl.getInstance().post(myActivity, "/Store/GetList", entity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                            // Handle resulting parsed JSON response here

                            asd=1;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            //signal.countDown();
                            asd=1;
                        }

                        public void onFinish(){
                            signal.countDown();
                        }
                    });
            }
        });


        try {
            signal.await(30, TimeUnit.SECONDS); // wait for callback
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //then
        assertEquals(0, signal.getCount());

    }*/
}
