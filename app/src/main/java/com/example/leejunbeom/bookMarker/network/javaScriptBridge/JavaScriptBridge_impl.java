package com.example.leejunbeom.bookMarker.network.javaScriptBridge;

import android.os.Handler;
import android.renderscript.RenderScript;
import android.webkit.JavascriptInterface;

/**
 * Created by noduritoto on 2016. 11. 21..
 */

public class JavaScriptBridge_impl {
    Handler mHandler;
    public JavaScriptBridge_impl(Handler handler){
        this.mHandler = handler;
    }

    @JavascriptInterface
    public void test(final String url){
        mHandler.post(new Runnable(){
            @Override
            public void run(){
                //원하는 동작
            }
        });
    }
}
