package com.example.leejunbeom.test.presenter;

import com.example.leejunbeom.bookMarker.network.jericho.Jericho;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.ui.presenter.BookAddPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.BookAddPresenter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter_impl;
import com.example.leejunbeom.bookMarker.util.html.HtmlParser;
import com.example.leejunbeom.test.BuildConfig;
import com.example.leejunbeom.test.dagger.DaggerTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Jun on 16. 3. 24..
 */
public class BookAddPresenterTest {

    @Inject
    BookAddPresenter bookAddPresenter;

    @Before
    public void setUp(){
        DaggerTestCase daggerTestCase= new DaggerTestCase();
        daggerTestCase.getTestComponent().inject(this);
    }

    @Test
    public void setUpTest(){
        assertNotNull("inject fail",bookAddPresenter);
        assertNotNull("inject book controller fail", ((BookAddPresenter_impl) bookAddPresenter).getBookController());
    }
}
