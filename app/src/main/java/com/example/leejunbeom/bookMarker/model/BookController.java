package com.example.leejunbeom.bookMarker.model;

import com.example.leejunbeom.bookMarker.model.pojo.Book;

import java.util.ArrayList;

/**
 * Created by Jun on 16. 3. 28..
 */
public class BookController {

    private ArrayList<Book> bookList=new ArrayList<Book>();

    //test
    public ArrayList<Book> getBookList(){
         return this.bookList;
    }

    //test
    public void addBook(Book book){
        this.bookList.add(book);
    }

    //test
    public Book getItem(int position) {
        return this.bookList.get(position);
    }

    //test
    public void deleteItem(int position) {
        this.bookList.remove(position);
    }

    public int size() {
        return this.bookList.size();
    }
}
