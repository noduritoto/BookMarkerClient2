package com.example.leejunbeom.bookMarker.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
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

    @Bind(R.id.tableView)
    ImageView tableView;

    private TableSpinnerAdapter_impl tableSpinnerAdapter;
    int spinnerPosition;
    Bitmap tableViewBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ((AppApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);

        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inSampleSize = 2;
        tableViewBitmap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10, bitmapOption);


        // 스피너 관련

        tableSpinnerAdapter = new TableSpinnerAdapter_impl(this.getApplicationContext());
        spinnerPosition=0;
        tableSpinner.setAdapter(tableSpinnerAdapter);
        addListener();
    }

    private void addListener() {
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tableView.setImageBitmap(null);
                //tableViewBitmap=null;
                spinnerPosition=position;

                tableView.setImageBitmap(tableViewBitmap);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
