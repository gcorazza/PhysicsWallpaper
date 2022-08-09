package com.example.physicswallpaper.phunletDto.draw;

import com.example.physicswallpaper.phunlet.draw.FixtureDraw;

import org.jbox2d.dynamics.Fixture;

public abstract class PhunletFixtureDrawDto {
    public abstract FixtureDraw create(Fixture fixture);
}
