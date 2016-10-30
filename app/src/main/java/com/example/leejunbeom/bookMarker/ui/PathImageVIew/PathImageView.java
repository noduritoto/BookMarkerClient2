package com.example.leejunbeom.bookMarker.ui.PathImageVIew;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by noduritoto on 2016. 10. 28..
 */

public class PathImageView extends ImageView {

    public PathImageView(Context context){
        super(context);
    }

    public PathImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        System.out.println("Painting content");
        Paint paint  = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setColor(0x0);
        paint.setTextSize(12.0F);
        System.out.println("Drawing text");
        canvas.drawText("Hello World in custom view", 100, 100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
        Log.d("Hello Android", "Got a touch event: " + event.getAction());
        return super.onTouchEvent(event);

    }

}
