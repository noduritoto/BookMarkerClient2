package com.example.leejunbeom.bookMarker.model.IPS.WIFIBroadCastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.leejunbeom.bookMarker.model.IPS.APInfo.APInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.leejunbeom.test.R.id.webViewForBookFind;

/**
 * Created by noduritoto on 2016. 11. 26..
 */


public class WifiBroadCastReciever extends BroadcastReceiver {

    @Inject
    APInfo apInfo;

    ArrayList<String> MAClist;
    WifiManager wm;
    private List<ScanResult> scanDatas;
    WebView mWebView;

    WifiBroadCastReciever(WifiManager wm, WebView webView){
        this.MAClist = apInfo.getMAClist();
        this.wm = wm;
        this.mWebView = webView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        ArrayList<Integer> SIGlist = new ArrayList<>(MAClist.size());
        int[] signalList = new int[183];
        if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            //signal List 초기화
            for(int i : signalList){
                signalList[i] = -130;
            }
            for(int i : SIGlist){
                SIGlist.set(i, -130);
            }
            scanDatas = wm.getScanResults();
            if (scanDatas.size() == 0) {
                //Toast.makeText(myContext, "Size : 0", Toast.LENGTH_SHORT);
                //scanResult.setText("size : 0");
            }
            else {
                for (ScanResult result : scanDatas) {
                    //if(result.SSID.equals("youngsu")) {
                    if (MAClist.contains(result.BSSID) ) {
                        SIGlist.set(MAClist.indexOf(result.BSSID), result.level);
                    }
                }
                //던져주기
                String listString = "";
                for (int i : SIGlist)
                {
                    listString += String.valueOf(i) + "@";
                }
                //Toast.makeText(myContext, String.valueOf(signalList[0]), Toast.LENGTH_SHORT).show();
                //Log.i("noduritoto ap", "apList to String :" + String.valueOf(SIGlist.get(0).toString()));
                mWebView.loadUrl("javascript:setlocation('kkkkkk')");
            }
            wm.startScan();

        } else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            context.sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
        }
    }
}
