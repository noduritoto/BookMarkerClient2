package com.example.leejunbeom.test.model;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.model.BookController;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jun on 16. 3. 28..
 */
public class BookControllerTest {

    private BookController bookController;

    @Before
    public void setUp() {
        this.bookController = new BookController();
        Book book = new Book();
        book.setMark("801이준범");
        bookController.addBook(book);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void should_add_book_work() {


        assertEquals("fald book add in the list", 1, bookController.getBookList().size());
        assertEquals("fail book add ", "801이준범", bookController.getBookList().get(0).getMark());
    }

    @Test
    public void testGetItemTest() throws Exception {
        assertEquals("fail getItem","801이준범",bookController.getItem(0).getMark());
    }

    @Test
    public void testGetBookList() throws Exception {
        assertNotNull("fail getBooklist",bookController.getBookList());
        assertEquals("fail getBooklist size",1,bookController.getBookList().size());
    }

    @Test
    public void testDeleteBook() throws Exception {
        bookController.deleteItem(0);
        assertEquals("fail delete book",0,bookController.getBookList().size());
    }
}
