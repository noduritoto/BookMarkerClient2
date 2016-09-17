package com.example.leejunbeom.test.dagger;

import com.example.leejunbeom.bookMarker.model.PostApi;
import com.example.leejunbeom.bookMarker.network.jericho.Jericho;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.network.okhttp.OkHttp;
import com.example.leejunbeom.bookMarker.ui.presenter.BookAddPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.BookAddPresenter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter_impl;
import com.example.leejunbeom.bookMarker.util.html.HtmlBookParser;
import com.example.leejunbeom.bookMarker.util.html.HtmlParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jun on 16. 3. 28..
 */
@Module
public class TestModule {

    @Provides
    @Singleton
    HtmlParser provideHtmlParser(){
        return new HtmlBookParser();
    }

    @Provides
    @Singleton
    Jericho provideJericho(HtmlParser htmlParser){
        return new Jericho(htmlParser);
    }
    @Provides
    @Singleton
    BookController provideBookController(){
        return new BookController();
    }

    @Provides
    @Singleton
    OkHttp provideOkHttp(){
        return new OkHttp();
    }

    @Provides
    @Singleton
    PostApi providePostApi(OkHttp okHttp){
        return new PostApi(okHttp);
    }


    @Provides
    @Singleton
    MainPresenter provideMainPresenter(BookController bookController){
        return new MainPresenter_impl(bookController);
    }

    @Provides
    @Singleton
    BookAddPresenter provideBookAddPresenter(PostApi postApi,BookController bookController){
        return new BookAddPresenter_impl(postApi,bookController);
    }

    @Provides
    @Singleton
    NaviPresenter provideNaviPresenter(BookController bookController){
        return new NaviPresenter_impl(bookController);
    }
}
