package com.example.leejunbeom.test.model;

import com.example.leejunbeom.bookMarker.model.PostApi;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.network.okhttp.OkHttp;
import com.example.leejunbeom.test.BuildConfig;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Jun on 16. 4. 12..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PostApiTest {

    PostApi postApi;
    Book testBook;

    @Before
    public void setUp(){
        OkHttp okHttp= Mockito.mock(OkHttp.class);
        try {
            when(okHttp.doPostRequest("http://52.79.133.224/book/getbook/","34679")).
                    thenReturn("{\"success\":true,\"author\":\"[中央日報社 發行]\",\"title\":\"세계를간다.  6: 독일\",\"feature\":\"\",\"mark\":\"910.202 중앙일세 v.6\",\"bookshelf\":\"102-2\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        postApi=new PostApi(okHttp);
    }

    @Test
    public void testConvert() throws Exception {
        String testString="{\"success\": true, \"author\": \"[\\u4e2d\\u592e\\u65e5\\u5831\\u793e \\u767c\\u884c]\", \"title\": \"\\uc138\\uacc4\\ub97c\\uac04\\ub2e4.  6: \\ub3c5\\uc77c\", \"feature\": \"\", \"mark\": \"910.202 \\uc911\\uc559\\uc77c\\uc138 v.6\", \"bookshelf\": \"102-2\"}";
        JSONObject jsonObject = new JSONObject(testString);
        System.out.print(jsonObject.toString());

    }

    @Test
    public void testPostApiObservableBook() throws Exception {

        final CountDownLatch lock = new CountDownLatch(1);
        postApi.postBook("34679").
                subscribe(new Action1<Book>() {
                    @Override
                    public void call(Book book) {
                        lock.countDown();
                        testBook = book;
                    }
                });
        try
        {
            lock.await(5000, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            lock.notifyAll();
        }
        //System.out.print(testBook.toString());
        assertEquals("fail post api book","Book{author='[中央日報社 發行]', featureUrl='', title='세계를간다.  6: 독일', mark='910.202 중앙일세 v.6', bookShelf='bookcase_102-2'}",testBook.toString());
    }
}
