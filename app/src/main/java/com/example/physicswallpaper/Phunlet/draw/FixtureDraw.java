package com.example.physicswallpaper.Phunlet.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public abstract class FixtureDraw {
    protected final Paint innerPaint = new Paint();
    protected final Paint outerPaint = new Paint();
    private int color;
    protected final Fixture fixture;
    protected final float strokeWidth = 0.1f;
    public Vec2 offset = new Vec2();
    public float offAngle = 0;

    FixtureDraw(int color, Fixture fixture) {
        this.color = color;
        this.fixture = fixture;
        colorSet2(color);
    }

    private void colorSet1(int color) {
        innerPaint.setColor(color);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeJoin(Paint.Join.MITER);
        innerPaint.setAntiAlias(false);

        outerPaint.setColor(darker(color, 0.8f));
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeJoin(Paint.Join.MITER);
        outerPaint.setStrokeWidth(strokeWidth);
        outerPaint.setAntiAlias(true);
    }

    private void colorSet2(int color) {
        innerPaint.setColor(paler(color, 0.6f));
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeJoin(Paint.Join.MITER);
        innerPaint.setAntiAlias(false);

        outerPaint.setColor(color);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeJoin(Paint.Join.MITER);
        outerPaint.setStrokeWidth(strokeWidth);
        outerPaint.setAntiAlias(true);
    }

    public static int paler(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] *= factor;
        return Color.HSVToColor(hsv);
    }

    public static int darker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    public void draw(Canvas canvas) {
        draw(canvas, fixture.m_body.getTransform());
    }

    public abstract void draw(Canvas canvas, Transform transform);

    public Body getBody() {
        return fixture.m_body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public Vec2 getOffset() {
        return offset;
    }

    public float getOffAngle() {
        return offAngle;
    }

    public int getColor() {
        return color;
    }
}
