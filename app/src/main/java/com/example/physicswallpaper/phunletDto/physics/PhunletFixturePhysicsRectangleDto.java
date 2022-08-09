package com.example.physicswallpaper.phunletDto.physics;

import static com.example.physicswallpaper.phunlet.BodyBuilder.addRect;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class PhunletFixturePhysicsRectangleDto extends PhunletFixturePhysicsDto {
    private final Vec2 offset;
    private final float offAngle;
    private final float w,h;

    public PhunletFixturePhysicsRectangleDto(Vec2 offset, float offAngle, float w, float h, float density) {
        super(density);
        this.offset = offset;
        this.offAngle = offAngle;
        this.w = w;
        this.h = h;
    }

    @Override
    public Fixture create(Body body) {
        return addRect(body, w,h, density, offset, offAngle);
    }
}
