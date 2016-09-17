package com.example.leejunbeom.bookMarker.ui.presenter;

import android.app.Application;

import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.Mainscreen;

/**
 * Created by Jun on 16. 3. 29..
 */
public interface MainPresenter {
    void onBookAddButtonClick(Mainscreen mainscreen);
    void onListViewMenuItemClick(int position);
    void refreshListViewData();
    void onSearchButtonClick(Mainscreen mainscreen);
    void onBookAddOCRButtonClick(Mainscreen mainscreen);
}
