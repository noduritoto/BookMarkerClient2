package com.example.leejunbeom.bookMarker.ui.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.leejunbeom.bookMarker.ui.preview.CameraPreview;
import com.example.leejunbeom.bookMarker.ui.preview.ImageProcessing;
import com.example.leejunbeom.test.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @Bind(R.id.testImageView)
    ImageView testImageView;

    private final String BASE_URL = "http://52.79.133.224";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        /*Glide.with(this).load(R.drawable.book12).asBitmap().into(new SimpleTarget<Bitmap>(500, 500) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ImageProcessing imageProcessing = new ImageProcessing();
                imageProcessing.sift(resource, testImageView);
            }
        });*/

        Glide.with(this)
                .load(BASE_URL + "4838326.jpg")
                        //.load(R.drawable.book13)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(500, 1000) {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        ImageProcessing imageProcessing = new ImageProcessing();
                        imageProcessing.sift(resource, testImageView);
                    }
                });
    }
}
