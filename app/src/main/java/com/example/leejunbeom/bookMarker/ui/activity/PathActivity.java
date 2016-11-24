package com.example.leejunbeom.bookMarker.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.ui.presenter.PathPresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.PathScreen;
import com.example.leejunbeom.test.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 이동경로 맵을 표시하기

public class PathActivity extends AppCompatActivity implements PathScreen {

    @Inject PathPresenter pathPresenter;

    @Bind(R.id.pathOKButton) Button pathOKButton;
    @Bind(R.id.tryScanButton) Button tryScanButton;
    @Bind(R.id.scanResult) TextView scanResult;

    Bitmap myBM;
    private WifiManager wm;
    private List<ScanResult> scanDatas;
    private ArrayList<String> MAClist;
    private int count, locaNum;

    private ListView listview;
    private FileOutputStream fos;


    private List<String> optimalPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        ((AppApplication) getApplication()).component().inject(this); // for dagger
        ButterKnife.bind(this); // for butterknife

        //byte[] arr = getIntent().getByteArrayExtra("image");
        //myBM =getIntent().getParcelableExtra("computedBitmap");
        //pathView.setImageBitmap(myBM);

        MAClist = new ArrayList<String>();
        // 와이파이 부분
        wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(!wm.isWifiEnabled()) wm.setWifiEnabled(true);

        scanResult.setText("Scan Start\n");
        scanResult.append("GO GO");

        /*
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wm.startScan();
        */

    }


    // 와이파이 리시버
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            String temp;
            if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                scanDatas = wm.getScanResults();
                if (scanDatas.size() == 0) scanResult.setText("size : 0");
                else {
                    count++;
                    scanResult.setText("Count: " + count + " / #AP: " + MAClist.size() + "\n");
                    //temp = locaNum + "\n" + scanDatas.size() + "\n";
                    for (ScanResult result : scanDatas) {
                        //if(result.SSID.equals("youngsu")) {
                        if (!MAClist.contains(result.BSSID) ) {
                            MAClist.add(result.BSSID);
                        }
                        //temp += MAClist.indexOf(result.BSSID)+ "@" +result.level + "\n";
                        // }
                        scanResult.append(MAClist.indexOf(result.BSSID)+ "@" +result.level + "\n");
                    }
                    //scanResult.append(temp);
                    /*
                    try {
                        fos.write(temp.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                }

                if (count == 10) {
                    scanResult.setText(" == Complete ==\n" + scanResult.getText());
                    //SaveAPList();
                    //unregisterReceiver(this);
                }

                else wm.startScan();

            } else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };




    @Override
    public void finishPathActivity(){
        finish();
        //System.exit(0);
        //this.onDestroy();
        unregisterReceiver(wifiReceiver);
    }

    @Override
    public void tryScan(){

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wm.startScan();



        /*
        libraryMap = new LibraryMap();
        optimalPath = libraryMap.getOptimalPath("1", "20");

        for(String data : optimalPath){
            System.out.println(data);
        }
        */




    }

    @OnClick(R.id.pathOKButton)
    public void onPathButtonClick(){
        System.gc();
        pathPresenter.finishActivity(this);
    }

    @OnClick(R.id.tryScanButton)
    public void onTryScanButtonClick(){
        pathPresenter.tryScan(this);
    }

    /*
    // 경로 그림 관련 //////////////////////////////////////////////

    class MyPositionSpot extends RectF{

    }

    class FootPrintSpot extends RectF{

    }

    */


    //////////////////////////////////////////////////////////////////



    /// 와이파이 신호 관련한 백그라운드

    /*


    private class PathAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void doInBackground(Void... p){
            super.doInBackground();
        }

        @Override
        protected void onPostExecute(){
            super.onPostExecute();
        }
    }
    */


}
