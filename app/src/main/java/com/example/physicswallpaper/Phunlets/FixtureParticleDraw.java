package com.example.physicswallpaper.Phunlets;

import android.graphics.Canvas;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Fixture;

public class FixtureParticleDraw extends FixtureDraw {

    public FixtureParticleDraw(int color, Fixture fixture) {
        super(color, fixture);
    }

    @Override
    public void draw(Canvas canvas, Transform transform) {

    }
}
