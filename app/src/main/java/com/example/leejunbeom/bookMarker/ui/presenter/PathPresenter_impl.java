package com.example.leejunbeom.bookMarker.ui.presenter;


import com.example.leejunbeom.bookMarker.ui.screen_contracts.PathScreen;

/**
 * Created by noduritoto on 2016. 10. 27..
 */

public class PathPresenter_impl implements PathPresenter{

    @Override
    public void finishActivity(PathScreen pathScreen){
        pathScreen.finishPathActivity();
    }

    @Override
    public void tryScan(PathScreen pathScreen){
        pathScreen.tryScan();
    }
}
