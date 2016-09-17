package com.example.leejunbeom.bookMarker.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.BitMapController;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.ui.adapter.BookAdapter_impl;
import com.example.leejunbeom.bookMarker.ui.adapter.SpinnerAdapter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter_impl;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.NaviScreen;
import com.example.leejunbeom.test.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

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

    @Bind(R.id.naviButton)
    Button searchButton;

    @Inject
    NaviPresenter naviPresenter;

    @Bind(R.id.libraryView)
    ImageView libraryView;

    @Bind(R.id.spinner2)
    Spinner spinner;

    private boolean mapDraw = true;
    private Bitmap libraryViewBitMap;
    private Bitmap computedBitMap;
    private SpinnerAdapter_impl spinnerAdapter;
    private ArrayList<Book> spinnerBookList;
    private Context myContext;
    private Resources myResources;
    private int spinnerPosition;
    private Activity mActivity;

    //// TODO: 16. 4. 18.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        ((AppApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        this.mActivity = this;
        this.myContext=this.getApplicationContext();
        this.myResources=this.getResources();
        spinnerAdapter = new SpinnerAdapter_impl(this.getApplicationContext());
        //spinnerAdapter.setBookData(spinnerBookList);
        //spinnerAdapter.notifyDataSetChanged();
        spinnerPosition=0;
        spinner.setAdapter(spinnerAdapter);
        addlistener();

        if(mapDraw) {
            libraryViewBitMap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10);
            this.libraryView.setImageBitmap(rotateImage(libraryViewBitMap,90));
            mapDraw=false;
        }
    }

    public void setComputedImage(){
        //spinnerBookList.add(0,spinnerBookList);
    }


    private void addlistener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                libraryView.setImageBitmap(null);
                libraryViewBitMap=null;

                spinnerPosition=position;
                libraryViewBitMap = BitmapFactory.decodeResource(myResources, R.drawable.non10);
                if (position == 0) {

                    libraryView.setImageBitmap(rotateImage(computedBitMap, 90));
                } else {
                    Book book=spinnerBookList.get(position);
                    int resourceId = myResources.getIdentifier(book.getBookShelf(), "drawable", myContext.getPackageName());
                    Glide.with(mActivity).load(resourceId).asBitmap().into(new SimpleTarget<Bitmap>(500, 500) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            libraryView.setImageBitmap(rotateImage(overlayMark(libraryViewBitMap,resource), 90));
                        }
                    });
                    //Bitmap bookBitMap=BitmapFactory.decodeResource(myResources, resourceId);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick(R.id.naviButton)
    public void onSearchButton(){
        naviPresenter.onBookSearchButtonClick(this);
    }


    @Override
    public void launchSearchActivity() {

        Book book=spinnerBookList.get(spinnerPosition);
        if(book.getFeatureUrl()!=null) {

            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("imageURL", book.getFeatureUrl());
            startActivity(intent);
        }else{
            Toast.makeText(this,"책 이미지 등록이 안되있습니다",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapDraw=true;
        naviPresenter.refreshListViewData();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.libraryView.setImageBitmap(null);
    }

    @Subscribe
    public void onSetBookList(BookController bookController){

        this.setSpinnerArrayList(bookController.getBookList());
        this.spinnerAdapter.setBookData(spinnerBookList);
        this.spinnerAdapter.notifyDataSetChanged();

        if(mapDraw){
            computedBitMap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.non10);
            this.libraryView.setImageBitmap(null);
            this.libraryViewBitMap=null;
            /*
             포문 돌면서 이미지 중첩
             */
            for(int i=0;i<bookController.size();i++) {
                Book book=bookController.getItem(i);
                Resources resources = this.getResources();
                int resourceId = resources.getIdentifier(book.getBookShelf(), "drawable", this.getPackageName());
                Bitmap bookBitMap=BitmapFactory.decodeResource(resources, resourceId);
                computedBitMap=this.overlayMark(computedBitMap,bookBitMap);
            }
            // 중첩된 이미지를 셋 한다.
            mapDraw=false;
            this.libraryView.setImageBitmap(rotateImage(computedBitMap,90));
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

    //test
    public Button getSearchButton() {
        return searchButton;
    }

    //test
    public NaviPresenter getNaviPresenter() {
        return naviPresenter;
    }
}