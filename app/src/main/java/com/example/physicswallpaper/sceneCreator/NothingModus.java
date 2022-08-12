package com.example.physicswallpaper.sceneCreator;


import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;

public class NothingModus extends Modus{
    public NothingModus(SceneCreatorActivity creatorActivity, View header) {
        super(creatorActivity, header);
        View addBodyBtn = header.findViewById(R.id.addBodyBtn);
        addBodyBtn.setOnClickListener((view) -> {
            creatorActivity.pushModus(creatorActivity.addBodyModus);
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
