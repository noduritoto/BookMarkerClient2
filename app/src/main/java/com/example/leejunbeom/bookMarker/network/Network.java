package com.example.leejunbeom.bookMarker.network;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Jun on 16. 3. 19..
 */
public interface Network {
    public boolean isOnline(Activity activity);
    public void post(Context context,String url, HttpEntity entity, String contentType, JsonHttpResponseHandler reponseHandler);
}
