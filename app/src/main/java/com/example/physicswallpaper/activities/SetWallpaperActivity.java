package com.example.physicswallpaper.activities;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.physicswallpaper.PhysicsWallpaperService;
import com.example.physicswallpaper.R;
import com.example.physicswallpaper.sceneCreator.SceneCreatorActivity;

public class SetWallpaperActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.setwallpaper);
        findViewById(R.id.createSceneBtn).setOnClickListener((view) -> {
            Intent switchActivityIntent = new Intent(this, SceneCreatorActivity.class);
            startActivity(switchActivityIntent);
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, PhysicsWallpaperService.class));
        startActivity(intent);
    }
}