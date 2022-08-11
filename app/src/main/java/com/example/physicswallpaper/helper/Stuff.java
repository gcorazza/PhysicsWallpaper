package com.example.physicswallpaper.helper;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.YELLOW;

import android.graphics.Paint;

public class Stuff {
    public static final Paint testPaint = testPaint();
    public static final Paint testPaint2 = testPaint2();

    public static Paint testPaint(){
        Paint paint = new Paint();
        paint.setColor(GREEN);
        paint.setStrokeWidth(3);
        return paint;
    }

    public static Paint testPaint2(){
        Paint paint = new Paint();
        paint.setColor(YELLOW);
        paint.setStrokeWidth(3);
        return paint;
    }
}
