package com.example.physicswallpaper.sceneCreator;

import static com.example.physicswallpaper.helper.Stuff.testPaint;
import static com.example.physicswallpaper.helper.Stuff.testPaint2;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.helper.PhunletMath;

import org.jbox2d.common.Vec2;

public class AddBodyModus extends Modus {
    private final View addFixtureBtn;
    Vec2 bodyPos;
    Vec2 preBodyPos;

    public AddBodyModus(SceneCreatorActivity sceneCreatorActivity, View header) {
        super(sceneCreatorActivity, header);
        View readyBtn = header.findViewById(R.id.readyBodyBtn);
        addFixtureBtn = header.findViewById(R.id.addFixtureBtn);

        addFixtureBtn.setEnabled(false);
        bodyPos = null;
        preBodyPos = null;

        readyBtn.setOnTouchListener((v, event) -> {
            sceneCreatorActivity.setModus(sceneCreatorActivity.nothingModus);
            return true;
        });

        addFixtureBtn.setOnTouchListener((v, event) -> {
            sceneCreatorActivity.setModus(sceneCreatorActivity.addFixtureModus);
            return true;
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
            canvas.drawCircle(bodyPos.x, bodyPos.y, 20, testPaint);
        if (preBodyPos != null)
            canvas.drawCircle(preBodyPos.x, preBodyPos.y, 20, testPaint2);
    }
}
