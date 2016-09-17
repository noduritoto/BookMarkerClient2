package com.example.leejunbeom.bookMarker.ui.presenter;

import com.example.leejunbeom.bookMarker.model.PostApi;
import com.example.leejunbeom.bookMarker.network.jericho.Jericho;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.model.BookController;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.BookAddScreen;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jun on 16. 3. 24..
 */
public class BookAddPresenter_impl implements BookAddPresenter{

    private BookController bookController;
    private Jericho jericho;
    private PostApi postApi;

    @Inject
    public BookAddPresenter_impl(PostApi postApi, BookController bookController) {
        this.postApi=postApi;
        this.bookController=bookController;
    }

    public void getBookData(String url) {

        String[] cidValue = url.split("cid=");
        postApi.postBook(cidValue[1]).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<Book>() {
                    @Override
                    public void call(Book book) {
                        System.out.print(book.toString());
                        EventBus.getDefault().post(book);
                        bookController.addBook(book);
                        EventBus.getDefault().post(bookController);
                    }
                });
    }

    public void finishActivity(BookAddScreen bookAddScreen) {
        bookAddScreen.finishBookAddActivity();
    }

    public BookController getBookController() {
        return bookController;
    }
}
