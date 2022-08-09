package com.example.physicswallpaper.phunletDto.draw;

import com.example.physicswallpaper.phunlet.draw.FixtureDraw;
import com.example.physicswallpaper.phunlet.draw.FixtureDrawRectangle;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

public class PhunletFixtureDrawRectangleDto extends PhunletFixtureDrawDto {
    private final int color;
    private final float w,h;
    private final Vec2 offset;
    private final float offAngle;

    public PhunletFixtureDrawRectangleDto(int color, float w, float h, Vec2 offset, float offAngle) {
        this.color = color;
        this.w = w;
        this.h = h;
        this.offset = offset;
        this.offAngle = offAngle;
    }


    @Override
    public FixtureDraw create(Fixture fixture) {
        return new FixtureDrawRectangle(fixture, color, new Vec2(w,h), offset, offAngle);
    }
}
