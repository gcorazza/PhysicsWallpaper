package com.example.physicswallpaper.activities.modi;

import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.activities.SceneCreatorActivity;

public class AddBodyModus extends Modus{
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
        return false;
    }
}
