package com.example.leejunbeom.test;

import com.example.leejunbeom.test.testObjectSets.eventBus.test_event;
import com.example.leejunbeom.test.testObjectSets.eventBus.test_eventBusSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Jun on 16. 3. 13..
 */
public class EventBusTest {

    private test_eventBusSubscriber test_eventBusSubscriber;

    @Before
    public void setUp(){
        test_eventBusSubscriber=new test_eventBusSubscriber();
        test_event test_event=new test_event();
        EventBus.getDefault().register(test_eventBusSubscriber);
    }

    @Test
    public void eventTest() throws JSONException {
        EventBus.getDefault().post(new test_event());
        assertEquals("event didnt delivered successfully", true, test_eventBusSubscriber.checkEvent());

    }
}
