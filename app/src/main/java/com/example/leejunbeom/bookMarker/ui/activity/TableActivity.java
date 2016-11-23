package com.example.leejunbeom.bookMarker.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.network.javaScriptBridge.JavaScriptBridge_impl;
import com.example.leejunbeom.bookMarker.ui.adapter.TableSpinnerAdapter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.TablePresenter;
import com.example.leejunbeom.test.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity {

    @Inject
    TablePresenter tablePresenter;

    @Bind(R.id.tableSpinner)
    Spinner tableSpinner;

    @Bind(R.id.pathForTableButton)
    Button naviForTableButton;

    @Bind(R.id.webViewForTableSearch)
    WebView webViewForTableSearch;

    private TableSpinnerAdapter_impl tableSpinnerAdapter;
    int spinnerPosition;
    Bitmap tableViewBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ((AppApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);

        /*
        // 이미지관련
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inSampleSize = 2;
        tableViewBitmap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10, bitmapOption);
        */

        // 스피너 관련
        tableSpinnerAdapter = new TableSpinnerAdapter_impl(this.getApplicationContext());
        spinnerPosition=0;
        tableSpinner.setAdapter(tableSpinnerAdapter);
        addListener();

        //웹뷰관련
        webViewForTableSearch.getSettings().setJavaScriptEnabled(true);
        webViewForTableSearch.loadUrl("www.naver.com");
        webViewForTableSearch.addJavascriptInterface(
                new JavaScriptBridge_impl(new Handler()),
                "Javascript for Table Search"
        );
    }

    // 스피너 리스너
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
                */
                spinnerPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
