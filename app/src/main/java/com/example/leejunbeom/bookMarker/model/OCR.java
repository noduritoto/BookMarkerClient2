package com.example.leejunbeom.bookMarker.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by noduritoto on 2016. 5. 30..
 */
public class OCR {
    String DATA_PATH;
    File _pictureFile;
    String _lang;
    int _cropWidth;

    String _result;
    String TAG = "noduri";
    String TAG1 = "noduri1";

    public OCR(File pictureFile, String lang, String dataPath, int cropWidth){
        this._pictureFile = pictureFile;
        this.DATA_PATH = dataPath;
        this._lang = lang;
        this._cropWidth = cropWidth;
    }

    public void init(File pictureFile, String lang, String dataPath, int cropWidth){
        this._pictureFile = pictureFile;
        this.DATA_PATH = dataPath;
        this._lang = lang;
        this._cropWidth = cropWidth;
    }

    public void ocrProcessing(){
        // ocr 프로세스 시작
        String _path = this._pictureFile.getAbsolutePath();
        String TAG1 = "noduriOCR";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        //_path = DATA_PATH + "ocr2.jpg";
        Log.i("photo path : ", _path);

        Bitmap bitmap = BitmapFactory.decodeFile(_path, options);
        // bitmap crop
        bitmap = cropCenterBitmap(bitmap, this._cropWidth, this._cropWidth);

        // bitmap image modified needed

        try {
            ExifInterface exif = new ExifInterface(_path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Log.v(TAG1, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            Log.v(TAG1, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }

            // Convert to ARGB_8888, required by tess
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        } catch (IOException e) {
            Log.e(TAG1, "Couldn't correct orientation: " + e.toString());
        }

        // _image.setImageBitmap( bitmap );

        Log.v(TAG1, "Before baseApi");

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(this.DATA_PATH, this._lang);
        baseApi.setImage(bitmap);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        Log.v(TAG, "OCRED TEXT: " + recognizedText);

        if ( this._lang.equalsIgnoreCase("eng") ) {
            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9\\.]+", " ");
        }

        recognizedText = recognizedText.trim();
        Log.v(TAG, "RESULT TEXT: " + recognizedText);

        this._result = new String(recognizedText);
                /*

                if ( recognizedText.length() != 0 ) {
                    _field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
                    _field.setSelection(_field.getText().toString().length());
                }
                */

        // Cycle done.

    }

    public String get_result(){
        return this._result;
    }

    /**
     * Bitmap 이미지를 가운데를 기준으로 w, h 크기 만큼 crop한다.
     *
     * @param src 원본
     * @param w 넓이
     * @param h 높이
     * @return
     */
    public static Bitmap cropCenterBitmap(Bitmap src, int w, int h) {
        if(src == null)
            return null;

        int width = src.getWidth();
        int height = src.getHeight();

        if(width < w && height < h)
            return src;

        int x = 0;
        int y = 0;

        if(width > w)
            x = (width - w)/2;

        if(height > h)
            y = (height - h)/2;

        int cw = w; // crop width
        int ch = h; // crop height

        if(w > width)
            cw = width;

        if(h > height)
            ch = height;

        return Bitmap.createBitmap(src, x, y, cw, ch);
    }
}
