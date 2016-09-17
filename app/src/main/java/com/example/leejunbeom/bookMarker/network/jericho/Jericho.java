package com.example.leejunbeom.bookMarker.network.jericho;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.util.html.HtmlParser;

import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Jun on 16. 3. 22..
 */
public class Jericho {

    private final String libraryUrl="http://library.cau.ac.kr/search/DetailView.ax?";
    private HtmlParser htmlParser;

    @Inject
    public Jericho(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    public Observable<Book> postBook(String UrlString){
        return Observable.just(libraryUrl+UrlString).map(new Func1<String, Book>() {
            @Override
            public Book call(String bookPostUrl) {
                Source htmltoString = null;
                try {
                    htmltoString = new Source(new URL(bookPostUrl));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Book book = (Book) htmlParser.sourceToObject(htmltoString);
                return book;
            }
        });
    }


}
