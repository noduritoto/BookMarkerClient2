package com.example.leejunbeom.test.adapter;

import android.view.View;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.ui.activity.MainActivity;
import com.example.leejunbeom.bookMarker.ui.adapter.BookAdapter_impl;
import com.example.leejunbeom.test.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jun on 16. 4. 6..
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class madapterTest {

    private BookAdapter_impl bookAdapter;
    private ArrayList<Book> bookList;
    private MainActivity mainActivity;
    private Book book1;
    private Book book2;

    @Before
    public void setUp(){
        bookList = new ArrayList<Book>();
        book1 = new Book();
        book2 = new Book();
        book1.setMark("1234");
        book2.setMark("4567");
        bookList.add(book1);
        bookList.add(book2);

        mainActivity = Robolectric.setupActivity(MainActivity.class);
        bookAdapter= new BookAdapter_impl(mainActivity.getApplicationContext());
        bookAdapter.setBookData(bookList);
    }

    @Test
    public void testGetItem() {
        assertEquals("John was expected.", book1.getMark(),
                ((Book) bookAdapter.getItem(0)).getMark());
    }


    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, bookAdapter.getItemId(0));
    }

    @Test
    public void testGetCount() {
        assertEquals("Book amount incorrect.", 2, bookAdapter.getCount());
    }

    @Test
    public void testGetView() {
        View view = bookAdapter.getView(0, null, null);
        //On this part you will have to test it with your own views/data
        assertNotNull("View is null. ", view);
    }

    @Test
    public void testDataChange(){
        Book newBook = new Book();
        newBook.setMark("testDataChange");
        bookList.add(newBook);
        bookAdapter.setBookData(bookList);

        assertEquals("book data set test",3,bookAdapter.getCount());
    }
}
