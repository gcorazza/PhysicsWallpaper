package com.example.physicswallpaper.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.activities.modi.AddBodyModus;
import com.example.physicswallpaper.activities.modi.AddFixtureModus;
import com.example.physicswallpaper.activities.modi.Modus;
import com.example.physicswallpaper.activities.modi.NothingModus;

public class SceneCreatorActivity extends Activity {

    Modus actualModus;

    View sceneCreatorFooter;
    LinearLayout sceneCreatorHeader;
    View nothingHeader;
    View addBodyHeader;
    View addFixtureHeader;

    public NothingModus nothingModus;
    public AddBodyModus addBodyModus;
    public AddFixtureModus addFixtureModus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene);

        sceneCreatorFooter = findViewById(R.id.sceneCreatorFooter);
        sceneCreatorHeader = findViewById(R.id.sceneCreatorHeader);

        nothingHeader = findViewById(R.id.nothingHeader);
        addBodyHeader = findViewById(R.id.addBodyHeader);
        addFixtureHeader = findViewById(R.id.addFixtureHeader);

        LinearLayout menuParts = findViewById(R.id.menueParts);
        menuParts.removeAllViews();

        nothingModus = new NothingModus(this, nothingHeader);
        addBodyModus = new AddBodyModus(this, addBodyHeader);
        addFixtureModus = new AddFixtureModus(this, addFixtureHeader);

        setModus(nothingModus);

    }

    public void setModus(Modus modus) {
        actualModus = modus;
        sceneCreatorHeader.removeAllViews();
        sceneCreatorHeader.addView(modus.getHeader());
    }

}
