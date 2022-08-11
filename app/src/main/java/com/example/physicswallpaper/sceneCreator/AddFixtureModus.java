package com.example.physicswallpaper.sceneCreator;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;

public class AddFixtureModus extends Modus {
    public AddFixtureModus(SceneCreatorActivity sceneCreatorActivity, View header) {
        super(sceneCreatorActivity, header);
        View readyBtn = header.findViewById(R.id.readyFixtureBtn);

        readyBtn.setOnClickListener((view) -> {
            sceneCreatorActivity.popModus();
        });

    }

    @Override
    public void init() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawScreenOutline(canvas);
    }
}
