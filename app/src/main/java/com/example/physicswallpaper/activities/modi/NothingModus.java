package com.example.physicswallpaper.activities.modi;

import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.activities.SceneCreatorActivity;

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
}
