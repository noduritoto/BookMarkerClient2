package com.example.leejunbeom.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.example.leejunbeom.bookMarker.ui.activity.BookAddActivity;
import com.example.leejunbeom.test.BuildConfig;
import com.example.leejunbeom.test.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Jun on 16. 3. 24..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class BookAddActivityTest {

    BookAddActivity bookAddActivity;
    Button bookAddButton;

    @Before
    public void setUp() {
        bookAddActivity = Robolectric.setupActivity(BookAddActivity.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void should_qrcode_activtiy_work_test() {

        ShadowActivity shadowActivity = shadowOf(bookAddActivity);

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        System.out.print(startedIntent.getComponent().getClassName());

        assertThat("ADDBook activtiy start fail", startedIntent.getComponent().getClassName(),
                equalTo("com.journeyapps.barcodescanner.CaptureActivity"));
    }

    @Test
    public void should_getbookinfo_fromhtml_and_parsetoBook_test(){

        Intent intent = shadowOf(bookAddActivity).getNextStartedActivity();

        shadowOf(bookAddActivity).receiveResult(
                intent,
                Activity.RESULT_OK,
                new Intent().putExtra(com.google.zxing.client.android.Intents.Scan.RESULT, "http://library.cau.ac.kr/search/DetailView.ax?sid=1&cid=5241729"));

        ShadowHandler.idleMainLooper();
        assertEquals(ShadowToast.getTextOfLatestToast(), "http://library.cau.ac.kr/search/DetailView.ax?sid=1&cid=5241729");

        ///////////////======================
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.getBackgroundThreadScheduler().idleConstantly(true);

        // A latch used to lock UI Thread.
        final CountDownLatch lock = new CountDownLatch(1);

        try
        {
            lock.await(5000, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            lock.notifyAll();
        }

        // Flush all UI tasks out of queue and force them to execute.
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.getForegroundThreadScheduler().idleConstantly(true);
        ///////////////======================
        TextView textView = (TextView) bookAddActivity.findViewById(R.id.bookTitle);
        assertEquals("양안시와 사시진가헌, 최혜정, 이준범 편저",textView.getText());
    }

    @Test
    public void should_inject_jericho_work_test(){
        //assertNotNull("inject_jericho_to_bookaddpresenter");
    }

    @Test
    public void should_gui_set_properly_test(){

        assertNotNull("confirmButton init fail", bookAddActivity.getConfirmButton());
        assertNotNull("bookTitle init fail", bookAddActivity.getBookTitieView());
        assertNotNull("mainpresenter init fail", bookAddActivity.getBookAddPresenter());
    }

    @Test
    public void should_confirmButton_work_test() {

        Button confirmButton = (Button) this.bookAddActivity.findViewById(R.id.confirmButton);
        confirmButton.performClick();

        ShadowActivity activityShadow = shadowOf(bookAddActivity);
        assertTrue(activityShadow.isFinishing());
    }

    @Test
    public void should_finishActivity_when_get_null_result_test(){
        Intent intent = shadowOf(bookAddActivity).getNextStartedActivity();

        shadowOf(bookAddActivity).receiveResult(
                intent,
                Activity.RESULT_OK,
                new Intent().putExtra(com.google.zxing.client.android.Intents.Scan.RESULT,""));

        ShadowHandler.idleMainLooper();
        ShadowActivity activityShadow = shadowOf(bookAddActivity);
        assertTrue(activityShadow.isFinishing());
    }
}
