package com.example.physicswallpaper.sceneCreator;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.physicswallpaper.R;

import java.util.ArrayList;
import java.util.List;

public class SceneCreatorActivity extends Activity implements Drawer {

    List<Modus> modiStack = new ArrayList<>();

    View sceneCreatorFooter;
    LinearLayout sceneCreatorHeader;
    View nothingHeader;
    View addBodyHeader;
    View addFixtureHeader;

    public NothingModus nothingModus;
    public AddBodyModus addBodyModus;
    public AddFixtureModus addFixtureModus;
    private SceneCreatorView sceneCreatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene);

        sceneCreatorView = findViewById(R.id.sceneCreatorView);
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

        pushModus(nothingModus);
        sceneCreatorView.setDrawer(this);

    }

    public void pushModus(Modus modus) {
        modiStack.add(modus);
        modus.init();
        setModus(modus);
    }

    private void setModus(Modus modus) {
        sceneCreatorHeader.removeAllViews();
        sceneCreatorHeader.addView(modus.getHeader());
        sceneCreatorView.setOnTouchListener((v, event) -> {
            sceneCreatorView.onTouch(v, event);
            sceneCreatorView.invalidate(); //redraw
            modus.onTouch(v, event);
            return true;
        });
        sceneCreatorView.invalidate(); //redraw
    }

    public void popModus() {
        modiStack.remove(modiStack.size()-1);
        setModus(modiStack.get(modiStack.size()-1));
    }

    @Override
    public void onDraw(Canvas canvas) {
        modiStack.forEach(modus -> {
            modus.onDraw(canvas);
        });
    }
}
