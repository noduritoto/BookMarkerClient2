package com.example.leejunbeom.test.presenter;

import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter;
import com.example.leejunbeom.bookMarker.ui.presenter.MainPresenter_impl;
import com.example.leejunbeom.test.BuildConfig;
import com.example.leejunbeom.test.dagger.DaggerTestCase;
import com.example.leejunbeom.test.dagger.TestApplication;
import com.example.leejunbeom.test.testObjectSets.eventBus.test_event;
import com.example.leejunbeom.test.testObjectSets.eventBus.test_eventBusSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jun on 16. 4. 10..
 */

public class MainPresenterTest {

    @Inject
    MainPresenter mainPresenter;

    @Before
    public void setUp(){
        DaggerTestCase daggerTestCase= new DaggerTestCase();
        daggerTestCase.getTestComponent().inject(this);
    }

    @Test
    public void setUpTest(){
        assertNotNull("inject fail",mainPresenter);
        assertNotNull("inject book controller fail",((MainPresenter_impl)mainPresenter).getBookController());
    }
}
