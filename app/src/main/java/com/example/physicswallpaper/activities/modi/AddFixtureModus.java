package com.example.physicswallpaper.activities.modi;

import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.activities.SceneCreatorActivity;

public class AddFixtureModus extends Modus {
    public AddFixtureModus(SceneCreatorActivity sceneCreatorActivity, View header) {
        super(sceneCreatorActivity, header);
        View readyBtn = header.findViewById(R.id.readyFixtureBtn);

        readyBtn.setOnTouchListener((v, event) -> {
            sceneCreatorActivity.setModus(sceneCreatorActivity.nothingModus);
            return true;
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
