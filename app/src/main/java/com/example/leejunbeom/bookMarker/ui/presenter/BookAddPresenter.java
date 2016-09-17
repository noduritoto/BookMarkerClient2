package com.example.leejunbeom.bookMarker.ui.presenter;

import com.example.leejunbeom.bookMarker.ui.screen_contracts.BookAddScreen;

/**
 * Created by Jun on 16. 3. 30..
 */
public interface BookAddPresenter {
    public void getBookData(String url);
    public void finishActivity(BookAddScreen bookAddScreen);
}
