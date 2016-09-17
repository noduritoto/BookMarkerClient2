package com.example.leejunbeom.bookMarker.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Jun on 16. 3. 19..
 */
public class Network_impl implements Network{

    private static final String BASE_URL = "http://54.238.172.227:8080/yeshow";
    private static Network_impl network_impl = new Network_impl();
    private static AsyncHttpClient client = new AsyncHttpClient();

    private Network_impl(){

    }

    public static Network getInstance(){
        return network_impl;
    }

    @Override
    public void post(Context context,String url, HttpEntity entity, String contentType, JsonHttpResponseHandler reponseHandler){
        client.post(context,getAbsoluteUrl(url), entity,contentType,reponseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    @Override
    public boolean isOnline(Activity activity) {

        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
