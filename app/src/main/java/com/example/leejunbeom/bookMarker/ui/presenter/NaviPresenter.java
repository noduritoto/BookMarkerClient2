package com.example.leejunbeom.bookMarker.ui.presenter;

import com.example.leejunbeom.bookMarker.ui.activity.NaviActivity;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.NaviScreen;

/**
 * Created by Jun on 16. 3. 30..
 */
public interface NaviPresenter {
    void onBookSearchButtonClick(NaviScreen naviScreen);
    void getMap(String symbolicRequest);
    void loadBookBitmaps();
    void refreshListViewData();
}
