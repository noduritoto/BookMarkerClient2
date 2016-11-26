package com.example.leejunbeom.bookMarker.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.model.IPS.APInfo.APInfo;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.model.IPS.javaScriptBridge.JavaScriptBridge_impl;
import com.example.leejunbeom.bookMarker.ui.adapter.SpinnerAdapter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.NaviScreen;
import com.example.leejunbeom.test.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 16. 3. 17..
 */


/**
 * 액비티에 책 정보를 띄우고
 * 서치버튼을 누르면 custom카메라 액티비티를 띄워서 서칭을 시작한다.
 */

public class NaviActivity extends AppCompatActivity implements NaviScreen{

    @Bind(R.id.searchButton)
    Button searchButton;

    @Bind(R.id.pathButton)
    Button pathButton;

    @Inject
    NaviPresenter naviPresenter;

    @Bind(R.id.libraryView)
    ImageView libraryView;

    @Bind(R.id.tableSpinner)
    Spinner spinner;

    @Bind(R.id.webViewForBookFind)
    WebView webViewForBookFind;

    @Bind(R.id.urlEditTextInNavi)
    EditText urlEditTextInNavi;

    private boolean mapDraw = true;
    private Bitmap libraryViewBitMap;
    private Bitmap computedBitMap;
    private SpinnerAdapter_impl spinnerAdapter;
    private ArrayList<Book> spinnerBookList;
    private Context myContext;
    private Resources myResources;
    private int spinnerPosition;
    private Activity mActivity;
    private ArrayList<String> MAClist;
    private APInfo apInfo;


    private WifiManager wm;
    private List<ScanResult> scanDatas;

    //// TODO: 16. 4. 18.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        ((AppApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        naviPresenter.refreshListViewData();

        //화면 안꺼지게
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ///


        this.mActivity = this;
        this.myContext=this.getApplicationContext();
        this.myResources=this.getResources();


        //웹뷰관련
        webViewForBookFind.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 자바스크립트 연동 부분 = ALL
                //Toast.makeText(myContext, "북리스트 갯수 : " + spinnerBookList.size(), Toast.LENGTH_SHORT).show();
                for(Book book :spinnerBookList){
                    webViewForBookFind.loadUrl("javascript:showshelf('" + book.getBookShelfNumber() + "')");
                }

            }
        });
        webViewForBookFind.getSettings().setJavaScriptEnabled(true);
        webViewForBookFind.loadUrl("http://52.79.133.224/location/map/");
        webViewForBookFind.addJavascriptInterface(
                new JavaScriptBridge_impl(new Handler()),
                "Javascript for Book Search"
        );

        // 스피너 관련
       spinnerAdapter = new SpinnerAdapter_impl(this.getApplicationContext());
        //spinnerAdapter.setBookData(spinnerBookList);
        //spinnerAdapter.notifyDataSetChanged();
        spinnerPosition=0;
        spinner.setAdapter(spinnerAdapter);
        addlistener();
        spinner.setSelection(0);
        //mapDraw = true;

        if(mapDraw) {
            //준범
            /*
            libraryViewBitMap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10);
            this.libraryView.setImageBitmap(rotateImage(libraryViewBitMap,90));
            */

            //영훈
            Glide.with(mActivity).load(R.drawable.non10).asBitmap().into(new SimpleTarget<Bitmap>(500, 500) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    //libraryView.setImageBitmap(rotateImage(resource, 90));
                    //훈의 주석처리
                }
            });
            mapDraw=false;
        }



        //와이파이 관련
        apInfo = new APInfo(getResources());
        this.MAClist = apInfo.getMAClist();

        wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(!wm.isWifiEnabled()) wm.setWifiEnabled(true);
        tryScan();


    }

    private void addlistener() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                libraryView.setImageBitmap(null);
                libraryViewBitMap=null;
                spinnerPosition=position;


                //libraryViewBitMap = BitmapFactory.decodeResource(myResources, R.drawable.non10); - 준범
                if (position == 0) {
                    libraryView.invalidate();
                    searchButton.setEnabled(false);
                    pathButton.setEnabled(false);
                    //libraryView.setImageBitmap(rotateImage(computedBitMap, 90)); // 준범, 훈의 주석처리

                    // 자바스크립트 연동 부분 = ALL
                    webViewForBookFind.loadUrl("javascript:clear()");
                    webViewForBookFind.loadUrl("javascript:hideshelf()");
                    for(Book book :spinnerBookList){
                        webViewForBookFind.loadUrl("javascript:showshelf('" + book.getBookShelfNumber() + "')");
                    }

                } else {
                    //computedBitMap.recycle();
                    libraryView.invalidate();
                    searchButton.setEnabled(true);
                    pathButton.setEnabled(true);
                    Log.i("noduri navi test 1. position test", Integer.toString(position) );
                    Book book=spinnerBookList.get(position);
                    int resourceId = myResources.getIdentifier(book.getBookShelf(), "drawable", myContext.getPackageName());
                    //libraryViewBitMap = BitmapFactory.decodeResource(myResources, R.drawable.non10);
                    Log.i("noduri navi test 2. resourceID test", Integer.toString(resourceId) );

                    Glide.with(mActivity).load(resourceId).asBitmap().into(new SimpleTarget<Bitmap>(500, 500) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            final Bitmap checkedBitmap = resource;
                            Glide.with(mActivity).load(R.drawable.non10).asBitmap().into(new SimpleTarget<Bitmap>(500, 500) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    //libraryView.setImageBitmap(rotateImage(overlayMark(resource, checkedBitmap), 90));
                                    //훈의 주석처리
                                }
                            });

                            //libraryView.setImageBitmap(rotateImage(overlayMark(libraryViewBitMap,resource), 90));
                        }
                    });


                    //libraryView.setImageBitmap(rotateImage(overlayMark(libraryViewBitMap, checkedSection), 90));
                    //Bitmap bookBitMap=BitmapFactory.decodeResource(myResources, resourceId);

                    //자바스크립트 연동부분
                    Toast.makeText(myContext, book.getBookShelfNumber(), Toast.LENGTH_SHORT).show();
                    System.out.println(book.getBookShelf());
                    webViewForBookFind.loadUrl("javascript:hideshelf()");
                    webViewForBookFind.loadUrl("javascript:showshelf('" + book.getBookShelfNumber() + "')");
                    webViewForBookFind.loadUrl("javascript:setshelf('" + book.getBookShelfNumber() + "')");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.searchButton)
    public void onSearchButton(){
        naviPresenter.onBookSearchButtonClick(this);
    }

    @OnClick(R.id.pathButton)
    public void onPathButton(){ naviPresenter.onPathButtonClick(this); }


    @Override
    public void launchSearchActivity() {

        Log.i("noduri launchSearchActivity", "");

        Book book=spinnerBookList.get(spinnerPosition);
        Log.i("noduri feature url test ", book.getFeatureUrl() );
        if(book.getFeatureUrl()!=null) {

            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("imageURL", book.getFeatureUrl());

            startActivity(intent);
        }else{
            Toast.makeText(this,"책 이미지 등록이 안되있습니다",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void launchPathActivity(){

        Intent intent = new Intent(this, PathActivity.class);
        startActivity(intent);

        //webViewForBookFind.loadUrl("http://" + urlEditTextInNavi.getText().toString() + "/");
    }

    @Override
    public void onResume() {
        super.onResume();
        mapDraw=true;
        spinner.setSelection(0);
        naviPresenter.refreshListViewData();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        spinner.setSelection(0);
    }

    @Override
    protected void onPause(){

        libraryView.setImageBitmap(null);
        computedBitMap.recycle();
        computedBitMap = null;
        System.gc();

        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void onSetBookList(BookController bookController){

        this.setSpinnerArrayList(bookController.getBookList());
        this.spinnerAdapter.setBookData(spinnerBookList);
        this.spinnerAdapter.notifyDataSetChanged();



        if(mapDraw){
            BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
            bitmapOption.inSampleSize = 2;
            computedBitMap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10, bitmapOption);
            this.libraryView.setImageBitmap(null);
            this.libraryViewBitMap=null;
            /*
             포문 돌면서 이미지 중첩
             */
            for(int i=0;i<bookController.size();i++) {
                Book book=bookController.getItem(i);
                Resources resources = this.getResources();
                int resourceId = resources.getIdentifier(book.getBookShelf(), "drawable", this.getPackageName());
                Bitmap bookBitMap=BitmapFactory.decodeResource(resources, resourceId, bitmapOption);
                computedBitMap=this.overlayMark(computedBitMap,bookBitMap);
            }
            // 중첩된 이미지를 셋 한다.
            mapDraw=false;
            //this.libraryView.setImageBitmap(rotateImage(computedBitMap,90));
        }
    }



    public ArrayList<Book> setSpinnerArrayList(ArrayList<Book> bookArrayList){

        Book computedBook = new Book();
        computedBook.setMark("All");
        spinnerBookList = new ArrayList<Book>(bookArrayList);
        spinnerBookList.add(0, computedBook);
        //spinnerBookList.addAll(1,bookArrayList);

        return null;
    }


    private Bitmap overlayMark(Bitmap main, Bitmap sub)

    {

        Bitmap bmOverlay = Bitmap.createBitmap(main.getWidth(), main.getHeight(), main.getConfig());

        Canvas canvas = new Canvas(bmOverlay);

        canvas.drawBitmap(main, 0, 0, null);

        canvas.drawBitmap(sub, 0, 0, null);

        //main.recycle();
        //sub.recycle();

        return bmOverlay;
    }

    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    // 와이파이 스캔 리스너
    // 와이파이 리시버
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            ArrayList<Integer> SIGlist = new ArrayList<>(MAClist.size());
            int[] signalList = new int[183];
            String temp;
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
                    Toast.makeText(myContext, "result size : 0", Toast.LENGTH_SHORT);
                    //scanResult.setText("size : 0");
                }
                else {
                    for (ScanResult result : scanDatas) {
                        //if(result.SSID.equals("youngsu")) {
                        if (MAClist.contains(result.BSSID) ) {
                            SIGlist.clear();
                            SIGlist.set(MAClist.indexOf(result.BSSID), result.level);
                            //signalList[MAClist.indexOf(result.BSSID)] = result.level;
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
                    webViewForBookFind.loadUrl("javascript:setlocation('kkkkkk')");
                }
                wm.startScan();

            } else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };


    public void tryScan(){

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
        wm.startScan();

    }

    public void arrayListToString(ArrayList<Integer> sigList){
        for (int sig : sigList) {
            sig = -130;
        }
    }
    //test
    public Button getSearchButton() {
        return searchButton;
    }

    //test
    public NaviPresenter getNaviPresenter() {
        return naviPresenter;
    }

}