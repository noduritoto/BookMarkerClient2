package com.example.leejunbeom.test.activity;

import android.content.Intent;
import android.widget.Button;

import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;
import com.example.leejunbeom.bookMarker.ui.activity.NaviActivity;
import com.example.leejunbeom.bookMarker.ui.activity.SearchActivity;
import com.example.leejunbeom.test.BuildConfig;
import com.example.leejunbeom.test.R;
import com.example.leejunbeom.test.dagger.TestApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Jun on 16. 3. 28..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,application = TestApplication.class)
public class NaviActivityTest {

    NaviActivity naviActivity;

    @Before
    public void setUp() {
        /**
         * intent를 넣어서 activity 생성
         */
        naviActivity = Robolectric.setupActivity(NaviActivity.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void activity_setup_properly_test(){
        assertNotNull("fail setup button",naviActivity.getSearchButton());
        assertNotNull("fail setup presenter",naviActivity.getNaviPresenter());
        //assertNotNull("fail setup symbolicRequestTextfield",naviActivity.getSymbolicRequestText());
    }

    @Test
    public void naviLaunchSearchActivityTest() {
        ShadowActivity shadowActivity = shadowOf(naviActivity);

        //when
        Button launchSearchActivityButton = naviActivity.getSearchButton();
        launchSearchActivityButton.performClick();

        //then
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertThat("search activtiy start fail", startedIntent.getComponent().getClassName(),
                equalTo(SearchActivity.class.getName()));
    }


}
