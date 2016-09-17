package com.example.leejunbeom.bookMarker.ui.presenter;

import android.graphics.Bitmap;

import com.example.leejunbeom.bookMarker.model.BitMapController;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.NaviScreen;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Jun on 16. 3. 28..
 */
public class NaviPresenter_impl implements NaviPresenter{

    private BookController bookController;

    @Inject
    public NaviPresenter_impl(BookController bookController) {
        this.bookController=bookController;
    }

    @Override
    public void onBookSearchButtonClick(NaviScreen naviScreen) {
        naviScreen.launchSearchActivity();
    }

    @Override
    public void getMap(String symbolicRequest) {

    }

    @Override
    public void loadBookBitmaps() {
        //ArrayList<Bitmap>
    }

    @Override
    public void refreshListViewData() {
        EventBus.getDefault().post(bookController);
    }

    public BookController getBookController() {
        return bookController;
    }
}
