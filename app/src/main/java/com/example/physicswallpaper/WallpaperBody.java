package com.example.physicswallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import org.jbox2d.common.Rot;
import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;

public abstract class WallpaperBody extends Rot {
    protected final Paint innerPaint = new Paint();
    protected final Paint outerPaint = new Paint();
    protected final Body body;
    protected final float strokeWidth = 0.2f;

    public WallpaperBody(int color, Body body) {
        this.body = body;
        innerPaint.setColor(color);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeJoin(Paint.Join.MITER);
        innerPaint.setAntiAlias(true);

        outerPaint.setColor(darker(color,0.8f));
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeJoin(Paint.Join.MITER);
        outerPaint.setStrokeWidth(strokeWidth);
        outerPaint.setAntiAlias(true);

    }

    public static int darker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    public abstract void draw(Canvas canvas, Transform transform);
}
