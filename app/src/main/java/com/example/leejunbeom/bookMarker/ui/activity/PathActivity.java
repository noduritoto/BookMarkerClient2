package com.example.leejunbeom.bookMarker.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.ui.presenter.PathPresenter;
import com.example.leejunbeom.bookMarker.ui.screen_contracts.PathScreen;
import com.example.leejunbeom.test.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 이동경로 맵을 표시하기

public class PathActivity extends AppCompatActivity implements PathScreen {

    @Inject PathPresenter pathPresenter;

    @Bind(R.id.pathOKButton) Button pathOKButton;
    @Bind(R.id.pathView) ImageView pathView;

    Bitmap myBM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        ((AppApplication) getApplication()).component().inject(this); // for dagger
        ButterKnife.bind(this); // for butterknife

        //byte[] arr = getIntent().getByteArrayExtra("image");
        myBM =getIntent().getParcelableExtra("computedBitmap");
        //pathView.setImageBitmap(myBM);


    }


    @Override
    public void finishPathActivity(){
        finish();
        //System.exit(0);
        //this.onDestroy();
    }

    @OnClick(R.id.pathOKButton)
    public void onPathButtonClick(){
        System.gc();
        pathPresenter.finishActivity(this);
    }

    // 경로 그림 관련 //////////////////////////////////////////////

    class MyPositionSpot extends RectF{

    }

    class FootPrintSpot extends RectF{

    }



    //////////////////////////////////////////////////////////////////

    /*

    /// 와이파이 신호 관련한 백그라운드 프로세스


    private class PathAsyncTask extends AsyncTask<>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void doInBackground(){
            super.doInBackground();
        }

        @Override
        protected void onPostExecute(){
            super.onPostExecute();
        }
    }
    */

}
