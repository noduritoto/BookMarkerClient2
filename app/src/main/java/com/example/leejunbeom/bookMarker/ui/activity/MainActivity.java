package com.example.leejunbeom.bookMarker.ui.activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.leejunbeom.bookMarker.SwipeMenuListView.SwipeMenuCreator_impl;
import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.network.Network_impl;
import com.example.leejunbeom.bookMarker.ui.adapter.BookAdapter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.Mainscreen;
import com.example.leejunbeom.test.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 리스트 뷰로 bookController에 있는 책의 목록을 가져오고
 * 각 책을 누르면 BookInfoActivity가 실행된다. intent로 해당하는 ListView의 책 정보를 저장
 * bookAddbutton을누르면 bookAddActivity로 넘어 간다.
 */

public class MainActivity extends AppCompatActivity implements Mainscreen{

    private BookAdapter_impl bAdapter;
    Book book;

    @Inject
    MainPresenter mainPresenter;

    @Bind(R.id.bookAddButton)
    Button bookAddButton;

    @Bind(R.id.listView)
    SwipeMenuListView listView;

    @Bind(R.id.searchButton)
    Button searchButton;


    @Bind(R.id.tableSearchButton)
    Button searchTableButton;

    Context mainContext;

    // permission request num
    private final int MY_PERMISSIONS_REQUEST = 100;


    // private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //injector.get().inject(this);
        mainContext=this.getApplicationContext();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //SwipeMenuListView listView;
        //listView.setMenuCreator();
        ((AppApplication) getApplication()).component().inject(this);

        permissionCheck();
        checkGPS();

        bAdapter = new BookAdapter_impl(this.getApplicationContext());
        listView.setAdapter(bAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator_impl(this.getApplicationContext());
        listView.setMenuCreator(creator);
        addListener();
    }


    public void addListener(){

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //Book item = mBookList.get(position); // 예시
                switch (index) {
                    case 0:
                        onCallMenuItemClick(position);
                        break;
                }
                return false;
            }
        });

        //Long Click Listener Implement
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //onCallItemClick(position);
            }
        });
    }

    private void onCallMenuItemClick(int position){
        this.mainPresenter.onListViewMenuItemClick(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.gc();
        mainPresenter.refreshListViewData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.bookAddButton)
    public void onCallClick(){
        this.mainPresenter.onBookAddButtonClick(this);
    }

    @OnClick(R.id.searchButton)
    public void onCallSearchButton(){
        this.mainPresenter.onSearchButtonClick(this);
    }

    @OnClick(R.id.tableSearchButton)
    public void onTableSearchButtonClick() {
        this.mainPresenter.onTableSearchButtonClick(this);
    }

    /**
     * test
     */
    @Override
    public void launchAddBookActivity() {
        if(Network_impl.getInstance().isOnline(this)){
            Intent intent = new Intent(this, BookAddActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Network Error!!",
                    Toast.LENGTH_LONG).show();
        }
    }

    //test
    @Override
    public void launchNaviActivity() {
        Intent intent = new Intent(this,NaviActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchBookAddOCRActivity() {
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchTableSearchActity(){
        Intent intent = new Intent(this,TableActivity.class);
        startActivity(intent);
    }


    /**
     * test
     * @param bookController
     */
    @Subscribe
    public void onSetBookList(BookController bookController){
        this.bAdapter.setBookData(bookController.getBookList());
        this.bAdapter.notifyDataSetChanged();
    }

    //test
    public SwipeMenuListView getListView() {
        return listView;
    }

    //test
    public MainPresenter getMainPresenter(){
        return this.mainPresenter;
    }

    // test
    public Button getAddButton() {
        return bookAddButton;
    }

    //권한얻기
    public void permissionCheck(){
        //위치

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
        }

        //저장공간

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }

        //카메라
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    permissionCheck();

                } else {

                    //Toast.makeText(this, "permission problem", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //GPS
    public void checkGPS(){
        String context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager)getSystemService(context);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertCheckGPS();
        }
    }

    private void alertCheckGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("사용자의 현위치를 표시하기 위해 위치 기능이 활성화 되어야 합니다.")
                .setCancelable(false)
                .setPositiveButton("위치 설정",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveConfigGPS();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(mainContext, "위치 기능(GPS)을 활성화 해야합니다.", Toast.LENGTH_LONG).show();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // GPS 설정화면으로 이동
    private void moveConfigGPS() {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }
}

