package com.example.physicswallpaper.phunlet.draw;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import static java.lang.Math.toDegrees;

public class FixtureDrawRectangle extends FixtureDraw {

    private final Vec2 dim;
    private final Vec2 offset;
    private final float offAngle;


    public FixtureDrawRectangle(Fixture fixture, int color, Vec2 dimensions, Vec2 offset, float offAngle) {
        super(color, fixture);
        this.dim = dimensions;
        this.offset = offset;
        this.offAngle = offAngle;
    }


    public void draw(Canvas canvas, Transform transform) {
        Matrix concrete = new Matrix();
        concrete.setTranslate(transform.p.x, transform.p.y);
        concrete.preRotate((float) toDegrees(transform.q.getAngle()));

        Matrix offset = new Matrix();
        offset.setTranslate(this.offset.x, this.offset.y);
        offset.preRotate((float) toDegrees(offAngle));

        canvas.concat(offset);
        canvas.concat(concrete);

        canvas.drawRect(new RectF(-dim.x, -dim.y, dim.x, dim.y), innerPaint);
        canvas.drawRect(new RectF(-dim.x + strokeWidth / 2, -dim.y + strokeWidth / 2, dim.x - strokeWidth / 2, dim.y - strokeWidth / 2), outerPaint);
    }

    public Vec2 getDim() {
        return dim;
    }
}
