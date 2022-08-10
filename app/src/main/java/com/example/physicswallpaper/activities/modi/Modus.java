package com.example.physicswallpaper.activities.modi;

import android.view.View;

import com.example.physicswallpaper.activities.SceneCreatorActivity;

public abstract class Modus implements View.OnTouchListener {
    private final View header;

    protected Modus(SceneCreatorActivity sceneCreatorActivity, View header) {
        this.header = header;
    }

    public View getHeader() {
        return header;
    }
}
