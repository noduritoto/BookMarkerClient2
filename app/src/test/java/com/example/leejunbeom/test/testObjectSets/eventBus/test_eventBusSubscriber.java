package com.example.leejunbeom.test.testObjectSets.eventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Jun on 16. 3. 13..
 */
public class test_eventBusSubscriber {

    private boolean check = false;

    public test_eventBusSubscriber(){
        //EventBus.getDefault().register(this);
    }

    public boolean checkEvent() {
        return check;
    }

    @Subscribe
    public void onTest_event(test_event test_event) {

        this.check=true;
    }
}
