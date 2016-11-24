package com.example.leejunbeom.bookMarker.ui.customImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by noduritoto on 2016. 11. 24..
 */

public class LibraryImageView extends ImageView {
    public LibraryImageView(Context context){
        super(context);
        Toast.makeText(getContext(), "custom Library image View", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Toast.makeText(getContext(), "custom Library image View", Toast.LENGTH_LONG).show();
    }
}
