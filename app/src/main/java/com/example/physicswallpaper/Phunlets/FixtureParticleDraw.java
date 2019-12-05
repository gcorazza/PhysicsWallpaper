package com.example.physicswallpaper.Phunlets;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Fixture;

import static java.lang.Math.toDegrees;

public class FixtureParticleDraw extends FixtureDraw {

    public FixtureParticleDraw(int color, Fixture fixture) {
        super(color, fixture);
    }

    private long startTime = System.currentTimeMillis();
    private int timeToLiveMs = 1000;

    @Override
    public void draw(Canvas canvas, Transform transform) {
        canvas.save();
        canvas.translate(transform.p.x, transform.p.y);
        canvas.rotate((float) (toDegrees(transform.q.getAngle())));
        canvas.translate(offset.x, offset.y);
        canvas.rotate((float) toDegrees(offAngle));
        float pw = 0.1f;
        innerPaint.setAlpha(255 - alpha());
        canvas.drawRect(new RectF(-pw, -pw, pw, pw), innerPaint);
        canvas.restore();
    }

    private int alpha() {
        return (int) (((float) (System.currentTimeMillis() - startTime)) / timeToLiveMs * 255);
    }
}
