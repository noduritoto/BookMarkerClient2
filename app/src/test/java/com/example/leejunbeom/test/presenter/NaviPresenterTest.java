package com.example.leejunbeom.test.presenter;

import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter_impl;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.NaviPresenter_impl;
import com.example.leejunbeom.test.dagger.DaggerTestCase;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Jun on 16. 4. 10..
 */
public class NaviPresenterTest {

    @Inject
    NaviPresenter naviPresenter;

    @Before
    public void setUp(){
        DaggerTestCase daggerTestCase= new DaggerTestCase();
        daggerTestCase.getTestComponent().inject(this);
    }

    @Test
    public void setUpTest(){
        assertNotNull("inject fail",naviPresenter);
        assertNotNull("inject book controller fail",((NaviPresenter_impl)naviPresenter).getBookController());
    }
}
