package com.example.physicswallpaper.sceneCreator;

import static android.graphics.Color.BLACK;
import static com.example.physicswallpaper.helper.Display.getScreenXcm;
import static com.example.physicswallpaper.helper.Display.getScreenYcm;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public abstract class Modus implements View.OnTouchListener, Drawer {
    private final View header;
    private static Paint screenOutline = screenOutline_();

    private static Paint screenOutline_() {
        Paint paint = new Paint();
        paint.setStrokeWidth(0.1f);
        paint.setColor(BLACK);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    protected Modus(SceneCreatorActivity sceneCreatorActivity, View header) {
        this.header = header;
    }

    public View getHeader() {
        return header;
    }

    public abstract void init();

    protected void drawScreenOutline(Canvas canvas) {
        float screenXcm = getScreenXcm();
        float screenYcm = getScreenYcm();
        canvas.drawRect(0,0, screenXcm, screenYcm, screenOutline);
    }

    protected void drawCoord(Canvas canvas, float x, float y, Paint coordPaint) {
        canvas.drawLine(x-0.5f, y,x +0.5f, y, coordPaint);
        canvas.drawLine(x, y-0.5f,x , y+0.5f, coordPaint);
    }
}
