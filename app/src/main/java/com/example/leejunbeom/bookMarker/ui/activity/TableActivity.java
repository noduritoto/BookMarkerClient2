package com.example.leejunbeom.bookMarker.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.IPS.javaScriptBridge.JavaScriptBridge_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.TablePresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.TableScreen;
import com.example.leejunbeom.test.R;

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
        checkTableNum(new Integer(tableNumEditText.getText().toString()));
        webViewForTableSearch.loadUrl("javascript:setseat('" + tableNumEditText.getText().toString() + "')");
    }

    public void checkTableNum(int tableNum){
        if(tableNum >= 1 && tableNum <= 312){
            Toast.makeText(this, "응 잘했어 (" + tableNumEditText.getText().toString() + ")", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "테이블 번호를 잘못 입력하셨습니다. (1~312)", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.pathForTableButton)
    public void onTableNumButtonClick(){
        tablePresenter.tryFindTable(this);
        //tableNumEditText.getText().toString();
    }
}
