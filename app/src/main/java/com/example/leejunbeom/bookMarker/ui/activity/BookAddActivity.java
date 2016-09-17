package com.example.leejunbeom.bookMarker.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.bookMarker.ui.presenter.BookAddPresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.BookAddScreen;
import com.example.leejunbeom.bookMarker.util.log.BMLogger;
import com.example.leejunbeom.test.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 제일 처음 qrcode인식을 띄우고 실패할경우 액티비티 종료
 * 성공할 경우 network콜을 통해 html소스를 book객체로 변환후 book정보를 띄운다.
 * BookController에 book을 저장한다.
 */
public class BookAddActivity extends AppCompatActivity implements BookAddScreen{

    @Inject
    BookAddPresenter bookAddPresenter;

    @Bind(R.id.confirmButton)
    Button confirmButton;

    @Bind(R.id.bookTitle)
    TextView bookTitieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);

        ((AppApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
        EventBus.getDefault().register(this);
    }

    //test
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if ((scanResult != null) && (scanResult.getContents() != null) && !scanResult.getContents().equals("")) {
            String data = scanResult.getContents();
            Toast.makeText(this, data,
                  Toast.LENGTH_LONG).show();

            this.bookAddPresenter.getBookData(data);

        }else{
            // failed to detect qrCode
            bookAddPresenter.finishActivity(this);
        }

    }

    @OnClick(R.id.confirmButton)
    public void onCallBack(){
        bookAddPresenter.finishActivity(this);
    }


    //test
    @Override
    public void finishBookAddActivity() {
        finish();
    }


    //test
    @Subscribe
    public void onSetBookInfo(Book book){
        this.bookTitieView.setText(book.getTitle()+book.getAuthor());
        Log.d(BMLogger.LOG_TAG, book.toString());
    }

    //test
    public BookAddPresenter getBookAddPresenter() {
        return bookAddPresenter;
    }

    //test
    public Button getConfirmButton() {
        return confirmButton;
    }

    //test
    public TextView getBookTitieView() {
        return bookTitieView;
    }
}
