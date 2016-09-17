package com.example.leejunbeom.bookMarker.model.pojo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Jun on 16. 3. 21..
 */

public class Book {

    private final String prefix="bookcase_";
    private String featureUrl;
    private String title;
    private String author;
    private String mark;
    private String bookShelf;
    private String imageURL;

    public Book(){

    }

    public Book(JSONObject jsonBookObject) throws JSONException {
        this.featureUrl = jsonBookObject.getString("feature");
        this.title = jsonBookObject.getString("title");
        this.author = jsonBookObject.getString("author");
        this.mark = jsonBookObject.getString("mark");
        this.bookShelf = prefix+jsonBookObject.getString("bookshelf");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFeatureUrl() {
        return featureUrl;
    }

    public void setFeatureUrl(String featureUrl) {
        this.featureUrl = featureUrl;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookShelf() {
        return bookShelf;
    }

    public void setBookShelf(String bookShelf) {
        this.bookShelf = bookShelf;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", featureUrl='" + featureUrl + '\'' +
                ", title='" + title + '\'' +
                ", mark='" + mark + '\'' +
                ", bookShelf='" + bookShelf + '\'' +
                '}';
    }
}
