package com.example.leejunbeom.bookMarker.model.IPS.javaScriptBridge;

import android.content.Context;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by noduritoto on 2016. 11. 21..
 */

public class JavaScriptBridge_impl {
    Handler mHandler;
    Context mContext;

    public JavaScriptBridge_impl(Handler handler){
        this.mHandler = handler;
    }
    public JavaScriptBridge_impl(Context context, Handler handler){
        this.mContext = context;
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

    @JavascriptInterface
    public void showToast(String toast){
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
