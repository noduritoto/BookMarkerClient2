package com.example.leejunbeom.bookMarker.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.example.leejunbeom.bookMarker.model.pojo.Book;
import com.example.leejunbeom.test.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jun on 16. 4. 18..
 */
public class BitMapController {

    private Context context;
    private Map<String,Bitmap> bitmapMap= new HashMap<String,Bitmap>();
    //다 합쳐진 bitmap
    private Bitmap composedBitMap;
    //기본 파일
    private Bitmap baseLibraryBitMap;
    // 합쳐진 bitmap key값
    private final String COMPOSED_LIBRARY_BIT_MAP = "0";

    public BitMapController(Context context){

        EventBus.getDefault().register(this);

        this.context=context;
        /**
         * 기본으로 계속 사용할 bit map
         */
        baseLibraryBitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.non10);

        /**
         * 합쳐진 bit map
         */
        composedBitMap = BitmapFactory.decodeResource(context.getResources(),R.drawable.non10);
        this.bitmapMap.put(COMPOSED_LIBRARY_BIT_MAP, composedBitMap);


    }

    public void setBitMap(BookController bookController){
        //Log.d("call bitMapController setBitMap","asdasdasasdasdasdas");
        /*this.bitmapMap.clear();
        this.bitmapMap.put(COMPOSED_LIBRARY_BIT_MAP,baseLibraryBitMap);
        for(int i=0;i<bookController.size();i++){
            Book book=bookController.getItem(i);
            Bitmap bookBitMap=book.getBookBitMap();
            String mark = book.getMark();
            /**
             * 1. bitMap + base push
             * 2. bitMap + compute push;
             *
             * key는 청구기호
             * 다 합쳐진거는 "0"이 key값
             */
            //this.bitmapMap.put(mark, overlayMark(baseLibraryBitMap, bookBitMap));
            //1 done
            //this.bitmapMap.put(COMPOSED_LIBRARY_BIT_MAP,overlayMark(this.bitmapMap.get(COMPOSED_LIBRARY_BIT_MAP),bookBitMap));
        //}
    }


    private Bitmap overlayMark(Bitmap main, Bitmap sub)

    {

        Bitmap bmOverlay = Bitmap.createBitmap(main.getWidth(), main.getHeight(), main.getConfig());

        Canvas canvas = new Canvas(bmOverlay);

        canvas.drawBitmap(main, 0, 0, null);

        canvas.drawBitmap(sub, 0, 0, null);

        //main.recycle();
        //sub.recycle();

        return bmOverlay;
    }

    public Bitmap getBitMap(String key) {
        return this.bitmapMap.get(key);
    }

    public void recycleAll(){

    }
}
