package com.example.physicswallpaper.phunletDto.physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public abstract class PhunletFixturePhysicsDto {
    protected final float density;

    protected PhunletFixturePhysicsDto(float density) {
        this.density = density;
    }

    public abstract Fixture create(Body body);
}
