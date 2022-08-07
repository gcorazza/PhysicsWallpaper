package com.example.physicswallpaper.Phunlet.draw;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import static java.lang.Math.toDegrees;

public class FixtureDrawRectangle extends FixtureDraw {

    private Vec2 dim;

    public FixtureDrawRectangle(Fixture fixture, int color, float w, float h) {
        this(fixture, color, new Vec2(w, h));
    }

    FixtureDrawRectangle(Fixture fixture, int color, Vec2 dimensions) {
        super(color, fixture);
        this.dim = dimensions;
    }


    public void draw(Canvas canvas, Transform transform) {
        canvas.save();
        canvas.translate(transform.p.x, transform.p.y);
        canvas.rotate((float) (toDegrees(transform.q.getAngle())));
        canvas.translate(offset.x, offset.y);
        canvas.rotate((float) toDegrees(offAngle));
        canvas.drawRect(new RectF(-dim.x, -dim.y, dim.x, dim.y), innerPaint);
        canvas.drawRect(new RectF(-dim.x + strokeWidth / 2, -dim.y + strokeWidth / 2, dim.x - strokeWidth / 2, dim.y - strokeWidth / 2), outerPaint);
        canvas.restore();
    }

    public Vec2 getDim() {
        return dim;
    }
}
