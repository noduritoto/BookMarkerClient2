package com.example.leejunbeom.bookMarker.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.IPS.APInfo.APInfo;
import com.example.leejunbeom.bookMarker.model.IPS.javaScriptBridge.JavaScriptBridge_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.TablePresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.TableScreen;
import com.example.leejunbeom.test.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TableActivity extends AppCompatActivity implements TableScreen {

    @Inject
    TablePresenter tablePresenter;

    /*스피너 삭제
    @Bind(R.id.tableSpinner)
    Spinner tableSpinner;
    */

    @Bind(R.id.pathForTableButton)
    Button naviForTableButton;

    @Bind(R.id.webViewForTableSearch)
    WebView webViewForTableSearch;

    @Bind(R.id.tableNumEditText)
    EditText tableNumEditText;


    private ArrayList<String> MAClist;
    private APInfo apInfo;
    private WifiManager wm;
    private List<ScanResult> scanDatas;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ((AppApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);
        this.myContext=this.getApplicationContext();

        /*
        // 이미지관련
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inSampleSize = 2;
        tableViewBitmap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10, bitmapOption);
        */

        /*
        // 스피너 관련 - 삭제됨
        tableSpinnerAdapter = new TableSpinnerAdapter_impl(this.getApplicationContext());
        spinnerPosition=0;
        tableSpinner.setAdapter(tableSpinnerAdapter);
        addListener();
        */

        //웹뷰관련
        webViewForTableSearch.setWebViewClient(new WebViewClient());
        webViewForTableSearch.getSettings().setJavaScriptEnabled(true);
        webViewForTableSearch.loadUrl("http://52.79.133.224/location/map/");

        webViewForTableSearch.addJavascriptInterface(
                new JavaScriptBridge_impl(new Handler()),
                "Javascript for Table Search"
        );

        //와이파이 관련
        apInfo = new APInfo(getResources());
        this.MAClist = apInfo.getMAClist();
        wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(!wm.isWifiEnabled()) wm.setWifiEnabled(true);

        tryFindTable();

    }


    /*
    // 스피너 리스너 - 삭제됨
    private void addListener() {
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                /*
                //이미지 관련
                tableView.setImageBitmap(null);
                //tableViewBitmap=null;
                spinnerPosition=position;
                tableView.setImageBitmap(tableViewBitmap);
                // 삭제됨

                spinnerPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    */
    @Override
    public void tryFindTable(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wm.startScan();
        //webViewForTableSearch.loadUrl("javascript:setseat('" + tableNumEditText.getText().toString() + "')");
    }

    public void checkTableNum(int tableNum){
        if(tableNum >= 1 && tableNum <= 312){
            Toast.makeText(this, "응 잘했어 (" + tableNumEditText.getText().toString() + ")", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "테이블 번호를 잘못 입력하셨습니다. (1~312)", Toast.LENGTH_SHORT).show();
        }
    }

    public void tryScan(){

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wm.startScan();

    }

    // 와이파이 스캔 리스너
    // 와이파이 리시버
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            ArrayList<Integer> SIGlist = new ArrayList<>(MAClist.size());
            int[] signalList = new int[184];
            if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                //signal List 초기화
                for(int i=0; i<184; i++){
                    signalList[i] = -130;
                }
                scanDatas = wm.getScanResults();
                if (scanDatas.size() == 0) {
                    Toast.makeText(myContext, "result size : 0", Toast.LENGTH_SHORT);
                    //scanResult.setText("size : 0");
                }
                else {
                    for (ScanResult result : scanDatas) {
                        //if(result.SSID.equals("youngsu")) {
                        if (MAClist.contains(result.BSSID) ) {
                            signalList[MAClist.indexOf(result.BSSID)] = result.level;
                        }
                    }
                    //던져주기
                    String listString = "";
                    for (int i=0; i<184; i++)
                    {
                        if(i==183) {
                            listString += String.valueOf(signalList[i]);
                        }
                        else{
                            listString += String.valueOf(signalList[i]) + "@";
                        }

                    }
                    //Toast.makeText(myContext, "-130", Toast.LENGTH_SHORT).show();
                    Log.i("noduritoto ap", "apList to String :" + listString);
                    webViewForTableSearch.loadUrl("javascript:setlocation('" + listString + "')");

                }
                wm.startScan();

            } else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };


    @OnClick(R.id.pathForTableButton)
    public void onTableNumButtonClick(){
        tablePresenter.tryFindTable(this);
        webViewForTableSearch.loadUrl("javascript:setseat('" + tableNumEditText.getText().toString() + "')");
        //tableNumEditText.getText().toString();
    }
}
