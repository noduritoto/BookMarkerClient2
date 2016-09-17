package com.example.leejunbeom.test.model;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.util.json.JsonParser;
import com.example.leejunbeom.test.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jun on 16. 4. 17..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class BookTest {

    @Test
    public void testSetJsonDataTest() throws Exception {
        JSONObject jsonObject=null;
        String testString = "{\"success\": true, \"author\": \"[\\u4e2d\\u592e\\u65e5\\u5831\\u793e \\u767c\\u884c]\", \"title\": \"\\uc138\\uacc4\\ub97c\\uac04\\ub2e4.  6: \\ub3c5\\uc77c\", \"feature\": \"\", \"mark\": \"910.202 \\uc911\\uc559\\uc77c\\uc138 v.6\", \"bookshelf\": \"102-2\"}";
        try {
            jsonObject = JsonParser.ParseStringToJson(testString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Book book=new Book(jsonObject);
        assertEquals("jsonData set fail","Book{author='[中央日報社 發行]', featureUrl='', title='세계를간다.  6: 독일', mark='910.202 중앙일세 v.6', bookShelf='bookcase_102-2'}",book.toString());

    }
}
