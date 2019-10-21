package com.example.physicswallpaper.Phunlets;

import android.graphics.Canvas;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;

public class PhunletCircle extends Phunlet {

    private float radius;

    PhunletCircle(Body body, int color, float radius) {
        super(color, body);
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Transform transform) {
        //canvas.drawArc();
    }
}
