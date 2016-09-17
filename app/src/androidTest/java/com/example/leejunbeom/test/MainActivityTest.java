package com.example.leejunbeom.test;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/*
 * Created by Jun on 16. 3. 13..
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void viewTest(){


        //Intent data = new Intent();
        //data.putExtra("value","leebduk@gmail.com");
        //Instrumentation.ActivityResult activityResult=new Instrumentation.ActivityResult(1,data);
        onView(withId(R.id.bookAddButton)).check(ViewAssertions.matches(isDisplayed()));
        //intending(hasAction("com.google.zxing.client.android.CaptureActivity")).respondWith(activityResult);
        //intended(toPackage("com.google.zxing.integration.android"));
        //intending(to);ß
       // intending(hasAction())
       // intending(toPackage());

    }
}