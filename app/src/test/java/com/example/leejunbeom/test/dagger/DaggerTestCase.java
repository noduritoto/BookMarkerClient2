package com.example.leejunbeom.test.dagger;

import android.app.Application;

import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.dagger.module.AppModule;
import com.example.leejunbeom.bookMarker.ui.activity.BookAddActivity;
import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;
import com.example.leejunbeom.bookMarker.ui.activity.NaviActivity;
import com.example.leejunbeom.test.presenter.BookAddPresenterTest;
import com.example.leejunbeom.test.presenter.MainPresenterTest;
import com.example.leejunbeom.test.presenter.NaviPresenterTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jun on 16. 4. 10..
 */
public class DaggerTestCase extends Application {

    private TestComponent testComponent=DaggerDaggerTestCase_TestComponent.builder().appModule(new AppModule(this)).build();
    @Singleton
    @Component(modules = {AppModule.class})
    public interface TestComponent{
        void inject(MainPresenterTest mainPresenterTest);
        void inject(NaviPresenterTest naviPresenterTest);
        void inject(BookAddPresenterTest bookAddPresenterTest);
    }
    public TestComponent getTestComponent(){
        return testComponent;
    }
}
