package com.example.physicswallpaper.sceneCreator;

import android.view.View;

public abstract class Modus implements View.OnTouchListener, Drawer {
    private final View header;

    protected Modus(SceneCreatorActivity sceneCreatorActivity, View header) {
        this.header = header;
    }

    public View getHeader() {
        return header;
    }

    public abstract void init();
}
