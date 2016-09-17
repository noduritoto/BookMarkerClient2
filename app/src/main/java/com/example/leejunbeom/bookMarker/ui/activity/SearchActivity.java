package com.example.leejunbeom.bookMarker.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leejunbeom.bookMarker.dagger.application.AppApplication;
import com.example.leejunbeom.bookMarker.ui.preview.CameraPreview;
import com.example.leejunbeom.test.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by Jun on 16. 3. 28..
 */
public class SearchActivity extends AppCompatActivity {

    private static String TAG = "CAMERA";

    private Context mContext = this;
    private Camera mCamera;
    private CameraPreview camPreview;
    private ImageView MyCameraPreview = null;
    private final String BASE_URL = "http://52.79.133.224";
    private ImageView imageView;
    private FrameLayout mainLayout;
    private Activity mActivity;
    private int PreviewSizeWidth = 640;
    private int PreviewSizeHeight= 480;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_search);
        mContext = this;
        mActivity = this;
        Log.v("intent load", this.getIntent().getStringExtra("imageURL"));

        MyCameraPreview = new ImageView(this);




        try {
            Glide.with(this)
                    .load(BASE_URL + this.getIntent().getStringExtra("imageURL"))
                    //.load(R.drawable.book13)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(500, 500) {

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            //MyCameraPreview.setImageBitmap(resource);
                            //Bitmap bookBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.book5);
                            camPreview = new CameraPreview(MyCameraPreview,resource,getApplicationContext(),mActivity);
                            SurfaceView camView = new SurfaceView(mActivity);
                            SurfaceHolder camHolder = camView.getHolder();
                            camHolder.addCallback(camPreview);
                            camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                            mainLayout = (FrameLayout) findViewById(R.id.camera_preview);
                            mainLayout.addView(camView);
                            mainLayout.addView(MyCameraPreview);
                        }
                    });
        }catch (Exception e){
            Toast.makeText(this,"glide error",Toast.LENGTH_SHORT);
        }
    }


    protected void onPause()
    {
        if ( camPreview != null)
            camPreview.onPause();
        super.onPause();
    }
}
