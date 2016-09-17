package com.example.leejunbeom.bookMarker.model;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.network.okhttp.OkHttp;

import net.htmlparser.jericho.Source;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Jun on 16. 4. 12..
 */
public class PostApi {

    private final String libraryUrl="http://52.79.133.224/book/getbook/";
    private OkHttp okHttp;

    @Inject
    public PostApi(OkHttp okHttp){
        this.okHttp=okHttp;
    }

    public Observable<Book> postBook(String requestData){

        return Observable.just(requestData).map(new Func1<String, JSONObject>() {
            @Override
            public JSONObject call(String requestData) {
                JSONObject jsonObjectBook=null;
                try {
                    String response = okHttp.doPostRequest(libraryUrl, requestData);
                    jsonObjectBook = new JSONObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObjectBook;
            }
        }).map(new Func1<JSONObject, Book>() {
            @Override
            public Book call(JSONObject jsonBookObject) {
                Book book=null;
                try {
                    book= new Book(jsonBookObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return book;
            }
        });
    }
}
