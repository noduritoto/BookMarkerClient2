package com.example.leejunbeom.bookMarker.dagger.component;

import com.example.leejunbeom.bookMarker.ui.activity.BookAddActivity;
import com.example.leejunbeom.bookMarker.ui.activity.NaviActivity;
import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;
import com.example.leejunbeom.bookMarker.ui.activity.PathActivity;

/**
 * Created by Jun on 16. 3. 28..
 */
public interface MyComponent {
    void inject(MainActivity mainActivity);
    void inject(BookAddActivity bookAddActivity);
    void inject(NaviActivity naviActivity);
    void inject(PathActivity pathActivity);
}
