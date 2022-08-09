package com.example.physicswallpaper.phunletDto;

import com.example.physicswallpaper.phunletDto.draw.PhunletFixtureDrawCircleDto;
import com.example.physicswallpaper.phunletDto.draw.PhunletFixtureDrawRectangleDto;
import com.example.physicswallpaper.phunletDto.physics.PhunletFixturePhysicsCircleDto;
import com.example.physicswallpaper.phunletDto.physics.PhunletFixturePhysicsRectangleDto;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class PhunletDtoBuilder {

    public static PhunletFixtureDto addRect(PhunletBodyDto body, int color, float width, float height, float density) {
        return addRect(body, color, width, height, density, new Vec2(), 0);
    }

    public static PhunletFixtureDto addRect(PhunletBodyDto body, int color, float width, float height, float density, Vec2 offset, float offAngle) {
        PhunletFixtureDto phunletFixtureDto = new PhunletFixtureDto(
                new PhunletFixtureDrawRectangleDto(color, width, height, offset, offAngle),
                new PhunletFixturePhysicsRectangleDto(offset, offAngle, width, height, density));
        body.fixturesDto.add(phunletFixtureDto);
        return phunletFixtureDto;
    }

    public static PhunletFixtureDto addCircle(PhunletBodyDto body, int color, float radius, float density, Vec2 offset) {
        PhunletFixtureDto phunletFixtureDto = new PhunletFixtureDto(
                new PhunletFixtureDrawCircleDto(color, radius),
                new PhunletFixturePhysicsCircleDto(radius, offset, density)
        );
        body.fixturesDto.add(phunletFixtureDto);
        return phunletFixtureDto;
    }

    public static PhunletBodyDto createBody(boolean bullet, BodyType bodyType, boolean allowSleeping) {
        return new PhunletBodyDto(bullet, allowSleeping, bodyType);
    }

    public static PhunletBodyDto createBody() {
        return createBody(false, BodyType.DYNAMIC, false);
    }
}
