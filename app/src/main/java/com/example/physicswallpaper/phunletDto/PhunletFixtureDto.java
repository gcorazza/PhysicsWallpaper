package com.example.physicswallpaper.phunletDto;

import com.example.physicswallpaper.phunlet.PhunletFixture;
import com.example.physicswallpaper.phunletDto.draw.PhunletFixtureDrawDto;
import com.example.physicswallpaper.phunletDto.physics.PhunletFixturePhysicsDto;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import java.io.Serializable;

public class PhunletFixtureDto implements Serializable {
    PhunletFixtureDrawDto phunletFixtureDrawDto;
    PhunletFixturePhysicsDto phunletFixturePhysicsDto;

    public PhunletFixtureDto(PhunletFixtureDrawDto phunletFixtureDrawDto, PhunletFixturePhysicsDto phunletFixturePhysicsDto) {
        this.phunletFixtureDrawDto = phunletFixtureDrawDto;
        this.phunletFixturePhysicsDto = phunletFixturePhysicsDto;
    }

    public PhunletFixture create(Body body){
        Fixture fixture = phunletFixturePhysicsDto.create(body);
        return new PhunletFixture(phunletFixtureDrawDto.create(fixture), fixture);
    }
}
