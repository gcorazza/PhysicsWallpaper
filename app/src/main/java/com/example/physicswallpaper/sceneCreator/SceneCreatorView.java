package com.example.physicswallpaper.sceneCreator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SceneCreatorView extends View {
    private Drawer drawer;

    public SceneCreatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawer != null)
            drawer.onDraw(canvas);
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }
}
