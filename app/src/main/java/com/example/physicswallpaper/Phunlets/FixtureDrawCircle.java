package com.example.physicswallpaper.Phunlets;

import android.graphics.Canvas;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Fixture;

public class FixtureDrawCircle extends FixtureDraw {

    private float radius;

    FixtureDrawCircle(Fixture fixture, int color, float radius) {
        super(color, fixture);
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Transform transform) {
        //canvas.drawArc();
    }

    public float getRadius() {
        return radius;
    }
}
