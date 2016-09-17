package com.example.leejunbeom.bookMarker.ui.preview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.leejunbeom.test.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jun on 16. 5. 16..
 */


public class ImageProcessing{

    static {
        System.loadLibrary("opencv_java");
        System.loadLibrary("nonfree");
    }

    private FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
    private DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);

    private Bitmap firstBookBitMap;
    private MatOfKeyPoint keyPoints;
    private Mat firstBookMat;
    private Mat extractBookMat;
    private Context context;
    private int bookWidth;
    private int bookHeight;

    public ImageProcessing(){

    }
    public ImageProcessing(Bitmap bookBitMap, Context context){

        this.firstBookBitMap=bookBitMap;
        firstBookMat = new Mat();
        extractBookMat = new Mat();
        Utils.bitmapToMat(bookBitMap, firstBookMat);
        this.bookWidth=bookBitMap.getWidth();
        this.bookHeight=bookBitMap.getHeight();
        keyPoints = new MatOfKeyPoint();
        Imgproc.cvtColor(firstBookMat, firstBookMat, Imgproc.COLOR_RGBA2GRAY);
        detector.detect(firstBookMat, keyPoints);
        extractor.compute(firstBookMat, keyPoints, extractBookMat);
        this.context=context;
        Log.d("bookBitmap","bookbitmap feature dectected done");
    }

    public void sift(Bitmap inputImage,ImageView imageView){

        Mat rgba = new Mat();
        Utils.bitmapToMat(inputImage, rgba);
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2GRAY);
        detector.detect(rgba, keyPoints);
        Features2d.drawKeypoints(rgba, keyPoints, rgba);
        Utils.matToBitmap(rgba, inputImage);
        imageView.setImageBitmap(inputImage);

    }

    public Bitmap drawMatchedPoint(Bitmap CameraBitMap) {


        Mat desc2= new Mat();
        Mat rgba2 = new Mat();
        Utils.bitmapToMat(CameraBitMap, rgba2);

        /*
        int bitMapWidth=CameraBitMap.getWidth();
        int bitMapHeight=CameraBitMap.getHeight();

        int THREAD_SIZE=3;
        ArrayList<Thread> threads = new ArrayList<Thread>();
        Mat matarray[] = new Mat[THREAD_SIZE*THREAD_SIZE];
        for(int i=0;i<THREAD_SIZE;i++){
            Log.d("Log=====",String.valueOf(bookWidth));
            Mat colMat1=rgba2.colRange(bitMapWidth*i/THREAD_SIZE,bitMapWidth*(i+1)/THREAD_SIZE);
            Mat colMat2=firstBookMat.colRange(bookWidth*i/THREAD_SIZE,bookWidth*(i+1)/THREAD_SIZE);
            for(int j=0;j<THREAD_SIZE;j++){
                matarray[i*THREAD_SIZE+j]=colMat1.rowRange(bitMapHeight*j/THREAD_SIZE,bitMapHeight*(j+1)/THREAD_SIZE);
                Thread t = new Test(matarray[i*THREAD_SIZE+j],colMat2.rowRange(bookHeight*j/THREAD_SIZE,bookHeight*(j+1)/THREAD_SIZE));
                t.start();
                threads.add(t);
            }
        }*/

        MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
        Imgproc.cvtColor(rgba2, rgba2, Imgproc.COLOR_RGBA2GRAY);
        detector.detect(rgba2, keyPoints2);


        MatOfDMatch matches= new MatOfDMatch();
        extractor.compute(rgba2, keyPoints2, desc2);


        if(extractBookMat.type() == desc2.type() && extractBookMat.cols() == desc2.cols()){

                matcher.match(extractBookMat, desc2, matches);
                double max_dist = 0;
                double min_dist = 100;
                List<DMatch> matchesList = matches.toList();
                LinkedList<DMatch> listOfGoodMatches = new LinkedList<>();
                MatOfDMatch goodMatches = new MatOfDMatch();


                for (int i = 0; i < matchesList.size() ;i++) {
                    if (matchesList.get(i).distance < 30) {
                        listOfGoodMatches.add(matchesList.get(i));
                    }
                }

                goodMatches.fromList(listOfGoodMatches);

                List<KeyPoint> keyPointList= new ArrayList<KeyPoint>();
                MatOfKeyPoint keyPointsMatched = new MatOfKeyPoint();

                Bitmap bitmapCopied=null;
                if(listOfGoodMatches.size()>3){

                    /*Mat imageOut=new Mat();
                    Features2d.drawMatches(firstBookMat, keyPoints, rgba2, keyPoints2, goodMatches, imageOut);
                    Bitmap bitmap = Bitmap.createBitmap(imageOut.cols(), imageOut.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(imageOut, bitmap);


                    return bitmap;*/

                    for (int i=0;i<goodMatches.toList().size();i++){
                        DMatch dMatch=goodMatches.toList().get(i);
                        KeyPoint keyPoint=keyPoints2.toList().get(dMatch.trainIdx);
                        keyPointList.add(keyPoint);
                    }

                    keyPointsMatched.fromList(keyPointList);
                    bitmapCopied=CameraBitMap.copy(Bitmap.Config.ARGB_8888,true);
                    Canvas canvas = new Canvas(bitmapCopied);
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.YELLOW);

                    for(int i=0;i<keyPointList.size();i++){
                        Point point2= new Point((int)keyPointList.get(i).pt.x,(int)keyPointList.get(i).pt.y);
                        canvas.drawRect(point2.x-10,point2.y-10, point2.x+10,point2.y+10,paint);
                    }

                }


                //Bitmap bitmap = null;
               // Features2d.drawKeypoints(rgba2, keyPointsMatched, rgba2);
                //Utils.matToBitmap(rgba2, CameraBitMap);

                return bitmapCopied;

                /*Mat imageOut = new Mat();


                if(listOfGoodMatches.size()>3){
                    long tStart= System.currentTimeMillis();
                    Features2d.drawMatches(firstBookMat, keyPoints, rgba2, keyPoints2, goodMatches, imageOut);
                    bitmap = Bitmap.createBitmap(imageOut.cols(), imageOut.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(imageOut, bitmap);
                    long tEnd=System.currentTimeMillis();
                    long tDelta=tEnd-tStart;
                    double elapsedSeconds = tDelta/1000.0;
                    Log.d("first---3:", String.valueOf(elapsedSeconds));
                    Vibrator vibe = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(1000);
                }
            return bitmap;*/
        }
        return null;

    }

    public class Test extends Thread {

        Mat matfirst;
        Mat matsecond;

        public Test(Mat matfirst,Mat matsecond) {
            this.matfirst=matfirst;
            this.matsecond=matsecond;
        }
        public void run() {
            Mat desc = new Mat();
            MatOfKeyPoint keyPoints = new MatOfKeyPoint();
            Imgproc.cvtColor(matfirst, matfirst, Imgproc.COLOR_RGBA2GRAY);
            detector.detect(matfirst, keyPoints);
            extractor.compute(matfirst, keyPoints, desc);

            Mat desc2= new Mat();
            MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
            Imgproc.cvtColor(matsecond, matsecond, Imgproc.COLOR_RGBA2GRAY);
            detector.detect(matsecond, keyPoints2);
            extractor.compute(matsecond, keyPoints2, desc2);

            MatOfDMatch matches= new MatOfDMatch();

            if(desc.type() == desc2.type() && desc.cols() == desc2.cols()){

                matcher.match(desc, desc2, matches);
                double max_dist = 0;
                double min_dist = 100;
                List<DMatch> matchesList = matches.toList();
                LinkedList<DMatch> listOfGoodMatches = new LinkedList<>();
                MatOfDMatch goodMatches = new MatOfDMatch();
                for (int i = 0; i < matchesList.size() ;i++) {
                    if (matchesList.get(i).distance < 40) {
                        listOfGoodMatches.add(matchesList.get(i));
                    }
                }
                goodMatches.fromList(listOfGoodMatches);
            }
        }
    }
}
