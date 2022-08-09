package com.example.physicswallpaper.phunlet.draw;

import android.graphics.Canvas;
import android.graphics.Matrix;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import static java.lang.Math.toDegrees;

public class FixtureDrawCircle extends FixtureDraw {

    private final float radius;

    public FixtureDrawCircle(Fixture fixture, int color, float radius) {
        super(color, fixture);
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Transform transform) {
        Matrix concrete = new Matrix();
        concrete.setTranslate(transform.p.x, transform.p.y);
        concrete.preRotate((float) toDegrees(transform.q.getAngle()));

        Matrix offset = new Matrix();
        Vec2 pos = ((CircleShape) fixture.getShape()).m_p;
        offset.setTranslate(pos.x, pos.y);

        canvas.concat(concrete);
        canvas.concat(offset);

        canvas.drawCircle(0, 0, radius, innerPaint);
        canvas.drawCircle(0, 0, radius, outerPaint);
        canvas.drawLine(0, 0, radius, 0, outerPaint);
    }

    public float getRadius() {
        return radius;
    }
}
