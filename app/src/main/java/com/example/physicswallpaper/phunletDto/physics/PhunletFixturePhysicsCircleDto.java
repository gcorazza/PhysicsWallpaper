package com.example.physicswallpaper.phunletDto.physics;

import static com.example.physicswallpaper.phunlet.BodyBuilder.addCircle;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class PhunletFixturePhysicsCircleDto extends PhunletFixturePhysicsDto {
    private final float radius;
    private final Vec2 offset;

    public PhunletFixturePhysicsCircleDto(float radius, Vec2 offset, float density) {
        super(density);
        this.radius = radius;
        this.offset = offset;
    }

    @Override
    public Fixture create(Body body) {
        return addCircle(body, radius, density, offset);
    }
}
