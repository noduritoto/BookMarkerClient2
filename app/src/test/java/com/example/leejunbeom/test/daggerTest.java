package com.example.leejunbeom.test;

import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jun on 16. 3. 24..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class daggerTest {

    MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void should_dagger_inject_work_mainpresenter_to_mainactivity_test() {
        assertNotNull("get main activity failed",mainActivity);
        assertNotNull("dagger presenter to activity failed", mainActivity.getMainPresenter().toString());
    }
}
