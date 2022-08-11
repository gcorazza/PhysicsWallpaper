package com.example.physicswallpaper.sceneCreator;

import static com.example.physicswallpaper.helper.Stuff.testPaint;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;

import org.jbox2d.common.Vec2;

public class AddBodyModus extends Modus {
    Vec2 bodyPos;

    public AddBodyModus(SceneCreatorActivity sceneCreatorActivity, View header) {
        super(sceneCreatorActivity, header);
        View readyBtn = header.findViewById(R.id.readyBodyBtn);
        View addFixtureBtn = header.findViewById(R.id.addFixtureBtn);

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
    public boolean onTouch(View v, MotionEvent event) {
        bodyPos = new Vec2(event.getX(),event.getY());
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(bodyPos == null)
            return;
        canvas.drawCircle(bodyPos.x, bodyPos.y, 20, testPaint);
    }
}
