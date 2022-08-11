package com.example.physicswallpaper.sceneCreator;

import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.helper.PhunletMath;

import org.jbox2d.common.Vec2;

public class AddBodyModus extends Modus {
    private final View addFixtureBtn;
    Vec2 bodyPos;
    Vec2 preBodyPos;
    private static Paint coordPaint = coordPaint_();
    private static Paint preCoordPaint = preCoordPaint_();

    private static Paint coordPaint_() {
        Paint coordPaint = new Paint();
        coordPaint.setColor(RED);
        coordPaint.setStrokeWidth(0.1f);
        return coordPaint;
    }

    private static Paint preCoordPaint_() {
        Paint coordPaint = new Paint();
        coordPaint.setColor(MAGENTA);
        coordPaint.setStrokeWidth(0.1f);
        return coordPaint;
    }

    public AddBodyModus(SceneCreatorActivity sceneCreatorActivity, View header) {
        super(sceneCreatorActivity, header);
        View readyBtn = header.findViewById(R.id.readyBodyBtn);
        addFixtureBtn = header.findViewById(R.id.addFixtureBtn);

        addFixtureBtn.setEnabled(false);
        bodyPos = null;
        preBodyPos = null;

        readyBtn.setOnClickListener((view) -> {
            sceneCreatorActivity.popModus();
        });

        addFixtureBtn.setOnClickListener((view) -> {
            sceneCreatorActivity.pushModus(sceneCreatorActivity.addFixtureModus);
        });

    }

    @Override
    public void init() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Matrix inverse = new Matrix();
        v.getMatrix().invert(inverse);
        Vec2 pos = PhunletMath.transformVec(inverse, new Vec2(event.getX(), event.getY()));
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                preBodyPos = pos;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                preBodyPos = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (preBodyPos != null)
                    preBodyPos  = pos;
                break;
            case MotionEvent.ACTION_UP:
                if (preBodyPos != null)
                    bodyPos = preBodyPos;
                preBodyPos = null;
                break;
        }

        updateUI();

        return false;
    }

    private void updateUI() {
        addFixtureBtn.setEnabled(bodyPos != null);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (bodyPos != null)
            drawCoord(canvas, bodyPos.x, bodyPos.y, coordPaint);
        if (preBodyPos != null)
            drawCoord(canvas, preBodyPos.x, preBodyPos.y, preCoordPaint);
    }

}
