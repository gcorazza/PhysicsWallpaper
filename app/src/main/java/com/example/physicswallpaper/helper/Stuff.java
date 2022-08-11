package com.example.physicswallpaper.helper;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.YELLOW;

import android.graphics.Paint;

public class Stuff {
    public static final Paint testPaint = testPaint();

    public static Paint testPaint(){
        Paint paint = new Paint();
        paint.setColor(GREEN);
        paint.setStrokeWidth(3);
        return paint;
    }
}
