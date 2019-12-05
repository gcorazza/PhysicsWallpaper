package com.example.physicswallpaper.Phunlets;

import android.graphics.Canvas;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Fixture;

import static java.lang.Math.toDegrees;

public class FixtureDrawCircle extends FixtureDraw {

    private float radius;

    FixtureDrawCircle(Fixture fixture, int color, float radius) {
        super(color, fixture);
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Transform transform) {
        canvas.save();
        canvas.translate(transform.p.x, transform.p.y);
        canvas.rotate((float) (toDegrees(transform.q.getAngle())));
        canvas.translate(offset.x, offset.y);
        canvas.rotate((float) toDegrees(offAngle));
        canvas.drawCircle(0, 0, radius, innerPaint);
        canvas.drawCircle(0, 0, radius, outerPaint);
        canvas.drawLine(0, 0, radius, 0, outerPaint);
        canvas.restore();
    }

    public float getRadius() {
        return radius;
    }
}
