package com.example.physicswallpaper.sceneCreator;

import static android.graphics.Color.YELLOW;

import static com.example.physicswallpaper.helper.Stuff.testPaint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;

public class NothingModus extends Modus{
    public NothingModus(SceneCreatorActivity creatorActivity, View header) {
        super(creatorActivity, header);
        View addBodyBtn = header.findViewById(R.id.addBodyBtn);
        addBodyBtn.setOnTouchListener((v, event) -> {
            creatorActivity.setModus(creatorActivity.addBodyModus);
            return true;
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {

        for (int i = 0; i < 360; i++) {
            canvas.drawLine(0,0,1000,0, testPaint);
            canvas.rotate(1);
        }
    }
}
