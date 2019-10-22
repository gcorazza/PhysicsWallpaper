package com.example.physicswallpaper.Phunlets;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import static java.lang.Math.PI;
import static java.lang.Math.toDegrees;

public class FixtureDrawRectangle extends FixtureDraw {
    private float wr;
    private float hr;

    FixtureDrawRectangle(Fixture fixture, int color, float w, float h) {
        super(color, fixture);
        this.wr = w;
        this.hr = h;
    }


    public void draw(Canvas canvas, Transform transform) {
        canvas.save();
        canvas.translate(transform.p.x, transform.p.y);
        canvas.rotate((float) (toDegrees(transform.q.getAngle())));
        canvas.translate(offset.x, offset.y);
        canvas.rotate((float) toDegrees(angle));
        canvas.drawRect(new RectF(-wr, -hr, wr, hr), innerPaint);
        canvas.drawRect(new RectF(-wr + strokeWidth / 2, -hr + strokeWidth / 2, wr - strokeWidth / 2, hr - strokeWidth / 2), outerPaint);
        canvas.restore();
    }
}
