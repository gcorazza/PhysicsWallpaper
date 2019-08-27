package com.example.physicswallpaper;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;

import static java.lang.Math.PI;

public class RectWallpaperBody extends WallpaperBody {
    private float wr;
    private float hr;

    public RectWallpaperBody(Body body, int color, float w, float h) {
        super(color, body);
        this.wr = w;
        this.hr = h;
    }

    public void draw(Canvas canvas, Transform transform) {
        canvas.save();
        canvas.translate(transform.p.x, transform.p.y);
        canvas.rotate((float) (transform.q.getAngle()*180/PI)); //angle??
        canvas.drawRect( new RectF(-wr,-hr, wr, hr), innerPaint);
        canvas.drawRect(new RectF(-wr+strokeWidth/2,-hr+strokeWidth/2, wr-strokeWidth/2, hr-strokeWidth/2), outerPaint);
        canvas.restore();
    }
}
