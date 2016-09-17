package com.example.leejunbeom.bookMarker.util.log;

import android.util.Log;

/**
 * Created by Jun on 16. 3. 18..
 */
public class BLogger {

    private static final String appName="BM_ANDROID_CLIENT";

    public static void log(Object object,String contents){
        Log.d(appName,contents);
    }
}
