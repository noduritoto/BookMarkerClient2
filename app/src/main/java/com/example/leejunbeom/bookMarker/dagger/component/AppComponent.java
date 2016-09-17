package com.example.leejunbeom.bookMarker.dagger.component;

import com.example.leejunbeom.bookMarker.dagger.module.AppModule;
import com.example.leejunbeom.bookMarker.ui.activity.BookAddActivity;
import com.example.leejunbeom.bookMarker.ui.activity.NaviActivity;
import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jun on 16. 3. 12..
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent extends MyComponent{
    void inject(MainActivity mainActivity);
    void inject(BookAddActivity bookAddActivity);
    void inject(NaviActivity naviActivity);
}