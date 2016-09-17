package com.example.leejunbeom.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import com.example.leejunbeom.bookMarker.ui.activity.NaviActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jun on 16. 3. 22..
 */

@RunWith(AndroidJUnit4.class)
public class BookInfoActivityTest {

    @Rule
    public final ActivityRule<NaviActivity> main = new ActivityRule<>(NaviActivity.class,true,false);

    @Test
    public void viewTest(){

        Intents.init();
        main.launchActivity(null);
        onView(withId(R.id.qrcodeInfo)).check(ViewAssertions.matches(isDisplayed()));

        Intent data = new Intent();
        data.putExtra(com.google.zxing.client.android.Intents.Scan.RESULT, "leebduk@gmail.com");
        Instrumentation.ActivityResult activityResult=new Instrumentation.ActivityResult(Activity.RESULT_OK,data);
        intending(hasAction("com.google.zxing.client.android.SCAN")).respondWith(activityResult);
        //intending(toPackage("com.google.zxing.client.android.SCAN")).respondWith(activityResult);

        onView(withId(R.id.qrcode_scan_button)).perform(click());

        onView(withId(R.id.qrcodeInfo)).check(ViewAssertions.matches(withText("leebduk@gmail.com")));

        Intents.release();
    }
}
