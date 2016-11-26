package com.example.leejunbeom.bookMarker.ui.presenter;

import com.example.leejunbeom.bookMarker.ui.screen_contracts.TableScreen;

/**
 * Created by noduritoto on 2016. 11. 20..
 */

public class TablePresenter_impl implements TablePresenter {
    @Override
    public void tryFindTable(TableScreen tableScreen){
        tableScreen.tryFindTable();
    }
}
