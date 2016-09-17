package com.example.leejunbeom.bookMarker.ui.preview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessControlContext;
import java.util.List;

/**
 * Created by Jun on 16. 4. 11..
 */
public class CameraPreview implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private Camera mCamera = null;
    private ImageView MyCameraPreview = null;
    private Bitmap bitmap = null;
    private int[] pixels = null;
    private byte[] FrameData = null;
    private int imageFormat;
    private boolean bProcessing = false;
    private ImageView imageView2;
    private Bitmap bookBitMap;
    private ImageProcessing imageProcessing;
    private ByteArrayOutputStream out;
    private Activity activity;
    private Context context;
    Handler mHandler = new Handler(Looper.getMainLooper());

    public CameraPreview(ImageView CameraPreview, Bitmap bookBitMap,Context context,Activity activity)
    {
        this.context=context;
        //imageView2=imageView;
        MyCameraPreview = CameraPreview;
        //bitmap = Bitmap.createBitmap(PreviewSizeWidth, PreviewSizeHeight, Bitmap.Config.ARGB_8888);
        //pixels = new int[PreviewSizeWidth * PreviewSizeHeight];
        this.bookBitMap=bookBitMap;
        imageProcessing=new ImageProcessing(bookBitMap,context);
        //imageProcessing.sift(bookBitMap,CameraPreview);
        this.activity=activity;
    }

    @Override
    public void onPreviewFrame(byte[] arg0, Camera arg1) {
        // At preview mode, the frame data will push to here.
        if (imageFormat == ImageFormat.NV21) {
            //We only accept the NV21(YUV420) format.
            if (!bProcessing) {
                bProcessing=true;

                FrameData = arg0;
                //imageView2.setImageBitmap(bitmap);
                // mHandler.post(DoImageProcessing);
                Camera.Parameters params = mCamera.getParameters();
                final int w = params.getPreviewSize().width;
                final int h = params.getPreviewSize().height;

                @SuppressWarnings("deprecation")
                int format = params.getPreviewFormat();

                long tStart= System.currentTimeMillis();
                final YuvImage image = new YuvImage(arg0, format, w, h, null);
                final Rect area = new Rect(0, 0, w, h);
                out = new ByteArrayOutputStream();
                image.compressToJpeg(area, 100, out);
                long tEnd=System.currentTimeMillis();
                long tDelta=tEnd-tStart;
                double elapsedSeconds = tDelta/1000.0;
                Log.d("first---2:",String.valueOf(elapsedSeconds));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap asdbitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size());
                        Bitmap bitmap = imageProcessing.drawMatchedPoint(asdbitmap);
                        if (bitmap != null) {
                            MyCameraPreview.setImageBitmap(rotateImage(bitmap, 90));
                            Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(1000);
                        } else {
                            MyCameraPreview.setImageBitmap(rotateImage(asdbitmap, 90));
                        }
                    }
                });

                bProcessing=false;
            }
        }
    }

    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    public void onPause()
    {
        mCamera.stopPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int w, int h)
    {
        Camera.Parameters parameters;

        parameters = mCamera.getParameters();
        //parameters.setPictureSize(MyCameraPreview.getWidth(),MyCameraPreview.getHeight());
        // Set the camera preview size
        //parameters.getPictureSize().width

        List<Camera.Size> tmpList=mCamera.getParameters().getSupportedPreviewSizes();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        Log.d("camera====",parameters.flatten());

        Camera.Size size = getBestPreviewSize(w, h);
        Log.d("camera====best previes size", String.valueOf(size.width) + String.valueOf(size.height));
        parameters.setPreviewSize(320,240);
        //parameters.setPreviewSize(size.width,size.height);
        imageFormat = parameters.getPreviewFormat();

        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    private Camera.Size getBestPreviewSize(int width, int height)
    {
        Camera.Size result=null;
        Camera.Parameters p = mCamera.getParameters();
        for (Camera.Size size : p.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                } else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }
        return result;

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0)
    {
        mCamera = Camera.open();
        try
        {
            // If did not set the SurfaceHolder, the preview area will be black.
            mCamera.setPreviewDisplay(arg0);
            mCamera.setPreviewCallback(this);
        }
        catch (IOException e)
        {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0)
    {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

}