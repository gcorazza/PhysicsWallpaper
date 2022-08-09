package com.example.physicswallpaper.phunletDto.draw;

import com.example.physicswallpaper.phunlet.draw.FixtureDraw;
import com.example.physicswallpaper.phunlet.draw.FixtureDrawCircle;

import org.jbox2d.dynamics.Fixture;

public class PhunletFixtureDrawCircleDto extends PhunletFixtureDrawDto {
    private final int color;
    private final float radius;

    public PhunletFixtureDrawCircleDto(int color, float radius) {
        this.color = color;
        this.radius = radius;
    }

    @Override
    public FixtureDraw create(Fixture fixture) {
        return new FixtureDrawCircle(fixture, color, radius);
    }
}
