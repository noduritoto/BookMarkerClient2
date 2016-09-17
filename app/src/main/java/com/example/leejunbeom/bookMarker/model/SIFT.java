package com.example.leejunbeom.bookMarker.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Jun on 16. 3. 12..
 */
public class SIFT {

    static {
        System.loadLibrary("opencv_java");
        System.loadLibrary("nonfree");
    }

    private ImageView imageView;
    private Bitmap inputImage; // make bitmap from image resource
    private FeatureDetector detector = FeatureDetector.create(FeatureDetector.SIFT);

    public void sift() {
        Mat rgba = new Mat();
        Utils.bitmapToMat(inputImage, rgba);
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2GRAY);
        detector.detect(rgba, keyPoints);
        Features2d.drawKeypoints(rgba, keyPoints, rgba);
        Utils.matToBitmap(rgba, inputImage);
        imageView.setImageBitmap(inputImage);
    }
}
